//package com.sxk.test;
//
//import java.sql.Connection;
//import java.sql.SQLException;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.concurrent.ConcurrentLinkedQueue;
//
//import javax.sql.DataSource;
//
//import com.alibaba.druid.pool.DruidDataSource;
//
//public class ConnectionUtils {
//    private static Map<String, DruidDataSource> idleNewMultiDBConnMap = new HashMap<>();
//
//    static {
//        // 每个分库i的连接池里放j=8个连接
//        for (int i = 0; i < 8; i++) {
//            DruidDataSource dataSource = new DruidDataSource();
//            dataSource.setUrl("");
//            dataSource.setUsername("");
//            dataSource.setPassword("");
//            setDataSourceBaseParams(dataSource);
//            idleNewMultiDBConnMap.put(String.valueOf(i), dataSource);
//        }
//    }
//
//    public static Connection getIdleConnectionFromPool(int dbIndex) {
//        
//        
//        
//        
//        return idleNewMultiDBConnMap.get("").getConnection();
//        
//        
//        
//        
//        
//        
//        
//        
//        
//        DataSource ds = new DruidDataSource();
//        ds.s
//        
//        
//        
//        
//        
//        Connection c=ds.getConnection();
//        c.close();
//        
//
//        Connection result = null;
//        String mapIndex = String.valueOf(dbIndex);
//        ConcurrentLinkedQueue<Connection> q = idleNewMultiDBConnMap.get(mapIndex);
//        while (result == null) {
//            try {
//                Thread.sleep(100L);
//            } catch (InterruptedException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//            result = q.poll();
//        }
//        return result;
//    }
//
//    //设置基本参数
//    private static void setDataSourceBaseParams(DruidDataSource dataSource) {
//        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
//        dataSource.setInitialSize(1);
//        dataSource.setMinIdle(1);
//        dataSource.setMaxActive(5);
//        dataSource.setMaxWait(30000);
//        dataSource.setTimeBetweenEvictionRunsMillis(6000);
//        dataSource.setMinEvictableIdleTimeMillis(300000);
//        dataSource.setValidationQuery("select 'x'");
//        dataSource.setTestWhileIdle(true);
//        dataSource.setTestOnBorrow(true);
//        dataSource.setTestOnReturn(false);
//        try {
//            dataSource.setFilters("stat");
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 释放连接池里的数据库连接
//     * @param dbIndex 分库的index
//     * @return
//     */
//    public static void releaseIdleConnectiontoPool(int dbIndex, Connection conn) {
//        String mapIndex = String.valueOf(dbIndex);
//        ConcurrentLinkedQueue<Connection> q = idleNewMultiDBConnMap.get(mapIndex);
//        q.offer(conn);
//    }
//
//}
