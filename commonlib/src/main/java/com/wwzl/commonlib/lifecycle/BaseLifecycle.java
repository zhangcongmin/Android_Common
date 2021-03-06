package com.wwzl.commonlib.lifecycle;

import androidx.annotation.Keep;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;

/**
 * 基于arch.lifecycle机制的接口定义, 实现生命周期的注解
 *
 * @author hanwei.chen
 * @since 2019/2/12 14:48
 */
@Keep
public interface BaseLifecycle extends LifecycleObserver {

    /**
     * 在LifecycleOwner的onCreate之后触发
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate();

    /**
     * 在LifecycleOwner的onStart之后触发
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    void onStart();

    /**
     * 在LifecycleOwner的onResume之后触发
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    void onResume();

    /**
     * 在LifecycleOwner的onPause之前触发
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    void onPause();

    /**
     * 在LifecycleOwner的onStop之前触发
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    void onStop();

    /**
     * 在LifecycleOwner的onDestroy之前触发
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    void onDestroy();

    /**
     * 任意生命周期
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
    void onAny(LifecycleOwner owner, Lifecycle.Event event);
}