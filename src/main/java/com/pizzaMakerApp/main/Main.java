package com.pizzaMakerApp.main;

import com.pizzaMakerApp.controller.AppController;
import com.pizzaMakerApp.model.Order;
import com.pizzaMakerApp.model.OrderManager;
import com.pizzaMakerApp.view.MainFrameView;
import com.pizzaMakerApp.view.ManagerView;
import com.pizzaMakerApp.view.OrderView;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                runApp();
            }
        });
    }

    private static void runApp() {
        // Initialize your Model, View, and Controller
        OrderManager orderManagerModel = new OrderManager();
        Order orderModel = orderManagerModel.getOrders().get(0);

        // Create views
        ManagerView managerView = new ManagerView(orderManagerModel);
        OrderView orderView = new OrderView(orderModel);
        MainFrameView mainFrameView = new MainFrameView(orderView, managerView);

        // Set up the controller
        AppController appController = new AppController(orderManagerModel, managerView, orderModel, orderView);
    }
}