package com.wwzl.commonlib.arouter;


import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.callback.NavigationCallback;
import com.wwzl.commonlib.activity.BaseRxActivity;
import com.wwzl.commonlib.log.LogDebug;

import java.lang.ref.WeakReference;

/**
* @Description  自定义导航拦截监听
* @Author weibin.chen
* @date 2019/8/6 11:44
*/
public class CustomNavigationCallback implements NavigationCallback {
    public static final String TAG = CustomNavigationCallback.class.getName();

    private WeakReference<BaseRxActivity> activityWeakReference;

    public CustomNavigationCallback(BaseRxActivity baseActivity) {
        activityWeakReference = new WeakReference<>(baseActivity);

    }

    @Override
    public void onFound(Postcard postcard) {
        LogDebug.i(TAG, "onFound");
    }

    @Override
    public void onLost(Postcard postcard) {
        LogDebug.i(TAG, "onLost 丢失");

    }

    @Override
    public void onArrival(Postcard postcard) {
        LogDebug.i(TAG, "到达 onArrival");

    }

    @Override
    public void onInterrupt(Postcard postcard) {
        LogDebug.i(TAG, "拦截 onInterrupt");
        if (activityWeakReference != null && activityWeakReference.get() != null) {
            if (!activityWeakReference.get().isFinishing()) {
                LogDebug.i(TAG, "被拦截 onInterrupt" +postcard.getPath());
            }
        }

    }
}
