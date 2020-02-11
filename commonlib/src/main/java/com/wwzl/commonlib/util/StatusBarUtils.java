package com.wwzl.commonlib.util;

import android.app.Activity;
import com.gyf.immersionbar.ImmersionBar;
import com.wwzl.commonlib.R;
import com.wwzl.commonlib.dialog.BaseDialog;

/**
 * 状态栏工具类
 *
 * @author hanwei.chen
 * @since 2019/1/16 13:53
 */
public class StatusBarUtils {

    public static void setStatusBarDarkFont(Activity activity, boolean isDarkFont) {
        ImmersionBar.with(activity).statusBarDarkFont(isDarkFont).init();
    }
    public static void setWhiteStatusBar(Activity activity, boolean isDarkFont) {
        ImmersionBar.with(activity).statusBarDarkFont(isDarkFont).keyboardEnable(true).barColor(R.color.white).init();
    }

    /**
     * 沉浸状态栏
     * @param activity
     */
    public static void setImmersionBar(Activity activity) {
        ImmersionBar.with(activity).init();

    }
    public static void setImmersionBar(BaseDialog dialog) {
        ImmersionBar.with(dialog).init();
    }
}
