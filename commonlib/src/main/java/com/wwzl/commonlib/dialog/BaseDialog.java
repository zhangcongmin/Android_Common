package com.wwzl.commonlib.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.trello.rxlifecycle2.components.support.RxAppCompatDialogFragment;
import com.wwzl.commonlib.R;
import com.wwzl.commonlib.activity.BaseRxActivity;
import com.wwzl.commonlib.log.LogDebug;


/**
 * @author weibin.chen
 * @since 2019/8/17 10:35
 */
public abstract class BaseDialog<V extends ViewDataBinding> extends RxAppCompatDialogFragment  implements DialogInterface.OnKeyListener{

    private DialogInterface.OnKeyListener mKeyListener;
    private ExceptionListener mExceptionListener;
    @NonNull
    private V binding;
    @Nullable
    @Override
    public final View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, getContentLayoutId(), container, false);
        return binding.getRoot();
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_FRAME, R.style.AlertDialogStyle);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(savedInstanceState);
    }

    protected abstract void init(Bundle savedInstanceState);

    @NonNull
    public V getBinding() {
        return binding;
    }

    protected abstract int getContentLayoutId();

    public void show(FragmentManager supportFragmentManager) {
        show(supportFragmentManager, getClass().getSimpleName());
    }

    /**
     * 设置显示位置，宽高 ，LayoutParams.WRAP_CONTENT = -2  MATCH_PARENT = -1
     *
     * @param gravity
     * @param width
     * @param height
     */
    public void showLocation(int gravity, int width, int height) {
        if (height == 0) {
            height = ViewGroup.LayoutParams.WRAP_CONTENT;
        }
        Window window = showLocation(gravity);
        if (window != null) {
            window.setLayout(width, height);
        }
    }

    public void setCanceledOnTouchOutside(boolean outside){
        if (getDialog() != null) {
            getDialog().setCanceledOnTouchOutside(outside);
        }
    }

    /**
     * 设置位置
     *
     * @param gravity
     */
    public Window showLocation(int gravity) {
        if (getDialog() != null) {
            getDialog().setCanceledOnTouchOutside(true);
            Window window = getDialog().getWindow();
            if (window != null) {
                window.setGravity(gravity);
                window.setDimAmount(0.5f);
            }
            return window;
        } else {
            return null;
        }
    }

    /**
     * 监听是否发生异常使得dialog不能show
     */
    protected interface ExceptionListener {
        void onExceptionHappen(Exception e);
    }

    protected void setOnExceptionListener(ExceptionListener exceptionListener) {
        mExceptionListener = exceptionListener;
    }

    /**
     * 设置按键监听
     */
    public void setOnKeyListener(DialogInterface.OnKeyListener listener) {
        mKeyListener = listener;
    }

    @Override
    public void show(FragmentManager manager, String tag) {
        this.show(manager.beginTransaction(), tag);
    }
    @Override
    public int show(FragmentTransaction transaction, String tag) {
        if (isResumed() || isAdded() || isVisible() || isRemoving() || isDetached()) {
            if (mExceptionListener != null) {
                mExceptionListener.onExceptionHappen(new Exception("showDialog Failed"));
            }
            return -1;
        }
        try {
            return transaction.add(this, tag).commitAllowingStateLoss();
        } catch (Exception e) {
            LogDebug.e(getClass().getSimpleName(), "showDialog Failed", e);
            e.printStackTrace();
            if (mExceptionListener != null) {
                mExceptionListener.onExceptionHappen(e);
            }
            return -1;
        }
    }

    @Override
    public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
        return null != mKeyListener && mKeyListener.onKey(dialog, keyCode, event);
    }

    public BaseRxActivity getBaseActivity() {
        return (BaseRxActivity) getActivity();
    }
}
