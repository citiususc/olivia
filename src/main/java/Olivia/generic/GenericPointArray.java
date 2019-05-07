/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Olivia.generic;

import Olivia.core.data.Point3D_id;
import Olivia.core.data.PointArray;
import Olivia.core.render.colours.PointColour;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author oscar.garcia
 */
public class GenericPointArray<P extends Point3D_id> extends PointArray<P>  {
    public static final int STRING = 1;
    public static final int INT = 2;
    public static final int FLOAT = 3;
    public static final int DOUBLE = 4;
    public static final int COLOUR = 5;
    public static final int POINT = 6;
    public static final int UNKOWN = 7;
    
    public static final ArrayList<String> SUPPORTED_TYPES = new ArrayList<String>() {{
        add("STRING");
        add("INT");
        add("FLOAT");
        add("DOUBLE");
        add("COLOUR");
        add("POINT");
    }};
    
    public static int getType(String name){
        switch(name){
            case "STRING" : return STRING;
            case "INT" : return INT;
            case "FLOAT" : return FLOAT;
            case "DOUBLE" : return DOUBLE;
            case "COLOUR" : return COLOUR;
            case "POINT" : return POINT;
            case "UNKOWN" : return UNKOWN;
            default : return UNKOWN;
        }
    }
    

    public static String getTypeName(int type){
        switch(type){
            case STRING : return "STRING";
            case INT : return "INT";
            case FLOAT : return "FLOAT";
            case DOUBLE : return "DOUBLE";
            case COLOUR : return "COLOUR";
            case POINT : return "POINT";
            case UNKOWN : return "UNKOWN";
            default : return "UNKOWN";
        }
    }
    
    public static int getRequiredColumns(String name){
        switch(name){
            case "STRING" : return 1;
            case "INT" : return 1;
            case "FLOAT" : return 1;
            case "DOUBLE" : return 1;
            case "COLOUR" : return 3;
            case "POINT" : return 3;
            case "UNKOWN" : return 1;
            default : return 1;
        }
    }
    
    public static int getRequiredColumns(int type){
        switch(type){
            case STRING : return 1;
            case INT : return 1;
            case FLOAT : return 1;
            case DOUBLE : return 1;
            case COLOUR : return 3;
            case POINT : return 3;
            case UNKOWN : return 1;
            default : return 1;
        }
    }
    
    
    protected int numberOfFields;
    protected int numberOfColumns;
    protected ArrayList<Integer> types;
    protected ArrayList<String> names;
    protected ArrayList<ArrayList> values;
    
    public GenericPointArray() {
        super();
        numberOfFields = 0;
        numberOfColumns = 0;
        types = new ArrayList<>(numberOfFields);
        names = new ArrayList<>(numberOfFields);
        values = new ArrayList<>(numberOfFields);
    }

    public int getNumberOfFields() {
        return numberOfFields;
    }

    /*
    public ArrayList getTypes() {
        return types;
    }

    public ArrayList getNames() {
        return names;
    }
    */
    
    public List<String> getNames(){
        return Collections.unmodifiableList(names);
    }
    
    public int getType(int field){
        return types.get(field);
    }

    public int getNumberOfColumns() {
        return numberOfColumns;
    }
    
    public boolean addField(int type, String name){
        if(this.size()>0){
            Olivia.core.Olivia.textOutputter.println("Already started adding points, fields cannot be modified");
            return false;
        }
        types.add(type);
        names.add(name);
        values.add(new ArrayList());
        numberOfFields++;
        numberOfColumns += getRequiredColumns(type);
        Olivia.core.Olivia.textOutputter.println("Added field " + (numberOfFields-1) + " named " + name + " with type " + GenericPointArray.getTypeName(type));
        return true;
    }

    public ArrayList getFieldValues(int field){
        return values.get(field);
    }
    
    
    /*
    public ArrayList<ArrayList> getValues() {
        return values;
    }
    */
    
    protected boolean checkType(Object value, int type){
        switch(type){
            case STRING : return (value instanceof String);
            case INT : return (value instanceof Integer);
            case FLOAT : return (value instanceof Float);
            case DOUBLE : return (value instanceof Double);
            case COLOUR : return (value instanceof PointColour);
            case POINT : return (value instanceof Point3D_id);
            case UNKOWN : return false;
            default : return false;
        }
    }
    
    public boolean addValue(int field, Object value){
        if(checkType(value, types.get(field))){
            return values.get(field).add(value);
        }else{
            return false;
        }
    }
    
    
    
}
