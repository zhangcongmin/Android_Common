package com.wwzl.commonlib.application;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;

import androidx.appcompat.app.AppCompatDelegate;

import com.alibaba.android.arouter.launcher.ARouter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.tencent.bugly.crashreport.CrashReport;
import com.wwzl.commonlib.BuildConfig;
import com.wwzl.commonlib.R;
import com.wwzl.commonlib.view.refresh.footer.CustomerFooter;
import com.wwzl.commonlib.view.refresh.header.MaterialHeader;

import java.util.List;
import java.util.Locale;

public class AppApplication extends Application {
    private static AppApplication instance;
    private static Context mContext;

    static {
        //启用矢量图兼容
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        //设置全局默认配置（优先级最低，会被其他设置覆盖）
        SmartRefreshLayout.setDefaultRefreshInitializer((context, layout) -> {
            //全局设置（优先级最低）
            layout.setEnableAutoLoadMore(false);
            layout.setEnableOverScrollDrag(false);
            layout.setEnableOverScrollBounce(false);
            layout.setEnableLoadMoreWhenContentNotFull(true);
            layout.setEnableScrollContentWhenRefreshed(true);
        });
        SmartRefreshLayout.setDefaultRefreshHeaderCreator((context, layout) -> {
            MaterialHeader header = new MaterialHeader(context);
            int color = R.color.colorPrimary;
            //全局设置主题颜色（优先级第二低，可以覆盖 DefaultRefreshInitializer 的配置，与下面的ClassicsHeader绑定）
            layout.setPrimaryColorsId(color);
            header.setColorSchemeResources(color, color, color, color, color, color);
            return header;
        });
    }

    //    public static boolean isEn = false;
    public static boolean isEn() {
        return Locale.getDefault().getLanguage().contains("en");
    }

    @Override
    public void onCreate() {
        super.onCreate();
//        isEn = Locale.getDefault().getLanguage().contains("en");
        instance = this;
        mContext = this.getApplicationContext();
        init();
        initCrashHandler();
    }




    private void initCrashHandler() {
        CrashReport.initCrashReport(getApplicationContext(), "2222e41055", BuildConfig.DEBUG);
    }


    public static Context getContext() {
        return mContext;
    }

    public static AppApplication getApp() {
        return instance;
    }

    private void init() {
        if (BuildConfig.DEBUG) {
            ARouter.openLog();
            ARouter.openDebug();
        }
        ARouter.init(this);
        SmartRefreshLayout.setDefaultRefreshFooterCreator((context, layout) -> {
            initFooterString();
            return new CustomerFooter(context).setSpinnerStyle(SpinnerStyle.Translate);
        });
    }


    private boolean shouldInit() {
        ActivityManager am = ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE));
        List<ActivityManager.RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
        String mainProcessName = getPackageName();
        int myPid = android.os.Process.myPid();
        for (ActivityManager.RunningAppProcessInfo info : processInfos) {
            if (info.pid == myPid && mainProcessName.equals(info.processName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 上拉加载更多的 刷新控件的文案初始化
     */
    private void initFooterString() {
//        ClassicsFooter.REFRESH_FOOTER_PULLING = getString(R.string.pull_up_load_more);
//        ClassicsFooter.REFRESH_FOOTER_RELEASE = getString(R.string.release_load);
//        ClassicsFooter.REFRESH_FOOTER_LOADING = getString(R.string.loading);
//        ClassicsFooter.REFRESH_FOOTER_REFRESHING = getString(R.string.refreshing);
//        ClassicsFooter.REFRESH_FOOTER_FINISH = getString(R.string.load_finish);
//        ClassicsFooter.REFRESH_FOOTER_FAILED = getString(R.string.load_fail);
//        ClassicsFooter.REFRESH_FOOTER_NOTHING = getString(R.string.no_more_date);
    }
}
