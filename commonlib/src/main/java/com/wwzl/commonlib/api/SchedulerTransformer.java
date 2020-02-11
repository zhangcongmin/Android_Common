package com.wwzl.commonlib.api;

import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/***
 * 线程转换
 * @author weibin  chen
 * @since 2019/3/1 14:36
 */
class SchedulerTransformer {
    static <T> ObservableTransformer<T, T> transformer() {
        return upstream -> upstream
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
