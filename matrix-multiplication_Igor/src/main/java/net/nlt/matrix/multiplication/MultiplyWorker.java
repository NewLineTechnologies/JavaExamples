package net.nlt.matrix.multiplication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;

import static java.lang.System.currentTimeMillis;

public class MultiplyWorker implements Callable<Long> {

    private static final Logger LOG = LoggerFactory.getLogger(MultiplyWorker.class);

    private SquareMatrix a;
    private SquareMatrix b;
    private SquareMatrix result;

    private int fromRow;
    private int toRow;


    public MultiplyWorker(SquareMatrix a, SquareMatrix b, SquareMatrix result, int fromRow, int toRow) {
        this.a = a;
        this.b = b;
        this.result = result;

        this.fromRow = fromRow;
        this.toRow = toRow;
    }

    @Override
    public Long call() throws Exception {
        long start = currentTimeMillis();

        for (int i = fromRow; i < toRow; i++) {
            for (int j = 0; j < result.size(); j++) {
                double value = produceRowToColumn(a.getRow(i), b.getColumn(j));
                result.set(value, i, j);
            }
        }

        long end = currentTimeMillis();

        long took = end - start;
        LOG.debug(": rows [{}..{}] calculated in {} ms", (fromRow + 1), toRow, took);
        return took;
    }

    private double produceRowToColumn(double[] row, double[] col) {
        int size = row.length;

        double result = 0;

        for (int i = 0; i < size; i++) {
            result += row[i] * col[i];
        }

        return result;
    }
}
