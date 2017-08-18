package com.lyf.gobangccn.ui.contacts;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.lyf.gobangccn.R;
import com.lyf.gobangccn.view.recyclerview.BaseRecyclerAdapter;
import com.lyf.gobangccn.view.recyclerview.IViewHolder;
import com.lyf.gobangccn.view.recyclerview.PlaceHolderType;

import java.util.List;

/**
 * Created by 01F on 2017/7/30.
 */

public class ContactsAdapter extends BaseRecyclerAdapter<String> {

    public ContactsAdapter(Context context, List<String> list) {
        super(context, list);
        status= PlaceHolderType.NODATA;
    }

    @Override
    public IViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        IViewHolder holder = super.onCreateViewHolder(parent, viewType);
        if(holder!=null){
            return holder;
        }
        View view = mLayoutInflater.inflate(R.layout.em_contact_item, parent, false);
        view.setOnClickListener(this);
        return new ContactsHolder(view);
    }



    public class ContactsHolder extends IViewHolder<String>{
        public ContactsHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void setData(String data) {
            super.setData(data);
            setAvatar(R.id.avatar,null,null,mContext)
                    .setText(R.id.name,data);

        }
    }
}
