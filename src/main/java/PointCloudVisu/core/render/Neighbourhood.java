package PointCloudVisu.core.render;

import PointCloudVisu.core.data.Point3D;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.gl2.GLUT;
import java.text.DecimalFormat;
import java.util.ArrayList;
import javax.swing.JToggleButton;

/**
 * This class stores the neighbours data and handles the drawing
 *
 * The neighbours data must follow the FLANN (Fast Library for Approximate
 * Nearest Neighbors) format. To add this drawing capabilty to a visualisation
 * add the appropiate show/hide buttons in the control pane of the visualisation
 *
 * @see <a href="https://www.cs.ubc.ca/research/flann/">FLANN</a>
 *
 * @author jorge.martinez.sanchez
 */
public class Neighbourhood {

    protected OpenGLScreen renderScreen;
    protected ArrayList<ArrayList<Integer>> ids;
    protected ArrayList<ArrayList<Double>> distances;
    protected boolean show = false;

    public Neighbourhood(OpenGLScreen renderScreen) {
        this.renderScreen = renderScreen;
        ids = new ArrayList<>();
        distances = new ArrayList<>();
    }

    public boolean isShow() {
        return show;
    }

    public void setShow(boolean show) {
        this.show = show;
    }

    public void toogleShow(JToggleButton btn) {
        if (!ids.isEmpty() && !distances.isEmpty()) {
            show = !show;
        } else {
            btn.setSelected(false);
            renderScreen.getMainFrame().logIntoConsole("Error: Neighbours are not loaded");
        }
    }

    public void setNeighbours(ArrayList<ArrayList<Integer>> neighbours) {
        if (renderScreen.getVisualisation() != null && renderScreen.getVisualisation().getPoints().size() != neighbours.size()) {
            System.out.println("Warning: points and neighbours do not match size: " + renderScreen.getVisualisation().getPoints().size() + " vs. " + neighbours.size());
        }
        this.ids = neighbours;
    }

    public void setNeighboursDistances(ArrayList<ArrayList<Double>> distances) {
        if (ids.size() != distances.size()) {
            System.out.println("Warning: neighbours and distances do not match size: " + ids.size() + " vs. " + distances.size());
        }
        this.distances = distances;
    }

    private void drawNeighbourPoints(float size) {
        renderScreen.getGl2().glPointSize(size);
        renderScreen.getGl2().glBegin(GL2.GL_POINTS);
        renderScreen.getGl2().glColor3f(0f, 1f, 0f);
        for (Integer id : ids.get(renderScreen.getSelectedPoint().getId())) {
            renderScreen.getGl2().glVertex3d(
                    ((Point3D) (renderScreen.getVisualisation().getPoints().get(id))).getX(),
                    ((Point3D) (renderScreen.getVisualisation().getPoints().get(id))).getY(),
                    ((Point3D) (renderScreen.getVisualisation().getPoints().get(id))).getZ());
        }
        renderScreen.getGl2().glEnd();
    }

    private void drawEdges(float size) {
        renderScreen.getGl2().glLineWidth(size);
        renderScreen.getGl2().glBegin(GL2.GL_LINES);
        for (Integer id : ids.get(renderScreen.getSelectedPoint().getId())) {
            renderScreen.getGl2().glVertex3d(renderScreen.getSelectedPoint().getX(), renderScreen.getSelectedPoint().getY(), renderScreen.getSelectedPoint().getZ());
            renderScreen.getGl2().glVertex3d(
                    ((Point3D) (renderScreen.getVisualisation().getPoints().get(id))).getX(),
                    ((Point3D) (renderScreen.getVisualisation().getPoints().get(id))).getY(),
                    ((Point3D) (renderScreen.getVisualisation().getPoints().get(id))).getZ());
        }
        renderScreen.getGl2().glEnd();
    }

    private void drawEdgeDist() {
        int i = 0;
        for (Integer id : ids.get(renderScreen.getSelectedPoint().getId())) {
            renderScreen.getGl2().glPushMatrix();
            renderScreen.getGl2().glTranslated(
                    ((Point3D) (renderScreen.getVisualisation().getPoints().get(id))).getX(),
                    ((Point3D) (renderScreen.getVisualisation().getPoints().get(id))).getY(),
                    ((Point3D) (renderScreen.getVisualisation().getPoints().get(id))).getZ());
            renderScreen.getGl2().glRasterPos3f(0.1f, 0.0f, 0.0f);
            DecimalFormat df = new DecimalFormat("#0.00");
            renderScreen.getGlut().glutBitmapString(GLUT.BITMAP_HELVETICA_12, String.valueOf(df.format(distances.get(renderScreen.getSelectedPoint().getId()).get(i++))));
            renderScreen.getGl2().glPopMatrix();
        }
    }

    private void drawSelectedBigger() {
        renderScreen.getGl2().glPointSize(renderScreen.getPointSize() * 2);
        renderScreen.getGl2().glBegin(GL2.GL_POINTS);
        renderScreen.getGl2().glColor3f(0f, 0f, 1f);
        renderScreen.getGl2().glVertex3d(renderScreen.getSelectedPoint().getX(), renderScreen.getSelectedPoint().getY(), renderScreen.getSelectedPoint().getZ());
        renderScreen.getGl2().glEnd();
    }

    public void drawNeighbours() {
        if (show && renderScreen.getSelectedPoint() != null) {
            drawNeighbourPoints(renderScreen.getPointSize());
            drawEdges(renderScreen.getLineWidth());
            drawEdgeDist();
            drawSelectedBigger();
        }
    }
}
