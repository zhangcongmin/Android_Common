
package com.wwzl.commonlib.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.ViewDataBinding;
import com.wwzl.commonlib.R;
import com.wwzl.commonlib.util.StatusBarUtils;


/**
 * @Description  BaseActionBarActivity继承于BaseActivity，封装了actionBar的逻辑
 * 继承于ActionBarBaseActivity的Activity都将默认带有ActionBar，并且只能使用AppTheme主题；
 * 只有那些ActionBar只带有Title和返回按钮的Activity方可继承
 * @Author weibin.chen
 * @date 2019-07-25 23:02
 */
public abstract class BaseActionBarActivity<V extends ViewDataBinding> extends BaseRxActivity<V> {

    private TextView tvTitle,tvRight;

    /**
     * 设置默认标题id
     *
     * @return 标题id
     */
    protected void setTitleId(int titleId) {
        if (tvTitle != null) {
            tvTitle.setText(titleId);
        }
    }

    /**
     * 更新标题
     *
     * @param title String标题
     */
    protected void setTitle(String title) {
        if (tvTitle != null) {
            tvTitle.setText(title);
        }
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //设置主题
        setTheme(R.style.AppTheme_DarkActionBar);
        super.onCreate(savedInstanceState);

        //标题栏设置
        ActionBar actionBar = getSupportActionBar();
        StatusBarUtils.setWhiteStatusBar(this,true);
        if (actionBar != null) {
            // 返回箭头（默认不显示）
//            actionBar.setHomeAsUpIndicator(R.drawable.ic_back_black);
            // 左侧图标显示
            actionBar.setDisplayHomeAsUpEnabled(false);
            // 使左上角图标(系统)是否显示
            actionBar.setDisplayShowHomeEnabled(false);
            // 左侧图标点击事件使能
            actionBar.setHomeButtonEnabled(false);
            // 显示标题
            actionBar.setDisplayShowTitleEnabled(false);
            //显示自定义视图
            actionBar.setDisplayShowCustomEnabled(true);
            actionBar.setElevation(0);
            // 这里可以自定义titlebar 的布局样式
            View customView = LayoutInflater.from(this).inflate(R.layout.custom_toolbar, null);
            ActionBar.LayoutParams layoutParams =  new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT,
                    ActionBar.LayoutParams.MATCH_PARENT);
            actionBar.setCustomView(customView,layoutParams);
            tvTitle = customView.findViewById(R.id.tv_title);
            tvRight = customView.findViewById(R.id.tv_right_text);
            ImageView imageView  = customView.findViewById(R.id.btn_back);
            if(getTitle()!=null) {
                // 默认获取 配置清单文件的 title name 动态需要调用 setTitle重新设置
                tvTitle.setText(getTitle());

            }
            Toolbar toolbar = (Toolbar) customView.getParent();
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                toolbar.setElevation(0);
//            }
//            toolbar.setPadding(0,0, 0,0);
            toolbar.setContentInsetsAbsolute(0,0);
            imageView.setOnClickListener(v -> {
                onBackPressed();
            });
        }
    }
    protected void showRight(String text, View.OnClickListener listener) {
        if(tvRight!=null) {
            tvRight.setVisibility(View.VISIBLE);
            tvRight.setText(text);
            tvRight.setOnClickListener(listener);
        }
    }

    protected void showRight(int resStringId, View.OnClickListener listener) {
        if(tvRight!=null) {
            tvRight.setVisibility(View.VISIBLE);
            tvRight.setText(resStringId);
            tvRight.setOnClickListener(listener);
        }
    }
}
