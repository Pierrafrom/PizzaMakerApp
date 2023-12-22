package com.pizzaMakerApp.model;

import com.pizzaMakerApp.utils.DatabaseManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents an Order in the pizza maker application.
 * This class is designed to encapsulate all the details of an order including items, quantities, and client information.
 */
public class Order {

    public enum Status {
        PENDING, PREPARATION, SHIPPED, DELIVERED, CANCELED
    }

    // Unique identifier for each order.
    private int id;

    // A list to hold the Food items in the order. Food is a generic type that can include pizzas, desserts, etc.
    private ArrayList<Food> items;

    // A parallel list to 'items', holding the quantity of each corresponding Food item.
    private ArrayList<Integer> quantity;

    // The date and time when the order was made.
    private Date dateTime;

    // The name of the client who made the order.
    private String clientName;

    /**
     * Constructor for the Order class.
     * This constructor initializes a new Order instance and loads its detailed information from the database
     * based on the provided order ID.
     *
     * @param orderId The unique identifier of the order to load from the database.
     */
    public Order(int orderId) {
        // Set the order ID.
        this.id = orderId;

        // Initialize the lists for storing order items and their quantities.
        this.items = new ArrayList<>();
        this.quantity = new ArrayList<>();

        // Load order details from the database.
        loadOrderDetails();
    }

    /**
     * Loads the details of the order from the database.
     * This method queries the database to retrieve all the items associated with the order
     * and populates the items and quantities in the order object.
     */
    private void loadOrderDetails() {
        // SQL query to fetch order details from the view which consolidates multiple item types (e.g., Pizza, Wine, etc.)
        String sqlQuery = "SELECT * FROM VIEW_ORDER_SUMMARY WHERE orderId = ?";

        try {
            // Execute the SQL query with the current order ID.
            ResultSet resultSet = DatabaseManager.sendQuery(sqlQuery, this.id);

            // Iterate over each row in the result set.
            while (resultSet.next()) {
                // Fetch the type and ID of the item in the current row.
                String itemType = resultSet.getString("itemType");
                int itemId = resultSet.getInt("itemId");

                // Convert the itemType and itemId into a Food object.
                // The method mapToFoodItem should create an instance of the correct subclass of Food based on the itemType.
                Food item = mapToFoodItem(itemType, itemId);

                // Add the Food item and its quantity to the respective lists in the Order object.
                this.items.add(item);
                this.quantity.add(resultSet.getInt("quantity"));

                // Set the date and time of the order.
                // This assumes all items in an order share the same order date and client.
                this.dateTime = resultSet.getDate("orderDate");
                this.clientName = resultSet.getString("clientLastName");
            }
        } catch (SQLException e) {
            // Log an error if there's an issue during database access.
            Logger logger = LoggerFactory.getLogger(Order.class);
            logger.error("Error loading order details", e);
        }
    }

    /**
     * Maps the specified item type and ID to a Food object.
     * This method is responsible for creating the correct type of Food object based on the item type
     * provided in the order details fetched from the database.
     *
     * @param itemType The type of the item to map. This is a string representing the type of Food object.
     * @param itemId   The ID of the item. This is used to load specific details for the Food object from the database.
     * @return A Food object that corresponds to the specified item type and ID.
     * The actual object returned will be an instance of a subclass of Food, based on the itemType.
     */
    private Food mapToFoodItem(String itemType, int itemId) {
        return switch (itemType) {
            // For each case, create an instance of the corresponding subclass of Food
            case "PIZZA" -> new Pizza(itemId); // Creates a Pizza object for items of type 'PIZZA'
            case "DESSERT" -> new Dessert(itemId); // Creates a Dessert object for items of type 'DESSERT'
            case "WINE" -> new Wine(itemId); // Creates a Wine object for items of type 'WINE'
            case "COCKTAIL" -> new Cocktail(itemId); // Creates a Cocktail object for items of type 'COCKTAIL'
            case "SODA" -> new Soda(itemId); // Creates a Soda object for items of type 'SODA'
            case "PIZZA CUSTOM" -> {
                // Special case for custom pizzas
                // Fetches the original pizza ID for the custom pizza, then creates a PizzaCustom object
                int originalPizzaId = fetchOriginalPizzaIdForCustomPizza(itemId);
                yield new PizzaCustom(originalPizzaId, itemId);
            }
            // Throws an exception if the item type is not recognized
            default -> throw new IllegalStateException("Unexpected value: " + itemType);
        };
    }

    /**
     * Fetches the original pizza ID for a given custom pizza ID.
     * This method queries the database to find the original pizza ID associated with a custom pizza.
     *
     * @param customPizzaId The ID of the custom pizza.
     * @return The ID of the original pizza that the custom pizza is based on.
     * @throws RuntimeException if there is an issue in fetching the data from the database.
     */
    private int fetchOriginalPizzaIdForCustomPizza(int customPizzaId) {
        // SQL query to fetch the original pizza ID from a view that maps custom pizzas to their original variants.
        String sqlQuery = "SELECT OriginalPizzaId FROM VIEW_CUSTOM_PIZZAS_WITH_INGREDIENTS WHERE CustomPizzaId = ?";

        try {
            // Execute the query with the custom pizza ID.
            ResultSet resultSet = DatabaseManager.sendQuery(sqlQuery, customPizzaId);

            // Check if the result set has data (i.e., if an original pizza ID was found).
            if (resultSet.next()) {
                // Return the original pizza ID.
                return resultSet.getInt("OriginalPizzaId");
            } else {
                // Throw an exception if the original pizza ID could not be found.
                throw new SQLException("Original pizza ID not found for custom pizza ID: " + customPizzaId);
            }
        } catch (SQLException e) {
            // Log the error and throw a RuntimeException to indicate an error during database access.
            Logger logger = LoggerFactory.getLogger(Order.class);
            logger.error("Error fetching original pizza ID for custom pizza", e);
            throw new RuntimeException(e);
        }
    }

    // Getters and setters for the Order class
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<Food> getItems() {
        return items;
    }

    public void setItems(ArrayList<Food> items) {
        this.items = items;
    }

    public ArrayList<Integer> getQuantity() {
        return quantity;
    }

    public void setQuantity(ArrayList<Integer> quantity) {
        this.quantity = quantity;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }


    /**
     * Provides a preview of the order, including its ID, date and time, and client name.
     * This method is used to display a list of orders in the application.
     *
     * @return A string representation of the order.
     */
    @Override
    public String toString() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM HH:mm");
        String strDate = formatter.format(getDateTime());
        return getId() + "      " + strDate + "     " + getClientName();

    }

    /**
     * Provides a string representation of the order, including its items and quantities, grouped by item type.
     * This method organizes the items in the order by their type and presents them in a structured format,
     * making it easier to read and understand the contents of the order.
     *
     * @return A string representation of the order.
     */
    public String display() {
        Map<String, ArrayList<String>> groupedItems = new HashMap<>();

        // Iterate through each item and group them by their class type.
        for (int i = 0; i < items.size(); i++) {
            Food item = items.get(i);
            Integer qty = quantity.get(i);

            // Get the simple class name as the group key and convert it to uppercase.
            String className = item.getClass().getSimpleName().toUpperCase();

            // Create a new group if it doesn't exist.
            groupedItems.computeIfAbsent(className, k -> new ArrayList<>());

            // Add the item's string representation and quantity to the group.
            groupedItems.get(className).add(item + " - Quantity: " + qty);
        }

        // Construct the final string representation.
        StringBuilder sb = new StringBuilder();

        // Append each group to the string builder.
        for (Map.Entry<String, ArrayList<String>> entry : groupedItems.entrySet()) {
            sb.append("\n").append(entry.getKey()).append(":\n");
            // Add each item in the group with numbering and indentation.
            for (int i = 0; i < entry.getValue().size(); i++) {
                sb.append(i + 1).append(". ").append(entry.getValue().get(i)).append("\n");
            }
        }

        return sb.toString();
    }

    /**
     * Updates the status of a specific order in the database based on the given order ID and status type.
     *
     * @param statusType An integer representing the status type (0 for refusal, 1 for validation).
     * @throws SQLException If a database access error occurs.
     */
    public void updateStatus(Status statusType) throws SQLException {
        String query;
        if (statusType == Status.CANCELED) { // Checks if the order is refused (0); if not 0, then it is validated.
            query = "UPDATE CLIENT_ORDER SET status = 'CANCELED' WHERE orderId = ?;";
        } else if (statusType == Status.PREPARATION) {
            query = "UPDATE CLIENT_ORDER SET status = 'PREPARATION' WHERE orderId = ?;";
        } else {
            throw new IllegalStateException("Unexpected value: " + statusType);
        }
        DatabaseManager.sendQuery(query, getId());
    }

    /**
     * Maps the enumerated status to a corresponding string for the database.
     *
     * @param status The enumerated status.
     * @return The corresponding string for the database.
     */
    private String mapStatusToString(Status status) {
        // Adapt this method according to the mapping between the enumeration and the database
        return switch (status) {
            case PENDING -> "PENDING";
            case CANCELED -> "CANCELED";
            case PREPARATION -> "PREPARATION";
            case SHIPPED -> "SHIPPED";
            case DELIVERED -> "DELIVERED";
            default -> throw new IllegalStateException("Unexpected status: " + status);
        };
    }

}
