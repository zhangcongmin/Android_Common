package com.wwzl.commonlib.bean;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * author : zcm
 * time   : 2019/12/26
 * desc   :
 * version: 1.0
 */
public class BaseResponse<T> {
    public String code;
    public String msg;
    public T data;

    public boolean isSuccessful(){
        return TextUtils.equals(code,"ok");
    }

    public String getMsg(){
        return msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }



    @SuppressWarnings("unchecked")
    public void castDataToObject() {
        Type type = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        Gson gson = new GsonBuilder().
                registerTypeAdapter(Double.class, new JsonSerializer<Double>() {

                    @Override
                    public JsonElement serialize(Double src, Type typeOfSrc, JsonSerializationContext context) {
                        if (src == src.longValue())
                            return new JsonPrimitive(src.longValue());
                        return new JsonPrimitive(src);
                    }
                }).create();
//        result = gson.fromJson(gson.toJson(data), type);
//        if (((Class<?>) type).getName().startsWith(TaliansheApplication.getInstance().getPackageName())||type != String.class) {
//        } else {
//            //说明要解析成自定义的javabean
//            result = (T) data ;
//        }
    }
}
