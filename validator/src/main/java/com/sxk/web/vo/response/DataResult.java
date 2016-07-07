package com.sxk.web.vo.response;

import com.sxk.web.msg.ResponseInfoEnum;

/**
 * @description 返回errcode和data 
 * @author sxk
 * @date 2016年7月7日
 *
 */
public class DataResult<T> extends BaseResult {
    private static final long serialVersionUID = 1L;
    private int               errcode;
    private T                 data;

    public int getErrcode() {
        return errcode;
    }

    public void setErrcode(int errcode) {
        this.errcode = errcode;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public DataResult(T data) {
        super();
        this.errcode = ResponseInfoEnum.CORRECT.getRet();
        this.data = data;
    }

    public DataResult(int errcode, T data) {
        super();
        this.errcode = errcode;
        this.data = data;
    }

}
