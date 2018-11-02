package Olivia.core.render.hi;

import Olivia.core.VisualisationManager;
import Olivia.core.data.Point3D_id;
import java.nio.DoubleBuffer;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.gl2.GLUT;

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
    //private final OpenGLScreen screen;
    private final VisualisationManager visualisationManager;

    private boolean isPointSelected;
    private int selectedIndex;
    private Point3D_id selectedPoint;
    private final PointSelectorOverlay selectionOverlay;

    /**
     * It is neccessary to create an instance to store the selected point
     *
     * @param screen The OpenGL render screen where the selction is being made
     */
    public OpenGLMouseSelection(VisualisationManager visualisationManager) {
        this.position_near = DoubleBuffer.allocate(3);
        this.position_far = DoubleBuffer.allocate(3);
        this.selectedIndex = -1;
        this.visualisationManager = visualisationManager;
        this.selectedPoint = new Point3D_id(0.0, 0.0, 0.0);
        this.isPointSelected = false;
        this.selectionOverlay = new PointSelectorOverlay(visualisationManager);
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
    public Point3D_id getSelectedPoint() {
        return isPointSelected ? selectedPoint : null;
    }
    
    /**
     * Sets the selected point to a point close to the coordinates given, the first point found is chosen
     * @param point The point with the coordinates where to search
     * @return true if a point was found close enough, false if not
     */
    public boolean setSelectedPoint(Point3D_id point){
        if(point==null) return false;
        int i = 0;
        Point3D_id my_point;
        for (i=0;i<visualisationManager.getPointCloud().size();i++) {
            my_point = (Point3D_id) (visualisationManager.getPointCloud().get(i));
            if (my_point.areCloseBy(point)) {
                selectedIndex = i;
                selectedPoint = my_point;
                isPointSelected = true;
                return true;
            }
        }
        isPointSelected = false;
        return false;
    }

    public void resetSelectedPoint() {
        this.isPointSelected = false;
        this.selectedIndex = -1;
        this.selectedPoint = new Point3D_id(0, 0, 0);
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
     * @param radius How close must the point be to the selecting ray to be
     * picked
     * @param epsilon The error margin od the distance to the point
     */
    public void pick(int mouseX, int mouseY, double radius, double epsilon) {
        createRay(mouseX, mouseY);
        collisionWithRay(radius, epsilon);
    }
    
    public void pick(int mouseX, int mouseY) {
        createRay(mouseX, mouseY);
        collisionWithRay();
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

        double win_x = (double) mouseX;
        double win_y = visualisationManager.getRenderScreen().getViewport().get(3) - (double) mouseY;

        //visualisationManager.getRenderScreen().getGLU().gluUnProject(win_x, win_y, 0.0, visualisationManager.getRenderScreen().getMatModelView().array(), 0, visualisationManager.getRenderScreen().getMatProjection().array(), 0, visualisationManager.getRenderScreen().getViewport().array(), 0, position_near.array(), 0);
        //visualisationManager.getRenderScreen().getGLU().gluUnProject(win_x, win_y, 1.0, visualisationManager.getRenderScreen().getMatModelView().array(), 0, visualisationManager.getRenderScreen().getMatProjection().array(), 0, visualisationManager.getRenderScreen().getViewport().array(), 0, position_far.array(), 0);
        
        myUnProject(win_x, win_y, 0.0, visualisationManager.getRenderScreen().getMatModelView().array(), visualisationManager.getRenderScreen().getMatProjection().array(), visualisationManager.getRenderScreen().getViewport().array(), position_near.array());
        myUnProject(win_x, win_y, 1.0, visualisationManager.getRenderScreen().getMatModelView().array(), visualisationManager.getRenderScreen().getMatProjection().array(), visualisationManager.getRenderScreen().getViewport().array(), position_far.array());
    }

    /**
     * Calculates the ditance form the point to the ray
     *
     * @param point A point in the same coordinate space
     * @return The ditance betwwen the point and the ray
     */
    public double distancePointToRay(Point3D_id point) {
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
    public Boolean RayTest(Point3D_id point, double radius, double epsilon) {
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
        //return collisionWithRayFirst(radius,epsilon);
        return collisionWithRayCloser();
    }
    
    public int collisionWithRay() {
        return collisionWithRayCloser();
    }
    
    public int collisionWithRayFirst(double radius, double epsilon) {
        int i = 0;
        Point3D_id point;
        for (i=0;i<visualisationManager.getPointCloud().size();i++) {
            point = (Point3D_id) (visualisationManager.getPointCloud().get(i));
            if (RayTest(point, radius, epsilon)) {
                selectedIndex = i;
                selectedPoint = point;
                isPointSelected = true;
                return i;
            }
        }
        isPointSelected = false;
        return -1;
    }
    
    public int collisionWithRayCloser() {
        int i = 0;
        double distance,min_distance=Double.MAX_VALUE;
        Point3D_id point;
        for (i=0;i<visualisationManager.getPointCloud().size();i++) {
            point = (Point3D_id) (visualisationManager.getPointCloud().get(i));
            distance = distancePointToRay(point);
            if (distance<min_distance) {
                min_distance = distance;
                selectedIndex = i;
                selectedPoint = point;
                isPointSelected = true;
            }
        }
        return selectedIndex;
    }

    /**
     * Draws the selecting ray in openGL
     */
    public void drawRay() {
        double x = position_near.get(0);
        double y = position_near.get(1);
        double z = position_near.get(2);
        double x2 = position_far.get(0);
        double y2 = position_far.get(1);
        double z2 = position_far.get(2);

        visualisationManager.getRenderScreen().getGl2().glColor3f(0.0f, 1.0f, 0.0f);
        visualisationManager.getRenderScreen().getGl2().glBegin(GL2.GL_LINES);
        visualisationManager.getRenderScreen().getGl2().glVertex3d(x, y, z);
        visualisationManager.getRenderScreen().getGl2().glColor3f(1.0f, 0.0f, 0.0f);
        visualisationManager.getRenderScreen().getGl2().glVertex3d(x2, y2, z2);
        visualisationManager.getRenderScreen().getGl2().glEnd();
    }

    /**
     * Draws a highlight over the selected point in openGL
     */
    public void drawSelected() {
        if (isPointSelected) {
            visualisationManager.getRenderScreen().getGl2().glColor3f(1.0f, 0.0f, 0.0f);

            visualisationManager.getRenderScreen().getGl2().glBegin(GL2.GL_LINES);
            visualisationManager.getRenderScreen().getGl2().glVertex3d(((Point3D_id) (visualisationManager.getPointCloud().get(selectedIndex))).getX(),
                     ((Point3D_id) (visualisationManager.getPointCloud().get(selectedIndex))).getY(),
                     ((Point3D_id) (visualisationManager.getPointCloud().get(selectedIndex))).getZ()
            );
            visualisationManager.getRenderScreen().getGl2().glVertex3d(((Point3D_id) (visualisationManager.getPointCloud().get(selectedIndex))).getX(),
                     ((Point3D_id) (visualisationManager.getPointCloud().get(selectedIndex))).getY(),
                     ((Point3D_id) (visualisationManager.getPointCloud().get(selectedIndex))).getZ() + 5
            );
            visualisationManager.getRenderScreen().getGl2().glEnd();
        }
    }
    
    /**
     * Draws a highlight over the selected point in openGL
     */
    public void drawSelected2() {
        if (isPointSelected) {
            visualisationManager.getRenderScreen().getGl2().glPushMatrix();
            visualisationManager.getRenderScreen().getGl2().glColor3f(0.0f, 0.0f, 1.0f);
            visualisationManager.getRenderScreen().getGl2().glTranslated(((Point3D_id) (visualisationManager.getPointCloud().get(selectedIndex))).getX(),
                    ((Point3D_id) (visualisationManager.getPointCloud().get(selectedIndex))).getY(),
                    ((Point3D_id) (visualisationManager.getPointCloud().get(selectedIndex))).getZ());
            //visualisationManager.getRenderScreen().getGlut().glutWireTorus(0.20, 0.22, 3, 10);
            //visualisationManager.getRenderScreen().getGlut().glutWireSphere(0.3, 5, 5);
            visualisationManager.getRenderScreen().getGl2().glRasterPos3f(0.1f, 0.0f, 0.0f);
            visualisationManager.getRenderScreen().getGlut().glutBitmapCharacter(GLUT.BITMAP_HELVETICA_18,(char)171);
            visualisationManager.getRenderScreen().getGlut().glutBitmapString(GLUT.BITMAP_HELVETICA_18,"--");
            visualisationManager.getRenderScreen().getGl2().glPopMatrix();
        }
    }
    
    public void drawSelected3() {
        this.selectionOverlay.moveTo(selectedPoint);
        this.selectionOverlay.draw();
    }
    

    
  protected int myUnProject(double winx, double winy, double winz, double[] modelview, double[] projection, int[] viewport, double[] objectCoordinate)
  {
      // Transformation matrices
      double[] m = new double[16], A= new double[16];
      double[] in = new double[4], out = new double[4];
      // Calculation for inverting a matrix, compute projection x modelview
      // and store in A[16]
      MultiplyMatrices4by4OpenGL(A, projection, modelview);
      // Now compute the inverse of matrix A
      if(glhInvertMatrixf2(A, m)==0)
         return 0;
      // Transformation of normalized coordinates between -1 and 1
      in[0]=(winx-(double)viewport[0])/(double)viewport[2]*2.0-1.0;
      in[1]=(winy-(double)viewport[1])/(double)viewport[3]*2.0-1.0;
      in[2]=2.0*winz-1.0;
      in[3]=1.0;
      // Objects coordinates
      MultiplyMatrixByVector4by4OpenGL(out, m, in);
      if(out[3]==0.0)
         return 0;
      out[3]=1.0/out[3];
      objectCoordinate[0]=out[0]*out[3];
      objectCoordinate[1]=out[1]*out[3];
      objectCoordinate[2]=out[2]*out[3];
      return 1;
  }

  protected void MultiplyMatrices4by4OpenGL(double[] result, double[] matrix1, double[] matrix2)
  {
    result[0]=matrix1[0]*matrix2[0]+
      matrix1[4]*matrix2[1]+
      matrix1[8]*matrix2[2]+
      matrix1[12]*matrix2[3];
    result[4]=matrix1[0]*matrix2[4]+
      matrix1[4]*matrix2[5]+
      matrix1[8]*matrix2[6]+
      matrix1[12]*matrix2[7];
    result[8]=matrix1[0]*matrix2[8]+
      matrix1[4]*matrix2[9]+
      matrix1[8]*matrix2[10]+
      matrix1[12]*matrix2[11];
    result[12]=matrix1[0]*matrix2[12]+
      matrix1[4]*matrix2[13]+
      matrix1[8]*matrix2[14]+
      matrix1[12]*matrix2[15];
    result[1]=matrix1[1]*matrix2[0]+
      matrix1[5]*matrix2[1]+
      matrix1[9]*matrix2[2]+
      matrix1[13]*matrix2[3];
    result[5]=matrix1[1]*matrix2[4]+
      matrix1[5]*matrix2[5]+
      matrix1[9]*matrix2[6]+
      matrix1[13]*matrix2[7];
    result[9]=matrix1[1]*matrix2[8]+
      matrix1[5]*matrix2[9]+
      matrix1[9]*matrix2[10]+
      matrix1[13]*matrix2[11];
    result[13]=matrix1[1]*matrix2[12]+
      matrix1[5]*matrix2[13]+
      matrix1[9]*matrix2[14]+
      matrix1[13]*matrix2[15];
    result[2]=matrix1[2]*matrix2[0]+
      matrix1[6]*matrix2[1]+
      matrix1[10]*matrix2[2]+
      matrix1[14]*matrix2[3];
    result[6]=matrix1[2]*matrix2[4]+
      matrix1[6]*matrix2[5]+
      matrix1[10]*matrix2[6]+
      matrix1[14]*matrix2[7];
    result[10]=matrix1[2]*matrix2[8]+
      matrix1[6]*matrix2[9]+
      matrix1[10]*matrix2[10]+
      matrix1[14]*matrix2[11];
    result[14]=matrix1[2]*matrix2[12]+
      matrix1[6]*matrix2[13]+
      matrix1[10]*matrix2[14]+
      matrix1[14]*matrix2[15];
    result[3]=matrix1[3]*matrix2[0]+
      matrix1[7]*matrix2[1]+
      matrix1[11]*matrix2[2]+
      matrix1[15]*matrix2[3];
    result[7]=matrix1[3]*matrix2[4]+
      matrix1[7]*matrix2[5]+
      matrix1[11]*matrix2[6]+
      matrix1[15]*matrix2[7];
    result[11]=matrix1[3]*matrix2[8]+
      matrix1[7]*matrix2[9]+
      matrix1[11]*matrix2[10]+
      matrix1[15]*matrix2[11];
    result[15]=matrix1[3]*matrix2[12]+
      matrix1[7]*matrix2[13]+
      matrix1[11]*matrix2[14]+
      matrix1[15]*matrix2[15];
  }

  protected void MultiplyMatrixByVector4by4OpenGL(double[] resultvector, double[] matrix, double[] pvector){
    resultvector[0]=matrix[0]*pvector[0]+matrix[4]*pvector[1]+matrix[8]*pvector[2]+matrix[12]*pvector[3];
    resultvector[1]=matrix[1]*pvector[0]+matrix[5]*pvector[1]+matrix[9]*pvector[2]+matrix[13]*pvector[3];
    resultvector[2]=matrix[2]*pvector[0]+matrix[6]*pvector[1]+matrix[10]*pvector[2]+matrix[14]*pvector[3];
    resultvector[3]=matrix[3]*pvector[0]+matrix[7]*pvector[1]+matrix[11]*pvector[2]+matrix[15]*pvector[3];
  }

  protected void  SWAP_ROWS(double[] a, double b[]){
      double[] tmp = new double[4];
      tmp = a;
      a=b;
      b=tmp; 
  }
  
  protected double MAT(double[] m, int r, int c){
     return m[c*4+r];
  }
  
  protected void MAT_SET(double[] m, int r, int c, double value){
     m[c*4+r] = value;
  }

  // This code comes directly from GLU except that it is for float
  int glhInvertMatrixf2(double[] m, double[] out)
  {
   double[][] wtmp = new double[4][8];
   double m0, m1, m2, m3, s;
   double[] r0 = new double[8], r1= new double[8], r2= new double[8], r3= new double[8];
   r0 = wtmp[0];
   r1 = wtmp[1];
   r2 = wtmp[2];
   r3 = wtmp[3];
   r0[0] = MAT(m, 0, 0);
   r0[1] = MAT(m, 0, 1);
   r0[2] = MAT(m, 0, 2);
   r0[3] = MAT(m, 0, 3);
   r0[4] = 1.0;
   r0[5] = r0[6] = r0[7] = 0.0;
   r1[0] = MAT(m, 1, 0);
   r1[1] = MAT(m, 1, 1);
   r1[2] = MAT(m, 1, 2);
   r1[3] = MAT(m, 1, 3);
   r1[5] = 1.0;
   r1[4] = r1[6] = r1[7] = 0.0;
   r2[0] = MAT(m, 2, 0);
   r2[1] = MAT(m, 2, 1);
   r2[2] = MAT(m, 2, 2);
   r2[3] = MAT(m, 2, 3);
   r2[6] = 1.0;
   r2[4] = r2[5] = r2[7] = 0.0;
   r3[0] = MAT(m, 3, 0);
   r3[1] = MAT(m, 3, 1);
   r3[2] = MAT(m, 3, 2);
   r3[3] = MAT(m, 3, 3);
   r3[7] = 1.0;
   r3[4] = r3[5] = r3[6] = 0.0;
   /* choose pivot - or die */
   if (Math.abs(r3[0]) > Math.abs(r2[0]))
      SWAP_ROWS(r3, r2);
   if (Math.abs(r2[0]) > Math.abs(r1[0]))
      SWAP_ROWS(r2, r1);
   if (Math.abs(r1[0]) > Math.abs(r0[0]))
      SWAP_ROWS(r1, r0);
   if (0.0 == r0[0])
      return 0;
   /* eliminate first variable */
   m1 = r1[0] / r0[0];
   m2 = r2[0] / r0[0];
   m3 = r3[0] / r0[0];
   s = r0[1];
   r1[1] -= m1 * s;
   r2[1] -= m2 * s;
   r3[1] -= m3 * s;
   s = r0[2];
   r1[2] -= m1 * s;
   r2[2] -= m2 * s;
   r3[2] -= m3 * s;
   s = r0[3];
   r1[3] -= m1 * s;
   r2[3] -= m2 * s;
   r3[3] -= m3 * s;
   s = r0[4];
   if (s != 0.0) {
      r1[4] -= m1 * s;
      r2[4] -= m2 * s;
      r3[4] -= m3 * s;
   }
   s = r0[5];
   if (s != 0.0) {
      r1[5] -= m1 * s;
      r2[5] -= m2 * s;
      r3[5] -= m3 * s;
   }
   s = r0[6];
   if (s != 0.0) {
      r1[6] -= m1 * s;
      r2[6] -= m2 * s;
      r3[6] -= m3 * s;
   }
   s = r0[7];
   if (s != 0.0) {
      r1[7] -= m1 * s;
      r2[7] -= m2 * s;
      r3[7] -= m3 * s;
   }
   /* choose pivot - or die */
   if (Math.abs(r3[1]) > Math.abs(r2[1]))
      SWAP_ROWS(r3, r2);
   if (Math.abs(r2[1]) > Math.abs(r1[1]))
      SWAP_ROWS(r2, r1);
   if (0.0 == r1[1])
      return 0;
   /* eliminate second variable */
   m2 = r2[1] / r1[1];
   m3 = r3[1] / r1[1];
   r2[2] -= m2 * r1[2];
   r3[2] -= m3 * r1[2];
   r2[3] -= m2 * r1[3];
   r3[3] -= m3 * r1[3];
   s = r1[4];
   if (0.0 != s) {
      r2[4] -= m2 * s;
      r3[4] -= m3 * s;
   }
   s = r1[5];
   if (0.0 != s) {
      r2[5] -= m2 * s;
      r3[5] -= m3 * s;
   }
   s = r1[6];
   if (0.0 != s) {
      r2[6] -= m2 * s;
      r3[6] -= m3 * s;
   }
   s = r1[7];
   if (0.0 != s) {
      r2[7] -= m2 * s;
      r3[7] -= m3 * s;
   }
   /* choose pivot - or die */
   if (Math.abs(r3[2]) > Math.abs(r2[2]))
      SWAP_ROWS(r3, r2);
   if (0.0 == r2[2])
      return 0;
   /* eliminate third variable */
   m3 = r3[2] / r2[2];
   r3[3] -= m3 * r2[3];
   r3[4] -= m3 * r2[4];
   r3[5] -= m3 * r2[5];
   r3[6] -= m3 * r2[6];
   r3[7] -= m3 * r2[7];
   /* last check */
   if (0.0 == r3[3])
      return 0;
   s = 1.0 / r3[3];		/* now back substitute row 3 */
   r3[4] *= s;
   r3[5] *= s;
   r3[6] *= s;
   r3[7] *= s;
   m2 = r2[3];			/* now back substitute row 2 */
   s = 1.0 / r2[2];
   r2[4] = s * (r2[4] - r3[4] * m2);
   r2[5] = s * (r2[5] - r3[5] * m2);
   r2[6] = s * (r2[6] - r3[6] * m2);
   r2[7] = s * (r2[7] - r3[7] * m2);
   m1 = r1[3];
   r1[4] -= r3[4] * m1;
   r1[5] -= r3[5] * m1;
   r1[6] -= r3[6] * m1;
   r1[7] -= r3[7] * m1;
   m0 = r0[3];
   r0[4] -= r3[4] * m0;
   r0[5] -= r3[5] * m0;
   r0[6] -= r3[6] * m0;
   r0[7] -= r3[7] * m0;
   m1 = r1[2];			/* now back substitute row 1 */
   s = 1.0 / r1[1];
   r1[4] = s * (r1[4] - r2[4] * m1);
   r1[5] = s * (r1[5] - r2[5] * m1);
   r1[6] = s * (r1[6] - r2[6] * m1);
   r1[7] = s * (r1[7] - r2[7] * m1);
   m0 = r0[2];
   r0[4] -= r2[4] * m0;
   r0[5] -= r2[5] * m0;
   r0[6] -= r2[6] * m0;
   r0[7] -= r2[7] * m0;
   m0 = r0[1];			/* now back substitute row 0 */
   s = 1.0 / r0[0];
   r0[4] = s * (r0[4] - r1[4] * m0);
   r0[5] = s * (r0[5] - r1[5] * m0);
   r0[6] = s * (r0[6] - r1[6] * m0);
   r0[7] = s * (r0[7] - r1[7] * m0);
   MAT_SET(out, 0, 0, r0[4]);
   MAT_SET(out, 0, 1,r0[5]);
   MAT_SET(out, 0, 2,r0[6]);
   MAT_SET(out, 0, 3,r0[7]);
   MAT_SET(out, 1, 0,r1[4]);
   MAT_SET(out, 1, 1,r1[5]);
   MAT_SET(out, 1, 2,r1[6]);
   MAT_SET(out, 1, 3,r1[7]);
   MAT_SET(out, 2, 0,r2[4]);
   MAT_SET(out, 2, 1,r2[5]);
   MAT_SET(out, 2, 2,r2[6]);
   MAT_SET(out, 2, 3,r2[7]);
   MAT_SET(out, 3, 0,r3[4]);
   MAT_SET(out, 3, 1,r3[5]);
   MAT_SET(out, 3, 2,r3[6]);
   MAT_SET(out, 3, 3,r3[7]);
   return 1;
  }
}
