package cinema.entity;

public class Seat {

    private Long row;
    private Long column;

    public Seat() {
    }

    public Seat(Long row, Long column) {
        this.row = row;
        this.column = column;
    }

    public Long getRow() {
        return row;
    }

    public void setRow(Long row) {
        this.row = row;
    }

    public Long getColumn() {
        return column;
    }

    public void setColumn(Long column) {
        this.column = column;
    }
}
