package Olivia.extended.overlays;

import Olivia.core.Overlay;
import Olivia.core.VisualisationManager;
import Olivia.core.data.Point3D;
import Olivia.core.data.Point3D_id;
import Olivia.core.render.OpenGLScreen;
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
public class Neighbourhood<VM extends VisualisationManager> extends Overlay<VM>{

    protected int id;
    protected ArrayList <Integer> neighbour_ids;
    protected ArrayList<Double> distances;
    protected boolean show = false;

    public Neighbourhood(VM visualisationManager, int id) {
        super(visualisationManager,"neighbourhood");
        this.id = id;
        neighbour_ids = new ArrayList<>();
        distances = new ArrayList<>();
        this.bounds.add((Point3D) visualisationManager.getPointCloud().get(id));
    }

    public void setNeighbours(ArrayList<Integer> neighbours) {
        this.neighbour_ids = neighbours;
        for (Integer n_id : neighbour_ids) {
            this.bounds.add((Point3D) visualisationManager.getPointCloud().get(n_id));
        }  
    }

    public void setNeighboursDistances(ArrayList<Double> distances) {
        if (this.neighbour_ids.size() != distances.size()) {
            System.out.println("Warning: neighbours and distances do not match size: " + this.neighbour_ids + " vs. " + distances.size());
        }
        this.distances = distances;
    }

    private void drawNeighbourPoints(float size) {
        visualisationManager.getRenderScreen().getGl2().glPointSize(size);
        visualisationManager.getRenderScreen().getGl2().glBegin(GL2.GL_POINTS);
        visualisationManager.getRenderScreen().getGl2().glColor3f(0f, 1f, 0f);
        for (Integer n_id : neighbour_ids) {
            visualisationManager.getRenderScreen().getGl2().glVertex3d(((Point3D_id) (visualisationManager.getPointCloud().get(n_id))).getX(),
                    ((Point3D_id) (visualisationManager.getPointCloud().get(n_id))).getY(),
                    ((Point3D_id) (visualisationManager.getPointCloud().get(n_id))).getZ());
        }
        visualisationManager.getRenderScreen().getGl2().glEnd();
    }

    private void drawEdges(float size) {
        visualisationManager.getRenderScreen().getGl2().glLineWidth(size);
        visualisationManager.getRenderScreen().getGl2().glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_LINE);
        visualisationManager.getRenderScreen().getGl2().glBegin(GL2.GL_LINES);
        for (Integer n_id : neighbour_ids) {
            visualisationManager.getRenderScreen().getGl2().glVertex3d(((Point3D_id) (visualisationManager.getPointCloud().get(id))).getX(),
                    ((Point3D_id) (visualisationManager.getPointCloud().get(id))).getY(),
                    ((Point3D_id) (visualisationManager.getPointCloud().get(id))).getZ());
            visualisationManager.getRenderScreen().getGl2().glVertex3d(((Point3D_id) (visualisationManager.getPointCloud().get(n_id))).getX(),
                    ((Point3D_id) (visualisationManager.getPointCloud().get(n_id))).getY(),
                    ((Point3D_id) (visualisationManager.getPointCloud().get(n_id))).getZ());
        }
        visualisationManager.getRenderScreen().getGl2().glEnd();
    }

    private void drawEdgeDist() {
        int i = 0;
        for (Integer n_id : neighbour_ids) {
            visualisationManager.getRenderScreen().getGl2().glPushMatrix();
            visualisationManager.getRenderScreen().getGl2().glTranslated(((Point3D_id) (visualisationManager.getPointCloud().get(n_id))).getX(),
                    ((Point3D_id) (visualisationManager.getPointCloud().get(n_id))).getY(),
                    ((Point3D_id) (visualisationManager.getPointCloud().get(n_id))).getZ());
            visualisationManager.getRenderScreen().getGl2().glRasterPos3f(0.1f, 0.0f, 0.0f);
            DecimalFormat df = new DecimalFormat("#0.00");
            visualisationManager.getRenderScreen().getGlut().glutBitmapString(GLUT.BITMAP_HELVETICA_12, String.valueOf(df.format(distances.get(i++))));
            visualisationManager.getRenderScreen().getGl2().glPopMatrix();
        }
    }

    private void drawSelectedBigger() {
        visualisationManager.getRenderScreen().getGl2().glPointSize(visualisationManager.getRenderScreen().getPointSize() * 2);
        visualisationManager.getRenderScreen().getGl2().glBegin(GL2.GL_POINTS);
        visualisationManager.getRenderScreen().getGl2().glColor3f(0f, 0f, 1f);
        visualisationManager.getRenderScreen().getGl2().glVertex3d(((Point3D_id) (visualisationManager.getPointCloud().get(id))).getX(),
                    ((Point3D_id) (visualisationManager.getPointCloud().get(id))).getY(),
                    ((Point3D_id) (visualisationManager.getPointCloud().get(id))).getZ());
        visualisationManager.getRenderScreen().getGl2().glEnd();
    }

    public void drawNeighbours() {
        drawNeighbourPoints(visualisationManager.getRenderScreen().getPointSize());
        drawEdges(visualisationManager.getRenderScreen().getLineWidth());
        drawEdgeDist();
        drawSelectedBigger();
    }

    @Override
    public void drawShape(OpenGLScreen renderScreen) {
        //System.out.println("Drawing with " + this.neighbour_ids.size() + "neig and " + this.distances.size() + " distances");
        drawNeighbours();
    }

    //@Override
    /*
    public double[] getCenter() {
        return ((Point3D_id) (visualisationManager.getPointCloud().get(id))).toArray();
    }
    */
    
    //@Override
    @Override
    public Point3D getSelectingPoint(){
        return (Point3D) visualisationManager.getPointCloud().get(id);
    }
}
