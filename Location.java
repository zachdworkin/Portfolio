//Pieces of this code were taken from focus in Larn Jaca in N Games
//Original code is getDistanceTo()

/** A location (with row and column) on the board. */
public class Location {
    /** @see #Location(int, int) */
    private final int column;

    /** @see #Location(int, int) */
    private final int row;

    /** Row is zero-based from top, column zero-based from top. */
    public Location(int row, int column) {
        this.row = row;
        this.column = column;
    }

    /**
     * Returns the column of this location.
     *
     * @see #Location(int, int)
     */
    public int getColumn() {
        return column;
    }

    /**
     * Returns the distance from this to that, or NOT_ON_SAME_LINE if this and
     * that are not on the same horizontal or vertical line.
     */
    public int getDistanceTo(Location that) {
        if(that.row==row){
            return Math.abs(that.column-this.column);
        }
        return Math.abs((13-that.column)+(12-this.column));
    }

    /**
     * Returns the row of this location.
     *
     * @see #Location(int, int)
     */
    public int getRow() {
        return row;
    }

}