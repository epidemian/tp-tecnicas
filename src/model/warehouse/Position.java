package model.warehouse;

/**
 * Almost a "struct" xD
 */
public class Position {

	public int row;
	public int col;
	
	public Position(int row, int col) {
		this.row = row;
		this.col = col;
	}
	
	public Position() {
		this(0, 0);
	}
}
