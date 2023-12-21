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
        // apply styles
        this.setBackground(Style.NEUTRAL_COLOR);
        this.setDividerSize(3);
        this.setBorder(null);

        // divider customisation
        this.setUI(new BasicSplitPaneUI() {
            @Override
            public BasicSplitPaneDivider createDefaultDivider() {
                return new BasicSplitPaneDivider(this) {
                    @Override
                    public void setBorder(Border b) {
                    }

                    @Override
                    public void paint(Graphics g) {
                        g.setColor(Style.BACKGROUND_COLOR);
                        g.fillRect(0, 0, getSize().width, getSize().height);
                        super.paint(g);
                    }
                };
            }
        });
        this.setDividerLocation(0.5);

        // creating a resizing listener in order to adjust the divider
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                setDividerLocation(0.5);
            }
        });
    }
}
