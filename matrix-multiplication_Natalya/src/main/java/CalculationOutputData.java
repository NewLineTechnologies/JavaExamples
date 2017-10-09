public class CalculationOutputData {

    private int rowIndex;
    private int columnIndex;
    private double element;
    private boolean lastElement;

    public int getRowIndex() {
        return rowIndex;
    }

    public void setRowIndex(int rowIndex) {
        this.rowIndex = rowIndex;
    }

    public int getColumnIndex() {
        return columnIndex;
    }

    public void setColumnIndex(int columnIndex) {
        this.columnIndex = columnIndex;
    }

    public double getElement() {
        return element;
    }

    public void setElement(double element) {
        this.element = element;
    }

    public boolean isLastElement() {
        return lastElement;
    }

    public void setLastElement(boolean lastElement) {
        this.lastElement = lastElement;
    }
}
