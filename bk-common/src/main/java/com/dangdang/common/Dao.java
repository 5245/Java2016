/**
 * Dao 包含DAO层操作数据库的基本方法.
 */
package com.dangdang.common;

import java.sql.ResultSet;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Dao 包含DAO层操作数据库的基本方法.
 * 
 * @author gm
 * 
 */
public interface Dao {
    /**
     * 删除一个Bean对象.
     * 
     * @param o
     *            实体对象
     */
    void delete(Object o);

    /**
     * 删除一个集合内的所有对象
     * 
     * @param list
     *            对象集合
     */
    @SuppressWarnings("unchecked")
    void deleteAll(Collection list);

    /**
     * 根据对象id删除对象.
     * 
     * @param id
     *            实体对象id
     */
    void deleteById(String id);

    /**
     * 执行SQL语句获得Set结果,此SQL语句非HQL语句.
     * 
     * @param sql
     *            基本sql语句
     * 
     * @return Set集合
     */
    ResultSet executeQuery(String sql) throws Exception;

    /**
     * 查询所有信息.
     * 
     * @return 实体Bean List
     */
    @SuppressWarnings("unchecked")
    List findAll();

    /**
     * 根据某些属性条件查询.
     * 
     * @param map
     *            存放属性name-value 相对应的条件集.
     * @return 根据条件获得的所有数据集
     */
    @SuppressWarnings("unchecked")
    List findAllByProperties(Map<String, Object> map);

    /**
     * 根据属性名值进行查询.
     * 
     * @param propertyName
     *            属性名字：如id，username。。。
     * @param propertyValue
     *            属性值： 如11，'admin'。。。
     * @return 根据条件获得的所有数据集
     */
    @SuppressWarnings("unchecked")
    List findAllByProperty(String propertyName, Object propertyValue);

    /**
     * 依靠当前实体的id查找该实体.
     * 
     * @param idValue
     *            主键值
     * @return 该id实体对象
     */
    Object findById(final String idValue);

    /**
     * 根据属性名值进行查询.
     * 
     * @param propertyName
     *            属性名字：如id，username。。。
     * @param propertyValue
     *            属性值： 如11，'admin'。。。
     * @return 获得符合条件的数据的条数
     */
    int findColumnCount(String propertyName, Object propertyValue);

    /**
     * 分页查询.
     * 
     * @param entity
     *            要查询的实体bean.
     * @param map
     *            条件map
     * @param pageNo
     *            页号
     * @param pageSize
     *            一页查多少条 *
     * @param orderNames
     *            排序字段关键字集合
     * @param sc
     *            升序还是降序
     * @return Page对象
     */
    @SuppressWarnings("unchecked")
    public Page pagedQuery(Class entity, Map<String, Object> map, int pageNo, int pageSize, String[] orderNames, String[] sc);

    /**
     * 分页查询.
     * 
     * @param entity
     *            要查询的实体bean.
     * @param map
     *            条件map
     * @param pageNo
     *            页号
     * @param pageSize
     *            一页查多少条 *
     * @param orderName
     *            排序字段关键字
     * @return page
     */
    @SuppressWarnings("unchecked")
    Page pagedQuery(Class entity, Map<String, Object> map, int pageNo, int pageSize, String orderName);

    /**
     * 分页查询.
     * 
     * @param hql
     *            查询sql语句
     * @param pageNo
     *            页号
     * @param pageSize
     *            一页查多少条
     * @param args
     *            基本参数
     * @return Page对象
     */
    Page pagedQuery(String hql, int pageNo, int pageSize, Object[] args);

    /**
     * 新增一条信息.
     * 
     */
    void save(Object value);

    /**
     * 保存一组对象集合.
     * 
     * @param coll
     *            对象集合
     * 
     */
    @SuppressWarnings("unchecked")
    void saveAll(Collection coll);

    /**
     * 根据条件查询某个Bean.
     * 
     * @param strings
     *            可以传个数为n的任意数量参数。名值之间用英文的::=分开 例
     *            selectBSql("id::=11","name::='myName'");
     * @return 返回find(from 表 where id=11 and name='myName')的结果
     */
    @SuppressWarnings("unchecked")
    List selectBySqlCondition(String... strings);

    /**
     * 根据条件查询某个Bean.
     * 
     * @param strings
     *            可以传个数为n的任意数量参数。名值之间用英文的::=分开 例
     *            selectBSql("id::=11","name::='myName'");
     * @return 返回find(from 表 where id=11 or name='myName')的结果
     */
    @SuppressWarnings("unchecked")
    List selectBySqlConditionOr(String... strings);

    /**
     * 修改一条信息.
     */
    void update(Object value);

    /**
     * 保存或更新一个对象.
     * 
     * @param value
     */
    void saveOrUpdate(Object value);

    @SuppressWarnings("unchecked")
    List findAllByProperties(Map<String, Object> map, String[] orderNames, String[] orders);

    /**
     * 日期：2012-9-28<br>
     * 版本：v1.0<br>
     * 描述：flush(即时提交数据.) <br>
     * 主要方法：<br>
     * ===============================================<br>
     * 创建日期：2012-9-28 下午03:38:58 <br>
     * 创建人 gm<br>
     * ===============================================<br>
     * 修改日期：2012-9-28<br>
     * 修改人 gm<br>
     * 修改描述 flush(这里用一句话描述这个方法修改原因和作用) <br>
     * ===============================================<br>
     * void
     * 
     * @Exception 异常对象 <br>
     */
    void flush();

    /**
     * 	
     * 描述：find(执行sql语句) <br>	
     */
    List<Map> find(String sql);

    /**
     * 	
     * 描述：执行单条sql语句 <br>	
     */
    void execSql(String sql);

    /**
     * 	
     * 描述：批量执行sql语句
     */
    void execSql(List<String> sqlList);

    /**
     * 	
     * 描述：执行带返回参数的存储过程
     */
    String execProcedureAndResult(BaseProcedureVO baseProcedureVO);

    /**
     * 	
     * 描述：执行存储过程
     */
    void execProcedure(BaseProcedureVO baseProcedureVO);
}
