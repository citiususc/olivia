package PointCloudVisu.core.gui;

import PointCloudVisu.core.render.OpenGLScreen;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import java.beans.PropertyVetoException;
import java.util.ArrayList;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.border.TitledBorder;

/**
 * This is the main frame for the controls, takes care of adding the components
 *
 * @author oscar.garcia
 */
public class MainFrame extends JFrame {

    /**
     * Title of the frame.
     */
    public static final String TITLE = "Point Cloud Viewer v.6.0.0417";
    /**
     * The OpenGL render screen.
     */
    protected OpenGLScreen renderScreen;
    /**
     * The menu bar.
     */
    protected MainMenuBar menuBar;
    /**
     * The main controls
     */
    protected MainControl controlPanel;
    /**
     * The pane holding the render screens
     */
    protected JDesktopPane renderPane;
    /**
     * The array containing the different visualisation panels
     */
    protected ArrayList<JPanel> visuPanels;
    /**
     * The second split pane where visualisation panels are added
     */
    protected JSplitPane splitPane2;
    /**
     * The frame holding the render screens when they are decoupled from the GUI
     */
    protected JFrame stereoFrame;

    /**
     * Initialize the components.
     * @param isStereo3D The flag to indicate if stereoscopic 3D is enabled
     */
    public void initialize(boolean isStereo3D) {
        addWindowStateListener(new WindowStateListener() {
            @Override
            public void windowStateChanged(WindowEvent e) {
                if (renderScreen != null && e.getNewState() == Frame.MAXIMIZED_BOTH) {
                    try {
                        MainFrame.this.renderScreen.getFrame().setMaximum(true);
                    }
                    catch (PropertyVetoException ex) {
                    }
                }
            }
        });
        setContentPane(splitPane2);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setJMenuBar(menuBar);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        if (!isStereo3D) {
            //setSize(screenSize.width, screenSize.height);
            setSize(1600, 900); // HD+
        } else {
            setSize(screenSize.width, screenSize.height / 3);
        }
        setTitle(TITLE);
        setVisible(true);
        setLocationRelativeTo(null);
    }

    /**
     * Create the main frame. Must call to initialize() afterwards.
     *
     * @param isStereo3D The flag to indicate if stereoscopic 3D is enabled
     */
    public MainFrame(boolean isStereo3D) {
        menuBar = new MainMenuBar();
        menuBar.initialize();
        controlPanel = new MainControl();
        controlPanel.initialize();
        visuPanels = new ArrayList();
        renderPane = new JDesktopPane();
        renderPane.setDragMode(JDesktopPane.OUTLINE_DRAG_MODE);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int renderHeight = screenSize.height - menuBar.getHeight() - controlPanel.getHeight();
        renderPane.setMinimumSize(new Dimension(screenSize.width, renderHeight));

        if (!isStereo3D) {
            JSplitPane splitPane1 = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
            splitPane1.setTopComponent(controlPanel);
            splitPane1.setDividerSize(0);
            splitPane1.setBottomComponent(renderPane);
            splitPane2 = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
            splitPane2.setTopComponent(splitPane1);
            splitPane2.setBottomComponent(null);
            splitPane2.setResizeWeight(1);
        } else {
            stereoFrame = new JFrame();
            stereoFrame.setContentPane(renderPane);
            stereoFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            stereoFrame.setSize(screenSize.width, screenSize.height);
            stereoFrame.setTitle(TITLE);
            stereoFrame.setVisible(true);
            stereoFrame.setLocationRelativeTo(null);
            splitPane2 = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
            splitPane2.setTopComponent(controlPanel);
            splitPane2.setBottomComponent(null);
            splitPane2.setResizeWeight(1);
        }
    }

    /**
     * Add the visualisation panel
     *
     * @param title The title of the pane
     * @param panel The control pane
     */
    public void addVisuPanel(String title, JPanel panel) {
        panel.setPreferredSize(new Dimension(panel.getWidth(), 150));
        TitledBorder border = new TitledBorder(title);
        border.setTitleJustification(TitledBorder.CENTER);
        border.setTitlePosition(TitledBorder.TOP);
        panel.setBorder(border);
        visuPanels.add(panel);
        splitPane2.setBottomComponent(visuPanels.get(visuPanels.size() - 1));
    }

    /**
     * setter
     *
     * @param renderScreen The OpenGL render screen
     */
    public void setRenderScreen(OpenGLScreen renderScreen) {
        this.renderScreen = renderScreen;
        menuBar.renderScreen = renderScreen;
        controlPanel.setRenderScreen(renderScreen);
        renderScreen.addActionListener(controlPanel);
        renderScreen.addActionListener(menuBar);
    }

    /**
     * Set the given visualisation panel as visible
     *
     * @param index Position of the visualisation panel in the panel list
     */
    public void setVisibleVisuPanel(int index) {
        splitPane2.setBottomComponent(visuPanels.get(index));
    }

    /**
     * Remove the visualisation panel and set the first one as visible
     *
     * @param index Position of the visualisation panel in the panel list
     */
    public void removeVisuPanel(int index) {
        visuPanels.remove(index);
        if (!visuPanels.isEmpty()) {
            splitPane2.setBottomComponent(visuPanels.get(0));
        } else {
            splitPane2.setBottomComponent(null);
        }
    }

    /**
     * getter
     *
     * @return The pane holding the render screens
     */
    public JDesktopPane getRenderPane() {
        return renderPane;
    }

    /**
     * Add the line into the output console (with line break)
     *
     * @param line A string
     */
    public void logIntoConsole(String line) {
        controlPanel.getConsolePane().addText(line + '\n');
    }
}
