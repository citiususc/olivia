package Olivia.segmenter;

import Olivia.core.data.Group;
import Olivia.extended.PointI;
import java.text.DecimalFormat;

/**
 * This is a standard group plus intensity
 *
 * @author jorge.martinez.sanchez
 */
public class SegmenterGroup extends Group<PointI> {

    private double sumI = 0;

    public double getMeanI() {
        return sumI / super.points.size();
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public void addPoint(PointI point) {
        super.addPoint(point);
        sumI += point.getIntensity();
    }

    @Override
    public String getDescriptionAsHTML() {
        DecimalFormat df = new DecimalFormat("#.00");
        String html = "<html><p>"
                + "<b>Id</b>: " + Integer.toString(id) + " "
                + "<b>Size</b>: " + Integer.toString(points.size()) + "<br>"
                + "<b>X</b> : " + df.format(getMeanX()) + " "
                + "<b>Y</b> : " + df.format(getMeanY()) + " "
                + "<b>Z</b> : " + df.format(getMeanZ()) + " "
                + "<b>I</b>: " + df.format(getMeanI())
                + "</html>";
        return html;
    }
}
