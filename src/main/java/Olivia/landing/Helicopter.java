package Olivia.landing;

import Olivia.core.render.OpenGLScreen;
import com.jogamp.opengl.GL2;

/**
 *
 * @author oscar.garcia
 */
public class Helicopter {

    float x;
    float y;
    float z;
    float radius;
    float height;
    private OpenGLScreen renderScreen;

    public Helicopter(OpenGLScreen renderScreen, float x, float y, float z, float radius, float height) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.radius = radius;
        this.height = height;
        this.renderScreen = renderScreen;

    }

    public Helicopter(double x, double y, double z, float radius, float height) {
        this.x = (float) x;
        this.y = (float) y;
        this.z = (float) z;
        this.radius = radius;
        this.height = height;

    }

    public void draw() {
        renderScreen.getGl2().glColor3f(0.0f, 0.0f, 1.0f);
        renderScreen.getGl2().glBegin(GL2.GL_LINES);
        renderScreen.getGl2().glVertex3f(x - radius, y, z + height);
        renderScreen.getGl2().glVertex3f(x + radius, y, z + height);
        renderScreen.getGl2().glEnd();
        renderScreen.getGl2().glBegin(GL2.GL_LINES);
        renderScreen.getGl2().glVertex3f(x, y - radius, z + height);
        renderScreen.getGl2().glVertex3f(x, y + radius, z + height);
        renderScreen.getGl2().glEnd();
    }
}
