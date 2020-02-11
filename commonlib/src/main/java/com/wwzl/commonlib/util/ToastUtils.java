package com.wwzl.commonlib.util;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.StringRes;

import com.wwzl.commonlib.R;

/**
 * @author sam
 * @version 1.0
 * @date 2/1/2018
 */
public final class ToastUtils {

    private static final int COLOR_DEFAULT = 0xFEFFFFFF;
    private static final Handler HANDLER = new Handler(Looper.getMainLooper());

    private static Toast sToast;
    private static int sGravity = -1;
    private static int sXOffset = -1;
    private static int sYOffset = -1;
    private static int sBgColor = COLOR_DEFAULT;
    private static int sBgResource = -1;
    private static int sMsgColor = COLOR_DEFAULT;
    private static int sMsgTextSize = -1;
    private static int sToastGravity = Gravity.BOTTOM;




    private static Toast mToast;
    private static Handler handler = new Handler();

    /**
     * Show the sToast for a short period of time.
     *
     * @param resId The resource id for text.
     */
    public static void showShort(Context context,@StringRes final int resId) {
        showShort(context,context.getResources().getText(resId).toString());
    }

    /**
     * Show the sToast for a short period of time.
     *
     * @param resId The resource id for text.
     * @param args  The args.
     */
    public static void showShort(Context context,@StringRes final int resId, final Object... args) {
        if (args != null && args.length == 0) {
            showShort(context,context.getString(resId));
        } else {
            showShort(context,context.getString(resId,args));
        }
    }
    
    public static void showShort(Context context,String message) {
        if (DeviceUtils.isBackground(context)) {
            return;
        }
        if (mToast != null) {
            mToast.cancel();
        }
        mToast = new Toast(context);
        TextView view = new TextView(context);
        view.setBackgroundResource(R.drawable.toast_round_black_bg);
        view.setTextColor(context.getResources().getColor(android.R.color.white));
        view.setTextSize(12);
        mToast.setView(view);
        handler.removeCallbacksAndMessages(null);
        ((TextView) mToast.getView()).setText(message);
//        mToast.setDuration(Toast.LENGTH_SHORT);
//        mToast.setGravity(sToastGravity, 0, 0);
        mToast.show();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mToast.cancel();
                mToast = null;
            }
        }, 2000);
    }

    
}
