package Utility;

public class Place {
    private int row;
    private int column;

    public Place(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public boolean hasSamePos(Place f){
        return column == f.getColumn() &&
                row == f.getRow();
    }

    @Override
    public String toString() {
        return  String.format("row: %d col: %d",row,column);
    }
}
