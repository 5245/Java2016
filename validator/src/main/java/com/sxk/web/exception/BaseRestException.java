package com.sxk.web.exception;

import com.sxk.web.msg.ResponseInfoEnum;

public class BaseRestException extends RuntimeException {
    private static final long serialVersionUID = -5309574770175613338L;

    private int               errcode          = ResponseInfoEnum.FAIL.getRet();
    private String            msg;

    public BaseRestException() {
        super();
    }

    public BaseRestException(Throwable cause) {
        super(cause);
    }

    public BaseRestException(String msg) {
        this.errcode = ResponseInfoEnum.FAIL.getRet();
        this.msg = msg;
    }

    public BaseRestException(int errcode, String msg) {
        this.errcode = errcode;
        this.msg = msg;
    }

    public int getErrcode() {
        return errcode;
    }

    public void setErrcode(int errcode) {
        this.errcode = errcode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "BaseRestException [errcode=" + errcode + ", msg=" + msg + "]";
    }

}
