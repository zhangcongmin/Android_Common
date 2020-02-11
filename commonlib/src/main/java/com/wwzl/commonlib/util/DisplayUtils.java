package com.wwzl.commonlib.util;

import android.content.Context;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;

import com.wwzl.commonlib.application.AppApplication;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 分辨率 相关工具类
 * Create By hanwei.chen on 2019/1/16 11:17
 */
public class DisplayUtils {
    /**
     * 密度转换像素
     *
     * @param pDipValue dp值
     * @return 像素
     */
    public static float dip2fpx(Context context, float pDipValue) {
        DisplayMetrics dm = context.getApplicationContext().getResources().getDisplayMetrics();
        return pDipValue * dm.density;
    }

    /**
     * 密度转换像素
     *
     * @param pDipValue dp值
     * @return 像素
     */
    public static float dip2fpx(float pDipValue) {
        return dip2fpx(AppApplication.getApp(), pDipValue);
    }

    /**
     * 密度转换像素
     *
     * @param dipValue dp值
     * @return 像素
     */
    public static int dp2px(Context context, float dipValue) {
        DisplayMetrics dm = context.getApplicationContext().getResources().getDisplayMetrics();
        return (int) (dipValue * dm.density + 0.5f);
    }

    /**
     * 密度转换像素
     *
     * @param dipValue dp值
     * @return 像素
     */
    public static int dp2px(float dipValue) {
        return dp2px(AppApplication.getApp(), dipValue);
    }

    /**
     * 获取屏幕密度值
     *
     * @return 屏幕密度值
     */
    public static float getDensityValue() {
        return getDensityValue(AppApplication.getApp());
    }

    /**
     * 获取屏幕密度值
     *
     * @return 屏幕密度值
     */
    public static float getDensityValue(Context context) {
        DisplayMetrics dm = context.getApplicationContext().getResources().getDisplayMetrics();
        return dm.density;
    }

    /**
     * 获取手机品牌
     *
     * @return 手机品牌
     * @see Build#BRAND
     */
    public static String getDeviceBrand() {
        return Build.BRAND;
    }

    /**
     * 获取手机型号
     *
     * @return 手机型号
     * @see Build#MODEL
     */
    public static String getDeviceMode() {
        return Build.MODEL;
    }

    /**
     * 获取手机版本号
     *
     * @return 手机版本号
     * @see android.os.Build.VERSION#RELEASE
     */
    public static String getDeviceVersionoRelease() {
        return Build.VERSION.RELEASE;
    }

    /**
     * 获取屏幕密度值
     *
     * @return 屏幕密度值
     * @see #getDensityValue(Context)
     */
    public static float getDmDensity(Context context) {
        return getDensityValue(context);
    }

    /**
     * 获取当前屏幕的显示密度（dpi）
     *
     * @retrun 当前屏幕的显示密度
     */
    public static float getDmDensityDpi() {
        return getDmDensityDpi(AppApplication.getApp());
    }

    /**
     * 当前屏幕的显示密度
     *
     * @retrun 当前屏幕的显示密度
     */
    public static float getDmDensityDpi(Context context) {
        DisplayMetrics dm = context.getApplicationContext().getResources().getDisplayMetrics();
        return dm.densityDpi;
    }

    /**
     * 获取屏幕高度
     *
     * @return 屏幕高度
     */
    public static int getScreenHeight() {
        return getScreenHeight(AppApplication.getApp());
    }

    /**
     * 获取屏幕高度
     *
     * @return 屏幕高度
     */
    public static int getScreenHeight(Context context) {
        DisplayMetrics dm = context.getApplicationContext().getResources().getDisplayMetrics();
        if (dm.widthPixels > dm.heightPixels) {
            return dm.widthPixels;
        } else {
            return dm.heightPixels;
        }
    }

    /**
     * 获取屏幕宽度
     *
     * @return 屏幕宽度
     */
    public static int getScreenWidth() {
        return getScreenWidth(AppApplication.getApp());
    }

    /**
     * 获取屏幕宽度
     *
     * @return 屏幕宽度
     */
    public static int getScreenWidth(Context context) {
        DisplayMetrics dm = context.getApplicationContext().getResources().getDisplayMetrics();
        if (dm.widthPixels > dm.heightPixels) {
            return dm.heightPixels;
        } else {
            return dm.widthPixels;
        }
    }

    /**
     * 获取系统状态栏高度
     *
     * @param context 上下文
     * @return 状态栏高度
     */
    public static int getStatusHeight(Context context) {
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int sbar = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            int x = Integer.parseInt(field.get(obj).toString());
            sbar = context.getResources().getDimensionPixelSize(x);
        } catch (Exception e) {
        }
        return sbar;
    }

    /**
     * 像素值转换为dp值
     *
     * @param context 上下文
     * @param pxValue 像素值
     * @return dp值
     */
    public static int px2dip(Context context, float pxValue) {
        DisplayMetrics dm = context.getApplicationContext().getResources().getDisplayMetrics();
        return (int) (pxValue / dm.density + 0.5f);
    }

    /**
     * 像素值转换为dp值
     *
     * @param pxValue 像素值
     * @return dp值
     */
    public static int px2dip(float pxValue) {
        return px2dip(AppApplication.getApp(), pxValue);
    }

    /*刘海屏全屏显示FLAG*/
    public static final int FLAG_NOTCH_SUPPORT = 0x00010000;

    /**
     * 设置应用窗口在华为刘海屏手机使用刘海区
     *
     * @param window 应用页面window对象
     */
    public static void setFullScreenWindowLayoutInDisplayCutout(Window window) {
        if (window == null) {
            return;
        }
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        try {
            Class layoutParamsExCls = Class.forName("com.huawei.android.view.LayoutParamsEx");
            Constructor con = layoutParamsExCls.getConstructor(WindowManager.LayoutParams.class);
            Object layoutParamsExObj = con.newInstance(layoutParams);
            Method method = layoutParamsExCls.getMethod("addHwFlags", int.class);
            method.invoke(layoutParamsExObj, FLAG_NOTCH_SUPPORT);
        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InstantiationException
                | InvocationTargetException e) {
        } catch (Exception e) {
        }
    }
}
