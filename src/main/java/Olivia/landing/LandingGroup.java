package Olivia.landing;

import Olivia.core.data.Group;
import Olivia.extended.PointI;

/**
 * 
 * @author oscar.garcia
 */
public class LandingGroup extends Group<PointI> {

    /* Surface Groups */
    public final static int LANDING2_POINT_TYPE_UNSORTED = 0;
    public final static int LANDING2_POINT_TYPE_STDZ_TOO_HIGH = 1;
    public final static int LANDING2_POINT_TYPE_BAD = 2;
    public final static int LANDING2_POINT_TYPE_SLOPE_ACCEPTABLE = 3;
    public final static int LANDING2_POINT_TYPE_SLOPE_GOOD = 4;
    public final static int LANDING2_POINT_TYPE_GRASS_ACCEPTABLE_M = 5;
    public final static int LANDING2_POINT_TYPE_GRASS_ACCEPTABLE = 6;
    public final static int LANDING2_POINT_TYPE_GRASS_GOOD_M = 9;
    public final static int LANDING2_POINT_TYPE_GRASS_GOOD = 10;
    public final static int LANDING2_POINT_TYPE_PLAIN_ACCEPTABLE_M = 7;
    public final static int LANDING2_POINT_TYPE_PLAIN_ACCEPTABLE = 8;
    public final static int LANDING2_POINT_TYPE_PLAIN_GOOD_M = 11;
    public final static int LANDING2_POINT_TYPE_PLAIN_GOOD = 12;

    public final static int LANDING2_NUMBER_TYPES1 = 13;

    /* Landing site groups */
    public final static int LANDING2_POINT_TYPE2_PART_NOSPACE = 0;
    public final static int LANDING2_POINT_TYPE2_NOSPACE = 1;
    public final static int LANDING2_POINT_TYPE2_PART_RISKY = 2;
    public final static int LANDING2_POINT_TYPE2_RISKY = 3;
    public final static int LANDING2_POINT_TYPE2_PART_ACCEPTABLE = 4;
    public final static int LANDING2_POINT_TYPE2_ACCEPTABLE = 5;
    public final static int LANDING2_POINT_TYPE2_PART_GOOD = 6;
    public final static int LANDING2_POINT_TYPE2_GOOD = 7;
    public final static int LANDING2_POINT_TYPE2_PART_APROX = 8; //actually the opposite
    public final static int LANDING2_POINT_TYPE2_APROX = 9;
    public final static int LANDING2_POINT_TYPE2_PERFECT = 10;

    public final static int LANDING2_NUMBER_TYPES2 = 11;

    protected int type1;
    protected int type2;

    public LandingGroup(int type1, int type2) {
        if ((type1 < 0) || (type1 >= LANDING2_NUMBER_TYPES1) || (type2 < 0) || (type2 >= LANDING2_NUMBER_TYPES2)) {
            type1 = 0;
            type2 = 0;
        } else {
            this.type1 = type1;
            this.type2 = type2;
        }
        this.id = type2 * LANDING2_NUMBER_TYPES1 + type1;
    }
    
    public LandingGroup(int id) {
        this.id = id;
        this.type1 = (id) % LANDING2_NUMBER_TYPES1;
        this.type2 = Math.abs((id) / LANDING2_NUMBER_TYPES1);
    }

    public LandingGroup() {
        this.type1 = -1;
        this.type2 = -1;
    }

    public int getType1() {
        return type1;
    }

    public int getType2() {
        return type2;
    }

    public int getFullType() {
        return type2 * LANDING2_NUMBER_TYPES1 + type1;
    }

    @Override
    public void setId(int id) {
        this.id = id;
        this.type1 = (id) % LANDING2_NUMBER_TYPES1;
        this.type2 = Math.abs((id) / LANDING2_NUMBER_TYPES1);
    }

    @Override
    public String toString() {
        String ret = "G(" + id + ";" + type1 + "," + type2 + ")";
        return ret;
    }

    public String toHtmlString() {
        String desc = "";
        switch (type1) {
            case LANDING2_POINT_TYPE_UNSORTED:
                desc += "Unsorted";
                break;
            case LANDING2_POINT_TYPE_STDZ_TOO_HIGH:
                desc += "StdZ too high";
                break;
            case LANDING2_POINT_TYPE_BAD:
                desc += "Unsuitable";
                break;
            case LANDING2_POINT_TYPE_SLOPE_ACCEPTABLE:
                desc += "Acceptable slope";
                break;
            case LANDING2_POINT_TYPE_SLOPE_GOOD:
                desc += "Good slope";
                break;
            case LANDING2_POINT_TYPE_GRASS_ACCEPTABLE_M:
                desc += "Maybe acceptable grass";
                break;
            case LANDING2_POINT_TYPE_GRASS_ACCEPTABLE:
                desc += "Acceptable grass";
                break;
            case LANDING2_POINT_TYPE_GRASS_GOOD_M:
                desc += "Maybe good grass";
                break;
            case LANDING2_POINT_TYPE_GRASS_GOOD:
                desc += "Good grass";
                break;
            case LANDING2_POINT_TYPE_PLAIN_ACCEPTABLE_M:
                desc += "Maybe acceptable plain";
                break;
            case LANDING2_POINT_TYPE_PLAIN_ACCEPTABLE:
                desc += "Acceptable plain";
                break;
            case LANDING2_POINT_TYPE_PLAIN_GOOD_M:
                desc += "Maybe good plain";
                break;
            case LANDING2_POINT_TYPE_PLAIN_GOOD:
                desc += "Good plain";
                break;
        }
        desc += " terrain, ";
        switch (type2) {
            case LANDING2_POINT_TYPE2_PART_NOSPACE:
                desc += "part of an unsuitable landing area";
                break;
            case LANDING2_POINT_TYPE2_NOSPACE:
                desc += "unsuitable landing area";
                break;
            case LANDING2_POINT_TYPE2_PART_RISKY:
                desc += "part of a risky landing area";
                break;
            case LANDING2_POINT_TYPE2_RISKY:
                desc += "risky landing area";
                break;
            case LANDING2_POINT_TYPE2_PART_ACCEPTABLE:
                desc += "part of an acceptable landing area";
                break;
            case LANDING2_POINT_TYPE2_ACCEPTABLE:
                desc += "acceptable landing area";
                break;
            case LANDING2_POINT_TYPE2_PART_GOOD:
                desc += "part of a good landing area";
                break;
            case LANDING2_POINT_TYPE2_GOOD:
                desc += "good landing area";
                break;
            case LANDING2_POINT_TYPE2_PART_APROX:
                desc += "approximation obstacle";
                break;
            case LANDING2_POINT_TYPE2_APROX:
                desc += "good approximation landing area";
                break;
            case LANDING2_POINT_TYPE2_PERFECT:
                desc += "perfect approximation landing area";
                break;
        }

        return "<html><p>" + desc + "</p></html>";
    }
}
