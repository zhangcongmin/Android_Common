package com.wwzl.commonlib.api.request_body;

import android.text.TextUtils;

import com.wwzl.commonlib.application.AppApplication;

import java.util.Locale;

/**
 * author : zcm
 * time   : 2020/01/09
 * desc   :
 * version: 1.0
 */
public abstract class BaseRequestBody<T> {
    public T data;
//    public String languageType = TextUtils.equals(AppApplication.getApp().getResources().getConfiguration().locale.getLanguage(), "zh") ? "cn" : "en";
    public String languageType = TextUtils.equals(Locale.getDefault().getLanguage(), "zh") ? "cn" : "en";
    public String routing = getRouting();

    public abstract String getRouting();
}
