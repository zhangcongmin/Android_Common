package com.wwzl.commonlib.http;

import android.text.TextUtils;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wwzl.commonlib.BuildConfig;
import com.wwzl.commonlib.api.HeaderConstans;
import com.wwzl.commonlib.db.DBConstants;
import com.wwzl.commonlib.db.SPUtils;
import com.wwzl.commonlib.gson.GsonHelper;
import com.wwzl.commonlib.log.LogDebug;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * retrofit  配置
 *
 * @author weibin.chen
 * @since 2019/2/13 15:50
 */
public class RetrofitClient {
    private static final String TAG = "RetrofitClient";
    //    private static final String API_URL = "http://11.1.0.222/";
    private static String API_URL = BuildConfig.DEBUG?"http://testwwhj.xianglianni.com/":"http://wwhj.xianglianni.com/";
    private static final int DEFAULT_TIMEOUT = 10;
    private static Retrofit retrofitManager;
    private static OkHttpClient httpClient;
    private static final String DEBUG = "debug";

    private static OkHttpClient getOkHttpClient() {
        if (httpClient == null) {
            httpClient = getOkHttpClientBuilder(DEFAULT_TIMEOUT);
        }
        return httpClient;
    }

    @MainThread
    public static Retrofit getRetrofit() {
        if (retrofitManager == null) {
            retrofitManager = getRetrofitBuild(API_URL, getOkHttpClient());
        }
        return retrofitManager;
    }

    @MainThread
    public static void setNewApiUrl(String apiUrl) {
        API_URL = apiUrl;
//        retrofitManager = getRetrofitBuild(API_URL, getOkHttpClient());
    }


    private static OkHttpClient getOkHttpClientBuilder(int timeout) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(timeout, TimeUnit.SECONDS)
                .readTimeout(timeout, TimeUnit.SECONDS)
                .writeTimeout(timeout, TimeUnit.SECONDS);
        if (BuildConfig.BUILD_TYPE.equals(DEBUG)) {
            // 添加log打印
            getHttplogInerceptor(builder);
        }
        Interceptor interceptor = getNetworkInterceptor();
        Interceptor baseUrlInterceptor = new MoreBaseUrlInterceptor();
        builder.addInterceptor(interceptor);
        builder.addInterceptor(baseUrlInterceptor);
        return builder.build();
    }

    @NonNull
    private static Retrofit getRetrofitBuild(String baseUrl, OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create(GsonHelper.getInstance().gson()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build();
    }

    private static void getHttplogInerceptor(OkHttpClient.Builder builder) {
        // 添加log打印
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(message ->
                LogDebug.d(TAG, " message = " + message));
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.addInterceptor(httpLoggingInterceptor);
    }

    @NonNull
    private static Interceptor getNetworkInterceptor() {
        return chain -> {
            Request request = chain.request();
            LogDebug.d(TAG, "--------intercept----------");
            Request.Builder requestBuilder = request.newBuilder();
            String needToken = request.header(HeaderConstans.HEADER_NEED_TOKEN);
            LogDebug.d(TAG, " needToken = " + needToken);
            if (TextUtils.isEmpty(needToken)) {
                // 不需要token
                LogDebug.d(TAG, "-------- 不需要token ----------");
            } else if (HeaderConstans.HEADER_NEED_TOKEN.equals(needToken)) {
                // 必须得有token
                LogDebug.d(TAG, "-------- 需要token ----------");
                requestBuilder.removeHeader(HeaderConstans.HEADER_NEED_TOKEN);
                // 获取缓存的 token
                String token = SPUtils.getString(DBConstants.TOKEN);
                if (TextUtils.isEmpty(token)) {
                    LogDebug.d(TAG, "-------- 没有token，需要到登录界面 ----------");
                } else {
                    requestBuilder.addHeader(HeaderConstans.AUTHORIZATION, token);
                }
                LogDebug.d(TAG, " token = " + token);
            }
            return chain.proceed(requestBuilder.build());
        };
    }


    public static class MoreBaseUrlInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            //获取原始的originalRequest
            Request originalRequest = chain.request();
            //获取老的url
            HttpUrl oldUrl = originalRequest.url();
            //获取originalRequest的创建者builder
            Request.Builder builder = originalRequest.newBuilder();
            HttpUrl baseURL=HttpUrl.parse(API_URL);
            //重建新的HttpUrl，需要重新设置的url部分
            HttpUrl newHttpUrl = oldUrl.newBuilder()
                    .scheme(baseURL.scheme())//http协议如：http或者https
                    .host(baseURL.host())//主机地址
                    .port(baseURL.port())//端口
                    .build();
            //获取处理后的新newRequest
            Request newRequest = builder.url(newHttpUrl).build();
            return  chain.proceed(newRequest);

        }
    }
}
