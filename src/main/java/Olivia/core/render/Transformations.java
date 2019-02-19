package Olivia.core.render;

import Olivia.core.Olivia;
import Olivia.core.data.Point3D;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;

/**
 * Used to save the OpenGL transformation of rotation and translation. Does not
 * execute any openGL operations until they are called
 *
 * @author oscar.garcia
 */
public class Transformations {

    /**
     * The file header for the file
     */
    public static String FILE_HEADER = "point cloud visu camera file";
    
    /**
     * The file version
     */
    public static String FILE_VERSION = "v1.0";


    /**
     * The center of the camera
     */
    //protected float[] center;
    protected Point3D centre;
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
     * Sets the defaults
     *
     */
    public Transformations() {
        this.transZ = 0.0f;
        this.transY = 0.0f;
        this.transX = 000.0f;
        this.rotY = 0.0f;
        this.rotZ = 0.0f;
        this.rotX = 0.0f;
        /*center = new float[3];
        center[0] = 0;
        center[1] = 0;
        center[2] = 0;*/
        centre = new Point3D();
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
     * Sets the rotation on Y
     * @param rotY 
     */
    public void setRotY(float rotY) {
        this.rotY = rotY;
    }
    
    /**
     * Adds amount to the rotation in Y
     *
     * @param amount The amount to be added to the rotation in Y
     */
    public void addToRotY(float amount) {
        rotY += amount;
    }
    
    /**
     * Gets the rotation on Y
     * @return the rotation on Y
     */
    public float getRotY() {
        return rotY;
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
     * Gets the centre of the transformations (needs not be the center of the object)
     * @return the centre of the transformations 
     */
    public Point3D getCentre() {
        return centre;
    }

    /**
     * Sets the centre of the transformations (needs not be the center of the object)
     * @param centre the centre of the transformations
     */
    public void setCentre(Point3D centre) {
        this.centre = centre;
    }
    
    

    /**
     * Executes a translation on the render screen with the stored parameters
     *
     * @param renderScreen The render screen
     */
    public void doTranslatef(OpenGLScreen renderScreen) {
        renderScreen.getGl2().glTranslatef(transX, transY, transZ);
        //visualisationManager.getGUI().cameraMoved();
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
        //visualisationManager.getGUI().cameraMoved();
    }

    /**
     * Executes the translation and rotation on the render screen with the
     * stored parameters
     *
     * @param renderScreen The render screen
     */
    public void doTranslateAndRotate(OpenGLScreen renderScreen) {
        //renderScreen.getGl2().glTranslated(-centre.getX(), -centre.getY(), -centre.getZ());
        
        //renderScreen.getGl2().glTranslated(centre.getX(), centre.getY(), centre.getZ());
        
        renderScreen.getGl2().glTranslatef(transX, transY, 0.0f);
        renderScreen.getGl2().glTranslatef(0.0f, 0.0f, transZ);

        renderScreen.getGl2().glRotatef(rotX, 1.0f, 0.0f, 0.0f);
        renderScreen.getGl2().glRotatef(rotY, 0.0f, 1.0f, 0.0f);
        renderScreen.getGl2().glRotatef(rotZ, 0.0f, 0.0f, 1.0f);

        //renderScreen.getGl2().glTranslated(centre.getX(), centre.getY(), centre.getZ());
        //renderScreen.getGl2().glTranslated(-centre.getX(), -centre.getY(), -centre.getZ());
    }

    /**
     * Writes the transforamtio parameters to a file
     * @param path The path to the file
     * @param fileName the fname of the file
     */
    public void writeToFile(String path, String fileName) {
        Charset charset = Charset.forName("US-ASCII");
        File file = new File(path + "/" + fileName);
        try (BufferedWriter writer = Files.newBufferedWriter(file.toPath(), charset)) {
            writer.write(FILE_HEADER, 0, FILE_HEADER.length());
            writer.newLine();
            writer.write(FILE_VERSION, 0, FILE_VERSION.length());
            writer.newLine();
            String data = centre.getX() + "\t" + centre.getY() + "\t" + centre.getZ() + "\t" 
                    + transX + "\t" + transY + "\t" + transZ + "\t" + rotX + "\t" + rotY + "\t" + rotZ + "\t0.0" ;
            writer.write(data, 0, data.length());
            writer.close();
            Olivia.textOutputter.println("Camera data written to file " + file);
        }
        catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }
    }

    /**
     * Reads the transformation values from a file 
     * @param path The path to the file
     * @param fileName the fname of the file
     * @return true if the camera values could be read
     */
    public int readFromFile(String path, String fileName) {
        Charset charset = Charset.forName("US-ASCII");
        File file = new File(path + "/" + fileName);
        try (BufferedReader reader = Files.newBufferedReader(file.toPath(), charset)) {
            String line = reader.readLine();
            if (line == null) {
                Olivia.textOutputter.println(file + " is empty");
                return -1;
            }
            if (!line.equals(FILE_HEADER)) {
                Olivia.textOutputter.println(file + " is not a correct camera file");
                return -1;
            }
            line = reader.readLine();
            if (line == null) {
                Olivia.textOutputter.println(file + " has no data");
                return -1;
            }
            if (!line.equals(FILE_VERSION)) {
                Olivia.textOutputter.println(file + " is version " + line + " and the current camera file version is " + FILE_VERSION);
                return -1;
            }
            line = reader.readLine();
            if (line == null) {
                Olivia.textOutputter.println(file + " has no data");
                return -1;
            }

            String[] data = line.split("\t");
            if (data.length != 10) {
                Olivia.textOutputter.println(file + " data does not coincide with version");
                return -1;
            }

            this.transX = Float.parseFloat(data[3]);
            this.transY = Float.parseFloat(data[4]);
            this.transZ = Float.parseFloat(data[5]);
            this.rotX = Float.parseFloat(data[6]);
            this.rotY = Float.parseFloat(data[7]);
            this.rotZ = Float.parseFloat(data[8]);
            //this.speed = Float.parseFloat(data[9]);

            Olivia.textOutputter.println("Camera data read from file " + file);
            return 0;
        }
        catch (IOException x) {
            System.err.format("IOException: %s%n", x);
            return -1;
        }
    }

}
