/**
 * The Soda class represents a soda item in the pizza maker application.
 * It extends the Food class and provides functionality to load details for a specific soda from the database.
 * <p>
 * The soda details include the name, price, and bottle type.
 * Details are retrieved from the VIEW_SODA_INGREDIENTS table in the database.
 * <p>
 * Usage:
 * To instantiate a Soda object, provide the soda ID as a parameter to the constructor.
 * After instantiation, the details can be accessed using the provided methods.
 */
package com.pizzaMakerApp.model;

import com.pizzaMakerApp.utils.DatabaseManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Soda extends Food {

    // Enum to represent the type of soda container (BOTTLE or CAN)
    public enum BottleType {
        BOTTLE, CAN
    }

    // Type of container for the soda
    private BottleType bottleType;

    /**
     * Constructs a Soda object with the specified ID and loads its details from the database.
     *
     * @param id The unique identifier of the soda.
     */
    public Soda(int id) {
        this.id = id;
        loadSodaDetails();
    }

    /**
     * Loads the details of the soda from the database, including name, price, and container type.
     * Details are retrieved from the VIEW_SODA_INGREDIENTS table.
     */
    private void loadSodaDetails() {
        // SQL query to retrieve soda details by ID from the VIEW_SODA_INGREDIENTS table
        String sqlQuery = "SELECT * FROM VIEW_SODA_INGREDIENTS WHERE id = ?";

        try {
            // Execute the SQL query using the DatabaseManager and pass the soda ID as a parameter
            ResultSet resultSet = DatabaseManager.sendQuery(sqlQuery, this.id);

            // Check if there is a result (row) in the ResultSet
            if (resultSet.next()) {
                // Retrieve and set the soda name from the ResultSet
                this.name = resultSet.getString("name");

                // Retrieve and set the soda price from the ResultSet
                this.price = resultSet.getFloat("price");

                // Retrieve and set the soda container type using the BottleType enum
                this.bottleType = BottleType.valueOf(resultSet.getString("bottleType"));

                // Set ingredients to null
                this.ingredients = null;
            }
        } catch (SQLException e) {
            // If an SQL exception occurs, log an error message with details
            Logger logger = LoggerFactory.getLogger(Soda.class);
            logger.error("Error loading soda details", e);
        }
    }

    /**
     * Returns the type of container for the soda.
     *
     * @return The type of container for the soda.
     */
    public BottleType getBottleType() {
        return bottleType;
    }

    /**
     * Sets the type of container for the soda.
     *
     * @param bottleType The type of container for the soda.
     */
    public void setBottleType(BottleType bottleType) {
        this.bottleType = bottleType;
    }

    /**
     * Returns a string representation of the soda.
     * The string representation includes the name, bottle type, and price.
     *
     * @return A string representation of the soda.
     */
    public String toString() {
        return this.getName() + " (" + this.getBottleType() + ")" + " - $" + this.getPrice();
    }
}