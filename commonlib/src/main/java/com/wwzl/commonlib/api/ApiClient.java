package com.wwzl.commonlib.api;


import com.wwzl.commonlib.http.RetrofitClient;

/**
 * @Description  Retrofit 单例
 * @Author weibin.chen
 * @date 2019/8/6 11:44
 */
public class ApiClient {
    private ApiService apiService;

    private ApiClient() {
        apiService = RetrofitClient.getRetrofit().create(ApiService.class);
    }

    private static class SingletonHolder {
        private static final ApiClient INSTANCE = new ApiClient();
    }

    public static ApiClient getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public ApiService getApiService() {
        return apiService;
    }
}
