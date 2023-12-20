package com.pizzaMakerApp.controller;

import com.pizzaMakerApp.model.MPizzaMakerApp;
import com.pizzaMakerApp.view.VPizzaMakerApp;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;


/**
 * The {@code CPizzaMakerApp} class serves as the controller in the Model-View-Controller (MVC) architecture
 * for the pizza maker application. It connects the model ({@code MPizzaMakerApp}) and the view ({@code VPizzaMakerApp}),
 * facilitating communication between them. This controller class handles user interactions and updates
 * the model accordingly.
 *
 * @author Samuel Boix-Segura
 * @since 2023-12-10
 */
public class CPizzaMakerApp {
    private MPizzaMakerApp model;
    private VPizzaMakerApp view;

    /**
     * Constructs a CPizzaMakerApp instance with the specified model and view.
     * Initializes the model and view components and establishes the necessary listeners.
     *
     * @param model The model component ({@code MPizzaMakerApp}) for handling application data.
     * @param view  The view component ({@code VPizzaMakerApp}) for displaying the graphical user interface.
     */
    public CPizzaMakerApp(MPizzaMakerApp model, VPizzaMakerApp view) {
        this.model = model;
        this.view = view;

        // Adding ActionListener to the button in the view
        view.addValidatePizzaButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    handleValidatePizzaButtonClick();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });



        // Adding ActionListener to the button in the view
        view.addRefusePizzaButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    handleRefusePizzaButtonClick();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        // Adding ActionListener to the button in the view
        view.addRefreshPizzaButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    handleRefreshPizzaButtonClick();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });


    }



    /**
     * Handles the action event when the "VALIDATE ORDER" button is clicked.
     * Updates the view's right panel text and notifies that the
     * order has been validated.
     *
     * @throws SQLException If a database access error occurs.
     */
    private void handleValidatePizzaButtonClick() throws SQLException {
        model.updateOrderStatus(view.getSelectedItem(), 1);
        view.update();
        System.out.println("validated");

    }



    /**
     * Handles the action event when the "REFUSE ORDER" button is clicked.
     * Updates the view's right panel text and notifies that the
     * order has been refused.
     *
     * @throws SQLException If a database access error occurs.
     */
    private void handleRefusePizzaButtonClick() throws SQLException {
        model.updateOrderStatus(view.getSelectedItem(), 0);
        view.update();
        System.out.println("canceled");
    }

    /**
     * Handles the action event when the "REFRESH ORDER" button is clicked.
     * Updates the whole app and notifies that the
     * order has been refused.
     *
     * @throws SQLException If a database access error occurs.
     */
    private void handleRefreshPizzaButtonClick() throws SQLException {
        view.update();
        System.out.println("orders refreshed");
    }



    /**
     * Starts the pizza maker application by displaying the main application window.
     */
    public void startApplication() {
        view.show();
    }


}