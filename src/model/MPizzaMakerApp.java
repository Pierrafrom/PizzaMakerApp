package model;


import view.VPizzaMakerApp;

import java.sql.SQLException;

import static utils.DatabaseManager.executeQuery2;

/**
 * The MPizzaMakerApp class represents the model in the Model-View-Controller (MVC) architecture
 * for the pizza maker application. It holds the application's data and provides methods for accessing
 * and modifying that data. In this case, it specifically manages the currently selected pizza.
 *
 * @author Samuel Boix-Segura
 * @since 2023-12-10
 */

public class MPizzaMakerApp {
    private int selectedPizza;

    /**
     * Gets the currently selected pizza.
     *
     * @return A string representing the currently selected pizza.
     */
    public int getSelectedPizza() {
        return selectedPizza;
    }

    /**
     * Sets the selected pizza to the specified value.
     *
     * @param selectedPizza A string representing the newly selected pizza.
     */
    public void setSelectedPizza(int selectedPizza) {
        this.selectedPizza = selectedPizza;
    }

    public void updateOrderStatus(int orderId, int statusType) throws SQLException {
        String query;
        if(statusType == 0) { // checks if the order is refused (0) if not 0 then it is validated
            query = "UPDATE CLIENT_ORDER SET status = 'CANCELED' WHERE orderId = ?;";
        }
        else {
            query = "UPDATE CLIENT_ORDER SET status = 'PREPARATION' WHERE orderId = ?;";
        }
        executeQuery2(query, orderId);
    }




}
