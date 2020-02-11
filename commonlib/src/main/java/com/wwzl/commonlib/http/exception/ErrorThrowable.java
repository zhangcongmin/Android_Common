package com.wwzl.commonlib.http.exception;

import com.google.gson.annotations.SerializedName;
import com.wwzl.commonlib.http.ReturnCode;

public class ErrorThrowable extends Throwable {
    @SerializedName("code")
    public int code;
    @SerializedName("message")
    public String message;

    public final Object data;

    public ErrorThrowable(@ReturnCode.Code int code, String message) {
        this(code, message, null);
    }

    public ErrorThrowable(@ReturnCode.Code int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
}
