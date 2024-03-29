package com.pizzaMakerApp.model;

import com.pizzaMakerApp.utils.DatabaseManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The Dessert class represents a dessert item in the pizza maker application.
 * It extends the Food class and provides functionality to load details and ingredients
 * for a specific dessert from the database.
 * <p>
 * The dessert details include the name, price, and a list of ingredients.
 * The ingredients are loaded from the VIEW_DESSERT_INGREDIENTS table in the database.
 * <p>
 * Usage:
 * To instantiate a Dessert object, provide the dessert ID as a parameter to the constructor.
 * After instantiation, the details and ingredients can be accessed using the provided methods.
 */
public class Dessert extends Food {
    /**
     * Constructs a Dessert object with the specified ID and loads its details from the database.
     *
     * @param id The unique identifier of the dessert.
     */
    public Dessert(int id) {
        this.id = id;
        loadDessertDetails();
    }

    /**
     * Loads the details of the dessert from the database, including name, price, and ingredients.
     * Details are retrieved from the VIEW_DESSERT_INGREDIENTS table.
     */
    private void loadDessertDetails() {
        // SQL query to retrieve dessert details by ID from the VIEW_DESSERT_INGREDIENTS table
        String sqlQuery = "SELECT * FROM VIEW_DESSERT_INGREDIENTS WHERE id = ?";

        try {
            // Execute the SQL query using the DatabaseManager and pass the dessert ID as a parameter
            ResultSet resultSet = DatabaseManager.sendQuery(sqlQuery, this.id);

            // Check if there is a result (row) in the ResultSet
            if (resultSet.next()) {
                // Retrieve and set the dessert name from the ResultSet
                this.name = resultSet.getString("name");

                // Retrieve and set the dessert price from the ResultSet
                this.price = resultSet.getFloat("price");

                // Load the dessert ingredients by calling the loadDessertIngredients method
                this.loadDessertIngredients();
            }
        } catch (SQLException e) {
            // If an SQL exception occurs, log an error message with details
            Logger logger = LoggerFactory.getLogger(Dessert.class);
            logger.error("Error loading dessert details", e);
        }
    }


    /**
     * Loads the ingredients of the dessert from the database and constructs a formatted string.
     * Ingredients are retrieved from the VIEW_DESSERT_INGREDIENTS table.
     */
    private void loadDessertIngredients() {
        // StringBuilder to construct the formatted string of dessert ingredients
        StringBuilder ingredientsBuilder = new StringBuilder();

        // SQL query to retrieve dessert ingredients by ID from the VIEW_DESSERT_INGREDIENTS table
        String sqlQuery = "SELECT ingredientName, quantity, unit FROM VIEW_DESSERT_INGREDIENTS WHERE id = ?";

        try {
            // Execute the SQL query using the DatabaseManager and pass the dessert ID as a parameter
            ResultSet resultSet = DatabaseManager.sendQuery(sqlQuery, this.id);

            // Flag to determine whether it's the first ingredient in the loop
            boolean first = true;

            // Loop through each row in the ResultSet
            while (resultSet.next()) {
                // If it's not the first ingredient, append a comma and space to the StringBuilder
                if (!first) {
                    ingredientsBuilder.append(", ");
                } else {
                    // Set the flag to false after processing the first ingredient
                    first = false;
                }

                // Retrieve values for each ingredient from the ResultSet
                String ingredientName = resultSet.getString("ingredientName");
                float quantity = resultSet.getFloat("quantity");
                String unit = resultSet.getString("unit");

                // Append the formatted ingredient to the StringBuilder
                ingredientsBuilder.append(ingredientName)
                        .append(" (")
                        .append(quantity)
                        .append(" ")
                        .append(unit)
                        .append(")");
            }

            // Set the ingredients property with the formatted string
            this.ingredients = ingredientsBuilder.toString();

        } catch (SQLException e) {
            // If an SQL exception occurs, log an error message with details
            Logger logger = LoggerFactory.getLogger(Dessert.class);
            logger.error("Error loading dessert ingredients", e);
        }
    }
}