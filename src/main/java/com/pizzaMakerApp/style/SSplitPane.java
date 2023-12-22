package com.pizzaMakerApp.style;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicSplitPaneDivider;
import javax.swing.plaf.basic.BasicSplitPaneUI;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

/**
 * A customized split pane class that extends JSplitPane.
 * This class provides a styled version of the standard JSplitPane with custom divider and color.
 */
public class SSplitPane extends JSplitPane {

    /**
     * Constructor for SSplitPane.
     * Applies custom styles and behaviors to the split pane.
     */
    public SSplitPane() {
        // Set the background color of the split pane from the Style class.
        this.setBackground(Style.NEUTRAL_COLOR);

        // Set the size of the divider in the split pane.
        this.setDividerSize(3);

        // Remove the border for a cleaner look.
        this.setBorder(null);

        // Customize the divider of the split pane.
        this.setUI(new BasicSplitPaneUI() {
            @Override
            public BasicSplitPaneDivider createDefaultDivider() {
                return new BasicSplitPaneDivider(this) {
                    @Override
                    public void setBorder(Border b) {
                        // Override to prevent setting any border on the divider.
                    }

                    @Override
                    public void paint(Graphics g) {
                        // Paint the divider with the specified background color from the Style class.
                        g.setColor(Style.BACKGROUND_COLOR);
                        g.fillRect(0, 0, getSize().width, getSize().height);
                        super.paint(g);
                    }
                };
            }
        });

        // Set the initial position of the divider to the middle of the split pane.
        this.setDividerLocation(0.5);

        // Add a component listener to adjust the divider location when the component is resized.
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                // Keep the divider at the middle of the split pane during resizing.
                setDividerLocation(0.5);
            }
        });
    }
}
