package Olivia.extended;

import Olivia.core.Olivia;
import Olivia.core.data.PointArray;
import Olivia.core.render.colours.ColourArray;
import Olivia.core.render.colours.PointColour;

/**
 *
 * @author jorge.martinez.sanchez
 */
public class GradientColourArray<P extends PointI> extends ColourArray {

    public GradientColourArray(final PointArray<P> points) {
        super(points);
        
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
        for (PointI point : points) {
            GradientColourArray.this.add(GetGradientColour(point.I, min_i, max_i));     
        }
    }
    
    PointColour GetGradientColour(double x, double min, double max)
    {
        PointColour c = new PointColour(1, 1, 1); // white
        if (x < min) x = min;
        if (x > max) x = max;
        double range = max - min;
        double q1 = 0.25 * range;
        double q2 = 0.50 * range;
        double q3 = 0.75 * range;

        if (x < (min + q1)) {
           c.setR(0);
           c.setG((float) (4 * (x - min) / range));
        } else if (x < (min + q2)) {
           c.setR(0);
           c.setB((float) (1 + 4 * (min + q1 - x) / range));
        } else if (x < (min + q3)) {
           c.setR((float) (4 * (x - min - q2) / range));
           c.setB(0);
        } else {
           c.setG((float) (1 + 4 * (min + q3 - x) / range));
           c.setB(0);
        }

        return(c);
    }
}
