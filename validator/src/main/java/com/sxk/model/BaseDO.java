package com.sxk.model;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

/**
 * @description 封装所有model的通用属性,所有model需要继承该类
 * @author sxk
 * @date 2016年7月7日
 */
public abstract class BaseDO {

    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BaseDO() {
        super();
    }

    public BaseDO(int id) {
        super();
        this.id = id;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
