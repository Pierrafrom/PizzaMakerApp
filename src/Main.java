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
import java.util.ArrayList;


public class Main {
    public static final int NB_PM = 3;
    public static final int MAX_ORDERS = 20;

    public static void main(String[] args) {
        PizzaMaker[] team = new PizzaMaker[NB_PM];

        team[0] = new PizzaMaker(1, "Vinsmoke", "Sanji", PizzaMaker.Level.PRO);
        team[1] = new PizzaMaker(2, "Giovanna", "Giorno", PizzaMaker.Level.INTERMEDIATE);
        team[2] = new PizzaMaker(3, "Corleone", "Vito",  PizzaMaker.Level.BEGINNER);

        Kitchen cookingshit = new Kitchen(team, new int[]{1,2,5,3,4,6,84,51,12,62});
        cookingshit.setTeam(team);
        team[0].start();
        team[1].start();
        team[2].start();



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
