package com.java.app;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class DbConnection {
    private String url;
    private String username;
    private String password;
    public DbConnection(){
        this.url = DbConf.URL;
        this.username = DbConf.USER;
        this.password = DbConf.PASSWORD;
    }
    public Connection createConnection(){
        try {
            return DriverManager.getConnection(
                    this.url, this.username, this.password
            );
        } catch (SQLException e){
            System.out.println("Connection error : " + e.getMessage());
        }
       return null;
    }
}
