package com.pizzaMakerApp.controller;

import com.pizzaMakerApp.model.Order;
import com.pizzaMakerApp.model.OrderManager;
import com.pizzaMakerApp.view.ManagerView;
import com.pizzaMakerApp.view.OrderView;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

/**
 * Controller class for the Pizza Maker application. Manages the interaction between the model
 * (OrderManager and Order) and the views (ManagerView and OrderView).
 *
 * The controller initializes listeners for the ManagerView's order list, as well as buttons in
 * the OrderView. It provides methods to handle order validation, refusal, and updates.
 *
 * Additionally, the controller sets up a timer to periodically update the order list.
 *
 */
public class AppController {

    private OrderManager orderManagerModel;
    private ManagerView managerView;
    private Order orderModel;
    private OrderView orderView;

    /**
     * Initializes listeners for the ManagerView's order list and OrderView's buttons.
     * Also sets up a timer for periodic order updates.
     *
     * @param orderManagerModel The model managing orders.
     * @param managerView The view for managing orders.
     * @param orderModel The model representing an individual order.
     * @param orderView The view for displaying an individual order.
     */
    public AppController(OrderManager orderManagerModel, ManagerView managerView, Order orderModel, OrderView orderView) {
        setManagerView(managerView);
        setOrderManagerModel(orderManagerModel);
        setOrderModel(orderModel);
        setOrderView(orderView);

        // Initialize listeners for user interactions and set up periodic order updates
        initListeners();
        initUpdateTimer();
    }


    /**
     * Initializes listeners for user interactions, such as selecting orders in the ManagerView's order list
     * and interacting with buttons in the OrderView.
     */
    private void initListeners() {
        // Add a list selection listener to the order list in the ManagerView
        managerView.getOrderList().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                // Check if the value change is not adjusting
                if (!e.getValueIsAdjusting()) {
                    // Retrieve the selected order from the order list
                    Order selectedOrder = managerView.getOrderList().getSelectedValue();
                    if (selectedOrder != null) {
                        setOrderModel(selectedOrder); // Update the order model in the controller
                        orderView.setOrderModel(selectedOrder); // Update the order model in the view
                        updateOrderView(); // Update the order view to display details of the newly selected order
                    }
                }
            }
        });

        // Get buttons from the OrderView for validation and refusal actions
        JButton validateButton = orderView.getValidateButton();
        JButton refuseButton = orderView.getRefuseButton();

        // Add action listeners to the validation and refusal buttons
        validateButton.addActionListener(e -> validateOrder());
        refuseButton.addActionListener(e -> refuseOrder());
    }


    /**
     * Validates the selected order by updating its status to "PREPARATION" and
     * triggers an update of the order list.
     */
    private void validateOrder() {
        // Check if there is a selected order
        if (orderModel != null) {
            try {
                // Update the status of the order to "PREPARATION"
                orderModel.updateStatus(Order.Status.PREPARATION);
            } catch (SQLException e) {
                // Propagate a runtime exception if a SQL exception occurs during the update
                throw new RuntimeException(e);
            }
            // Update the order list, selecting the first item if specified
            updateOrderList(true);
        }
    }


    /**
     * Refuses the selected order by updating its status to "CANCELED" and
     * triggers an update of the order list.
     */
    private void refuseOrder() {
        // Check if there is a selected order
        if (orderModel != null) {
            try {
                // Update the status of the order to "CANCELED"
                orderModel.updateStatus(Order.Status.CANCELED);
            } catch (SQLException e) {
                // Propagate a runtime exception if a SQL exception occurs during the update
                throw new RuntimeException(e);
            }
            // Update the order list, selecting the first item if specified
            updateOrderList(true);
        }
    }


    /**
     * Initializes a timer that triggers an update of the order list at regular intervals.
     * The timer is set to fire every 60 seconds (60000 milliseconds).
     */
    private void initUpdateTimer() {
        // Create a Timer that fires an action event every 60 seconds
        Timer updateTimer = new Timer(60000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Trigger an update of the order list without selecting the first item
                updateOrderList(false);
            }
        });

        // Start the timer
        updateTimer.start();
    }


    /**
     * Updates the OrderView to reflect changes in the underlying data model.
     * This method triggers the update mechanism in the OrderView.
     */
    public void updateOrderView() {
        orderView.update();
    }

    /**
     * Updates the order list in the ManagerView by clearing the current list of orders,
     * reloading orders from the database, and updating the view with the refreshed order list.
     *
     * @param selectfirst If true, selects the first item in the updated order list.
     */
    public void updateOrderList(boolean selectfirst) {
        // Clear the existing list of orders
        orderManagerModel.getOrders().clear();

        // Load orders from the database into the OrderManager model
        orderManagerModel.loadOrders();

        // Update the order list in the ManagerView with the refreshed order list
        managerView.updateOrderList(orderManagerModel.getOrders());

        // Select the first item in the order list if specified
        if (selectfirst) {
            managerView.getOrderList().setSelectedIndex(0);
        }
    }


    /**
     * Retrieves the OrderManager model associated with this controller.
     *
     * @return The OrderManager model.
     */
    public OrderManager getOrderManagerModel() {
        return orderManagerModel;
    }

    /**
     * Sets the OrderManager model for this controller.
     *
     * @param orderManagerModel The OrderManager model to set.
     */
    public void setOrderManagerModel(OrderManager orderManagerModel) {
        this.orderManagerModel = orderManagerModel;
    }

    /**
     * Retrieves the ManagerView associated with this controller.
     *
     * @return The ManagerView.
     */
    public ManagerView getManagerView() {
        return managerView;
    }

    /**
     * Sets the ManagerView for this controller.
     *
     * @param managerView The ManagerView to set.
     */
    public void setManagerView(ManagerView managerView) {
        this.managerView = managerView;
    }

    /**
     * Retrieves the Order model associated with this controller.
     *
     * @return The Order model.
     */
    public Order getOrderModel() {
        return orderModel;
    }

    /**
     * Sets the Order model for this controller.
     *
     * @param orderModel The Order model to set.
     */
    public void setOrderModel(Order orderModel) {
        this.orderModel = orderModel;
    }

    /**
     * Retrieves the OrderView associated with this controller.
     *
     * @return The OrderView.
     */
    public OrderView getOrderView() {
        return orderView;
    }

    /**
     * Sets the OrderView for this controller.
     *
     * @param orderView The OrderView to set.
     */
    public void setOrderView(OrderView orderView) {
        this.orderView = orderView;
    }
}
