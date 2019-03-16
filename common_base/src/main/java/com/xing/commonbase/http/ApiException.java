package com.xing.commonbase.http;

public class ApiException extends RuntimeException {

    private int errcode;
    private String errmsg;

    public ApiException() {

    }

    public ApiException(int code, String msg) {
        this.errcode = code;
        this.errmsg = msg;
    }

    public int getErrcode() {
        return errcode;
    }

    public void setErrcode(int errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    @Override
    public String toString() {
        return "ApiException{" +
                "errcode=" + errcode +
                ", errmsg='" + errmsg + '\'' +
                '}';
    }
}