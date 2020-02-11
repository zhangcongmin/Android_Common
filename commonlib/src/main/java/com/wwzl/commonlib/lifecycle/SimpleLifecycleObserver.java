package com.wwzl.commonlib.lifecycle;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;

/**
 * 基于arch.lifecycle机制的简单的生命周期实现, 提供绑定的方法和生命周期回调
 *
 * @author hanwei.chen
 * @since 2019/2/12 14:49
 */
public abstract class SimpleLifecycleObserver implements BaseLifecycle {
    @NonNull
    private final LifecycleOwner mLifecycleOwner;

    /**
     * 绑定生命周期, 不需要主动调用removeObserver, 除非不希望再监听了
     */
    protected SimpleLifecycleObserver(@NonNull LifecycleOwner owner) {
        mLifecycleOwner = owner;
        owner.getLifecycle().addObserver(this);
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onStart() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onStop() {

    }

    @CallSuper
    @Override
    public void onDestroy() {
        mLifecycleOwner.getLifecycle().removeObserver(this);
    }

    @Override
    public void onAny(LifecycleOwner owner, Lifecycle.Event event) {

    }
}
