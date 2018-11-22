/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Olivia.landing;

import Olivia.core.data.PointArray;
import Olivia.core.render.colours.ColourArray;
import Olivia.core.render.colours.PointColour;
import Olivia.extended.PointI;

/**
 *
 * @author oscar.garcia
 */
public class LandingIntensityColourArray extends ColourArray{
    /*PointColour black = new PointColour(0.0f, 0.0f, 0.0f);
    PointColour r_1 = new PointColour(0.5f, 0.0f, 0.0f);
    PointColour r_2 = new PointColour(0.4f, 0.0f, 0.0f);
    PointColour r_3 = new PointColour(0.55f, 0.0f, 0.0f);
    PointColour r_4 = new PointColour(0.6f, 0.0f, 0.0f);
    PointColour r_5 = new PointColour(0.65f, 0.0f, 0.0f);
    PointColour r_6 = new PointColour(0.7f, 0.0f, 0.0f);
    PointColour r_7 =new PointColour(0.8f, 0.0f, 0.0f);
    PointColour r_9 = new PointColour(0.9f, 0.0f, 0.0f);
    PointColour r = new PointColour(1.0f, 0.0f, 0.0f);*/
    PointColour b_0 =  new PointColour(0.0f, 0.0f, 0.50f);
    PointColour b_1 =  new PointColour(0.0f, 0.0f, 0.60f);
    PointColour b_2 = new PointColour(0.0f, 0.0f, 0.65f);
    PointColour b_3 = new PointColour(0.0f, 0.0f, 0.70f);
    PointColour b_4 = new PointColour(0.0f, 0.0f, 0.75f);
    PointColour b_5 = new PointColour(0.0f, 0.0f, 0.80f);
    PointColour b_6 = new PointColour(0.0f, 0.0f, 0.85f);
    PointColour b_7 = new PointColour(0.0f, 0.0f, 0.90f);
    PointColour b_8 = new PointColour(0.0f, 0.0f, 0.95f);
    PointColour b = new PointColour(0.0f, 0.0f, 0.1f);
    PointColour y = new PointColour(0.5f, 0.0f, 0.5f);
    PointColour pg_0 = new PointColour(0.5f, 0.5f, 0.0f);
    PointColour pg_1 = new PointColour(0.0f, 0.5f, 0.0f);
    PointColour g_0 = new PointColour(0.7f, 0.7f, 0.0f);
    PointColour g_1 = new PointColour(0.8f, 0.8f, 0.0f);
    PointColour g_2 = new PointColour(0.9f, 0.9f, 0.0f);
    PointColour g_3 = new PointColour(1.0f, 1.0f, 0.0f);
    PointColour g_4 = new PointColour(0.0f, 0.7f, 0.0f);
    PointColour g_5 = new PointColour(0.0f, 0.8f, 0.0f);
    PointColour g_6 = new PointColour(0.0f, 1.0f, 0.0f);
    protected PointColour a_0 = new PointColour(1.0f, 0.5f, 0.7f);
    protected PointColour a_1 = new PointColour(0.7f, 0.7f, 0.5f);
    protected PointColour a_2 = new PointColour(0.8f, 0.8f, 0.5f);
    protected PointColour a_3 = new PointColour(0.9f, 0.9f, 0.5f);
    protected PointColour a_4 = new PointColour(1.0f, 1.0f, 0.5f);
    protected PointColour a_5 = new PointColour(0.0f, 0.7f, 0.5f);
    protected PointColour a_6 = new PointColour(0.0f, 0.8f, 0.5f);
    protected PointColour a_7 = new PointColour(0.0f, 1.0f, 0.5f);
    protected PointColour p_0 = new PointColour(0.9f, 0.60f, 0.60f);
    protected PointColour p_1 = new PointColour(0.9f, 0.65f, 0.65f);
    protected PointColour p_2 = new PointColour(0.9f, 0.7f, 0.7f);
    protected PointColour p_3 = new PointColour(0.9f, 0.75f, 0.75f);
    protected PointColour p_4 = new PointColour(0.9f, 0.8f, 0.8f);
    protected PointColour p_5 = new PointColour(0.85f, 0.85f, 0.85f);
    protected PointColour p_6 = new PointColour(0.9f, 0.9f, 0.9f);
    protected PointColour p_7 = new PointColour(0.95f, 0.95f, 0.95f);
    protected PointColour w = new PointColour(1.0f, 1.0f, 1.0f);
    
    public LandingIntensityColourArray(PointArray<PointI> points, LandingGroupArray lgroups) {
        super(points);
        this.ensureCapacity(points.size());
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
        
        
        System.out.println("Loading intensity landing colours");
        for (PointI point : points) {
            float r = 0.05f + point.getIntensity() / grad;
            float g = 0.05f + point.getIntensity() / grad;
            float b = 0.05f + point.getIntensity() / grad;
            PointColour dc = new PointColour(r, g, b);
            for (LandingGroup group : lgroups) {
                if (group.getPoints().contains(point)) {
                    this.add(getGroupColour(group,dc));
                }
            }
        }
        System.out.println("landing intensity colours loaded");
    }

    private PointColour getGroupColour(LandingGroup lGroup, PointColour dc) {
        PointColour ret;// = new PointColour(0.0f,0.0f,0.0f);;

        switch (lGroup.getType2()) {
            default:
                ret = dc;
                break;
            //UNSORTED, BAD
            case 0:
            case 1:
                   ret = dc;
                   break;
            //Part Risky
            case 2:
                ret = b_0;
                break;
            //Risky
            case 3:
                switch (lGroup.getType1()) {
                    default:
                        ret = b_1;
                        break;
                    case 5:
                        ret = b_2;
                        break;
                    case 6:
                        ret = b_3;
                        break;
                    case 7:
                        ret = b_4;
                        break;
                    case 8:
                        ret = b_5;
                        break;
                    case 9:
                        ret = b_6;
                        break;
                    case 10:
                        ret = b_7;
                        break;
                    case 11:
                        ret = b_8;
                        break;
                    case 12:
                        ret = b;
                        break;
                }
                break;
            case 4:
                ret = y;
                break;
            //Part Good
            case 6:
                switch (lGroup.getType1()) {
                    default:
                        ret = pg_0;
                        break;
                    case 9:
                        ret = pg_1;
                        break;
                    case 10:
                        ret = pg_1;
                        break;
                    case 11:
                        ret = pg_1;
                        break;
                    case 12:
                        ret = pg_1;
                        break;
                }
                break;
            //Good
            case 7:
                switch (lGroup.getType1()) {
                    default:
                        ret = g_0;
                        break;
                    case 5:
                        ret = g_1;
                        break;
                    case 6:
                        ret = g_1;
                        break;
                    case 7:
                        ret = g_2;
                        break;
                    case 8:
                        ret = g_3;
                        break;
                    case 9:
                        ret = g_4;
                        break;
                    case 10:
                        ret = g_5;
                        break;
                    case 11:
                        ret = g_5;
                        break;
                    case 12:
                        ret = g_6;
                        break;
                }
                break;
            //APROX
            case 8:
                ret = a_0;
                break;
            //Good AProx
            case 9:
                switch (lGroup.getType1()) {
                    default:
                        ret = a_1;
                        break;
                    case 5:
                        ret = a_2;
                        break;
                    case 6:
                        ret = a_2;
                        break;
                    case 7:
                        ret = a_3;
                        break;
                    case 8:
                        ret = a_4;
                        break;
                    case 9:
                        ret = a_5;
                        break;
                    case 10:
                        ret = a_6;
                        break;
                    case 11:
                        ret = a_6;
                        break;
                    case 12:
                        ret = a_7;
                        break;
                }
                break;
            //PERFECT AProx
            case 10:
                switch (lGroup.getType1()) {
                    default:
                        ret = p_0;
                        break;
                    case 5:
                        ret = p_1;
                        break;
                    case 6:
                        ret = p_2;
                        break;
                    case 7:
                        ret = p_3;
                        break;
                    case 8:
                        ret = p_4;
                        break;
                    case 9:
                        ret = p_5;
                        break;
                    case 10:
                        ret = p_6;
                        break;
                    case 11:
                        ret = p_7;
                        break;
                    case 12:
                        ret = w;
                        break;
                }
                break;
        }
        return ret;
    }
    
}
