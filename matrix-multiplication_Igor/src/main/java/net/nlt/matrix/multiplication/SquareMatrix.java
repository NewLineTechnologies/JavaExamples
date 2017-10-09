package net.nlt.matrix.multiplication;

import java.util.Random;

public class SquareMatrix {

    private final double[][] data;
    private final int size;

    public SquareMatrix(double[][] data) {
        if (!isMatrixSquare(data)) {
            throw new IllegalArgumentException("Matrix is not square");
        }
        this.data = data;
        this.size = data.length;
    }

    public double get(int row, int col) {
        return data[row][col];
    }

    public void set(double value, int row, int col) {
        data[row][col] = value;
    }

    public double[] getRow(int index) {
        return data[index];
    }

    public double[] getColumn(int colIndex) {
        double[] result = new double[size];

        for (int i = 0; i < size; i++) {
            result[i] = data[i][colIndex];
        }

        return result;
    }

    public int size() {
        return size;
    }

    public double[][] toArray() {
        return data;
    }

    public static SquareMatrix zeros(int size) {
        return new SquareMatrix(new double[size][size]);
    }

    public static SquareMatrix rand(int size) {
        double[][] result = new double[size][size];

        Random r = new Random();

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                result[i][j] = r.nextGaussian();
            }
        }

        return new SquareMatrix(result);
    }

    private boolean isMatrixSquare(double[][] matrix) {
        int rowsCount = matrix.length;

        for (double[] row : matrix) {
            if (row.length != rowsCount) {
                return false;
            }
        }

        return true;
    }
}
