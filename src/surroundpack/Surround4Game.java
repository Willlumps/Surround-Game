package surroundpack;

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
			c.setPropertyColor(2);
			board[row][col] = c;
			return true;
		}
		else 
			return false;
	}


	/**
	 *   Checks for a winning player on the game board
	 *
	 * @return The number of the player if one has one, if no winners returns -1
	 */

	public int getWinner() {
		//TODO ADD ADDITIONAL CHECKS FOR EMPTY CELLS
		//iterates through the board to check each cell
		for (int row = 0; row < board.length; row++) {
			for (int col = 0; col < board.length; col++) {

				//top left corner case
				if (row == 0 && col == 0 && board[0][0] != null) {
					//Are the cells directly below and to the right empty?
					if (board[0][1] != null && board[1][0] != null) {
						//Does the same player occupy both of these cells?
						if (board[0][1].getPlayerNumber() == board[1][0].getPlayerNumber() && board[0][0].getPlayerNumber() != board[0][1].getPlayerNumber()) {
							//Player has won, returns their player number
							return board[0][1].getPlayerNumber();
						}
					}
				}

				//top right corner case
				if (row == 0 && col == board.length - 1) {
					//Are the cells directly to the left and below empty?
					if (board[0][board.length - 2] != null && board[1][board.length - 1] != null) {
						//Does the same player occupy both of these cells?
						if (board[0][board.length - 2].getPlayerNumber() == board[1][board.length - 1].getPlayerNumber()) {
							//Player has won, return their player number
							return board[0][board.length - 2].getPlayerNumber();
						}
					}
				}

				//bottom left corner case
				if (row == board.length - 1 && col == 0) {
					//Are the cells directly above and to the right empty?
					if (board[board.length - 2][0] != null && board[board.length - 1][1] != null) {
						//Are these cells occupied by the same player?
						if (board[board.length - 2][0].getPlayerNumber() == board[board.length - 1][1].getPlayerNumber()) {
							//Player has won, returns their player number
							return board[board.length - 2][0].getPlayerNumber();
						}
					}
				}

				//bottom right corner case
				if (row == board.length - 1 && col == board.length - 1) {
					//Are the cells directly to the left and above empty?
					if (board[board.length - 1][board.length - 2] != null && board[board.length - 2][board.length - 1] != null) {
						//Are these cells occupied by the same player?
						if (board[board.length - 1][board.length - 2].getPlayerNumber() == board[board.length - 2][board.length - 1].getPlayerNumber()) {
							//Player has won, returns their player number
							return board[board.length - 1][board.length - 2].getPlayerNumber();
						}
					}
				}

				//left border case (excluding corners)
				if (row != 0 && row != board.length - 1 && col == 0) {
					//Are the cells directly above, below, and to the right empty?
					if (board[row - 1][col] != null && board[row + 1][col] != null && board[row][col + 1] != null) {
						//Are these cells occupied by the same player?
						if (board[row - 1][col].getPlayerNumber() == board[row + 1][col].getPlayerNumber() &&
								board[row - 1][col].getPlayerNumber() == board[row][col + 1].getPlayerNumber()) {
									//Player has won, returns their player number
									return board[row - 1][col].getPlayerNumber();
						}
					}
				}

				//top border case (excluding corners)
				if (row == 0 && col != board.length - 1 && col != 0) {
					//Are the cells directly to the left, right, and below empty?
					if (board[0][col - 1] != null && board[0][col + 1] != null && board[row + 1][col] != null) {
						//Are these cells occupied by the same player?
						if (board[0][col - 1].getPlayerNumber() == board[0][col + 1].getPlayerNumber() &&
								board[0][col - 1].getPlayerNumber() == board[row + 1][col].getPlayerNumber()) {
									//Player has one, returns their player number
									return board[0][col - 1].getPlayerNumber();
						}
					}
				}

				//right border case (excluding corners)
				if (row != 0 && row != board.length - 1 && col == board.length - 1) {
					//Are the cells directly above, below, and to the right occupied?
					if (board[row - 1][col] != null && board[row + 1][col] != null && board[row][col - 1] != null) {
						//Are these cells occupied by the same player?
						if (board[row - 1][col].getPlayerNumber() == board[row + 1][col].getPlayerNumber() &&
								board[row - 1][col].getPlayerNumber() == board[row][col - 1].getPlayerNumber()) {
									//Player has won, returns their player number
									return board[row - 1][col].getPlayerNumber();
						}
					}
				}

				//bottom border case (excluding corners)
				if (row == board.length - 1 && col != board.length - 1 && col != 0) {
					//Are the cells directly to the left, right, and above empty?
					if (board[0][col - 1] != null && board[0][col + 1] != null && board[row - 1][col] != null) {
						//Are these cells occupied by the same player?
						if (board[0][col - 1].getPlayerNumber() == board[0][col + 1].getPlayerNumber() &&
								board[0][col - 1].getPlayerNumber() == board[row - 1][col].getPlayerNumber()) {
									//Player has won, returns their player number
									return board[0][col - 1].getPlayerNumber();
						}
					}
				}

				//middle of board case (excluding corners and borders)
				if (row != 0 && row != board.length - 1 && col != 0 && col != board.length - 1) {
					//Are the four cells directly surrounding this cell empty?
					if (board[row - 1][col] != null && board[row][col + 1] != null && board[row + 1][col] != null
							&& board[row][col - 1] != null) {
						//Are these cells occupied by the same player?
						if (board[row - 1][col].getPlayerNumber() == board[row][col + 1].getPlayerNumber()
								&& board[row - 1][col].getPlayerNumber() == board[row + 1][col].getPlayerNumber()
								&& board[row - 1][col].getPlayerNumber() == board[row][col - 1].getPlayerNumber()) {
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






