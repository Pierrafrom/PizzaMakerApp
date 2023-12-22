/**
 * Represents the ManagerView component of the Pizza Maker application. Displays a scrollable
 * list of orders managed by the OrderManager.
 *
 * @author [Your Name]
 * @version 1.0
 * @since [Date]
 */
package com.pizzaMakerApp.view;

import com.pizzaMakerApp.model.Order;
import com.pizzaMakerApp.model.OrderManager;
import com.pizzaMakerApp.style.SList;
import com.pizzaMakerApp.style.SScrollPane;

import javax.swing.*;
import java.util.ArrayList;

public class ManagerView extends SScrollPane {

    private OrderManager orderManagerModel;

    private SList<Order> orderList;

    /**
     * Constructs the ManagerView with the specified OrderManager.
     *
     * @param orderManagerModel The OrderManager associated with this view.
     */
    public ManagerView(OrderManager orderManagerModel) {
        super(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        setOrderManagerModel(orderManagerModel);
        init();
    }

    /**
     * Initializes the ManagerView by creating and setting up the order list.
     */
    private void init() {
        // Create a new SList for Orders.
        orderList = new SList<>();

        // Retrieve the list of Orders from the OrderManager.
        DefaultListModel<Order> listModel = new DefaultListModel<>();
        for (Order order : orderManagerModel.getOrders()) {
            listModel.addElement(order);
        }

        // Set the model for the order list.
        orderList.setModel(listModel);

        // Set the SList as the viewport view of the scroll pane.
        this.setViewportView(orderList);
    }

    /**
     * Updates the order list with the provided list of orders. Retains the selected index
     * if possible after the update.
     *
     * @param orders The list of orders to update the view with.
     */
    public void updateOrderList(ArrayList<Order> orders) {
        int selectedIndex = orderList.getSelectedIndex(); // Save the selected index before the update

        DefaultListModel<Order> listModel = new DefaultListModel<>();
        for (Order order : orders) {
            listModel.addElement(order);
        }
        orderList.setModel(listModel);

        // Restore the selection if possible
        if (selectedIndex != -1 && selectedIndex < listModel.size()) {
            orderList.setSelectedIndex(selectedIndex);
        }
    }

    /**
     * Sets the OrderManager associated with this ManagerView.
     *
     * @param orderManagerModel The OrderManager to set.
     */
    public void setOrderManagerModel(OrderManager orderManagerModel) {
        this.orderManagerModel = orderManagerModel;
    }

    /**
     * Retrieves the OrderManager associated with this ManagerView.
     *
     * @return The OrderManager.
     */
    public OrderManager getOrderManagerModel() {
        return orderManagerModel;
    }

    /**
     * Retrieves the SList component representing the order list.
     *
     * @return The SList component.
     */
    public SList<Order> getOrderList() {
        return orderList;
    }
}
