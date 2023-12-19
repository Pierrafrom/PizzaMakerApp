
package com.pizzaMakerApp.model;

import com.pizzaMakerApp.utils.DatabaseManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Pizza {
    protected final int id;
    protected String name;
    protected List<String> ingredients;
    protected List<Double> quantities;
    protected List<String> units;

    public Pizza(int id) {
        this.id = id;
        this.ingredients = new ArrayList<>();
        this.quantities = new ArrayList<>();
        this.units = new ArrayList<>();
        loadPizzaFromDatabase();
    }

    private void loadPizzaFromDatabase() {
        String query = "SELECT name, ingredientName, quantity, unit FROM VIEW_PIZZA_INGREDIENTS WHERE id = " + this.id;
        try {
            ResultSet rs = DatabaseManager.sendQuery(query, 1);
            boolean isFirstRow = true;
            while (rs.next()) {
                if (isFirstRow) {
                    this.name = rs.getString("name");
                    isFirstRow = false;
                }
                String ingredient = rs.getString("ingredientName");
                double quantity = rs.getDouble("quantity");
                String unit = rs.getString("unit");
                ingredients.add(ingredient);
                quantities.add(quantity);
                units.add(unit);
            }
        } catch (SQLException e) {
            Logger logger = LoggerFactory.getLogger(DatabaseManager.class);
            logger.error("SQL Exception occurred", e);
        }
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public List<Double> getQuantities() {
        return quantities;
    }

    public List<String> getUnits() {
        return units;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Pizza: ").append(getName()).append("\n");

        for (int i = 0; i < ingredients.size(); i++) {
            sb.append(" - ")
                    .append(quantities.get(i))
                    .append(units.get(i))
                    .append(" ")
                    .append(ingredients.get(i))
                    .append("\n");
        }

        return sb.toString();
    }

    public static void main(String[] args) {
        String query = "SELECT itemType, itemId FROM VIEW_ORDER_SUMMARY";
        try {
            ResultSet rs = DatabaseManager.sendQuery(query, 1);
            while (rs.next()) {
                String itemType = rs.getString("itemType");
                int itemId = rs.getInt("itemId");

                if (itemType.equals("PIZZA")) {
                    Pizza pizza = new Pizza(itemId);
                    System.out.println(pizza);
                } else if (itemType.equals("PIZZA CUSTOM")) {
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
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle exception appropriately
        }
    }
}
