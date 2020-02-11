package com.wwzl.commonlib.glide;

import android.content.Context;

import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Priority;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator;
import com.bumptech.glide.module.AppGlideModule;
import com.bumptech.glide.request.RequestOptions;

@GlideModule
public final class MyAppGlideModule extends AppGlideModule {
    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        MemorySizeCalculator calculator = new MemorySizeCalculator.Builder(context)
                .build();
        builder.setMemoryCache(new LruResourceCache((long) (calculator.getMemoryCacheSize() * 1.2)));
        builder.setBitmapPool(new LruBitmapPool((long) (calculator.getBitmapPoolSize() * 1.2)));
        // 默认 200 MB
        int diskCacheSizeBytes = 1024 * 1024 * 200;
        builder.setDiskCache(new InternalCacheDiskCacheFactory(context, diskCacheSizeBytes));
        RequestOptions options = new RequestOptions()
                .format(DecodeFormat.PREFER_RGB_565)
                .disallowHardwareConfig()
                .priority(Priority.LOW)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        builder.setDefaultRequestOptions(options);
    }

    /**
     * 清单解析的开启
     * 这里不开启，避免添加相同的modules两次
     */
    @Override
    public boolean isManifestParsingEnabled() {
        return false;
    }
}