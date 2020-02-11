package com.wwzl.smartportal.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.alibaba.android.arouter.launcher.ARouter;
import com.wwzl.commonlib.BuildConfig;
import com.wwzl.commonlib.activity.BaseRxActivity;
import com.wwzl.commonlib.arouter.RouterUrl;
import com.wwzl.commonlib.config.ShakeSensorWorker;
import com.wwzl.commonlib.fragment.BaseRxFragment;
import com.wwzl.commonlib.util.ContextUtils;
import com.wwzl.commonlib.util.UIUtils;
import com.wwzl.commonlib.view.NavigationView;
import com.wwzl.smartportal.R;
import com.wwzl.smartportal.databinding.ActivityHomeBinding;
import java.util.ArrayList;
import java.util.List;


/**
 * author : zcm
 * updateTime   : 2019/12/25
 * summary   :
 * version: 1.0
 */
public class HomeActivity extends BaseRxActivity<ActivityHomeBinding> {

    @Override
    protected void init(Bundle savedInstanceState) {
        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            //完美解决：APP下载安装后，点击“直接打开”，启动应用后，按下HOME键，再次点击桌面上的应用，会重启一个新的应用问题
            finish();
            return;
        }
        UIUtils.setImmersionStatusBar(this, true);
        getWindow().setBackgroundDrawable(null);
        initView();
        registerListener();
        yaoyitaoTest();
    }

    /**
     * 进入摇一摇配置界面
     */
    private void yaoyitaoTest() {
        new ShakeSensorWorker(this, () -> {
            if (!ContextUtils.isContextValid(this)) {
                return;
            }
            if(BuildConfig.DEBUG){
                toActivity(this,RouterUrl.App.TEST);
            }
        });

    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_home;
    }

    private void registerListener() {
        getBinding().naviView.setOnTabSelectedListener(new NavigationView.OnTabSelectedListener() {
            @Override
            public void selected(int index, NavigationView.Model model) {
//                vPager.setCurrentItem(index);
                getBinding().viewPager.setCurrentItem(index, false);
            }

            @Override
            public void unselected(int index, NavigationView.Model model) {

            }
        });
    }

    private void initView() {
        List<NavigationView.Model> tabs = new ArrayList<>();
        tabs.add(new NavigationView.Model.Builder(R.mipmap.home_selected, R.mipmap.home).title(getString(R.string.home)).build());
        tabs.add(new NavigationView.Model.Builder(R.mipmap.category_selected, R.mipmap.category).title(getString(R.string.category)).build());
        tabs.add(new NavigationView.Model.Builder(R.mipmap.mine_selected, R.mipmap.mine).title(getString(R.string.mine)).build());
        getBinding().naviView.setItems(tabs);
        getBinding().naviView.build();
        getBinding().naviView.check(1);
        initViewPager();
    }

    private int TAB_HOME = 0;
    private int TAB_CATEGORY = 1;
    private int TAB_MINE = 2;

    private void initViewPager() {
        ArrayList<Fragment> fragments = new ArrayList<>();
        BaseRxFragment home = (BaseRxFragment) ARouter.getInstance().build(RouterUrl.Category.MINE).navigation();
        BaseRxFragment categoryHome = (BaseRxFragment) ARouter.getInstance().build(RouterUrl.Category.HOME).navigation();
        BaseRxFragment mine = (BaseRxFragment) ARouter.getInstance().build(RouterUrl.Category.MINE).navigation();
        fragments.add(TAB_HOME, home);
        fragments.add(TAB_CATEGORY, categoryHome);
        fragments.add(TAB_MINE, mine);
        getBinding().viewPager.setAdapter(new TabsPagerAdapter(getSupportFragmentManager(), fragments));
        getBinding().viewPager.setOffscreenPageLimit(3);
        getBinding().viewPager.setNoScroll(true);
        getBinding().viewPager.setCurrentItem(1, false);
    }

    class TabsPagerAdapter extends FragmentPagerAdapter {
        private List<Fragment> fragments;

        public TabsPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
            super(fm);
            this.fragments = fragments;
        }

        @Override
        public Fragment getItem(int position) {
            return (fragments == null || fragments.size() == 0) ? null : fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments == null ? 0 : fragments.size();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
        } else {
//            ActivityManager mActivityManager = (ActivityManager) this.getSystemService(Context.ACTIVITY_SERVICE);
//            List<ActivityManager.RunningAppProcessInfo> mList = mActivityManager.getRunningAppProcesses();
//            for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : mList) {
//                if (runningAppProcessInfo.pid != android.os.Process.myPid()) {
//                    android.os.Process.killProcess(runningAppProcessInfo.pid);
//                }
//            }
//            android.os.Process.killProcess(android.os.Process.myPid());
//
//            System.exit(0);
        }
    }
}
