import java.util.concurrent.BlockingQueue;

public class DataWriter implements Runnable {
    private int matrixDimension;
    private BlockingQueue<CalculationOutputData> queue;
    private double[][] result;

    public DataWriter(int n, BlockingQueue<CalculationOutputData> queue) {
        this.matrixDimension = n;
        this.queue = queue;
        result = new double[n][n];
    }

    public void run() {
        try {

            System.out.println(String.format("Thread %s started writing data", Thread.currentThread().getName()));
            while (true) {
                CalculationOutputData data = queue.take();
                if (data.isLastElement()) {
                    break;
                }

                result[data.getRowIndex()][data.getColumnIndex()] = data.getElement();

                // This line serves to illustrate concurrency. Comment it if you're using big matrix with random values
                Thread.sleep(175);
            }

            for (int i = 0; i < matrixDimension; i++) {
                for (int j = 0; j < matrixDimension; j++) {
                    System.out.print(result[i][j] + " ");
                }

                System.out.println();
            }
            System.out.println(String.format("Thread %s finished writing data", Thread.currentThread().getName()));
        }
        catch(InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
