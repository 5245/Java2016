package com.sxk.web.vo.response;

import java.io.Serializable;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

/**
 * @description 基础resopnse
 * @author sxk
 * @email sxk5245@126.com
 * @date 2016年6月5日
 */
public class BaseResult implements Serializable {
    private static final long serialVersionUID = 8074093711829201217L;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
