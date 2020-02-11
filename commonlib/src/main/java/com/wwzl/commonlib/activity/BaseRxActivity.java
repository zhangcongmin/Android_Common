package com.wwzl.commonlib.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.alibaba.android.arouter.launcher.ARouter;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import com.wwzl.commonlib.arouter.CustomNavigationCallback;
import com.wwzl.commonlib.dialog.WaitDialog;
import com.wwzl.commonlib.fragment.BaseRxFragment;
import com.wwzl.commonlib.util.SpUtils;
import java.lang.reflect.Field;
import java.util.Locale;

import io.reactivex.disposables.Disposable;


/**
 * @Description 基础抽象类
 * @Author weibin.chen
 * @date 2019-07-25 23:02
 */
public abstract class BaseRxActivity<V extends ViewDataBinding> extends RxAppCompatActivity {

    protected static String TAG;
    private V binding;

    protected Disposable mdis;
    private ProgressDialog mProgressDialog;

    @NonNull
    public V getBinding() {
        return binding;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        TAG = this.getClass().getName();
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, getContentLayoutId());
        ARouter.getInstance().inject(this);
        init(savedInstanceState);
        loadData();
    }


    @Override
    protected void attachBaseContext(Context newBase) {
        String language = SpUtils.getAsString(newBase, SpUtils.commonCacheName, SpUtils.LANGUAGE, "cn");
        if (!TextUtils.equals(language, "cn")) {
            Configuration configuration = newBase.getResources().getConfiguration();
            configuration.setLocale(Locale.ENGLISH);
            Context newContext = newBase.createConfigurationContext(configuration);
            super.attachBaseContext(newContext);
        } else {
            super.attachBaseContext(newBase);
        }
    }

    protected abstract void init(Bundle savedInstanceState);

    protected abstract int getContentLayoutId();

    /**
     * 加载数据
     */
    protected void loadData() {

    }

    /**
     * 不带参数跳转
     *
     * @param context
     * @param path
     */
    public static void toActivity(final BaseRxActivity context, String path) {
        ARouter.getInstance().build(path).navigation(context, new CustomNavigationCallback(context));
    }


    private long startTime;
    private WaitDialog waitDialog;

    protected void showWaitDialog() {
        showWaitDialog("");
    }
    protected void showWaitDialog(String msg) {
        if(waitDialog != null){
            waitDialog.dismiss();
            waitDialog = null;
        }
        startTime = System.currentTimeMillis();
        waitDialog = new WaitDialog();
        waitDialog.showNow(getSupportFragmentManager(),this.getClass().getSimpleName());
    }

    //判断是否加载时长超过预设值
    protected boolean checkWaiting(long duration) {
        long curTime = System.currentTimeMillis();
        return curTime - startTime > duration;
    }

    protected void disMissWaitDialog(){
        if(waitDialog != null && !isFinishing()){
            waitDialog.dismiss();
            waitDialog = null;
        }
    }


    public void showProgress(final String msg) {
        if (!isFinishing()) {
            mProgressDialog = ProgressDialog.show(BaseRxActivity.this, "", msg, false, false);
        }
    }

    public void dismissProgress() {
        if (mProgressDialog != null && mProgressDialog.isShowing()
                && !isFinishing()) {
            mProgressDialog.dismiss();
        }
    }

    /**
     * 不带参数跳转
     *
     * @param context
     * @param path
     */
    public static void toActivityForResult(final BaseRxActivity context, final int requestCode, String path) {
        ARouter.getInstance().build(path).navigation(context, requestCode, new CustomNavigationCallback(context));
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mdis != null && !mdis.isDisposed()) {
            mdis.dispose();
            Log.d(TAG, "mdis: dispose");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (binding != null) {
            binding.unbind();
            binding = null;
        }
        dismissProgress();
    }

    /**
     * 返回上一级
     *
     * @return
     */
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    /**
     * 添加fragment
     *
     * @param fragment
     * @param frameId
     */
    protected void addFragment(BaseRxFragment fragment, @IdRes int frameId) {
        getSupportFragmentManager().beginTransaction()
                .add(frameId, fragment, fragment.getClass().getSimpleName())
//                .addToBackStack(fragment.getClass().getSimpleName())
                .commitAllowingStateLoss();

    }


    /**
     * 替换fragment
     *
     * @param fragment
     * @param frameId
     */
    protected void replaceFragment(BaseRxFragment fragment, @IdRes int frameId) {
        getSupportFragmentManager().beginTransaction()
                .replace(frameId, fragment, fragment.getClass().getSimpleName())
//                .addToBackStack(fragment.getClass().getSimpleName())
                .commitAllowingStateLoss();

    }


    /**
     * 隐藏fragment
     *
     * @param fragment
     */
    protected void hideFragment(BaseRxFragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .hide(fragment)
                .commitAllowingStateLoss();

    }

    private Fragment curFragment;
    /**
     * 显示fragment
     * @param fragment
     * @param frameId
     * 显示fragment 该方法如果传进来的fragment已经被add过则不会再次执行onCreateView方法
     */
    protected void showFragment(BaseRxFragment fragment,@IdRes int frameId) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if(curFragment!=null){
            fragmentTransaction.hide(curFragment);
        }
        curFragment = fragment;
        if (fragment != null && fragment.isAdded()) {
            fragmentTransaction.show(fragment);
        } else {
            fragmentTransaction.add(frameId, fragment);
        }
        fragmentTransaction.commitAllowingStateLoss();
//        getSupportFragmentManager().beginTransaction()
//                .show(fragment)
//                .commitAllowingStateLoss();

    }


    /**
     * 移除fragment
     *
     * @param fragment
     */
    protected void removeFragment(BaseRxFragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .remove(fragment)
                .commitAllowingStateLoss();

    }

    protected Fragment findFragment(String simpleName) {
        return getSupportFragmentManager().findFragmentByTag(TextUtils.isEmpty(simpleName) ? "" : simpleName);
    }

    /**
     * 弹出栈顶部的Fragment
     */
    protected void popFragment() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack();
        } else {
            finish();
        }
    }

    @Override
    public void finish() {
        super.finish();
    }


    public void toast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
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
//    public String getStatusbarHeight() {
//        int stateBarHeight = 0;
//        try {
//            Class c = Class.forName("com.android.internal.R$dimen");
//            Object obj = c.newInstance();
//            Field field = c.getField("status_bar_height");
//            int x = Integer.parseInt(field.get(obj).toString());
//            stateBarHeight = getResources().getDimensionPixelSize(x);
//            return stateBarHeight+"px";
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return "25dp";
//    }


    //去掉动画关闭activity
    public void finishWithoutAnim() {
        super.finish();
        overridePendingTransition(0, 0);
    }


    //region 点击非输入框之外地方隐藏软键盘并使输入框失去焦点
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (cursorInvisibleEditText != null)
                cursorInvisibleEditText.setCursorVisible(true);
            if (isShouldHideInput(v, ev)) {
                hideInputMethod(this, v);
//                if (hideResult)
//                    return true;
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    private EditText cursorInvisibleEditText;

    private boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            v.getLocationOnScreen(leftTop);
            int left = leftTop[0], top = leftTop[1], bottom = top + v.getHeight(), right = left
                    + v.getWidth();
            if (event.getRawX() > left && event.getRawX() < right
                    && event.getRawY() > top && event.getRawY() < bottom) {
                // 保留点击EditText的事件
                return false;
            } else {
                cursorInvisibleEditText = (EditText) v;
                cursorInvisibleEditText.setCursorVisible(false);
                return true;
            }
        }
        return false;
    }

    private Boolean hideInputMethod(Context context, View v) {
        InputMethodManager imm = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            return imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
        return false;
    }
}
