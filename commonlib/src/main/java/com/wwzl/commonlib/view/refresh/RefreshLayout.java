package com.wwzl.commonlib.view.refresh;

import android.content.Context;
import android.util.AttributeSet;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;

/**
 * @author: weibin.chen
 * @time: 2020/1/7
 * @des : 下拉刷新  上拉加载更多
 */
public class RefreshLayout extends SmartRefreshLayout {
    public RefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public RefreshLayout(Context context) {
        super(context);
    }


}
