package com.pizzaMakerApp.model;

import com.pizzaMakerApp.utils.DatabaseManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Represents a custom pizza, extending the Pizza class with additional functionality
 * for handling customizations.
 */
public class CustomPizza extends Pizza {
    private final int customId;

    /**
     * Constructor for CustomPizza class.
     * It initializes a custom pizza object by loading the original pizza and then applying
     * customizations from the database.
     *
     * @param originalId The ID of the original pizza to load.
     * @param customId   The ID of the custom pizza to apply customizations.
     */
    public CustomPizza(int originalId, int customId) {
        super(originalId);
        this.customId = customId;
        applyCustomizations();
    }

    /**
     * Applies customizations to the pizza based on the custom pizza ID.
     * It updates the ingredients and quantities lists with custom ingredients.
     */
    private void applyCustomizations() {
        String query = "SELECT IngredientAddedName, QuantityAdded, UnitAdded, IngredientRemovedName, QuantityRemoved, UnitRemoved FROM VIEW_CUSTOM_PIZZAS_WITH_INGREDIENTS WHERE CustomPizzaId = " + this.customId;
        try {
            ResultSet rs = DatabaseManager.sendQuery(query, 1);
            while (rs.next()) {
                String ingredientAdded = rs.getString("IngredientAddedName");
                double quantityAdded = rs.getDouble("QuantityAdded");
                String unitAdded = rs.getString("UnitAdded");
                String ingredientRemoved = rs.getString("IngredientRemovedName");
                double quantityRemoved = rs.getDouble("QuantityRemoved");

                // Adjust ingredients and quantities according to customizations
                if (quantityAdded > 0) {
                    addIngredient(ingredientAdded, quantityAdded, unitAdded);
                }
                if (quantityRemoved > 0) {
                    removeIngredient(ingredientRemoved, quantityRemoved);
                }
            }
            // Update the name to reflect customization
            setName(getName() + " Custom");
        } catch (SQLException e) {
            Logger logger = LoggerFactory.getLogger(DatabaseManager.class);
            logger.error("SQL Exception occurred", e);
        }
    }

    /**
     * Adds an ingredient to the custom pizza.
     *
     * @param ingredientName Name of the ingredient to add.
     * @param quantity       Quantity of the ingredient to add.
     */
    public void addIngredient(String ingredientName, double quantity, String unit) {
        int index = getIngredients().indexOf(ingredientName);
        if (index != -1) {
            // If the ingredient already exists, update its quantity
            double newQuantity = getQuantities().get(index) + quantity;
            getQuantities().set(index, newQuantity);
        } else {
            // If the ingredient does not exist, add it to the list
            getIngredients().add(ingredientName);
            getQuantities().add(quantity);
            getUnits().add(unit);
        }
    }

    /**
     * Removes an ingredient from the custom pizza.
     *
     * @param ingredientName Name of the ingredient to remove.
     * @param quantity       Quantity of the ingredient to remove.
     */
    public void removeIngredient(String ingredientName, double quantity) {
        int index = getIngredients().indexOf(ingredientName);
        if (index != -1) {
            double newQuantity = getQuantities().get(index) - quantity;
            if (newQuantity <= 0) {
                // Remove the ingredient if the new quantity is zero or negative
                getIngredients().remove(index);
                getQuantities().remove(index);
            } else {
                // Update the quantity of the ingredient
                getQuantities().set(index, newQuantity);
            }
        } else {
            // Log or handle the case where the ingredient is not found
            System.out.println("Ingredient not found: " + ingredientName);
        }
    }

    // Getters and Setters for customId
    public int getCustomId() {
        return customId;
    }

    @Override
    public String toString() {
        return "Custom " + super.toString();
    }

}
/*
else if (itemType.equals("PIZZA CUSTOM")) {
int originId = -1;
query = "SELECT originalPizza FROM PIZZA_CUSTOM WHERE pizzaId = " + itemId;
                    try {
rs = DatabaseManager.sendQuery(query, 1);
                        if (rs.next()) {
originId = rs.getInt("originalPizza");
                        }
                                } catch (SQLException e) {
Logger logger = LoggerFactory.getLogger(DatabaseManager.class);
                        logger.error("SQL Exception occurred", e);
                    }
CustomPizza customPizza = new CustomPizza(itemId, itemId);
                    System.out.println(customPizza);
                }


 */