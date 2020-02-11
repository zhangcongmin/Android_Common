package com.wwzl.commonlib.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.wwzl.commonlib.R;


public class CommonDialog extends Dialog {

    private Context mContext;
    private TextView tvCancel;
    private View divider;

    public CommonDialog(Context context, OnConfirmClickListener onConfirmClickListener) {
        this(context, onConfirmClickListener,-1,-1,-1);


    }
    private int resContentId = -1;
    private int resConfirmId = -1;
    private int resCancelId = -1;
    public CommonDialog(Context context, OnConfirmClickListener onConfirmClickListener, int resContentId, int resConfirmId, int resCancelId) {
        super(context, R.style.AlertDialogStyle);
        this.resContentId = resContentId;
        this.resConfirmId = resConfirmId;
        this.resCancelId = resCancelId;
        this.mContext = context;
        setContentView(initView(onConfirmClickListener));
        Window window = getWindow();
        window.setGravity(Gravity.CENTER);
        WindowManager.LayoutParams lp = window.getAttributes();
//        int screenHeight = getScreenHeight(context);
//        int statusBarHeight = getStatusBarHeight(getContext());
//        int dialogHeight = screenHeight - statusBarHeight;
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, displayMetrics.heightPixels);

        setCanceledOnTouchOutside(true);
        setCancelable(true);
    }

    private static int getScreenHeight(Context context) {
        DisplayMetrics displaymetrics = context.getResources().getDisplayMetrics();
//        context.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        return displaymetrics.heightPixels;
    }

    private static int getStatusBarHeight(Context context) {
        int statusBarHeight = 0;
        Resources res = context.getResources();
        int resourceId = res.getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = res.getDimensionPixelSize(resourceId);
        }
        return statusBarHeight;
    }

    public void hideCancelButton(){
        if(tvCancel != null){
            tvCancel.setVisibility(View.GONE);
        }
        if(divider != null){
            divider.setVisibility(View.GONE);
        }
    }
    public void setCancelText(String text){
        if(tvCancel != null && !TextUtils.isEmpty(text)){
            tvCancel.setText(text);
        }
    }

    private View initView(OnConfirmClickListener onConfirmClickListener) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.common_dialog, null);
        TextView tvContent = view.findViewById(R.id.tvContent);
        if(resContentId > 0){
            tvContent.setText(resContentId);
        }
        tvCancel = view.findViewById(R.id.tvCancel);
        divider = view.findViewById(R.id.divider);
        if(resCancelId > 0){
            tvCancel.setText(resCancelId);
        }
        tvCancel.setOnClickListener(view12 -> {
            if (onConfirmClickListener != null) {
                onConfirmClickListener.onCancelClick();
                dismiss();
            }
        });
        TextView tvConfirm = view.findViewById(R.id.tvConfirm);
        if(resConfirmId > 0){
            tvConfirm.setText(resConfirmId);
        }
        tvConfirm.setOnClickListener(view12 -> {
            if (onConfirmClickListener != null) {
                onConfirmClickListener.onConfirmClick();
                dismiss();
            }
        });
        return view;
    }

    public interface OnConfirmClickListener {
        void onConfirmClick();
        default void onCancelClick(){}
    }
}
