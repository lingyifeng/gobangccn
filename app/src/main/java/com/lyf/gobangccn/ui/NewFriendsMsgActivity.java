package com.lyf.gobangccn.ui;

import com.jiang.common.base.irecyclerview.IRecyclerView;
import com.jiang.common.widget.ToolBarBuilder;
import com.lyf.gobangccn.R;
import com.lyf.gobangccn.base.MVPBaseActivity;
import com.lyf.gobangccn.easemob.db.InviteMessgeDao;
import com.lyf.gobangccn.easemob.domain.InviteMessage;
import com.lyf.gobangccn.ui.splash.NewFriendsMsgAdapter;
import com.lyf.gobangccn.view.recyclerview.RecyclerViewInit;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class NewFriendsMsgActivity extends MVPBaseActivity {

    @BindView(R.id.irv_newFriend)
    IRecyclerView irvNewFriend;
    private List<InviteMessage> mInvites = new ArrayList<>();
    private NewFriendsMsgAdapter mAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_new_friends_msg;
    }

    @Override
    protected void init() {
        new ToolBarBuilder(this)
                .setTitle("新好友")
                .setNavigationIcon(R.drawable.ic_back)
                .setNavigationOnClickListener(v -> onBackPressed());
        InviteMessgeDao dao = new InviteMessgeDao(this);
        mInvites.addAll(dao.getMessagesList());
        mAdapter = new NewFriendsMsgAdapter(mContext, mInvites);
        RecyclerViewInit.init(mContext, irvNewFriend, mAdapter);
        dao.saveUnreadMessageCount(0);
    }

    @Override
    protected void initInjector() {

    }
}
