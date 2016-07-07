package com.sxk.web.vo.response;

import java.io.Serializable;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import com.sxk.web.msg.ResponseInfoEnum;

/**
 * @description 返回errcode和msg 
 * @author sxk
 * @date 2016年7月7日
 */
public class SingleResult implements Serializable {

    private static final long serialVersionUID = 1L;
    private int               errcode;
    private String            msg;

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

    public SingleResult() {
        super();
    }

    public SingleResult(int errcode) {
        super();
        this.errcode = errcode;
    }

    public SingleResult(String msg) {
        super();
        this.errcode = ResponseInfoEnum.CORRECT.getRet();
        this.msg = msg;
    }

    public SingleResult(int errcode, String msg) {
        super();
        this.errcode = errcode;
        this.msg = msg;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

}
