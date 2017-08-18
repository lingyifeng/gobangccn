package com.lyf.gobangccn.ui.splash;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.chat.EMClient;
import com.lyf.gobangccn.R;
import com.lyf.gobangccn.easemob.db.InviteMessgeDao;
import com.lyf.gobangccn.easemob.domain.InviteMessage;
import com.lyf.gobangccn.easemob.domain.InviteMessage.InviteMesageStatus;
import com.lyf.gobangccn.view.recyclerview.BaseRecyclerAdapter;
import com.lyf.gobangccn.view.recyclerview.IViewHolder;
import com.lyf.gobangccn.view.recyclerview.PlaceHolderType;

import java.util.List;

/**
 * Created by 01F on 2017/8/11.
 */

public class NewFriendsMsgAdapter extends BaseRecyclerAdapter<InviteMessage> {
    private InviteMessgeDao messgeDao;
    public NewFriendsMsgAdapter(Context context, List<InviteMessage> list) {
        super(context, list);
        status = PlaceHolderType.NODATA;
        messgeDao = new InviteMessgeDao(context);
    }

    @Override
    public IViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        IViewHolder holder = super.onCreateViewHolder(parent, viewType);
        if (holder != null) {
            return holder;
        }
        View view = mLayoutInflater.inflate(R.layout.em_row_invite_msg, parent, false);
        return new NewFriendsMsgHolder(view);
    }

    public class NewFriendsMsgHolder extends IViewHolder<InviteMessage> {
        private ImageView mAvatar;
        private TextView mReason;
        private TextView mName;
        private Button mAgree;
        private Button mUserState;

        public NewFriendsMsgHolder(View itemView) {
            super(itemView);
            mAvatar=getView(R.id.avatar);
            mReason=getView(R.id.message);
            mName=getView(R.id.name);
            mAgree=getView(R.id.agree);
            mUserState=getView(R.id.user_state);
        }



        @Override
        public void setData(InviteMessage data) {
            super.setData(data);
            mAgree.setVisibility(View.INVISIBLE);
            mReason.setText(data.getReason());
            mName.setText(data.getFrom());
            if (data.getStatus() == InviteMesageStatus.BEAGREED) {
                mUserState.setVisibility(View.INVISIBLE);
                mReason.setText(context.getString(R.string.Has_agreed_to_your_friend_request));
            } else if (data.getStatus() == InviteMesageStatus.BEINVITEED || data.getStatus() == InviteMesageStatus.BEAPPLYED ||
                    data.getStatus() == InviteMesageStatus.GROUPINVITATION) {
                mAgree.setVisibility(View.VISIBLE);
                mAgree.setEnabled(true);
                mAgree.setBackgroundResource(android.R.drawable.btn_default);
                mAgree.setText(context.getString(R.string.agree));

                mUserState.setVisibility(View.VISIBLE);
                mUserState.setEnabled(true);
                mUserState.setBackgroundResource(android.R.drawable.btn_default);
                mUserState.setText(context.getString(R.string.refuse));
                if(data.getStatus() == InviteMesageStatus.BEINVITEED){
                    if (data.getReason() == null) {
                        // use default text
                        mReason.setText(context.getString(R.string.Request_to_add_you_as_a_friend));
                    }
                }


                // set click listener
                mAgree.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // accept invitation
                        acceptInvitation(mAgree, mUserState, data);
                    }
                });
            mUserState.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // decline invitation
                        refuseInvitation(mAgree, mUserState, data);
                    }
                });
            } else if (data.getStatus() == InviteMesageStatus.AGREED) {
                mUserState.setText(context.getString(R.string.Has_agreed_to));
                mUserState.setBackgroundDrawable(null);
                mUserState.setEnabled(false);
            } else if(data.getStatus() == InviteMesageStatus.REFUSED){
                mUserState.setText(context.getString(R.string.Has_refused_to));
                mUserState.setBackgroundDrawable(null);
                mUserState.setEnabled(false);
            }
        }
    }
    private void acceptInvitation(final Button buttonAgree, final Button buttonRefuse, final InviteMessage msg) {
        final ProgressDialog pd = new ProgressDialog(context);
        String str1 = context.getResources().getString(R.string.Are_agree_with);
        final String str2 = context.getResources().getString(R.string.Has_agreed_to);
        final String str3 = context.getResources().getString(R.string.Agree_with_failure);
        pd.setMessage(str1);
        pd.setCanceledOnTouchOutside(false);
        pd.show();

        new Thread(new Runnable() {
            public void run() {
                // call api
                try {
                    if (msg.getStatus() == InviteMesageStatus.BEINVITEED) {//accept be friends
                        EMClient.getInstance().contactManager().acceptInvitation(msg.getFrom());
                    } else if (msg.getStatus() == InviteMesageStatus.BEAPPLYED) { //accept application to join group
                        EMClient.getInstance().groupManager().acceptApplication(msg.getFrom(), msg.getGroupId());
                    } else if (msg.getStatus() == InviteMesageStatus.GROUPINVITATION) {
                        EMClient.getInstance().groupManager().acceptInvitation(msg.getGroupId(), msg.getGroupInviter());
                    }
                    msg.setStatus(InviteMesageStatus.AGREED);
                    // update database
                    ContentValues values = new ContentValues();
                    values.put(InviteMessgeDao.COLUMN_NAME_STATUS, msg.getStatus().ordinal());
                    messgeDao.updateMessage(msg.getId(), values);
                    ((Activity) context).runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            pd.dismiss();
                            buttonAgree.setText(str2);
                            buttonAgree.setBackgroundDrawable(null);
                            buttonAgree.setEnabled(false);

                            buttonRefuse.setVisibility(View.INVISIBLE);
                        }
                    });
                } catch (final Exception e) {
                    ((Activity) context).runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            pd.dismiss();
                            Toast.makeText(context, str3 + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });

                }
            }
        }).start();
    }


    private void refuseInvitation(final Button buttonAgree, final Button buttonRefuse, final InviteMessage msg) {
        final ProgressDialog pd = new ProgressDialog(context);
        String str1 = context.getResources().getString(R.string.Are_agree_with);
        final String str2 = context.getResources().getString(R.string.Has_refused_to);
        final String str3 = context.getResources().getString(R.string.Agree_with_failure);
        pd.setMessage(str1);
        pd.setCanceledOnTouchOutside(false);
        pd.show();

        new Thread(new Runnable() {
            public void run() {
                // call api
                try {
                    if (msg.getStatus() == InviteMesageStatus.BEINVITEED) {//decline the invitation
                        EMClient.getInstance().contactManager().declineInvitation(msg.getFrom());
                    } else if (msg.getStatus() == InviteMesageStatus.BEAPPLYED) { //decline application to join group
                        EMClient.getInstance().groupManager().declineApplication(msg.getFrom(), msg.getGroupId(), "");
                    } else if (msg.getStatus() == InviteMesageStatus.GROUPINVITATION) {
                        EMClient.getInstance().groupManager().declineInvitation(msg.getGroupId(), msg.getGroupInviter(), "");
                    }
                    msg.setStatus(InviteMesageStatus.REFUSED);
                    // update database
                    ContentValues values = new ContentValues();
                    values.put(InviteMessgeDao.COLUMN_NAME_STATUS, msg.getStatus().ordinal());
                    messgeDao.updateMessage(msg.getId(), values);
                    ((Activity) context).runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            pd.dismiss();
                            buttonRefuse.setText(str2);
                            buttonRefuse.setBackgroundDrawable(null);
                            buttonRefuse.setEnabled(false);

                            buttonAgree.setVisibility(View.INVISIBLE);
                        }
                    });
                } catch (final Exception e) {
                    ((Activity) context).runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            pd.dismiss();
                            Toast.makeText(context, str3 + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }
        }).start();
    }
}
