package com.java.app;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AppDAO {
    DbConnection dbConnection = new DbConnection();
    Connection connection = dbConnection.createConnection();

    // new connection
    public void newconnexion(String firstname){
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

    // read connexion
    public List<UserConnection> readconnection(int limit){
        List<UserConnection> userConnection = new ArrayList<>();
        try{
            String request;
            int i;
            if (limit == 0){
                request = "SELECT * FROM connection";

            } else {
                request = "SELECT * FROM connection LIMIT ?";
            }

            PreparedStatement preparedStatement = connection.prepareStatement(request);
            preparedStatement.setInt(1, limit);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                userConnection.add(new UserConnection(
                        resultSet.getObject("id", UUID.class),
                        resultSet.getString("firstname"),
                        resultSet.getString("connection_datetime")
                ));
            }
            preparedStatement.close();
            return userConnection;
        } catch (SQLException exception) {
            System.out.println("Error while reading the connexion : " + exception.getMessage());
        }
        return userConnection;
    }
    public void closeConnection(){
        try {
            connection.close();
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }

    }
}
