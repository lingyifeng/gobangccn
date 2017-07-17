package com.lyf.gobangccn.view.recyclerview;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.jiang.common.base.irecyclerview.IRecyclerView;


/**
 * Created by DB on 2016/9/3.
 */

public class RecyclerViewInit {

    // TODO: 2017/3/11 改写成builder形式
    //加载视图统一初始化设置
    public static void init(Context context, IRecyclerView recyclerView, RecyclerView.Adapter recycleAdapter
            , LinearLayoutManager layoutManager) {
        init(context, recyclerView, recycleAdapter, layoutManager, OrientationHelper.VERTICAL);
    }

    //加载视图统一初始化设置
    public static void init(Context context, IRecyclerView recyclerView, RecyclerView.Adapter recycleAdapter) {
        init(context, recyclerView, recycleAdapter, new LinearLayoutManager(context), OrientationHelper.VERTICAL);
    }

    //加载视图统一初始化设置
    public static void init(Context context, IRecyclerView recyclerView, RecyclerView.Adapter recycleAdapter,
                            LinearLayoutManager layoutManager, int orientation) {

        //设置布局管理器
        recyclerView.setLayoutManager(layoutManager);
        //设置Adapter
        recyclerView.setIAdapter(recycleAdapter);
        recyclerView.setHasFixedSize(true);
        //设置为垂直布局，这也是默认的
        layoutManager.setOrientation(orientation);

        //设置分隔线  无效？
        if (!(layoutManager instanceof GridLayoutManager)) {
            recyclerView.addItemDecoration(new DecorationHelper(context
                    , orientation == OrientationHelper.HORIZONTAL ? DecorationHelper.VERTICAL_LIST :
                    DecorationHelper.HORIZONTAL_LIST));
        }

        //设置增加或删除条目的动画
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    //加载视图统一初始化设置
    public static void init(Context context, IRecyclerView recyclerView, RecyclerView.Adapter recycleAdapter
            , StaggeredGridLayoutManager gridLayoutManager) {
        //设置布局管理器
        recyclerView.setLayoutManager(gridLayoutManager);
        //设置Adapter
        recyclerView.setIAdapter(recycleAdapter);
        recyclerView.setHasFixedSize(true);

        //设置分隔线
        //        recyclerView.addItemDecoration(new DividerGridItemDecoration(this));
        //设置增加或删除条目的动画
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }
}
