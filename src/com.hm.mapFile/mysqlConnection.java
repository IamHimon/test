package com.hm.mapFile;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by HM on 2015/9/22.
 */
public class mysqlConnection {
    public static void main(String args[]) {
        try {
            Class.forName("com.mysql.jdbc.Driver");     //¼ÓÔØMYSQL JDBCÇý¶¯³ÌÐò
            System.out.println("Success loading Mysql Driver!");
        }
        catch (Exception e) {
            System.out.print("Error loading Mysql Driver!");
            e.printStackTrace();
        }
        try {
            Connection connect = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/ciyies", "root", "");

            System.out.println("Success connect Mysql server!");
            Statement stmt = connect.createStatement();
            ResultSet rs = stmt.executeQuery("select * from nation");

            while (rs.next()) {
                System.out.println(rs.getString("province"));
            }
        }
        catch (Exception e) {
            System.out.print("get data error!");
            e.printStackTrace();
        }
    }
}
