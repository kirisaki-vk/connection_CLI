import java.sql.*;

public class Main {
    public static void main(String[] args) {
        String user = "postgres";
        String password = "1234";
        String host = "localhost";
        String database = "sys2_connection";

        try {
            Connection mainConnection = DriverManager.getConnection("jdbc:postgresql://" + host + "/" + database, user, password);

            if(args.length > 0) {
                switch (args[0]) {
                    case "help":
                        showHelp();
                        break;
                    case "read_connection":
                        readConnections(mainConnection, args);
                        break;
                    case "new_connection":
                        createNewConnection(mainConnection, args);
                        break;
                    default:
                        showHelp();
                        break;
                }
            } else showHelp();

            mainConnection.close();
        } catch (SQLException exception) {
            System.out.println("An error occurred: " + exception.getMessage());
        }
    }

    private static void showHelp() {
        System.out.println("This java app is a CLI to read and write a distant an AWS RDS ");
        System.out.println("\nSYS2_connection <options>");
        System.out.println("==================================");
        System.out.println("help: Show this help");
        System.out.println("read_connection [number]: displays all connections");
        System.out.println("new_connection <username>: create a new connection");
        System.out.println("\n");
    }

    private static void readConnections(Connection connection, String[] args) {
        try {
            Statement statement = connection.createStatement();
            String query = (args.length == 1) ? "SELECT * FROM connection" : "SELECT * FROM connection LIMIT " + Integer.parseInt(args[1]);
            ResultSet result = statement.executeQuery(query);

            while (result.next()) {
                System.out.println("============================================");
                System.out.println("UUID: " + result.getObject("id", java.util.UUID.class));
                System.out.println("Name: " + result.getString("username"));
                System.out.println("Connection time: " + result.getTimestamp("connection_timestamp"));
                System.out.println("============================================");
            }

            result.close();
        } catch (SQLException | NumberFormatException exception) {
            System.out.println("Error while executing the query: " + exception.getMessage());
        }
    }

    private static void createNewConnection(Connection connection, String[] args) {
        if (args.length == 1) {
            System.out.println("No username provided. Please provide a username.");
        } else {
            try {
                Statement statement = connection.createStatement();
                String query = "INSERT INTO connection(username) VALUES ('" + args[1] + "')";
                statement.execute(query);
                System.out.println("New connection created successfully.");
            } catch (SQLException exception) {
                System.out.println("An error occurred during the insert: " + exception.getMessage());
            }
        }
    }
}
