package com.wwzl.commonlib.api;

import android.text.TextUtils;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * author : zcm
 * time   : 2020/01/09
 * desc   :
 * version: 1.0
 */
public class RequestBodyUtil {
    public static Map<String,Object> getBaseBody(String routing){
        return getBaseBody(routing,null);
    }
    public static Map<String,Object> getBaseBody(String routing,Object data){
        Map<String,Object> requestBody = new HashMap<>();
        requestBody.put("languageType", TextUtils.equals("zh",Locale.getDefault().getLanguage())?"cn":"en");
        requestBody.put("routing",routing);
        if(data != null){
            requestBody.put("data",data);
        }else{
            requestBody.put("data",new Object());
        }
        return requestBody;
    }
}
