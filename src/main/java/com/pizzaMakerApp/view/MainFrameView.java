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

public class MainFrameView extends SFrame {

    private OrderView orderView;
    private ManagerView managerView;
    private SPanel mainPanel;

    public MainFrameView(OrderView orderView, ManagerView managerView) {
        super("Pizza Maker App", 1000, 700);
        mainPanel = new SPanel(new BorderLayout(), new Dimension(700, 500));
        setOrderView(orderView);
        setManagerView(managerView);
        init();
    }

    private void init() {
        createTitlePanel();
        createSplitPane();
        this.add(mainPanel);
        setVisible(true);
    }

    private void createSplitPane() {
        SSplitPane splitPane = new SSplitPane();
        splitPane.setLeftComponent(getManagerView());
        splitPane.setRightComponent(getOrderView());
        mainPanel.add(splitPane, BorderLayout.CENTER);
    }

    private void createTitlePanel() {
        SPanel titlePanel = new SPanel(new FlowLayout(FlowLayout.CENTER), new Dimension(1000, 100));
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

        mainPanel.add(titlePanel, BorderLayout.NORTH);
    }

    public OrderView getOrderView() {
        return orderView;
    }

    public ManagerView getManagerView() {
        return managerView;
    }

    public void setOrderView(OrderView orderView) {
        this.orderView = orderView;
    }

    public void setManagerView(ManagerView managerView) {
        this.managerView = managerView;
    }

}
