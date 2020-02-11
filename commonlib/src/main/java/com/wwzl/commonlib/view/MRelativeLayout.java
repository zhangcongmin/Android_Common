package com.wwzl.commonlib.view;


import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.wwzl.commonlib.R;

public class MRelativeLayout extends RelativeLayout {

    public float mScale;

    public MRelativeLayout(Context context) {
        this(context, null);
    }

    public MRelativeLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }


    public MRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.ScaleLayout);
        mScale = typedArray.getFloat(R.styleable.ScaleLayout_scale, -1);
        typedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        if (mScale != -1) {
            super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec((int) (MeasureSpec.getSize(widthMeasureSpec) * mScale), MeasureSpec.getMode(widthMeasureSpec)));
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }
    /**
     * dp单位转换为px
     * @param context 上下文，需要通过上下文获取到当前屏幕的像素密度
     * @param dpValue dp值
     * @return px值
     */
    public static int dp2px(Context context, float dpValue){
        return (int)(dpValue * (context.getResources().getDisplayMetrics().density) + 0.5f);
    }
}
