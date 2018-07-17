package PointCloudVisu.core.render;

import PointCloudVisu.core.PointCloudVisu;
import PointCloudVisu.core.data.Point3D;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;

/**
 * Used to save the camera atributes of rotation and translation. Does not
 * execute any openGL operations until they are called
 *
 * @author oscar.garcia
 */
public class Camera {

    public static String DEFAULT_FILE_NAME = "camera.txt";
    public static String FILE_HEADER = "point cloud visu camera file";
    public static String FILE_VERSION = "v1.0";

    protected double cloudCenterX;
    protected double cloudCenterY;
    protected double cloudCenterZ;

    /**
     * Needed for tracking selected visualisation (mirroring)
     */
    protected OpenGLScreen renderScreen;
    /**
     * The initial zoom, or translation on Z
     */
    protected float initZ;
    /**
     * The center of the camera, as 3 doubles
     */
    protected double[] center;
    /**
     * The rotation on X
     */
    protected float rotX;
    /**
     * The rotation on Z
     */
    protected float rotZ;
    /**
     * The rotation on Y
     */
    protected float rotY;
    /**
     * The translation on X
     */
    protected float transX;
    /**
     * The translation on Y
     */
    protected float transY;
    /**
     * The translation on Z
     */
    protected float transZ;
    /**
     * The step of the default transformations, depends on the transformation
     */
    protected float speed;

    /**
     * Sets the defaults
     *
     * @param renderScreen Where data is being rendered
     */
    public Camera(OpenGLScreen renderScreen) {
        this.initZ = -600.0f;
        this.speed = 0.0f;
        this.transZ = 0.0f;
        this.transY = 0.0f;
        this.transX = 000.0f;
        this.rotY = 0.0f;
        this.rotZ = 0.0f;
        this.rotX = 0.0f;
        this.renderScreen = renderScreen;
        center = new double[3];
        center[0] = 0;
        center[1] = 0;
        center[2] = 0;
    }

    /**
     * getter
     *
     * @return The rotation in X
     */
    public float getRotX() {
        return rotX;
    }

    /**
     * setter
     *
     * @param rotX the rotation in X
     */
    public void setRotX(float rotX) {
        this.rotX = rotX;
    }

    /**
     * Adds amount to the rotation in X
     *
     * @param amount The ammount to be added to the rotation in X
     */
    public void addToRotX(float amount) {
        rotX += amount;
    }

    /**
     * Increases the rotation in X one unit plus speed
     */
    public void increaseRotX() {
        rotX += 1.0f + speed;
    }

    /**
     * Decreases the rotation in X one unit plus speed
     */
    public void decreaseRotX() {
        rotX -= 1.0f + speed;
    }

    /**
     * getter
     *
     * @return The rotation in Z
     */
    public float getRotZ() {
        return rotZ;
    }

    /**
     * setter
     *
     * @param rotZ the rotation in Z
     */
    public void setRotZ(float rotZ) {
        this.rotZ = rotZ;
    }

    /**
     * Adds amount to the rotation in Z
     *
     * @param amount The ammount to be added to the rotation in Z
     */
    public void addToRotZ(float amount) {
        rotZ += amount;
    }

    /**
     * Increases the rotation in Z one unit plus speed
     */
    public void increaseRotZ() {
        rotZ += 1.0f + speed;
    }

    /**
     * Decreases the rotation in Z one unit plus speed
     */
    public void decreaseRotZ() {
        rotZ -= 1.0f + speed;
    }

    /**
     * getter
     *
     * @return The translation in X
     */
    public float getTransX() {
        return transX;
    }

    /**
     * setter
     *
     * @param transX the translation in X
     */
    public void setTransX(float transX) {
        this.transX = transX;
    }

    /**
     * Adds amount to the translation in X
     *
     * @param amount The ammount to be added to the translation in X
     */
    public void addToTransX(float amount) {
        transX += amount;
    }

    /**
     * Increases the translation in X 5 units plus speed
     */
    public void increaseTransX() {
        transX += 5.0f + speed;
    }

    /**
     * Decreases the translation in X 5 units plus speed
     */
    public void decreaseTransX() {
        transX -= 5.0f + speed;
    }

    /**
     * getter
     *
     * @return The translation in Y
     */
    public float getTransY() {
        return transY;
    }

    /**
     * setter
     *
     * @param transY the translation in Y
     */
    public void setTransY(float transY) {
        this.transY = transY;
    }

    /**
     * Adds amount to the translation in Y
     *
     * @param amount The ammount to be added to the translation in Y
     */
    public void addToTransY(float amount) {
        transY += amount;
    }

    /**
     * Increases the translation in Y 5 units plus speed
     */
    public void increaseTransY() {
        transY += 3.0f + speed;
    }

    /**
     * Decreases the translation in Y 5 units plus speed
     */
    public void decreaseTransY() {
        transY -= 3.0f + speed;
    }

    /**
     * getter
     *
     * @return The translation in Z
     */
    public float getTransZ() {
        return transZ;
    }

    /**
     * setter
     *
     * @param transZ the translation in z
     */
    public void setTransZ(float transZ) {
        this.transZ = transZ;
    }

    /**
     * Adds amount to the translation in Z
     *
     * @param amount The ammount to be added to the translation in Z
     */
    public void addToTransZ(float amount) {
        transZ += amount;
    }

    /**
     * Increases the translation in Z 5 units plus speed
     */
    public void increaseTransZ() {
        transZ += 5.0f + speed;
    }

    /**
     * Decreases the translation in Z 5 units plus speed
     */
    public void decreaseTransZ() {
        transZ -= 5.0f + speed;
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

    public void increaseSpeed() {
        speed += 1.0f;
        renderScreen.getMainFrame().logIntoConsole("Speed increased to " + speed);
    }

    public void decreaseSpeed() {
        speed -= 1.0f;
        renderScreen.getMainFrame().logIntoConsole("Speed decreased to " + speed);
    }

    /**
     * Centers the camera at a given point, does not call the openGL
     * tranformations
     *
     * @param p The point ot center the camera
     */
    public void centerAtPoint(Point3D p) {
        if (p == null) {
            p = new Point3D(0f, 0f, 0f);
        }
        double zoomOut = 400f;  
        transX = (float) -p.getX() + (float) cloudCenterX;
        transY = (float) -p.getY() + (float) cloudCenterY;
        transZ = (float) (-p.getZ() - zoomOut + (float) cloudCenterZ);
        rotX = 0;
        rotZ = 0;
    }

    /**
     * Center the camera at a given point, does not call the openGL
     * tranformations
     *
     * @param center 3 doubles with x, y, z coordinates
     */
    public void centerAt(double center[]) {
        double zoomOut = 400f;
        transX = (float) -center[0];
        transY = (float) -center[1];
        transZ = (float) (-center[2] - zoomOut);
        rotX = 0;
        rotZ = 0;
    }

    public void setCloudCentre(double center[]) {
        cloudCenterX = center[0];
        cloudCenterY = center[1];
        cloudCenterZ = center[2];
    }

    /**
     * Executes a translation on the render screen with the stored parameters
     *
     * @param renderScreen The render screen
     */
    public void doTranslatef(OpenGLScreen renderScreen) {
        renderScreen.getGl2().glTranslatef(transX, transY, transZ);
    }

    /**
     * Executes a rotation on the render screen with the stored parameters
     *
     * @param renderScreen The render screen
     */
    public void doRotatef(OpenGLScreen renderScreen) {
        renderScreen.getGl2().glRotatef(rotX, 1.0f, 0.0f, 0.0f);
        renderScreen.getGl2().glRotatef(rotY, 0.0f, 1.0f, 0.0f);
        renderScreen.getGl2().glRotatef(rotZ, 0.0f, 0.0f, 1.0f);
    }

    /**
     * Executes the translation and rotation on the render screen with the
     * stored parameters
     *
     * @param renderScreen The render screen
     */
    public void doTranslateAndRotate(OpenGLScreen renderScreen) {
        renderScreen.getGl2().glTranslatef(transX, transY, 0.0f);
        renderScreen.getGl2().glTranslatef(0.0f, 0.0f, transZ);
        renderScreen.getGl2().glTranslatef(0.0f, 0.0f, initZ);

        renderScreen.getGl2().glRotatef(rotX, 1.0f, 0.0f, 0.0f);
        renderScreen.getGl2().glRotatef(rotY, 0.0f, 1.0f, 0.0f);
        renderScreen.getGl2().glRotatef(rotZ, 0.0f, 0.0f, 1.0f);

        renderScreen.getGl2().glTranslated(-cloudCenterX, -cloudCenterY, -cloudCenterZ);
    }

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

    public void writeToFile(String path) {
        writeToFile(path, DEFAULT_FILE_NAME);
    }

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

    public int readFromFile(String path) {
        return readFromFile(path, DEFAULT_FILE_NAME);
    }

    /**
     * Copies the camera of the active render screen
     */
    public void copyActiveCamera() {
        rotX = PointCloudVisu.getActiveScreen().getCamera().getRotX();
        rotZ = PointCloudVisu.getActiveScreen().getCamera().getRotZ();
        transX = PointCloudVisu.getActiveScreen().getCamera().getTransX();
        transY = PointCloudVisu.getActiveScreen().getCamera().getTransY();
        transZ = PointCloudVisu.getActiveScreen().getCamera().getTransZ();
        speed = PointCloudVisu.getActiveScreen().getCamera().getSpeed();
    }
}
