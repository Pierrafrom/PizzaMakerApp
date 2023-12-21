package com.pizzaMakerApp.view;

import com.pizzaMakerApp.utils.DatabaseManager;
import com.pizzaMakerApp.style.*;
import com.pizzaMakerApp.utils.ImageLoader;
import com.pizzaMakerApp.utils.ImageResizer;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.lang.reflect.Type;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.pizzaMakerApp.utils.DatabaseManager.sendQuery;


/**
 * The {@code VPizzaMakerApp} class represents the graphical user interface for a pizza maker application.
 * It allows the user to view and interact with pizza orders, display order details, and validate orders.
 * The application uses Swing components for the graphical interface and interacts with a MariaDB database
 * to retrieve and display order information.
 *
 * @author Samuel Boix-Segura
 * @since 2023-12-10
 */
public class VPizzaMakerApp {
    private SFrame frame = new SFrame("Pizza Maker App 🍕");
    private int selectedItem;
    private STextArea textArea = new STextArea();
    private DefaultListModel<String> listModel;
    SList<String> sList = new SList<>();
    private SButton validateBtn = new SButton("VALIDATE ORDER", SButton.ButtonType.PRIMARY, 10);
    //for now the app will refresh itslef every 10 seconds so no need to use this button
    //private SButton refreshBtn = new SButton("REFRESH ORDERS", SButton.ButtonType.NEUTRAL, 10);
    private SButton refuseBtn = new SButton("REFUSE ORDER", SButton.ButtonType.ERROR, 10);


    /**
     * Constructs a new {@code VPizzaMakerApp} instance.
     * Initializes the main window, split panel, left and right panels, and components such as JList and JTextArea.
     * Also populates the JList with order IDs retrieved from the database.
     */
    public VPizzaMakerApp() {

        SPanel contentPanel = new SPanel(new BorderLayout());

        SPanel titlePanel = new SPanel(new FlowLayout(FlowLayout.CENTER), new Dimension(1920, 100));
        // load the logo
        ImageIcon imageIcon = ImageLoader.loadImageIcon("logo.png");
        if (imageIcon != null) {
            BufferedImage bufferedImage = ImageResizer.iconToBufferedImage(imageIcon);

            BufferedImage resizedImage = ImageResizer.resizeImage(bufferedImage, 100, 100);

            ImageIcon resizedIcon = new ImageIcon(resizedImage);

            JLabel imageLabel = new JLabel(resizedIcon);
            titlePanel.add(imageLabel);
        }
        else {
            System.err.println("Image not found or could not be loaded.");
        }
        SLabel titleLabel = new SLabel("Pizza Maker App", SLabel.FontStyle.TITLE);
        titlePanel.add(titleLabel);
        contentPanel.add(titlePanel, BorderLayout.NORTH);

        SSplitPane splitPane = new SSplitPane();
        listModel = new DefaultListModel<>();
        populateListModel(listModel);
        sList = new SList<>(listModel);
        SScrollPane leftPane = new SScrollPane(sList, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        splitPane.setLeftComponent(leftPane);

        textArea.setText("SELECT AN ORDER");
        SScrollPane rightScrollPane = new SScrollPane(textArea);
        rightScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        rightScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);


        SPanel rightPanel = new SPanel(new BorderLayout());
        // Panel to hold both buttons with BoxLayout for horizontal centering
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));
        buttonPanel.add(Box.createHorizontalGlue());
        buttonPanel.add(validateBtn);
        buttonPanel.add(Box.createHorizontalStrut(10));
        buttonPanel.add(refuseBtn);
        //buttonPanel.add(Box.createHorizontalStrut(10));
        //buttonPanel.add(refreshBtn);
        buttonPanel.add(Box.createHorizontalGlue());
        buttonPanel.setBackground(Style.BACKGROUND_COLOR);
        // Directly adding the button panel to the right panel at the bottom
        rightPanel.add(buttonPanel, BorderLayout.SOUTH);
        rightPanel.add(rightScrollPane);
        splitPane.setRightComponent(rightPanel);



        sList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String selectedValue = sList.getSelectedValue();
                if (selectedValue != null) {
                    // Splitting the selected value based on the hyphen delimiter
                    String[] parts = selectedValue.split("-");
                    if (parts.length > 0) {
                        // Extracting the identifier
                        String identifier = parts[0].trim();
                        // Converting the identifier to an integer if needed
                        try {
                            selectedItem = Integer.parseInt(identifier);
                            textArea.setText(displayOrderInfo(selectedItem));
                        }
                        catch (NumberFormatException ex) {
                            // Handle the case when the identifier is not a valid integer
                            textArea.setText("Invalid identifier");
                        }
                    }
                }
            }
        });


        contentPanel.add(splitPane, BorderLayout.CENTER);

        frame.setContentPane(contentPanel);
        frame.setVisible(true);
    }



    /**
     * Adds an {@code ActionListener} to the "VALIDATE ORDER" button to listen for button clicks.
     * When the button is clicked, the provided listener is notified.
     *
     * @param listener The {@code ActionListener} to be added.
     */
    public void addValidatePizzaButtonListener(ActionListener listener) {
        validateBtn.addActionListener(listener);
    }

    /**
     * Adds an {@code ActionListener} to the "REFUSE ORDER" button to listen for button clicks.
     * When the button is clicked, the provided listener is notified.
     *
     * @param listener The {@code ActionListener} to be added.
     */
    public void addRefusePizzaButtonListener(ActionListener listener) {
        refuseBtn.addActionListener(listener);
    }
/*
    **
     * Adds an {@code ActionListener} to the "REFRESH ORDERS" button to listen for button clicks.
     * When the button is clicked, the provided listener is notified.
     *
     * @param listener The {@code ActionListener} to be added.
     *
    public void addRefreshPizzaButtonListener(ActionListener listener) {
        refreshBtn.addActionListener(listener);
    }
*/

    /**
     * Displays the main application window.
     * Sets the size, location, and visibility of the frame.
     */
    public void show() {
        frame.setSize(1000, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    /**
     * Populates the DefaultListModel with order IDs and dates retrieved from the database.
     * Uses a SQL query to fetch distinct order IDs and dates from the "VIEW_ORDER_SUMMARY" view, the results are ordered by the date.
     *
     * @param listModel The DefaultListModel to be populated with order IDs & dates.
     */
    private void populateListModel(DefaultListModel<String> listModel) {
        try {
            String query = "SELECT DISTINCT orderId, orderDate, clientLastName FROM VIEW_ORDER_SUMMARY WHERE status = 'PENDING' ORDER BY orderDate";
            try (ResultSet resultSet = sendQuery(query,null)) {
                while (resultSet.next()) {
                    int orderId = resultSet.getInt("orderId");
                    String orderDate = resultSet.getString("orderDate");
                    String clientName = resultSet.getString("clientLastName");
                    listModel.addElement(orderId+"-  |  name: "+ clientName +"  |  time: "+orderDate);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Displays order information in the JTextArea based on the provided order ID.
     * Retrieves order details from the database and formats them for display.
     *
     * @param orderId The ID of the order for which information should be displayed.
     * @return A formatted string containing the order details.
     */
    public String displayOrderInfo(int orderId) {
        try {
            // SQL query to retrieve order details for the specified order ID
            String query = "SELECT * FROM VIEW_ORDER_SUMMARY WHERE orderId = " + orderId;
            // Executing the query
            try (ResultSet resultSet = sendQuery(query, null)) {
                StringBuilder orderDetails = new StringBuilder();
                // Iterating through the result set and add each row to the orderDetails StringBuilder
                while (resultSet.next()) {
                    String itemType = resultSet.getString("itemType");
                    String itemName = resultSet.getString("itemName");
                    int itemId = resultSet.getInt("itemId");
                    int quantity = resultSet.getInt("quantity");
                    double price = resultSet.getDouble("price");
                    String unit = resultSet.getString("unit");

                    StringBuilder ingredients = new StringBuilder();

                    if ("PIZZA".equals(itemType) || "PIZZA CUSTOM".equals(itemType)) {
                        String queryIngredients = "SELECT * FROM VIEW_PIZZA_INGREDIENTS WHERE id = " + itemId;
                        try (ResultSet rs = sendQuery(queryIngredients, null)) {
                            while (rs.next()) {
                                String ingredientName = rs.getString("ingredientName");
                                float quantityIngredient = rs.getFloat("quantity");
                                String unitIngredient = rs.getString("unit");
                                ingredients.append(ingredientName).append(" ").append(quantityIngredient).append(unitIngredient).append(" | ");
                            }
                        }
                    }
                    if ("DESSERT".equals(itemType)) {
                        String queryIngredients = "SELECT * FROM VIEW_DESSERT_INGREDIENTS WHERE id = " + itemId;
                        try (ResultSet rs = sendQuery(queryIngredients, null)) {
                            while (rs.next()) {
                                String ingredientName = rs.getString("ingredientName");
                                float quantityIngredient = rs.getFloat("quantity");
                                String unitIngredient = rs.getString("unit");
                                ingredients.append(ingredientName).append(" ").append(quantityIngredient).append(unitIngredient).append(" | ");
                            }
                        }
                    }
                    if ("COCKTAIL".equals(itemType)) {
                        String queryIngredients = "SELECT * FROM VIEW_COCKTAIL_INGREDIENTS WHERE id = " + itemId;
                        try (ResultSet rs = sendQuery(queryIngredients, null)) {
                            while (rs.next()) {
                                String ingredientName = rs.getString("ingredientName");
                                float quantityIngredient = rs.getFloat("quantity");
                                String unitIngredient = rs.getString("unit");
                                ingredients.append(ingredientName).append(" ").append(quantityIngredient).append(unitIngredient).append(" | ");
                            }
                        }
                    }

                    orderDetails.append("Item Type: ").append(itemType).append("\n");
                    orderDetails.append("Item Name: ").append(itemName).append("\n");
                    orderDetails.append("Ingredients: ").append(ingredients).append("\n");
                    orderDetails.append("Quantity: ").append(quantity).append("\n");
                    orderDetails.append("Price: ").append(price).append("\n");

                    if (unit != null) {
                        orderDetails.append("Unit: ").append(unit).append("\n");
                    }

                    orderDetails.append("\n");
                }
                return orderDetails.toString();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "error";
        }
    }

    /**
     * Gets the selected item from the list.
     *
     * @return The selected item ID.
     */
    public int getSelectedItem() {
        return selectedItem;
    }

    /**
     * Updates the list of orders by clearing and repopulating the list model.
     * @param refreshAll if it is true we refresh all the app, if not we are refreshing the app,
     * but we are keeping the selected values
     *
     */
    public void update(boolean refreshAll) {
        int selectedIndex = sList.getSelectedIndex();
        listModel.clear();
        populateListModel(listModel);
        if(refreshAll) {
            // Set the selected element to the first line
            if (listModel.getSize() > 0) {
                selectedItem = extractOrderId(listModel.getElementAt(0));
                // Assuming extractOrderId is a method that extracts the order ID from the formatted string
                textArea.setText(displayOrderInfo(selectedItem));
                sList.setSelectedIndex(0);
            }
        }
        else {
            if (listModel.getSize() > 0) {
                sList.setSelectedIndex(selectedIndex);
            }
        }
    }

    /**
     * Extracts the order ID from the formatted string.
     *
     * @param formattedString The string containing order details.
     * @return The extracted order ID.
     */
    private int extractOrderId(String formattedString) {
        // Splitting the formatted string based on the hyphen delimiter
        String[] parts = formattedString.split("-");
        if (parts.length > 0) {
            // Extracting the identifier
            String identifier = parts[0].trim();
            // Converting the identifier to an integer if needed
            try {
                return Integer.parseInt(identifier);
            } catch (NumberFormatException ex) {
                return -1;
            }
        } else {
            return -1;
        }
    }

}