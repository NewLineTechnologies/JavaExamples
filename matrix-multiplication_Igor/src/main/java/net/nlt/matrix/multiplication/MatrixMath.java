package net.nlt.matrix.multiplication;

public interface MatrixMath {

    SquareMatrix matmul(double[][] a, double[][] b);

    SquareMatrix matmul(SquareMatrix a, SquareMatrix b);
}
