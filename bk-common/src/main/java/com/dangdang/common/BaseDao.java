/**
 * BaseDao 包含DAO层操作数据库的基本方法.
 */
package com.dangdang.common;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate5.SessionFactoryUtils;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

/**
 * BaseDao 实现Dao方法 <br>
 * BaseDao 包含DAO层操作数据库的基本方法.
 * 
 * @author gm
 * 
 */
public abstract class BaseDao extends HibernateDaoSupport implements Dao {

    /** loggger. */
    private static final Logger logger   = LoggerFactory.getLogger(BaseDao.class);

    /** 当前Dao所要操作的Bean的class地址. */
    protected String            className;

    private static final String DB_ERROR = "db error:";

    /**
     * 删除一个Bean对象.
     * 
     * @param o
     *            实体对象
     */
    public void delete(Object o) {
        logger.debug("开始删除一个实体对象!");
        this.getHibernateTemplate().delete(o);
        logger.debug("结束删除一个实体对象!");

    }

    /**
     * 删除一个集合内的所有对象
     * 
     * @param list
     *            对象集合
     */
    @SuppressWarnings("unchecked")
    public void deleteAll(Collection list) {
        logger.debug("开始删除一组实体对象!");
        this.getHibernateTemplate().deleteAll(list);
        logger.debug("结束删除一组实体对象!");
    }

    /**
     * 依靠id删除一个实体对象.
     * 
     * @param id
     *            实体对象的id
     */
    public void deleteById(String id) {
        Object o = this.findById(id);
        if (null == o) {
            throw new HibernateException("This Object by id is " + id + " doesnot exist");
        }
        this.getHibernateTemplate().delete(o);

    }

    /**
     * 执行SQL语句获得Set结果,此SQL语句非HQL语句.
     * 
     * @param sql
     *            基本sql语句
     * 
     * @return Set集合
     */
    @SuppressWarnings("deprecation")
    public ResultSet executeQuery(String sql) throws Exception {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
        } catch (Exception e) {
            throw new Exception(this.getClass() + " execute " + sql + " ERROR!");
        } finally {
            try {
                if (null != rs)
                    rs.close();
                if (null != stmt)
                    stmt.close();
                if (null != conn)
                    conn.close();
            } catch (SQLException e) {
                logger.error(DB_ERROR, e);
            }
        }
        return rs;
    }

    /**
     * 查询所有信息.
     * 
     * @return 实体Bean List
     */
    @SuppressWarnings("unchecked")
    public List findAll() {
        return this.getHibernateTemplate().find("select acc from " + className + " as acc");
    }

    /**
     * 根据某些属性条件查询.
     * 
     * @param map
     *            存放属性name-value 相对应的条件集.
     * @return 根据条件获得的所有数据集
     */
    @SuppressWarnings("unchecked")
    public List findAllByProperties(Map<String, Object> map) {
        String hql = "select acc from " + className.substring(className.lastIndexOf(".") + 1) + " as acc" + " where 1=1 ";
        String[] names = new String[map.size()];
        Object[] values = new Object[map.size()];
        int i = 0;
        for (String str : map.keySet()) {
            hql = hql + " and acc." + str + "=:" + str;
            names[i] = str;
            values[i] = map.get(str);
            i++;
        }
        return this.getHibernateTemplate().findByNamedParam(hql, names, values);
    }

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
    public List findAllByProperty(final String propertyName, Object propertyValue) {

        String hql = "select acc from " + className.substring(className.lastIndexOf(".") + 1) + " as acc" + " where acc." + propertyName + "=:" + propertyName;
        return this.getHibernateTemplate().findByNamedParam(hql, propertyName, propertyValue);
    }

    /**
     * 依靠当前实体的id查找该实体.
     * 
     * @param idValue
     *            主键值
     * @return 该id实体对象
     */
    public Object findById(final String idValue) {
        return this.getHibernateTemplate().get(className, new String(idValue));
    }

    /**
     * 根据属性名值进行查询.
     * 
     * @param propertyName
     *            属性名字：如id，username。。。
     * @param propertyValue
     *            属性值： 如11，'admin'。。。
     * @return 获得符合条件的数据的条数
     */
    public int findColumnCount(String propertyName, Object propertyValue) {
        return this.findAllByProperty(propertyName, propertyValue).size();
    }

    /**
     * @return the className
     */
    public String getClassName() {
        return className;
    }

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
    public Page pagedQuery(Class entity, Map<String, Object> map, int pageNo, int pageSize, String[] orderNames, String[] sc) {
        StringBuffer hql = new StringBuffer();
        hql.append(" from ");
        hql.append(entity.getName() + " as acc ");
        Object[] args = null;
        if (null != map && 0 < map.size()) {
            hql.append(" where ");
            args = new Object[map.size()];
            Iterator it = map.keySet().iterator();
            int i = 0;
            while (it.hasNext()) {
                String columnKey = (String) it.next();
                Object value = map.get(columnKey);
                hql.append(" acc." + columnKey + " = ? ");
                if (it.hasNext()) {
                    hql.append(" and ");
                }
                args[i] = value;
                i++;
            }
        }
        String orderName = "";
        if (null != orderNames && 0 < orderNames.length) {
            for (int i = 0; i < orderNames.length; i++) {
                orderName += ",acc." + orderNames[i] + " " + sc[i];
            }
            orderName = orderName.substring(1);
            hql.append(" order by " + orderName);
        }
        Page page = this.pagedQueryP(hql.toString(), pageNo, pageSize, args);
        if (null != page) {
            return page;
        }
        return null;
    }

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
     * @return Page对象
     */
    @SuppressWarnings("unchecked")
    public Page pagedQuery(Class entity, Map<String, Object> map, int pageNo, int pageSize, String orderName) {
        StringBuffer hql = new StringBuffer();
        hql.append("from ");
        hql.append(entity.getName() + " as acc");
        Object[] args = null;
        if (null != map && 0 < map.size()) {
            hql.append(" where ");
            args = new Object[map.size()];
            Iterator it = map.keySet().iterator();
            int i = 0;
            while (it.hasNext()) {
                String columnKey = (String) it.next();
                Object value = map.get(columnKey);
                hql.append(" acc." + columnKey + " = ? ");
                if (it.hasNext()) {
                    hql.append(" and ");
                }
                args[i] = value;
                i++;
            }
        }
        if (null != orderName && 0 < orderName.length()) {
            hql.append(" order by acc." + orderName);
        }
        Page page = this.pagedQueryP(hql.toString(), pageNo, pageSize, args);
        if (null != page) {
            return page;
        }
        return null;
    }

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
    @SuppressWarnings("unchecked")
    private Page pagedQueryP(String hql, int pageNo, int pageSize, Object[] args) {
        List list = null;
        // 查询query
        Query query = this.getSessionFactory().getCurrentSession().createQuery("select acc " + hql);
        if (null != args && 0 < args.length) {
            // 设置查询参数 顺序放入
            for (int i = 0; i < args.length; ++i) {
                query.setParameter(i, args[i]);
            }
        }
        // 统计查询表的信息条数.
        String countQueryString = " select count (acc) " + removeSelect(removeOrders(hql));
        List countlist = getHibernateTemplate().find(countQueryString, args);
        long totalCount = ((Long) countlist.get(0)).longValue();
        if (totalCount < 1) {
            // 如果信息条数小于1,则返回null
            // return null;
        }
        if (1 > pageNo) {
            pageNo = 1;
        }
        // 分页信息
        Page page = new Page();
        // 当前页
        page.setCurrentPage(pageNo);
        // 一页显示信息条数
        page.setPageSize(pageSize);
        // 信息总条数
        page.setTotalCount(totalCount);
        // 如果页号大于最大页号 取得最大页号值
        if (pageNo > page.getPages()) {
            pageNo = page.getPages();
        }
        if (0 >= totalCount) {
            page.setResult(new ArrayList());
            return page;
        }
        int startIndex = (pageNo - 1) * pageSize;
        query.setFirstResult(startIndex).setMaxResults(pageSize);
        list = query.list();
        // 当前结果
        page.setResult(list);
        return page;
    }

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
    @SuppressWarnings("unchecked")
    public Page pagedQuery(String hql, int pageNo, int pageSize, Object[] args) {
        List list = null;
        // 查询query
        Query query = this.getSessionFactory().getCurrentSession().createQuery(hql);
        if (null != args && 0 < args.length) {
            // 设置查询参数 顺序放入
            for (int i = 0; i < args.length; ++i) {
                query.setParameter(i, args[i]);
            }
        }
        // 统计查询表的信息条数.
        String countQueryString = " select count (*) " + removeSelect(removeOrders(hql));
        List countlist = getHibernateTemplate().find(countQueryString, args);
        long totalCount = ((Long) countlist.get(0)).longValue();
        if (totalCount < 1) {
            // 如果信息条数小于1,则返回null
            // return null;
        }
        if (1 > pageNo) {
            pageNo = 1;
        }
        // 分页信息
        Page page = new Page();
        // 当前页
        page.setCurrentPage(pageNo);
        // 一页显示信息条数
        page.setPageSize(pageSize);
        // 信息总条数
        page.setTotalCount(totalCount);
        // 如果页号大于最大页号 取得最大页号值
        if (pageNo > page.getPages()) {
            pageNo = page.getPages();
        }
        if (0 >= totalCount) {
            page.setResult(new ArrayList());
            return page;
        }
        int startIndex = (pageNo - 1) * pageSize;
        query.setFirstResult(startIndex).setMaxResults(pageSize);
        list = query.list();
        // 当前结果
        page.setResult(list);
        return page;
    }

    /**
     * 清除sql中order以后数据.
     * 
     * @param hql
     * @return
     */
    private String removeOrders(String hql) {
        Pattern p = Pattern.compile("ORDER\\s*by[\\w|\\W|\\s|\\S]*", 2);
        Matcher m = p.matcher(hql);
        StringBuffer sb = new StringBuffer();
        while (m.find())
            m.appendReplacement(sb, "");

        m.appendTail(sb);
        return sb.toString();
    }

    private String removeSelect(String hql) {
        int beginPos = hql.toLowerCase().indexOf("from");
        return hql.substring(beginPos);
    }

    /**
     * 保存一个实体对象.
     * 
     * @param o
     *            保存的实体对象
     */
    public void save(Object o) {
        this.getHibernateTemplate().save(o);
        // this.getHibernateTemplate().flush();
    }

    /**
     * 保存或更新一个实体对象:当实体对象在数据库存在时更新，不存在则保存.<br>
     * 
     * @param o
     *            保存的实体对象
     */
    public void saveOrUpdate(Object o) {
        this.getSessionFactory().getCurrentSession().merge(o);
    }

    /**
     * 保存一组对象集合.
     * 
     * @param coll
     *            对象集合
     * 
     */
    @SuppressWarnings("unchecked")
    public void saveAll(Collection coll) {
        for (Iterator it = coll.iterator(); it.hasNext();) {
            Object o = it.next();
            this.save(o);
        }
        // this.getHibernateTemplate().flush();
    }

    /**
     * 根据条件查询某个Bean.
     * 
     * @param strings
     *            可以传个数为n的任意数量参数。名值之间用英文的::=分开 例
     *            selectBSql("id::=11","name::='myName'");
     * @return 返回find(from 表 where id=11 and name='myName')的结果
     */
    @SuppressWarnings("unchecked")
    public List selectBySqlCondition(String... strings) {
        String sql = "select acc from " + className.substring(className.lastIndexOf(".") + 1) + " as acc";
        sql += "where 1 = 1";
        for (String str : strings) {
            sql += " and acc." + str.split("::=")[0] + "=" + str.split("::=")[1];
        }
        return this.getHibernateTemplate().find(sql);
    }

    /**
     * 根据条件查询某个Bean.
     * 
     * @param strings
     *            可以传个数为n的任意数量参数。名值之间用英文的::=分开 例
     *            selectBSql("id::=11","name::='myName'");
     * @return 返回find(from 表 where id=11 or name='myName')的结果
     */
    @SuppressWarnings("unchecked")
    public List selectBySqlConditionOr(String... strings) {
        String sql = "select acc from " + className.substring(className.lastIndexOf(".") + 1) + " as acc";
        sql += "where ";
        String a = "";
        for (String str : strings) {
            a += " or acc." + str.split("::=")[0] + "=" + str.split("::=")[1];
        }
        a = a.substring(3);
        sql += a;
        return this.getHibernateTemplate().find(sql);
    }

    /**
     * @param className
     *            the className to set
     * @throws ClassNotFoundException
     *             配置的class地址错误，无法创建dao
     */
    public void setClassName(String className) throws ClassNotFoundException {
        this.className = className;
    }

    /**
     * 更新一个实体对象.
     * 
     * @param o
     *            要更新的实体对象
     * 
     */
    public void update(Object o) {
        this.getHibernateTemplate().update(o);
        // this.getHibernateTemplate().flush();
    }

    /**
     * 根据某些属性条件查询.
     * 
     * @param map
     *            存放属性name-value 相对应的条件集.
     * @return 根据条件获得的所有数据集
     */
    @SuppressWarnings("unchecked")
    public List findAllByProperties(Map<String, Object> map, String[] orderNames, String[] orders) {
        String hql = "select acc from " + className.substring(className.lastIndexOf(".") + 1) + " as acc " + " where 1=1 ";
        String[] names = new String[map.size()];
        Object[] values = new Object[map.size()];
        int i = 0;
        for (String str : map.keySet()) {
            hql = hql + " and acc." + str + "=:" + str;
            names[i] = str;
            values[i] = map.get(str);
            i++;
        }
        if (orders != null && orders.length > 0) {
            hql += " order by ";
            for (int j = 0; j < orders.length; j++) {
                hql += " acc." + orderNames[j] + " " + orders[j] + " ,";
            }
            // hql+=orders[orders.length-1];
            hql = hql.substring(0, hql.length() - 1);
        }
        return this.getHibernateTemplate().findByNamedParam(hql, names, values);
    }

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
    public void flush() {
        super.getSessionFactory().getCurrentSession().flush();
    }

    /**
     * 
     * 日期：2012-11-27<br>
     * 版本：v1.0<br>
     * 描述：executeProcedure(这里用一句话描述这个方法的作用) <br>
     * 主要方法：<br>
     * ===============================================<br>
     * 创建日期：2012-11-27 下午05:31:29 <br>
     * 创建人 李厚生<br>
     * ===============================================<br>
     * 修改日期：2012-11-27<br>
     * 修改人 李厚生<br>
     * 修改描述 executeProcedure(这里用一句话描述这个方法修改原因和作用) <br>
     * ===============================================<br>
     * 
     * @param dqmProcedureVO
     *            void
     * @Exception 异常对象 <br>
     */
    public void execProcedure(BaseProcedureVO baseProcedureVO) {

        Connection conn = null;
        CallableStatement call = null;

        try {
            // 得到数据库连接
            conn = SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
            // 得到存储过程名称
            String proName = baseProcedureVO.getProcedureName();
            // 得到存储过程的参数
            String args[] = baseProcedureVO.getParam();
            // 存储过程执行语句
            String procedureSql = "";

            if (null != args && 0 < args.length) {
                procedureSql = "{Call " + proName.toUpperCase() + "(";
                for (int i = 0; i < args.length; i++) {
                    procedureSql += "?,";

                }
                procedureSql = procedureSql.substring(0, procedureSql.length() - 1) + ")} ";

                call = conn.prepareCall(procedureSql);
                for (int i = 0; i < args.length; i++) {
                    call.setString(i + 1, args[i]);
                }
                call.executeUpdate();
            } else {
                procedureSql = "{Call " + proName.toUpperCase() + "}";
                call = conn.prepareCall(procedureSql);
                call.executeUpdate();
            }
        } catch (SQLException e1) {
            logger.error(DB_ERROR, e1);
        } catch (Exception e) {
            logger.error(DB_ERROR, e);
        } finally {
            try {
                if (null != call)
                    call.close();
                if (null != conn)
                    conn.close();
            } catch (SQLException e) {
                logger.error(DB_ERROR, e);
            }
        }

    }

    /**
     * 
     * 日期：2012-11-29<br>
     * 版本：v1.0<br>
     * 描述：execProcedureAndResult(适用于只有一个返回参数的存储过程，并且返回参数在最后一个位置) <br>
     * 主要方法：<br>
     * ===============================================<br>
     * 创建日期：2012-11-29 上午09:10:29 <br>
     * 创建人 李厚生<br>
     * ===============================================<br>
     * 修改日期：2012-11-29<br>
     * 修改人 李厚生<br>
     * 修改描述 execProcedureAndResult(这里用一句话描述这个方法修改原因和作用) <br>
     * ===============================================<br>
     * 
     * @param baseProcedureVO
     * @return String
     * @Exception 异常对象 <br>
     */
    public String execProcedureAndResult(BaseProcedureVO baseProcedureVO) {
        String result = null;
        Connection conn = null;
        CallableStatement call = null;

        try {
            // 得到数据库连接
            conn = SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
            // 得到存储过程名称
            String proName = baseProcedureVO.getProcedureName();
            // 得到存储过程的参数
            String args[] = baseProcedureVO.getParam();
            // 存储过程执行语句
            String procedureSql = "";

            if (null != args && 0 < args.length) {
                procedureSql = "{Call " + proName.toUpperCase() + "(";
                for (int i = 0; i < args.length; i++) {
                    procedureSql += "?,";

                }
                procedureSql += "?)} ";

                call = conn.prepareCall(procedureSql);
                for (int i = 0; i < args.length; i++) {
                    call.setString(i + 1, args[i]);
                }
                call.registerOutParameter(args.length + 1, Types.VARCHAR);// 传出参数
                call.executeUpdate();
                result = call.getString(args.length + 1);
            } else {
                procedureSql = "{Call " + proName.toUpperCase() + "}";
                call = conn.prepareCall(procedureSql);
                call.executeUpdate();
            }
        } catch (SQLException e1) {
            logger.error(DB_ERROR, e1);
        } catch (Exception e) {
            logger.error(DB_ERROR, e);
        } finally {
            try {
                if (null != call)
                    call.close();
                if (null != conn)
                    conn.close();
            } catch (SQLException e) {
                logger.error(DB_ERROR, e);
            }
        }
        return result;
    }

    /**
     * 
     * 日期：2012-11-29<br>
     * 版本：v1.0<br>
     * 描述：execSql(批量执行sql语句) <br>
     * 主要方法：<br>
     * ===============================================<br>
     * 创建日期：2012-11-29 下午04:39:45 <br>
     * 创建人 李厚生<br>
     * ===============================================<br>
     * 修改日期：2012-11-29<br>
     * 修改人 李厚生<br>
     * 修改描述 execSql(这里用一句话描述这个方法修改原因和作用) <br>
     * ===============================================<br>
     * 
     * @param sqlList
     *            void
     * @Exception 异常对象 <br>
     */
    public void execSql(List<String> sqlList) {
        Connection conn = null;
        Statement stmt = null;
        if (null != sqlList && sqlList.size() > 0) {
            try {
                // 得到数据库连接
                conn = SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
                stmt = conn.createStatement();
                for (String sql : sqlList) {
                    stmt.addBatch(sql);
                    logger.error(sql);
                    // System.out.println(sql);
                }
                stmt.executeBatch();
            } catch (SQLException e) {
                logger.error(DB_ERROR, e);
            } finally {
                try {
                    if (null != stmt)
                        stmt.close();
                    if (null != conn)
                        conn.close();
                } catch (SQLException e) {
                    logger.error(DB_ERROR, e);
                }
            }
        }
    }

    /**
     * 
     * 日期：2012-11-29<br>
     * 版本：v1.0<br>
     * 描述：execSql(执行单条sql语句) <br>
     * 主要方法：<br>
     * ===============================================<br>
     * 创建日期：2012-11-29 下午04:43:21 <br>
     * 创建人 李厚生<br>
     * ===============================================<br>
     * 修改日期：2012-11-29<br>
     * 修改人 李厚生<br>
     * 修改描述 execSql(这里用一句话描述这个方法修改原因和作用) <br>
     * ===============================================<br>
     * 
     * @param sql
     *            void
     * @Exception 异常对象 <br>
     */
    public void execSql(String sql) {
        Connection conn = null;
        Statement stmt = null;
        if (null != sql && 0 < sql.length()) {
            try {
                // 得到数据库连接
                conn = SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
                stmt = conn.createStatement();
                stmt.execute(sql);
            } catch (SQLException e) {
                logger.error(DB_ERROR, e);
            } finally {
                try {
                    if (null != stmt)
                        stmt.close();
                    if (null != conn)
                        conn.close();
                } catch (SQLException e) {
                    logger.error(DB_ERROR, e);
                }
            }
        }
    }

    /**
     * 
     * 日期：2012-12-2<br>
     * 版本：v1.0<br>
     * 描述：find(执行sql语句查询) <br>
     * 主要方法：<br>
     * ===============================================<br>
     * 创建日期：2012-12-2 下午12:20:04 <br>
     * 创建人 李厚生<br>
     * ===============================================<br>
     * 修改日期：2012-12-2<br>
     * 修改人 李厚生<br>
     * 修改描述 find(这里用一句话描述这个方法修改原因和作用) <br>
     * ===============================================<br>
     * 
     * @param sql
     * @return List<Map>
     * @Exception 异常对象 <br>
     */
    public List<Map> find(String sql) {
        Connection conn = null;
        Statement sm = null;
        ResultSet rs = null;
        List list = new LinkedList();
        try {
            // 得到数据库连接
            conn = SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
            sm = conn.createStatement();
            rs = sm.executeQuery(sql);
            int list_index = 0;
            ResultSetMetaData rsmd = rs.getMetaData();
            int columncount = rsmd.getColumnCount();
            while (rs.next()) {
                Map map = new HashMap();
                for (int i = 0; i < columncount; i++) {
                    map.put(rsmd.getColumnName(i + 1), rs.getObject(i + 1));
                }
                list.add(list_index, map);
                list_index++;
            }

        } catch (SQLException e) {
            logger.error(DB_ERROR, e);
        } finally {
            try {
                if (null != rs)
                    rs.close();
                if (null != sm)
                    sm.close();
                if (null != conn)
                    conn.close();
            } catch (SQLException e) {
                logger.error(DB_ERROR, e);
            }
        }
        return list;
    }

}
