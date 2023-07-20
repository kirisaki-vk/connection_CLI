package com.java.app;
import java.util.ArrayList;
import java.util.List;
public class Main {
    static AppDAO appDAO = new AppDAO();
    public static void connectionList(List<UserConnection> list){
        List<UserConnection> userConnection = new ArrayList<>(list);
        for (UserConnection connections : userConnection) {
            System.out.println("----------");
            System.out.println("Id : " + connections.getUuid());
            System.out.println("Username : " + connections.getFirstname());
            System.out.println("Connection date & time : " + connections.getConnextionDatetime());
        }
    }
    public static void main(String[] args) {
        switch (args[0]){
            case "newconnection" -> {
                if (args.length == 1) {
                    System.out.println("No username provided. Please provide an username");
                } else {
                    appDAO.newconnexion(args[1]);
                }
            }
            case "readconnection" -> {
                if (args.length == 1) {
                    connectionList(appDAO.readconnection(0));
                } else {
                    connectionList(appDAO.readconnection(Integer.parseInt(args[1])));
                }
            }
        }
        appDAO.closeConnection();
    }
}