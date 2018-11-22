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
    protected PointColour COLOUR_FIRST_RETURN = new PointColour(0.1f, 0.1f, 1.0f); //Bluish
    protected PointColour COLOUR_SECOND_RETURN = new PointColour(0.1f, 1.0f, 0.1f); //Greenish
    protected PointColour COLOUR_THIRD_RETURN = new PointColour(1.0f, 0.1f, 0.1f); //Redish
    protected PointColour COLOUR_OTHER_RETURN = new PointColour(1.0f, 1.0f, 1.0f); //White

    public StandardReturnColourArray(PointArray<P> points) {
        super(points);
        for (int i = 0; i < points.size(); i++) {
            this.add(getPointColour(points.get(i).getReturnNumber()));
        }
    }
    
    public PointColour getPointColour(int returnNumber) {
        PointColour colour;
        switch (returnNumber) {
            case 1:
                colour = COLOUR_FIRST_RETURN;
                break;
            case 2:
                colour = COLOUR_SECOND_RETURN;
                break;
            case 3:
                colour = COLOUR_THIRD_RETURN;
                break;
            default:
                colour = COLOUR_OTHER_RETURN;
                break;
        }
        return colour;
    }
}
