package com.lyf.gobangccn.ui.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.jiang.common.widget.BottomTabView;
import com.lyf.gobangccn.R;
import com.lyf.gobangccn.base.MVPBaseActivity;
import com.lyf.gobangccn.base.MVPBaseFragment;
import com.lyf.gobangccn.ui.contacts.ContactsFragment;
import com.lyf.gobangccn.ui.gobang.GobangFragment;
import com.lyf.gobangccn.ui.message.MessageFrament;

import java.util.ArrayList;

import butterknife.BindString;
import butterknife.BindView;

public class MainActivity extends MVPBaseActivity {

    @BindString(R.string.gobang)
    String goBang;
    @BindString(R.string.message)
    String message;
    @BindString(R.string.contacts)
    String contacts;
    @BindView(R.id.viewPager)
    ViewPager mViewPager;
    @BindView(R.id.tab_main)
    BottomTabView mTabMain;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void init() {
        ArrayList<BottomTabView.TabItemView> tabItemViews = new ArrayList<>();
        BottomTabView.TabItemView goBangTabView = new BottomTabView.TabItemView(this, goBang, R.color.unchoose, R.color.dot_color, R.mipmap.doc,
                R.mipmap.doc_blue);
        BottomTabView.TabItemView messageTabView = new BottomTabView.TabItemView(this, message, R.color.unchoose, R.color.dot_color, R.mipmap.massage,
                R.mipmap.massage_blue);
        BottomTabView.TabItemView contactTabView = new BottomTabView.TabItemView(this, contacts, R.color.unchoose, R.color.dot_color, R.mipmap.contacts,
                R.mipmap.contactsblue);
        tabItemViews.add(goBangTabView);
        tabItemViews.add(messageTabView);
        tabItemViews.add(contactTabView);
        mTabMain.setTabItemViews(tabItemViews);
        mTabMain.setUpWithViewPager(mViewPager);
        ArrayList<MVPBaseFragment> fragments = new ArrayList<>();
        fragments.add(new GobangFragment());
        fragments.add(new MessageFrament());
        fragments.add(new ContactsFragment());
        FragmentPagerAdapter adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public int getCount() {
                return fragments.size();
            }

            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }
        };
        mViewPager.setAdapter(adapter);
    }

    @Override
    protected void initInjector() {

    }


}
