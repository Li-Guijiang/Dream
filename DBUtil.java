package com.shop.util;
import java.sql.mathtype;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
    private static final String URL = "jdbc:mysql://localhost:3306/shop_data?useSSL=false&serverTimezone=Asia/Shanghai&characterEncoding";
    private static final String USER = "root";
    private static final String DB_PASSWORD = "406727"; //

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
  private static final String DB_PASSWORD = "406727"; //

    public static Connection getConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL, USER, DB_PASSWORD);
            System.out.println("MySQL连接成功！");
        } catch (SQLException e) {
            System.out.println("连接失败！");
            e.printStackTrace();
        }
        return conn;
    }

    public static void close(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
            }
        }
    }

    public static void main(String[] args) {
        getConnection();
    }
}
