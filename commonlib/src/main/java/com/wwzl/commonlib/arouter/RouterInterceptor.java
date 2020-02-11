package com.wwzl.commonlib.arouter;

import android.content.Context;
import android.util.Log;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Interceptor;
import com.alibaba.android.arouter.facade.callback.InterceptorCallback;
import com.alibaba.android.arouter.facade.template.IInterceptor;

/**
 * @Description 比较经典的应用就是在跳转过程中处理登陆事件，这样就不需要在目标页重复做登陆检查
 * 拦截器会在跳转之前执行，多个拦截器会按优先级顺序依次执行
 * priority就是优先级 可以设置多个级别的拦截器都活一次执行
 * 创建一个实现IInterceptor接口的类就是一个拦截器，不用做额外的配置了
 * @Author weibin.chen
 * @date 2019/8/6 11:40
 */
@Interceptor(priority = 1, name = "登录拦截")
public class RouterInterceptor implements IInterceptor {
    public static final int MUST_LOGIN = 666;
    private static final String TAG = RouterInterceptor.class.getName();

    @Override
    public void process(Postcard postcard, InterceptorCallback callback) {

        // todo 这里 处理登录逻辑  && !LoginUitl.isLogin()
        if(postcard.getExtra()==MUST_LOGIN  ){
            //觉得有问题，中断路由流程
            callback.onInterrupt(null);
        }
        else {
            // 处理完成，交还控制权
            callback.onContinue(postcard);
        }
    }

    @Override
    public void init(Context context) {
        // 拦截器的初始化，会在sdk初始化的时候调用该方法，仅会调用一次
        Log.d(TAG, "RouterInterceptor init");
    }
}
