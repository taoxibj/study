package com.taoxi.study.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

public class NativeJDBC {

    private static Logger logger = LoggerFactory.getLogger(NativeJDBC.class);

    public static void query() throws Exception {
        String URL = "jdbc:mysql://127.0.0.1:3306/mybatis?characterEncoding=utf-8";
        String USER = "root";
        String PASSWORD = "123456";
        // 1.加载驱动程序
        try {
            Class.forName("com.mysql.jdbc.Driver");
            // 2.获得数据库链接
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            // 3.通过数据库的连接操作数据库，实现增删改查（使用Statement类）
            String name = "中国";
            //预编译
            String sql = "select * from country where countryname=?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, name);
            ResultSet rs = statement.executeQuery();

            // 4.处理数据库的返回结果(使用ResultSet类)
            while (rs.next()) {
                logger.info(rs.getString("countryname") + " " + rs.getString("countrycode"));
            }

            // 关闭资源
            rs.close();
            statement.close();
            conn.close();
        } catch (ClassNotFoundException e) {
            logger.error("error:", e);
            throw e;
        } catch (SQLException e) {
            logger.error("error:", e);
            throw e;
        }
    }

}
