package PointCloudVisu.core.render.hi;

import PointCloudVisu.core.data.Point3D;
import PointCloudVisu.core.render.OpenGLScreen;
import java.nio.DoubleBuffer;
import com.jogamp.opengl.GL2;

/**
 * This takes care of slecting point with the mouse, using ray picking It needs
 * access to the transformation matrices of the render screen It keeps the
 * selected point, may lead to inconsistencies when changing between
 * Visualisations, careful
 *
 * @author oscar.garcia
 */
public class OpenGLMouseSelection {

    private final DoubleBuffer position_near;
    private final DoubleBuffer position_far;
    private final OpenGLScreen screen;

    private boolean isPointSelected;
    private int selectedIndex;
    private Point3D selectedPoint;

    /**
     * It is neccessary to create an instance to store the selected point
     *
     * @param screen The OpenGL render screen where the selction is being made
     */
    public OpenGLMouseSelection(OpenGLScreen screen) {
        this.position_near = DoubleBuffer.allocate(3);
        this.position_far = DoubleBuffer.allocate(3);
        this.selectedIndex = -1;
        this.screen = screen;
        this.selectedPoint = new Point3D(0, 0, 0);
        this.isPointSelected = false;
    }

    /**
     * Gets the selected index of the point, relative to the PointArray of the
     * Visualisation selected in the render screen
     *
     * @return The point index in the array
     */
    public int getSelectedIndex() {
        return selectedIndex;
    }

    /**
     * Gets the selected point, as is in the the PointArray of the Visualisation
     * selected in the render screen
     *
     * @return
     */
    public Point3D getSelectedPoint() {
        return isPointSelected ? selectedPoint : null;
    }

    public void resetSelectedPoint() {
        this.isPointSelected = false;
        this.selectedIndex = -1;
        this.selectedPoint = new Point3D(0, 0, 0);
    }

    /**
     * Does all the computation needed to select a point, and stores it in this
     * class atributes. Note that the Y position is 0 at the bottom (may need
     * checking).
     *
     * @param mouseX The mouse X position in the screen, with 0 at the right
     * side
     * @param mouseY The mouse Y position in the screen, with 0 at the BOTTOM!
     * check how your OS gives the values (needs fixing for compatibility)
     * @param radius How close must the point be to the slecting ray to be
     * picked
     * @param epsilon The error margin od the distance to the point
     */
    public void pick(int mouseX, int mouseY, double radius, double epsilon) {
        createRay(mouseX, mouseY);
        collisionWithRay(radius, epsilon);
    }

    /**
     * Creates the selecting ray
     *
     * @param mouseX The mouse X position in the screen, with 0 at the right
     * side
     * @param mouseY The mouse Y position in the screen, with 0 at the BOTTOM!
     * check how your OS gives the values (needs fixing for compatibility)
     */
    protected void createRay(int mouseX, int mouseY) {

        float win_x = (float) mouseX;
        float win_y = screen.getViewport().get(3) - (float) mouseY;

        screen.getGLU().gluUnProject((double) win_x, (double) win_y, 0.0, screen.getMatModelView().array(), 0, screen.getMatProjection().array(), 0, screen.getViewport().array(), 0, position_near.array(), 0);
        screen.getGLU().gluUnProject((double) win_x, (double) win_y, 1.0, screen.getMatModelView().array(), 0, screen.getMatProjection().array(), 0, screen.getViewport().array(), 0, position_far.array(), 0);
    }

    /**
     * Calculates the ditance form the point to the ray
     *
     * @param point A point in the same coordinate space
     * @return The ditance betwwen the point and the ray
     */
    public double distancePointToRay(Point3D point) {
        double AB[] = new double[3];
        AB[0] = position_far.get(0) - position_near.get(0);
        AB[1] = position_far.get(1) - position_near.get(1);
        AB[2] = position_far.get(2) - position_near.get(2);

        double ab_square = AB[0] * AB[0] + AB[1] * AB[1] + AB[2] * AB[2];
        double ab_m = Math.sqrt(ab_square);

        double PA[] = new double[3];
        PA[0] = position_near.get(0) - point.getX();
        PA[1] = position_near.get(1) - point.getY();
        PA[2] = position_near.get(2) - point.getZ();

        double cross[] = new double[3];
        cross[0] = AB[1] * PA[2] - AB[2] * PA[1];
        cross[1] = AB[2] * PA[0] - AB[0] * PA[2];
        cross[2] = AB[0] * PA[1] - AB[1] * PA[0];

        double cross_square = cross[0] * cross[0] + cross[1] * cross[1] + cross[2] * cross[2];
        double cross_m = Math.sqrt(cross_square);

        return cross_m / ab_m;
    }

    /**
     * Checks is the distance is withing parameters. Left public in case it is
     * useful for something other that selecting.
     *
     * @param point A point in the same coordinate space
     * @param radius How close must the point be to the slecting ray to be
     * picked
     * @param epsilon The error margin od the distance to the point
     * @return True if the distance is less that radius+epsilon, false otherwise
     */
    public Boolean RayTest(Point3D point, double radius, double epsilon) {
        double len = distancePointToRay(point);
        len = Math.sqrt(len);
        return len < (radius + epsilon);
    }

    /**
     * Checks the distance between ray and all the points in the render screen
     * selected visualisation.
     *
     * @param radius How close must the point be to the slecting ray to be
     * picked
     * @param epsilon The error margin od the distance to the point
     * @return The index of the first point that is close enough
     */
    public int collisionWithRay(double radius, double epsilon) {
        int i = 0;
        for (Point3D point : screen.getVisualisation().getPoints()) {
            if (RayTest(point, radius, epsilon)) {
                selectedIndex = i;
                selectedPoint = point;
                isPointSelected = true;
                return i;
            }
            i++;
        }
        isPointSelected = false;
        return -1;
    }

    /**
     * Draws the selecting ray in openGL
     */
    public void drawRay() {
        float x = (float) position_near.get(0);
        float y = (float) position_near.get(1);
        float z = (float) position_near.get(2);
        float x2 = (float) position_far.get(0);
        float y2 = (float) position_far.get(1);
        float z2 = (float) position_far.get(2);

        screen.getGl2().glColor3f(1.0f, 0.0f, 0.0f);
        screen.getGl2().glBegin(GL2.GL_LINES);
        screen.getGl2().glVertex3f(x, y, z);
        screen.getGl2().glVertex3f(x2, y2, z2);
        screen.getGl2().glEnd();
    }

    /**
     * Draws a highlight over the selected point in openGL
     */
    public void drawSelected() {
        if (isPointSelected) {
            screen.getGl2().glColor3f(1.0f, 0.0f, 0.0f);

            screen.getGl2().glBegin(GL2.GL_LINES);
            screen.getGl2().glVertex3f((float) screen.getVisualisation().getPoints().get(selectedIndex).getX(),
                     (float) screen.getVisualisation().getPoints().get(selectedIndex).getY(),
                     (float) screen.getVisualisation().getPoints().get(selectedIndex).getZ()
            );
            screen.getGl2().glVertex3f((float) screen.getVisualisation().getPoints().get(selectedIndex).getX(),
                     (float) screen.getVisualisation().getPoints().get(selectedIndex).getY(),
                     (float) screen.getVisualisation().getPoints().get(selectedIndex).getZ() + 5
            );
            screen.getGl2().glEnd();
        }
    }
}
