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

    public ManagerView(OrderManager orderManagerModel) {
        super(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        setOrderManagerModel(orderManagerModel);
        init();
    }

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

    public void updateOrderList(ArrayList<Order> orders) {
        int selectedIndex = orderList.getSelectedIndex(); // Sauvegarde l'index sélectionné avant la mise à jour

        DefaultListModel<Order> listModel = new DefaultListModel<>();
        for (Order order : orders) {
            listModel.addElement(order);
        }
        orderList.setModel(listModel);

        // Rétablit la sélection si possible
        if (selectedIndex != -1 && selectedIndex < listModel.size()) {
            orderList.setSelectedIndex(selectedIndex);
        }
    }

    public void setOrderManagerModel(OrderManager orderManagerModel) {
        this.orderManagerModel = orderManagerModel;
    }

    public OrderManager getOrderManagerModel() {
        return orderManagerModel;
    }

    public SList<Order> getOrderList() {
        return orderList;
    }
}
