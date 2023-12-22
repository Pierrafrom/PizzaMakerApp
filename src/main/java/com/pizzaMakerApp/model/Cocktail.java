package com.pizzaMakerApp.model;

import com.pizzaMakerApp.utils.DatabaseManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The Cocktail class represents a cocktail item in the pizza maker application.
 * It extends the Food class and provides functionality to load details and ingredients
 * for a specific cocktail from the database.
 * <p>
 * The cocktail details include the name, price, and a list of ingredients.
 * The ingredients are loaded from the VIEW_COCKTAIL_INGREDIENTS table in the database.
 * <p>
 * Usage:
 * To instantiate a Cocktail object, provide the cocktail ID as a parameter to the constructor.
 * After instantiation, the details and ingredients can be accessed using the provided methods.
 */
public class Cocktail extends Food {
    /**
     * Constructs a Cocktail object with the specified ID and loads its details from the database.
     *
     * @param id The unique identifier of the cocktail.
     */
    public Cocktail(int id) {
        this.id = id;
        loadCocktailDetails();
    }

    /**
     * Loads the details of the cocktail from the database, including name, price, and ingredients.
     * Details are retrieved from the VIEW_COCKTAIL_INGREDIENTS table.
     */
    private void loadCocktailDetails() {
        // SQL query to retrieve cocktail details by ID from the VIEW_COCKTAIL_INGREDIENTS table
        String sqlQuery = "SELECT * FROM VIEW_COCKTAIL_INGREDIENTS WHERE id = ?";

        try {
            // Execute the SQL query using the DatabaseManager and pass the cocktail ID as a parameter
            ResultSet resultSet = DatabaseManager.sendQuery(sqlQuery, this.id);

            // Check if there is a result (row) in the ResultSet
            if (resultSet.next()) {
                // Retrieve and set the cocktail name from the ResultSet
                this.name = resultSet.getString("name");

                // Retrieve and set the cocktail price from the ResultSet
                this.price = resultSet.getFloat("price");

                // Load the cocktail ingredients by calling the loadCocktailIngredients method
                this.loadCocktailIngredients();
            }
        } catch (SQLException e) {
            // If an SQL exception occurs, log an error message with details
            Logger logger = LoggerFactory.getLogger(Cocktail.class);
            logger.error("Error loading cocktail details", e);
        }
    }


    /**
     * Loads the ingredients of the cocktail from the database and constructs a formatted string.
     * Ingredients are retrieved from the VIEW_COCKTAIL_INGREDIENTS table.
     */
    private void loadCocktailIngredients() {
        // StringBuilder to construct the formatted string of cocktail ingredients
        StringBuilder ingredientsBuilder = new StringBuilder();

        // SQL query to retrieve cocktail ingredients by ID from the VIEW_COCKTAIL_INGREDIENTS table
        String sqlQuery = "SELECT ingredientName, quantity, unit FROM VIEW_COCKTAIL_INGREDIENTS WHERE id = ?";

        try {
            // Execute the SQL query using the DatabaseManager and pass the cocktail ID as a parameter
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
            Logger logger = LoggerFactory.getLogger(Cocktail.class);
            logger.error("Error loading cocktail ingredients", e);
        }
    }
}