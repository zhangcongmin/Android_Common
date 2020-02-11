package com.wwzl.commonlib.http;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class ReturnCode {
    /**
     * 服务器错误码
     */
    //成功
    public static final int CODE_SUCCESS = 200;
    //失败
    public static final int CODE_ERROR = -1;
    //token过期
    public static final int CODE_TOKEN_EXPIRE = 401;
    //没绑定手机号
    public static final int CODE_UNBIND_PHONE = -1009;
    //接口不存在
    public static final int CODE_NOT =65792;

    /**
     * 本地错误码
     */
    //未知错误
    public static final int CODE_LOCAL_UNKNOWN_ERROR = 0x10100;
    //无网络状态
    public static final int CODE_LOCAL_NO_NETWORK_ERROR = 0x10101;
    //连接超时
    public static final int CODE_LOCAL_TIMEOUT_ERROR = 0x10102;


    @IntDef({CODE_SUCCESS, CODE_ERROR, CODE_TOKEN_EXPIRE, CODE_LOCAL_UNKNOWN_ERROR,
            CODE_LOCAL_NO_NETWORK_ERROR, CODE_LOCAL_TIMEOUT_ERROR})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Code {

    }
}
