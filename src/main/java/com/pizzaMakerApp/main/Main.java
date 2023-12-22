package com.pizzaMakerApp.main;

import com.pizzaMakerApp.controller.AppController;
import com.pizzaMakerApp.model.Order;
import com.pizzaMakerApp.model.OrderManager;
import com.pizzaMakerApp.view.MainFrameView;
import com.pizzaMakerApp.view.ManagerView;
import com.pizzaMakerApp.view.OrderView;

import javax.swing.*;

/**
 * The main class of the Pizza Maker application.
 * This class initializes the Model, View, and Controller components of the application
 * and starts the graphical user interface using Swing.
 * <p>
 * The application consists of a manager view, an order view, and the main frame view
 * that brings them together. The controller is responsible for handling user input
 * and updating the underlying model accordingly.
 * </p>
 * <p>
 * To run the application, execute the main method, which sets up the GUI on the
 * Event Dispatch Thread using {@link SwingUtilities#invokeLater(Runnable)}.
 * </p>
 * <p>
 * The Model component includes an {@link OrderManager} and an {@link Order}.
 * The views include a {@link ManagerView} and an {@link OrderView}. The {@link MainFrameView}
 * combines the order and manager views into a single frame.
 * </p>
 * <p>
 * The controller, represented by the {@link AppController}, manages the interactions
 * between the views and the underlying model.
 * </p>
 * <p>
 * Example usage:
 * <pre>
 * {@code
 * public static void main(String[] args) {
 *     SwingUtilities.invokeLater(new Runnable() {
 *         public void run() {
 *             runApp();
 *         }
 *     });
 * }
 * }
 * </pre>
 * </p>
 *
 * @see OrderManager
 * @see Order
 * @see ManagerView
 * @see OrderView
 * @see MainFrameView
 * @see AppController
 */
public class Main {
    /**
     * Main method to start the Pizza Maker application.
     * Invokes the {@link #runApp()} method on the Event Dispatch Thread.
     *
     * @param args Command line arguments (unused in this application).
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                runApp();
            }
        });
    }

    /**
     * Initializes the Model, View, and Controller components of the Pizza Maker application.
     * Creates instances of the {@link OrderManager}, {@link Order}, {@link ManagerView},
     * {@link OrderView}, {@link MainFrameView}, and {@link AppController}.
     */
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
