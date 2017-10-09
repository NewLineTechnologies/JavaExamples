public class CalculationInputData {

    private int rowIndex;
    private int columnIndex;
    private double[] row;
    private double[] column;
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

    public double[] getRow() {
        return row;
    }

    public void setRow(double[] row) {
        this.row = row;
    }

    public double[] getColumn() {
        return column;
    }

    public void setColumn(double[] column) {
        this.column = column;
    }

    public boolean isLastElement() {
        return lastElement;
    }

    public void setLastElement(boolean lastElement) {
        this.lastElement = lastElement;
    }
}
