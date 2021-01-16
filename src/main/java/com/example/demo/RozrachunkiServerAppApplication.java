package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.sql.*;
@EnableAutoConfiguration
@SpringBootApplication
@ComponentScan

public class RozrachunkiServerAppApplication {

    public static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    public static final String DB_URL = "jdbc:mysql://localhost:3308/rozrachunki?useSSL=false&serverTimezone=UTC";

    public static final String USER = "admin";
    public static final String PASS = "admin";

    public static void main(String[] args) {

        Connection connection = null;
        Statement statement = null;
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
            statement = connection.createStatement();

            
            ResultSet resultSet = connection.getMetaData().getCatalogs();
            boolean exists = false;
            while (resultSet.next()) {
                System.out.println("Connected");
            }
            resultSet.close();

        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException se2) {
            }
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }

        SpringApplication.run(RozrachunkiServerAppApplication.class, args);
    }

}
