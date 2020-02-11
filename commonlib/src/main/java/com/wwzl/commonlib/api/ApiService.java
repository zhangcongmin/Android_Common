package com.wwzl.commonlib.api;


import com.wwzl.commonlib.bean.BaseResponse;

import java.util.Map;
import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * @Description  API 接口
 * @Author weibin.chen
 * @date 2019/8/6 11:44
 */
public interface ApiService {
    /**
     * 登录
     */
    @POST("login")
    Observable<BaseResponse<?>> login(@Body Map<String, String> params);

    /**
     * 退出登录
     */
    @Headers(HeaderConstans.HEADER_NEED_TOKEN)
    @DELETE("logOut")
    Observable<BaseResponse<String>> logOut();

    /**
     * 登录
     */
    @GET("test")
    Observable<BaseResponse<String>> test();
}
