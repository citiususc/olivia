package Olivia.core.render;

import Olivia.core.data.Point3D_id;
import Olivia.core.render.hi.NEWTKeyListener;
import Olivia.core.render.hi.NEWTMouseListener;
import Olivia.core.render.hi.NEWTGestureListener;
import Olivia.core.render.hi.NEWTZoomGestureHandler;
import Olivia.core.render.hi.NEWTScrollGestureHandler;
import Olivia.core.VisualisationManager;
import Olivia.core.data.Point3D;
import Olivia.core.render.hi.PositionShowOverlay;
import com.jogamp.newt.event.InputEvent;
import com.jogamp.opengl.util.Animator;
import com.jogamp.opengl.util.gl2.GLUT;
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
import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.fixedfunc.GLLightingFunc;
import com.jogamp.opengl.fixedfunc.GLMatrixFunc;
import com.jogamp.opengl.glu.GLU;

/**
 * This is the render screen, it takes care of most of the openGL operations It
 changes among different Visualisations and calls their draw() to render them
 It uses each visualisation Camera to execute translations an rotations It
 allows other classes to listen for a "pointSelected" event and get the point
 slectede though the NEWTMouseListener
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
    //protected int id;
    /**
     * The custom visualisation
     */
    protected VisualisationManager visualisationManager;
    /**
     * Its own Camera to manage rotation and translation
     */
    protected Camera camera;
    /**
     * Its own Capture to save the render screen into image/video
     */
    protected Capture capture;
    /**
     * The key listener when using NEWT
     */
    protected NEWTKeyListener keyListenerNEWT;
    /**
     * The mouse listener when using NEWT, contains the point selection
     */
    protected NEWTMouseListener mouseListenerNEWT;
    
    protected NEWTGestureListener gestureListenerNEWT;
    protected NEWTZoomGestureHandler gestureHandlerNEWT;
    protected NEWTScrollGestureHandler scrollHandlerNEWT;
    /**
     * An overlay to show a posiiton on hte screen, maily used for mirroring
     */
    protected PositionShowOverlay positionOverlay;
    /**
     * If true positionOverlay will be drawn
     */
    protected boolean showPosition;
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
    //protected float eyeDist;
    /**
     * The current eye distance offset
     */
    //protected int eyeDistOffset;

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
    //protected boolean isStereo3D;
    /**
     *
     */
    //protected MainFrame mainFrame;
    
    //protected double aspectRatio;
    
    /**
     * We store the Window to access frame size and position (capture methods)
     */
    protected GLWindow window;
    
    
    /**
     * Create a custom OpenGL capabilities mainly to enable stereoscopic 3D
     */
    private GLCapabilities createCapabilites() {
        GLProfile glp = GLProfile.get(GLProfile.GL2);
        GLCapabilities caps = new GLCapabilities(glp);
        caps.setStereo(visualisationManager.isStereo3D());
        //caps.setDepthBits(32); // This was a solution for someone with depth problems, does not seem to do anything
        return caps;
    }
    
    /**
     * Creates an AWT frame to render the visualisation
     */
    /*
    public void createRenderFrame() {
        if(usingNEWT){
            createRenderFrameNEWT();
        }else{
            createRenderFrameAWT();
        }
    }
    */

    /**
     * Creates an AWT frame to render the visualisation
     */
    public void createRenderFrameNEWT() {
        //final GLCanvas canvas = new GLCanvas(createCapabilites());
        //GLJPanel canvas = new GLJPanel(createCapabilites());
        window = GLWindow.create(createCapabilites());
        window.addGLEventListener(this);
        scrollHandlerNEWT = new NEWTScrollGestureHandler(this,1,500);
        window.addGestureHandler(scrollHandlerNEWT);
        window.addKeyListener(keyListenerNEWT);
        window.addMouseListener(mouseListenerNEWT);
        window.addGestureListener(gestureListenerNEWT);
        gestureHandlerNEWT = new NEWTZoomGestureHandler(this,window,false);
        window.addGestureHandler(gestureHandlerNEWT);
        //canvas = new NewtCanvasAWT(window);
        animator = new Animator(window);
        //frame = new JInternalFrame(visualisationManager.getName(), true, true, true, true);
        /*animator = new Animator(canvas);
        canvas.addGLEventListener(this);*/
        /*frame.add(canvas);
        frame.setResizable(true);
        frame.addInternalFrameListener(new FrameEventListener(visualisationManager));
        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                if (visualisationManager.getGUI().isActiveVisualisationManager(visualisationManager)) {
                    if (((JInternalFrame) e.getComponent()).isMaximum()) {
                        System.out.println(visualisationManager.getId() + " Maximized");
                        visualisationManager.getGUI().setInactiveScreensIconify(true); // Iconify hidden render screens to stop them for being rendered                     
                    } else {
                        System.out.println(visualisationManager.getId() + " Un-maximized");
                        visualisationManager.getGUI().setInactiveScreensIconify(false); // Undo the previous iconify
                        visualisationManager.getGUI().updateRenderFrameLayout();
                    }
                }
            }
        });
        visualisationManager.getGUI().getRenderPane().add(frame);
        frame.setVisible(true);
        canvas.setVisible(true);
        */
        animator.start();
        //canvas.requestFocusInWindow();
    }


    /**
     * Initialises many things
     *
     */
    public OpenGLScreen(VisualisationManager visualisationManager) {
        this.visualisationManager = visualisationManager;
        viewport = IntBuffer.allocate(4);
        matProjection = DoubleBuffer.allocate(16);
        matModelView = DoubleBuffer.allocate(16);
        pointSize = 1;
        lineWidth = 1;
        //eyeDist = 1;
        //eyeDistOffset = 0;
        glut = new GLUT();
        glu = new GLU();
        keyListenerNEWT = new NEWTKeyListener(this);
        mouseListenerNEWT = new NEWTMouseListener(visualisationManager);
        gestureListenerNEWT = new NEWTGestureListener();
        listeners = new ArrayList<>();
        camera = new Camera(visualisationManager);
        capture = new Capture(visualisationManager);
        positionOverlay = new PositionShowOverlay(visualisationManager);
        showPosition = false;
        //aspectRatio = 16/9.0;
    }

    /**
     * Gets whether it is showing a higlight in a position
     * @return true if an overlay is being rendered in a position
     */
    public boolean isShowPosition() {
        return showPosition;
    }

    /**
     * Sets whether to render the position highlight
     * @param showPosition true to render the overlay
     */
    public void setShowPosition(boolean showPosition) {
        this.showPosition = showPosition;
    }
    
    

    /**
     * setter
     *
     * @param visualisation The custom visualisation
     */
    /*
    public void setVisualisation(Visualisation visualisation) {
        this.visualisation = visualisation;
        visualisation.setSelected(true);
        System.out.println("Setting centre at "
                + visualisation.getPointCloud().getCenterOfMass()[0] + " "
                + visualisation.getPointCloud().getCenterOfMass()[1] + " "
                + visualisation.getPointCloud().getCenterOfMass()[2]);
        visualisation.getRenderScreen().getCamera().setCloudCentre(visualisation.getPointCloud().getCenterOfMass());
        mouseListenerNEWT.getMouseSelection().resetSelectedPoint();
        fireEvent("visuActive");
    }
    */

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
        if (visualisationManager != null) {
            glut.glutBitmapString(GLUT.BITMAP_HELVETICA_12, "POINTS: " + visualisationManager.getPointCloud().size());
        } else {
            glut.glutBitmapString(GLUT.BITMAP_HELVETICA_12, "No points to draw");
        }
        gl2.glWindowPos2i(x, viewport.array()[3] - y * 3);
        glut.glutBitmapString(GLUT.BITMAP_HELVETICA_12, "FPS: " + String.format("%.0f", time));
        gl2.glWindowPos2i(x, viewport.array()[3] - y * 4);
        glut.glutBitmapString(GLUT.BITMAP_HELVETICA_12, "AVG: " + String.format("%.0f", gLDrawable.getAnimator().getTotalFPS()));
        if (visualisationManager.isStereo3D()) {
            gl2.glWindowPos2i(x, viewport.array()[3] - y * 5);
            //glut.glutBitmapString(GLUT.BITMAP_HELVETICA_12, "EYE: " + String.format("%.2f", eyeDist));
            glut.glutBitmapString(GLUT.BITMAP_HELVETICA_12, "EYE: " + String.format("%.2f", camera.getIntraOcularDistance()));
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
        if (visualisationManager == null) {
            return;
        }
        visualisationManager.draw();
        if(visualisationManager.isDrawMouseSelection()){
            mouseListenerNEWT.getMouseSelection().drawSelected3();
            //mouseListener.getMouseSelection().drawRay();
        }
        if(showPosition){
            positionOverlay.draw(this);
        }
    }

    /**
     * Inits many things
     *
     * @param glad this drawable
     */
    @Override
    public void init(GLAutoDrawable glad) {
        initNEWT(glad);
    }
    
    protected void initNEWT(GLAutoDrawable glad) {
        glad.getAnimator().setUpdateFPSFrames(1, null);
        gl2 = glad.getGL().getGL2();
        gl2.glShadeModel(GLLightingFunc.GL_SMOOTH);
        gl2.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        gl2.glClearDepth(1.0f);
        gl2.glEnable(GL2.GL_DEPTH_TEST);
        gl2.glDepthFunc(GL2.GL_LEQUAL);
        gl2.glHint(GL2ES1.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST);
        
        System.out.println("Drawing...");
    }

    /**
     * What to do on close
     *
     * @param glad this drawable
     */
    @Override
    public void dispose(GLAutoDrawable glad) {
        visualisationManager.freeVBOs();
        System.out.println("Exiting...");
    }

    /**
     * Draws everything
     *
     * @param glad this drawable
     */
    @Override
    public void display(GLAutoDrawable glad) {
        if (visualisationManager == null) {
            return;
        }
        Camera cam = visualisationManager.getRenderScreen().getCamera();
        Capture cap = visualisationManager.getRenderScreen().getCapture();

        gl2 = glad.getGL().getGL2();
        gl2.glEnable(GL2.GL_POINT_SMOOTH);
        //gl2.glEnable(GL2.GL_POINT_SPRITE);
        gl2.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_SRC_ALPHA);
        gl2.glPointSize(pointSize);

        if (!visualisationManager.isStereo3D()) {
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

            gl2.glDrawBuffer(GL2GL3.GL_BACK_LEFT);
            gl2.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
            gl2.glLoadIdentity();
            //gl2.glFrustum(left - fustrumShift, right - fustrumShift, bottom, top, zNear, zFar);
            gl2.glFrustum(camera.getLeftEyeLeft(), camera.getLeftEyeRight(), camera.getBottom(), camera.getTop(), camera.getzNear(), camera.getzFar());
            gl2.glTranslated(camera.getIntraOcularDistanceHalf(), 0, 0);
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
            gl2.glFrustum(camera.getRightEyeLeft(), camera.getRightEyeRight(), camera.getBottom(), camera.getTop(), camera.getzNear(), camera.getzFar());
            gl2.glTranslated(-camera.getIntraOcularDistanceHalf(), 0, 0);
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
        //float h = (float) aspectRatio;
        camera.setAspectRatio(width, height);
        gl2 = glad.getGL().getGL2();
        gl2.glMatrixMode(GLMatrixFunc.GL_PROJECTION);
        gl2.glGetDoublev(GL2.GL_PROJECTION_MATRIX, matProjection);
        gl2.glGetIntegerv(GL2.GL_VIEWPORT, viewport);
        gl2.glLoadIdentity();
        if (!visualisationManager.isStereo3D()) {
            //System.out.println("Resizing, new aspect ratio: " +  aspectRatio);
            //glu.gluPerspective(50.0f, camera.getAspectRatio(), 1.0, 5000.0);
            glu.gluPerspective(camera.getFieldOfView(), camera.getAspectRatio(), camera.getzNear(), camera.getzFar());
        }else{
            //System.out.println("Resizing with 3d, new aspect ratio: " + aspectRatio);
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
    public Point3D_id getSelectedPoint() {
            return mouseListenerNEWT.getMouseSelection().getSelectedPoint();
    }

    /**
     * getter
     *
     * @return The mouse selected point, null if none
     */
    public int getSelectedPointIndex() {
            return mouseListenerNEWT.getMouseSelection().getSelectedIndex();
    }
    
    /**
     * Set the selected point as the closest it can find, if any is close enough, to point
     * @param point The point we are trying to find
     * @return true if there is a point in the main cloud close enough to point
     */
    public boolean setSelectedPoint(Point3D_id point){
            return mouseListenerNEWT.getMouseSelection().setSelectedPoint(point);
    }
    
    /**
     * Sets the position to be higlighted
     * @param point A point to be highlighted (needs not to exist in the main cloud, just for rendering)
     */
    public void setPosition(Point3D point){
        if(point!=null) positionOverlay.moveTo(point);
    }

    /**
     * getter
     *
     * @return The visualisation, null if none
     */
    /*
    public Visualisation<Point3D,PointArray<Point3D>> getVisualisation() {
        return (visualisation != null ? visualisation : null);
    }
    **/

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
    /*
    public JInternalFrame getFrame() {
        return frame;
    }
    */

    /**
     * getter
     *
     * @return MatrixModeLView
     */
    public DoubleBuffer getMatModelView() {
        gl2.glGetDoublev(GL2.GL_MODELVIEW_MATRIX, matModelView);
        return matModelView;
    }

    /**
     * getter
     *
     * @return MatrixProjection
     */
    public DoubleBuffer getMatProjection() {
        gl2.glGetDoublev(GL2.GL_PROJECTION_MATRIX, matProjection);
        return matProjection;
    }

    /**
     * getter
     *
     * @return viewport
     */
    public IntBuffer getViewport() {
        gl2.glGetIntegerv(GL2.GL_VIEWPORT, viewport.array(), 0);
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
    /*
    public boolean isStereo3D() {
        return isStereo3D;
    }
    */

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
    /*
    public void setStereo3D(boolean isStereo3D) {
        this.isStereo3D = isStereo3D;
        System.out.println("Enable stereoscopic 3D");
    }
    */

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
        camera.increaseIntraOcularDistance();
    }

    /**
     * Decreases the eye distance offset in one unit
     */
    public void decreaseEyeDist() {
        camera.decreaseIntraOcularDistance();
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

    
    public void doMouseSelection(){
            mouseListenerNEWT.getMouseSelection().pick(mouseListenerNEWT.getWindowX(),
                        mouseListenerNEWT.getWindowY(),
                        NEWTMouseListener.MOUSE_PICK_RADIUS,
                        NEWTMouseListener.MOUSE_PICK_EPSILON);
    }

    public void destroy() {
        window.destroy();
        if (visualisationManager != null) {
            visualisationManager.destroy();
        }
    }

    public GLWindow getWindow() {
        return window;
    }
    
    public void windowInteracted(InputEvent event){
        visualisationManager.windowInteracted(event);
    }
    
    public void animatorPause(){
        animator.pause();
        while(animator.isAnimating()){
            System.out.print(".");
        };
        System.out.println("Animator Paused");
    }
    
    public void animatorResume(){
        animator.resume();
    }
    
    public void animatorStop(){
        animator.stop();
        while(animator.isAnimating()){
            System.out.print(".");
        };
        System.out.println("Animator Stopped");
    }
    
}
