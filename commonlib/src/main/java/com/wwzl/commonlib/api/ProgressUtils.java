package com.wwzl.commonlib.api;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.SystemClock;

import com.wwzl.commonlib.activity.BaseRxActivity;
import com.wwzl.commonlib.dialog.WaitDialog;
import com.wwzl.commonlib.util.LogUtils;

import java.lang.ref.WeakReference;

import io.reactivex.ObservableTransformer;
import io.reactivex.annotations.NonNull;

public class ProgressUtils {
    private static long showTime = 0;

    public static <T> ObservableTransformer<T, T> applyProgressBar(
            @NonNull final BaseRxActivity activity, boolean cancelable) {
        final WeakReference<BaseRxActivity> activityWeakReference = new WeakReference<>(activity);
        showTime = System.currentTimeMillis();
        WaitDialog.showWaitDialog(activity, cancelable);
        return upstream -> upstream.doOnSubscribe(disposable -> {
            BaseRxActivity context;
            if ((context = activityWeakReference.get()) != null
                    && !context.isFinishing()) {
                dismissDialog(context);
            }
        }).doOnTerminate(() -> {
            BaseRxActivity context;
            if ((context = activityWeakReference.get()) != null
                    && !context.isFinishing()) {
                dismissDialog(context);
            }
        }).doOnDispose(() -> {
            BaseRxActivity context;
            if ((context = activityWeakReference.get()) != null
                    && !context.isFinishing()) {
                dismissDialog(context);
            }
        });
    }

    private static void dismissDialog(BaseRxActivity context) {
        long curTime = System.currentTimeMillis();
        if (curTime - showTime < 300) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    SystemClock.sleep(300 - (curTime - showTime));
                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            WaitDialog.dismissWaitDialog(context);
                        }
                    });
                }
            }).start();
        } else {
            WaitDialog.dismissWaitDialog(context);
        }
    }

}
