package com.grevoltec.cosechaguiddins.util;

import android.annotation.SuppressLint;

public class AppException extends  Exception {

    public static final int ERROR_DUPLICATE = 10;

    private int code = -1;

    public AppException() {
    }

    public AppException(String message) {
        super(message);
    }

    public AppException(String message, Throwable cause) {
        super(message, cause);
    }

    public AppException(Throwable cause) {
        super(cause);
    }

    @SuppressLint("NewApi")
    public AppException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public int getCode() {
        return code;
    }

    public AppException setCode(int code) {
        this.code = code;
        return this;
    }
}
