package com.shop.service;

import com.shop.util.DBUtil;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

// 数据二次提取类：从MySQL查询、筛选、统计
public class DataQuery {
    public static void main(String[] args) {
        Connection conn = DBUtil.getConnection();
        if (conn == null) return;

        // 场景1：按价格区间提取（20-100元）
        System.out.println("===== 价格20-100元商品 =====");
        queryByPriceRange(conn, 1120, 1100);

        // 场景2：按购买人数排序，取销量前10
        System.out.println("\n===== 销量前10商品 =====");
        queryTopSales(conn);

        // 场景3：按网站分类统计
        System.out.println("\n===== 各网站数据统计 =====");
        queryByWebsite(conn);

        DBUtil.close(conn);
    }

    // 按价格区间查询
    private static void queryByPriceRange(Connection conn, int min, int max) {
        String sql = "SELECT product_name,shop_website,price,buy_count FROM product_info WHERE price BETWEEN ? AND ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setBigDecimal(1, new BigDecimal(min));
            pstmt.setBigDecimal(2, new BigDecimal(max));
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                System.out.printf("商品：%s | 网站：%s | 价格：%s | 销量：%d%n",
                        rs.getString("product_name"),
                        rs.getString("shop_website"),
                        rs.getBigDecimal("price"),
                        rs.getInt("buy_count"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 销量前10查询
    private static void queryTopSales(Connection conn) {
        String sql = "SELECT product_name,shop_website,buy_count,price FROM product_info ORDER BY buy_count DESC LIMIT 10";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            int rank = 1;
            while (rs.next()) {
                System.out.printf("第%d名 | 商品：%s | 销量：%d | 价格：%s%n",
                        rank++,
                        rs.getString("product_name"),
                        rs.getInt("buy_count"),
                        rs.getBigDecimal("price"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 按网站统计
    private static void queryByWebsite(Connection conn) {
        String sql = "SELECT shop_website,COUNT(*) as total,AVG(price) as avg_price FROM product_info GROUP BY shop_website";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                System.out.printf("网站：%s | 商品数：%d | 均价：%.2f元%n",
                        rs.getString("shop_website"),
                        rs.getInt("total"),
                        rs.getBigDecimal("avg_price"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
