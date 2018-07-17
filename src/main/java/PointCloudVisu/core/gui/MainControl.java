package PointCloudVisu.core.gui;

import PointCloudVisu.core.gui.controls.ActiveGeometryPanel;
import PointCloudVisu.core.gui.controls.ConsoleTextPane;
import PointCloudVisu.core.gui.controls.SpeedArrowPanel;
import PointCloudVisu.core.gui.controls.PointSizePanel;
import PointCloudVisu.core.gui.controls.LineWidthPanel;
import PointCloudVisu.core.gui.controls.EyeDistPanel;
import PointCloudVisu.core.render.OpenGLScreen;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;

/**
 * Here are the common controls to all Visualisations
 * <p>
 * Note: Each time a item is added in this panel must be also added to
 * setRenderScreen() if it uses the render screen, otherwise its render screen
 * will not be set
 *
 * TODO create another class to handle threading instead doing it inside the
 * methods with invokeLater()
 * </p>
 *
 * @author oscar.garcia
 */
public class MainControl extends JPanel implements ActionListener {

    public static final int DEFAULT_HEIGHT = 90;
    public static final int COMPONENTS_HEIGHT = DEFAULT_HEIGHT - 20;
    protected OpenGLScreen renderScreen;
    protected JLabel pointLabel;
    protected JLabel groupLabel;
    protected JButton centerAtPointB;
    protected SpeedArrowPanel speedArrowPanel;
    protected LineWidthPanel lineWidthPanel;
    protected PointSizePanel pointSizePanel;
    protected EyeDistPanel eyeDistPanel;
    protected ActiveGeometryPanel activeGeoPanel;
    protected ConsoleTextPane consoleTextPane;

    /**
     * Create the main control panel. Must call to initialize() afterwards.
     */
    public MainControl() {
        speedArrowPanel = new SpeedArrowPanel(renderScreen);
        lineWidthPanel = new LineWidthPanel(renderScreen);
        pointSizePanel = new PointSizePanel(renderScreen);
        eyeDistPanel = new EyeDistPanel(renderScreen);
        activeGeoPanel = new ActiveGeometryPanel(renderScreen);
        consoleTextPane = new ConsoleTextPane();

        centerAtPointB = new JButton("Center at Point");
        centerAtPointB.setToolTipText("Centers at the selected point");
        centerAtPointB.setActionCommand("centerPoint");

        pointLabel = new JLabel("<html><h2>No point selected</h2></html>", SwingConstants.CENTER);
        pointLabel.setToolTipText("selected point");
        TitledBorder border = new TitledBorder("Point selection");
        border.setTitleJustification(TitledBorder.CENTER);
        pointLabel.setBorder(border);
        pointLabel.setPreferredSize(new Dimension(400, COMPONENTS_HEIGHT));

        groupLabel = new JLabel("<html><h2>No group selected</h2></html>", SwingConstants.CENTER);
        groupLabel.setToolTipText("selected group");
        border = new TitledBorder("Group selection");
        border.setTitleJustification(TitledBorder.CENTER);
        groupLabel.setBorder(border);
        groupLabel.setPreferredSize(new Dimension(300, COMPONENTS_HEIGHT));
    }

    public void initialize() {
        setBorder(new EmptyBorder(3, 3, 3, 3));
        setLayout(new GridBagLayout());
        setPreferredSize(new Dimension(this.getWidth(), DEFAULT_HEIGHT));

        centerAtPointB.addActionListener(this);

        GridBagConstraints c = new GridBagConstraints();
        int fill = GridBagConstraints.HORIZONTAL;
        int ipadx = 10;

        c.fill = fill;
        c.gridx = 0;
        c.gridy = 0;
        c.ipadx = ipadx;
        add(speedArrowPanel, c);
        c.fill = fill;
        c.gridx = 1;
        c.gridy = 0;
        add(pointSizePanel, c);
        c.fill = fill;
        c.gridx = 2;
        c.gridy = 0;
        c.ipadx = ipadx;
        add(lineWidthPanel, c);
        c.fill = fill;
        c.gridx = 3;
        c.gridy = 0;
        c.ipadx = ipadx;
        add(activeGeoPanel, c);
        c.fill = fill;
        c.ipadx = ipadx;
        c.gridx = 4;
        c.gridy = 0;
        add(centerAtPointB, c);

        ipadx = 30;
        c.fill = fill;
        c.ipadx = ipadx;
        c.gridx = 5;
        c.gridy = 0;
        add(pointLabel, c);
        c.fill = fill;
        c.ipadx = ipadx;
        c.gridx = 6;
        c.gridy = 0;
        add(groupLabel, c);
        c.fill = fill;
        c.ipadx = ipadx;
        c.gridx = 7;
        c.gridy = 0;
        add(consoleTextPane.getTextPane(), c);

        if (renderScreen != null && renderScreen.isStereo3D()) {
            c.fill = fill;
            c.gridx = 7;
            c.gridy = 0;
            add(eyeDistPanel, c);
        }

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "pointSelected":
                changePointLabel();
                break;
            case "groupSelected":
                changeGroupLabel();
                break;
            case "groupReleased":
                resetGroupLabel();
                break;
            case "centerPoint":
                renderScreen.getCamera().centerAtPoint(renderScreen.getSelectedPoint());
                break;
        }
    }

    /**
     * Changes the text in the point label
     */
    public void changePointLabel() {
        if (renderScreen.getSelectedPoint() != null) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    pointLabel.setText(renderScreen.getSelectedPoint().getDescriptionAsHTML(renderScreen.getVisualisation().getPoints().getCenterOfMassOriginal()));
                }
            });
        }
    }

    /**
     * Changes the text in the group label
     */
    public void changeGroupLabel() {
        if (renderScreen.getSelectedPoint() != null) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    groupLabel.setText(renderScreen.getVisualisation().getTextInfo());
                }
            });
        }
    }

    /**
     * Set the text in the group label to the default text
     */
    public void resetGroupLabel() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                groupLabel.setText("<html><h2>No group selected</h2></html>");
            }
        });
    }

    /**
     * setter
     *
     * @param renderScreen The OpenGL render screen
     */
    public void setRenderScreen(OpenGLScreen renderScreen) {
        this.renderScreen = renderScreen;
        eyeDistPanel.setRenderScreen(renderScreen);
        pointSizePanel.setRenderScreen(renderScreen);
        lineWidthPanel.setRenderScreen(renderScreen);
        speedArrowPanel.setRenderScreen(renderScreen);
        activeGeoPanel.setRenderScreen(renderScreen);
    }

    public ConsoleTextPane getConsolePane() {
        return consoleTextPane;
    }
}
