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
    public JList<Integer> orderIdListUpd  = new JList<Integer> ();
    private JTextArea textArea;
    private JTextArea textAreaUpd;
    private SButton bigButton;
    private SButton bigButtonUpd = new SButton("VALIDATE ORDER", SButton.ButtonType.PRIMARY, 5);
    private SButton refuseButton;
    private SButton refuseButtonUpd = new SButton("REFUSE ORDER", SButton.ButtonType.ERROR, 5);



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
            if (!e.getValueIsAdjusting()) {
                Integer selectedOrderId = orderIdList.getSelectedValue();
                if (selectedOrderId != null) {
                    textArea.setText(displayOrderInfo(selectedOrderId));
                    listener.valueChanged(e);
                }
            }
        });
    }
    public void addPizzaSelectionListenerUpd(ListSelectionListener listener) {
        orderIdListUpd.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                Integer selectedOrderId = orderIdListUpd.getSelectedValue();
                if (selectedOrderId != null) {
                    textAreaUpd.setText(displayOrderInfo(selectedOrderId));
                    listener.valueChanged(e);
                }
            }
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
    public void addValidatePizzaButtonListenerUpd(ActionListener listener) {
        bigButtonUpd.addActionListener(listener);
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

    public void addRefusePizzaButtonListenerUpd(ActionListener listener) {
        refuseButtonUpd.addActionListener(listener);
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

    public void update() {
        splitPanel = new SSplitPanel();
        SPanel leftPanel = splitPanel.getLeftPanel();
        SPanel rightPanel = splitPanel.getRightPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        rightPanel.setLayout(new BorderLayout());

        DefaultListModel<Integer> listModel = new DefaultListModel<>();
        listModel.clear();
        populateListModel(listModel);
        orderIdListUpd.setModel(listModel);
        orderIdListUpd.setFont(Style.TEXT_FONT);
        orderIdListUpd.setForeground(Style.TEXT_COLOR);
        orderIdListUpd.setBackground(Style.BACKGROUND_COLOR);
        orderIdListUpd.setSelectionForeground(Style.TEXT_COLOR);
        orderIdListUpd.setSelectionBackground(Style.PRIMARY_HOVER_COLOR);

        SScrollPane scrollPane = new SScrollPane(orderIdListUpd);
        scrollPane.setVerticalScrollBarPolicy(SScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setBackground(Style.BACKGROUND_COLOR);
        leftPanel.add(scrollPane);

        // Creating a JTextArea for the multiline text
        textAreaUpd = new JTextArea(10, 20);
        textAreaUpd.setText("PLEASE SELECT AN ORDER");
        textAreaUpd.setEditable(false);
        textAreaUpd.setWrapStyleWord(true);
        textAreaUpd.setLineWrap(true);
        textAreaUpd.setFont(Style.TEXT_FONT);
        textAreaUpd.setForeground(Style.TEXT_COLOR);
        textAreaUpd.setBackground(Style.BACKGROUND_COLOR);
        textAreaUpd.setBorder(null);

        // Setting up a scroll pane for the JTextArea
        SScrollPane scrollPane2 = new SScrollPane(textAreaUpd);
        scrollPane2.setVerticalScrollBarPolicy(SScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        rightPanel.add(scrollPane2, BorderLayout.CENTER);

        // Panel to hold both buttons with BoxLayout for horizontal centering
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));
        buttonPanel.add(Box.createHorizontalGlue());
        buttonPanel.add(bigButtonUpd);
        buttonPanel.add(Box.createHorizontalStrut(10));
        buttonPanel.add(refuseButtonUpd);
        buttonPanel.add(Box.createHorizontalGlue());
        buttonPanel.setBackground(Style.BACKGROUND_COLOR);

        rightPanel.add(buttonPanel, BorderLayout.SOUTH);

        frame.add(splitPanel);
    }


    public void deleteAllComponents() {
            // Clear components from the left panel
            SPanel leftPanel = splitPanel.getLeftPanel();
            leftPanel.removeAll();

            // Clear components from the right panel
            SPanel rightPanel = splitPanel.getRightPanel();
            rightPanel.removeAll();

            splitPanel.removeAll();

            // Repaint the panels to reflect the changes
            leftPanel.revalidate();
            leftPanel.repaint();
            rightPanel.revalidate();
            rightPanel.repaint();
            splitPanel.revalidate();
            splitPanel.repaint();
            leftPanel = null;
            rightPanel = null;
            splitPanel = null;
    }



    /**
     * Populates the DefaultListModel with order IDs retrieved from the database.
     * Uses a SQL query to fetch distinct order IDs from the "VIEW_ORDER_SUMMARY" view.
     *
     * @param listModel The DefaultListModel to be populated with order IDs.
     */
    private void populateListModel(DefaultListModel<Integer> listModel) {
        try {
            String query = "SELECT DISTINCT orderId FROM view_order_summary WHERE status = 'PENDING'";
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
    public String displayOrderInfo(int orderId) {
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
                return orderDetails.toString();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "error";
        }
    }



}
