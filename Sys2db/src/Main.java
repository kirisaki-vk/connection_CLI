import java.sql.*;
import java.util.UUID;
public class Main {
    private final String URL = "jdbc:postgresql://localhost:5432/java_app";
    private final String USER = "postgres";
    private final String PASSWORD = "pass_word";
    public Connection createConnection(){
        try {
            return DriverManager.getConnection(this.URL, this.USER, this.PASSWORD);
        } catch (SQLException e){
            System.out.println("Connection error :\n" + e.getMessage());
            return null;
        }
    }
    public void closeConnection(){
        try {
            this.createConnection().close();
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }
    }
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
    public void readconnection(Connection connection) {
        try {
            String request = "SELECT * FROM connection";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(request);
            Main.displayResult(resultSet);
            statement.close();
        } catch (SQLException exception) {
            System.out.println("Error while reading the connexion :\n" + exception.getMessage());
        }
    }
    public void readconnection(int limit, Connection connection){
        try{
            if (limit < 0){
                System.out.println("Limit should be a positive number.");
            } else {
                String request = "SELECT * FROM connection LIMIT ?";
                PreparedStatement preparedStatement = connection.prepareStatement(request);
                preparedStatement.setInt(1, limit);
                ResultSet resultSet = preparedStatement.executeQuery();
                Main.displayResult(resultSet);
                preparedStatement.close();
            }
        } catch (SQLException exception) {
            System.out.println("Error while reading the connexion :\n" + exception.getMessage());
        }
    }
    private static void displayResult(ResultSet resultSet) throws SQLException {
        try {
            while (resultSet.next()){
                System.out.println(
                        "Id : " + resultSet.getObject("id", UUID.class) + "\n"
                                + "Firstname : " + resultSet.getString("firstname") + "\n"
                                + "Connection date time : " + resultSet.getString("connection_datetime")
                                + "\n" + "-".repeat(10)
                );
            }
        } catch (SQLException exception){
            System.out.println("Error while reading the connexion :\n" + exception.getMessage());
        }
        resultSet.close();
    }
    public static void main(String[] args) {
        Main runMain = new Main();
        Connection connection = runMain.createConnection();
        switch (args[0]){
            case "newconnection" -> {
                if (args.length == 1) {
                    System.out.println("No username provided. Please provide an username.");
                } else {
                    runMain.newconnexion(args[1], connection);
                }
            }
            case "readconnection" -> {
                if (args.length == 1) {
                    runMain.readconnection(connection);
                } else {
                    runMain.readconnection(Integer.parseInt(args[1]), connection);
                }
            }
        }
        runMain.closeConnection();
    }
}