package PointCloudVisu.core.gui;

import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JToggleButton;

/**
 * This class provides common methods for GUI creation
 *
 * @author jorge.martinez.sanchez
 */
public class GUIManager {

    public final static JButton createButton(String name, String tooltip, String action, ActionListener al) {
        JButton btn = new JButton(name);
        btn.setToolTipText(tooltip);
        btn.setActionCommand(action);
        btn.addActionListener(al);
        return btn;
    }

    public final static JToggleButton createToggleButton(String name, String tooltip, String action, boolean selected, ActionListener al) {
        JToggleButton btn = new JToggleButton(name);
        btn.setToolTipText(tooltip);
        btn.setActionCommand(action);
        btn.setSelected(selected);
        btn.addActionListener(al);
        return btn;
    }
}
