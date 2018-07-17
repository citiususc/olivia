/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PointCloudVisu.standard;

import PointCloudVisu.core.data.PointArray;
import PointCloudVisu.extended.PointStandard;
import java.io.IOException;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author oscar.garcia
 */
public class StandardLineConsumer implements Consumer<String>{
    
    protected PointArray<PointStandard> points;
    protected int numPointsRead;
    protected String delimiter;
    
    public StandardLineConsumer(PointArray<PointStandard> points, String delimiter) throws IOException{
        this.points = points;
        numPointsRead = 0;
        this.delimiter = delimiter;
    }
    
    public int getNumPointsRead(){
        return numPointsRead;
    }

    @Override
    public void accept(String t) {
        String[] cols;
        cols = t.split(delimiter);
        switch(cols.length) {
            case 3 : points.add(new PointStandard(
                                    Double.parseDouble(cols[0]),
                                    Double.parseDouble(cols[1]),
                                    Double.parseDouble(cols[2])
                            ));
                            numPointsRead++;
                     break;
            case 4 : points.add(new PointStandard(
                                    Double.parseDouble(cols[0]),
                                    Double.parseDouble(cols[1]),
                                    Double.parseDouble(cols[2]),
                                    Float.parseFloat(cols[3]) //intensity
                            ));
                            numPointsRead++;
                    break;
            case 6 :  points.add(new PointStandard(
                                    Double.parseDouble(cols[0]),
                                    Double.parseDouble(cols[1]),
                                    Double.parseDouble(cols[2]),
                                    Float.parseFloat(cols[3]), //intensity
                                    Integer.parseInt(cols[4]), //return number 
                                    Integer.parseInt(cols[5]) //number of returns
                            ));
                            numPointsRead++;
                     break;
            case 8 : points.add(new PointStandard(
                                    Double.parseDouble(cols[0]),
                                    Double.parseDouble(cols[1]),
                                    Double.parseDouble(cols[2]),
                                    Float.parseFloat(cols[3]), //intensity
                                    Integer.parseInt(cols[4]), //return number 
                                    Integer.parseInt(cols[5]), //number of returns
                                    Integer.parseInt(cols[6]), //direction
                                    Integer.parseInt(cols[7]) //edge
                            ));
                            numPointsRead++;
                     break;
            case 9 : points.add(new PointStandard(
                                    Double.parseDouble(cols[0]),
                                    Double.parseDouble(cols[1]),
                                    Double.parseDouble(cols[2]),
                                    Float.parseFloat(cols[3]), //intensity
                                    Integer.parseInt(cols[4]), //return number 
                                    Integer.parseInt(cols[5]), //number of returns
                                    Integer.parseInt(cols[6]), //direction
                                    Integer.parseInt(cols[7]), //edge
                                    Integer.parseInt(cols[8]) //classification
                            ));
                            numPointsRead++;
                     break;
            case 12 : points.add(new PointStandard(
                                    Double.parseDouble(cols[0]),
                                    Double.parseDouble(cols[1]),
                                    Double.parseDouble(cols[2]),
                                    Float.parseFloat(cols[3]), //intensity
                                    Integer.parseInt(cols[4]), //return number 
                                    Integer.parseInt(cols[5]), //number of returns
                                    Integer.parseInt(cols[6]), //direction
                                    Integer.parseInt(cols[7]), //edge
                                    Integer.parseInt(cols[8]), //classification
                                    Integer.parseInt(cols[9]), //R
                                    Integer.parseInt(cols[10]), //G
                                    Integer.parseInt(cols[11]) //B
                            ));
                            numPointsRead++;
                     break;
            default : System.out.println("Line not recognised");
                     break;
        }
    }
    
    
}
