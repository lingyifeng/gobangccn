package com.lyf.gobangccn.ui.message;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.hyphenate.chat.EMConversation;
import com.jiang.common.base.irecyclerview.IRecyclerView;
import com.jiang.common.base.irecyclerview.OnRefreshListener;
import com.jiang.common.widget.ToolBarBuilder;
import com.lyf.gobangccn.R;
import com.lyf.gobangccn.base.MVPBaseFragment;
import com.lyf.gobangccn.inject.components.DaggerMessageComponents;
import com.lyf.gobangccn.inject.modules.MessageModules;
import com.lyf.gobangccn.ui.main.MainActivity;
import com.lyf.gobangccn.view.recyclerview.RecyclerViewInit;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by 01F on 2017/7/2.
 */

public class MessageFrament extends MVPBaseFragment<MessagePresenter> {
    private final static int MSG_REFRESH = 2;
    protected boolean hidden;
    protected boolean isConflict;
    @BindView(R.id.irv_message)
    IRecyclerView mIrvMessage;
    List<EMConversation> messages = new ArrayList<>();
    private MessageAdapter mAdapter;
    protected Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_REFRESH:
                    if (mPresenter != null) {
                        mPresenter.getConversationList();
                    }
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public int getLayoutId() {
        return R.layout.frag_message;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        if (savedInstanceState != null && savedInstanceState.getBoolean("isConflict", false))
            return;
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    protected void init(View view) {
        new ToolBarBuilder(view)
                .setTitle(getString(R.string.message));
        mAdapter = new MessageAdapter(mContext, messages);
        RecyclerViewInit.init(mContext, mIrvMessage, mAdapter, new LinearLayoutManager(mContext));
        mIrvMessage.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                mIrvMessage.setRefreshing(false);
                refresh();
            }
        });

    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        this.hidden = hidden;
        if (!hidden && !isConflict) {
            refresh();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!hidden) {
            refresh();
        }
    }

    @Override
    protected void initInjector() {
        DaggerMessageComponents.builder()
                .messageModules(new MessageModules(this))
                .build().inject(this);
    }

    public void returnMessageList(List<EMConversation> list) {
        messages.clear();
        messages.addAll(list);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void refresh() {
        super.refresh();
        ((MainActivity) mContext).updateUnreadLabel();
        if (!handler.hasMessages(MSG_REFRESH)) {
            handler.sendEmptyMessage(MSG_REFRESH);
        }
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (isConflict) {
            outState.putBoolean("isConflict", true);
        }
    }

}
