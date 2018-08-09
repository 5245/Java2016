package com.sxk.common.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.Arrays;

public class TestInsertOnduplicate {

    public static void main(String[] args) throws Exception {

        String dbUrl = "jdbc:mysql://localhost:3306/test";
        String dbUser = "root";
        String dbPassword = "0o0o0";
        long start = System.currentTimeMillis();
        Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        String insertSql = "insert into user (mobileNo,password,userName) "//
                //+ "values (?,?,?) ON DUPLICATE KEY UPDATE userName = VALUES(userName)";
                + "values (?,?,?) ON DUPLICATE KEY UPDATE update_time = ?";
        PreparedStatement insertps = connection.prepareStatement(insertSql);

        int i = 0;
        insertps.setString(1, "mobile_" + i);
        insertps.setString(2, "pass_" + i);
        insertps.setString(3, "name_" + 1);
        insertps.setTimestamp(4, new Timestamp(System.currentTimeMillis()));

        int affectRows = insertps.executeUpdate();
        System.out.println("affectRows:" + affectRows);


        insertps.close();
        connection.close();


    }
}
