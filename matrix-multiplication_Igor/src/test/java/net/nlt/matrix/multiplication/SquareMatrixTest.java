package net.nlt.matrix.multiplication;

import org.junit.Before;
import org.junit.Test;

public class SquareMatrixTest {

    private MatrixMath mm;

    @Before
    public void setup() throws Exception {
        mm = new ConcurrentMatrixMath();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testValidateNotSquareMatrix() throws Exception {
        double[][] notSquareMatrix = {
                {1},
                {0, 1},
                {1, 0, 1}
        };

        mm.matmul(notSquareMatrix, notSquareMatrix);
    }
}