package com.lyf.gobangccn.ui.message;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.R;
import com.hyphenate.easeui.utils.EaseCommonUtils;
import com.hyphenate.easeui.utils.EaseSmileUtils;
import com.hyphenate.easeui.utils.EaseUserUtils;
import com.hyphenate.util.DateUtils;
import com.jiang.common.base.CommonActivity;
import com.lyf.gobangccn.easemob.Constant;
import com.lyf.gobangccn.ui.chat.ChatActivity;
import com.lyf.gobangccn.view.recyclerview.BaseRecyclerAdapter;
import com.lyf.gobangccn.view.recyclerview.IViewHolder;
import com.lyf.gobangccn.view.recyclerview.PlaceHolderType;

import java.util.Date;
import java.util.List;

/**
 * Created by 01F on 2017/7/23.
 */

public class MessageAdapter extends BaseRecyclerAdapter<EMConversation> {

    public MessageAdapter(Context context, List<EMConversation> list) {
        super(context, list);
        status= PlaceHolderType.NODATA;
    }

    @Override
    public IViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        IViewHolder holder = super.onCreateViewHolder(parent, viewType);
        if(holder!=null){
            return holder;
        }
        View view = mLayoutInflater.inflate(R.layout.ease_row_chat_history, parent, false);
        return new MessageViewHolder(view);
    }

    public  class MessageViewHolder extends IViewHolder<EMConversation>{

        public MessageViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void setData(EMConversation data) {
            super.setData(data);
            getView(R.id.list_itease_layout)
                    .setOnClickListener(v -> {
                        String username = data.conversationId();
                        if (username.equals(EMClient.getInstance().getCurrentUser()))
                            ((CommonActivity)mContext).showShortToast(R.string.Cant_chat_with_yourself);
                        else {
                            // start chat acitivity
                            Intent intent = new Intent(mContext, ChatActivity.class);
                            // it's single chat
                            intent.putExtra(Constant.EXTRA_USER_ID, username);
                            mContext.startActivity(intent);
                        }
                    });
            EMMessage lastMessage = data.getLastMessage();
            String userName = data.conversationId();
            EaseUserUtils.setUserAvatar(mContext, userName, getView(R.id.avatar));
            getView(R.id.btn_delete).setOnClickListener(v ->  removeData(data));
            setText(R.id.name,userName)
                    .setVisible(R.id.unread_msg_number,data.getUnreadMsgCount()>0)
                    .setText(R.id.message, EaseSmileUtils.getSmiledText(mContext,
                            EaseCommonUtils.getMessageDigest(lastMessage, mContext)).toString())
                    .setText(R.id.time, DateUtils.getTimestampString(new Date(lastMessage.getMsgTime())))
                    .setVisible(R.id.msg_state,
                            lastMessage.direct() == EMMessage.Direct.SEND
                                    && lastMessage.status() == EMMessage.Status.FAIL);
            if(data.getUnreadMsgCount()>0){
                setText(R.id.unread_msg_number,String.valueOf(data.getUnreadMsgCount()));
            }
        }
    }
}
