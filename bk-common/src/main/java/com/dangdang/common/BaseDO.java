/**
 * BaseDO封装hibernate所有do通用属性,所有do必须继承该类.
 */
package com.dangdang.common;

import java.io.Serializable;

/**
 * BaseDO封装hibernate所有do通用属性,所有do必须继承该类.
 * 
 * @author gm
 * 
 */
public abstract class BaseDO implements Serializable {
    /** . */
    private static final long serialVersionUID = 1L;

    /** 主键id. */
    private String            id;

    /**
     * 初始化实现id生成.
     * 
     */
    public BaseDO() {
        super();
        this.id = IdGenerator.createId();
    }

    /**
     * @return Returns the id.
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     *            The id to set.
     */
    public void setId(String id) {
        this.id = id;
    }

}
