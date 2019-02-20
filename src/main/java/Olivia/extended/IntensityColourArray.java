package Olivia.extended;

import Olivia.core.Olivia;
import Olivia.core.render.colours.ColourArray;
import Olivia.core.data.PointArray;
import Olivia.core.render.colours.PointColour;

/**
 * This creates colours for all points in an array depending of their intensity,
 * so it needs intensity points
 *
 * @author oscar.garcia
 * @param <P> Point needs to have at least intensity
 * @todo Use a worker thread but not in Basic Visualisation
 * @todo Handle intensity outliers
 */
public class IntensityColourArray<P extends PointI> extends ColourArray {
    protected float grad;

    public IntensityColourArray(final PointArray<P> points) {
        super(points);

        //SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
        //@Override
        //protected Void doInBackground() throws Exception {
        float max_i = 0;
        float min_i = 0;

        for (PointI point : points) {
            if (point.getIntensity() < min_i) {
                min_i = point.getIntensity();
            }
            if (point.getIntensity() > max_i) {
                max_i = point.getIntensity();
            }
        }

        Olivia.textOutputter.println("Intensity between: " + min_i + " and " + max_i);
        this.grad = max_i - min_i;
        
        //long startTime = System.currentTimeMillis();
        
        //points.parallelStream().forEach(i -> calculateIntensity(i));

        
        for (PointI point : points) {
            float r = 0.05f + point.getIntensity() / grad;
            float g = 0.05f + point.getIntensity() / grad;
            float b = 0.05f + point.getIntensity() / grad;
            this.add(new PointColour(r, g, b));
        }
        
        
        Olivia.textOutputter.println("Loaded intensity colours");
        //return null;
        //}
        //};

        //worker.execute();
        //long endTime = System.currentTimeMillis();
        
        //Olivia.textOutputter.println("Time required to calculate intensity colours : " + (endTime - startTime));
    }
    /*
    protected void calculateIntensity(PointI point){
        float r = 0.05f + point.getIntensity() / grad;
        float g = 0.05f + point.getIntensity() / grad;
        float b = 0.05f + point.getIntensity() / grad;
        this.add(new PointColour(r, g, b));
    }
    */
}
