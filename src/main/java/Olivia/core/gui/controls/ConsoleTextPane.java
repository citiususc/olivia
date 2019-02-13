package Olivia.core.gui.controls;

import Olivia.core.gui.MainControl;
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
public class ConsoleTextPane extends JScrollPane {

    protected final int MINIMUM_WIDTH = 300;
    protected final int MINIMUM_HEIGHT = 300;
    //protected JPanel panel;
    protected JTextPane textPane;
    //protected JScrollPane scrollPane;

    public ConsoleTextPane(){
        TitledBorder border = new TitledBorder("Console output");
        border.setTitleJustification(TitledBorder.CENTER);
        this.setBorder(border);
        //panel.setPreferredSize(new Dimension(DEFAULT_WIDTH, MainControl.COMPONENTS_HEIGHT));
        //panel.setPreferredSize(new Dimension(DEFAULT_WIDTH, MainControl.HEIGHT- MainControl.HEIGHT_OFFSET));
        this.setPreferredSize(new Dimension(MINIMUM_WIDTH, MINIMUM_HEIGHT));
                
        textPane = new JTextPane();
        //textPane.setPreferredSize(new Dimension(DEFAULT_WIDTH, 35));
        textPane.setEditable(false);
        textPane.setFocusable(false);
        textPane.setBackground(Color.black);
        //textPane.setPreferredSize(new Dimension(MINIMUM_WIDTH, MINIMUM_HEIGHT));
        //scrollPane = new JScrollPane(textPane);
        //scrollPane.setPreferredSize(new Dimension(DEFAULT_WIDTH, 35));
        //scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        this.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

        //uncomment this if you want to redirect the standard output
        //redirectOut();
                
        //panel.add(scrollPane);
        //this.add(scrollPane);
        //this.add(textPane);
        this.setViewportView(textPane);
    }

    
    public JTextPane getTextPane() {
        return textPane;
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
