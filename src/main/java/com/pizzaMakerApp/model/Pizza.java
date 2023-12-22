package com.pizzaMakerApp.model;

import com.pizzaMakerApp.utils.DatabaseManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Represents a Pizza in the pizza maker application.
 * This class extends Food and is responsible for handling pizza-specific details.
 */
public class Pizza extends Food {

    /**
     * Constructor for the Pizza class.
     * Initializes a new Pizza instance with a specified ID and loads its details.
     *
     * @param id The unique identifier of the pizza to load from the database.
     */
    public Pizza(int id) {
        this.id = id; // Set the pizza ID
        loadPizzaDetails(); // Load pizza details (name, price, ingredients) from the database
    }

    /**
     * Loads the basic details (name and price) of the pizza from the database.
     * Calls another method to load the ingredients of the pizza.
     */
    private void loadPizzaDetails() {
        // SQL query to fetch the name and price of the pizza with the given ID
        String sqlQuery = "SELECT * FROM VIEW_PIZZA_INGREDIENTS WHERE id = ?";
        try {
            // Execute the query and process the result set
            ResultSet resultSet = DatabaseManager.sendQuery(sqlQuery, this.id);
            if (resultSet.next()) {
                // Set the name and price of the pizza from the database results
                this.name = resultSet.getString("name");
                this.price = resultSet.getFloat("price");

                // Load the ingredients of the pizza
                this.loadPizzaIngredients();
            }
        } catch (SQLException e) {
            // Log an error if there is an issue loading the pizza details
            Logger logger = LoggerFactory.getLogger(Pizza.class);
            logger.error("Error loading pizza details", e);
        }
    }

    /**
     * Loads the ingredients of the pizza from the database.
     * Constructs a string representation of all the ingredients, including their quantities and units.
     */
    private void loadPizzaIngredients() {
        // StringBuilder to build a string of ingredients.
        StringBuilder ingredientsBuilder = new StringBuilder();

        // SQL query to fetch ingredient details (name, quantity, unit) for the pizza.
        String sqlQuery = "SELECT ingredientName, quantity, unit FROM VIEW_PIZZA_INGREDIENTS WHERE id = ?";

        try {
            // Execute the query with the current pizza ID to fetch ingredient data.
            ResultSet resultSet = DatabaseManager.sendQuery(sqlQuery, this.id);
            boolean first = true; // Flag to help format the string (avoid leading comma)

            // Iterate over each row in the result set.
            while (resultSet.next()) {
                if (!first) {
                    // Append a comma separator if this is not the first ingredient.
                    ingredientsBuilder.append(", ");
                } else {
                    // After processing the first ingredient, set flag to false.
                    first = false;
                }

                // Retrieve each ingredient's name, quantity, and unit from the result set.
                String ingredientName = resultSet.getString("ingredientName");
                float quantity = resultSet.getFloat("quantity");
                String unit = resultSet.getString("unit");

                // Append the formatted ingredient to the ingredients string.
                // Format: "IngredientName (Quantity Unit)"
                ingredientsBuilder.append(ingredientName)
                        .append(" (")
                        .append(quantity)
                        .append(" ")
                        .append(unit)
                        .append(")");
            }

            // Set the constructed ingredients string to the pizza's ingredients field.
            this.ingredients = ingredientsBuilder.toString();
        } catch (SQLException e) {
            // Log an error if there is an issue loading the pizza ingredients.
            Logger logger = LoggerFactory.getLogger(Pizza.class);
            logger.error("Error loading pizza ingredients", e);
        }
    }
}



