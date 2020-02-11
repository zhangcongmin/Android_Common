package com.wwzl.commonlib.api;


import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.android.FragmentEvent;
import com.wwzl.commonlib.activity.BaseRxActivity;
import com.wwzl.commonlib.bean.BaseResponse;
import com.wwzl.commonlib.dialog.BaseDialog;
import com.wwzl.commonlib.fragment.BaseRxFragment;
import io.reactivex.Observable;
import io.reactivex.Observer;

/**
 * 自定义网络请求  生命周期绑定
 *
 * @author weibin.chen
 * @since 2019/4/2 19:19
 */
public class ApiRequest {

    public static <T> void requestToFragment(BaseRxFragment fragment, Observable<BaseResponse<T>> responseObservable, Observer<BaseResponse<T>> observer) {
        responseObservable
                .compose(fragment.bindUntilEvent(FragmentEvent.DESTROY))
                .compose(SchedulerTransformer.transformer())
                .subscribe(observer);
    }

    /**
     * 有加载进度条的接口请求  可设置加载是否手动关闭
     */
    public static <T> void requestToFragmentShowLoading(BaseRxFragment fragment, Observable<BaseResponse<T>> responseObservable,
                                                        Observer<BaseResponse<T>> observer, boolean cancelable) {
        responseObservable
                .compose(fragment.bindUntilEvent(FragmentEvent.DESTROY))
                .compose(SchedulerTransformer.transformer())
                .compose(ProgressUtils.applyProgressBar(fragment.getBaseActivity(), cancelable))
                .subscribe(observer);
    }


    public static <T> void requestToActivity(BaseRxActivity activity, Observable<BaseResponse<T>> responseObservable,
                                             Observer<BaseResponse<T>> observer) {
        responseObservable
                .compose(activity.bindUntilEvent(ActivityEvent.DESTROY))
                .compose(SchedulerTransformer.transformer())
                .subscribe(observer);

    }

    public static <T> void requestToFragmentShowLoading(BaseRxFragment fragment, Observable<BaseResponse<T>> responseObservable,
                                                        Observer<BaseResponse<T>> observer) {
        requestToFragmentShowLoading(fragment, responseObservable, observer, false);
    }

    public static <T> void requestToDialogFragmentShowLoading(BaseDialog fragment, Observable<BaseResponse<T>> responseObservable,
                                                              Observer<BaseResponse<T>> observer) {
        requestToDialogFragmentShowLoading(fragment, responseObservable, observer, true);
    }

    /**
     * 有加载进度条的接口请求  可设置加载是否手动关闭
     */
    public static <T> void requestToDialogFragmentShowLoading(BaseDialog dialog, Observable<BaseResponse<T>> responseObservable,
                                                              Observer<BaseResponse<T>> observer, boolean cancelable) {
        responseObservable
                .compose(dialog.bindUntilEvent(FragmentEvent.DESTROY))
                .compose(SchedulerTransformer.transformer())
                .compose(ProgressUtils.applyProgressBar(dialog.getBaseActivity(), cancelable))
                .subscribe(observer);
    }

    /**
     * 有进度条的接口请求  可设置加载是否手动关闭
     */
    public static <T> void requestToActivityShowLoading(BaseRxActivity activity, Observable<BaseResponse<T>> responseObservable,
                                                        Observer<BaseResponse<T>> observer, boolean cancelable) {
        responseObservable
                .compose(activity.bindUntilEvent(ActivityEvent.DESTROY))
                .compose(SchedulerTransformer.transformer())
                .compose(ProgressUtils.applyProgressBar(activity, cancelable))
                .subscribe(observer);
    }

    public static <T> void requestToActivityShowLoading(BaseRxActivity activity, Observable<BaseResponse<T>> responseObservable,
                                                        Observer<BaseResponse<T>> observer) {
        requestToActivityShowLoading(activity, responseObservable, observer, false);
    }

    public static <T> Observable<BaseResponse<T>> requestToActivityShowLoading(BaseRxActivity activity, Observable<BaseResponse<T>> responseObservable, boolean cancelable) {
        return responseObservable
                .compose(activity.bindUntilEvent(ActivityEvent.DESTROY))
                .compose(SchedulerTransformer.transformer())
                .compose(ProgressUtils.applyProgressBar(activity, cancelable));
    }
}
