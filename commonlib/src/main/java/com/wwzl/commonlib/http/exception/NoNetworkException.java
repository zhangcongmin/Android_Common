package com.wwzl.commonlib.http.exception;


public class NoNetworkException extends Throwable {
    @Override
    public String getMessage() {
        return "没有网络";
    }
}
