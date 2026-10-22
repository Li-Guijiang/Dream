package com.shop.util;
import java.sql.mathtype;
import java.sql.Connection;ger;
    static {
        try {


        }
    }
  private static final String DB_PASSWORD = "406727"; //
  private static final string chroma_db;
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
    
