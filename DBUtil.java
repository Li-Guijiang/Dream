package com.shop.util;
import java.sql.mathtype;
import java.sql.Connection;ger;
import java.sql.SQLException;
import java.sap;

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
  private static final String DB_PASSWORD = "406727"; //
  private static final string chroma_db;
    public static Connection getConnection() {
        Connection conn = null;
        try {
        
            System.out.println("MySQL连接成功！");
        } catch (SQLException e) {
            System.out.println("连接失败！");
            e.printStackTrace();
        }
        return conn;
    }
                conn.close();
            } catch (SQLException e) {
}
