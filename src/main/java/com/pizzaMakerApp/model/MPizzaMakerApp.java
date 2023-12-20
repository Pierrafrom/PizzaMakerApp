package com.pizzaMakerApp.model;

import com.pizzaMakerApp.view.VPizzaMakerApp;
import java.sql.SQLException;
import static com.pizzaMakerApp.utils.DatabaseManager.sendQuery;

/**
 * The {@code MPizzaMakerApp} class represents the model in the Model-View-Controller (MVC) architecture
 * for the pizza maker application. It manages the application's data
 *
 * @author Samuel Boix-Segura
 * @since 2023-12-10
 */
public class MPizzaMakerApp {

    /**
     * Updates the status of a specific order in the database based on the given order ID and status type.
     *
     * @param orderId    The ID of the order to be updated.
     * @param statusType An integer representing the status type (0 for refusal, 1 for validation).
     * @throws SQLException If a database access error occurs.
     */
    public void updateOrderStatus(int orderId, int statusType) throws SQLException {
        String query;
        if(statusType == 0) { // Checks if the order is refused (0); if not 0, then it is validated.
            query = "UPDATE CLIENT_ORDER SET status = 'CANCELED' WHERE orderId = ?;";
        }
        else {
            query = "UPDATE CLIENT_ORDER SET status = 'PREPARATION' WHERE orderId = ?;";
        }
        Object[] tab = {orderId};
        sendQuery(query, tab);
    }
}
