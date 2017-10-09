package net.nlt.matrix.multiplication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ForkJoinPool;

import static java.lang.Math.max;
import static java.lang.Math.min;
import static java.lang.String.format;
import static net.nlt.matrix.multiplication.CommonUtils.nextMultiple;
import static net.nlt.matrix.multiplication.SquareMatrix.zeros;

public class ConcurrentMatrixMath implements MatrixMath {

    private static final Logger LOG = LoggerFactory.getLogger(ConcurrentMatrixMath.class);

    private ForkJoinPool pool;
    private int parallelism;

    public ConcurrentMatrixMath() {
        this.pool = new ForkJoinPool();
        this.parallelism = pool.getParallelism();
    }

    public ConcurrentMatrixMath(int parallelism) {
        this.parallelism = parallelism;
        this.pool = new ForkJoinPool(parallelism);
    }

    @Override
    public SquareMatrix matmul(double[][] a, double[][] b) {
        return matmul(new SquareMatrix(a), new SquareMatrix(b));
    }

    @Override
    public SquareMatrix matmul(SquareMatrix a, SquareMatrix b) {
        validate(a, b);
        int size = a.size();

        SquareMatrix result = zeros(size);

        LOG.debug("Starting calculation, parallelism = {}", parallelism);
        pool.invokeAll(split(a, b, result, size));

        return result;
    }

    private Collection<Callable<Long>> split(SquareMatrix a, SquareMatrix b, SquareMatrix result, int size) {
        int perWorker = calcPerWorker(size);

        List<Callable<Long>> tasks = new ArrayList<>();

        int lo = 0;
        int hi = perWorker;
        for (int i = 0; i < size; i += perWorker) {
            MultiplyWorker task = new MultiplyWorker(a, b, result, lo, hi);
            tasks.add(task);

            lo = hi;
            hi = min(hi + perWorker, size); //min() - avoid ArrayOutOfBoundException
        }

        return tasks;
    }

    private static void validate(SquareMatrix a, SquareMatrix b) {
        if (a.size() != b.size()) {
            throw new IllegalArgumentException(format("Size must be equal (a = %d, b = %d)", a.size(), b.size()));
        }
    }

    private int calcPerWorker(int size) {
        return max(nextMultiple(size, parallelism) / parallelism, 1); //max() - to avoid infinite loop when parallelism >= size
    }
}
