package Olivia.core.render;

import Olivia.core.VisualisationManager;
import Olivia.core.data.Point3D;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;

/**
 * Used to save the camera attributes of the main application. Does not
 * execute any openGL operations until they are called
 *
 * @author oscar.garcia
 */
public class Camera extends Transformations{

    /**
     * The default name of the file to which to save the main camera of the application
     */
    public static String DEFAULT_FILE_NAME = "camera.txt";

    
    public static Double INTRA_OCULAR_DISTANCE_INC = 0.1;
 
    /**
     * Used to save the centre of the camera
     */
    protected float cloudCenterX,cloudCenterY,cloudCenterZ;
   


    /**
     * Needed for tracking selected visualisation (mirroring)
     */
    //protected OpenGLScreen renderScreen;
    
    protected VisualisationManager visualisationManager;
    /**
     * The initial zoom, or translation on Z
     */
    protected double zDepth;
    
    protected double zNear, zFar, fieldOfView, leftEyeLeft, leftEyeRight, rightEyeLeft, rightEyeRight, top, bottom, aspectRatio, intraOcularDistance, intraOcularDistanceHalf, fustrumShift;
    
    /**
     * The center of the camera
     */
    //protected float[] center;
    //protected Point3D centre;
    
        /**
     * The step of the default transformations, depends on the transformation
     */
    protected float speed;

    /**
     * Sets the defaults
     *
     * @param visualisationManager the visualisation manger 
     */
    public Camera(VisualisationManager visualisationManager) {
        super();
        this.visualisationManager = visualisationManager;
        this.zDepth = -600.0f;
        this.speed = 0.0f;
        this.fieldOfView = 50.0;
        this.zNear = 1.0;
        this.zFar = 5000.0;
        this.intraOcularDistance=0.5;
        this.intraOcularDistanceHalf=0.25;
        this.fustrumShift = 0.0;
        this.aspectRatio = 16.0/9.0; //dummy, needs to be calculated
        /*center = new float[3];
        center[0] = 0;
        center[1] = 0;
        center[2] = 0;*/
        //centre = new Point3D();
    }
    
    /**
     * Increases the rotation in X one unit plus speed
     */
    public void increaseRotX() {
        rotX += 1.0f + speed;
        visualisationManager.getGUI().cameraMoved();
    }

    /**
     * Decreases the rotation in X one unit plus speed
     */
    public void decreaseRotX() {
        rotX -= 1.0f + speed;
        visualisationManager.getGUI().cameraMoved();
    }

        /**
     * Increases the rotation in Z one unit plus speed
     */
    public void increaseRotZ() {
        rotZ += 1.0f + speed;
        visualisationManager.getGUI().cameraMoved();
    }

    /**
     * Decreases the rotation in Z one unit plus speed
     */
    public void decreaseRotZ() {
        rotZ -= 1.0f + speed;
        visualisationManager.getGUI().cameraMoved();
    }
    
        /**
     * Increases the translation in X 5 units plus speed
     */
    public void increaseTransX() {
        transX += 5.0f + speed;
        visualisationManager.getGUI().cameraMoved();
    }

    /**
     * Decreases the translation in X 5 units plus speed
     */
    public void decreaseTransX() {
        transX -= 5.0f + speed;
        visualisationManager.getGUI().cameraMoved();
    }
    
        /**
     * Increases the translation in Y 5 units plus speed
     */
    public void increaseTransY() {
        transY += 3.0f + speed;
        visualisationManager.getGUI().cameraMoved();
    }

    /**
     * Decreases the translation in Y 5 units plus speed
     */
    public void decreaseTransY() {
        transY -= 3.0f + speed;
        visualisationManager.getGUI().cameraMoved();
    }
    
       /**
     * Increases the translation in Z 5 units plus speed
     */
    public void increaseTransZ() {
        transZ += 5.0f + speed;
        visualisationManager.getGUI().cameraMoved();
    }

    /**
     * Decreases the translation in Z 5 units plus speed
     */
    public void decreaseTransZ() {
        transZ -= 5.0f + speed;
        visualisationManager.getGUI().cameraMoved();
    }
    
        /**
     * Adds amount to the rotation in X
     *
     * @param amount The ammount to be added to the rotation in X
     */
    @Override
    public void addToRotX(float amount) {
        super.addToRotX(amount);
        visualisationManager.getGUI().cameraMoved();
    }
    
    @Override
    public void addToRotZ(float amount) {
        super.addToRotZ(amount);
        visualisationManager.getGUI().cameraMoved();
    }
    
    @Override
    public void addToTransX(float amount) {
        super.addToTransX(amount);
        visualisationManager.getGUI().cameraMoved();
    }
    
        @Override
    public void addToTransY(float amount) {
        super.addToTransY(amount);
        visualisationManager.getGUI().cameraMoved();
    }
    
        @Override
    public void addToTransZ(float amount) {
        super.addToTransZ(amount);
        visualisationManager.getGUI().cameraMoved();
    }
    
        /**
     * getter
     *
     * @return The speed
     */
    public float getSpeed() {
        return speed;
    }

    /**
     * setter
     *
     * @param speed the speed
     */
    public void setSpeed(float speed) {
        this.speed = speed;
    }

    /**
     * Adds amount to the speed
     *
     * @param amount The ammount to be added to the speed
     */
    public void addToSpeed(float amount) {
        speed += amount;
    }

    /**
     * Increases the speed by a certain amount (1.0f)
     */
    public void increaseSpeed() {
        speed += 1.0f;
    }

    /**
     * Decreases the speed by a certain amount (1.0f) 
     */
    public void decreaseSpeed() {
        speed -= 1.0f;
    }
    
    /**
     * To use when zNear, zFar, zDepth, fieldOfView intraOcularDistance or aspectRatio have changed
     */
    protected void recalculateValues(){
        this.intraOcularDistanceHalf = this.intraOcularDistance*0.5;
        this.top = Math.tan(fieldOfView * Math.PI / 360.0) * zNear;
        this.bottom = -top;
        this.fustrumShift = intraOcularDistanceHalf*zNear/(zDepth);
        this.leftEyeLeft = aspectRatio*bottom - fustrumShift;
        this.leftEyeRight = aspectRatio*top - fustrumShift;
        this.rightEyeLeft = aspectRatio*bottom + fustrumShift;
        this.rightEyeRight = aspectRatio*top + fustrumShift;
    }

    /**
     * Gets the depth
     * @return the depth of the visualisation
     */
    public double getzDepth() {
        return zDepth;
    }

    /**
     * Sets the depth of the visualisation
     * @param zDepth Sets the depth of the visualisation
     */
    public void setzDepth(double zDepth) {
        this.zDepth = zDepth;
        this.recalculateValues();
    }

    public double getzNear() {
        return zNear;
    }

    public void setzNear(double zNear) {
        this.zNear = zNear;
        this.recalculateValues();
    }

    public double getzFar() {
        return zFar;
    }

    public void setzFar(double zFar) {
        this.zFar = zFar;
        this.recalculateValues();
    }

    public double getFieldOfView() {
        return fieldOfView;
    }

    public void setFieldOfView(double fieldOfView) {
        this.fieldOfView = fieldOfView;
        this.recalculateValues();
    }

    public double getIntraOcularDistance() {
        return intraOcularDistance;
    }

    public void setIntraOcularDistance(double intraOcularDistance) {
        this.intraOcularDistance = intraOcularDistance;
        this.recalculateValues();
    }

    public double getIntraOcularDistanceHalf() {
        return intraOcularDistanceHalf;
    }
    
    public void increaseIntraOcularDistance(){
        this.intraOcularDistance = this.intraOcularDistance + INTRA_OCULAR_DISTANCE_INC;
        this.recalculateValues();
    }
    
    public void decreaseIntraOcularDistance(){
        this.intraOcularDistance = this.intraOcularDistance - INTRA_OCULAR_DISTANCE_INC;
        this.recalculateValues();
    }

    public double getLeftEyeLeft() {
        return leftEyeLeft;
    }

    public double getLeftEyeRight() {
        return leftEyeRight;
    }

    public double getRightEyeLeft() {
        return rightEyeLeft;
    }

    public double getRightEyeRight() {
        return rightEyeRight;
    }

    public double getTop() {
        return top;
    }

    public double getBottom() {
        return bottom;
    }

    public double getAspectRatio() {
        return aspectRatio;
    }
    
    public void setAspectRatio(int width, int height){
        reshape(width,height);
        this.recalculateValues();
    }
    
    public void reshape(int width, int height){
        aspectRatio = (float) width / (float) height;
        this.recalculateValues();
    }

    
    
    
    /**
     * Centers the camera at a given point, does not call the openGL
     * tranformations
     *
     * @param p The point ot center the camera
     */
    public void centerAt(Point3D p) {
        if (p == null) {
            centre = new Point3D(0.0, 0.0, 0.0);
        }else{
            centre.copyCoords(p);
            visualisationManager.getGUI().cameraMoved();
        }
        /*
        float zoomOut = 400f;  
        transX = (float) -p.getX() + (float) cloudCenterX;
        transY = (float) -p.getY() + (float) cloudCenterY;
        transZ = (float) (-p.getZ() - zoomOut + (float) cloudCenterZ);
        rotX = 0;
        rotZ = 0;
        visualisationManager.getGUI().cameraMoved();
        */
    }


    /**
     * Executes a translation on the render screen with the stored parameters
     *
     * @param renderScreen The render screen
     */
    @Override
    public void doTranslatef(OpenGLScreen renderScreen) {
        renderScreen.getGl2().glTranslatef(transX, transY, transZ);
        //super.doTranslatef(renderScreen);
        visualisationManager.getGUI().cameraMoved();
    }

    /**
     * Executes a rotation on the render screen with the stored parameters
     *
     * @param renderScreen The render screen
     */
    @Override
    public void doRotatef(OpenGLScreen renderScreen) {
        renderScreen.getGl2().glRotatef(rotX, 1.0f, 0.0f, 0.0f);
        renderScreen.getGl2().glRotatef(rotY, 0.0f, 1.0f, 0.0f);
        renderScreen.getGl2().glRotatef(rotZ, 0.0f, 0.0f, 1.0f);
        //super.doRotatef(renderScreen);
        visualisationManager.getGUI().cameraMoved();
    }

    /**
     * Executes the translation and rotation on the render screen with the
     * stored parameters
     *
     * @param renderScreen The render screen
     */
    @Override
    public void doTranslateAndRotate(OpenGLScreen renderScreen) {
        //renderScreen.getGl2().glTranslated(-centre.getX(), -centre.getY(), -centre.getZ());
        
        renderScreen.getGl2().glTranslatef(transX, transY, 0.0f);
        renderScreen.getGl2().glTranslatef(0.0f, 0.0f, transZ);
        renderScreen.getGl2().glTranslated(0.0f, 0.0f, zDepth);

        renderScreen.getGl2().glRotatef(rotX, 1.0f, 0.0f, 0.0f);
        renderScreen.getGl2().glRotatef(rotY, 0.0f, 1.0f, 0.0f);
        renderScreen.getGl2().glRotatef(rotZ, 0.0f, 0.0f, 1.0f);

        //renderScreen.getGl2().glTranslated(-cloudCenterX, -cloudCenterY, -cloudCenterZ);
        renderScreen.getGl2().glTranslated(-centre.getX(), -centre.getY(), -centre.getZ());
        
        visualisationManager.getGUI().cameraMoved();
    }

    /**
     * Writes the camera values to a file, format "cloudCenterX + "\t" + cloudCenterY + "\t" + cloudCenterZ + "\t" 
       + transX + "\t" + transY + "\t" + transZ + "\t" + rotX + "\t" + rotY + "\t" + rotZ + "\t" + speed"
     * @param path The path to the file
     * @param fileName the fname of the file
     */
    @Override
    public void writeToFile(String path, String fileName) {
        Charset charset = Charset.forName("US-ASCII");
        File file = new File(path + "/" + fileName);
        try (BufferedWriter writer = Files.newBufferedWriter(file.toPath(), charset)) {
            writer.write(FILE_HEADER, 0, FILE_HEADER.length());
            writer.newLine();
            writer.write(FILE_VERSION, 0, FILE_VERSION.length());
            writer.newLine();
            String data = cloudCenterX + "\t" + cloudCenterY + "\t" + cloudCenterZ + "\t" 
                    + transX + "\t" + transY + "\t" + transZ + "\t" + rotX + "\t" + rotY + "\t" + rotZ + "\t" + speed;
            writer.write(data, 0, data.length());
            writer.close();
            System.out.println("Camera data written to file " + file);
        }
        catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }
    }

    /**
     * Writes the camera values to a file named DEFAULT_FILE_NAME, format "cloudCenterX + "\t" + cloudCenterY + "\t" + cloudCenterZ + "\t" 
       + transX + "\t" + transY + "\t" + transZ + "\t" + rotX + "\t" + rotY + "\t" + rotZ + "\t" + speed"
     * @param path The path to the file
     */
    public void writeToFile(String path) {
        writeToFile(path, DEFAULT_FILE_NAME);
    }

    /**
     * Reads the camera values from a file 
     * @param path The path to the file
     * @param fileName the fname of the file
     * @return true if the camera values could be read
     */
    @Override
    public int readFromFile(String path, String fileName) {
        Charset charset = Charset.forName("US-ASCII");
        File file = new File(path + "/" + fileName);
        try (BufferedReader reader = Files.newBufferedReader(file.toPath(), charset)) {
            String line = reader.readLine();
            if (line == null) {
                System.out.println(file + " is empty");
                return -1;
            }
            if (!line.equals(FILE_HEADER)) {
                System.out.println(file + " is not a correct camera file");
                return -1;
            }
            line = reader.readLine();
            if (line == null) {
                System.out.println(file + " has no data");
                return -1;
            }
            if (!line.equals(FILE_VERSION)) {
                System.out.println(file + " is version " + line + " and the current camera file version is " + FILE_VERSION);
                return -1;
            }
            line = reader.readLine();
            if (line == null) {
                System.out.println(file + " has no data");
                return -1;
            }

            String[] data = line.split("\t");
            if (data.length != 10) {
                System.out.println(file + " data does not coincide with version");
                return -1;
            }

            this.transX = Float.parseFloat(data[3]);
            this.transY = Float.parseFloat(data[4]);
            this.transZ = Float.parseFloat(data[5]);
            this.rotX = Float.parseFloat(data[6]);
            this.rotY = Float.parseFloat(data[7]);
            this.rotZ = Float.parseFloat(data[8]);
            this.speed = Float.parseFloat(data[9]);

            System.out.println("Camera data read from file " + file);
            return 0;
        }
        catch (IOException x) {
            System.err.format("IOException: %s%n", x);
            return -1;
        }
    }

        /**
     * Reads the camera values from a file named DEFAULT_FILE_NAME
     * @param path The path to the file
     * @return true if the camera values could be read
     */
    public int readFromFile(String path) {
        return readFromFile(path, DEFAULT_FILE_NAME);
    }

    /**
     * Copies the camera of the active render screen
     * @see copyActiveCameraNoDisp()
     * @see copyActiveCameraDisp()
     * @see copyActiveCameraTotalDisp()
     */
    public void copyActiveCamera() {
        this.copyActiveCameraTotalDisp();
        //this.copyActiveCameraNoDisp();
    }
    
    /**
     * Just copies the camera of the active render screen
     */
    protected void copyActiveCameraNoDisp(){
        rotX = visualisationManager.getGUI().getActiveVisualisation().getRenderScreen().getCamera().getRotX();
        rotY = visualisationManager.getGUI().getActiveVisualisation().getRenderScreen().getCamera().getRotY();
        rotZ = visualisationManager.getGUI().getActiveVisualisation().getRenderScreen().getCamera().getRotZ();
        transX = visualisationManager.getGUI().getActiveVisualisation().getRenderScreen().getCamera().getTransX();
        transY = visualisationManager.getGUI().getActiveVisualisation().getRenderScreen().getCamera().getTransY();
        transZ = visualisationManager.getGUI().getActiveVisualisation().getRenderScreen().getCamera().getTransZ();
        speed = visualisationManager.getGUI().getActiveVisualisation().getRenderScreen().getCamera().getSpeed();
    }
    
    /**
     * Copies the camera of the active render screen adding translations depending on the respective displacements 
     */
    protected void copyActiveCameraDisp(){
        rotX = visualisationManager.getGUI().getActiveVisualisation().getRenderScreen().getCamera().getRotX();
        rotY = visualisationManager.getGUI().getActiveVisualisation().getRenderScreen().getCamera().getRotY();
        rotZ = visualisationManager.getGUI().getActiveVisualisation().getRenderScreen().getCamera().getRotZ();
        float dispX = (float) (this.visualisationManager.getDisplacement().getX() - visualisationManager.getGUI().getActiveVisualisation().getDisplacement().getX());
        float dispY = (float) (this.visualisationManager.getDisplacement().getY() - visualisationManager.getGUI().getActiveVisualisation().getDisplacement().getY());
        float dispZ = (float) (this.visualisationManager.getDisplacement().getZ() - visualisationManager.getGUI().getActiveVisualisation().getDisplacement().getZ());
        transX = visualisationManager.getGUI().getActiveVisualisation().getRenderScreen().getCamera().getTransX() + dispX;
        transY = visualisationManager.getGUI().getActiveVisualisation().getRenderScreen().getCamera().getTransY() + dispY;
        transZ = visualisationManager.getGUI().getActiveVisualisation().getRenderScreen().getCamera().getTransZ() + dispZ;
        speed = visualisationManager.getGUI().getActiveVisualisation().getRenderScreen().getCamera().getSpeed();
    }
    
    /**
     * Copies the camera of the active render screen displacing the data of the visualisation to the displacement of the active visualisation 
     */
    protected void copyActiveCameraTotalDisp(){
        visualisationManager.setDisplacement(visualisationManager.getGUI().getActiveVisualisation().getDisplacement());
        copyActiveCameraNoDisp();
    }
}
