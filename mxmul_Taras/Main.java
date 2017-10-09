package com.nlt;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;

public class Main {

    public static void main(String[] args) {

        final int matrixSize = 1000;
        long startTime;
        long endTime;

        HighSpeedMatrix aMatrix = new HighSpeedMatrix("A");
        HighSpeedMatrix bMatrix = new HighSpeedMatrix("B");

        try {
            aMatrix.generateMatrix2(matrixSize);

        }catch(Exception e){
            e.printStackTrace();
        }

        try {
            bMatrix.generateMatrix2(matrixSize);

        }catch(Exception e){
            e.printStackTrace();
        }

        startTime = PerfMsr.getTime();
        try {
           aMatrix.multiply(bMatrix);
        }catch (Exception e){
            e.printStackTrace();
        }
        endTime = PerfMsr.getTime();
        System.out.println("Control Time: " + PerfMsr.toSeconds(endTime-startTime));

        aMatrix.print(10);

        //Apache matrix multiplication
//        try {
//            aMatrix.generateMatrix2(matrixSize);
//
//        }catch(Exception e){
//            e.printStackTrace();
//        }
//
//        RealMatrix apacheAMatrix = new Array2DRowRealMatrix(aMatrix.getMatrix());
//        RealMatrix apacheBMatrix = new Array2DRowRealMatrix(bMatrix.getMatrix());
//        endTime = PerfMsr.getTime();
//        RealMatrix apacheCMatrix = apacheAMatrix.multiply(apacheBMatrix);
//        endTime = PerfMsr.getTime();
//        System.out.println("Apache Time: " + PerfMsr.toSeconds(endTime-startTime));

        //The same with multithreaded multiplication
        try {
            aMatrix.generateMatrix2(matrixSize);

        }catch(Exception e){
            e.printStackTrace();
        }


        startTime= PerfMsr.getTime();

        try {
            aMatrix.multiply2(bMatrix);
        }catch (Exception e){
            e.printStackTrace();
        }

        endTime = PerfMsr.getTime();
        System.out.println("Final Time: " + PerfMsr.toSeconds(endTime-startTime));

        aMatrix.print(10);


    }
}
