package com.wwzl.commonlib.glide;

import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.widget.ImageView;

import androidx.annotation.IntDef;
import androidx.annotation.NonNull;

import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory;
import com.wwzl.commonlib.R;
import com.wwzl.commonlib.glide.transformation.CropTransformation;
import com.wwzl.commonlib.glide.transformation.RoundedCornersTransformation;
import com.wwzl.commonlib.util.DisplayUtils;

/**
 * @author weibin chen
 * @since 2019/2/24 15:31
 */
public class ImageUtils {
    //图片来源路径 判断来源图片是否为服务器 对图片尺寸进行处理
    private static final String IMAGE_URL_SOURCE = "https://test/";
    private static final String GIF = ".gif";
    private static final String[] IMAGE_SUFFIX_ARRAY = new String[]{".png", ".jpg", ".jpeg", ".bmp", ".gif", ".webp"};
    private static DrawableTransitionOptions options;

    /**
     * 图片类型
     */
    @IntDef({ImageType.SMALL, ImageType.MIDDLE, ImageType.BIG})
    public @interface ImageType {
        int SMALL = 150;
        int MIDDLE = 300;
        int BIG = 500;
    }


    /**
     * 居中裁剪展示
     */
    public static void showCropImage(ImageView imageView, String url, int imageType) {
        GlideApp.with(imageView).load(getParseUrl(url, imageType))
                .format(getImageFormat(url))
                .centerCrop()
                .placeholder(R.mipmap.place_holder)
                .error(R.mipmap.place_holder)
                .fallback(R.mipmap.place_holder)
                .dontAnimate()
                .into(imageView);
    }
    /**
     * 不做裁剪处理  不设置默认图 自适应宽高
     */
    public static void showImage(ImageView imageView, String url, int imageType) {
        GlideApp.with(imageView).load(getParseUrl(url, imageType))
                .format(getImageFormat(url))
                .fitCenter()
                .dontAnimate()
                .into(imageView);
    }

    /**
     * 小图  居中裁剪展示
     * @param imageView
     * @param url
     */
    public static void showSmallCropImage(ImageView imageView, String url) {
        showCropImage(imageView,url, ImageType.SMALL);
    }
    /**
     * 中图  居中裁剪展示
     */
    public static void showMiddleCropImage(ImageView imageView, String url) {
        showCropImage(imageView,url, ImageType.MIDDLE);
    }
    /**
     * 大图  居中裁剪展示
     * @param imageView
     * @param url
     */
    public static void showBigCropImage(ImageView imageView, String url) {
        showCropImage(imageView,url, ImageType.SMALL);
    }


    /**
     * 从顶部裁图
     */
    public static void showCropTopImage(ImageView imageView, String url, int w, int h) {
        GlideApp.with(imageView).load(getParseUrl(url, w, h))
                .format(getImageFormat(url))
                .transform(new CropTransformation(w, h, CropTransformation.CropType.TOP))
                .placeholder(R.mipmap.place_holder)
                .error(R.mipmap.place_holder)
                .transition(getCrossFadeOptions())
                .into(imageView);
    }

    /**
     * 圆形图片
     * @param imageView
     * @param url
     */
    public static void showCircleImage(ImageView imageView, String url) {
        GlideApp.with(imageView).load(getParseUrl(url, ImageType.SMALL, ImageType.SMALL))
                .format(getImageFormat(url))
                .circleCrop().placeholder(R.mipmap.place_holder)
                .error(R.mipmap.place_holder).into(imageView);
    }


    /**
     * 圆形图片  自定义宽高
     * @param imageView
     * @param url
     * @param width
     * @param height
     */
    public static void showCircleImage(ImageView imageView, String url, int width, int height) {
        GlideApp.with(imageView).load(getParseUrl(url, width, height))
                .format(getImageFormat(url))
                .placeholder(R.mipmap.place_holder)
                .error(R.mipmap.place_holder)
                .circleCrop().into(imageView);
    }
    /**
     * 圆角图片   圆角
     * @param imageView
     * @param url
     */
    public static void showImage(ImageView imageView, String url) {
        GlideApp.with(imageView).load(url)
                .format(getImageFormat(url))
                .fitCenter()
                .placeholder(R.mipmap.place_holder)
                .error(R.mipmap.place_holder)
                .into(imageView);
    }
    /**
     * 圆角图片   圆角
     * @param imageView
     * @param url
     * @param round
     */
    public static void showRoundImage(ImageView imageView, String url, int round) {
        GlideApp.with(imageView).load(url)
                .format(getImageFormat(url))
                .centerCrop()
                .transform(new RoundedCornersTransformation(DisplayUtils.dp2px(round), 0, RoundedCornersTransformation.CornerType.ALL))
                .placeholder(R.mipmap.place_holder)
                .error(R.mipmap.place_holder)
                .into(imageView);
    }
    /**
     * 圆角图片   圆角
     * @param imageView
     * @param url
     * @param round
     */
    public static void showRoundImageCenterInside(ImageView imageView, String url, int round) {
        GlideApp.with(imageView).load(getParseUrl(url, ImageType.SMALL, ImageType.SMALL))
                .format(getImageFormat(url))
                .centerInside()
                .transform(new RoundedCornersTransformation(DisplayUtils.dp2px(round), 0, RoundedCornersTransformation.CornerType.ALL))
                .placeholder(R.mipmap.place_holder)
                .error(R.mipmap.place_holder)
                .into(imageView);
    }
    /**
     * 圆角图片  自定宽高  圆角
     * @param imageView
     * @param url
     * @param w
     * @param h
     * @param round
     */
    public static void showRoundImage(ImageView imageView, String url, int w, int h, int round) {
        GlideApp.with(imageView).load(getParseUrl(url, w, h))
                .format(getImageFormat(url))
                .override(w, h)
                .transform(new RoundedCornersTransformation(DisplayUtils.dp2px(round), 0, RoundedCornersTransformation.CornerType.ALL))
                .placeholder(R.mipmap.place_holder)
                .error(R.mipmap.place_holder)
                .into(imageView);
    }


    /**
     * 顶部圆角
     * @param imageView
     * @param url
     * @param round
     */
    public static void showTopRoundImage(ImageView imageView, String url, int round) {
        GlideApp.with(imageView).load(url)
                .format(getImageFormat(url))
                .placeholder(R.mipmap.place_holder)
                .error(R.mipmap.place_holder)
                .transform(new RoundedCornersTransformation(DisplayUtils.dp2px(round), 0, RoundedCornersTransformation.CornerType.TOP))
                .into(imageView);
    }


    /**
     * 自定义图片加载监听
     * @param imageView
     * @param url
     * @param w
     * @param h
     * @param listener
     */
    public static void showImageListener(ImageView imageView, String url, int w, int h, RequestListener<Drawable> listener) {
        GlideApp.with(imageView).load(getParseUrl(url, w, h)).format(getImageFormat(url)).listener(listener).into(imageView);
    }

    /**
     * 设置加载图片（优先级最高）
     */
    public static void showImageListener(ImageView imageView, String url, RequestListener<Drawable> listener) {
        GlideApp.with(imageView).load(url).priority(Priority.HIGH)
                .format(getImageFormat(url))
                .listener(listener).into(imageView);
    }

    /**
     * 显示 GIF
     * @param imageView
     * @param url
     */
    public static void showGifImage(ImageView imageView, String url) {
        GlideApp.with(imageView).load(url).format(DecodeFormat.PREFER_ARGB_8888).into(imageView);
    }


    /**
     * 切换GIF  展示模式 避免黑底
     * @param url
     * @return
     */
    private static DecodeFormat getImageFormat(String url) {
        if (TextUtils.isEmpty(url)) {
            return DecodeFormat.PREFER_RGB_565;
        } else {
            return url.endsWith(GIF) ? DecodeFormat.PREFER_ARGB_8888 : DecodeFormat.PREFER_RGB_565;
        }
    }


    private static String getParseUrl(String url, int imageType) {
        return getParseUrl(url,imageType,imageType);
    }
    /**
     * 处理图片尺寸展示
     * @param url
     * @param w
     * @param h
     * @return
     */
    private static String getParseUrl(String url, int w ,int h) {
        // todo 处理图片尺寸展示

        return url;
    }

    /**
     * 获取配置
     * @return
     */
    @NonNull
    private static DrawableTransitionOptions getCrossFadeOptions() {
        if (options == null) {
            DrawableCrossFadeFactory fadeFactory = new DrawableCrossFadeFactory.Builder(300).setCrossFadeEnabled(true).build();
            options = DrawableTransitionOptions.with(fadeFactory);
        }
        return options;
    }
}
