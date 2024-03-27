package com.smu.util;

import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * @Author: MingYun
 * @Date: 2024-02-28 17:20
 */
public class JdbcUtils {

    private static String driver=null;
    private static String url=null;
    private static String username=null;
    private static String password=null;
    static {
        try {
            InputStream in=JdbcUtils.class.getClassLoader().getResourceAsStream("db.properties");
            Properties properties=new Properties();
            properties.load(in);
            driver=properties.getProperty("driver");
            url=properties.getProperty("URL");
            username=properties.getProperty("USER");
            password=properties.getProperty("PASSWORD");


            Class.forName(driver);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url,username,password);
    }

    public static void release(Connection conn, Statement st, ResultSet rs) throws SQLException {
        if(rs!=null){
            rs.close();
        }
        if(st!=null){
            st.close();
        }
        if(conn!=null){
            conn.close();
        }
    }
}
