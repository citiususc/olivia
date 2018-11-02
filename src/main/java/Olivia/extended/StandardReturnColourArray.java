package Olivia.extended;

import Olivia.core.render.colours.ColourArray;
import Olivia.core.data.PointArray;
import Olivia.core.render.colours.PointColour;

/**
 * This creates colours depending on the return for all standard points
 *
 * @author jorge.martinez.sanchez oscar.garcia
 * @param <P> The point type, with support for returns
 */
public class StandardReturnColourArray<P extends PointR> extends ColourArray {
    private static final float[] COLOUR_FIRST_RETURN = {0.1f, 0.1f, 1.0f}; //Bluish
    private static final float[] COLOUR_SECOND_RETURN = {0.1f, 1.0f, 0.1f}; //Greenish
    private static final float[] COLOUR_THIRD_RETURN = {1.0f, 0.1f, 0.1f}; //Redish
    private static final float[] COLOUR_OTHER_RETURN = {1.0f, 1.0f, 1.0f}; //White

    public StandardReturnColourArray(PointArray<P> points) {
        super(points);
        for (int i = 0; i < points.size(); i++) {
            this.add(getPointColour(points.get(i).getReturnNumber()));
        }
    }
    
    public static PointColour getPointColour(int type) {
        PointColour colour;
        switch (type) {
            case 1:
                colour = new PointColour(COLOUR_FIRST_RETURN[0], COLOUR_FIRST_RETURN[1], COLOUR_FIRST_RETURN[2]);
                break;
            case 2:
                colour = new PointColour(COLOUR_SECOND_RETURN[0], COLOUR_SECOND_RETURN[1], COLOUR_SECOND_RETURN[2]);
                break;
            case 3:
                colour = new PointColour(COLOUR_THIRD_RETURN[0], COLOUR_THIRD_RETURN[1], COLOUR_THIRD_RETURN[2]);
                break;
            default:
                colour = new PointColour(COLOUR_OTHER_RETURN[0], COLOUR_OTHER_RETURN[1], COLOUR_OTHER_RETURN[2]);
                break;
        }
        return colour;
    }
}
