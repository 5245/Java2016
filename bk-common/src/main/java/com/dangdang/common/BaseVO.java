/**  
 * 文件名：BaseVO <br>
 * 包名：com.tsinghuatec.framework.vo <br>
 * 版本信息：V1.0  <br>
 * 日期：2010-4-13  <br>
 * Copyright 同方电子 Corporation 2010<br>
 * 版权所有 北京同方电子科技有限公司
 *  
 */
package com.dangdang.common;

import java.io.Serializable;

/**
 * 项目名称：EAI-framework<br>
 * 包名：com.tsinghuatec.framework.vo <br>
 * 类名称：BaseVO <br>
 * 类描述：BaseVO封装vo所有vo通用属性,所有VO必须继承该类。<br>
 * &nbsp;&nbsp;VO定义为业务层与Action层数据通信对象，在本框架中不允许适用DO(hibernate映射模型)<br>
 * 作为业务层与Action层之间的数通信换对象，如果未使用DO作为前两者通信对象会出现事务异常，抛出异常信息<br>
 * session is close。 <br>
 * 主要方法：<br>
 * 1:getId 获得模型数据Id(一般为主键信息) <br>
 * 2:setId 保存模型数据Id(一般为主键信息) <br>
 ******************************************************<br>
 * 作者：gm <br>
 * 创建日期：2010-4-17 <br>
 * 公司：北京同方电子科技有限公司<br>
 * 版本：V1.0<br>
 *******************************************************<br>
 * 修改人：gm <br>
 * 修改时间：2010-4-17 下午07:28:54 <br>
 * 修改原因： 无<br>
 * 
 */
public abstract class BaseVO implements Serializable {
    /** serialVersionUID */

    private static final long serialVersionUID = 1L;
    /** 主键id. */
    private String            id;

    /**
     * 得到id信息(一般获得模型数据的主键信息).<br>
     * 返回String类型数据。<br>
     * 当前返回的该数据的参数名是 id
     * 
     * @return String类型
     * @since v1.0
     */

    public String getId() {
        return id;
    }

    /**
     * 保存id信息(一般保存模型主键信息).<br>
     * 传入类型为String的参数。<br>
     * 当前接收传入参数值得参数名为:id
     * 
     * @param id
     *            将类型为String且传入参数名为id的值保存
     */
    public void setId(String id) {
        this.id = id;
    }

}
