package com.wwzl.commonlib.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.wwzl.commonlib.R;
import com.wwzl.commonlib.databinding.DialogWaitBinding;
import com.wwzl.commonlib.log.LogDebug;
import com.wwzl.commonlib.util.ContextUtils;

import java.util.List;


public class WaitDialog extends BaseDialog<DialogWaitBinding> {
    private static final String TAG = WaitDialog.class.getSimpleName();
    private static boolean mCancelable = true;
    @Override
    public void onStart() {
        super.onStart();
        if(getDialog() != null){
            getDialog().setCancelable(mCancelable);
            getDialog().setOnKeyListener(new DialogInterface.OnKeyListener() {
                @Override
                public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        if(mCancelable){
                            dismiss();
                        }
                        return true;
                    }
                    return false;
                }
            });
        }
        setCanceledOnTouchOutside(false);
        showLocation(Gravity.CENTER, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }
    public static void showWaitDialog(@NonNull FragmentActivity activity, boolean cancelable) {
        if (!ContextUtils.isContextValid(activity)) {
            return;
        }
        WaitDialog dialogFragment = null;
        try {
            List<Fragment> fragments = activity.getSupportFragmentManager().getFragments();
            for (Fragment fragment : fragments) {
                if (fragment instanceof WaitDialog) {
                    dialogFragment = (WaitDialog) fragment;
                    LogDebug.e("cwb", " LoadingDialogFragment isShow return ");
                    break;
                }
            }
        } catch (Exception e) {
            LogDebug.e("cwb", e.toString());
        }
        if (dialogFragment == null) {
            dialogFragment = new WaitDialog();
        }
        mCancelable = cancelable;
        dialogFragment.show(activity.getSupportFragmentManager(), TAG);
        dialogFragment.setCancelable(cancelable);
    }

    public static void dismissWaitDialog(@NonNull FragmentActivity activity) {
        if (!ContextUtils.isContextValid(activity)) {
            return;
        }
        try {
            List<Fragment> fragments = activity.getSupportFragmentManager().getFragments();
            for (Fragment fragment : fragments) {
                if (fragment instanceof WaitDialog) {
                    ((WaitDialog) fragment).dismissAllowingStateLoss();
                }
            }
        } catch (Exception e) {
            LogDebug.e("cwb", e.toString());
        }
        LogDebug.e("cwb", " dismissLoadingDialogFragment isShow = " + isShow(activity));
    }

    public static boolean isShow(@NonNull FragmentActivity activity) {
        if (!ContextUtils.isContextValid(activity)) {
            return false;
        }
        return isShow(activity.getSupportFragmentManager().findFragmentByTag(TAG));
    }


    public void setText(String msg) {
        getBinding().tvWaitMessage.setText(msg);
    }


    @Override
    protected void init(Bundle savedInstanceState) {

    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.dialog_wait;
    }

    public static boolean isShow(Fragment fragment) {
        boolean isDialogFragment = fragment instanceof DialogFragment;
        if (!isDialogFragment) {
            return false;
        }
        Dialog dialog = ((DialogFragment) fragment).getDialog();
        if (dialog == null) {
            return false;
        }
        return dialog.isShowing();
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }

}
