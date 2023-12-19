package com.pizzaMakerApp.view;

import com.pizzaMakerApp.style.*;
import com.pizzaMakerApp.utils.DatabaseManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CenterView extends SSplitPane {
    private SList<String> commandList;
    private JTextArea detailTextArea;
    private JButton acceptButton, refuseButton;

    public CenterView() {
        super();
        initComponents();
        loadData();
    }

    private void initComponents() {
        // Initialisation de la SList
        commandList = new SList<>(new DefaultListModel<>());
        SScrollPane leftPane = new SScrollPane(commandList);
        setLeftComponent(leftPane);

        // Initialisation du panneau de détails
        detailTextArea = new JTextArea();
        SScrollPane detailScrollPane = new SScrollPane(detailTextArea);
        JPanel detailPanel = new JPanel(new BorderLayout());
        detailPanel.add(detailScrollPane, BorderLayout.CENTER);

        // Initialisation des boutons
        JPanel buttonPanel = new JPanel();
        acceptButton = new JButton("Accepter");
        refuseButton = new JButton("Refuser");
        buttonPanel.add(acceptButton);
        buttonPanel.add(refuseButton);
        detailPanel.add(buttonPanel, BorderLayout.SOUTH);

        setRightComponent(detailPanel);

        // Ajouter des gestionnaires d'événements si nécessaire
        // Par exemple, pour la sélection d'éléments de la liste et les clics sur les boutons
    }

    private void loadData() {
        try {
            // Remplacer 'YOUR_SQL_QUERY' par la requête SQL appropriée
            ResultSet rs = DatabaseManager.sendQuery("SELECT * FROM VIEW_ORDER_SUMMARY", 0);
            updateView(rs);
        } catch (SQLException e) {
            e.printStackTrace();
            // Gérer l'exception
        }
    }

    private void updateView(ResultSet resultSet) throws SQLException {
        DefaultListModel<String> model = (DefaultListModel<String>) commandList.getModel();
        model.clear();
        while (resultSet.next()) {
            String orderSummary = "Commande ID: " + resultSet.getString("orderId")
                    + ", Article: " + resultSet.getString("itemName")
                    + ", Quantité: " + resultSet.getString("quantity");
            model.addElement(orderSummary);
        }
    }
}

