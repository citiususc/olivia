package Olivia.core.gui;

import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JToggleButton;

/**
 * This class provides common methods for GUI creation
 *
 * @author jorge.martinez.sanchez
 */
public class GUIManager {

    /**
     * Creates a JButton
     * @param name The name of the button
     * @param tooltip The button's tooltip
     * @param action The action the button will perform
     * @param al The action listener
     * @return A configured JButton
     */
    public final static JButton createButton(String name, String tooltip, String action, ActionListener al) {
        JButton btn = new JButton(name);
        btn.setToolTipText(tooltip);
        btn.setActionCommand(action);
        btn.addActionListener(al);
        return btn;
    }

    /**
     * Creates a JToggleButton
     * @param name The name of the button
     * @param tooltip The button's tooltip
     * @param action The action the button will perform
     * @param selected Whether the button will be selected initially
     * @param al The action listener
     * @return 
     */
    public final static JToggleButton createToggleButton(String name, String tooltip, String action, boolean selected, ActionListener al) {
        JToggleButton btn = new JToggleButton(name);
        btn.setToolTipText(tooltip);
        btn.setActionCommand(action);
        btn.setSelected(selected);
        btn.addActionListener(al);
        return btn;
    }
}
