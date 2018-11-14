package Olivia.landing;

import Olivia.core.OverlayArray;
import Olivia.core.data.Point3D_id;
import Olivia.core.render.OpenGLScreen;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 *
 * @author oscar.garcia
 */
public class LandingApproachOverlayArray extends OverlayArray<LandingApproachOverlay,LandingVisualisationManager> {

    public static float DEFAULT_RADIUS = 12.5f;

    private float radius;

    public LandingApproachOverlayArray(LandingVisualisationManager visualisationM) {
        super(visualisationM,"Approaches");
        this.radius = DEFAULT_RADIUS;
    }
    
    public LandingApproachOverlayArray(LandingVisualisationManager visualisationM, String name) {
        super(visualisationM,name);
        this.radius = DEFAULT_RADIUS;
    }

    public LandingApproachOverlayArray(LandingVisualisationManager visualisationM,float radius) {
        super(visualisationM,"Approaches");
        this.radius = radius;
    }
    
    public LandingApproachOverlayArray(LandingVisualisationManager visualisationM, String name, float radius) {
        super(visualisationM,name);
        this.radius = radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
        int i;
        for(i=0;i<overlays.size();i++){
                overlays.get(i).setRadius(radius);
        }
    }


    public void readFromFile(Path path) throws FileNotFoundException,IOException{
        try{
            int i,j;
            double pair1,pair2;
            boolean good=true;
            List<String> lines = Files.readAllLines(path);
            String delimiter = "\t";
            String[] cols;
            System.out.println("Read " + lines.size() + " lines in approaches file");
            for(String line : lines){
                cols = line.split(delimiter);
                if(cols.length<3){
                    System.out.println("Error Reading Approaches Overlay");
                    throw new IOException();
                }
                LandingApproachOverlay la = new LandingApproachOverlay(visualisationManager,Double.parseDouble(cols[0]), Double.parseDouble(cols[1]), Double.parseDouble(cols[2]));
                good=true;
                for (j = 3; j < cols.length; j = j + 2) {
                    try{//for NaN, should fix in landing
                        pair1 = Double.parseDouble(cols[j]);
                        pair2 = Double.parseDouble(cols[j + 1]);
                        la.addOcluddedPair(Double.parseDouble(cols[j]), Double.parseDouble(cols[j + 1]));
                    }catch( Exception e ){
                        good=false;
                    }
                }
                if(good) this.add(la,false);
            }
            System.out.println("Read " + overlays.size() + " approaches");
            
        }catch (IOException ex) {
            System.out.println("Error Reading Landing Approaches Overlay");
        }
    }
    /*
    @Override
    public void draw(){
        //System.out.println("Drawing approach at " + this.bounds.getCentre());
        this.drawCurrent(radius);
    }
*/

    /*
    @Override
    protected void drawShape(OpenGLScreen renderScreen) {
        //System.out.println("Drawing approaches at " + this.bounds.getCentre());
        this.drawShapeCurrent(renderScreen);
        //this.drawShapeAll(renderScreen);
    }
    */

    /*
    public void draw(float radius){
        this.drawCurrent(radius);
    }
    
    //@Override
    public void drawCurrent(){
        this.drawCurrent(radius);
    }
    
    public void drawCurrent(float radius){
        if(current!=MAX_OVERLAYS_SUPPORTED){
                overlays.get(current).setRadius(radius);
                overlays.get(current).draw();
        }    
    }
*/

}
