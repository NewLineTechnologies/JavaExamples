package com.nlt;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * Matrix class
 * Created by taras on 10/6/2017.
 *
 * Actually, the approach of building matrix multiplication algorithm depends of several specific requirements.
 * Some of them:
 * 1. Are we going to calculate one big multiplication or we need many iterative multiplications?
 * In other words, what is better - calculate multiple multiplications in parallel or calculate one multiplication in parallel?
 *
 * 2. Matrices size matters. For smaller matrices the number of threads should be other then for bigger ones.
 * Number of threads which equals to number of CPU cores is not optimal for all matrix sizes.
 * This example can be used for comparison of various thread pools with different thread numbers.
 *
 * 3. Memory limitations.
 * This example is implemented on the assumption that two matrices of given size should fit in memory.
 * Multiplication result is written into the first matrix because of need to decrease memory usage.
 *
 * If this limitation is not actual, we can implement multiplication in other manner.
 *
 * 3. Matrix size limitation.
 * This example is not designed to process matrices that are not fit in memory.
 * RAM size of 256MB is able to contain two matrices of about 40,000 x 40,000
 * Multiplication of matrices of such size can not be provided by single server architecture with resonable time.
 * This example is related with single machine system only.
 * It can perform with up to 10,000 x 10,000 matrices on conventional hardware.
 *
 */
public class HighSpeedMatrix {

    /**
     * Constructor
     * @param name
     * the name of the matrix, may be empty
     */
    public HighSpeedMatrix(String name) {
        this.name = name;
    }

    /**
     * Setup of timeout for multithreaded multiplication in seconds,
     * may be changed if needed
     */
    public long TIMEOUT = 60;

    /**
     * private firelds
     */
    private double[][] matrix = new double[0][0];
    private int size = 0;
    private String name = "NoName";

    /**
     * Getters and setters
     * @return
     */
    public double[][] getMatrix() {
        return this.matrix;
    }

    public void setMatrix(double[][] m) {
        this.matrix = m;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSize() {
        return this.size;
    }

    public void setSize(int size) {
        this.size = size;
        matrix = new double[size][size];
    }

    /**
     * Generates the matrix with random values adn given size
     * @param size
     * @throws Exception
     */
    public void generateMatrix(int size) throws Exception {
        this.size = size;
        matrix = new double[size][size];
        for (int i = 0; i < size - 1; i++) {
            for (int j = 0; j < size - 1; j++) {
                matrix[i][j] = Math.random() * 1000;
            }
        }
        return;
    }

    /**
     * Generates the matrix with values and given size
     * @param size
     * @throws Exception
     */
    public void generateMatrix2(int size) throws Exception {
        this.size = size;
        matrix = new double[size][size];
        for (int i = 0; i < size - 1; i++) {
            for (int j = 0; j < size - 1; j++) {
                matrix[i][j] = i * size + j;
            }
        }
        return;
    }

    /**
     * Prints entire matrix
     */
    public void print() {
        print(this.size);
    }

    /**
     * Prints the part n x n of the matrix
     * @param n
     */
    public void print(int n) {
        System.out.println(this.name + ":");
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - 1; j++) {
                System.out.print(String.format("%9.3f", matrix[i][j]) + " ");
            }
            System.out.println();
        }
    }

    /**
     * Single threaded multiplication
     * @param bMatrix
     * @throws Exception
     */
    public void multiply(HighSpeedMatrix bMatrix) throws Exception {
        //this.setSize(aMatrix.getSize());
        //double[][] aM = aMatrix.getMatrix();
        double[][] bM = bMatrix.getMatrix();

        for (int i = 0; i < size - 1; i++) {
            for (int j = 0; j < size - 1; j++) {
                double sum = 0;
                for (int k = 0; k < size - 1; k++) {
                    sum = sum + matrix[i][k] * bM[k][j];
                }
                matrix[i][j] = sum;
            }
        }
        return;

    }

    /**
     * Multiple threaded matrix multiplication
     * @param bMatrix
     * @throws Exception
     */
    public void multiply2(HighSpeedMatrix bMatrix) throws Exception {
        int CPU_COUNT = 1;
        CPU_COUNT = Runtime.getRuntime().availableProcessors();
        System.out.println("Cores = " + CPU_COUNT);
        ExecutorService pool = Executors.newFixedThreadPool(CPU_COUNT);

        //for some matrix sizes this works better
        //ExecutorService pool = Executors.newFixedThreadPool(this.getSize());

        Collection<CalcTask> taskCollecion = new ArrayList<>( );
        IntStream.range(0, this.getSize()).forEach(i -> taskCollecion.add(new CalcTask(i ,this, bMatrix)));

        try {
            pool.invokeAll(taskCollecion, TIMEOUT, TimeUnit.SECONDS);
        } catch (Exception e) {
            throw e;
        }finally{
            pool.shutdown();
        }

        pool.awaitTermination(TIMEOUT, TimeUnit.SECONDS );
    }

    /**
     *Matrix row multiplication
     * Result is written to the same row of current matrix
     *
     * @param row - row number
     * @param bMatrix
     * @throws Exception
     */
    public void multiplyRow(int row, HighSpeedMatrix bMatrix) throws Exception {
        double[][] bM = bMatrix.getMatrix();

        for (int j = 0; j < size - 1; j++) {
            double sum = 0;
            for (int k = 0; k < size - 1; k++) {
                sum = sum + matrix[row][k] * bM[k][j];
            }
            matrix[row][j] = sum;
        }
        return;

    }

}
