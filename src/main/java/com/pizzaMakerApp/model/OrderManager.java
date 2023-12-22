package com.pizzaMakerApp.model;

import com.pizzaMakerApp.utils.DatabaseManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Manages a collection of Order objects within the pizza maker application.
 * This class is responsible for loading and accessing orders from the database.
 */
public class OrderManager {
    // A list to store Order objects.
    private ArrayList<Order> orders;

    /**
     * Constructor for OrderManager.
     * Initializes the order list and loads orders from the database upon creation.
     */
    public OrderManager() {
        // Initialize the orders ArrayList.
        this.orders = new ArrayList<>();

        // Load orders from the database.
        loadOrders();
    }

    /**
     * Loads orders from the database.
     * This method fetches order data from the VIEW_ORDER_SUMMARY and creates Order objects.
     */
    public void loadOrders() {
        // SQL query to fetch unique orders. Orders are sorted by date.
        String sqlQuery = "SELECT DISTINCT orderId, orderDate FROM VIEW_ORDER_SUMMARY " +
                "WHERE status = 'PENDING' ORDER BY orderDate";
        try {
            // Execute the SQL query.
            ResultSet resultSet = DatabaseManager.sendQuery(sqlQuery);

            // Iterate through the result set to create and add Order objects to the list.
            while (resultSet.next()) {
                int orderId = resultSet.getInt("orderId");
                Order order = new Order(orderId);
                orders.add(order);
            }
        } catch (SQLException e) {
            // Log an error in case of SQL exception.
            Logger logger = LoggerFactory.getLogger(OrderManager.class);
            logger.error("Error loading orders", e);
        }
    }

    /**
     * Retrieves a list of all orders.
     *
     * @return An ArrayList containing all Order objects.
     */
    public ArrayList<Order> getOrders() {
        return orders;
    }

    /**
     * Retrieves an order by its ID.
     *
     * @param id The ID of the order to retrieve.
     * @return The Order object with the specified ID.
     * @throws IllegalArgumentException if an order with the given ID does not exist.
     */
    public Order getOrderById(int id) throws IllegalArgumentException {
        // Iterate through the list of orders to find the one with the matching ID.
        for (Order order : orders) {
            if (order.getId() == id) {
                return order;
            }
        }

        // If no order is found with the given ID, throw an IllegalArgumentException.
        throw new IllegalArgumentException("Order with ID " + id + " not found");
    }
}
