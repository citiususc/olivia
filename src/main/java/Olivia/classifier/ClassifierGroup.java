package Olivia.classifier;

import Olivia.core.data.Group;
import Olivia.extended.PointI;
import java.text.DecimalFormat;

/**
 * This is a standard group plus intensity and classification label
 * 
 * @author jorge.martinez.sanchez
 */
public class ClassifierGroup extends Group<PointI> {

    private double sumI = 0;
    private final int type;

    public ClassifierGroup(int type) {
        this.type = type;
    }

    public double getMeanI() {
        return sumI / super.points.size();
    }

    public int getType() {
        return type;
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
        DecimalFormat df = new DecimalFormat("#.##");
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
