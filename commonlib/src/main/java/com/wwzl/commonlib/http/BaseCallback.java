package com.wwzl.commonlib.http;


import androidx.annotation.Nullable;
import com.wwzl.commonlib.bean.BaseResponse;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * 网络请求通用的回调
 *
 * @author weibin.chen
 * @since 2019/4/2 19:19
 */
public abstract class BaseCallback<T> implements Observer<BaseResponse<T>> {

    Disposable disposable;
    /**
     * 成功返回数据
     */
    public abstract void onSucceed(@Nullable T t, String resultMsg);

    /**
     * 网络异常 解析异常  或 服务端异常，错误 并返回数据。
     */
    public void onError(String errorCode, String error, @Nullable T t) {
    }

    public void onFinish() {
    }

    @Override
    public void onSubscribe(Disposable disposable) {
        this.disposable = disposable;
    }

    @Override
    public void onNext(BaseResponse<T> response) {
        if (response.isSuccessful()) {
            onSucceed(response.data, response.msg);
        } else {
            onError(response.code, response.msg, response.data);
        }
        onFinish();
    }

    @Override
    public void onError(Throwable throwable) {
        onError("UNKNOWN_ERROR", throwable.toString(), null);
        onFinish();
    }

    @Override
    public final void onComplete() {
        //事件完成取消订阅
        if (disposable !=null && !disposable.isDisposed()){
            disposable.dispose();
            disposable =null;
        }
    }
}
