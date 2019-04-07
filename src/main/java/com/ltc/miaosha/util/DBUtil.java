package com.ltc.miaosha.util;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

@Component
public class DBUtil {

//    private static Properties props;
//
//    static {
//        try {
//            InputStream in = DBUtil.class.getClassLoader().getResourceAsStream("application.yml");
//            props = new Properties();
//            props.load(in);
//            in.close();
//        }catch(Exception e) {
//            e.printStackTrace();
//        }
//    }

//    @Value("${spring.datasource.url}")
//     String url;
//
//    @Value("${username}")
//     String username;
//
//    @Value("${password}")
//     String password;
//
//    @Value("${driver-class-name}")
//    String driver;

    public static Connection getConn() throws Exception{
        String url = "jdbc:mysql://47.106.74.64:3306/miaosha?useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=Asia/Shanghai";
        String username ="root";
        String password = "123456";
        String driver = "com.mysql.cj.jdbc.Driver";
        Class.forName(driver);
        return DriverManager.getConnection(url,username, password);
    }



}

