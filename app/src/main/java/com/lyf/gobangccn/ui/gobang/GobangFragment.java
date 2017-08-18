package com.lyf.gobangccn.ui.gobang;

import android.view.View;

import com.jiang.common.widget.ToolBarBuilder;
import com.lyf.gobangccn.R;
import com.lyf.gobangccn.base.MVPBaseFragment;

/**
 * Created by 01F on 2017/7/2.
 */

public class GobangFragment extends MVPBaseFragment {
    @Override
    public int getLayoutId() {
        return R.layout.frag_gobang;
    }

    @Override
    protected void init(View view) {
        new ToolBarBuilder(view)
                .setTitle("下棋");

    }

    @Override
    protected void initInjector() {

    }
}
