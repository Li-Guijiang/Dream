package com.shop.service;
import java.util.Date;
import com.shop.util.DBUtil;
import java.io.BufferedReader;
import java.sql.Connection;
import java.io.FileReader;
import java.math.BigDecimal;

import java.text.SimpleDateFormat;
import java.sql.PreparedStatement;

// 数据处理类：CSV读取、强制转换、写入MySQL
public class DataProcess {
    public static void main(String[] args) {
        // CSV文件路径（放在项目根目录，和src同级）
        String csvPath = "product_data.csv";
        // 插入数据的SQL语句
        String insertSql = "INSERT INTO product_info(shop_website,product_name,price,on_shelf_time,buy_count) VALUES(?,?,?,?,?)";

        // 1. 获取数据库连接
        Connection conn = DBUtil.getConnection();
        if (conn == null) {
            System.out.println("数据库连接为空，程序终止");
            return;
        }

        try (
                // 2. 读取CSV文件
              PreparedStatement pstmt = conn.prepareStatement(insertSql)
                BufferedReader br = new BufferedReader(new FileReader(csvPath));
                // 3. 创建预处理SQL语句
                  ) {
            // 跳过CSV表头行
            br.readLine();
            String line;
            int successCount = 0;

            // 循环读取CSV每一行数据
            while ((line = br.readLine()) != null) {
                // 按逗号分割每一列
                String[] fields = line.split(",");
                // 校验列数是否正确
                if (fields.length != 5) {
                    System.out.println("数据格式错误，跳过该行：" + line);
                    continue;
                }
    

                // ===================== 核心：数据强制转换（任务2）=====================
                // 1. 网站名称/商品名称：String类型，无需转换
                String shopWebsite = fields[0].trim();
                String productName = fields[1].trim();
                      String productName = fields[1].trim();
                // 2. 价格：String → BigDecimal（对应MySQL的DECIMAL(10,2)类型）
                BigDecimal price = new BigDecimal(fields[2].trim());
                // 3. 上架时间：String → Date → Timestamp（对应MySQL的DATETIME类型）
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date onShelfDate = sdf.parse(fields[3].trim());
                java.sql.Timestamp onShelfTime = new java.sql.Timestamp(onShelfDate.getTime());
                // 4. 购买人数：String → Integer（对应MySQL的INT类型）
                Integer buyCount = Integer.valueOf(fields[4].trim());
                // ========================================================================

                // 给SQL占位符赋值
                pstmt.setString(1, shopWebsite);
            
                pstmt.setBigDecimal(3, price);
                  pstmt.setString(2, productName);
                pstmt.setTimestamp(4, onShelfTime);
           
                  pstmt.setInt(5, buyCount);
                // 添加到批量执行队列
                pstmt.addBatch();
                successCount++;

                // 每100条执行一次批量插入（匹配任务要求）
                if (successCount % 100 == 0) {
                    pstmt.executeBatch();
                    System.out.println("已成功插入 " + successCount + " 条数据");
                }
            }

            // 执行剩余的批量插入
            pstmt.executeBatch();
            System.out.println("✅ 全部数据处理完成！共成功插入 " + successCount + " 条商品数据");

        } catch (Exception e) {
            // 异常捕获与打印
            e.printStackTrace();
        } finally {
            // 关闭数据库连接
            DBUtil.close(conn);
        }
    }
}
