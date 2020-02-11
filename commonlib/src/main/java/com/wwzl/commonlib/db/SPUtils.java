package com.wwzl.commonlib.db;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.wwzl.commonlib.application.AppApplication;
import com.wwzl.commonlib.gson.GsonHelper;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author weibin  chen
 * @since 2019/2/24 17:42
 */
public class SPUtils {
    /**
     * 基础数据存储
     */
    private static final String SP_NAME = "wwzl_wwhz";
    /**
     * 复杂数据存储 数组 等
     */
    private static final String SP_DATA_NAME = "wwzl_wwhz_data";

    private static SharedPreferences getSharedPreferencesToData() {
        return AppApplication.getApp().getSharedPreferences(SP_DATA_NAME, Context.MODE_PRIVATE);
    }
    private static SharedPreferences getSharedPreferences() {
        return AppApplication.getApp().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
    }

    public static void putString(String key, String result) {
        getSharedPreferences().edit().putString(key, result).apply();
    }

    public static void putString(@NonNull SharedPreferences sharedPreferences, String key, String result) {
        sharedPreferences.edit().putString(key, result).apply();
    }

    public static void putLong(String key, long result) {
        getSharedPreferences().edit().putLong(key, result).apply();
    }

    public static void putInt(String key, int result) {
        getSharedPreferences().edit().putInt(key, result).apply();
    }

    public static void putFloat(String key, int result) {
        getSharedPreferences().edit().putFloat(key, result).apply();
    }

    public static void putBoolean(String key, boolean b) {
        getSharedPreferences().edit().putBoolean(key, b).apply();
    }

    /**
     * 存储  数组
     *
     * @param key
     * @param set
     */
    public static void putStringSet(String key, Set<String> set) {
        getSharedPreferences().edit().putStringSet(key, set).apply();
    }

    /**
     * @describe 获取字符串
     * @author weibinchen
     */
    public static String getString(String key) {
        return getSharedPreferences().getString(key, "");
    }

    public static long getLong(String key) {
        return getSharedPreferences().getLong(key, 0L);
    }

    public static int getInt(String key) {
        return getSharedPreferences().getInt(key, 0);
    }

    public static float getFloat(String key) {
        return getSharedPreferences().getFloat(key, 0);
    }

    public static boolean getBoolean(String key) {
        return getSharedPreferences().getBoolean(key, false);
    }

    /**
     * 获取 数组
     * @param key
     * @return
     */
    public static Set<String> getStringSet(String key) {
        return getSharedPreferences().getStringSet(key, null);
    }

    public static <T> void putList(String key, List<T> result) {
        putList(getSharedPreferencesToData(), key, result);
    }

    public static <T> void putList(@NonNull SharedPreferences sharedPreferences, String key, List<T> result) {
        sharedPreferences.edit().putString(key, GsonHelper.getInstance().gson().toJson(result)).apply();
    }

    /**
     * 获取对象
     * @param key    SharedPreferences key
     * @param tClass class bean
     */
    public static <T> T getObject(@NonNull String key, @NonNull Class<T> tClass) {
        return getObject(getSharedPreferencesToData(), key, tClass);
    }

    public static <T> T getObject(@NonNull SharedPreferences sharedPreferences, @NonNull String key, @NonNull Class<T> tClass) {
        String obj = sharedPreferences.getString(key, "");
        return GsonHelper.getInstance().gson().fromJson(obj, tClass);
    }

    public static <T> void putObject(@NonNull String key, @NonNull T result) {
        putObject(getSharedPreferencesToData(), key, result);
    }

    public static <T> void putObject(@NonNull SharedPreferences sharedPreferences, @NonNull String key, @NonNull T result) {
        sharedPreferences.edit().putString(key, GsonHelper.getInstance().gson().toJson(result)).apply();
    }

    public static <T> List<T> getList(String key, Class<T> tClass) {
        return getList(getSharedPreferencesToData(), key, tClass);
    }

    public static <T> List<T> getList(@NonNull SharedPreferences sharedPreferences, String key, Class<T> tClass) {
        List<T> list = new ArrayList<>();
        String obj = sharedPreferences.getString(key, "");
        if (!TextUtils.isEmpty(obj)) {
            try {
                JsonArray array = new JsonParser().parse(obj).getAsJsonArray();
                for (final JsonElement elem : array) {
                    list.add(GsonHelper.getInstance().gson().fromJson(elem, tClass));
                }
            } catch (Exception e) {
            }
        }
        return list;
    }





}
