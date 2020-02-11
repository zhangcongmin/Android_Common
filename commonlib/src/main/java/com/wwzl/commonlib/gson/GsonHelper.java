package com.wwzl.commonlib.gson;


import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

/**
 * @author weibin.chen
 * @since 2019/2/1 15:31
 */
public class GsonHelper {
    private Gson mGson;

    private static final class GsonHelperHolder {
        private static final GsonHelper S_INSTANCE = new GsonHelper();
    }

    private GsonHelper() {
        mGson = new GsonBuilder().create();
    }

    public static GsonHelper getInstance() {
        return GsonHelperHolder.S_INSTANCE;
    }

    public Gson gson() {
        return mGson;
    }

    public  <T> List<T> getList(String obj, Class<T> tClass) {
        List<T> list = new ArrayList<>();
        if (!TextUtils.isEmpty(obj)) {
            try {
                JsonArray array = new JsonParser().parse(obj).getAsJsonArray();
                for (final JsonElement elem : array) {
                    list.add(gson().fromJson(elem, tClass));
                }
            } catch (Exception e) {
            }
        }
        return list;
    }


    public  <T> List<T> getList(List<JsonObject> jsonObjectList, Class<T> tClass) {
        List<T> list = new ArrayList<>();
        if (jsonObjectList!=null && jsonObjectList.size()>0) {
            try {
                for (final JsonObject elem : jsonObjectList) {
                    list.add(gson().fromJson(elem, tClass));
                }
            } catch (Exception e) {
            }
        }
        return list;
    }

}
