/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Olivia.generic;

import Olivia.core.InputReader;
import Olivia.core.data.Point3D_id;
import Olivia.core.render.colours.PointColour;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 *
 * @author oscar.garcia
 */
public class GenericInputReader extends InputReader<GenericVisualisationManager> {

    public void readFromFiles(String filePath, GenericVisualisationManager visualisationManager, int decimation) throws FileNotFoundException, IOException {
        int decimation_counter = 0;
        int columns_read;
        int field=0;
        openFile(filePath);
        String delimiter = getDelimiter();
        
        String line;
        String[] cols;
        line = buffer.readLine();
        GenericPointArray<Point3D_id> points = readHead(line);        
        line = buffer.readLine();
        while (line != null) {
            if(decimation_counter==0){
                decimation_counter=decimation;
                cols = line.split(delimiter);
                points.add(new Point3D_id(
                    Double.parseDouble(cols[0]),
                    Double.parseDouble(cols[1]),
                    Double.parseDouble(cols[2])
                ));
                numPointsRead++;
                columns_read=3;
                field=0;
                while(field<points.numberOfFields){
                    switch(points.getType(field)){
                        case GenericPointArray.STRING : 
                            points.addValue(field, cols[columns_read]);
                            columns_read++;
                            break;
                        case GenericPointArray.INT :
                            points.addValue(field, Integer.parseInt(cols[columns_read]));
                            columns_read++;
                            break;
                        case GenericPointArray.FLOAT :
                            points.addValue(field, Float.parseFloat(cols[columns_read]));
                            columns_read++;
                            break;
                        case GenericPointArray.DOUBLE :
                            points.addValue(field, Double.parseDouble(cols[columns_read]));
                            columns_read++;
                            break;
                        case GenericPointArray.COLOUR :
                            points.addValue(field, new PointColour(
                                                        Float.parseFloat(cols[columns_read]),
                                                        Float.parseFloat(cols[columns_read+1]),
                                                        Float.parseFloat(cols[columns_read+2])
                                                    )
                                    );
                            columns_read +=3;
                            break;
                        case GenericPointArray.COLOUR_INT :
                            points.addValue(field, new PointColour(
                                                        Integer.parseInt(cols[columns_read]),
                                                        Integer.parseInt(cols[columns_read+1]),
                                                        Integer.parseInt(cols[columns_read+2])
                                                    )
                                    );
                            columns_read +=3;
                            break;
                        case GenericPointArray.POINT :
                            points.addValue(field, new Point3D_id(
                                                        Double.parseDouble(cols[columns_read]),
                                                        Double.parseDouble(cols[columns_read+1]),
                                                        Double.parseDouble(cols[columns_read+2])
                                                    )
                                    );
                            columns_read +=3;
                            break;
                        case GenericPointArray.UNKOWN :
                            points.addValue(field, cols[columns_read]);
                            columns_read++;
                            break;
                    }
                    field++;
                }        
            }else{
                decimation_counter--;
            }
            line = buffer.readLine();
            }
        visualisationManager.setPointCloud(points);
    }
    
    protected GenericPointArray<Point3D_id> readHead(String line) throws IOException{
        GenericPointArray<Point3D_id> points;
        String[] cols = line.split(getDelimiter());
        int numberOfValues = Integer.parseInt(cols[0]);
        int numberOfColumns = Integer.parseInt(cols[1]);
        if(numberOfColumns<3) throw new IOException("Need at least 3 columns, for points");
        if(cols.length!=(numberOfValues*2+3)) throw new IOException("Declared values and HEAD do not match");
        if(GenericPointArray.getType(cols[2])!=GenericPointArray.POINT) throw new IOException("First 3 columns must be the points");
        points = new GenericPointArray<>();
        for(int i=3;i<numberOfValues*2+3;i=i+2){
                points.addField(GenericPointArray.getType(cols[i]), cols[i+1]);
        }
        if(numberOfValues!=points.getNumberOfFields()) throw new IOException("Declared number of fields and those calculated using the types do not match"); //POINT are not counted as fields
        if(numberOfColumns!=points.getNumberOfColumns()) throw new IOException("Declared number of columns and those calculated using the types do not match");
        return points;
    }

    @Override
    public void readFromFiles(String filePath, GenericVisualisationManager visualisationManager) throws FileNotFoundException, IOException {
        readFromFiles(filePath,visualisationManager,1);
    }
    
}
