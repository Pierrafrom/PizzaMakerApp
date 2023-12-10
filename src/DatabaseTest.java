import config.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseTest extends config.DBconfig {

    public static void main(String[] args) {
        try {
            // Establishing a database connection
            Connection connection = DriverManager.getConnection(DBconfig.DATABASE_URL, DBconfig.USERNAME, DBconfig.PASSWORD);

            // Perform your database operations here
            // Example SELECT query
            String selectQuery = "SELECT * FROM PIZZA";

            try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
                // Execute the query
                ResultSet resultSet = preparedStatement.executeQuery();

                // Display the results in the console
                System.out.println("Pizza Table:");

                // Print column headers
                System.out.printf("%-10s %-30s %-15s %-10s%n", "pizzaId", "pizzaName", "pizzaPrice", "spotlight");

                // Process the results
                while (resultSet.next()) {
                    // Retrieve data from the result set
                    int pizzaId = resultSet.getInt("pizzaId");
                    String pizzaName = resultSet.getString("pizzaName");
                    double pizzaPrice = resultSet.getDouble("pizzaPrice");
                    boolean spotlight = resultSet.getBoolean("spotlight");

                    // Display the data in the console
                    System.out.printf("%-10d %-30s %-15.2f %-10b%n", pizzaId, pizzaName, pizzaPrice, spotlight);
                }
            }
            // Close the connection when done
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}