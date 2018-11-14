package Olivia.landing;

import Olivia.core.Overlay;
import Olivia.core.data.Point3D_id;
import Olivia.core.render.OpenGLScreen;
import java.util.ArrayList;
import java.util.Collections;
import com.jogamp.opengl.GL2;

/**
 *
 * @author oscar.garcia
 */
public class LandingApproachOverlay extends Overlay<LandingVisualisationManager>{

    public static final float DEFAULT_RADIUS = 12.5f;
    private static final float RADIUS_HIGH = 30f;
    
    private class OcluddedPair implements Comparable<OcluddedPair> {

        public double a;
        public double b;

        public OcluddedPair(double a, double b) {
            this.a = a;
            this.b = b;
        }

        @Override
        public int compareTo(OcluddedPair another) {
            // -1 - less than, 1 - greater than, 0 - equal, all inversed for descending
            return this.a < another.a ? -1 : (this.a > another.a) ? 1 : 0;
        }

    }

    //This must be even+2 to have enough verticves for quad_strip
    public static int RESOLUTION = 102;
    public static float HOOVER = 10;

    private ArrayList<OcluddedPair> ocludded;
    
    protected float radius;

    public LandingApproachOverlay(LandingVisualisationManager visualisationManager, double x, double y, double z) {
        super(visualisationManager);
        this.bounds.setCentre(new Point3D_id(x, y, z));
        this.ocludded = new ArrayList<>();
        this.radius = DEFAULT_RADIUS;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }
    
    

    public void addOcluddedPair(double a, double b) {
        if (a < b) {
            ocludded.add(new OcluddedPair(a, b));
        } else {
            ocludded.add(new OcluddedPair(0, b));
            ocludded.add(new OcluddedPair(a, (float) Math.PI * 2));
        }
        Collections.sort(ocludded);
    }

    private void drawCircle(OpenGLScreen renderScreen, float radius) {
        double iter;
        double x = this.bounds.getCentre().getX();
        double y = this.bounds.getCentre().getY();
        double z = this.bounds.getCentre().getZ() + HOOVER;

        double rot = 0;//(float) (Math.PI/1);

        renderScreen.getGl2().glLineWidth(3.0f);
        renderScreen.getGl2().glColor3f(0.3f, 0.3f, 0.3f);

        double step = (Math.PI * 2) / RESOLUTION;

        renderScreen.getGl2().glBegin(GL2.GL_LINES);
        renderScreen.getGl2().glColor3f(0.0f, 0.0f, 1.0f);
        renderScreen.getGl2().glVertex3d(x, y, z - HOOVER);
        renderScreen.getGl2().glVertex3d(x, y, z);
        renderScreen.getGl2().glEnd();

        Helicopter helo = new Helicopter(renderScreen, (float) this.bounds.getCentre().getX(), (float) this.bounds.getCentre().getY(), (float) this.bounds.getCentre().getZ(), 5, 4);
        helo.draw();
        /*
        gl2.glBegin(GL2.GL_LINES);
        gl2.glColor3f(0.0f, 0.0f, 0.5f);
            gl2.glVertex3f(x,y,z);
            gl2.glVertex3f(x,y+radius,z);
        gl2.glEnd();
         */

        iter = 0;

        renderScreen.getGl2().glBegin(GL2.GL_LINE_LOOP);
        for (OcluddedPair oclusion : ocludded) {
            while (iter <= oclusion.b) {
                if (iter < oclusion.a) {
                    renderScreen.getGl2().glColor3f(0.0f, 1.0f, 0.0f);
                } else {
                    renderScreen.getGl2().glColor3f(1.0f, 0.0f, 0.0f);
                }
                renderScreen.getGl2().glVertex3d(Math.sin(iter + rot) * radius + x, Math.cos(iter + rot) * radius + y, z);
                iter = iter + step;
            }
            //System.out.println("ended at " + iter);
        }
        renderScreen.getGl2().glColor3f(0.0f, 1.0f, 0.0f);
        while (iter <= (float) (Math.PI * 2)) {
            renderScreen.getGl2().glVertex3d(Math.sin(iter + rot) * radius + x, Math.cos(iter + rot) * radius + y, z);
            iter = iter + step;
        }
        /*
        gl2.glBegin(GL2.GL_LINE_LOOP);
        while(iter<Math.PI/4){
                    gl2.glColor3f(0.0f, 1.0f, 0.0f);
                gl2.glVertex3f((float)Math.cos(iter+rot)*radius+x,(float)Math.sin(iter+rot)*radius+y,z);
                iter = iter + step;
            }
         */
        renderScreen.getGl2().glEnd();
        //System.out.println("a0=" + ocludded.get(0).a +  ", b0="+ ocludded.get(0).b +"  a1=" + ocludded.get(1).a +  ", b1="+ ocludded.get(1).b );

    }

    private void drawCone(OpenGLScreen renderScreen, float radiusLow, float radiusHigh, float angleDeg, float alpha) {
        double iter;
        double x = this.bounds.getCentre().getX();
        double y = this.bounds.getCentre().getY();
        double z = this.bounds.getCentre().getZ();

        /*
         * cot(alpha) = h / radius
         * h = radius / tan(alpha)
         */
        double angleRad = Math.PI / 2 - Math.toRadians(angleDeg);

        double h1 = radiusLow / Math.tan(angleRad);
        double h2 = radiusHigh / Math.tan(angleRad);

        double rot = 0;//(float) (Math.PI/1);

        renderScreen.getGl2().glLineWidth(3.0f);
        renderScreen.getGl2().glColor3f(0.3f, 0.3f, 0.3f);

        double step = (Math.PI * 2) / RESOLUTION;

        renderScreen.getGl2().glBegin(GL2.GL_LINES);
        renderScreen.getGl2().glColor3f(0.0f, 0.0f, 1.0f);
        renderScreen.getGl2().glVertex3d(x, y, z);
        renderScreen.getGl2().glVertex3d(x, y, z + h1);
        renderScreen.getGl2().glEnd();

        Helicopter helo = new Helicopter(renderScreen, (float) this.bounds.getCentre().getX(), (float) this.bounds.getCentre().getY(), (float) this.bounds.getCentre().getZ(), 5, 4);
        helo.draw();

        iter = 0;

        renderScreen.getGl2().glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_FILL);
        renderScreen.getGl2().glBegin(GL2.GL_QUAD_STRIP);
        for (OcluddedPair oclusion : ocludded) {
            while (iter <= oclusion.b) {
                if (iter < oclusion.a) {
                    renderScreen.getGl2().glColor4f(0.0f, 1.0f, 0.0f, alpha);
                } else {
                    renderScreen.getGl2().glColor4f(1.0f, 0.0f, 0.0f, alpha);
                }
                renderScreen.getGl2().glVertex3d(Math.sin(iter + rot) * radiusLow + x, Math.cos(iter + rot) * radiusLow + y, z + h1);
                renderScreen.getGl2().glVertex3d(Math.sin(iter + rot) * radiusHigh + x, Math.cos(iter + rot) * radiusHigh + y, z + h2);
                iter = iter + step;
            }
            //System.out.println("ended at " + iter);
        }
        renderScreen.getGl2().glColor4f(0.0f, 1.0f, 0.0f, alpha);
        while (iter <= (float) (Math.PI * 2)) {
            renderScreen.getGl2().glVertex3d(Math.sin(iter + rot) * radiusLow + x, Math.cos(iter + rot) * radiusLow + y, z + h1);
            renderScreen.getGl2().glVertex3d(Math.sin(iter + rot) * radiusHigh + x, Math.cos(iter + rot) * radiusHigh + y, z + h2);
            iter = iter + step;
        }
        renderScreen.getGl2().glEnd();
        //System.out.println("a0=" + ocludded.get(0).a +  ", b0="+ ocludded.get(0).b +"  a1=" + ocludded.get(1).a +  ", b1="+ ocludded.get(1).b );

    }
    
    
     private void drawConeF(OpenGLScreen renderScreen, float radiusLow, float radiusHigh, float angleDeg, float alpha) {
        float iter;
        float x = (float) this.bounds.getCentre().getX();
        float y = (float) this.bounds.getCentre().getY();
        float z = (float) this.bounds.getCentre().getZ();

        /*
         * cot(alpha) = h / radius
         * h = radius / tan(alpha)
         */
        double angleRad = Math.PI / 2 - Math.toRadians(angleDeg);

        float h1 = radiusLow / (float) Math.tan(angleRad);
        float h2 = radiusHigh / (float) Math.tan(angleRad);

        float rot = 0;//(float) (Math.PI/1);

        renderScreen.getGl2().glLineWidth(3.0f);
        renderScreen.getGl2().glColor3f(0.3f, 0.3f, 0.3f);

        float step = (float) (Math.PI * 2) / RESOLUTION;

        renderScreen.getGl2().glBegin(GL2.GL_LINES);
        renderScreen.getGl2().glColor3f(0.0f, 0.0f, 1.0f);
        renderScreen.getGl2().glVertex3f(x, y, z);
        renderScreen.getGl2().glVertex3f(x, y, z + h1);
        renderScreen.getGl2().glEnd();

        Helicopter helo = new Helicopter(renderScreen, x, y, z, 5, 4);
        helo.draw();

        iter = 0;

        renderScreen.getGl2().glBegin(GL2.GL_QUAD_STRIP);
        for (OcluddedPair oclusion : ocludded) {
            while (iter <= oclusion.b) {
                if (iter < oclusion.a) {
                    renderScreen.getGl2().glColor4f(0.0f, 1.0f, 0.0f, alpha);
                } else {
                    renderScreen.getGl2().glColor4f(1.0f, 0.0f, 0.0f, alpha);
                }
                renderScreen.getGl2().glVertex3f((float) Math.sin(iter + rot) * radiusLow + x, (float) Math.cos(iter + rot) * radiusLow + y, z + h1);
                renderScreen.getGl2().glVertex3f((float) Math.sin(iter + rot) * radiusHigh + x, (float) Math.cos(iter + rot) * radiusHigh + y, z + h2);
                iter = iter + step;
            }
            //System.out.println("ended at " + iter);
        }
        renderScreen.getGl2().glColor4f(0.0f, 1.0f, 0.0f, alpha);
        while (iter <= (float) (Math.PI * 2)) {
            renderScreen.getGl2().glVertex3f((float) Math.sin(iter + rot) * radiusLow + x, (float) Math.cos(iter + rot) * radiusLow + y, z + h1);
            renderScreen.getGl2().glVertex3f((float) Math.sin(iter + rot) * radiusHigh + x, (float) Math.cos(iter + rot) * radiusHigh + y, z + h2);
            iter = iter + step;
        }
        renderScreen.getGl2().glEnd();
        //System.out.println("a0=" + ocludded.get(0).a +  ", b0="+ ocludded.get(0).b +"  a1=" + ocludded.get(1).a +  ", b1="+ ocludded.get(1).b );

    }

    
    
    
    @Override
    protected void drawShape(OpenGLScreen renderScreen) {
        //System.out.println("Drawing approach at " + this.bounds.getCentre() +" with " + radius );
        this.drawCone(visualisationManager.getRenderScreen(), radius, RADIUS_HIGH, 10f, 0.5f);
        //this.drawCircle(renderScreen, radius);
    }
 
}
