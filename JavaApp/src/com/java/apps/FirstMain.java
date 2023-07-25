package com.java.apps;

import java.sql.*;
import java.util.UUID;

public class FirstMain {
    private final String URL = "jdbc:postgresql://localhost:5432/java_app";
    private final String USER = "postgres";
    private final String PASSWORD = "pass_word";

    // db connection
    public Connection createConnection(){
        try {
            return DriverManager.getConnection(this.URL, this.USER, this.PASSWORD);
        } catch (SQLException e){
            System.out.println("Connection error :\n" + e.getMessage());
        }
        return null;
    }
    public void closeConnection(){
        try {
            this.createConnection().close();
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }
    }

        // TODO : new connection
    public void newconnexion(String firstname, Connection connection){
        try {
            String request = "INSERT INTO connection(firstname) VALUES(?)";
            PreparedStatement preparedStatement = connection.prepareStatement(request);
            preparedStatement.setString(1, firstname);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            System.out.println("Connection added successfully !");
        } catch (SQLException exception) {
            System.out.println("Error while adding the new connection :\n" + exception.getMessage());
        }
    }

        // TODO :  read connection
    public void readconnection(int limit, Connection connection){
        try{
            String request;
            PreparedStatement preparedStatement;
            if (limit == 0){
                request = "SELECT * FROM connection";
                preparedStatement = connection.prepareStatement(request);
            } else {
                request = "SELECT * FROM connection LIMIT ?";
                preparedStatement = connection.prepareStatement(request);
                preparedStatement.setInt(1, limit);
            }
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                System.out.println(
                        "Id : " + resultSet.getObject("id", UUID.class) + "\n"
                        + "Firstname : " + resultSet.getString("firstname") + "\n"
                        + "Connection date time : " + resultSet.getInt("connection_datetime")
                );
            }
            preparedStatement.close();
        } catch (SQLException exception) {
            System.out.println("Error while reading the connexion :\n" + exception.getMessage());
        }
    }
    public static void main(String[] args) {
        FirstMain runFirstMain = new FirstMain();
        Connection connection = runFirstMain.createConnection();

        switch (args[0]){
            case "newconnection" -> {
                if (args.length == 1) {
                    System.out.println("No username provided. Please provide an username");
                } else {
                    runFirstMain.newconnexion(args[1], connection);
                }
            }
            case "readconnection" -> {
                if (args.length == 1) {
                    runFirstMain.readconnection(0, connection);
                } else {
                    runFirstMain.readconnection(Integer.parseInt(args[1]), connection);
                }
            }
            //jpql
        }
        runFirstMain.closeConnection();
    }
}
