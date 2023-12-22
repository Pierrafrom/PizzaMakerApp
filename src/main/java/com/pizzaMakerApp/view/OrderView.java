package com.pizzaMakerApp.view;

import com.pizzaMakerApp.model.Order;
import com.pizzaMakerApp.style.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Represents the OrderView component of the Pizza Maker application. Displays details of an individual order,
 * including a text area for order information, buttons for validation and refusal, and a link to test the order.
 *
 */
public class OrderView extends SPanel {

    private Order orderModel;
    private STextArea textArea;
    private SButton validateButton;
    private SButton refuseButton;
    private SScrollPane scrollPane;

    /**
     * Constructs the OrderView with the specified Order model.
     *
     * @param orderModel The Order model associated with this view.
     */
    public OrderView(Order orderModel) {
        super(new BorderLayout(), new Dimension(350, 500));
        init(orderModel);
    }

    /**
     * Initializes the OrderView by creating and setting up the bottom panel and text panel.
     *
     * @param orderModel The Order model associated with this view.
     */
    private void init(Order orderModel) {
        setOrderModel(orderModel);
        createBottomPanel();
        createTextPanel();
    }

    /**
     * Creates and adds the bottom panel to the OrderView. The bottom panel includes
     * buttons for order validation and refusal, as well as a link to test the order.
     */
    private void createBottomPanel() {
        // create bottom panel
        SPanel bottomPanel = new SPanel(new BorderLayout(), new Dimension(350, 100));
        SPanel linkPanel = new SPanel(new BorderLayout(), new Dimension(350, 40));
        SPanel buttonPanel = new SPanel(new FlowLayout(FlowLayout.CENTER), new Dimension(350, 60));

        // create buttons
        validateButton = new SButton("VALIDATE ORDER", SButton.ButtonType.PRIMARY, 10);
        refuseButton = new SButton("REFUSE ORDER", SButton.ButtonType.ERROR, 10);

        // add buttons to button panel
        buttonPanel.add(validateButton);
        buttonPanel.add(refuseButton);

        // add link label to link panel
        linkPanel.add(getLinkLabel(), BorderLayout.CENTER);

        // add link panel to bottom panel
        bottomPanel.add(linkPanel, BorderLayout.SOUTH);
        // add button panel to bottom panel
        bottomPanel.add(buttonPanel, BorderLayout.NORTH);

        // add bottom panel to order view
        this.add(bottomPanel, BorderLayout.SOUTH);
    }

    /**
     * Creates and adds the text panel to the OrderView. The text panel includes
     * a scrollable text area displaying order information.
     */
    private void createTextPanel() {
        // create the text Area
        textArea = new STextArea();
        textArea.setText(getOrderModel().display());

        // create the scroll pane
        scrollPane = new SScrollPane(textArea, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
        verticalScrollBar.setValue(verticalScrollBar.getMinimum());

        // add scroll pane to order view
        this.add(scrollPane, BorderLayout.CENTER);
    }

    /**
     * Creates and returns a hyperlink label for testing the order.
     *
     * @return The hyperlink label.
     */
    private static SLabel getLinkLabel() {
        SLabel linkLabel = new SLabel("<html>Note for teacher: Click <u>here</u> to test the order</html>",
                SLabel.FontStyle.REGULAR);
        linkLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        linkLabel.setForeground(Style.TEXT_COLOR);
        linkLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    Desktop.getDesktop().browse(new URI("https://iut2orsaybestpizza.duckdns.org/"));
                } catch (IOException | URISyntaxException ex) {
                    Logger logger = LoggerFactory.getLogger(OrderView.class);
                    logger.error("Error loading the link", ex);
                }
            }
        });
        linkLabel.setHorizontalAlignment(SwingConstants.CENTER);
        return linkLabel;
    }

    /**
     * Updates the OrderView to reflect changes in the underlying data model.
     */
    public void update() {
        if (orderModel != null) {
            textArea.setText(orderModel.display());
            JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
            verticalScrollBar.setValue(verticalScrollBar.getMinimum());
        }
    }

    /**
     * Sets the Order model associated with this OrderView.
     *
     * @param orderModel The Order model to set.
     */
    public void setOrderModel(Order orderModel) {
        this.orderModel = orderModel;
    }

    /**
     * Retrieves the Order model associated with this OrderView.
     *
     * @return The Order model.
     */
    public Order getOrderModel() {
        return orderModel;
    }

    /**
     * Retrieves the STextArea component representing the text area.
     *
     * @return The STextArea component.
     */
    public STextArea getTextArea() {
        return textArea;
    }

    /**
     * Retrieves the SButton component representing the validate button.
     *
     * @return The SButton component for validation.
     */
    public SButton getValidateButton() {
        return validateButton;
    }

    /**
     * Retrieves the SButton component representing the refuse button.
     *
     * @return The SButton component for refusal.
     */
    public SButton getRefuseButton() {
        return refuseButton;
    }
}
