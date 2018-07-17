package PointCloudVisu.core.gui.controls;

import PointCloudVisu.core.gui.MainControl;
import java.awt.Color;
import java.awt.Dimension;
import java.io.PrintStream;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.TitledBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

/**
 * Any prints should be redirected to this console to show feedback to the user
 *
 * @author jorge.martinez.sanchez
 */
public class ConsoleTextPane {

    protected final int DEFAULT_WIDTH = 300;
    protected JPanel panel;
    protected JTextPane textPane;
    protected JScrollPane scrollPane;

    public ConsoleTextPane() {
        panel = new JPanel();
        TitledBorder border = new TitledBorder("Console output");
        border.setTitleJustification(TitledBorder.CENTER);
        panel.setBorder(border);
        panel.setPreferredSize(new Dimension(DEFAULT_WIDTH, MainControl.COMPONENTS_HEIGHT));
                
        textPane = new JTextPane();
        textPane.setPreferredSize(new Dimension(DEFAULT_WIDTH, 35));
        textPane.setEditable(false);
        textPane.setFocusable(false);
        textPane.setBackground(Color.black);

        scrollPane = new JScrollPane(textPane);
        scrollPane.setPreferredSize(new Dimension(DEFAULT_WIDTH, 35));
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

        //uncomment this if you want to redirect the standard output
        //redirectOut();
                
        panel.add(scrollPane);
    }

    public JPanel getTextPane() {
        return panel;
    }

    public void addText(String line) {
        StyledDocument doc = textPane.getStyledDocument();
        Style style = textPane.addStyle("Color Style", null);
        StyleConstants.setForeground(style, Color.white);
        try {
            doc.insertString(doc.getLength(), line, style);
        }
        catch (BadLocationException e) {
        }
    }
    
    public void redirectOut(){
        PrintStream printStream = new PrintStream(new TextPaneOutputStream(this));
        System.setOut(printStream);
    }
}
