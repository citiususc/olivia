/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Olivia.generic;

import java.util.ArrayList;

/**
 *
 * @author oscar.garcia
 */
public class TypedArrayList<O extends Object> extends ArrayList<O> {
    public static final int STRING = 1;
    public static final int INT = 2;
    public static final int FLOAT = 3;
    public static final int DOUBLE = 4;
    public static final int COLOUR = 5;
    public static final int UNKOWN = 6;
    
    protected int type;
    
    public TypedArrayList(int type){
        super();
        this.type = type;
    }

    public int getType() {
        return type;
    }
    
}
