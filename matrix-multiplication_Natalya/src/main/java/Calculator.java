import java.util.Random;
import java.util.concurrent.BlockingQueue;

public class Calculator implements Runnable {

    private BlockingQueue<CalculationOutputData> queue;
    private CalculationInputData data;

    public Calculator(BlockingQueue<CalculationOutputData> queue, CalculationInputData data) {
        this.queue = queue;
        this.data = data;
    }

    public void run() {
        double element = 0;
        System.out.println(String.format("Thread %s started calculating row %s and column %s", Thread.currentThread().getName(), data.getRowIndex(), data.getColumnIndex()));
        for (int i = 0; i < data.getRow().length; i++)
        {
            element += data.getRow()[i] * data.getColumn()[i];
        }

        CalculationOutputData result = new CalculationOutputData();
        result.setRowIndex(data.getRowIndex());
        result.setColumnIndex(data.getColumnIndex());
        result.setElement(element);

        try {
            // These lines serve to illustrate concurrency. Comment them if you're using big matrix with random values
            Random r = new Random();
            int value = r.nextInt(500);
            Thread.sleep(value);

            System.out.println(String.format("Thread %s finished calculating row %s and column %s", Thread.currentThread().getName(), data.getRowIndex(), data.getColumnIndex()));

            queue.put(result);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
