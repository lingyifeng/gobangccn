package com.lyf.gobangccn.ui.addfriend;

import android.app.ProgressDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.widget.EaseAlertDialog;
import com.hyphenate.exceptions.HyphenateException;
import com.jiang.common.utils.LogUtils;
import com.jiang.common.utils.ToastUtil;
import com.jiang.common.widget.ToolBarBuilder;
import com.lyf.gobangccn.R;
import com.lyf.gobangccn.base.MVPBaseActivity;
import com.lyf.gobangccn.easemob.DemoHelper;
import com.lyf.gobangccn.rx.RxSchedulers;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;

public class AddContactActivity extends MVPBaseActivity {


    @BindView(R.id.edit_note)
    EditText mEditNote;
    @BindView(R.id.avatar)
    ImageView mAvatar;
    @BindView(R.id.name)
    TextView mName;
    @BindView(R.id.indicator)
    Button mIndicator;
    @BindView(R.id.ll_user)
    RelativeLayout mLlUser;
    @BindView(R.id.search)
    Button mSearch;
    private ProgressDialog progressDialog;
    private String mNameText;

    @Override
    public int getLayoutId() {
        return R.layout.activity_add_contact;
    }

    @Override
    protected void init() {
        new ToolBarBuilder(this)
                .setTitle("添加")
                .setNavigationIcon(R.drawable.ic_back)
                .setNavigationOnClickListener(view -> onBackPressed());
    }

    @Override
    protected void initInjector() {

    }


    @OnClick({R.id.indicator, R.id.search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.indicator:
                mNameText = mName.getText().toString().trim();
                if (EMClient.getInstance().getCurrentUser().equals(mNameText)) {
                    new EaseAlertDialog(this, R.string.not_add_myself).show();
                    return;
                }

                if (DemoHelper.getInstance().getContactList().containsKey(mNameText)) {
                    new EaseAlertDialog(this, R.string.This_user_is_already_your_friend).show();
                    return;
                }

                progressDialog = new ProgressDialog(this);
                String stri = getResources().getString(R.string.Is_sending_a_request);
                progressDialog.setMessage(stri);
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();
                Observable.create((Observable.OnSubscribe<Boolean>) subscriber -> {
                    String s = getResources().getString(R.string.Add_a_friend);
                    try {
                        EMClient.getInstance().contactManager().addContact(mNameText, s);
                        subscriber.onNext(true);
                        subscriber.onCompleted();
                    } catch (HyphenateException e) {
                        subscriber.onError(e);
                        subscriber.onCompleted();
                    }
                }).compose(RxSchedulers.io_main())
                        .subscribe(aBoolean -> {
                            progressDialog.dismiss();
                            ToastUtil.showShort(R.string.send_successful);
                        }, throwable -> {
                            LogUtils.loge("throwable :"+throwable.getMessage());
                            progressDialog.dismiss();
                            ToastUtil.showShort(R.string.Request_add_buddy_failure);
                        });
                break;
            case R.id.search:
                String userName = mEditNote.getText().toString().trim();
                if (userName.length() > 0) {
                    mLlUser.setVisibility(View.VISIBLE);
                    mName.setText(userName);
                } else {
                    ToastUtil.showShort(R.string.Please_enter_a_username);
                    mLlUser.setVisibility(View.INVISIBLE);
                }
                break;
        }
    }


}
