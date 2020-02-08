package surroundpack;

import javax.swing.*;

public class Cell extends JButton {

	/* Player that occupies the cell */
	private int playerNumber;

	/* Property of the cell used to determine cell color and risk level */
	private int propertyColor;

	/* Row the current cell is located in */
	private int row;

	/* Column the current cell is located in */
	private int col;


	/**
	 *   Constructor for the cells that fill the game board.
	 *   Sets the player number of the cell to the passed value
	 *   and sets the cells property color to the default
	 *   value (1)
	 *
	 * @param playerNumber The number to be set as the cell's player number
	 */

	public Cell(int playerNumber) {
		super();
		this.playerNumber = playerNumber;
		this.propertyColor = 1;
	}


	/**
	 * 	Returns the player number of the selected cell.
	 *
	 * @return	The player number of the selected cell
	 */

	public int getPlayerNumber() {
		return playerNumber;
	}


	/**
	 * 	Sets the cells property color to the passed value
	 *
	 * @param propertyColor The value that will be set as the cell's property color
	 */

	public void setPropertyColor(int propertyColor) {
		this.propertyColor = propertyColor;

	}


	/**
	 * 	Returns the property color of the selected cell.
	 *
	 * @return	The property color of the selected cell
	 */

	public int getPropertyColor() {
		return propertyColor;
	}


	/**
	 * 	Sets the value to row the cell is located in
	 *
	 * @param row The row the cell is located in
	 */

	public void setRow(int row) {
		this.row = row;
	}


	/**
	 * 	Returns the row number the cell is located in
	 *
	 * @return The current row the cell is located in
	 */

	public int getRow() {
		return row;
	}


	/**
	 * 	Sets the value to column the cell is located in
	 *
	 * @param col The row the cell is located in
	 */

	public void setCol(int col) {
		this.col = col;
	}


	/**
	 * 	Returns the column number the cell is located in
	 *
	 * @return The current column the cell is located in
	 */

	public int getCol() {
		return col;
	}

}

