package com.lyf.gobangccn.ui.contacts;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.utils.EaseCommonUtils;
import com.hyphenate.exceptions.HyphenateException;
import com.jiang.common.base.irecyclerview.IRecyclerView;
import com.jiang.common.widget.ToolBarBuilder;
import com.lyf.gobangccn.R;
import com.lyf.gobangccn.base.MVPBaseFragment;
import com.lyf.gobangccn.ui.NewFriendsMsgActivity;
import com.lyf.gobangccn.ui.addfriend.AddContactActivity;
import com.lyf.gobangccn.ui.chat.ChatActivity;
import com.lyf.gobangccn.ui.login.LoginActivity;
import com.lyf.gobangccn.ui.login.LoginManager;
import com.lyf.gobangccn.view.recyclerview.RecyclerViewInit;
import com.lyf.gobangccn.view.view.ContactItemView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by 01F on 2017/7/2.
 */

public class ContactsFragment extends MVPBaseFragment {
    protected List<String> contactList = new ArrayList<>();
    @BindView(R.id.irv_contacts)
    IRecyclerView mIrvContacts;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    private ContactsAdapter mAdapter;
    private Map<String, EaseUser> contactsMap;
    private PopupWindow mPopWindow;

    @Override
    public int getLayoutId() {
        return R.layout.frag_contacts;
    }

    @Override
    protected void init(View view) {
        new ToolBarBuilder(view)
                .inflateMenu(R.menu.menu_add)
                .setTitle(getString(R.string.contacts))
                .setOnMenuItemClickListener(item -> {
                    showPopWindow();
                    return true;
                });
        mAdapter = new ContactsAdapter(mContext, contactList);
        RecyclerViewInit.init(mContext, mIrvContacts, mAdapter, new LinearLayoutManager(mContext));
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.em_contacts_header, null);
        ContactItemView contactItemView = (ContactItemView) inflate.findViewById(R.id.application_item);
        contactItemView.setOnClickListener(v -> startActivity(new Intent(getActivity(), NewFriendsMsgActivity.class)));
        mIrvContacts.addHeaderView(inflate);
        mIrvContacts.setOnRefreshListener(() -> {
            mIrvContacts.setRefreshing(false);
            refresh();
//            getContactList();
            mAdapter.notifyDataSetChanged();
        });
        mAdapter.setOnItemClickListener((view1, position) -> {
            String user = contactList.get(position);
            if (user != null) {

                startActivity(new Intent(getActivity(), ChatActivity.class).putExtra("userId", user));
            }
        });

    }


    @Override
    public void onResume() {
        super.onResume();
        mIrvContacts.setRefreshing(true);
    }


    @Override
    public void refresh() {
        super.refresh();
//        Map<String, EaseUser> m = DemoHelper.getInstance().getContactList();
//        if (m instanceof Hashtable<?, ?>) {
//            //noinspection unchecked
//            m = (Map<String, EaseUser>) ((Hashtable<String, EaseUser>) m).clone();
//        }
        Observable.create(new Observable.OnSubscribe<List<String>>() {
            @Override
            public void call(Subscriber<? super List<String>> subscriber) {
                try {
                    List<String> userName = EMClient.getInstance().contactManager().getAllContactsFromServer();
                    subscriber.onNext(userName);
                    subscriber.onCompleted();
                } catch (HyphenateException e) {
                    e.printStackTrace();
                    subscriber.onError(e);
                    subscriber.onCompleted();
                }
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<String>>() {
                    @Override
                    public void call(List<String> strings) {
                        contactList.clear();
                        contactList.addAll(strings);
                        mAdapter.notifyDataSetChanged();
                    }
                });

    }

    public void showPopWindow() {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.pop_item, null);
        mPopWindow = new PopupWindow(inflate,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, true);
        TextView addFriend = (TextView) inflate.findViewById(R.id.tv_addfriend);
        TextView loginout = (TextView) inflate.findViewById(R.id.tv_loginout);
        addFriend.setOnClickListener(v -> startActivity(new Intent(getActivity(), AddContactActivity.class)));
        loginout.setOnClickListener(v -> {
            LoginManager.getInstance().loginOut();
            if (!LoginManager.getInstance().isLogin()) {
                startActivity(new Intent(getActivity(), LoginActivity.class));
                getActivity().finish();
            }
        });
        mPopWindow.setOutsideTouchable(true);
        mPopWindow.setBackgroundDrawable(new BitmapDrawable());
        DisplayMetrics metrics = getResources().getDisplayMetrics();

        mPopWindow.showAsDropDown(mToolbar, metrics.widthPixels, 0);

    }

    /**
     * set contacts map, key is the hyphenate id
     *
     * @param contactsMap
     */
    public void setContactsMap(Map<String, EaseUser> contactsMap) {
        this.contactsMap = contactsMap;
    }

    /**
     * get contact list and sort, will filter out users in blacklist
     */
    protected void getContactList() {
        contactList.clear();
        if (contactsMap == null) {
            return;
        }
        synchronized (this.contactsMap) {
            Iterator<Map.Entry<String, EaseUser>> iterator = contactsMap.entrySet().iterator();
            List<String> blackList = EMClient.getInstance().contactManager().getBlackListUsernames();
            while (iterator.hasNext()) {
                Map.Entry<String, EaseUser> entry = iterator.next();
                // to make it compatible with data in previous version, you can remove this check if this is new app
                if (!entry.getKey().equals("item_new_friends")
                        && !entry.getKey().equals("item_groups")
                        && !entry.getKey().equals("item_chatroom")
                        && !entry.getKey().equals("item_robots")) {
                    if (!blackList.contains(entry.getKey())) {
                        //filter out users in blacklist
                        EaseUser user = entry.getValue();
                        EaseCommonUtils.setUserInitialLetter(user);
//                        contactList.add(user);
                    }
                }
            }
        }

        // sorting
//        Collections.sort(contactList, new Comparator<EaseUser>() {
//
//            @Override
//            public int compare(EaseUser lhs, EaseUser rhs) {
//                if (lhs.getInitialLetter().equals(rhs.getInitialLetter())) {
//                    return lhs.getNick().compareTo(rhs.getNick());
//                } else {
//                    if ("#".equals(lhs.getInitialLetter())) {
//                        return 1;
//                    } else if ("#".equals(rhs.getInitialLetter())) {
//                        return -1;
//                    }
//                    return lhs.getInitialLetter().compareTo(rhs.getInitialLetter());
//                }
//
//            }
//        });

    }


    @Override
    protected void initInjector() {

    }


}
