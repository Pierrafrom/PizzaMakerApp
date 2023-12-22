package com.pizzaMakerApp.view;

import com.pizzaMakerApp.style.SFrame;
import com.pizzaMakerApp.style.SLabel;
import com.pizzaMakerApp.style.SPanel;
import com.pizzaMakerApp.style.SSplitPane;
import com.pizzaMakerApp.utils.ImageLoader;
import com.pizzaMakerApp.utils.ImageResizer;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Represents the main frame of the Pizza Maker application. This frame contains
 * the ManagerView and OrderView components in a split pane, along with a title panel
 * displaying the application logo and name.
 *
 */
public class MainFrameView extends SFrame {

    private OrderView orderView;
    private ManagerView managerView;
    private SPanel mainPanel;

    /**
     * Constructs the MainFrameView with the specified OrderView and ManagerView.
     *
     * @param orderView The OrderView component.
     * @param managerView The ManagerView component.
     */
    public MainFrameView(OrderView orderView, ManagerView managerView) {
        super("Pizza Maker App", 1000, 700);
        mainPanel = new SPanel(new BorderLayout(), new Dimension(700, 500));
        setOrderView(orderView);
        setManagerView(managerView);
        init();
    }

    /**
     * Initializes the main frame by creating the title panel and split pane,
     * then adds them to the main panel.
     */
    private void init() {
        createTitlePanel();
        createSplitPane();
        this.add(mainPanel);
        setVisible(true);
    }

    /**
     * Creates and adds the split pane containing the ManagerView and OrderView
     * to the main panel.
     */
    private void createSplitPane() {
        SSplitPane splitPane = new SSplitPane();
        splitPane.setLeftComponent(getManagerView());
        splitPane.setRightComponent(getOrderView());
        mainPanel.add(splitPane, BorderLayout.CENTER);
    }

    /**
     * Creates and adds the title panel to the main panel. The title panel includes
     * the application logo and name.
     */
    private void createTitlePanel() {
        SPanel titlePanel = new SPanel(new FlowLayout(FlowLayout.CENTER), new Dimension(1000, 100));

        // Load and display the application logo
        ImageIcon imageIcon = ImageLoader.loadImageIcon("logo.png");
        if (imageIcon != null) {
            BufferedImage bufferedImage = ImageResizer.iconToBufferedImage(imageIcon);
            BufferedImage resizedImage = ImageResizer.resizeImage(bufferedImage, 100, 100);
            ImageIcon resizedIcon = new ImageIcon(resizedImage);
            JLabel imageLabel = new JLabel(resizedIcon);
            titlePanel.add(imageLabel);
        } else {
            System.err.println("Image not found or could not be loaded.");
        }

        // Add the application title label
        SLabel titleLabel = new SLabel("Pizza Maker App", SLabel.FontStyle.TITLE);
        titlePanel.add(titleLabel);

        mainPanel.add(titlePanel, BorderLayout.NORTH);
    }

    /**
     * Retrieves the OrderView component.
     *
     * @return The OrderView.
     */
    public OrderView getOrderView() {
        return orderView;
    }

    /**
     * Retrieves the ManagerView component.
     *
     * @return The ManagerView.
     */
    public ManagerView getManagerView() {
        return managerView;
    }

    /**
     * Sets the OrderView component for this MainFrameView.
     *
     * @param orderView The OrderView to set.
     */
    public void setOrderView(OrderView orderView) {
        this.orderView = orderView;
    }

    /**
     * Sets the ManagerView component for this MainFrameView.
     *
     * @param managerView The ManagerView to set.
     */
    public void setManagerView(ManagerView managerView) {
        this.managerView = managerView;
    }
}
