package Olivia.extended;

import Olivia.core.render.colours.ColourArray;
import Olivia.core.data.PointArray;
import Olivia.core.render.colours.PointColour;

/**
 * This creates colours depending on the scanline for all standard points
 *
 * @author jorge.martinez.sanchez oscar.garcia
 * @param <P> The point type, with support for scanlines
 */
public class StandardScanlineColourArray<P extends PointS> extends ColourArray {
    protected PointColour COLOUR_DIR_0 = new PointColour(0.1f, 0.1f, 1.0f); //Bluish
    protected PointColour COLOUR_DIR_1 = new PointColour(0.1f, 1.0f, 0.1f); //Greenish
    protected PointColour COLOUR_EDGE = new PointColour(1.0f, 0.1f, 0.1f); //Redish
    protected PointColour COLOUR_UNKOWN = new PointColour(1.0f, 1.0f, 1.0f); //White

    public StandardScanlineColourArray(PointArray<P> points) {
        super(points);
        for (int i = 0; i < points.size(); i++) {
            if(points.get(i).getEdge()== 1){
                this.add(COLOUR_EDGE);
            }else{
                switch (points.get(i).getDirection()) {
                    case 0:
                        this.add(COLOUR_DIR_0);
                        break;
                    case 1:
                        this.add(COLOUR_DIR_1);
                        break;
                    default:
                        this.add(COLOUR_UNKOWN);
                        break;
                }
            }
        }
    }
  
}
