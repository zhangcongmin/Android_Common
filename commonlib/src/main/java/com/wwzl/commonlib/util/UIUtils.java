package com.wwzl.commonlib.util;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.RequiresApi;


public class UIUtils {

    /**
     * 沉浸式方法
     *
     * @param activity
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static void setImmersionStatusBar(Activity activity) {
//        Window window = activity.getWindow();
//        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
////        window.clearFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
////        View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR 将状态栏文字颜色变为黑色
////        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
////                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE|View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
//        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
//        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//        window.setStatusBarColor(Color.TRANSPARENT);
        setImmersionStatusBar(activity,false);
    }

    /**
     * 沉浸式方法
     *
     * @param activity
     * @param needBlack 是否需要状态栏文字变黑色
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static void setImmersionStatusBar(Activity activity, boolean needBlack) {
        Window window = activity.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        window.clearFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//        View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR 将状态栏文字颜色变为黑色
        if (needBlack) {
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        } else {
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        }
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.TRANSPARENT);
    }



    /**
     * 防重复点击的工具类
     * if (!Utils.isFastDoubleClick()) {
     * doSomething
     * }
     */
    private static long lastClickTime;
    private static View lastClickView;

    public static boolean isFastDoubleClick(View clickView) {
        if (clickView != lastClickView) {
            lastClickView = clickView;
        } else {
            long time = System.currentTimeMillis();
            long timeD = time - lastClickTime;
            if (0 < timeD && timeD < 500) {
                return true;
            }
            lastClickTime = time;
            lastClickView = clickView;
        }
        return false;
    }

}
