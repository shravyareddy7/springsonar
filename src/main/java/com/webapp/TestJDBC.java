package com.webapp;

import java.sql.Connection;
import java.sql.DriverManager;

public class TestJDBC {
    public static void main(String[] args) {
        String jdbcUrl="jdbc:mysql://localhost:3306/hibernate?useSSL=false&serverTimezone=UTC";
        String user="hibernate";
        String password="hibernate";
        try {
            System.out.println("Connecting to database"+jdbcUrl);
            Connection connection= DriverManager.getConnection(jdbcUrl,user,password);
            System.out.println("Connection successful");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
