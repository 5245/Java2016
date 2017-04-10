/**  
* 文件名：BaseProcedureVO.java <br>
* 包名：com.unistec.framework.dqm.dbconfig.vo <br>
* 版本信息：V1.0  <br>
* 日期：2012-11-27  <br>
* Copyright 紫光同软 Corporation 2012<br>
* 版权所有 紫光同软（北京）信息技术有限公司
*  
*/
package com.dangdang.common;

/**  
 * 项目名称：EAI-framework<br>
 * 包名：com.tsinghuatec.framework.vo <br>
 * 类名称：BaseProcedureVO  <br>
 * 类描述：简单描述该类的主要功能<br>
 ******************************************************<br>
 * 主要方法：<br>
 *   描述各个方法用途  <br>
 *   描述各个方法用途  <br>
 ******************************************************<br>
 * 作者：李厚生  <br>
 * 创建日期：2012-11-27 <br>
 * 公司：紫光同软（北京）信息技术有限公司<br>
 * 版本：V1.0<br>
 *******************************************************<br>
 * 修改人：李厚生  <br>
 * 修改时间：2012-11-27 下午04:40:24  <br>
 * 修改原因：  描述修改原因和用途<br>
 *   
 */
public class BaseProcedureVO {

    /** 存储过程名称 */
    String procedureName;

    /** 存储过程参数 */
    String param[];

    /**  
     * 返回String类型数据。<br>
     * 当前返回的该数据的参数名是 procedureName
     *  
     * @return  String类型 
     * @since   v1.0  
     */

    public String getProcedureName() {
        return procedureName;
    }

    /**  
     * 传入类型为String的参数。<br>
     * 当前接收传入参数值得参数名为:procedureName
     * @param procedureName 
     *      将类型为String且传入参数名为procedureName的值保存 
     */
    public void setProcedureName(String procedureName) {
        this.procedureName = procedureName;
    }

    /**  
     * 返回String[]类型数据。<br>
     * 当前返回的该数据的参数名是 param
     *  
     * @return  String[]类型 
     * @since   v1.0  
     */

    public String[] getParam() {
        return param;
    }

    /**  
     * 传入类型为String[]的参数。<br>
     * 当前接收传入参数值得参数名为:param
     * @param param 
     *      将类型为String[]且传入参数名为param的值保存 
     */
    public void setParam(String[] param) {
        this.param = param;
    }

}
