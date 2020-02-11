package com.wwzl.commonlib.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.alibaba.android.arouter.launcher.ARouter;
import com.trello.rxlifecycle2.components.support.RxFragment;
import com.wwzl.commonlib.activity.BaseRxActivity;
import com.wwzl.commonlib.arouter.CustomNavigationCallback;
import com.wwzl.commonlib.dialog.WaitDialog;
import com.wwzl.commonlib.log.LogDebug;

import java.lang.reflect.Field;

public abstract class BaseRxFragment<V extends ViewDataBinding> extends RxFragment {
    private static final String TAG = "fragment";
    protected V binding;

    @Nullable
    @Override
    public final View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, getContentLayoutId(), container, false);
        return binding.getRoot();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        LogDebug.e(TAG, this + " onCreate ");
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        LogDebug.e(TAG, this + " onViewCreated ");
        super.onViewCreated(view, savedInstanceState);
        ARouter.getInstance().inject(this);
        init(savedInstanceState);
    }

    public V getBinding() {
        return binding;
    }

    @Nullable
    public BaseRxActivity getBaseActivity() {
        return (BaseRxActivity) getActivity();
    }

    protected abstract int getContentLayoutId();

    protected abstract void init(@Nullable Bundle savedInstanceState);

    @Override
    public void onDestroy() {
        LogDebug.e(TAG, this + " onDestroy ");
        super.onDestroy();
    }

    /**
     * 不带参数跳转
     *
     * @param path
     */
    public void toActivity(String path) {
        ARouter.getInstance().build(path).navigation(getBaseActivity(), new CustomNavigationCallback(getBaseActivity()));
    }


    /**
     * 替换fragment
     *
     * @param fragment
     * @param frameId
     */
    protected void replaceFragment(BaseRxFragment fragment, @IdRes int frameId) {
        getChildFragmentManager().beginTransaction()
                .replace(frameId, fragment, fragment.getClass().getSimpleName())
                .commitAllowingStateLoss();

    }

    /**
     * 设置顶部布局高度
     *
     * @param viewTop
     */
    protected void setStatusBarHeight(View viewTop) {
        //注意：layoutParm必须是LinearLayout
        int statusbarHeight = getStatusbarHeight();
        if (statusbarHeight > 0) {
            ViewGroup.LayoutParams layoutParams = viewTop.getLayoutParams();
            layoutParams.height = statusbarHeight;
//            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, statusbarHeight);
            viewTop.setLayoutParams(layoutParams);
        }
    }

    /**
     * 获取顶部状态栏高度
     *
     * @return
     */
    public int getStatusbarHeight() {
        int stateBarHeight = 0;
        try {
            Class c = Class.forName("com.android.internal.R$dimen");
            Object obj = c.newInstance();
            Field field = c.getField("status_bar_height");
            int x = Integer.parseInt(field.get(obj).toString());
            stateBarHeight = getResources().getDimensionPixelSize(x);
            return stateBarHeight;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stateBarHeight;
    }

    private long startTime;
    private WaitDialog waitDialog;

    protected void showWaitDialog() {
        showWaitDialog("");
    }

    protected void showWaitDialog(String msg) {
        if (waitDialog != null) {
            waitDialog.dismiss();
            waitDialog = null;
        }
        startTime = System.currentTimeMillis();
        waitDialog = new WaitDialog();
        waitDialog.showNow(getChildFragmentManager(), this.getClass().getSimpleName());
    }

    //判断是否加载时长超过预设值
    protected boolean checkWaiting(long duration) {
        long curTime = System.currentTimeMillis();
        return curTime - startTime > duration;
    }

    protected void disMissWaitDialog() {
        if (waitDialog != null) {
            waitDialog.dismiss();
            waitDialog = null;
        }
    }

}
