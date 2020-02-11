package com.wwzl.commonlib.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.Nullable;

/**
 * 上下文的一些处理工具
 *
 * @author hanwei.chen
 * @since 2019/1/16 14:21
 */
public class ContextUtils {
    public static boolean isContextValid(Context context) {
        if (context == null) {
            return false;
        }
        if (context instanceof Activity) {
            Activity activity = (Activity) context;
            return !activity.isFinishing() && !activity.isDestroyed();
        }
        return true;
    }

    public static boolean startService(@Nullable Context context, @Nullable Intent service) {
        if (!isContextValid(context) || service == null) {
            return false;
        }
        try {
            context.startService(service);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
