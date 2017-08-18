package com.lyf.gobangccn.ui.message;

import android.util.Pair;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.lyf.gobangccn.base.BasePresenter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

/**
 * Created by 01F on 2017/7/22.
 */

public class MessagePresenter extends BasePresenter{

    private MessageFrament mView;
    protected List<EMConversation> conversationList = new ArrayList<>();
    @Inject
    public MessagePresenter(MessageFrament view){
        this.mView=view;
    }
    public void getConversationList() {

        conversationList.clear();
        List<Pair<Long, EMConversation>> sortList = loadConversationList();

        try {
            // Internal is TimSort algorithm, has bug
            sortConversationByLastChatTime(sortList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<EMConversation> list = new ArrayList<EMConversation>();
        for (Pair<Long, EMConversation> sortItem : sortList) {
            list.add(sortItem.second);
        }
        conversationList.addAll(list);
        mView.returnMessageList(list);
    }

    public List<Pair<Long, EMConversation>> loadConversationList() {
        // get all conversations
        Map<String, EMConversation> conversations = EMClient.getInstance().chatManager().getAllConversations();
        List<Pair<Long, EMConversation>> sortList = new ArrayList<Pair<Long, EMConversation>>();
        /**
         * lastMsgTime will change if there is new message during sorting
         * so use synchronized to make sure timestamp of last message won't change.
         */
        synchronized (conversations) {
            for (EMConversation conversation : conversations.values()) {
                if (conversation.getAllMessages().size() != 0) {
                    sortList.add(new Pair<Long, EMConversation>(conversation.getLastMessage().getMsgTime(), conversation));
                }
            }
        }
        return sortList;
    }

    private void sortConversationByLastChatTime(List<Pair<Long, EMConversation>> conversationList) {
        Collections.sort(conversationList, new Comparator<Pair<Long, EMConversation>>() {
            @Override
            public int compare(final Pair<Long, EMConversation> con1, final Pair<Long, EMConversation> con2) {

                if (con1.first.equals(con2.first)) {
                    return 0;
                } else if (con2.first.longValue() > con1.first.longValue()) {
                    return 1;
                } else {
                    return -1;
                }
            }

        });
    }
}
