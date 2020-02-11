package com.wwzl.commonlib.adapter;

import android.content.Context;

import androidx.recyclerview.widget.DividerItemDecoration;

import com.wwzl.commonlib.R;

/**
 * @Description
 * @Author weibin.chen
 * @date 2019/8/30 14:18
 */
public class LineItemDecoration extends DividerItemDecoration {
    public LineItemDecoration(Context context) {
        super(context, DividerItemDecoration.VERTICAL);
       setDrawable(context.getResources().getDrawable(R.drawable.divider_line));
    }
    public LineItemDecoration(Context context, int orientation) {
        super(context,orientation);
        setOrientation(orientation);
        setDrawable(context.getResources().getDrawable(R.drawable.divider_line));
    }
}
