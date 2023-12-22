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

public class AppController {

    private OrderManager orderManagerModel;
    private ManagerView managerView;
    private Order orderModel;
    private OrderView orderView;

    public AppController(OrderManager orderManagerModel, ManagerView managerView, Order orderModel, OrderView orderView) {
        setManagerView(managerView);
        setOrderManagerModel(orderManagerModel);
        setOrderModel(orderModel);
        setOrderView(orderView);

        initListeners();
        initUpdateTimer();
    }

    private void initListeners() {
        managerView.getOrderList().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    Order selectedOrder = managerView.getOrderList().getSelectedValue();
                    if (selectedOrder != null) {
                        setOrderModel(selectedOrder); // Met à jour le modèle de commande dans le contrôleur
                        orderView.setOrderModel(selectedOrder); // Met à jour le modèle de commande dans la vue
                        updateOrderView(); // Met à jour la vue de commande pour afficher les détails de la nouvelle commande sélectionnée
                    }
                }
            }
        });

        JButton validateButton = orderView.getValidateButton();
        JButton refuseButton = orderView.getRefuseButton();

        validateButton.addActionListener(e -> validateOrder());
        refuseButton.addActionListener(e -> refuseOrder());
    }

    private void validateOrder() {
        if (orderModel != null) {
            try {
                orderModel.updateStatus(Order.Status.PREPARATION);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            updateOrderList(true);
        }
    }

    private void refuseOrder() {
        if (orderModel != null) {
            try {
                orderModel.updateStatus(Order.Status.CANCELED);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            updateOrderList(true);
        }
    }

    private void initUpdateTimer() {
        // Crée un Timer qui se déclenche toutes les 10 secondes (10000 millisecondes)
        Timer updateTimer = new Timer(60000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateOrderList(false);
            }
        });

        updateTimer.start();
    }

    public void updateOrderView() {
        orderView.update();
    }

    public void updateOrderList(boolean selectfirst) {
        orderManagerModel.getOrders().clear(); // Vide la liste des commandes
        orderManagerModel.loadOrders(); // Recharge les commandes depuis la base de données
        managerView.updateOrderList(orderManagerModel.getOrders()); // Met à jour la liste des commandes dans la vue
        if (selectfirst) {
            managerView.getOrderList().setSelectedIndex(0);
        }
    }

    public OrderManager getOrderManagerModel() {
        return orderManagerModel;
    }

    public void setOrderManagerModel(OrderManager orderManagerModel) {
        this.orderManagerModel = orderManagerModel;
    }

    public ManagerView getManagerView() {
        return managerView;
    }

    public void setManagerView(ManagerView managerView) {
        this.managerView = managerView;
    }

    public Order getOrderModel() {
        return orderModel;
    }

    public void setOrderModel(Order orderModel) {
        this.orderModel = orderModel;
    }

    public OrderView getOrderView() {
        return orderView;
    }

    public void setOrderView(OrderView orderView) {
        this.orderView = orderView;
    }
}
