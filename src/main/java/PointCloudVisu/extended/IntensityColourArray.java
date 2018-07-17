package PointCloudVisu.extended;

import PointCloudVisu.core.render.colours.ColourArray;
import PointCloudVisu.core.data.PointArray;
import PointCloudVisu.core.render.colours.PointColour;
import javax.swing.SwingWorker;

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

        System.out.println("Intensity between: " + min_i + " and " + max_i);
        float grad = max_i - min_i;

        for (PointI point : points) {
            float r = point.getIntensity() / grad;
            float g = point.getIntensity() / grad;
            float b = point.getIntensity() / grad;
            IntensityColourArray.this.add(new PointColour(r, g, b));
        }
        //System.out.println("Loaded intensity colours");
        //return null;
        //}
        //};

        //worker.execute();
    }
}
