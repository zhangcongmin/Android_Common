package com.wwzl.commonlib.arouter;

import android.content.Context;

import androidx.core.app.ActivityOptionsCompat;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.launcher.ARouter;
import com.wwzl.commonlib.R;

/**
 * 作者: created by zcm on 2018/11/22
 * 修改: modified by zcm on 2018/11/22
 * desc(给页面跳转增加左右专场动画)
 */
public class RouterManager {

    public static Postcard getPostcard(String url){
        Postcard postcard = ARouter.getInstance().build(url);
//        postcard.navigation();
        return postcard;
    }

    /**
     * 添加无转场动画
     * @param context
     * @param url
     * @return
     */
    public static Postcard getPostcardWithNoneTransition(Context context, String url){
        Postcard postcard = ARouter.getInstance().build(url).withOptionsCompat(ActivityOptionsCompat
                .makeCustomAnimation(context, R.anim.none_animation,R.anim.none_animation));
        return postcard;
    }
    /**
     * 添加左右滑动转场动画
     * @param context
     * @param url
     * @return
     */
    public static Postcard getPostcardWithLRTransition(Context context, String url){
        Postcard postcard = ARouter.getInstance().build(url).withOptionsCompat(ActivityOptionsCompat
                .makeCustomAnimation(context, R.anim.slide_in_right,R.anim.slide_out_left));
        return postcard;
    }


    /**
     * 添加上下滑动转场动画  从顶部进入  主要用于HomeActivity 跳TopActivity
     * @param context
     * @param url
     * @return
     */
    public static Postcard getPostcardWithTBTransition(Context context, String url){
        Postcard postcard = ARouter.getInstance().build(url).withOptionsCompat(ActivityOptionsCompat
                .makeCustomAnimation(context, R.anim.enter_top,R.anim.exit_bottom));
        return postcard;
    }


    /**
     * 添加下上滑动转场动画  从底部进入  主要用于TopActivity 跳其他5个主模块
     * @param context
     * @param url
     * @return
     */
    public static Postcard getPostcardWithBTTransition(Context context, String url){
        Postcard postcard = ARouter.getInstance().build(url).withOptionsCompat(ActivityOptionsCompat
                .makeCustomAnimation(context, R.anim.enter_bottom,R.anim.exit_top));
        return postcard;
    }
}
