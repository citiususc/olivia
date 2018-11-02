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
 * Used to save the camera atributes of rotation and translation. Does not
 * execute any openGL operations until they are called
 *
 * @author oscar.garcia
 */
public class TransformationsDouble {

    public static String FILE_HEADER = "point cloud visu camera file";
    public static String FILE_VERSION = "v2.0";


    /**
     * The center of the camera
     */
    //protected double[] center;
    protected Point3D centre;
    /**
     * The rotation on X
     */
    protected double rotX;
    /**
     * The rotation on Z
     */
    protected double rotZ;
    /**
     * The rotation on Y
     */
    protected double rotY;
    /**
     * The translation on X
     */
    protected double transX;
    /**
     * The translation on Y
     */
    protected double transY;
    /**
     * The translation on Z
     */
    protected double transZ;

    /**
     * Sets the defaults
     *
     */
    public TransformationsDouble() {
        this.transZ = 0.0f;
        this.transY = 0.0f;
        this.transX = 000.0f;
        this.rotY = 0.0f;
        this.rotZ = 0.0f;
        this.rotX = 0.0f;
        /*center = new double[3];
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
    public double getRotX() {
        return rotX;
    }

    /**
     * setter
     *
     * @param rotX the rotation in X
     */
    public void setRotX(double rotX) {
        this.rotX = rotX;
    }

    /**
     * Adds amount to the rotation in X
     *
     * @param amount The ammount to be added to the rotation in X
     */
    public void addToRotX(double amount) {
        rotX += amount;
    }

    
    /**
     * getter
     *
     * @return The rotation in Z
     */
    public double getRotZ() {
        return rotZ;
    }

    /**
     * setter
     *
     * @param rotZ the rotation in Z
     */
    public void setRotZ(double rotZ) {
        this.rotZ = rotZ;
    }
    

    /**
     * Adds amount to the rotation in Z
     *
     * @param amount The ammount to be added to the rotation in Z
     */
    public void addToRotZ(double amount) {
        rotZ += amount;
    }

    public void setRotY(double rotY) {
        this.rotY = rotY;
    }
    
    public double getRotY() {
        return rotY;
    }

    /**
     * getter
     *
     * @return The translation in X
     */
    public double getTransX() {
        return transX;
    }

    /**
     * setter
     *
     * @param transX the translation in X
     */
    public void setTransX(double transX) {
        this.transX = transX;
    }

    /**
     * Adds amount to the translation in X
     *
     * @param amount The ammount to be added to the translation in X
     */
    public void addToTransX(double amount) {
        transX += amount;
    }


    /**
     * getter
     *
     * @return The translation in Y
     */
    public double getTransY() {
        return transY;
    }

    /**
     * setter
     *
     * @param transY the translation in Y
     */
    public void setTransY(double transY) {
        this.transY = transY;
    }

    /**
     * Adds amount to the translation in Y
     *
     * @param amount The ammount to be added to the translation in Y
     */
    public void addToTransY(double amount) {
        transY += amount;
    }


    /**
     * getter
     *
     * @return The translation in Z
     */
    public double getTransZ() {
        return transZ;
    }

    /**
     * setter
     *
     * @param transZ the translation in z
     */
    public void setTransZ(double transZ) {
        this.transZ = transZ;
    }

    /**
     * Adds amount to the translation in Z
     *
     * @param amount The ammount to be added to the translation in Z
     */
    public void addToTransZ(double amount) {
        transZ += amount;
    }


    public Point3D getCentre() {
        return centre;
    }

    public void setCentre(Point3D centre) {
        this.centre = centre;
    }
    
    

    /**
     * Executes a translation on the render screen with the stored parameters
     *
     * @param renderScreen The render screen
     */
    public void doTranslated(OpenGLScreen renderScreen) {
        renderScreen.getGl2().glTranslated(transX, transY, transZ);
        //visualisationManager.getGUI().cameraMoved();
    }

    /**
     * Executes a rotation on the render screen with the stored parameters
     *
     * @param renderScreen The render screen
     */
    public void doRotated(OpenGLScreen renderScreen) {
        renderScreen.getGl2().glRotated(rotX, 1.0, 0.0, 0.0);
        renderScreen.getGl2().glRotated(rotY, 0.0, 1.0, 0.0);
        renderScreen.getGl2().glRotated(rotZ, 0.0, 0.0, 1.0);
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
        
        renderScreen.getGl2().glTranslated(transX, transY, 0.0);
        renderScreen.getGl2().glTranslated(0.0, 0.0, transZ);

        renderScreen.getGl2().glRotated(rotX, 1.0, 0.0, 0.0);
        renderScreen.getGl2().glRotated(rotY, 0.0, 1.0, 0.0);
        renderScreen.getGl2().glRotated(rotZ, 0.0, 0.0, 1.0);

        //renderScreen.getGl2().glTranslated(centre.getX(), centre.getY(), centre.getZ());
        //renderScreen.getGl2().glTranslated(-centre.getX(), -centre.getY(), -centre.getZ());
    }

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
            System.out.println("Camera data written to file " + file);
        }
        catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }
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

            this.transX = Double.parseDouble(data[3]);
            this.transY = Double.parseDouble(data[4]);
            this.transZ = Double.parseDouble(data[5]);
            this.rotX = Double.parseDouble(data[6]);
            this.rotY = Double.parseDouble(data[7]);
            this.rotZ = Double.parseDouble(data[8]);
            //this.speed = Double.parseDouble(data[9]);

            System.out.println("Camera data read from file " + file);
            return 0;
        }
        catch (IOException x) {
            System.err.format("IOException: %s%n", x);
            return -1;
        }
    }

}
