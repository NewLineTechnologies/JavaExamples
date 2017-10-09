import java.util.Random;
import java.util.concurrent.BlockingQueue;

public class DataReader implements Runnable {
    private int matrixDimension;
    private BlockingQueue<CalculationInputData> inputQueue;

    public DataReader(int n, BlockingQueue<CalculationInputData> inputQueue) {
        this.matrixDimension = n;
        this.inputQueue = inputQueue;
    }

    public void run() {
        try {
            System.out.println(String.format("Thread %s started reading data", Thread.currentThread().getName()));
            // Use these values to see predetermined input and output values
            double[][] firstMatrix = getFirstMatrix();
            double[][] secondMatrix = getSecondMatrix();

            // Use these values for complete randomness :-)
//            double[][] firstMatrix = generateMatrix();
//            double[][] secondMatrix = generateMatrix();
            for (int i = 0; i < matrixDimension; i++) {
                for (int j = 0; j < matrixDimension; j++) {
                    double[] column = new double[matrixDimension];
                    for (int k = 0; k < matrixDimension; k++) {
                        column[k] = secondMatrix[k][j];
                    }

                    CalculationInputData input = new CalculationInputData();
                    input.setRowIndex(i);
                    input.setColumnIndex(j);
                    input.setRow(firstMatrix[i]);
                    input.setColumn(column);
                    inputQueue.put(input);

                    // This line serves to illustrate concurrency. Comment it if you're using big matrix with random values
                    Thread.sleep(150);
                }
            }

            System.out.println(String.format("Thread %s finished reading data", Thread.currentThread().getName()));
            CalculationInputData lastElement = new CalculationInputData();
            lastElement.setLastElement(true);
            inputQueue.put(lastElement);

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private double[][] getFirstMatrix() {
        return new double[][]{
                {2d, 2d, 2d, 2d, 2d},
                {2d, 2d, 2d, 2d, 2d},
                {2d, 2d, 2d, 2d, 2d},
                {2d, 2d, 2d, 2d, 2d},
                {2d, 2d, 2d, 2d, 2d}};
    }

    private double[][] getSecondMatrix() {
        return new double[][]{
                {3d, 3d, 3d, 3d, 3d},
                {3d, 3d, 3d, 3d, 3d},
                {3d, 3d, 3d, 3d, 3d},
                {3d, 3d, 3d, 3d, 3d},
                {3d, 3d, 3d, 3d, 3d}};
    }

    private double[][] generateMatrix() {
        double[][] matrix = new double[matrixDimension][matrixDimension];
        Random rand = new Random();
        for (int i = 0; i < matrixDimension; i++) {
            for (int j = 0; j < matrixDimension; j++) {
                matrix[i][j] = rand.nextInt(100);
            }
        }

        return matrix;
    }
}
