package com.pizzaMakerApp.style;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SList<T> extends JList<T> {

    private int hoveredIndex = -1;

    public SList(ListModel<T> model) {
        super(model);
        setCellRenderer(new CustomListCellRenderer());
        setFont(Style.TEXT_FONT);
        setBackground(Style.BACKGROUND_COLOR);
        setForeground(Style.TEXT_COLOR);

        // Supprimer la bordure
        setBorder(null);

        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                int index = locationToIndex(e.getPoint());
                if (index != hoveredIndex) {
                    hoveredIndex = index;
                    repaint();
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                hoveredIndex = -1;
                repaint();
            }
        });
    }

    private class CustomListCellRenderer extends DefaultListCellRenderer {

        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            SLabel label = new SLabel(value.toString());
            label.setOpaque(true);

            if (isSelected) {
                label.setBackground(Style.PRIMARY_COLOR);
                label.setForeground(Style.TEXT_COLOR);
            } else if (index == hoveredIndex) {
                label.setBackground(Style.PRIMARY_HOVER_COLOR);
                label.setForeground(Style.TEXT_COLOR);
            } else {
                label.setBackground(Style.BACKGROUND_COLOR);
                label.setForeground(Style.TEXT_COLOR);
            }

            return label;
        }
    }
}
