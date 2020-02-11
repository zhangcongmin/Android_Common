package com.wwzl.commonlib.log;

import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.IntDef;


/**
 * logcat日志输出
 *
 * @author weibin.chen
 * @since 2019/2/11 11:19
 */
public class LogDebug {
    @IntDef({LogDebugLevel.VERBOSE, LogDebugLevel.DEBUG, LogDebugLevel.INFO,
            LogDebugLevel.WARN, LogDebugLevel.ERROR, LogDebugLevel.NONE})
    private @interface LogDebugLevel {
        int VERBOSE = 2;
        int DEBUG = 3;
        int INFO = 4;
        int WARN = 5;
        int ERROR = 6;
        int NONE = 7;
    }

    private static int level = LogDebugLevel.VERBOSE;

    public static void setLevel(int level) {
        LogDebug.level = level;
    }

    /**
     * 判断是否打印
     *
     * @param level See {@link LogDebugLevel}
     * @return true打印, false不打印
     */
    private static boolean checkToLog(@LogDebugLevel int level) {
        return level >= LogDebug.level;
    }

    public static void v(String tag, String msg) {
        log(LogDebugLevel.VERBOSE, tag, msg, null);
    }

    public static void v(String tag, String msg, Throwable tr) {
        log(LogDebugLevel.VERBOSE, tag, msg, tr);
    }

    public static void d(String tag, String msg) {
        log(LogDebugLevel.DEBUG, tag, msg, null);
    }

    public static void d(String tag, String msg, Throwable tr) {
        log(LogDebugLevel.DEBUG, tag, msg, tr);
    }

    public static void i(String tag, String msg) {
        log(LogDebugLevel.INFO, tag, msg, null);
    }

    public static void i(String tag, String msg, Throwable tr) {
        log(LogDebugLevel.INFO, tag, msg, tr);
    }

    public static void w(String tag, String msg) {
        log(LogDebugLevel.WARN, tag, msg, null);
    }

    public static void w(String tag, String msg, Throwable tr) {
        log(LogDebugLevel.WARN, tag, msg, tr);
    }

    public static void e(String tag, String msg) {
        log(LogDebugLevel.ERROR, tag, msg, null);
    }

    public static void e(String tag, String msg, Throwable tr) {
        log(LogDebugLevel.ERROR, tag, msg, tr);
    }

    private static void log(@LogDebugLevel int level, String tag, String msg, Throwable tr) {
        if (!checkToLog(level)) {
            return;
        }
        if (TextUtils.isEmpty(tag)) {
            return;
        }
        if (TextUtils.isEmpty(msg)) {
            return;
        }
        switch (level) {
            case LogDebugLevel.VERBOSE: {
                if (tr == null) {
                    Log.v(tag, msg);
                } else {
                    Log.v(tag, msg, tr);
                }
                break;
            }
            case LogDebugLevel.DEBUG: {
                if (tr == null) {
                    Log.d(tag, msg);
                } else {
                    Log.d(tag, msg, tr);
                }
                break;
            }
            case LogDebugLevel.INFO: {
                if (tr == null) {
                    Log.i(tag, msg);
                } else {
                    Log.i(tag, msg, tr);
                }
                break;
            }
            case LogDebugLevel.WARN: {
                if (tr == null) {
                    Log.w(tag, msg);
                } else {
                    Log.w(tag, msg, tr);
                }
                break;
            }
            case LogDebugLevel.ERROR: {
                if (tr == null) {
                    Log.e(tag, msg);
                } else {
                    Log.e(tag, msg, tr);
                }
                break;
            }
            case LogDebugLevel.NONE:
                default:
                break;
        }
    }
}
