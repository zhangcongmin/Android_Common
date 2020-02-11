/**
 * Copyright (c) 2012-2013, Michael Yang 杨福海 (www.yangfuhai.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed toGeRen in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.wwzl.commonlib.util;

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

import static android.content.Context.MODE_PRIVATE;

public class SpUtils {
    public static final String commonCacheName = "spFile";
    public static final String SP_DATA_NAME = "spDataFile";
    public static final String LANGUAGE = "language";

    public static String getAsString(Context ctx, String cacheName, String key,String defaultValue) {
        SharedPreferences sp = ctx.getSharedPreferences(cacheName, MODE_PRIVATE);
        String value = sp.getString(key, defaultValue);
        if (value.contains("@#@")) {
            String[] split = value.split("@#@");
            long time = Long.parseLong(split[1]);
            long l = System.currentTimeMillis();
            if ((time + Long.parseLong(split[2])) < l) {
                clear(ctx, cacheName);
                return defaultValue;
            }
            return split[0];
        }else if(TextUtils.isEmpty(value)){
            return defaultValue;
        }
        return value;
    }

    public static void putAsString(Context ctx, String cacheName, String key, String value) {
        SharedPreferences sp = ctx.getSharedPreferences(cacheName, MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString(key, value);
        edit.commit();
    }

    public static void clear(Context ctx, String cacheName) {
        SharedPreferences sp = ctx.getSharedPreferences(cacheName, MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.clear();
        edit.commit();
    }


    private static SharedPreferences getSharedPreferencesToData() {
        return AppApplication.getApp().getSharedPreferences(SP_DATA_NAME, Context.MODE_PRIVATE);
    }
    private static SharedPreferences getSharedPreferences() {
        return AppApplication.getApp().getSharedPreferences(commonCacheName, Context.MODE_PRIVATE);
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
