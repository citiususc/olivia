package PointCloudVisu.extended;

import PointCloudVisu.core.render.colours.ColourArray;
import PointCloudVisu.core.data.PointArray;
import PointCloudVisu.core.render.colours.PointColour;

/**
 * This creates colours depending on the scanline for all standard points
 *
 * @author jorge.martinez.sanchez oscar.garcia
 * @param <P> The point type, with support for scanlines
 */
public class StandardScanlineColourArray<P extends PointS> extends ColourArray {
    private static final float[] COLOUR_DIR_0 = {0.1f, 0.1f, 1.0f}; //Bluish
    private static final float[] COLOUR_DIR_1 = {0.1f, 1.0f, 0.1f}; //Greenish
    private static final float[] COLOUR_EDGE = {1.0f, 0.1f, 0.1f}; //Redish
    private static final float[] COLOUR_UNKOWN = {1.0f, 1.0f, 1.0f}; //White

    public StandardScanlineColourArray(PointArray<P> points) {
        super(points);
        for (int i = 0; i < points.size(); i++) {
            if(points.get(i).getEdge()== 1){
                this.add(new PointColour(COLOUR_EDGE[0], COLOUR_EDGE[1], COLOUR_EDGE[2]));
            }else{
                switch (points.get(i).getDirection()) {
                    case 0:
                        this.add(new PointColour(COLOUR_DIR_0[0], COLOUR_DIR_0[1], COLOUR_DIR_0[2]));
                        break;
                    case 1:
                        this.add(new PointColour(COLOUR_DIR_1[0], COLOUR_DIR_1[1], COLOUR_DIR_1[2]));
                        break;
                    default:
                        this.add(new PointColour(COLOUR_UNKOWN[0], COLOUR_UNKOWN[1], COLOUR_UNKOWN[2]));
                        break;
                }
            }
        }
    }
  
}
