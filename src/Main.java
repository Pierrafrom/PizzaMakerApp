import model.Kitchen;
import model.PizzaMaker;
import style.SButton;
import style.SButton.ButtonType;
import style.SFrame;
import style.SLabel;
import style.SPanel;
import utils.ImageLoader;
import utils.ImageResizer;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Main {
    // Constants for the number of pizza makers and maximum orders
    public static final int NB_PM = 3;
    public static final int MAX_ORDERS = 20;

    public static void main(String[] args) {
        // Invoking the GUI creation and display
        SwingUtilities.invokeLater(Main::createAndShowGUI);

        // Creating an array to store the pizza maker team
        PizzaMaker[] team = new PizzaMaker[NB_PM];

        // Initializing pizza makers with different skill levels
        team[0] = new PizzaMaker(1, "Vinsmoke", "Sanji", PizzaMaker.Level.PRO);
        team[1] = new PizzaMaker(2, "Giovanna", "Giorno", PizzaMaker.Level.INTERMEDIATE);
        team[2] = new PizzaMaker(3, "Corleone", "Vito", PizzaMaker.Level.BEGINNER);

        // Creating a kitchen with the pizza maker team and initial orders
        // for now the orders will just be IDs but in the future it will maybe interact with the database
        Kitchen baratie = new Kitchen(team, new int[]{1, 2, 5, 3, 4, 6, 84, 51, 12, 62});

        // Setting the pizza maker team for the kitchen
        baratie.setTeam(team);

        // Starting the threads for each pizza maker
        team[0].start();
        team[1].start();
        team[2].start();
    }

    /**
     * Creates and shows the graphical user interface with stylized components.
     */
    private static void createAndShowGUI() {
        // Creating the main window
        SFrame frame = new SFrame("Test SComponents", 400, 300);

        // Creating and adding stylized components to the panel
        JPanel contentPanel = new SPanel();
        contentPanel.setLayout(new FlowLayout());

        // Test SButton
        SButton primaryButton = new SButton("Primary", ButtonType.NEUTRAL);
        SButton secondaryButton = new SButton("Secondary", ButtonType.SECONDARY);
        SButton errorButton = new SButton("Error", ButtonType.ERROR);

        // Test SLabel
        SLabel label = new SLabel("Test Label", SLabel.FontStyle.TITLE);

        // Loading and resizing an image
        ImageIcon imageIcon = ImageLoader.loadImageIcon("logo.png"); // Make sure "logo.png" is the correct path
        if (imageIcon != null) {
            BufferedImage bufferedImage = ImageResizer.iconToBufferedImage(imageIcon);
            BufferedImage resizedImage = ImageResizer.resizeImage(bufferedImage, 100, 100);
            ImageIcon resizedIcon = new ImageIcon(resizedImage);
            JLabel imageLabel = new JLabel(resizedIcon);
            contentPanel.add(imageLabel);
        } else {
            System.err.println("Image not found or could not be loaded.");
        }

        // Adding components to the panel
        contentPanel.add(primaryButton);
        contentPanel.add(secondaryButton);
        contentPanel.add(errorButton);
        contentPanel.add(label);

        // Adding the panel to the window
        frame.setContentPane(contentPanel);
        frame.setVisible(true);
    }
}
