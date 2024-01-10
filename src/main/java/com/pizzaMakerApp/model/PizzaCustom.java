package com.pizzaMakerApp.model;

import com.pizzaMakerApp.utils.DatabaseManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Represents a customized pizza in the pizza maker application.
 * This class extends the Pizza class and allows additional modifications to the pizza.
 */
public class PizzaCustom extends Pizza {

    // Unique identifier for the customized pizza.
    private final int customPizzaId;

    // Constant representing the additional cost per added ingredient.
    private static final float ADDITIONAL_COST_PER_INGREDIENT = 1.50f;

    /**
     * Constructor for PizzaCustom.
     * Initializes a new PizzaCustom instance using an existing pizza's ID and a custom pizza ID.
     * The existing pizza's details are loaded and then modified according to the customizations.
     *
     * @param originalPizzaId The ID of the original pizza that the custom pizza is based on.
     * @param customPizzaId   The ID of the custom pizza for loading custom modifications.
     */
    public PizzaCustom(int originalPizzaId, int customPizzaId) {
        super(originalPizzaId); // Calls the constructor of the superclass (Pizza) to load the original pizza details

        // Append "(custom)" to the name of the pizza to indicate that it is a customized version
        this.setName(this.getName() + " (custom)");

        // Set the ID for the customized pizza
        this.customPizzaId = customPizzaId;

        // Modify the pizza according to the customizations (add or remove ingredients, adjust the price, etc.)
        modifyPizza();
    }

    /**
     * Modifies the pizza according to custom specifications.
     * This method queries the database to find out what ingredients need to be added or removed from the pizza
     * based on the customPizzaId, and then adjusts the pizza ingredients accordingly.
     */
    private void modifyPizza() {
        // SQL query to retrieve the names, quantities, and units of both added and removed ingredients
        // for this custom pizza.
        String sqlQuery = "SELECT IngredientAddedName, " +
                "QuantityAdded, " +
                "Unit1, " +
                "IngredientRemovedName, " +
                "Unit2 " +
                "FROM VIEW_CUSTOM_PIZZAS_WITH_INGREDIENTS " +
                "WHERE CustomPizzaId = ?";

        // Lists to hold the formatted strings of ingredients to be added and removed.
        ArrayList<String> addedIngredients = new ArrayList<>();
        ArrayList<String> removedIngredients = new ArrayList<>();

        try {
            // Execute the query with the customPizzaId to fetch custom ingredient modifications.
            ResultSet resultSet = DatabaseManager.sendQuery(sqlQuery, this.customPizzaId);
            while (resultSet.next()) {
                // Retrieve the name, quantity, and unit for each added ingredient.
                if (!resultSet.getString("IngredientAddedName").equals("Empty")) {
                    String addedIngredientName = resultSet.getString("IngredientAddedName");
                    float addedQuantity = resultSet.getFloat("QuantityAdded");
                    String addedUnit = resultSet.getString("Unit1");

                    String formattedAddedIngredient = String.format("%s (%.2f %s)", addedIngredientName,
                            addedQuantity, addedUnit);
                    addedIngredients.add(formattedAddedIngredient);
                }

                // Retrieve the name, quantity, and unit for each removed ingredient.
                if (!resultSet.getString("IngredientRemovedName").equals("Empty")) {
                    String removedIngredientName = resultSet.getString("IngredientRemovedName");
                    float removedQuantity = resultSet.getFloat("QuantityRemoved");
                    String removedUnit = resultSet.getString("Unit2");

                    String formattedRemovedIngredient = String.format("%s (%.2f %s)", removedIngredientName,
                            removedQuantity, removedUnit);
                    removedIngredients.add(formattedRemovedIngredient);
                }
            }

            // Adjust the current pizza ingredients by adding new ones and removing specified ones.
            adjustIngredients(addedIngredients, removedIngredients);
        } catch (SQLException e) {
            // Log an error if there is a problem with modifying the pizza.
            Logger logger = LoggerFactory.getLogger(PizzaCustom.class);
            logger.error("Error modifying custom pizza", e);
        }
    }

    /**
     * Adjusts the ingredients of the pizza based on the lists of ingredients to be added and removed.
     * It updates the pizza's ingredients and recalculates the price based on these modifications.
     *
     * @param addedIngredients   A list of formatted ingredient strings to be added to the pizza.
     * @param removedIngredients A list of formatted ingredient strings to be removed from the pizza.
     */
    private void adjustIngredients(ArrayList<String> addedIngredients, ArrayList<String> removedIngredients) {
        // Split the current ingredients string into an ArrayList for easier manipulation.
        // Each ingredient is assumed to be separated by ", " in the string.
        ArrayList<String> originalIngredients = new ArrayList<>(Arrays.asList(this.getIngredients().split(", ")));

        // Loop through each ingredient that needs to be removed.
        for (String ingredientToRemove : removedIngredients) {
            // Remove the ingredient from the list if it matches.
            // The removal is based on the ingredient name which is obtained before the " (" part.
            // This assumes that the ingredient name is unique in the list and does not occur as a part of another ingredient name.
            originalIngredients.removeIf(ingredient -> ingredient.contains(ingredientToRemove.split(" \\(")[0]));
        }

        // Add all the new ingredients to the original list.
        // These are already formatted correctly (e.g., "Ingredient (quantity unit)").
        originalIngredients.addAll(addedIngredients);

        // Reconstruct the ingredients string from the modified list.
        StringBuilder ingredientsBuilder = new StringBuilder();
        for (int i = 0; i < originalIngredients.size(); i++) {
            ingredientsBuilder.append(originalIngredients.get(i));
            // Add a separator (", ") after each ingredient except the last one.
            if (i < originalIngredients.size() - 1) {
                ingredientsBuilder.append(", ");
            }
        }

        // Update the pizza's ingredients attribute with the new, modified string.
        this.setIngredients(ingredientsBuilder.toString());

        // Update the price of the pizza.
        // The price is increased by a set amount for each added ingredient.
        this.setPrice(this.getPrice() + addedIngredients.size() * ADDITIONAL_COST_PER_INGREDIENT);
    }

}
