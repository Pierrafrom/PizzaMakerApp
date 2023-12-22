package com.pizzaMakerApp.style;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * A custom JList implementation with enhanced UI features.
 * This class extends JList to provide additional styling and behavior like hover effects.
 *
 * @param <T> The type of elements this list will contain.
 */
public class SList<T> extends JList<T> {

    // Index of the list item currently being hovered over by the mouse.
    private int hoveredIndex = -1;

    /**
     * Default constructor for SList.
     */
    public SList() {
        super();
        // Set custom cell renderer to modify the look and feel of list items.
        setCellRenderer(new CustomListCellRenderer());

        // Set the font and color scheme for the list from a predefined style class.
        setFont(Style.TEXT_FONT);
        setBackground(Style.BACKGROUND_COLOR);
        setForeground(Style.TEXT_COLOR);

        // Remove the border for a cleaner appearance.
        setBorder(null);

        // Add mouse motion listener to handle hover behavior.
        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                // Determine which list item is under the mouse.
                int index = locationToIndex(e.getPoint());
                // If the hovered item changes, update the hoveredIndex and repaint the list.
                if (index != hoveredIndex) {
                    hoveredIndex = index;
                    repaint();
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // Reset the hovered index when the mouse leaves the list and repaint.
                hoveredIndex = -1;
                repaint();
            }
        });
    }

    /**
     * Custom list cell renderer to change the appearance of list items based on state.
     */
    private class CustomListCellRenderer extends DefaultListCellRenderer {

        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
                                                      boolean cellHasFocus) {
            // Create a custom label for each list item.
            SLabel label = new SLabel(value.toString());
            label.setOpaque(true);

            // Change the background and foreground of the label based on selection and hover state.
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