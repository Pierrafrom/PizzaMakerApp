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

public class OrderView extends SPanel {

    private Order orderModel;
    private STextArea textArea;
    private SButton validateButton;
    private SButton refuseButton;
    private SScrollPane scrollPane;

    public OrderView(Order orderModel) {
        super(new BorderLayout(), new Dimension(350, 500));
        init(orderModel);
    }

    private void init(Order orderModel) {
        setOrderModel(orderModel);
        createBottomPanel();
        createTextPanel();
    }

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

    public void update() {
        if (orderModel != null) {
            textArea.setText(orderModel.display());
            JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
            verticalScrollBar.setValue(verticalScrollBar.getMinimum());
        }
    }

    public void setOrderModel(Order orderModel) {
        this.orderModel = orderModel;
    }

    public Order getOrderModel() {
        return orderModel;
    }

    public STextArea getTextArea() {
        return textArea;
    }

    public SButton getValidateButton() {
        return validateButton;
    }

    public SButton getRefuseButton() {
        return refuseButton;
    }

}
