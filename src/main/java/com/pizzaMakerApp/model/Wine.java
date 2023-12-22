package com.pizzaMakerApp.model;

import com.pizzaMakerApp.utils.DatabaseManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Represents a Wine in the pizza maker application.
 * This class extends Food and is responsible for handling wine-specific details.
 */
public class Wine extends Food {

    /**
     * Enum for the type of wine bottle.
     */
    public enum BottleType {
        BOTTLE, PICCOLO, MAGNUM, JEROBOAM, REHOBOAM, MATHUSALEM
    }

    /**
     * Enum for the color of the wine.
     */
    public enum WineColor {
        RED, WHITE, ROSE
    }

    // Additional attributes specific to wine
    private String domain;
    private String origin;
    private int year;
    private WineColor color;
    private BottleType bottleType;

    /**
     * Constructor for the Wine class.
     * Initializes a new Wine instance with a specified ID and loads its details.
     *
     * @param id The unique identifier of the wine to load from the database.
     */
    public Wine(int id) {
        this.id = id;
        loadWineDetails();
    }

    /**
     * Loads the basic details of the wine from the database.
     */
    private void loadWineDetails() {
        String sqlQuery = "SELECT * FROM VIEW_WINE WHERE id = ?";
        try {
            ResultSet resultSet = DatabaseManager.sendQuery(sqlQuery, this.id);
            if (resultSet.next()) {
                this.name = resultSet.getString("name");
                this.price = resultSet.getFloat("price");
                this.domain = resultSet.getString("domain");
                this.origin = resultSet.getString("origin");
                this.year = resultSet.getInt("year");
                this.color = WineColor.valueOf(resultSet.getString("color").toUpperCase());
                this.bottleType = BottleType.valueOf(resultSet.getString("bottleType"));
                this.ingredients = null;
            }
        } catch (SQLException e) {
            Logger logger = LoggerFactory.getLogger(Wine.class);
            logger.error("Error loading wine details", e);
        }
    }

    /**
     * Returns the domain of the wine.
     *
     * @return The domain of the wine.
     */
    public String getDomain() {
        return domain;
    }

    /**
     * Returns the origin of the wine.
     *
     * @return The origin of the wine.
     */
    public String getOrigin() {
        return origin;
    }

    /**
     * Returns the year of the wine.
     *
     * @return The year of the wine.
     */
    public int getYear() {
        return year;
    }

    /**
     * Returns the color of the wine.
     *
     * @return The color of the wine.
     */
    public WineColor getColor() {
        return color;
    }

    /**
     * Returns the bottle type of the wine.
     *
     * @return The bottle type of the wine.
     */
    public BottleType getBottleType() {
        return bottleType;
    }

    /**
     * Returns a string representation of the wine.
     *
     * @return A string representation of the wine.
     */
    @Override
    public String toString() {
        return name + " - " + domain + " - " + year + " - " + origin + " - " + color + " - " + bottleType + " - " + price;
    }
}
