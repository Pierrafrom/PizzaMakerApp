package controller;

import model.MPizzaMakerApp;
import view.VPizzaMakerApp;

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

        // Adding listeners and connect view with model
        view.addPizzaSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedorder = view.orderIdList.getSelectedValue();
                model.setSelectedPizza(selectedorder);
            }
        });

        view.addPizzaSelectionListenerUpd(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedorder = view.orderIdListUpd.getSelectedValue();
                model.setSelectedPizza(selectedorder);
            }
        });

        // Adding ActionListener to the button in the view
        view.addValidatePizzaButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    handleValidatePizzaButtonClick(false);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        // Adding ActionListener to the updated button in the view
        view.addValidatePizzaButtonListenerUpd(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    handleValidatePizzaButtonClick(true);
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
                    handleRefusePizzaButtonClick(false);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        view.addRefusePizzaButtonListenerUpd(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    handleRefusePizzaButtonClick(true);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

    }



    /**
     * Handles the action event when the "VALIDATE ORDER" button is clicked.
     * Updates the view's right panel text and notifies the model that the
     * order has been validated.
     *
     * @param upd If {@code true}, the update mode is enabled.
     * @throws SQLException If a database access error occurs.
     */
    private void handleValidatePizzaButtonClick(boolean upd) throws SQLException {
        if (upd) {
            view.deleteAllComponents();
            model.updateOrderStatus(view.orderIdListUpd.getSelectedValue(), 1);
            view.update();
            System.out.println("validated");
        }
        else {
            view.deleteAllComponents();
            model.updateOrderStatus(view.orderIdList.getSelectedValue(), 1);
            view.update();
            System.out.println("validated");
        }
    }



    /**
     * Handles the action event when the "REFUSE ORDER" button is clicked.
     * Updates the view's right panel text and notifies the model that the
     * order has been refused.
     *
     * @param upd If {@code true}, the update mode is enabled.
     * @throws SQLException If a database access error occurs.
     */
    private void handleRefusePizzaButtonClick(boolean upd) throws SQLException {
        if (upd) {
            view.deleteAllComponents();
            model.updateOrderStatus(view.orderIdListUpd.getSelectedValue(), 1);
            view.update();
            System.out.println("canceled");
        }
        else {
            view.deleteAllComponents();
            model.updateOrderStatus(view.orderIdList.getSelectedValue(), 1);
            view.update();
            System.out.println("canceled");
        }
    }



    /**
     * Starts the pizza maker application by displaying the main application window.
     */
    public void startApplication() {
        view.show();
    }


}
