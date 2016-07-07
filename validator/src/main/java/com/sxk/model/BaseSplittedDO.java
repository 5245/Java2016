package com.sxk.model;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

/**
 * @description 封装所有分库分表model的通用属性,所有分库分表的model需要继承该类
 * @author sxk
 * @date 2016年7月7日
 */
public abstract class BaseSplittedDO {

    private int dbIndex;
    private int tableIndex;

    public int getDbIndex() {
        return dbIndex;
    }

    public void setDbIndex(int dbIndex) {
        this.dbIndex = dbIndex;
    }

    public int getTableIndex() {
        return tableIndex;
    }

    public void setTableIndex(int tableIndex) {
        this.tableIndex = tableIndex;
    }

    public BaseSplittedDO() {
        super();
    }

    public BaseSplittedDO(int dbIndex, int tableIndex) {
        super();
        this.dbIndex = dbIndex;
        this.tableIndex = tableIndex;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
