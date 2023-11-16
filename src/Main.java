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
    public static final int NB_PM = 3;
    public static final int MAX_ORDERS = 20;
    public static void main(String[] args) {
        // Create and show the GUI (assuming createAndShowGUI is a method in your Main class)
        SwingUtilities.invokeLater(Main::createAndShowGUI);

        PizzaMaker[] team = new PizzaMaker[NB_PM];

        team[0] = new PizzaMaker(1, "Vinsmoke", "Sanji", true, PizzaMaker.Level.PRO);
        team[1] = new PizzaMaker(2, "Giovanna", "Giorno", true, PizzaMaker.Level.INTERMEDIATE);
        team[2] = new PizzaMaker(3, "Corleone", "Vito", true, PizzaMaker.Level.BEGINNER);

        int[] i = new int[MAX_ORDERS];
        i[0] = 31;
        i[1] = 32;
        i[2] = 33;
        i[3] = 35;
        i[4] = 37;
        i[5] = 39;
        i[6] = 74;
        i[7] = 99;
        i[8] = 65;
        i[9] = 48;

        Kitchen kitchen = new Kitchen(team, i);



        /*----------------- TEST CASES --------------------*/
        System.out.println("ouais maggle");
        for(int j = 0; j<i.length; j++) {
            // Simulate order taking
            team[0].takeOrder(i[j]); // Should be able to take the order immediately
            team[1].takeOrder(i[j]); // Should be able to take the order immediately
            team[2].takeOrder(i[j]); // Should be able to take the order immediately

            // Simulate order taking within the 2-minute cooldown period
            team[0].takeOrder(i[j]); // Should print a message that the Pizza Maker needs to wait
            team[1].takeOrder(i[j]); // Should print a message that the Pizza Maker needs to wait
            team[2].takeOrder(i[j]); // Should print a message that the Pizza Maker needs to wait

            // Wait for 2 minutes
            try {
                Thread.sleep(120000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Simulate order taking after the cooldown period
            team[0].takeOrder(i[j]); // Should be able to take the order immediately
            team[1].takeOrder(i[j]); // Should be able to take the order immediately
            team[2].takeOrder(i[j]); // Should be able to take the order immediately
        }


    }

    private static void createAndShowGUI() {
        // Création de la fenêtre principale
        SFrame frame = new SFrame("Test SComponents", 400, 300);

        // Création et ajout des composants stylisés
        JPanel contentPanel = new SPanel();
        contentPanel.setLayout(new FlowLayout());

        // Test SButton
        SButton primaryButton = new SButton("Primary", ButtonType.PRIMARY);
        SButton secondaryButton = new SButton("Secondary", ButtonType.SECONDARY);
        SButton errorButton = new SButton("Error", ButtonType.ERROR);

        // Test SLabel
        SLabel label = new SLabel("Test Label", SLabel.FontStyle.TITLE);

        ImageIcon imageIcon = ImageLoader.loadImageIcon("logo.png"); // Assurez-vous que "logo.png" est le bon chemin
        if (imageIcon != null) {
            // Convertir ImageIcon en BufferedImage
            BufferedImage bufferedImage = ImageResizer.iconToBufferedImage(imageIcon);

            // Redimensionner l'image
            BufferedImage resizedImage = ImageResizer.resizeImage(bufferedImage, 100, 100);

            // Créer un nouvel ImageIcon à partir de l'image redimensionnée
            ImageIcon resizedIcon = new ImageIcon(resizedImage);

            // Ajouter l'image redimensionnée à un JLabel et l'ajouter au panneau
            JLabel imageLabel = new JLabel(resizedIcon);
            contentPanel.add(imageLabel);
        } else {
            System.err.println("Image not found or could not be loaded.");
        }

        // Ajout des composants au panel
        contentPanel.add(primaryButton);
        contentPanel.add(secondaryButton);
        contentPanel.add(errorButton);
        contentPanel.add(label);

        // Ajout du panel à la fenêtre
        frame.setContentPane(contentPanel);
        frame.setVisible(true);
    }
}
