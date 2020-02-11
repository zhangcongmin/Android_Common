package com.wwzl.commonlib.arouter;

import android.content.Context;
import android.text.TextUtils;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Interceptor;
import com.alibaba.android.arouter.facade.callback.InterceptorCallback;
import com.alibaba.android.arouter.facade.template.IInterceptor;
import com.wwzl.commonlib.util.LogUtils;

/**
 * author : zcm
 * time   : 2020/01/16
 * desc   :
 * version: 1.0
 */
@Interceptor(priority = 2, name = "页面拦截")
public class ArouterInterceptor implements IInterceptor {
    private static long startTime;
    private static String routerPath;

    @Override
    public void process(Postcard postcard, InterceptorCallback callback) {
        long curTime = System.currentTimeMillis();
        if (TextUtils.isEmpty(routerPath)) {
            callback.onContinue(postcard);
        } else {
            if (TextUtils.equals(routerPath, postcard.getPath())) {
                if (curTime - startTime < 300) {
                    LogUtils.e(postcard.getPath() + "时间太快，拦截");
                    callback.onInterrupt(new Throwable("跳转过快，拦截了"));
                    return;
                } else {
                    callback.onContinue(postcard);
                }
            } else {
                callback.onContinue(postcard);
            }
        }
        routerPath = postcard.getPath();
        startTime = System.currentTimeMillis();
    }

    @Override
    public void init(Context context) {
        LogUtils.e("arouter拦截器已初始化");
    }
}
