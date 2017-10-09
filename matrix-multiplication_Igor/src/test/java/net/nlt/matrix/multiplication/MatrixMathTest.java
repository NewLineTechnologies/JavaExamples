package net.nlt.matrix.multiplication;


import org.junit.Before;
import org.junit.Test;

import static net.nlt.matrix.multiplication.SquareMatrix.rand;
import static org.assertj.core.api.Assertions.assertThat;

public class MatrixMathTest {

    private MatrixMath mm;

    @Before
    public void setup() throws Exception {
        mm = new ConcurrentMatrixMath();
    }

    @Test
    public void testMatmul() throws Exception {
        double[][] matrixA = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };

        double[][] matrixB = {
                {1, 0, 1},
                {0, 1, 0},
                {1, 0, 1}
        };

        double[][] expectedResult = {
                {4, 2, 4},
                {10, 5, 10},
                {16, 8, 16}
        };

        assertThat(mm.matmul(matrixA, matrixB).toArray()).isEqualTo(expectedResult);
    }

    @Test
    public void testBigMatrix() throws Exception {
        SquareMatrix a = rand(98);
        SquareMatrix b = rand(98);

        SquareMatrix c = mm.matmul(a, b);

        assertThat(c).isNotNull();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testValidateMatrixDifferentSize() throws Exception {
        double[][] a = {
                {1, 1},
                {1, 1}
        };

        double[][] b = {
                {1, 1, 1},
                {1, 1, 1},
                {1, 1, 1}
        };

        mm.matmul(a, b);
    }
}