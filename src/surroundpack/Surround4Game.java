package surroundpack;

import java.util.ArrayList;

public class Surround4Game {

	/* 2D array of cells */
	private Cell[][] board;

	/* Current player of the game */
	private int player;

	/* Number of players in the current game */
	private int numPlayers;

	/* Player that goes first in the current game */
	private int firstPlayer;


	/**
	 *
	 *   This constructor creates a board of cells with the given size.
	 *   Sets the first player to player 1 and sets the number of players
	 *   to the predetermined amount.
	 *
	 * @param size How many rows and columns of cells the board will have
	 */

	public Surround4Game(int size) {
		//super();
		board = new Cell[size][size];
		this.player = 1;
		setNumPlayers(this.numPlayers);
	}


	/**
	 *
	 *   Default constructor for the game. Creates a 10x10 board of cells with two
	 *   players.
	 *
	 */

	public Surround4Game() {
		//super();
		board = new Cell[10][10];
		this.player = 1;
		this.numPlayers = 2;
	}


	/**
	 *   Resets the game board by setting each cell to null;
	 */

	public void reset() {
		//iterates through each cell on the board
		for (int r = 0; r < board.length; r++) {
			for (int c = 0; c < board.length; c++) {
				//sets the currently selected cell to null (empty)
				board[r][c] = null;
			}
		}
	}


	/**
	 *   Takes a row and column and returns the current cell that is at the intersection of
	 *   the passed row and column
	 *
	 * @param row The row of the board
	 * @param col The column of the board
	 * @return The cell located at the intersection of the passed row and column
	 */

	public Cell getCell(int row, int col) {
		return board[row][col];
	}


	/**
	 *   Returns the player whose turn it currently is
	 *
	 * @return The player whose turn it is currently
	 */

	public int getCurrentPlayer() {
		return player;
	}


	/**
	 *   Sets the passed player number as the current player as well as the player
	 *   who will take their turn first.
	 *
	 * @param player The number of the player to be set
	 * @throws IllegalArgumentException when the player number is below one or greater than the
	 * 		   total number of players currently in the game.
	 */

	public void setPlayer(int player) {
		//Was a valid player number passed?
		if (player < 1 || player > this.numPlayers) {
			//If not throw new exception
			throw new IllegalArgumentException();
		}
		//Sets the first player of the game
		this.firstPlayer = player;
		//sets the player to the passed parameter
		this.player = player;
	}


	/**
	 *   Sets the number of players in the game to the passed amount
	 *
	 * @param numPlayers The number of players the game will have
	 */

	public void setNumPlayers(int numPlayers) {
		this.numPlayers = numPlayers;
	}


	/**
	 *   Returns the number of players in the current game.
	 *
	 * @return Number of players in the current game.
	 */

	public int getNumPlayers() {
		return numPlayers;
	}


	/**
	 *   Returns the player who has the first turn of the game
	 *
	 * @return The player who take the first play of the game.
	 */

	public int getFirstPlayer() {
		return this.firstPlayer;
	}

	/**
	 *   Sets the player to the person whose turn is next.
	 *
	 * @return the next player whose turn it now is.
	 */

	public int nextPlayer() {
		//If the current player is the last person in the group, reset it back to the first player
		if (this.player >= this.numPlayers) {
			this.player = 1;
		} else {
			//Otherwise the turn is passed to the next player.
			this.player++;
		}
		//returns the player whose turn it now is
		return player;
	}


	/**
	 *   Checks the passed cell and returns true or false depending on if the cell
	 *   is null or not.
	 *
	 * @param row The row that contains the cell
	 * @param col The column that contains the cell
	 * @return True if the cell is null, false if not
	 */

	public boolean select(int row, int col) {
		//Is the cell null?
		if (board[row][col] == null ) {  //|| (cats() && board[row][col].getPlayeNumber() != player)) {
			//If so, creates a new cell with the current player number and sets it as the cell on the board
			//at the current location.
			Cell c = new Cell(player);
			c.setRow(row);
			c.setCol(col);
			board[row][col] = c;
			return true;
		}
		else 
			return false;
	}


	/**
	 *   Checks the tiles neighboring the top left corner tile for opponents
	 *
	 * @param row The row that contains the cell
	 * @param col The column that contains the cell
	 */

	public void checkTopLeftCornerOpponents(int row, int col) {
		// Counting variable used for keeping track of the number of adjacent tiles that are occupied.
		// Starts at one as that is the default property color used for determining the background color of the
		// tiles. This variable will be used to set the passed tiles property color to the correct number
		// depending on how many opponents surround the tile.
		int count = 1;
		//ArrayList that holds the playernumber, if any, of the adjacent tiles
		ArrayList<Integer> opp = new ArrayList<Integer>();

		//If the tile directly to the right is occupied, increment the count variable by one and
		//add the player number of that tile to the ArrayList opp.
		if (isRightNull(row, col)) {
			count++;
			opp.add(board[row][col + 1].getPlayerNumber());
		}
		//If the tile directly below is occupied, increment the count variable by one and
		//add the player number of that tile to the ArrayList opp.
		if (isDownNull(row, col)) {
			count++;
			opp.add(board[row + 1][col].getPlayerNumber());
		}
		// If the adjacent tiles are all surrounded by the same opponent, set that tile's property color to the
		// variable count.
		// Otherwise the property color will remain at it's default value (1, blue)
		if (sameOpponents(opp)) {
			board[row][col].setPropertyColor(count);
		} else {
			board[row][col].setPropertyColor(1);
		}
	}


	/**
	 *   Checks the tiles neighboring the top right corner tile for opponents
	 *
	 * @param row The row that contains the cell
	 * @param col The column that contains the cell
	 */

	public void checkTopRightCornerOpponents(int row, int col) {
		// This method follows the same procedure outlined above in the "checkTopLeftCornerForOpponents" method.
		// If you would like a detailed breakdown of what is happening please refer to that method.

		int count = 1;
		ArrayList<Integer> opp = new ArrayList<Integer>();

		if (isLeftNull(row, col)) {
			count++;
			opp.add(board[row][col - 1].getPlayerNumber());
		}
		if (isDownNull(row, col)) {
			count++;
			opp.add(board[row + 1][col].getPlayerNumber());
		}

		if (sameOpponents(opp)) {
			board[row][col].setPropertyColor(count);
		} else {
			board[row][col].setPropertyColor(1);
		}

	}


	/**
	 *   Checks the tiles neighboring the bottom right corner tile for opponents
	 *
	 * @param row The row that contains the cell
	 * @param col The column that contains the cell
	 */

	public void checkBottomRightOpponents(int row, int col) {
		// This method follows the same procedure outlined above in the "checkTopLeftCornerForOpponents" method.
		// If you would like a detailed breakdown of what is happening please refer to that method.

		int count = 1;
		ArrayList<Integer> opp = new ArrayList<Integer>();

		if (isLeftNull(row, col)) {
			count++;
			opp.add(board[row][col - 1].getPlayerNumber());
		}
		if (isUpNull(row, col)) {
			count++;
			opp.add(board[row - 1][col].getPlayerNumber());
		}

		if (sameOpponents(opp)) {
			board[row][col].setPropertyColor(count);
		} else {
			board[row][col].setPropertyColor(1);
		}

	}


	/**
	 *   Checks the tiles neighboring the bottom left corner tile for opponents
	 *
	 * @param row The row that contains the cell
	 * @param col The column that contains the cell
	 */

	public void checkBottomLeftOpponents(int row, int col) {
		// This method follows the same procedure outlined above in the "checkTopLeftCornerForOpponents" method.
		// If you would like a detailed breakdown of what is happening please refer to that method.

		int count = 1;
		ArrayList<Integer> opp = new ArrayList<Integer>();

		if (isRightNull(row, col)) {
			count++;
			opp.add(board[row][col + 1].getPlayerNumber());
		}
		if (isUpNull(row, col)) {
			count++;
			opp.add(board[row - 1][col].getPlayerNumber());
		}

		if (sameOpponents(opp)) {
			board[row][col].setPropertyColor(count);
		} else {
			board[row][col].setPropertyColor(1);
		}

	}


	/**
	 *   Checks the tiles neighboring a tile on the top row of the board, excluding the corner tiles, for opponents
	 *   returns 1 if the adjacent tiles all belong to the same opponent, -1 otherwise
	 *
	 * @param row The row that contains the cell
	 * @param col The column that contains the cell
	 * @return returns 1 if the adjacent tiles all belong to the same opponent, -1 otherwise
	 */

	public int checkTopBorderForOpponents(int row, int col) {
		// This method follows the same procedure outlined above in the "checkTopLeftCornerForOpponents" method with
		// the exception that this method also returns a value depending on if the property color was set to a value
		// other than default.
		// If you would like a detailed breakdown of what is happening please refer to that method.

		int count = 1;
		ArrayList<Integer> opp = new ArrayList<Integer>();

		if (isLeftNull(row, col)) {
			count++;
			opp.add(board[row][col - 1].getPlayerNumber());
		}
		if (isDownNull(row, col)) {
			count++;
			opp.add(board[row + 1][col].getPlayerNumber());
		}
		if (isRightNull(row, col)) {
			count++;
			opp.add(board[row][col + 1].getPlayerNumber());
		}
		if (sameOpponents(opp)) {
			board[row][col].setPropertyColor(count);
			return 1;
		} else {
			board[row][col].setPropertyColor(1);
			return -1;
		}

	}


	/**
	 *   Checks the tiles neighboring a tile on the far left column, excluding the corner tiles, for opponents
	 *   returns 1 if the adjacent tiles all belong to the same opponent, -1 otherwise
	 *
	 * @param row The row that contains the cell
	 * @param col The column that contains the cell
	 * @return returns 1 if the adjacent tiles all belong to the same opponent, -1 otherwise
	 */

	public int checkLeftBorderForOpponents(int row, int col) {
		// This method follows the same procedure outlined above in the "checkTopLeftCornerForOpponents" method with
		// the exception that this method also returns a value depending on if the property color was set to a value
		// other than default.
		// If you would like a detailed breakdown of what is happening please refer to that method.

		int count = 1;
		ArrayList<Integer> opp = new ArrayList<Integer>();

		if (isUpNull(row, col)) {
			count++;
			opp.add(board[row - 1][col].getPlayerNumber());
		}
		if (isDownNull(row, col)) {
			count++;
			opp.add(board[row + 1][col].getPlayerNumber());
		}
		if (isRightNull(row, col)) {
			count++;
			opp.add(board[row][col + 1].getPlayerNumber());
		}

		if (sameOpponents(opp)) {
			board[row][col].setPropertyColor(count);
			return 1;
		} else {
			board[row][col].setPropertyColor(1);
			return -1;
		}
	}


	/**
	 *   Checks the tiles neighboring a tile on the bottom row of the board, excluding the corner tiles, for opponents
	 *   returns 1 if the adjacent tiles all belong to the same opponent, -1 otherwise
	 *
	 * @param row The row that contains the cell
	 * @param col The column that contains the cell
	 * @return returns 1 if the adjacent tiles all belong to the same opponent, -1 otherwise
	 */

	public int checkBottomBorderForOpponents(int row, int col) {
		// This method follows the same procedure outlined above in the "checkTopLeftCornerForOpponents" method with
		// the exception that this method also returns a value depending on if the property color was set to a value
		// other than default.
		// If you would like a detailed breakdown of what is happening please refer to that method.

		int count = 1;
		ArrayList<Integer> opp = new ArrayList<Integer>();

		if (isUpNull(row, col)) {
			count++;
			opp.add(board[row - 1][col].getPlayerNumber());
		}
		if (isLeftNull(row, col)) {
			count++;
			opp.add(board[row][col - 1].getPlayerNumber());
		}
		if (isRightNull(row, col)) {
			count++;
			opp.add(board[row][col + 1].getPlayerNumber());
		}

		if (sameOpponents(opp)) {
			board[row][col].setPropertyColor(count);
			return 1;
		} else {
			board[row][col].setPropertyColor(1);
			return -1;
		}
	}


	/**
	 *   Checks the tiles neighboring a tile on the far right column, excluding the corner tiles, for opponents
	 *   returns 1 if the adjacent tiles all belong to the same opponent, -1 otherwise
	 *
	 * @param row The row that contains the cell
	 * @param col The column that contains the cell
	 * @return returns 1 if the adjacent tiles all belong to the same opponent, -1 otherwise
	 */

	public int checkRightBorderForOpponents(int row, int col) {
		// This method follows the same procedure outlined above in the "checkTopLeftCornerForOpponents" method with
		// the exception that this method also returns a value depending on if the property color was set to a value
		// other than default.
		// If you would like a detailed breakdown of what is happening please refer to that method.

		int count = 1;
		ArrayList<Integer> opp = new ArrayList<Integer>();

		if (isUpNull(row, col)) {
			count++;
			opp.add(board[row - 1][col].getPlayerNumber());
		}
		if (isLeftNull(row, col)) {
			count++;
			opp.add(board[row][col - 1].getPlayerNumber());
		}
		if (isDownNull(row, col)) {
			count++;
			opp.add(board[row + 1][col].getPlayerNumber());
		}

		if (sameOpponents(opp)) {
			board[row][col].setPropertyColor(count);
			return 1;
		} else {
			board[row][col].setPropertyColor(1);
			return -1;
		}
	}


	/**
	 *   Checks the tiles neighboring a tile in the middle of the board, excluding the corner and
	 *   perimeter tiles, for opponents
	 *   returns 1 if the adjacent tiles all belong to the same opponent, -1 otherwise
	 *
	 * @param row The row that contains the cell
	 * @param col The column that contains the cell
	 * @return returns 1 if the adjacent tiles all belong to the same opponent, -1 otherwise
	 */

	public int checkMiddleForOpponents(int row, int col) {
		// This method follows the same procedure outlined above in the "checkTopLeftCornerForOpponents" method with
		// the exception that this method also returns a value depending on if the property color was set to a value
		// other than default.
		// If you would like a detailed breakdown of what is happening please refer to that method.

		int count = 1;
		ArrayList<Integer> opp = new ArrayList<Integer>();

		if (isUpNull(row, col)) {
			count++;
			opp.add(board[row - 1][col].getPlayerNumber());
		}
		if (isLeftNull(row, col)) {
			count++;
			opp.add(board[row][col - 1].getPlayerNumber());
		}
		if (isDownNull(row, col)) {
			count++;
			opp.add(board[row + 1][col].getPlayerNumber());
		}
		if (isRightNull(row, col)) {
			count++;
			opp.add(board[row][col + 1].getPlayerNumber());
		}

		if (sameOpponents(opp)) {
			board[row][col].setPropertyColor(count);
			return 1;
		} else {
			board[row][col].setPropertyColor(1);
			return -1;
		}

	}


	/**
	 *   Used to check every tile on the board for adjacent opponents
	 *
	 * @param row The row that contains the tile to be checked
	 * @param col The column that contains the tile to be checked
	 */

	public void checkForOpponents(int row, int col) {
		if (row == 0 && col == 0 && board[0][0] != null) {
			checkTopLeftCornerOpponents(row, col);
		} else if (row == 0 && col == board.length - 1 && board[row][col] != null) {
			checkTopRightCornerOpponents(row, col);
		} else if (row == board.length - 1 && col == board.length - 1 && board[row][col] != null) {
			checkBottomRightOpponents(row, col);
		} else if (row == board.length - 1 && col == 0 && board[row][col] != null) {
			checkBottomLeftOpponents(row, col);
		} else if (row == 0 && board[row][col] != null) {
			checkTopBorderForOpponents(row, col);
		} else if (col == 0 && board[row][col] != null) {
			checkLeftBorderForOpponents(row, col);
		} else if (row == board.length - 1 && board[row][col] != null) {
			checkBottomBorderForOpponents(row, col);
		} else if (col == board.length - 1 && board[row][col] != null) {
			checkRightBorderForOpponents(row, col);
		} else if (row != 0 && col != 0 && col != board.length - 1 && row != board.length - 1 && board[row][col] != null) {
			checkMiddleForOpponents(row, col);
		}
	}


	/**
	 *   Checks if the tile to directly to the left of the passed tile is occupied
	 *
	 * @param row The row that contains the tile to be checked
	 * @param col The column that contains the tile to be checked
	 * @return true if the tile is occupied, false otherwise
	 */

	public boolean isLeftNull(int row, int col) {
		 return board[row][col] != null && board[row][col - 1] != null;
	}


	/**
	 *   Checks if the tile to directly to the right of the passed tile is occupied
	 *
	 * @param row The row that contains the tile to be checked
	 * @param col The column that contains the tile to be checked
	 * @return true if the tile is occupied, false otherwise
	 */

	public boolean isRightNull(int row, int col) {
		return board[row][col] != null && board[row][col + 1] != null;
	}


	/**
	 *   Checks if the tile to directly above the passed tile is occupied
	 *
	 * @param row The row that contains the tile to be checked
	 * @param col The column that contains the tile to be checked
	 * @return true if the tile is occupied, false otherwise
	 */

	public boolean isUpNull(int row, int col) {
		return board[row][col] != null && board[row - 1][col] != null;
	}


	/**
	 *   Checks if the tile to directly below the passed tile is occupied
	 *
	 * @param row The row that contains the tile to be checked
	 * @param col The column that contains the tile to be checked
	 * @return true if the tile is occupied, false otherwise
	 */

	public boolean isDownNull(int row, int col) {
		return board[row][col] != null && board[row + 1][col] != null;
	}


	/**
	 *   Checks if the values in the passed array are identical indicating that the
	 *   tile that was checked is surrounded by the same opponent.
	 *
	 * @return true if every item in the list is identical, false otherwise
	 */

	private boolean sameOpponents(ArrayList<Integer> opp) {
		for (Integer i : opp) {
			if (i != opp.get(0) || i == getCurrentPlayer()) {
				return false;
			}
		}
		return true;
	}


	/**
	 *   Checks for the number of occupied tiles surrounding the tile that is in the position
	 *   of the passed row and column.
	 *
	 * @param row The row that contains the tile to be checked
	 * @param col The column that contains the tile to be checked
	 * @return The number of adjacent tiles that are currently occupied.
	 */

	public int checkAIWin(int row, int col) {
		//variable used to keep track of the number of occupied adjacent tiles.
		int count = 0;
		if (row == 0 && col == 0) {
			if (isRightNull(row, col)) {
				count++;
			}
			if (isDownNull(row, col)) {
				count++;
			}
		} else if (row == 0 && col == board.length - 1) {
			if (isLeftNull(row, col)) {
				count++;
			}
			if (isDownNull(row, col)) {
				count++;
			}
		} else if (row == board.length - 1 && col == 0) {
			if (isRightNull(row, col)) {
				count++;
			}
			if (isUpNull(row, col)) {
				count++;
			}
		} else if (row == board.length - 1 && col == board.length - 1) {
			if (isLeftNull(row, col)) {
				count++;
			}
			if (isUpNull(row, col)) {
				count++;
			}
		} else if (row == 0) {
			if (isLeftNull(row, col)) {
				count++;
			}
			if (isRightNull(row, col)) {
				count++;
			}
			if (isDownNull(row, col)) {
				count++;
			}
		} else if (row == board.length - 1) {
			if (isLeftNull(row, col)) {
				count++;
			}
			if (isRightNull(row, col)) {
				count++;
			}
			if (isUpNull(row, col)) {
				count++;
			}
		} else if (col == 0) {
			if (isUpNull(row, col)) {
				count++;
			}
			if (isRightNull(row, col)) {
				count++;
			}
			if (isDownNull(row, col)) {
				count++;
			}
		} else if (col == board.length - 1) {
			if (isLeftNull(row, col)) {
				count++;
			}
			if (isUpNull(row, col)) {
				count++;
			}
			if (isDownNull(row, col)) {
				count++;
			}
		} else {
			if (isLeftNull(row, col)) {
				count++;
			}
			if (isRightNull(row, col)) {
				count++;
			}
			if (isDownNull(row, col)) {
				count++;
			}
			if (isUpNull(row, col)) {
				count++;
			}
		}

		return count;
	}


	/**
	 *   This is the main logic behind the computers AI. It determines if there is a winnable move or one that can block
	 *   the player from winning and makes its selection accordingly.
	 *
	 * @return Returns a cell containing the coordinates on the board that the computer has determined to be a suitable
	 * 		   next move
	 */

	public Cell AI() {
		// The first round of AI logic will be commented thoroughly to explain the logic behind it
		// All remaining moves/blocks follow the same logic with minor changes to determine the best
		// possible move for the computer to make.
		// Please refer to these comments to see how the rest of the logic works


		// First the computer will check for winnable moves, if an immediate win is not found it will attempt to
		// block the player if they are about to win in the following turn.
		// Looping through the board the first time
		for (int row = 0; row < board.length; row++) {
			for (int col = 0; col < board.length; col++) {

				// First the current player is checked, the computer will always be player 1
				if (getCurrentPlayer() == 1) {
					// If the current cell is occupied by the opposing player (Player 2), the computer will look
					// for a winning play. i.e. a tile with one open adjacent tile while the computer is occupying the
					// other adjacent tiles.
					if (getCell(row, col) != null && getCell(row, col).getPlayerNumber() == 2) {

						// First the AI will check if there is a winnable move to play

						// Top Left Corner Win AI
						if (row == 0 & col == 0) {
							// If the corner tile has one adjacent tile that is unoccupied.
							if (checkAIWin(row, col) == 1) {
								// First it will check if that unoccupied tile is to the right and that the occupied
								// tile below is is occupied by the computer.
								if (getCell(row + 1, col) == null && getCell(row, col + 1).getPlayerNumber() == 1) {
									// If so the the computer will check that it is a valid tile to make a play on
									// And if so, returns that tile.
									if (select(row + 1, col)) {
										return board[row + 1][col];
									}
								}
								// Otherwise it will check if the tile below is unoccupied and the tile to the right
								// is occupied by the computer. Again returning that cell if it is a valid play to make.
								else if (getCell(row, col + 1) == null && getCell(row + 1, col).getPlayerNumber() == 1) {
									if (select(row, col + 1)) {
										return board[row][col + 1];
									}
								}
							}
						}

						//The following three corners are check in the same manner as the first.
						//Top Right Corner Win AI
						else if (row == 0 & col == board.length - 1) {
							if (checkAIWin(row, col) == 1) {
								if (getCell(row + 1, col) == null && getCell(row, col - 1).getPlayerNumber() == 1) {
									if (select(row + 1, col)) {
										return board[row + 1][col];
									}
								} else if (getCell(row, col - 1) == null && getCell(row + 1, col).getPlayerNumber() == 1) {
									if (select(row, col - 1)) {
										return board[row][col - 1];
									}
								}
							}
						}

						//Bottom Left Corner Win AI
						else if (row == board.length - 1 & col == 0) {
							if (checkAIWin(row, col) == 1) {
								if (getCell(row - 1, col) == null && getCell(row, col + 1).getPlayerNumber() == 1) {
									if (select(row - 1, col)) {
										return board[row - 1][col];
									}
								} else if (getCell(row, col + 1) == null && getCell(row - 1, col).getPlayerNumber() == 1) {
									if (select(row, col + 1)) {
										return board[row][col + 1];
									}
								}
							}
						}

						//Bottom Right Corner Win AI
						else if (row == board.length - 1 & col == board.length - 1) {
							if (checkAIWin(row, col) == 1) {
								if (getCell(row - 1, col) == null && getCell(row, col - 1).getPlayerNumber() == 1) {
									if (select(row - 1, col)) {
										return board[row - 1][col];
									}
								} else if (getCell(row, col - 1) == null && getCell(row - 1, col).getPlayerNumber() == 1) {
									if (select(row, col - 1)) {
										return board[row][col - 1];
									}
								}
							}
						}
						// The perimeter tiles are check in the same manner as the corner tiles withe the exception that
						// a third tile has to be checked.
						//Top Border Win AI
						else if (row == 0) {
							if (checkAIWin(row, col) == 2) {
								if (getCell(row + 1, col) == null && getCell(row, col - 1).getPlayerNumber()
										== 1 && getCell(row, col + 1).getPlayerNumber() == 1) {
									if (select(row + 1, col)) {
										return board[row + 1][col];
									}
								} else if (getCell(row, col - 1) == null && getCell(row + 1, col).getPlayerNumber()
										== 1 && getCell(row, col + 1).getPlayerNumber() == 1)  {
									if (select(row, col - 1)) {
										return board[row][col - 1];
									}
								} else if (getCell(row, col + 1) == null && getCell(row + 1, col).getPlayerNumber()
										== 1 && getCell(row, col - 1).getPlayerNumber() == 1) {
									if (select(row, col + 1)) {
										return board[row][col + 1];
									}
								}
							}
						}

						//Right Border Win AI
						else if (col == board.length - 1) {
							if (checkAIWin(row, col) == 2) {
								if (getCell(row + 1, col) == null && getCell(row, col - 1).getPlayerNumber()
										== 1 && getCell(row - 1, col).getPlayerNumber() == 1) {
									if (select(row + 1, col)) {
										return board[row + 1][col];
									}
								} else if (getCell(row, col - 1) == null && getCell(row + 1, col).getPlayerNumber()
										== 1 && getCell(row - 1, col).getPlayerNumber() == 1)  {
									if (select(row, col - 1)) {
										return board[row][col - 1];
									}
								} else if (getCell(row - 1, col) == null && getCell(row + 1, col).getPlayerNumber()
										== 1 && getCell(row, col - 1).getPlayerNumber() == 1) {
									if (select(row - 1, col)) {
										return board[row - 1][col];
									}
								}
							}
						}

						//Bottom Border Win AI
						else if (row == board.length - 1) {
							if (checkAIWin(row, col) == 2) {
								if (getCell(row - 1, col) == null && getCell(row, col - 1).getPlayerNumber()
										== 1 && getCell(row, col + 1).getPlayerNumber() == 1) {
									if (select(row - 1, col)) {
										return board[row - 1][col];
									}
								} else if (getCell(row, col - 1) == null && getCell(row - 1, col).getPlayerNumber()
										== 1 && getCell(row, col + 1).getPlayerNumber() == 1)  {
									if (select(row, col - 1)) {
										return board[row][col - 1];
									}
								} else if (getCell(row, col + 1) == null && getCell(row - 1, col).getPlayerNumber()
										== 1 && getCell(row, col - 1).getPlayerNumber() == 1) {
									if (select(row, col + 1)) {
										return board[row][col + 1];
									}
								}
							}
						}

						//Left Border Win AI
						else if (col == 0) {
							if (checkAIWin(row, col) == 2) {
								if (getCell(row + 1, col) == null && getCell(row, col + 1).getPlayerNumber()
										== 1 && getCell(row - 1, col).getPlayerNumber() == 1) {
									if (select(row + 1, col)) {
										return board[row + 1][col];
									}
								} else if (getCell(row, col + 1) == null && getCell(row + 1, col).getPlayerNumber()
										== 1 && getCell(row - 1, col).getPlayerNumber() == 1)  {
									if (select(row, col + 1)) {
										return board[row][col + 1];
									}
								} else if (getCell(row - 1, col) == null && getCell(row + 1, col).getPlayerNumber()
										== 1 && getCell(row, col + 1).getPlayerNumber() == 1) {
									if (select(row - 1, col)) {
										return board[row - 1][col];
									}
								}
							}
						}
						// Tiles in the middle of the board follow the same logic as those in the corners
						// and those on the perimeter.
						//Middle Board Win AI
						else if (row != board.length - 1 && col != board.length - 1 && board[row][col] != null) {
							if (checkAIWin(row, col) == 3) {
								if (getCell(row + 1, col) == null && getCell(row, col + 1).getPlayerNumber()
										== 1 && getCell(row - 1, col).getPlayerNumber() == 1 &&
										getCell(row, col - 1).getPlayerNumber() == 1) {
									if (select(row + 1, col)) {
										return board[row + 1][col];
									}
								} else if (getCell(row, col + 1) == null && getCell(row + 1, col).getPlayerNumber()
										== 1 && getCell(row - 1, col).getPlayerNumber() == 1 &&
										getCell(row, col - 1).getPlayerNumber() == 1)  {
									if (select(row, col + 1)) {
										return board[row][col + 1];
									}
								} else if (getCell(row - 1, col) == null && getCell(row + 1, col).getPlayerNumber()
										== 1 && getCell(row, col + 1).getPlayerNumber() == 1 &&
										getCell(row, col - 1).getPlayerNumber() == 1) {
									if (select(row - 1, col)) {
										return board[row - 1][col];
									}
								} else if (getCell(row, col - 1) == null && getCell(row + 1, col).getPlayerNumber()
										== 1 && getCell(row, col + 1).getPlayerNumber() == 1 &&
										getCell(row - 1, col).getPlayerNumber() == 1) {
									if (select(row, col - 1)) {
										return board[row][col - 1];
									}
								}
							}
						}
					}

					// If no winnable move was found, it will next check for a winning move for the player in an attempt
					// to block it. These conditions are check the same as before with the exception being that
					// the computer uses the property color of the square to determine if it would be a suitable block
					// to make.
					// This is the case because the game resets the background at the start of every turn in order to
					// paint the tiles belonging to the current player. This in turn causes

					if (getCell(row, col) != null && getCell(row, col).getPlayerNumber() == 1) {
						//Top Left Corner AI Block
						if (row == 0 & col == 0) {
							if (board[row][col].getPropertyColor() == 2) {
								if (getCell(row + 1, col) == null && board[row][col].getPlayerNumber() == 1 &&
										getCell(row, col + 1).getPlayerNumber() == 2) {
									if (select(row + 1, col)) {
										return board[row + 1][col];
									}
								} else if (getCell(row, col + 1) == null && board[row][col].getPlayerNumber() == 1 &&
										getCell(row + 1, col).getPlayerNumber() == 2) {
									if (select(row, col + 1)) {
										return board[row][col + 1];
									}
								}
							}

						}
						//Top Right Corner AI Block
						else if (row == 0 & col == board.length - 1) {
							if (board[row][col].getPropertyColor() == 2) {
								if (getCell(row + 1, col) == null && board[row][col].getPlayerNumber() == 1 &&
										getCell(row, col - 1).getPlayerNumber() == 2) {
									if (select(row + 1, col)) {
										return board[row + 1][col];
									}
								} else if (getCell(row, col - 1) == null && board[row][col].getPlayerNumber() == 1 &&
										getCell(row + 1, col).getPlayerNumber() == 2) {
									if (select(row, col - 1)) {
										return board[row][col - 1];
									}
								}
							}

						}
						//Bottom Left Corner AI Block
						else if (row == board.length - 1 & col == 0) {
							if (board[row][col].getPropertyColor() == 2) {
								if (getCell(row - 1, col) == null && board[row][col].getPlayerNumber() == 1 &&
										getCell(row, col + 1).getPlayerNumber() == 2) {
									if (select(row - 1, col)) {
										return board[row - 1][col];
									}
								} else if (getCell(row, col + 1) == null && board[row][col].getPlayerNumber() == 1 &&
										getCell(row - 1, col).getPlayerNumber() == 2) {
									if (select(row, col + 1)) {
										return board[row][col + 1];
									}
								}
							}

						}

						//Bottom Right Corner AI Block
						else if (row == board.length - 1 & col == board.length - 1) {
							if (board[row][col].getPropertyColor() == 2) {
								if (getCell(row - 1, col) == null && board[row][col].getPlayerNumber() == 1 &&
										getCell(row, col - 1).getPlayerNumber() == 2) {
									if (select(row - 1, col)) {
										return board[row - 1][col];
									}
								} else if (getCell(row, col - 1) == null && board[row][col].getPlayerNumber() == 1 &&
										getCell(row - 1, col).getPlayerNumber() == 2) {
									if (select(row, col - 1)) {
										return board[row][col - 1];
									}
								}
							}

						}
						//Top Border AI Block
						else if (row == 0) {
							if (board[row][col].getPropertyColor() == 3) {
								if (getCell(row + 1, col) == null && getCell(row, col - 1).getPlayerNumber() == 2
										&& getCell(row, col + 1).getPlayerNumber() == 2 && board[row][col].getPlayerNumber() == 1) {
									if (select(row + 1, col)) {
										return board[row + 1][col];
									}
								} else if (getCell(row, col - 1) == null && getCell(row + 1, col).getPlayerNumber() == 2
										&& getCell(row, col + 1).getPlayerNumber() == 2 && board[row][col].getPlayerNumber() == 1)  {
									if (select(row, col - 1)) {
										return board[row][col - 1];
									}
								} else if (getCell(row, col + 1) == null && getCell(row + 1, col).getPlayerNumber() == 2
										&& getCell(row, col - 1).getPlayerNumber() == 2 && board[row][col].getPlayerNumber() == 1) {
									if (select(row, col + 1)) {
										return board[row][col + 1];
									}
								}
							}

						}
						//Right Border AI Block
						else if (col == board.length - 1) {
							if (board[row][col].getPropertyColor() == 3) {
								if (getCell(row + 1, col) == null && getCell(row, col - 1).getPlayerNumber()
										== 2 && getCell(row - 1, col).getPlayerNumber() == 2 && board[row][col].getPlayerNumber() == 1) {
									if (select(row + 1, col)) {
										return board[row + 1][col];
									}
								} else if (getCell(row, col - 1) == null && getCell(row + 1, col).getPlayerNumber()
										== 2 && getCell(row - 1, col).getPlayerNumber() == 2 && board[row][col].getPlayerNumber() == 1)  {
									if (select(row, col - 1)) {
										return board[row][col - 1];
									}
								} else if (getCell(row - 1, col) == null && getCell(row + 1, col).getPlayerNumber()
										== 2 && getCell(row, col - 1).getPlayerNumber() == 2 && board[row][col].getPlayerNumber() == 1) {
									if (select(row - 1, col)) {
										return board[row - 1][col];
									}
								}
							}

						}
						//Bottom Border Win AI
						else if (row == board.length - 1) {
							if (board[row][col].getPropertyColor() == 3) {
								if (getCell(row - 1, col) == null && getCell(row, col - 1).getPlayerNumber()
										== 2 && getCell(row, col + 1).getPlayerNumber() == 2 && board[row][col].getPlayerNumber() == 1) {
									if (select(row - 1, col)) {
										return board[row - 1][col];
									}
								} else if (getCell(row, col - 1) == null && getCell(row - 1, col).getPlayerNumber()
										== 2 && getCell(row, col + 1).getPlayerNumber() == 2 && board[row][col].getPlayerNumber() == 1)  {
									if (select(row, col - 1)) {
										return board[row][col - 1];
									}
								} else if (getCell(row, col + 1) == null && getCell(row - 1, col).getPlayerNumber()
										== 2 && getCell(row, col - 1).getPlayerNumber() == 2 && board[row][col].getPlayerNumber() == 1) {
									if (select(row, col + 1)) {
										return board[row][col + 1];
									}
								}
							}

						}
						//Left Border AI Block
						else if (col == 0) {
							if (board[row][col].getPropertyColor() == 3) {
								if (getCell(row + 1, col) == null && getCell(row, col + 1).getPlayerNumber()
										== 2 && getCell(row - 1, col).getPlayerNumber() == 2 && board[row][col].getPlayerNumber() == 1) {
									if (select(row + 1, col)) {
										return board[row + 1][col];
									}
								} else if (getCell(row, col + 1) == null && getCell(row + 1, col).getPlayerNumber()
										== 2 && getCell(row - 1, col).getPlayerNumber() == 2 && board[row][col].getPlayerNumber() == 1)  {
									if (select(row, col + 1)) {
										return board[row][col + 1];
									}
								} else if (getCell(row - 1, col) == null && getCell(row + 1, col).getPlayerNumber()
										== 2 && getCell(row, col + 1).getPlayerNumber() == 2 && board[row][col].getPlayerNumber() == 1) {
									if (select(row - 1, col)) {
										return board[row - 1][col];
									}
								}
							}
						}
						//Middle Board AI Block
						else if (row != 0 && col != 0 && row != board.length - 1 && col != board.length - 1) {
							if (board[row][col].getPropertyColor() == 4) {
								if (getCell(row + 1, col) == null && getCell(row, col + 1).getPlayerNumber()
										== 2 && getCell(row - 1, col).getPlayerNumber() == 2 &&
										getCell(row, col - 1).getPlayerNumber() == 2 && board[row][col].getPlayerNumber() == 1) {
									if (select(row + 1, col)) {
										return board[row + 1][col];
									}
								} else if (getCell(row, col + 1) == null && getCell(row + 1, col).getPlayerNumber()
										== 2 && getCell(row - 1, col).getPlayerNumber() == 2 &&
										getCell(row, col - 1).getPlayerNumber() == 2 && board[row][col].getPlayerNumber() == 1)  {
									if (select(row, col + 1)) {
										return board[row][col + 1];
									}
								} else if (getCell(row - 1, col) == null && getCell(row + 1, col).getPlayerNumber()
										== 2 && getCell(row, col + 1).getPlayerNumber() == 2 &&
										getCell(row, col - 1).getPlayerNumber() == 2 && board[row][col].getPlayerNumber() == 1) {
									if (select(row - 1, col)) {
										return board[row - 1][col];
									}
								} else if (getCell(row, col - 1) == null && getCell(row + 1, col).getPlayerNumber()
										== 2 && getCell(row, col + 1).getPlayerNumber() == 2 &&
										getCell(row - 1, col).getPlayerNumber() == 2 && board[row][col].getPlayerNumber() == 1) {
									if (select(row, col - 1)) {
										return board[row][col - 1];
									}
								}
							}
						}
					}
				}
			}
		}

		// If no winning move or block was found
		// the AI will look for an opponents tile that has two open playable sides
		// and play one of these two tiles at random to set up for a winning move
		// on its next turn.
		// If none were found it will look for one of its own tiles surrounded by
		// two opposing tiles to block the player from winning on that tile.

		// From this point forward, the computer will use a random number to determine which tile to
		// play next to create a a bit of unpredictability in what move it will make next.
		// For example, if there are three open tiles that are available for the computer to play, all three have an
		// equal chance of being chosen.

		for (int row = 0; row < board.length; row++) {
			for (int col = 0; col < board.length; col++) {
				Double rand = Math.random();

				if (getCurrentPlayer() == 1) {
					if (getCell(row, col) != null && getCell(row, col).getPlayerNumber() == 2) {

						//Top Left Corner
						if (row == 0 & col == 0) {
							if (checkAIWin(0, 0) == 0) {
								if (rand < .5) {
									if (select(row + 1, col)) {
										return board[row + 1][col];
									}
								} else {
									if (select(row, col + 1)) {
										return board[row][col + 1];
									}
								}
							}
						}
						//Top Right Corner
						else if (row == 0 && col == board.length - 1) {
							if (checkAIWin(0, board.length - 1) == 0) {
								if (rand < .5) {
									if (select(row + 1, col)) {
										return board[row + 1][col];
									}
								} else {
									if (select(row, col - 1)) {
										return board[row][col - 1];
									}
								}
							}
						}
						//Bottom Left Corner
						else if (row == board.length - 1 && col == 0) {
							if (checkAIWin(board.length - 1, 0) == 0) {
								if (rand < .5) {
									if (select(row - 1, col)) {
										return board[row - 1][col];
									}
								} else {
									if (select(row, col + 1)) {
										return board[row][col + 1];
									}
								}
							}
						}
						//Bottom Right Corner
						else if (row == board.length - 1 && col == board.length - 1) {
							if (checkAIWin(board.length - 1, board.length - 1) == 0) {


								if (rand < .5) {
									if (select(row - 1, col)) {
										return board[row - 1][col];
									}
								} else {
									if (select(row, col - 1)) {
										return board[row][col - 1];
									}
								}
							}
						}
						//Top Border
						else if (row == 0) {
							if (checkAIWin(row, col) == 1) {
								if (checkTopBorderForOpponents(row, col) == -1) {
									if (rand < .33) {
										if (select(row, col - 1)) {
											return board[row][col - 1];
										} else {
											if (rand < .15) {
												if (select(row + 1, col)) {
													return board[row + 1][col];
												}
											} else {
												if (select(row , col + 1)) {
													return board[row][col + 1];
												}
											}
										}
									} else if (rand >= .33 && rand < .66) {
										if (select(row + 1, col)) {
											return board[row + 1][col];
										} else {
											if (rand >= .33 && rand < .48) {
												if (select(row, col - 1)) {
													return board[row][col - 1];
												}
											} else {
												if (select(row , col + 1)) {
													return board[row][col + 1];
												}
											}
										}
									} else {
										if (select(row , col + 1)) {
											return board[row][col + 1];
										} else {
											if (rand >= .66 && rand < .81) {
												if (select(row, col - 1)) {
													return board[row][col - 1];
												}
											} else {
												if (select(row + 1, col)) {
													return board[row + 1][col];
												}
											}
										}
									}
								}
							}
						}
						//Right Border
						else if (col == board.length - 1) {
							if (checkAIWin(row, col) == 1) {
								if (checkRightBorderForOpponents(row, col) == -1) {
									if (rand < .33) {
										if (select(row, col - 1)) {
											return board[row][col - 1];
										} else {
											if (rand < .15) {
												if (select(row + 1, col)) {
													return board[row + 1][col];
												}
											} else {
												if (select(row - 1 , col)) {
													return board[row - 1][col];
												}
											}
										}
									} else if (rand >= .33 && rand < .66) {
										if (select(row + 1, col)) {
											return board[row + 1][col];
										} else {
											if (rand >= .33 && rand < .48) {
												if (select(row, col - 1)) {
													return board[row][col - 1];
												}
											} else {
												if (select(row - 1 , col)) {
													return board[row - 1][col];
												}
											}
										}
									} else {
										if (select(row - 1 , col)) {
											return board[row - 1][col];
										} else {
											if (rand >= .66 && rand < .81) {
												if (select(row, col - 1)) {
													return board[row][col - 1];
												}
											} else {
												if (select(row + 1, col)) {
													return board[row + 1][col];
												}
											}
										}
									}
								}
							}
						}
						//Bottom Border
						else if (row == board.length - 1) {
							if (checkAIWin(row, col) == 1) {
								if (checkBottomBorderForOpponents(row, col) == -1) {
									if (rand < .33) {
										if (select(row, col - 1)) {
											return board[row][col - 1];
										} else {
											if (rand < .15) {
												if (select(row - 1, col)) {
													return board[row - 1][col];
												}
											} else {
												if (select(row , col + 1)) {
													return board[row][col + 1];
												}
											}
										}
									} else if (rand >= .33 && rand < .66) {
										if (select(row - 1, col)) {
											return board[row - 1][col];
										} else {
											if (rand >= .33 && rand < .48) {
												if (select(row, col - 1)) {
													return board[row][col - 1];
												}
											} else {
												if (select(row , col + 1)) {
													return board[row][col + 1];
												}
											}
										}
									} else {
										if (select(row , col + 1)) {
											return board[row][col + 1];
										} else {
											if (rand >= .66 && rand < .81) {
												if (select(row, col - 1)) {
													return board[row][col - 1];
												}
											} else {
												if (select(row - 1, col)) {
													return board[row - 1][col];
												}
											}
										}
									}
								}

							}
						}
						//Left Border
						else if (col == 0) {
							if (checkAIWin(row, col) == 1) {
								if (checkLeftBorderForOpponents(row, col) == -1) {
									if (rand < .33) {
										if (select(row - 1, col)) {
											return board[row - 1][col];
										} else {
											if (rand < .15) {
												if (select(row + 1, col)) {
													return board[row + 1][col];
												}
											} else {
												if (select(row , col + 1)) {
													return board[row][col + 1];
												}
											}
										}
									} else if (rand >= .33 && rand < .66) {
										if (select(row + 1, col)) {
											return board[row + 1][col];
										} else {
											if (rand >= .33 && rand < .48) {
												if (select(row - 1, col)) {
													return board[row - 1][col];
												}
											} else {
												if (select(row , col + 1)) {
													return board[row][col + 1];
												}
											}
										}
									} else {
										if (select(row , col + 1)) {
											return board[row][col + 1];
										} else {
											if (rand >= .66 && rand < .81) {
												if (select(row - 1, col)) {
													return board[row - 1][col];
												}
											} else {
												if (select(row + 1, col)) {
													return board[row + 1][col];
												}
											}
										}
									}
								}
							}
						}
						//Middle Board
						else if (row != board.length - 1 && col != board.length - 1){
							if (checkAIWin(row, col) == 2) {
								if (checkMiddleForOpponents(row, col) == -1) {
									if (rand < .25) {
										if (select(row - 1, col)) {
											return board[row - 1][col];
										} else if (rand < .12) {
											if (select(row, col + 1)) {
												return board[row][col + 1];
											} else if (rand < .06) {
												if (select(row + 1, col)) {
													return board[row + 1][col];
												}
											} else {
												if (select(row, col - 1)) {
													return board[row][col - 1];
												}
											}
										} else {
											if (select(row, col + 1)) {
												return board[row][col + 1];
											} else if (rand < .18) {
												if (select(row + 1, col)) {
													return board[row + 1][col];
												}
											} else {
												if (select(row, col - 1)) {
													return board[row][col - 1];
												}
											}
										}
									} else if (rand >= .25 && rand < .5) {
										if (select(row, col + 1)) {
											return board[row][col + 1];
										} else if (rand < .37) {
											if (select(row + 1, col)) {
												return board[row + 1][col];
											} else if (rand < .31) {
												if (select(row, col - 1)) {
													return board[row][col - 1];
												}
											} else {
												if (select(row - 1, col)) {
													return board[row - 1][col];
												}
											}
										} else {
											if (select(row + 1, col)) {
												return board[row + 1][col];
											} else if (rand < .43) {
												if (select(row, col - 1)) {
													return board[row][col - 1];
												}
											} else {
												if (select(row - 1, col)) {
													return board[row - 1][col];
												}
											}
										}
									} else if (rand >= .5 && rand < .75) {
										if (select(row + 1, col)) {
											return board[row + 1][col];
										} else if (rand < .62) {
											if (select(row, col - 1)) {
												return board[row][col - 1];
											} else if (rand < .56) {
												if (select(row - 1, col)) {
													return board[row - 1][col];
												}
											} else {
												if (select(row, col + 1)) {
													return board[row][col + 1];
												}
											}
										} else {
											if (select(row, col - 1)) {
												return board[row][col - 1];
											} else if (rand < .68) {
												if (select(row - 1, col)) {
													return board[row - 1][col];
												}
											} else {
												if (select(row, col + 1)) {
													return board[row][col + 1];
												}
											}
										}
									} else {
										if (select(row, col - 1)) {
											return board[row][col - 1];
										} else if (rand < .87) {
											if (select(row - 1, col)) {
												return board[row - 1][col];
											} else if (rand < .81) {
												if (select(row, col + 1)) {
													return board[row][col + 1];
												}
											} else {
												if (select(row + 1, col)) {
													return board[row + 1][col];
												}
											}
										} else {
											if (select(row - 1, col)) {
												return board[row - 1][col];
											} else if (rand < .93) {
												if (select(row, col + 1)) {
													return board[row][col + 1];
												}
											} else {
												if (select(row + 1, col)) {
													return board[row + 1][col];
												}
											}
										}
									}
								}
							}
						}
					}

					// The computer will once again attempt to block the player

					else if ((getCell(row, col) != null && getCell(row, col).getPlayerNumber() == 1)) {
						//Top Left Corner
						if (row == 0 & col == 0) {
							if (checkAIWin(0, 0) == 0) {
								if (rand < .5) {
									if (select(row + 1, col)) {
										return board[row + 1][col];
									}
								} else {
									if (select(row, col + 1)) {
										return board[row][col + 1];
									}
								}
							}
						}
						//Top Right Corner
						else if (row == 0 && col == board.length - 1) {
							if (checkAIWin(0, board.length - 1) == 0) {
								if (rand < .5) {
									if (select(row + 1, col)) {
										return board[row + 1][col];
									}
								} else {
									if (select(row, col - 1)) {
										return board[row][col - 1];
									}
								}
							}
						}
						//Bottom Left Corner
						else if (row == board.length - 1 && col == 0) {
							if (checkAIWin(board.length - 1, 0) == 0) {
								if (rand < .5) {
									if (select(row - 1, col)) {
										return board[row - 1][col];
									}
								} else {
									if (select(row, col + 1)) {
										return board[row][col + 1];
									}
								}
							}
						}
						//Bottom Right Corner
						else if (row == board.length - 1 && col == board.length - 1) {
							if (checkAIWin(board.length - 1, board.length - 1) == 0) {
								if (rand < .5) {
									if (select(row - 1, col)) {
										return board[row - 1][col];
									}
								} else {
									if (select(row, col - 1)) {
										return board[row][col - 1];
									}
								}
							}
						}
						//Top Border
						else if (row == 0) {
							if (getCell(row, col).getPropertyColor() == 2) {
								if (checkTopBorderForOpponents(row, col) == 1) {
									if (rand < .33) {
										if (select(row, col - 1)) {
											return board[row][col - 1];
										} else {
											if (rand < .15) {
												if (select(row + 1, col)) {
													return board[row + 1][col];
												}
											} else {
												if (select(row , col + 1)) {
													return board[row][col + 1];
												}
											}
										}
									} else if (rand >= .33 && rand < .66) {
										if (select(row + 1, col)) {
											return board[row + 1][col];
										} else {
											if (rand >= .33 && rand < .48) {
												if (select(row, col - 1)) {
													return board[row][col - 1];
												}
											} else {
												if (select(row , col + 1)) {
													return board[row][col + 1];
												}
											}
										}
									} else {
										if (select(row , col + 1)) {
											return board[row][col + 1];
										} else {
											if (rand >= .66 && rand < .81) {
												if (select(row, col - 1)) {
													return board[row][col - 1];
												}
											} else {
												if (select(row + 1, col)) {
													return board[row + 1][col];
												}
											}
										}
									}
								}
							}
						}
						//Right Border
						else if (col == board.length - 1) {
							if (getCell(row, col).getPropertyColor() == 2) {
								if (checkRightBorderForOpponents(row, col) == 1) {
									if (rand < .33) {
										if (select(row, col - 1)) {
											return board[row][col - 1];
										} else {
											if (rand < .15) {
												if (select(row + 1, col)) {
													return board[row + 1][col];
												}
											} else {
												if (select(row - 1 , col)) {
													return board[row - 1][col];
												}
											}
										}
									} else if (rand >= .33 && rand < .66) {
										if (select(row + 1, col)) {
											return board[row + 1][col];
										} else {
											if (rand >= .33 && rand < .48) {
												if (select(row, col - 1)) {
													return board[row][col - 1];
												}
											} else {
												if (select(row - 1 , col)) {
													return board[row - 1][col];
												}
											}
										}
									} else {
										if (select(row - 1 , col)) {
											return board[row - 1][col];
										} else {
											if (rand >= .66 && rand < .81) {
												if (select(row, col - 1)) {
													return board[row][col - 1];
												}
											} else {
												if (select(row + 1, col)) {
													return board[row + 1][col];
												}
											}
										}
									}
								}
							}
						}
						//Bottom Border
						else if (row == board.length - 1) {
							if (getCell(row, col).getPropertyColor() == 2) {
								if (checkBottomBorderForOpponents(row, col) == 1) {
									if (rand < .33) {
										if (select(row, col - 1)) {
											return board[row][col - 1];
										} else {
											if (rand < .15) {
												if (select(row - 1, col)) {
													return board[row - 1][col];
												}
											} else {
												if (select(row , col + 1)) {
													return board[row][col + 1];
												}
											}
										}
									} else if (rand >= .33 && rand < .66) {
										if (select(row - 1, col)) {
											return board[row - 1][col];
										} else {
											if (rand >= .33 && rand < .48) {
												if (select(row, col - 1)) {
													return board[row][col - 1];
												}
											} else {
												if (select(row , col + 1)) {
													return board[row][col + 1];
												}
											}
										}
									} else {
										if (select(row , col + 1)) {
											return board[row][col + 1];
										} else {
											if (rand >= .66 && rand < .81) {
												if (select(row, col - 1)) {
													return board[row][col - 1];
												}
											} else {
												if (select(row - 1, col)) {
													return board[row - 1][col];
												}
											}
										}
									}
								}

							}
						}
						//Left Border
						else if (col == 0) {
							if (getCell(row, col).getPropertyColor() == 2) {
								if (checkLeftBorderForOpponents(row, col) == 1) {
									if (rand < .33) {
										if (select(row - 1, col)) {
											return board[row - 1][col];
										} else {
											if (rand < .15) {
												if (select(row + 1, col)) {
													return board[row + 1][col];
												}
											} else {
												if (select(row , col + 1)) {
													return board[row][col + 1];
												}
											}
										}
									} else if (rand >= .33 && rand < .66) {
										if (select(row + 1, col)) {
											return board[row + 1][col];
										} else {
											if (rand >= .33 && rand < .48) {
												if (select(row - 1, col)) {
													return board[row - 1][col];
												}
											} else {
												if (select(row , col + 1)) {
													return board[row][col + 1];
												}
											}
										}
									} else {
										if (select(row , col + 1)) {
											return board[row][col + 1];
										} else {
											if (rand >= .66 && rand < .81) {
												if (select(row - 1, col)) {
													return board[row - 1][col];
												}
											} else {
												if (select(row + 1, col)) {
													return board[row + 1][col];
												}
											}
										}
									}
								}
							}
						}
						//Middle Board
						else if (row != board.length - 1 && col != board.length - 1){
							if (getCell(row, col).getPropertyColor() == 3) {
								if (checkMiddleForOpponents(row, col) == 1) {
									if (rand < .25) {
										if (select(row - 1, col)) {
											return board[row - 1][col];
										} else if (rand < .12) {
											if (select(row, col + 1)) {
												return board[row][col + 1];
											} else if (rand < .06) {
												if (select(row + 1, col)) {
													return board[row + 1][col];
												}
											} else {
												if (select(row, col - 1)) {
													return board[row][col - 1];
												}
											}
										} else {
											if (select(row, col + 1)) {
												return board[row][col + 1];
											} else if (rand < .18) {
												if (select(row + 1, col)) {
													return board[row + 1][col];
												}
											} else {
												if (select(row, col - 1)) {
													return board[row][col - 1];
												}
											}
										}
									} else if (rand >= .25 && rand < .5) {
										if (select(row, col + 1)) {
											return board[row][col + 1];
										} else if (rand < .37) {
											if (select(row + 1, col)) {
												return board[row + 1][col];
											} else if (rand < .31) {
												if (select(row, col - 1)) {
													return board[row][col - 1];
												}
											} else {
												if (select(row - 1, col)) {
													return board[row - 1][col];
												}
											}
										} else {
											if (select(row + 1, col)) {
												return board[row + 1][col];
											} else if (rand < .43) {
												if (select(row, col - 1)) {
													return board[row][col - 1];
												}
											} else {
												if (select(row - 1, col)) {
													return board[row - 1][col];
												}
											}
										}
									} else if (rand >= .5 && rand < .75) {
										if (select(row + 1, col)) {
											return board[row + 1][col];
										} else if (rand < .62) {
											if (select(row, col - 1)) {
												return board[row][col - 1];
											} else if (rand < .56) {
												if (select(row - 1, col)) {
													return board[row - 1][col];
												}
											} else {
												if (select(row, col + 1)) {
													return board[row][col + 1];
												}
											}
										} else {
											if (select(row, col - 1)) {
												return board[row][col - 1];
											} else if (rand < .68) {
												if (select(row - 1, col)) {
													return board[row - 1][col];
												}
											} else {
												if (select(row, col + 1)) {
													return board[row][col + 1];
												}
											}
										}
									} else {
										if (select(row, col - 1)) {
											return board[row][col - 1];
										} else if (rand < .87) {
											if (select(row - 1, col)) {
												return board[row - 1][col];
											} else if (rand < .81) {
												if (select(row, col + 1)) {
													return board[row][col + 1];
												}
											} else {
												if (select(row + 1, col)) {
													return board[row + 1][col];
												}
											}
										} else {
											if (select(row - 1, col)) {
												return board[row - 1][col];
											} else if (rand < .93) {
												if (select(row, col + 1)) {
													return board[row][col + 1];
												}
											} else {
												if (select(row + 1, col)) {
													return board[row + 1][col];
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}

		// If two open spots were not found on either the AI's tiles or the player's tiles
		// the AI will then look for tiles with three open tiles following the same
		// guidelines as before.

		for (int row = 0; row < board.length; row++) {
			for (int col = 0; col < board.length; col++) {
				Double rand = Math.random();

				if (getCurrentPlayer() == 1) {
					if (getCell(row, col) != null && getCell(row, col).getPlayerNumber() == 2) {
						//Top Border
						if (row == 0) {
							if (checkAIWin(row, col) == 0) {
								if (rand < .33) {
									if (select(row, col - 1)) {
										return board[row][col - 1];
									} else {
										if (rand < .15) {
											if (select(row + 1, col)) {
												return board[row + 1][col];
											}
										} else {
											if (select(row , col + 1)) {
												return board[row][col + 1];
											}
										}
									}
								} else if (rand >= .33 && rand < .66) {
									if (select(row + 1, col)) {
										return board[row + 1][col];
									} else {
										if (rand >= .33 && rand < .48) {
											if (select(row, col - 1)) {
												return board[row][col - 1];
											}
										} else {
											if (select(row , col + 1)) {
												return board[row][col + 1];
											}
										}
									}
								} else {
									if (select(row , col + 1)) {
										return board[row][col + 1];
									} else {
										if (rand >= .66 && rand < .81) {
											if (select(row, col - 1)) {
												return board[row][col - 1];
											}
										} else {
											if (select(row + 1, col)) {
												return board[row + 1][col];
											}
										}
									}
								}

							}
						}
						//Right Border
						else if (col == board.length - 1) {
							if (checkAIWin(row, col) == 0) {
								if (rand < .33) {
									if (select(row, col - 1)) {
										return board[row][col - 1];
									} else {
										if (rand < .15) {
											if (select(row + 1, col)) {
												return board[row + 1][col];
											}
										} else {
											if (select(row - 1 , col)) {
												return board[row - 1][col];
											}
										}
									}
								} else if (rand >= .33 && rand < .66) {
									if (select(row + 1, col)) {
										return board[row + 1][col];
									} else {
										if (rand >= .33 && rand < .48) {
											if (select(row, col - 1)) {
												return board[row][col - 1];
											}
										} else {
											if (select(row - 1 , col)) {
												return board[row - 1][col];
											}
										}
									}
								} else {
									if (select(row - 1 , col)) {
										return board[row - 1][col];
									} else {
										if (rand >= .66 && rand < .81) {
											if (select(row, col - 1)) {
												return board[row][col - 1];
											}
										} else {
											if (select(row + 1, col)) {
												return board[row + 1][col];
											}
										}
									}
								}

							}
						}
						//Bottom Border
						else if (row == board.length - 1) {
							if (checkAIWin(row, col) == 0) {
								if (rand < .33) {
									if (select(row, col - 1)) {
										return board[row][col - 1];
									} else {
										if (rand < .15) {
											if (select(row - 1, col)) {
												return board[row - 1][col];
											}
										} else {
											if (select(row , col + 1)) {
												return board[row][col + 1];
											}
										}
									}
								} else if (rand >= .33 && rand < .66) {
									if (select(row - 1, col)) {
										return board[row - 1][col];
									} else {
										if (rand >= .33 && rand < .48) {
											if (select(row, col - 1)) {
												return board[row][col - 1];
											}
										} else {
											if (select(row , col + 1)) {
												return board[row][col + 1];
											}
										}
									}
								} else {
									if (select(row , col + 1)) {
										return board[row][col + 1];
									} else {
										if (rand >= .66 && rand < .81) {
											if (select(row, col - 1)) {
												return board[row][col - 1];
											}
										} else {
											if (select(row - 1, col)) {
												return board[row - 1][col];
											}
										}
									}
								}
							}
						}
						//Left Border
						else if (col == 0) {
							if (checkAIWin(row, col) == 0) {
								if (rand < .33) {
									if (select(row - 1, col)) {
										return board[row - 1][col];
									} else {
										if (rand < .15) {
											if (select(row + 1, col)) {
												return board[row + 1][col];
											}
										} else {
											if (select(row , col + 1)) {
												return board[row][col + 1];
											}
										}
									}
								} else if (rand >= .33 && rand < .66) {
									if (select(row + 1, col)) {
										return board[row + 1][col];
									} else {
										if (rand >= .33 && rand < .48) {
											if (select(row - 1, col)) {
												return board[row - 1][col];
											}
										} else {
											if (select(row , col + 1)) {
												return board[row][col + 1];
											}
										}
									}
								} else {
									if (select(row , col + 1)) {
										return board[row][col + 1];
									} else {
										if (rand >= .66 && rand < .81) {
											if (select(row - 1, col)) {
												return board[row - 1][col];
											}
										} else {
											if (select(row + 1, col)) {
												return board[row + 1][col];
											}
										}
									}
								}
							}
						}
						//Middle Board
						else if (row != board.length - 1 && col != board.length - 1){
							if (checkAIWin(row, col) == 1) {
								if (checkMiddleForOpponents(row, col) == -1) {
									if (rand < .25) {
										if (select(row - 1, col)) {
											return board[row - 1][col];
										} else if (rand < .12) {
											if (select(row, col + 1)) {
												return board[row][col + 1];
											} else if (rand < .06) {
												if (select(row + 1, col)) {
													return board[row + 1][col];
												}
											} else {
												if (select(row, col - 1)) {
													return board[row][col - 1];
												}
											}
										} else {
											if (select(row, col + 1)) {
												return board[row][col + 1];
											} else if (rand < .18) {
												if (select(row + 1, col)) {
													return board[row + 1][col];
												}
											} else {
												if (select(row, col - 1)) {
													return board[row][col - 1];
												}
											}
										}
									} else if (rand >= .25 && rand < .5) {
										if (select(row, col + 1)) {
											return board[row][col + 1];
										} else if (rand < .37) {
											if (select(row + 1, col)) {
												return board[row + 1][col];
											} else if (rand < .31) {
												if (select(row, col - 1)) {
													return board[row][col - 1];
												}
											} else {
												if (select(row - 1, col)) {
													return board[row - 1][col];
												}
											}
										} else {
											if (select(row + 1, col)) {
												return board[row + 1][col];
											} else if (rand < .43) {
												if (select(row, col - 1)) {
													return board[row][col - 1];
												}
											} else {
												if (select(row - 1, col)) {
													return board[row - 1][col];
												}
											}
										}
									} else if (rand >= .5 && rand < .75) {
										if (select(row + 1, col)) {
											return board[row + 1][col];
										} else if (rand < .62) {
											if (select(row, col - 1)) {
												return board[row][col - 1];
											} else if (rand < .56) {
												if (select(row - 1, col)) {
													return board[row - 1][col];
												}
											} else {
												if (select(row, col + 1)) {
													return board[row][col + 1];
												}
											}
										} else {
											if (select(row, col - 1)) {
												return board[row][col - 1];
											} else if (rand < .68) {
												if (select(row - 1, col)) {
													return board[row - 1][col];
												}
											} else {
												if (select(row, col + 1)) {
													return board[row][col + 1];
												}
											}
										}
									} else {
										if (select(row, col - 1)) {
											return board[row][col - 1];
										} else if (rand < .87) {
											if (select(row - 1, col)) {
												return board[row - 1][col];
											} else if (rand < .81) {
												if (select(row, col + 1)) {
													return board[row][col + 1];
												}
											} else {
												if (select(row + 1, col)) {
													return board[row + 1][col];
												}
											}
										} else {
											if (select(row - 1, col)) {
												return board[row - 1][col];
											} else if (rand < .93) {
												if (select(row, col + 1)) {
													return board[row][col + 1];
												}
											} else {
												if (select(row + 1, col)) {
													return board[row + 1][col];
												}
											}
										}
									}
								}
							}
						}
					}

					// Attempting to block yet again

					else if (getCell(row, col) != null && getCell(row, col).getPlayerNumber() == 1) {
						//Top Border
						if (row == 0) {
							if (rand < .33) {
								if (select(row, col - 1)) {
									return board[row][col - 1];
								} else {
									if (rand < .15) {
										if (select(row + 1, col)) {
											return board[row + 1][col];
										}
									} else {
										if (select(row , col + 1)) {
											return board[row][col + 1];
										}
									}
								}
							} else if (rand >= .33 && rand < .66) {
								if (select(row + 1, col)) {
									return board[row + 1][col];
								} else {
									if (rand >= .33 && rand < .48) {
										if (select(row, col - 1)) {
											return board[row][col - 1];
										}
									} else {
										if (select(row , col + 1)) {
											return board[row][col + 1];
										}
									}
								}
							} else {
								if (select(row , col + 1)) {
									return board[row][col + 1];
								} else {
									if (rand >= .66 && rand < .81) {
										if (select(row, col - 1)) {
											return board[row][col - 1];
										}
									} else {
										if (select(row + 1, col)) {
											return board[row + 1][col];
										}
									}
								}
							}

						}
						//Right Border
						else if (col == board.length - 1) {
							if (getCell(row, col).getPropertyColor() == 1) {
								if (rand < .33) {
									if (select(row, col - 1)) {
										return board[row][col - 1];
									} else {
										if (rand < .15) {
											if (select(row + 1, col)) {
												return board[row + 1][col];
											}
										} else {
											if (select(row - 1 , col)) {
												return board[row - 1][col];
											}
										}
									}
								} else if (rand >= .33 && rand < .66) {
									if (select(row + 1, col)) {
										return board[row + 1][col];
									} else {
										if (rand >= .33 && rand < .48) {
											if (select(row, col - 1)) {
												return board[row][col - 1];
											}
										} else {
											if (select(row - 1 , col)) {
												return board[row - 1][col];
											}
										}
									}
								} else {
									if (select(row - 1 , col)) {
										return board[row - 1][col];
									} else {
										if (rand >= .66 && rand < .81) {
											if (select(row, col - 1)) {
												return board[row][col - 1];
											}
										} else {
											if (select(row + 1, col)) {
												return board[row + 1][col];
											}
										}
									}
								}
							}
						}
						//Bottom Border
						else if (row == board.length - 1) {
							if (getCell(row, col).getPropertyColor() == 1) {
								if (rand < .33) {
									if (select(row, col - 1)) {
										return board[row][col - 1];
									} else {
										if (rand < .15) {
											if (select(row - 1, col)) {
												return board[row - 1][col];
											}
										} else {
											if (select(row , col + 1)) {
												return board[row][col + 1];
											}
										}
									}
								} else if (rand >= .33 && rand < .66) {
									if (select(row - 1, col)) {
										return board[row - 1][col];
									} else {
										if (rand >= .33 && rand < .48) {
											if (select(row, col - 1)) {
												return board[row][col - 1];
											}
										} else {
											if (select(row , col + 1)) {
												return board[row][col + 1];
											}
										}
									}
								} else {
									if (select(row , col + 1)) {
										return board[row][col + 1];
									} else {
										if (rand >= .66 && rand < .81) {
											if (select(row, col - 1)) {
												return board[row][col - 1];
											}
										} else {
											if (select(row - 1, col)) {
												return board[row - 1][col];
											}
										}
									}
								}
							}
						}
						//Left Border
						else if (col == 0) {
							if (getCell(row, col).getPropertyColor() == 1) {
								if (rand < .33) {
									if (select(row - 1, col)) {
										return board[row - 1][col];
									} else {
										if (rand < .15) {
											if (select(row + 1, col)) {
												return board[row + 1][col];
											}
										} else {
											if (select(row , col + 1)) {
												return board[row][col + 1];
											}
										}
									}
								} else if (rand >= .33 && rand < .66) {
									if (select(row + 1, col)) {
										return board[row + 1][col];
									} else {
										if (rand >= .33 && rand < .48) {
											if (select(row - 1, col)) {
												return board[row - 1][col];
											}
										} else {
											if (select(row , col + 1)) {
												return board[row][col + 1];
											}
										}
									}
								} else {
									if (select(row , col + 1)) {
										return board[row][col + 1];
									} else {
										if (rand >= .66 && rand < .81) {
											if (select(row - 1, col)) {
												return board[row - 1][col];
											}
										} else {
											if (select(row + 1, col)) {
												return board[row + 1][col];
											}
										}
									}
								}
							}
						}
						//Middle Board
						else if (row != board.length - 1 && col != board.length - 1){
							if (getCell(row, col).getPropertyColor() == 2) {
								if (checkMiddleForOpponents(row, col) == 1) {
									if (rand < .25) {
										if (select(row - 1, col)) {
											return board[row - 1][col];
										} else if (rand < .12) {
											if (select(row, col + 1)) {
												return board[row][col + 1];
											} else if (rand < .06) {
												if (select(row + 1, col)) {
													return board[row + 1][col];
												}
											} else {
												if (select(row, col - 1)) {
													return board[row][col - 1];
												}
											}
										} else {
											if (select(row, col + 1)) {
												return board[row][col + 1];
											} else if (rand < .18) {
												if (select(row + 1, col)) {
													return board[row + 1][col];
												}
											} else {
												if (select(row, col - 1)) {
													return board[row][col - 1];
												}
											}
										}
									} else if (rand >= .25 && rand < .5) {
										if (select(row, col + 1)) {
											return board[row][col + 1];
										} else if (rand < .37) {
											if (select(row + 1, col)) {
												return board[row + 1][col];
											} else if (rand < .31) {
												if (select(row, col - 1)) {
													return board[row][col - 1];
												}
											} else {
												if (select(row - 1, col)) {
													return board[row - 1][col];
												}
											}
										} else {
											if (select(row + 1, col)) {
												return board[row + 1][col];
											} else if (rand < .43) {
												if (select(row, col - 1)) {
													return board[row][col - 1];
												}
											} else {
												if (select(row - 1, col)) {
													return board[row - 1][col];
												}
											}
										}
									} else if (rand >= .5 && rand < .75) {
										if (select(row + 1, col)) {
											return board[row + 1][col];
										} else if (rand < .62) {
											if (select(row, col - 1)) {
												return board[row][col - 1];
											} else if (rand < .56) {
												if (select(row - 1, col)) {
													return board[row - 1][col];
												}
											} else {
												if (select(row, col + 1)) {
													return board[row][col + 1];
												}
											}
										} else {
											if (select(row, col - 1)) {
												return board[row][col - 1];
											} else if (rand < .68) {
												if (select(row - 1, col)) {
													return board[row - 1][col];
												}
											} else {
												if (select(row, col + 1)) {
													return board[row][col + 1];
												}
											}
										}
									} else {
										if (select(row, col - 1)) {
											return board[row][col - 1];
										} else if (rand < .87) {
											if (select(row - 1, col)) {
												return board[row - 1][col];
											} else if (rand < .81) {
												if (select(row, col + 1)) {
													return board[row][col + 1];
												}
											} else {
												if (select(row + 1, col)) {
													return board[row + 1][col];
												}
											}
										} else {
											if (select(row - 1, col)) {
												return board[row - 1][col];
											} else if (rand < .93) {
												if (select(row, col + 1)) {
													return board[row][col + 1];
												}
											} else {
												if (select(row + 1, col)) {
													return board[row + 1][col];
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}

		//Next is to check for a standalone player tile somewhere in the middle of the board
		for (int row = 0; row < board.length; row++) {
			for (int col = 0; col < board.length; col++) {
				Double rand = Math.random();

				if (getCurrentPlayer() == 1) {
					if (getCell(row, col) != null && getCell(row, col).getPlayerNumber() == 2) {

						//Middle Board
						if (row != board.length - 1 && col != board.length - 1){
							if (checkAIWin(row, col) == 0) {
								if (rand < .25) {
									if (select(row - 1, col)) {
										return board[row - 1][col];
									}
								} else if (rand >= .25 && rand < .5) {
									if (select(row, col + 1)) {
										return board[row][col + 1];
									}
								} else if (rand >= .5 && rand < .75) {
									if (select(row + 1, col)) {
										return board[row + 1][col];
									}
								} else {
									if (select(row, col - 1)) {
										return board[row][col - 1];
									}
								}
							}
						}
					}
				}
			}
		}

		//If you're reading this, send help.

		return null;
	}

	/**
	 *   Checks for a winning player on the game board
	 *
	 * @return The number of the player if one has one, if no winners returns -1
	 */

	public int getWinner() {
		//iterates through the board to check each cell
		for (int row = 0; row < board.length; row++) {
			for (int col = 0; col < board.length; col++) {

				//top left corner case
				if (row == 0 && col == 0 && board[0][0] != null) {
					//Are the cells directly below and to the right empty?
					if (board[0][1] != null && board[1][0] != null) {
						//Does the same player occupy both of these cells?
						if (board[0][1].getPlayerNumber() == board[1][0].getPlayerNumber() &&
								board[0][0].getPlayerNumber() != board[0][1].getPlayerNumber()) {
							//Player has won, returns their player number
							return board[0][1].getPlayerNumber();
						}
					}
				}

				//top right corner case
				if (row == 0 && col == board.length - 1 && board[0][board.length - 1] != null) {
					//Are the cells directly to the left and below empty?
					if (board[0][board.length - 2] != null && board[1][board.length - 1] != null) {
						//Does the same player occupy both of these cells?
						if (board[0][board.length - 2].getPlayerNumber() == board[1][board.length - 1].getPlayerNumber()
								&& board[0][board.length - 1].getPlayerNumber() != board[1][board.length - 1].getPlayerNumber()) {
							//Player has won, return their player number
							return board[0][board.length - 2].getPlayerNumber();
						}
					}
				}

				//bottom left corner case
				if (row == board.length - 1 && col == 0 && board[board.length - 1][0] != null) {
					//Are the cells directly above and to the right empty?
					if (board[board.length - 2][0] != null && board[board.length - 1][1] != null) {
						//Are these cells occupied by the same player?
						if (board[board.length - 2][0].getPlayerNumber() == board[board.length - 1][1].getPlayerNumber()
								&& board[board.length - 1][0].getPlayerNumber() != board[board.length - 1][1].getPlayerNumber()) {
							//Player has won, returns their player number
							return board[board.length - 2][0].getPlayerNumber();
						}
					}
				}

				//bottom right corner case
				if (row == board.length - 1 && col == board.length - 1 && board[board.length - 1][board.length - 1] != null) {
					//Are the cells directly to the left and above empty?
					if (board[board.length - 1][board.length - 2] != null && board[board.length - 2][board.length - 1] != null) {
						//Are these cells occupied by the same player?
						if (board[board.length - 1][board.length - 2].getPlayerNumber() ==
								board[board.length - 2][board.length - 1].getPlayerNumber() &&
								board[board.length - 1][board.length - 1].getPlayerNumber() !=
								board[board.length - 2][board.length - 1].getPlayerNumber()) {
							//Player has won, returns their player number
							return board[board.length - 1][board.length - 2].getPlayerNumber();
						}
					}
				}

				//left border case (excluding corners)
				if (row != 0 && row != board.length - 1 && col == 0 && board[row][col] != null) {
					//Are the cells directly above, below, and to the right empty?
					if (board[row - 1][col] != null && board[row + 1][col] != null && board[row][col + 1] != null) {
						//Are these cells occupied by the same player?
						if (board[row - 1][col].getPlayerNumber() == board[row + 1][col].getPlayerNumber() &&
								board[row - 1][col].getPlayerNumber() == board[row][col + 1].getPlayerNumber() &&
								board[row - 1][col].getPlayerNumber() != board[row][col].getPlayerNumber()) {
									//Player has won, returns their player number
									return board[row - 1][col].getPlayerNumber();
						}
					}
				}

				//top border case (excluding corners)
				if (row == 0 && col != board.length - 1 && col != 0 && board[row][col] != null) {
					//Are the cells directly to the left, right, and below empty?
					if (board[0][col - 1] != null && board[0][col + 1] != null && board[row + 1][col] != null) {
						//Are these cells occupied by the same player?
						if (board[0][col - 1].getPlayerNumber() == board[0][col + 1].getPlayerNumber() &&
								board[0][col - 1].getPlayerNumber() == board[row + 1][col].getPlayerNumber() &&
								board[0][col - 1].getPlayerNumber() != board[row][col].getPlayerNumber()) {
									//Player has one, returns their player number
									return board[0][col - 1].getPlayerNumber();
						}
					}
				}

				//right border case (excluding corners)
				if (row != 0 && row != board.length - 1 && col == board.length - 1 && board[row][col] != null) {
					//Are the cells directly above, below, and to the right occupied?
					if (board[row - 1][col] != null && board[row + 1][col] != null && board[row][col - 1] != null) {
						//Are these cells occupied by the same player?
						if (board[row - 1][col].getPlayerNumber() == board[row + 1][col].getPlayerNumber() &&
								board[row - 1][col].getPlayerNumber() == board[row][col - 1].getPlayerNumber() &&
								board[row - 1][col].getPlayerNumber() != board[row][col].getPlayerNumber()) {
									//Player has won, returns their player number
									return board[row - 1][col].getPlayerNumber();
						}
					}
				}

				//bottom border case (excluding corners)
				if (row == board.length - 1 && col != board.length - 1 && col != 0 && board[row][col] != null) {
					//Are the cells directly to the left, right, and above empty?
					if (board[board.length - 1][col - 1] != null && board[board.length - 1][col + 1] != null && board[row - 1][col] != null) {
						//Are these cells occupied by the same player?
						if (board[board.length - 1][col - 1].getPlayerNumber() == board[board.length - 1][col + 1].getPlayerNumber() &&
								board[board.length - 1][col - 1].getPlayerNumber() == board[row - 1][col].getPlayerNumber() &&
								board[board.length - 1][col - 1].getPlayerNumber() != board[row][col].getPlayerNumber()) {
							//Player has won, returns their player number
							return board[board.length - 1][col - 1].getPlayerNumber();
						}
					}
				}

				//middle of board case (excluding corners and borders)


				if (row != 0 && row != board.length - 1 && col != 0 && col != board.length - 1 && board[row][col] != null) {
					//Are the four cells directly surrounding this cell empty?
					if (board[row - 1][col] != null && board[row][col + 1] != null && board[row + 1][col] != null
							&& board[row][col - 1] != null) {
						//Are these cells occupied by the same player?
						if (board[row - 1][col].getPlayerNumber() == board[row][col + 1].getPlayerNumber()
								&& board[row - 1][col].getPlayerNumber() == board[row + 1][col].getPlayerNumber()
								&& board[row - 1][col].getPlayerNumber() == board[row][col - 1].getPlayerNumber()
								&& board[row - 1][col].getPlayerNumber() != board[row][col].getPlayerNumber()) {
							//Player has won, returns their player number
							return board[row - 1][col].getPlayerNumber();
						}
					}
				}
			}
		}

		//If no player has won, return -1
		return -1;	
	}
}






