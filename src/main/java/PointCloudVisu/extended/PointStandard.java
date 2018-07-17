/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PointCloudVisu.extended;

/**
 * This class is for standard LAS points. It uses more memory than its antecessors and may have values with zero, it is a wildcard class, but should be used only if necessary
 * @author oscar.garcia
 */
public class PointStandard extends PointS{
    
    final static protected int COLOUR_BITS = 16;
    final static protected int MAX_COLOUR_VALUE = 1 << COLOUR_BITS -1;
    
    public static final int UNCLASSIFIED = 0;
    public static final int UNKNOWN = 1;
    public static final int GROUND = 2;
    public static final int LOW_VEG = 3;
    public static final int MEDIUM_VEG = 4;
    public static final int HIGH_VEG = 5;
    public static final int BUILDING = 6;
    public static final int LOW_POINT = 7;
    public static final int RESERVED = 8;
    public static final int WATER = 9;
    public static final int ROAD = 11;
    public static final int PARKING = 21;  // Field not in the LAS standard
    public static final int OTHER = 1337;
    
    protected int classification;
    protected int r;
    protected int g;
    protected int b;

    public PointStandard(double x, double y, double z) {
        super(x, y, z, 1, 0, 0, 0, 0); //Set intensity to 1 so points are visible
        this.classification = UNKNOWN;
        this.r = MAX_COLOUR_VALUE; //white
        this.g = MAX_COLOUR_VALUE;
        this.b = MAX_COLOUR_VALUE;
    }
    
    public PointStandard(double x, double y, double z, float I) {
        super(x, y, z, I, 1, 1, 0, 0); //Number of returns 1 and return number 1, 
        this.classification = UNKNOWN;
        this.r = MAX_COLOUR_VALUE; //white
        this.g = MAX_COLOUR_VALUE;
        this.b = MAX_COLOUR_VALUE;
    }
    
    public PointStandard(double x, double y, double z, float I, int rn, int nor) {
        super(x, y, z, I, rn, nor, 0, 0); //Dir 0 and edge 0
        this.classification = UNKNOWN;
        this.r = MAX_COLOUR_VALUE; //white
        this.g = MAX_COLOUR_VALUE;
        this.b = MAX_COLOUR_VALUE;
    }
    
    public PointStandard(double x, double y, double z, float I, int rn, int nor, int dir, int edge) {
        super(x, y, z, I, rn, nor, dir, edge);
        this.classification = UNKNOWN;
        this.r = MAX_COLOUR_VALUE; //white
        this.g = MAX_COLOUR_VALUE;
        this.b = MAX_COLOUR_VALUE;
    }
    
    public PointStandard(double x, double y, double z, float I, int rn, int nor, int dir, int edge, int classification) {
        super(x, y, z, I, rn, nor, dir, edge);
        this.classification = classification;
        this.r = MAX_COLOUR_VALUE; //white
        this.g = MAX_COLOUR_VALUE;
        this.b = MAX_COLOUR_VALUE;
    }
    
    public PointStandard(double x, double y, double z, float I, int rn, int nor, int dir, int edge, int classification, int r, int g, int b) {
        super(x, y, z, I, rn, nor, dir, edge);
        this.classification = classification;
        this.r = r;
        this.g = g;
        this.b = b;
    }
    
    public int getClassification() {
        return classification;
    }

    public void setClassification(int c) {
        this.classification = c;
    }
    
    public int getR() {
        return r;
    }

    public void setR(int r) {
        this.r = r;
    }

    public int getG() {
        return g;
    }

    public void setG(int g) {
        this.g = g;
    }

    public int getB() {
        return b;
    }

    public void setB(int b) {
        this.b = b;
    }
    
    public float getR_asFloat(){
        return r*1.0f /MAX_COLOUR_VALUE;
    }
    
    public float getG_asFloat(){
        return g*1.0f /MAX_COLOUR_VALUE;
    }
    
    public float getB_asFloat(){
        return b*1.0f /MAX_COLOUR_VALUE;
    }
    
}
