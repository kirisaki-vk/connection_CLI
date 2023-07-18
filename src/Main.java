import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        String user = "postgres";
        String password = "1234";
        String host = "localhost";
        String database = "sys2_connection";
        String port = "5432";
        Connection mainConnection = null;
        try {
            mainConnection = DriverManager.getConnection("jdbc:postgresql://" + host + "/" + database,
                    user,
                    password);
        } catch (SQLException exception) {
            System.out.println("Connection error: " + exception.getMessage());
        }
        switch (args[0]) {
            case "help" -> {
                System.out.println("This java app is a CLI to read and write a distant an AWS RDS ");
                System.out.println("\nSYS2_connection <options>");
                System.out.println("==================================");
                System.out.println("help: Show this help");
                System.out.println("read_connection [number]: displays all connections");
                System.out.println("new_connection <username>: create a new connection");
                System.out.println("\n");
            }
            case "read_connection" -> {
                if (args.length == 1) {
                    try {
                        ResultSet result = mainConnection.createStatement().executeQuery("SELECT * FROM connection");
                        while (result.next()) {
                            System.out.println("============================================");
                            System.out.println("UUID: " + result.getObject("id", java.util.UUID.class));
                            System.out.println("Name: " + result.getString("username"));
                            System.out.println("Connection time: " + result.getTimestamp("connection_timestamp"));
                            System.out.println("============================================");
                        }
                        result.close();
                    } catch (SQLException exception) {
                        System.out.println("Error while executing the query:" + exception.getMessage());
                    }
                } else {
                    int parsedValue = Integer.parseInt(args[1]);
                    String stringValue = String.valueOf(parsedValue);
                    if (args[1].equals(stringValue) && parsedValue > 0) {
                        try {
                            ResultSet result = mainConnection.createStatement().executeQuery("SELECT * FROM connection LIMIT " + parsedValue);
                            while (result.next()) {
                                System.out.println("============================================");
                                System.out.println("UUID: " + result.getObject("id", java.util.UUID.class));
                                System.out.println("Name: " + result.getString("username"));
                                System.out.println("Connection time: " + result.getTimestamp("connection_timestamp"));
                                System.out.println("============================================");
                            }

                            result.close();
                        } catch (SQLException exception) {
                            System.out.println("Error while executing the query:" + exception.getMessage());
                        }
                    } else {
                        System.out.println("The input is not a valid integer.");
                    }

                }
            }
            case "new_connection" -> {
                if (args.length == 1) {
                    System.out.println("No username provided. Please provide an username");
                } else {
                    try {
                        mainConnection.createStatement().execute("INSERT INTO connection(username) VALUES ('" + args[1] + "')");
                    } catch (SQLException exception) {
                        System.out.println("An error occured during the insert:" + exception.getMessage());
                    }
                }
            }
            default -> {
                System.out.println("This java app is a CLI to read and write a distant an AWS RDS ");
                System.out.println("\nSYS2_connection <options>");
                System.out.println("==================================");
                System.out.println("help: Show this help");
                System.out.println("read_connection [number]: displays all connections");
                System.out.println("new_connection <username>: create a new connection");
                System.out.println("\n");
            }
        }
    }
}