package PointCloudVisu.core.gui.controls;

import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicArrowButton;

/**
 * Simple panel with arrow-like up and down buttons, just extend this and set up
 * the action listener
 *
 * @author jorge.martinez.sanchez
 */
public class BasicArrowPanel extends JPanel {

    protected JLabel nameLabel;
    protected BasicArrowButton buttonUp;
    protected BasicArrowButton buttonDown;

    public BasicArrowPanel(String name, String upToolTip, String downToolTip) {
        nameLabel = new JLabel(name, SwingConstants.CENTER);
        nameLabel.setBorder(new EmptyBorder(3, 3, 3, 3));

        buttonUp = new BasicArrowButton(BasicArrowButton.NORTH);
        buttonUp.setToolTipText(upToolTip);
        buttonUp.setActionCommand("up");

        buttonDown = new BasicArrowButton(BasicArrowButton.SOUTH);
        buttonDown.setToolTipText(downToolTip);
        buttonDown.setActionCommand("down");

        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(5, 5, 5, 5));
        add(nameLabel, BorderLayout.CENTER);
        add(buttonUp, BorderLayout.NORTH);
        add(buttonDown, BorderLayout.SOUTH);
    }
}
