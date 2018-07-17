package PointCloudVisu.core.render;

import PointCloudVisu.core.PointCloudVisu;
import static PointCloudVisu.core.PointCloudVisu.*;
import PointCloudVisu.core.data.Point3D;
import PointCloudVisu.core.render.hi.OpenGLKeyListener;
import PointCloudVisu.core.render.hi.OpenGLMouseListener;
import PointCloudVisu.core.Visualisation;
import PointCloudVisu.core.data.PointArray;
import PointCloudVisu.core.gui.MainFrame;
import com.jogamp.opengl.util.Animator;
import com.jogamp.opengl.util.gl2.GLUT;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.DoubleBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GL2ES1;
import com.jogamp.opengl.GL2GL3;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.fixedfunc.GLLightingFunc;
import com.jogamp.opengl.fixedfunc.GLMatrixFunc;
import com.jogamp.opengl.glu.GLU;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.JInternalFrame;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;

/**
 * This is the render screen, it takes care of most of the openGL operations It
 * changes among different Visualisations and calls their draw() to render them
 * It uses each visualisation Camera to execute translations an rotations It
 * allows other classes to listen for a "pointSelected" event and get the point
 * slectede though the OpenGLMouseListener
 * <p>
 * Note: It is strongly recommended to always refetch the GL object out of the
 * GLAutoDrawable as opposed to storing the GL in a field and referencing it
 * (multithreading issues)
 * </p>
 *
 * @author oscar.garcia, jorge.martinez.sanchez
 * @see <a href="https://jogamp.org/jogl/doc/userguide/#joglapi">Using the JOGL
 * API</a>
 */
public class OpenGLScreen implements GLEventListener {

    /**
     * The identifier
     */
    protected int id;
    /**
     * The custom visualisation
     */
    protected Visualisation visualisation;
    /**
     * Its own Camera to manage rotation and translation
     */
    protected Camera camera;
    /**
     * Its own Capture to save the render screen into image/video
     */
    protected Capture capture;
    /**
     * The key listener
     */
    protected OpenGLKeyListener keyListener;
    /**
     * The mause listener, contains the point selection
     */
    protected OpenGLMouseListener mouseListener;
    /**
     * Everyone listening for events, like pointSelected
     */
    protected ArrayList<ActionListener> listeners;

    //public static int [] vboIndices = new int [] {-1};
    /**
     * This is GLU, it may be instantiated many times, this way we do only once
     */
    protected GLU glu;
    /**
     * This is GL2, it may be instantiated many times, this way we do only once
     */
    protected GL2 gl2;
    /**
     * This is GLUT, it may be instantiated many times, this way we do only once
     */
    protected GLUT glut;
    /**
     *
     */
    protected Animator animator;

    /**
     * We store the Frame to access frame size and position (capture methods)
     */
    protected JInternalFrame frame;

    /*
     * Display variables
     */
    /**
     * The current point size
     */
    protected int pointSize;
    /**
     * The current line width
     */
    protected int lineWidth;
    /**
     * The current eye distance
     */
    protected float eyeDist;
    /**
     * The current eye distance offset
     */
    protected int eyeDistOffset;

    /**
     * We store the MatrixModelView in case someone needs it (such as mouse
     * selection)
     */
    protected DoubleBuffer matModelView;
    /**
     * We store the MatrixProjection in case someone needs it (such as mouse
     * selection)
     */
    protected DoubleBuffer matProjection;
    /**
     * We store the Viewport in case someone needs it (such as mouse selection)
     */
    protected IntBuffer viewport;
    /**
     * Flag to enable/disable stereoscopic 3D visualisation
     */
    protected boolean isStereo3D;
    /**
     *
     */
    protected MainFrame mainFrame;
    
    /**
     * Create a custom OpenGL capabilities mainly to enable stereoscopic 3D
     */
    private GLCapabilities createCapabilites() {
        GLProfile glp = GLProfile.get(GLProfile.GL2);
        GLCapabilities caps = new GLCapabilities(glp);
        caps.setStereo(isStereo3D);
        //caps.setDepthBits(32); // This was a solution for someone with depth problems, does not seem to do anything
        return caps;
    }

    public class FrameEventListener implements InternalFrameListener {

        @Override
        public void internalFrameActivated(InternalFrameEvent e) {
            System.out.println(id + " activated");
            if (isScreenAdded(OpenGLScreen.this) && OpenGLScreen.this != getActiveScreen()) {
                setActiveScreen(OpenGLScreen.this);
            }
        }

        @Override
        public void internalFrameClosed(InternalFrameEvent e) {
            System.out.println(id + " closed");
        }

        @Override
        public void internalFrameClosing(InternalFrameEvent e) {
            System.out.println(id + " closing");
            animator.stop();
            frame.dispose();
            removeScreen(OpenGLScreen.this);
        }

        @Override
        public void internalFrameDeactivated(InternalFrameEvent e) {
            System.out.println(id + " deactivated");
        }

        @Override
        public void internalFrameDeiconified(InternalFrameEvent e) {
            //OpenGLScreen.this.frame.setSize(640, 480);
            if (OpenGLScreen.this.getVisualisation() != null) {
                OpenGLScreen.this.getVisualisation().getPoints().repack();
            }

            System.out.println(id + " deiconified");
        }

        @Override
        public void internalFrameIconified(InternalFrameEvent e) {
            System.out.println(id + " iconified");
        }

        @Override
        public void internalFrameOpened(InternalFrameEvent e) {
            System.out.println(id + " opened");
        }
    }

    /**
     * Creates an AWT frame to render the visualisation
     */
    public void createRenderFrame() {
        final GLCanvas canvas = new GLCanvas(createCapabilites());
        frame = new JInternalFrame("Visualisation " + id, true, true, true, true);
        animator = new Animator(canvas);
        canvas.addGLEventListener(this);
        frame.add(canvas);
        frame.setResizable(true);
        frame.addInternalFrameListener(new FrameEventListener());
        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                if (OpenGLScreen.this == getActiveScreen()) {
                    if (((JInternalFrame) e.getComponent()).isMaximum()) {
                        System.out.println(id + " Maximized");
                        PointCloudVisu.setInactiveScreensIconify(true); // Iconify hidden render screens to stop them for being rendered                     
                    } else {
                        System.out.println(id + " Un-maximized");
                        PointCloudVisu.setInactiveScreensIconify(false); // Undo the previous iconify
                        PointCloudVisu.updateRenderFrameLayout();
                    }
                }
            }
        });
        mainFrame.getRenderPane().add(frame);
        frame.setVisible(true);
        animator.start();
        canvas.requestFocusInWindow();
    }

    /**
     * Initialises many things
     *
     * @param id The identifier of the render screen
     * @param mainFrame The main frame of the application
     * @param isStereo3D The flag to indicate if stereoscopic 3D is enabled
     */
    public OpenGLScreen(int id, MainFrame mainFrame, boolean isStereo3D) {
        this.id = id;
        this.mainFrame = mainFrame;
        this.isStereo3D = isStereo3D;
        viewport = IntBuffer.allocate(4);
        matProjection = DoubleBuffer.allocate(16);
        matModelView = DoubleBuffer.allocate(16);
        pointSize = 1;
        lineWidth = 1;
        eyeDist = 1;
        eyeDistOffset = 0;
        glut = new GLUT();
        glu = new GLU();
        keyListener = new OpenGLKeyListener(this);
        mouseListener = new OpenGLMouseListener(this);
        listeners = new ArrayList<>();
        camera = new Camera(this);
        capture = new Capture(this);
    }

    /**
     * setter
     *
     * @param visualisation The custom visualisation
     */
    public void setVisualisation(Visualisation visualisation) {
        this.visualisation = visualisation;
        visualisation.setSelected(true);
        System.out.println("Setting centre at "
                + visualisation.getPoints().getCenterOfMass()[0] + " "
                + visualisation.getPoints().getCenterOfMass()[1] + " "
                + visualisation.getPoints().getCenterOfMass()[2]);
        visualisation.getRenderScreen().getCamera().setCloudCentre(visualisation.getPoints().getCenterOfMass());
        mouseListener.getMouseSelection().resetSelectedPoint();
        fireEvent("visuActive");
    }

    /**
     * Adds an ActionListener
     *
     * @param al The action listener
     */
    public void addActionListener(ActionListener al) {
        listeners.add(al);
    }

    /**
     * Removes an Action Listener
     *
     * @param al The action listener
     */
    public void removeActionListener(ActionListener al) {
        listeners.remove(al);
    }

    /**
     * Fires the ActionEvent "pointSelected" to all listeners Should be
     * protected, but it is being called from the mouse listener
     *
     * @param name The name/identifier of the event
     */
    public void fireEvent(String name) {
        ActionEvent ae = new ActionEvent(this, ActionEvent.ACTION_FIRST, name);
        for (ActionListener ac : listeners) {
            ac.actionPerformed(ae);
        }
    }

    /**
     * Shows information about the visualization
     *
     * @param gLDrawable this drawable
     */
    public void drawStats(GLAutoDrawable gLDrawable) {
        //if(!showStats) return;
        int x = 10;
        int y = 20;
        float time = gLDrawable.getAnimator().getLastFPS();
        gl2 = gLDrawable.getGL().getGL2();
        gl2.glColor3f(1f, 1f, 1f);
        gl2.glReadBuffer(GL2.GL_FRONT);
        gl2.glGetIntegerv(GL2.GL_VIEWPORT, viewport.array(), 0);
        gl2.glWindowPos2i(x, viewport.array()[3] - y);
        //glut.glutBitmapString(GLUT.BITMAP_HELVETICA_12, "MODE: " + modeToString());    
        gl2.glWindowPos2i(x, viewport.array()[3] - y * 2);
        if (visualisation != null) {
            glut.glutBitmapString(GLUT.BITMAP_HELVETICA_12, "POINTS: " + visualisation.getPoints().size());
        } else {
            glut.glutBitmapString(GLUT.BITMAP_HELVETICA_12, "No points to draw");
        }
        gl2.glWindowPos2i(x, viewport.array()[3] - y * 3);
        glut.glutBitmapString(GLUT.BITMAP_HELVETICA_12, "FPS: " + String.format("%.0f", time));
        gl2.glWindowPos2i(x, viewport.array()[3] - y * 4);
        glut.glutBitmapString(GLUT.BITMAP_HELVETICA_12, "AVG: " + String.format("%.0f", gLDrawable.getAnimator().getTotalFPS()));
        if (isStereo3D) {
            gl2.glWindowPos2i(x, viewport.array()[3] - y * 5);
            glut.glutBitmapString(GLUT.BITMAP_HELVETICA_12, "EYE: " + String.format("%.2f", eyeDist));
        }
    }

    public void drawCircle(double cx, double cy, double cz, double radius, int num_segments) {
        gl2.glBegin(GL.GL_LINE_LOOP);
        for (int i = 0; i < num_segments; i++) {
            double theta = 2.0d * Math.PI * (double) i / (double) num_segments;
            double x = radius * Math.cos(theta);
            double y = radius * Math.sin(theta);
            gl2.glVertex3d(x + cx, y + cy, cz);
        }
        gl2.glEnd();
    }

//    private void drawAxis(GLAutoDrawable gLDrawable) {
//        double length = 0.125;
//        double offset = length / 2.0;
//        double coords[] = new double[4];
//        double xWindow = 100;
//        double yWindow = frame.getHeight() / 2.0;
//        yWindow = viewport.array()[3] - yWindow;
//
//        gl2 = gLDrawable.getGL().getGL2();
//        glu.gluUnProject(xWindow, yWindow, 0.1, matModelView.array(), 0, matProjection.array(), 0, viewport.array(), 0, coords, 0);
//        double x = coords[0], y = coords[1], z = coords[2];
//
//        gl2.glColor3f(1, 0, 0);
//        gl2.glBegin(GL2.GL_LINES);
//        gl2.glVertex3d(x - offset, y, z);
//        gl2.glVertex3d(x + offset, y, z);
//        gl2.glColor3f(0, 1, 0);
//        gl2.glVertex3d(x, y - offset, z);
//        gl2.glVertex3d(x, y + offset, z);
//        gl2.glColor3f(0, 0, 1);
//        gl2.glVertex3d(x, y, z - offset);
//        gl2.glVertex3d(x, y, z + offset);
//        gl2.glEnd();
//        gl2.glColor3f(1, 1, 1);
//        drawCircle(x, y, z, offset, 30);
//    }

    /**
     * Here is where all drawing is done Requires some visuData
     */
    public void drawScene() {
        if (visualisation == null) {
            return;
        }
        visualisation.draw();
        visualisation.getGeometry().draw();
        visualisation.getNeighbourhood().drawNeighbours();
        //mouseListener.getMouseSelection().drawSelected();
        //mouseListener.getMouseSelection().drawRay();
    }

    /**
     * Inits many things
     *
     * @param glad this drawable
     */
    @Override
    public void init(GLAutoDrawable glad) {
        glad.getAnimator().setUpdateFPSFrames(1, null);
        gl2 = glad.getGL().getGL2();
        gl2.glShadeModel(GLLightingFunc.GL_SMOOTH);
        gl2.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        gl2.glClearDepth(1.0f);
        gl2.glEnable(GL2.GL_DEPTH_TEST);
        gl2.glDepthFunc(GL2.GL_LEQUAL);
        gl2.glHint(GL2ES1.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST);
        ((Component) glad).addKeyListener(keyListener);
        ((Component) glad).addMouseListener(mouseListener);
        ((Component) glad).addMouseWheelListener(mouseListener);
        ((Component) glad).addMouseMotionListener(mouseListener);
        System.out.println("Drawing...");
    }

    /**
     * What to do on close
     *
     * @param glad this drawable
     */
    @Override
    public void dispose(GLAutoDrawable glad) {
        this.visualisation.getPoints().freeVBO(this);
        System.out.println("Exiting...");
    }

    /**
     * Draws everything
     *
     * @param glad this drawable
     */
    @Override
    public void display(GLAutoDrawable glad) {
        if (visualisation == null) {
            return;
        }
        Camera cam = visualisation.getRenderScreen().getCamera();
        Capture cap = visualisation.getRenderScreen().getCapture();

        gl2 = glad.getGL().getGL2();
        gl2.glEnable(GL2.GL_POINT_SMOOTH);
        gl2.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_SRC_ALPHA);
        gl2.glPointSize(pointSize);

        if (!isStereo3D) {
            gl2.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
            gl2.glLoadIdentity();
            cam.doTranslateAndRotate(this);
            gl2.glGetDoublev(GL2.GL_PROJECTION_MATRIX, matProjection);
            gl2.glGetIntegerv(GL2.GL_VIEWPORT, viewport);
            gl2.glGetDoublev(GL2.GL_MODELVIEW_MATRIX, matModelView);
            drawScene();
            drawStats(glad);
            //drawAxis(glad);
            if (cap.isCaptureImage()) {
                cap.screenshot();
            }
            if (cap.isCaptureVideo()) {
                cap.recordVideo();
            }
        } else {
            double eyeDistStep = 0.20;
            eyeDist = (float) (1.0 + eyeDistOffset * eyeDistStep);
            double aspect = 4 / 3.0;
            double fov = 50;
            double zNear = 10;
            double zFar = 5000.0;
            double top = Math.tan(fov * Math.PI / 360.0) * zNear;
            double bottom = -top;
            double left = aspect * bottom;
            double right = aspect * top;
            double fasym = 0.05;

            gl2.glDrawBuffer(GL2GL3.GL_BACK_LEFT);
            gl2.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
            gl2.glLoadIdentity();
            gl2.glFrustum(left - fasym, right - fasym, bottom, top, zNear, zFar);
            gl2.glTranslated(eyeDist, 0, 0);
            cam.doTranslateAndRotate(this);
            gl2.glPushMatrix();         

            gl2.glGetDoublev(GL2.GL_PROJECTION_MATRIX, matProjection);
            gl2.glGetIntegerv(GL2.GL_VIEWPORT, viewport);
            gl2.glGetDoublev(GL2.GL_MODELVIEW_MATRIX, matModelView);

            drawScene();
            drawStats(glad);
            if (cap.isCaptureVideo()) {
                cap.recordVideo3D(Capture.RIGHT_SIDE);
            }
            gl2.glPopMatrix();

            gl2.glDrawBuffer(GL2GL3.GL_BACK_RIGHT);
            gl2.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
            gl2.glLoadIdentity();
            gl2.glFrustum(left + fasym, right + fasym, bottom, top, zNear, zFar);
            gl2.glTranslated(-eyeDist, 0, 0);
            cam.doTranslateAndRotate(this);
            gl2.glPushMatrix();

            drawScene();
            drawStats(glad);
            if (cap.isCaptureVideo()) {
                cap.recordVideo3D(Capture.LEFT_SIDE);
            }
            gl2.glPopMatrix();
        }
    }

    /**
     * How to reshape
     *
     * @param glad this drawable
     * @param x values
     * @param y values
     * @param width values
     * @param height values
     */
    @Override
    public void reshape(GLAutoDrawable glad, int x, int y, int width, int height) {
        if (height <= 0) {
            height = 1;
        }
        float h = (float) width / (float) height;
        gl2 = glad.getGL().getGL2();
        gl2.glMatrixMode(GLMatrixFunc.GL_PROJECTION);
        gl2.glGetDoublev(GL2.GL_PROJECTION_MATRIX, matProjection);
        gl2.glGetIntegerv(GL2.GL_VIEWPORT, viewport);
        gl2.glLoadIdentity();
        if (!isStereo3D) {
            glu.gluPerspective(50.0f, h, 1.0, 5000.0);
        }
        gl2.glMatrixMode(GLMatrixFunc.GL_MODELVIEW);
        gl2.glGetDoublev(GL2.GL_MODELVIEW_MATRIX, matModelView);
        gl2.glLoadIdentity();
    }

    /*
     * Getters and Setters
     */
    /**
     * getter
     *
     * @return The mouse selected point, null if none
     */
    public Point3D getSelectedPoint() {
        return mouseListener.getMouseSelection().getSelectedPoint();
    }

    /**
     * getter
     *
     * @return The mouse selected point, null if none
     */
    public int getSelectedPointIndex() {
        return mouseListener.getMouseSelection().getSelectedIndex();
    }

    /**
     * getter
     *
     * @return The visualisation, null if none
     */
    public Visualisation<Point3D,PointArray<Point3D>> getVisualisation() {
        return (visualisation != null ? visualisation : null);
    }

    /**
     * getter
     *
     * @return GLU
     */
    public GLU getGLU() {
        return glu;
    }

    /**
     * getter
     *
     * @return GL2
     */
    public GL2 getGl2() {
        return gl2;
    }
    
    /**
     * getter
     *
     * @return GLUT
     */
    public GLUT getGlut() {
        return glut;
    }

    /**
     * getter
     *
     * @return Frame
     */
    public JInternalFrame getFrame() {
        return frame;
    }

    /**
     * getter
     *
     * @return MatrixModeLView
     */
    public DoubleBuffer getMatModelView() {
        return matModelView;
    }

    /**
     * getter
     *
     * @return MatrixProjection
     */
    public DoubleBuffer getMatProjection() {
        return matProjection;
    }

    /**
     * getter
     *
     * @return viewport
     */
    public IntBuffer getViewport() {
        return viewport;
    }

    /**
     * getter
     *
     * @return Current point size
     */
    public int getPointSize() {
        return pointSize;
    }

    /**
     * getter
     *
     * @return Current point size
     */
    public int getLineWidth() {
        return lineWidth;
    }

    /**
     * getter
     *
     * @return stereoscopic 3D flag
     */
    public boolean isStereo3D() {
        return isStereo3D;
    }

    /**
     * setter
     *
     * @param pointSize Desired point size
     */
    public void setPointSize(int pointSize) {
        this.pointSize = pointSize;
    }

    /**
     * setter
     *
     * @param isStereo3D flag to enable/disable stereoscopic mode
     */
    public void setStereo3D(boolean isStereo3D) {
        this.isStereo3D = isStereo3D;
        System.out.println("Enable stereoscopic 3D");
    }

    /**
     * Increases the point size in one unit
     */
    public void increasePointSize() {
        pointSize++;
    }

    /**
     * Decreases the point size in one unit
     */
    public void decreasePointSize() {
        if (pointSize > 1) {
            pointSize--;
        }
    }

    /**
     * Increases the line width in one unit
     */
    public void increaseLineWidth() {
        lineWidth++;
    }

    /**
     * Decreases the line width in one unit
     */
    public void decreaseLineWidth() {
        if (lineWidth > 1) {
            lineWidth--;
        }
    }

    /**
     * Increases the eye distance offset in one unit
     */
    public void increaseEyeDist() {
        eyeDistOffset++;
    }

    /**
     * Decreases the eye distance offset in one unit
     */
    public void decreaseEyeDist() {
        eyeDistOffset--;
    }

    /**
     * getter
     *
     * @return The camera
     */
    public Camera getCamera() {
        return camera;
    }

    /**
     * getter
     *
     * @return The capture
     */
    public Capture getCapture() {
        return capture;
    }

    /**
     * getter
     *
     * @return The main frame
     */
    public MainFrame getMainFrame() {
        return mainFrame;
    }

    /**
     * getter
     *
     * @return The main frame
     */
    public OpenGLMouseListener getMouseListener() {
        return mouseListener;
    }

    public void destroy() {
        frame.dispose();
        if (visualisation != null) {
            visualisation.getPoints().freeVBO(this);
            visualisation.getPoints().clear();
        }
    }
}
