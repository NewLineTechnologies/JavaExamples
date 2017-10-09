package com.nlt;

import static org.junit.Assert.*;

/**
 * Created by taras on 10/9/2017.
 */
public class HighSpeedMatrixTest {
    @org.junit.Test
    public void getMatrix() throws Exception {

    }

    @org.junit.Test
    public void setMatrix() throws Exception {

    }

    @org.junit.Test
    public void getName() throws Exception {

    }

    @org.junit.Test
    public void setName() throws Exception {

    }

    @org.junit.Test
    public void getSize() throws Exception {

    }

    @org.junit.Test
    public void setSize() throws Exception {

    }

    @org.junit.Test
    public void generateMatrix() throws Exception {

    }

    @org.junit.Test
    public void generateMatrix2() throws Exception {

    }

 private double[][] getData(int size){
     double[][] data = new double[size][size];
     for(int i=0; i<size; i++){
         for (int j=0; j< size; j++){
             data[i][j]=i*10 + j;
         }
     }
    return data;
 }

    private double[][] getDataConst(int size, double value){
        double[][] data = new double[size][size];
        for(int i=0; i<size; i++){
            for (int j=0; j< size; j++){
                data[i][j]=value;
            }
        }
        return data;
    }

    @org.junit.Test
    public void print() throws Exception {
        HighSpeedMatrix aMatrix = new HighSpeedMatrix("Test");
        int size = 5;
        double[][]data  = getData(size);
        try {
            aMatrix.setMatrix(data);
            aMatrix.print();
        }catch(Exception e){
            fail("Exception: " + e.getMessage());
        }
        System.out.println(size + " x " + size + " matrix should been printed");
    }

    @org.junit.Test
    public void print1() throws Exception {
        HighSpeedMatrix aMatrix = new HighSpeedMatrix("Test");
        int size = 5;
        int printSize = 3;
        double[][]data  = getData(size);
        try {
            aMatrix.setMatrix(data);
            aMatrix.print(printSize);
        }catch(Exception e){
            fail("Exception: " + e.getMessage());
        }
        System.out.println(printSize + " x " + printSize + " matrix should been printed");
    }

    @org.junit.Test
    public void multiply() throws Exception {
        HighSpeedMatrix aMatrix = new HighSpeedMatrix("A");
        HighSpeedMatrix bMatrix = new HighSpeedMatrix("B");
        int size = 100;
        double[][]aData  = getDataConst(size, 2);
        double[][]bData  = getDataConst(size, 3);
        try {
            aMatrix.setMatrix(aData);
            //aMatrix.print();
            bMatrix.setMatrix(bData);
            aMatrix.multiply(bMatrix);
        }catch(Exception e){
            fail("Exception: " + e.getMessage());
        }
        //aMatrix.print();
    }

    @org.junit.Test
    public void multiply2() throws Exception {
        HighSpeedMatrix aMatrix = new HighSpeedMatrix("A");
        HighSpeedMatrix bMatrix = new HighSpeedMatrix("B");
        int size = 100;
        double[][]aData  = getDataConst(size, 2);
        double[][]bData  = getDataConst(size, 3);
        try {
            aMatrix.setMatrix(aData);
            //aMatrix.print();
            bMatrix.setMatrix(bData);
            aMatrix.multiply2(bMatrix);
        }catch(Exception e){
            fail("Exception: " + e.getMessage());
        }
        //aMatrix.print();
    }

    @org.junit.Test
    public void multiplyRow() throws Exception {

    }

}