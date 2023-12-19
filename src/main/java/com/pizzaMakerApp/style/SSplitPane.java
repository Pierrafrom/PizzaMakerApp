package com.pizzaMakerApp.style;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicSplitPaneDivider;
import javax.swing.plaf.basic.BasicSplitPaneUI;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class SSplitPane extends JSplitPane {

    public SSplitPane() {
        // Appliquer les styles
        this.setBackground(Style.BACKGROUND_COLOR);
        this.setDividerSize(10);
        this.setBorder(null);

        // Personnalisation du séparateur
        this.setUI(new BasicSplitPaneUI() {
            @Override
            public BasicSplitPaneDivider createDefaultDivider() {
                return new BasicSplitPaneDivider(this) {
                    @Override
                    public void setBorder(Border b) {
                    }

                    @Override
                    public void paint(Graphics g) {
                        g.setColor(Style.NEUTRAL_COLOR);
                        g.fillRect(0, 0, getSize().width, getSize().height);
                        super.paint(g);
                    }
                };
            }
        });
        this.setDividerLocation(0.5);

        // Ajouter un écouteur de redimensionnement pour ajuster le séparateur
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                setDividerLocation(0.5);
            }
        });
    }

    public static void main(String[] args) {
        SFrame frame = new SFrame();
        SSplitPane splitPane = new SSplitPane();

        // Création de données pour la liste
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

        frame.add(splitPane);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setVisible(true);
    }
}
