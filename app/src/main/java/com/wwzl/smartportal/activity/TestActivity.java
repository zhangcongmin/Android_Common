package com.wwzl.smartportal.activity;

import android.os.Bundle;
import android.text.TextUtils;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.wwzl.commonlib.activity.BaseActionBarActivity;
import com.wwzl.commonlib.arouter.RouterUrl;
import com.wwzl.commonlib.http.RetrofitClient;
import com.wwzl.commonlib.rxjava.ApiChange;
import com.wwzl.commonlib.rxjava.RxBusUtil;
import com.wwzl.smartportal.R;
import com.wwzl.smartportal.databinding.ActivityTestBinding;

/**
 * @author: weibin.chen
 * @time: 2020/1/15
 * @des : 测试界面
 */
@Route(path = RouterUrl.App.TEST)
public class TestActivity extends BaseActionBarActivity<ActivityTestBinding> {
    @Override
    protected void init(Bundle savedInstanceState) {
        getBinding().setActivity(this);
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_test;
    }

    public void onClickTestIp() {
        RetrofitClient.setNewApiUrl("http://testwwhj.xianglianni.com/");
        RxBusUtil.post(new ApiChange());
        finish();
    }

    public void onClickLiveIp() {
        RetrofitClient.setNewApiUrl("http://wwhj.xianglianni.com/");
        RxBusUtil.post(new ApiChange());
        finish();
    }

    public void onClickChangeIp() {
        String url = getBinding().etUrl.getText().toString();
        if (TextUtils.isEmpty(url)) {
            toast("url 不能为空");
            return;
        }

        //todo   切换自定义 本地IP 环境
        // 定义正则表达式
        String regex = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\."
                + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
                + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
                + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";
        // 判断ip地址是否与正则表达式匹配
        if (url.matches(regex)) {
            // 返回判断信息
            RetrofitClient.setNewApiUrl("http://"+url+"/");
            RxBusUtil.post(new ApiChange());
            finish();

        } else {
            // 返回判断信息
            toast("请输入合法ip地址");
        }
    }
}
