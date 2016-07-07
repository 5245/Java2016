package com.sxk.web.vo;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

/**
 * @description 封装所有vo的通用属性,所有vo需要继承该类
 * @author sxk
 * @date 2016年7月7日
 */
public abstract class BaseVO {
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BaseVO() {
        super();
    }

    public BaseVO(int id) {
        super();
        this.id = id;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
