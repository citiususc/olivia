package Olivia.standard;

import Olivia.core.data.PointArray;
import Olivia.extended.PointStandard;
import Olivia.core.InputReader;
import com.google.common.base.Charsets;
import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableList;
import java.io.FileNotFoundException;
import java.io.IOException;
import com.google.common.io.CharSource;
import com.google.common.io.Files;
import com.google.common.io.LineReader;
import com.google.common.io.MoreFiles;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.StandardOpenOption;

/**
 * This class manages the reading of 
 * 
 * @author oscar.garcia
 */
public class StandardInputReader extends InputReader<StandardVisualisationManager> {

    public StandardPointArray<PointStandard> readPointCloudFile(String filePath, int decimation) throws FileNotFoundException, IOException {

        int decimation_counter = 0;
        openFile(filePath);
        String delimiter = getDelimiter();
        StandardPointArray<PointStandard> points = new StandardPointArray<>();
        String line;
        String[] cols;
        line = buffer.readLine();
        cols = line.split(delimiter);
        switch(cols.length) {
            case 3 : System.out.println("Found 'X Y Z' file");
                     while (line != null) {
                         if(decimation_counter==0){
                            decimation_counter=decimation;
                            cols = line.split(delimiter);
                            points.add(new PointStandard(
                                    Double.parseDouble(cols[0]),
                                    Double.parseDouble(cols[1]),
                                    Double.parseDouble(cols[2])
                            ));
                            numPointsRead++;
                         }else{
                             decimation_counter--;
                         }
                            line = buffer.readLine();
                        }
                     break;
            case 4 : System.out.println("Found 'X Y Z I' file");
                     points.setHave_intensity(true);
                     while (line != null) {
                        if(decimation_counter==0){
                            decimation_counter=decimation;
                            cols = line.split(delimiter);
                            points.add(new PointStandard(
                                    Double.parseDouble(cols[0]),
                                    Double.parseDouble(cols[1]),
                                    Double.parseDouble(cols[2]),
                                    Float.parseFloat(cols[3]) //intensity
                            ));
                            numPointsRead++;
                        }else decimation_counter--;
                        line = buffer.readLine();
                    }
                     break;
            case 6 : System.out.println("Found 'X Y Z I rn nor' file");
                     points.setHave_intensity(true);
                     points.setHave_returns(true);
                     while (line != null) {
                        if(decimation_counter==0){
                            decimation_counter=decimation;
                            cols = line.split(delimiter);
                            points.add(new PointStandard(
                                    Double.parseDouble(cols[0]),
                                    Double.parseDouble(cols[1]),
                                    Double.parseDouble(cols[2]),
                                    Float.parseFloat(cols[3]), //intensity
                                    Integer.parseInt(cols[4]), //return number 
                                    Integer.parseInt(cols[5]) //number of returns
                            ));
                            numPointsRead++;
                        }else decimation_counter--;
                        line = buffer.readLine();
                    }
                     break;
            case 8 : System.out.println("Found 'X Y Z I rn nor dir edge' file");
                     points.setHave_intensity(true);
                     points.setHave_returns(true);
                     points.setHave_scanlines(true);
                     while (line != null) {
                        if(decimation_counter==0){
                            decimation_counter=decimation;
                            cols = line.split(delimiter);
                            points.add(new PointStandard(
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
                        }else decimation_counter--;
                        line = buffer.readLine();
                    }
                     break;
            case 9 : System.out.println("Found 'X Y Z I rn nor dir edge class' file");
                     points.setHave_intensity(true);
                     points.setHave_returns(true);
                     points.setHave_scanlines(true);
                     points.setHave_classification(true);
                     while (line != null) {
                        if(decimation_counter==0){
                            decimation_counter=decimation;
                            cols = line.split(delimiter);
                            points.add(new PointStandard(
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
                        }else decimation_counter--;
                        line = buffer.readLine();
                        }
                     break;
            case 10 : System.out.println("Found 'X Y Z I rn nor dir edge class ???' file");
                     points.setHave_intensity(true);
                     points.setHave_returns(true);
                     points.setHave_scanlines(true);
                     points.setHave_classification(true);
                     while (line != null) {
                        if(decimation_counter==0){
                            decimation_counter=decimation;
                            cols = line.split(delimiter);
                            points.add(new PointStandard(
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
                        }else decimation_counter--;
                        line = buffer.readLine();
                        }
                     break;
            case 12 : System.out.println("Found 'X Y Z I rn nor dir edge class R G B' file");
                     points.setHave_intensity(true);
                     points.setHave_returns(true);
                     points.setHave_scanlines(true);
                     points.setHave_classification(true);
                     points.setHave_RGB(true);
                      while (line != null) {
                        if(decimation_counter==0){
                            decimation_counter=decimation;
                            cols = line.split(delimiter);
                            points.add(new PointStandard(
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
                        }else decimation_counter--;
                        line = buffer.readLine();
                        }
                     break;
            default : throw new IOException("Data file format not recognised");
            } 
        
        
        
        System.out.println("Read " + numPointsRead + " points");
        //points.centerPoints();
        
        buffer.close();
        reader.close();
        
        return points;
    }
    
    
//    public StandardPointArray<PointStandard> readPointCloudFileG(File file) throws FileNotFoundException, IOException {
//
//        CharSource source = Files.asCharSource(file, Charsets.UTF_8);
//        String firstLine = source.readFirstLine();
//        String delimiter = getDelimiter(firstLine);
//        String[] cols;
//        cols = firstLine.split(delimiter);
//        StandardPointArray<PointStandard> points = new StandardPointArray<>();
//        switch(cols.length) {
//            case 3 : System.out.println("Found 'X Y Z' file");
//                     break;
//            case 4 : System.out.println("Found 'X Y Z I' file");
//                     points.setHave_intensity(true);
//                     break;
//            case 6 : System.out.println("Found 'X Y Z I rn nor' file");
//                     points.setHave_intensity(true);
//                     points.setHave_returns(true);
//                     break;
//            case 8 : System.out.println("Found 'X Y Z I rn nor dir edge' file");
//                     points.setHave_intensity(true);
//                     points.setHave_returns(true);
//                     points.setHave_scanlines(true);
//                     break;
//            case 9 : System.out.println("Found 'X Y Z I rn nor dir edge class' file");
//                     points.setHave_intensity(true);
//                     points.setHave_returns(true);
//                     points.setHave_scanlines(true);
//                     points.setHave_classification(true);
//                     break;
//            case 12 : System.out.println("Found 'X Y Z I rn nor dir edge class R G B' file");
//                     points.setHave_intensity(true);
//                     points.setHave_returns(true);
//                     points.setHave_scanlines(true);
//                     points.setHave_classification(true);
//                     points.setHave_RGB(true);
//                     break;
//            default : throw new IOException("Data file format not recognised");
//            } 
//        StandardLineConsumer lineConsumer = new StandardLineConsumer(points, delimiter);
//        
//        source.forEachLine(lineConsumer);
//        
//        System.out.println("Read " + numPointsRead + " points");
//        points.centerPoints();
//  
//        return points;
//    }
    
//    public StandardPointArray<PointStandard> readPointCloudFile(File file) throws FileNotFoundException, IOException {
//        
//        reader = new FileReader(file);
//        buffer = new BufferedReader(reader);
//        System.out.println("Reading " + file.getName() + "...");
//        String firstLine = buffer.readLine();
//        buffer.close();
//        reader.close();
//        StandardPointArray<PointStandard> points = new StandardPointArray<>();
//        String delimiter = getDelimiter(firstLine);
//        String[] cols;
//        cols = firstLine.split(delimiter);
//        switch(cols.length) {
//            case 3 : System.out.println("Found 'X Y Z' file");
//                     break;
//            case 4 : System.out.println("Found 'X Y Z I' file");
//                     points.setHave_intensity(true);
//                     break;
//            case 6 : System.out.println("Found 'X Y Z I rn nor' file");
//                     points.setHave_intensity(true);
//                     points.setHave_returns(true);
//                     break;
//            case 8 : System.out.println("Found 'X Y Z I rn nor dir edge' file");
//                     points.setHave_intensity(true);
//                     points.setHave_returns(true);
//                     points.setHave_scanlines(true);
//                     break;
//            case 9 : System.out.println("Found 'X Y Z I rn nor dir edge class' file");
//                     points.setHave_intensity(true);
//                     points.setHave_returns(true);
//                     points.setHave_scanlines(true);
//                     points.setHave_classification(true);
//                     break;
//            case 12 : System.out.println("Found 'X Y Z I rn nor dir edge class R G B' file");
//                     points.setHave_intensity(true);
//                     points.setHave_returns(true);
//                     points.setHave_scanlines(true);
//                     points.setHave_classification(true);
//                     points.setHave_RGB(true);
//                     break;
//            default : throw new IOException("Data file format not recognised");
//            } 
//        StandardLineConsumer lineConsumer = new StandardLineConsumer(points, delimiter);
//        
//        java.nio.file.Files.lines(file.toPath()).forEach(lineConsumer);
//        
//        System.out.println("Read " + numPointsRead + " points");
//        points.centerPoints();
//        
//        return points;
//    }
    

    @Override
    public void readFromFiles(String filePath, StandardVisualisationManager visualisationM) throws FileNotFoundException, IOException {
        long timeS, timeE;
        timeS = System.nanoTime();
        StandardPointArray<PointStandard> points = readPointCloudFile(filePath,1);
        timeE = System.nanoTime();
        System.out.println("Time to read file " + ((timeE-timeS)/1000000000.0));
        visualisationM.setPointCloud(points);
    }
    
    public void readFromFiles(String filePath, StandardVisualisationManager visualisationM, int decimation) throws FileNotFoundException, IOException {
        long timeS, timeE;
        timeS = System.nanoTime();
        StandardPointArray<PointStandard> points = readPointCloudFile(filePath,decimation);
        timeE = System.nanoTime();
        System.out.println("Time to read file " + ((timeE-timeS)/1000000000.0));
        visualisationM.setPointCloud(points);
    }
    
//    public void readFromFiles(File file, StandardVisualisation visualisation) throws FileNotFoundException, IOException {
//        long timeS, timeE;
//        timeS = System.nanoTime();
//        StandardPointArray<PointStandard> points = readPointCloudFileG(file);
//        //PointArray<PointStandard> points = readPointCloudFile(file);
//        timeE = System.nanoTime();
//        System.out.println("Time to read file " + ((timeE-timeS)/1000000000.0));
//        visualisation.setPointCloud(points);
//    }
}
