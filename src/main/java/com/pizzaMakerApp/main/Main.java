package com.pizzaMakerApp.main;

import com.pizzaMakerApp.style.*;
import com.pizzaMakerApp.utils.ImageLoader;
import com.pizzaMakerApp.utils.ImageResizer;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        // Création de la fenêtre principale
        SFrame frame = new SFrame("Test SComponents", 1920, 1080);
        SPanel contentPanel = new SPanel(new BorderLayout());

        SPanel titlePanel = new SPanel(new FlowLayout(FlowLayout.CENTER), new Dimension(1920, 100));
        // load the logo
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
            titlePanel.add(imageLabel);
        } else {
            System.err.println("Image not found or could not be loaded.");
        }
        SLabel titleLabel = new SLabel("Pizza Maker App", SLabel.FontStyle.TITLE);
        titlePanel.add(titleLabel);
        contentPanel.add(titlePanel, BorderLayout.NORTH);

        SSplitPane splitPane = new SSplitPane();
        DefaultListModel<String> listModel = new DefaultListModel<>();
        listModel.addElement("Élément 1");
        listModel.addElement("Élément 2");
        listModel.addElement("Élément 3");
        listModel.addElement("Élément 4");
        listModel.addElement("Élément 5");

        // Création de la liste personnalisée
        SList<String> sList = new SList<>(listModel);
        SScrollPane leftPane = new SScrollPane(sList, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        splitPane.setLeftComponent(leftPane);

        // SScrollPane comme panneau de contenu à droite
        // Remplacez ceci par le composant que vous souhaitez afficher dans le SScrollPane
        STextArea textArea = new STextArea("Contenu initial");
        textArea.setText("selectionner une commande"); // Texte de démonstration
        SScrollPane rightScrollPane = new SScrollPane(textArea);
        splitPane.setRightComponent(rightScrollPane);

        // Gestionnaire d'événements pour la liste
        sList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String selectedItem = sList.getSelectedValue();
                textArea.setText("contenu :" + selectedItem); // Texte de démonstration
            }
        });

        contentPanel.add(splitPane, BorderLayout.CENTER);


        /*

        // Création et ajout des composants stylisés
        JPanel contentPanel = new SPanel();
        contentPanel.setLayout(new FlowLayout());

        // Test SButton
        SButton primaryButton = new SButton("Primary", ButtonType.NEUTRAL);
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
        */

        // Ajout du panel à la fenêtre
        frame.setContentPane(contentPanel);
        frame.setVisible(true);
    }
}
