package PointCloudVisu.extended;

import PointCloudVisu.core.render.colours.ColourArray;
import PointCloudVisu.core.data.PointArray;
import PointCloudVisu.core.render.colours.PointColour;
import java.util.Random;

/**
 * This creates colours for all points in an array randomly, so it does need to
 * know what kind of points they are
 *
 * @author oscar.garcia
 */
public class RandomColourArray extends ColourArray {

    public static int RANDOM_SEED = 1;

    public RandomColourArray(PointArray points) {
        super(points);
        Random rand = new Random();
        rand.setSeed(RANDOM_SEED);
        for (int i = 0; i < points.size(); i++) {
            float r = rand.nextFloat();
            float g = rand.nextFloat();
            float b = rand.nextFloat();
            this.add(new PointColour(r, g, b));
        }
        System.out.println("Random colours loaded");
    }
}
