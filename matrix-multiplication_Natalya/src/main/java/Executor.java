import java.util.concurrent.*;

public class Executor {

    public void run() throws InterruptedException {
        int inputCapacity = 3;
        int outputCapacity = 2;

        // Use 5 to see predetermined input and output values (both input matrix are hardcoded in DataReader class)
        // If you need big and random data - use whatever number you want along with generateMatrix() method in DataReader class
        int matrixDimension = 5;

        // Use one processor for reading data (implying that that's a long process), one for writing data (long process as well)
        // and use the rest for calculation
        // If reading/writing data is fast (not using files etc.) - use all processors for calculation
        int threadsCount = Math.max(Runtime.getRuntime().availableProcessors() - 2, 1);

        System.out.println(String.format("Thread %s started main calculation, available processors for calculation - %s", Thread.currentThread().getName(), threadsCount));

        BlockingQueue<CalculationInputData> inputQueue = new LinkedBlockingQueue<>(inputCapacity);
        BlockingQueue<CalculationOutputData> outputQueue = new LinkedBlockingQueue<>(outputCapacity);

        DataReader reader = new DataReader(matrixDimension, inputQueue);
        DataWriter writer = new DataWriter(matrixDimension, outputQueue);

        Thread readerThread = new Thread(reader);
        Thread writerThread = new Thread(writer);

        readerThread.start();
        writerThread.start();

        ExecutorService executor = Executors.newFixedThreadPool(threadsCount);

        while (true) {
            CalculationInputData input = inputQueue.take();
            if(input.isLastElement()) {
                break;
            }

            Calculator calculator = new Calculator(outputQueue, input);
            executor.execute(calculator);
        }

        executor.shutdown();
        executor.awaitTermination(matrixDimension*matrixDimension, TimeUnit.SECONDS);

        // Signal queue to finish
        CalculationOutputData lastElement = new CalculationOutputData();
        lastElement.setLastElement(true);
        outputQueue.put(lastElement);

        System.out.println(String.format("Thread %s finished main calculation", Thread.currentThread().getName()));
    }
}
