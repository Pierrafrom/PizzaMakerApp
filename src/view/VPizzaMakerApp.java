package view;

import config.DBconfig;
import style.*;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static utils.DatabaseManager.executeQuery;



/**
 * The VPizzaMakerApp class represents the graphical user interface for a pizza maker application.
 * It allows the user to view and interact with pizza orders, display order details, and validate orders.
 * The application uses Swing components for the graphical interface and interacts with a MariaDB database
 * to retrieve and display order information.
 *
 * @author Samuel Boix-Segura
 * @since 2023-12-10
 */
public class VPizzaMakerApp {
    private SFrame frame;
    private SSplitPanel splitPanel;
    public JList<Integer> orderIdList;
    private JTextArea textArea;
    private SButton bigButton;
    private SButton refuseButton;
    int selectedOrder;



    /**
     * Constructs a new VPizzaMakerApp instance.
     * Initializes the main window, split panel, left and right panels, and components such as JList and JTextArea.
     * Also populates the JList with order IDs retrieved from the database.
     */
    public VPizzaMakerApp() {
        // Creating the main window
        frame = new SFrame("TESTING 🔥🥵💀⚠️🥶🥶🥶");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Creating the SSplitPanel
        splitPanel = new SSplitPanel();


        //************************************* Left panel customization ***************************************
        SPanel leftPanel = splitPanel.getLeftPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));

        // Creating a DefaultListModel for Integer
        DefaultListModel<Integer> listModel = new DefaultListModel<>();

        // Populating the list model with data from the "VIEW_ORDER_SUMMARY" view
        populateListModel(listModel);

        // Creating a JList with the list model
        orderIdList = new JList<>(listModel);
        orderIdList.setFont(Style.TEXT_FONT);
        orderIdList.setForeground(Style.TEXT_COLOR);
        orderIdList.setBackground(Style.BACKGROUND_COLOR);
        orderIdList.setSelectionForeground(Style.TEXT_COLOR);
        orderIdList.setSelectionBackground(Style.PRIMARY_HOVER_COLOR);

        // Setting up a scroll pane for the JList
        SScrollPane scrollPane = new SScrollPane(orderIdList);
        scrollPane.setVerticalScrollBarPolicy(SScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        leftPanel.add(scrollPane);
        //***************************************************************************************************



        //************************************* Right panel customization **********************************
        SPanel rightPanel = splitPanel.getRightPanel();
        rightPanel.setLayout(new BorderLayout());

        // Creating a JTextArea for the multiline text
        textArea = new JTextArea(10, 20);
        textArea.setText("PLEASE SELECT AN ORDER");
        textArea.setEditable(false);
        textArea.setWrapStyleWord(true);
        textArea.setLineWrap(true);
        textArea.setFont(Style.TEXT_FONT);
        textArea.setForeground(Style.TEXT_COLOR);
        textArea.setBackground(Style.BACKGROUND_COLOR);
        textArea.setBorder(null);

        // Setting up a scroll pane for the JTextArea
        SScrollPane scrollPane2 = new SScrollPane(textArea);
        scrollPane2.setVerticalScrollBarPolicy(SScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        rightPanel.add(scrollPane2, BorderLayout.CENTER);

        bigButton = new SButton("VALIDATE ORDER", SButton.ButtonType.PRIMARY, 5);
        refuseButton = new SButton("REFUSE ORDER", SButton.ButtonType.ERROR, 5);

        // Panel to hold both buttons with BoxLayout for horizontal centering
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));
        buttonPanel.add(Box.createHorizontalGlue());
        buttonPanel.add(bigButton);
        buttonPanel.add(Box.createHorizontalStrut(10));
        buttonPanel.add(refuseButton);
        buttonPanel.add(Box.createHorizontalGlue());
        buttonPanel.setBackground(Style.BACKGROUND_COLOR);

        // Directly adding the button panel to the right panel at the bottom
        rightPanel.add(buttonPanel, BorderLayout.SOUTH);
        //***************************************************************************************************
        frame.add(splitPanel);
    }

    /**
     * Adds a ListSelectionListener to the orderIdList (JList) to listen for selection changes.
     * When a selection is made, it retrieves the selected order ID and displays the corresponding order information.
     *
     * @param listener The ListSelectionListener to be added.
     */
    public void addPizzaSelectionListener(ListSelectionListener listener) {
        orderIdList.addListSelectionListener(e -> {
            Integer selectedOrderId = orderIdList.getSelectedValue();
            System.out.println("Selected Order ID: " + selectedOrderId);

                displayOrderInfo(selectedOrderId);
                listener.valueChanged(e);

        });
    }



    /**
     * Adds an ActionListener to the "VALIDATE ORDER" button to listen for button clicks.
     * When the button is clicked, the provided listener is notified.
     *
     * @param listener The ActionListener to be added.
     */
    public void addValidatePizzaButtonListener(ActionListener listener) {
        bigButton.addActionListener(listener);
    }
    /**
     * Adds an ActionListener to the "REFUSE ORDER" button to listen for button clicks.
     * When the button is clicked, the provided listener is notified.
     *
     * @param listener The ActionListener to be added.
     */
    public void addRefusePizzaButtonListener(ActionListener listener) {
        refuseButton.addActionListener(listener);
    }

    /**
     * Updates the text displayed in the JTextArea in the right panel.
     *
     * @param text The new text to be displayed.
     */
    public void updateRightPanel(String text) {
        textArea.setText(text);
    }

    public void updateLeftPanel(int orderToDelete) {
        DefaultListModel<Integer> listModel = new DefaultListModel<>();
        try {
            String query = "SELECT DISTINCT orderId FROM view_order_summary";
            try (ResultSet resultSet = executeQuery(query)) {
                while (resultSet.next()) {
                    int orderId = resultSet.getInt("orderId");
                    if (orderId != orderToDelete) {
                        listModel.addElement(orderId);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        SPanel leftPanel = splitPanel.getLeftPanel();
        leftPanel.removeAll();  // Clear previous components

        // Creating a JList with the list model
        orderIdList = new JList<>(listModel);
        orderIdList.setFont(Style.TEXT_FONT);
        orderIdList.setForeground(Style.TEXT_COLOR);
        orderIdList.setBackground(Style.BACKGROUND_COLOR);
        orderIdList.setSelectionForeground(Style.TEXT_COLOR);
        orderIdList.setSelectionBackground(Style.PRIMARY_HOVER_COLOR);

        // Setting up a scroll pane for the JList
        SScrollPane scrollPane = new SScrollPane(orderIdList);
        scrollPane.setVerticalScrollBarPolicy(SScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        leftPanel.add(scrollPane);

        // Update the left panel in the split panel
        splitPanel.setLeftPanel(leftPanel);
        System.out.println("Updating left panel. Order to delete: " + orderToDelete);

    }





    /**
     * Gets the selected order ID from the JList.
     *
     * @return The selected order ID as an int.
     */
    public int getDeletedOrder() {
        return orderIdList.getSelectedValue(); // Assuming orderIdList is your JList
    }



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
     * Populates the DefaultListModel with order IDs retrieved from the database.
     * Uses a SQL query to fetch distinct order IDs from the "VIEW_ORDER_SUMMARY" view.
     *
     * @param listModel The DefaultListModel to be populated with order IDs.
     */
    private void populateListModel(DefaultListModel<Integer> listModel) {
        try {
            String query = "SELECT DISTINCT orderId FROM view_order_summary";
            try (ResultSet resultSet = executeQuery(query)) {
                while (resultSet.next()) {
                    int orderId = resultSet.getInt("orderId");
                    listModel.addElement(orderId);
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
     */
    public void displayOrderInfo(int orderId) {
        try {
            // SQL query to retrieve order details for the specified order ID
            String query = "SELECT * FROM VIEW_ORDER_SUMMARY WHERE orderId = " + orderId;
            // Executing the query
            try (ResultSet resultSet = executeQuery(query)) {
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

                    if ("PIZZA".equals(itemType)) {
                        String queryIngredients = "SELECT * FROM VIEW_PIZZA_INGREDIENTS WHERE id = " + itemId;
                        try (ResultSet rs = executeQuery(queryIngredients)) {
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
                        try (ResultSet rs = executeQuery(queryIngredients)) {
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
                        try (ResultSet rs = executeQuery(queryIngredients)) {
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

                // Setting the order details in the text area
                textArea.setText(orderDetails.toString());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



}
