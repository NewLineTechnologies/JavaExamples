package com.nlt;

import java.util.concurrent.Callable;

/**
 * Task which multiplies the matrix to the row of other
 */

public class CalcTask implements Callable< Boolean > {
    private int row;
    private HighSpeedMatrix aMatrix;
    private HighSpeedMatrix bMatrix;

    public CalcTask (int row, HighSpeedMatrix aMatrix, HighSpeedMatrix bMatrix){
        this.row = row;
        this.aMatrix = aMatrix;
        this.bMatrix = bMatrix;
    }

    @Override
    public Boolean call() {
        try {
            aMatrix.multiplyRow(this.row, bMatrix);
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

}
