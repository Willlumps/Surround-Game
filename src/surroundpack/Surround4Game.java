package surroundpack;

public class Surround4Game {
	private Cell[][] board;
	private int player;

	public Surround4Game() {
		//super();
		board = new Cell[10][10];		
		this.player = 1;
	}

	public void reset() {
		for (int r = 0; r < 10; r++) {
			for (int c = 0; c < 10; c++) {
				board[r][c] = null;
			}
		}
	}

	public Cell getCell(int row, int col) {
		return board[row][col];
	}

	public int getCurrentPlayer() {
		return player;
	}

	public int nextPlayer() {
		//		player = player + 1;
		//		if (player == 2)
		//			player = 0;

		player = (player % 2) + 1;
		return player;
	}

	public boolean select(int row, int col) {
		if (board[row][col] == null ) {  //|| (cats() && board[row][col].getPlayeNumber() != player)) {
			Cell c = new Cell(player);
			board[row][col] = c;
			return true;
		}
		else 
			return false;
	}

	public int getWinner() {

		for (int row = 0; row < 10; row++) {
			for (int col = 0; col < 10; col++) {

				//top left corner case
				if (row == 0 && col == 0) {
					if (board[0][1] != null && board[1][0] != null) {
						if (board[0][1].getPlayerNumber() == board[1][0].getPlayerNumber()) {
							return board[0][1].getPlayerNumber();
						}
					}
				}

				//top right corner case
				if (row == 0 && col == board.length - 1) {
					if (board[0][board.length - 2] != null && board[1][board.length - 1] != null) {
						if (board[0][board.length - 2].getPlayerNumber() == board[1][board.length - 1].getPlayerNumber()) {
							return board[0][board.length - 2].getPlayerNumber();
						}
					}
				}

				//bottom left corner case
				if (row == board.length - 1 && col == 0) {
					if (board[board.length - 2][0] != null && board[board.length - 1][1] != null) {
						if (board[board.length - 2][0].getPlayerNumber() == board[board.length - 1][1].getPlayerNumber()) {
							return board[board.length - 2][0].getPlayerNumber();
						}
					}
				}

				//bottom right corner case
				if (row == board.length - 1 && col == board.length - 1) {
					if (board[board.length - 1][board.length - 2] != null && board[board.length - 2][board.length - 1] != null) {
						if (board[board.length - 1][board.length - 2].getPlayerNumber() == board[board.length - 2][board.length - 1].getPlayerNumber()) {
							return board[board.length - 1][board.length - 2].getPlayerNumber();
						}
					}
				}

				//left border case (excluding corners)
				if (row != 0 && row != board.length - 1 && col == 0) {
					if (board[row - 1][col] != null && board[row + 1][col] != null && board[row][col + 1] != null) {
						if (board[row - 1][col].getPlayerNumber() == board[row + 1][col].getPlayerNumber() &&
								board[row - 1][col].getPlayerNumber() == board[row][col + 1].getPlayerNumber()) {
							return board[row - 1][col].getPlayerNumber();
						}
					}
				}

				//top border case (excluding corners)
				if (row == 0 && col != board.length - 1 && col != 0) {
					if (board[0][col - 1] != null && board[0][col + 1] != null && board[row + 1][col] != null) {
						if (board[0][col - 1].getPlayerNumber() == board[0][col + 1].getPlayerNumber() &&
								board[0][col - 1].getPlayerNumber() == board[row + 1][col].getPlayerNumber()) {
							return board[0][col - 1].getPlayerNumber();
						}
					}
				}

				//right border case (excluding corners)
				if (row != 0 && row != board.length - 1 && col == board.length - 1) {
					if (board[row - 1][col] != null && board[row + 1][col] != null && board[row][col - 1] != null) {
						if (board[row - 1][col].getPlayerNumber() == board[row + 1][col].getPlayerNumber() &&
								board[row - 1][col].getPlayerNumber() == board[row][col - 1].getPlayerNumber()) {
							return board[row - 1][col].getPlayerNumber();
						}
					}
				}

				//bottom border case (excluding corners)
				if (row == board.length - 1 && col != board.length - 1 && col != 0) {
					if (board[0][col - 1] != null && board[0][col + 1] != null && board[row - 1][col] != null) {
						if (board[0][col - 1].getPlayerNumber() == board[0][col + 1].getPlayerNumber() &&
								board[0][col - 1].getPlayerNumber() == board[row - 1][col].getPlayerNumber()) {
							return board[0][col - 1].getPlayerNumber();
						}
					}
				}

				//middle of board case (excluding corners and borders)
				if (row != 0 && row != board.length - 1 && col != 0 && col != board.length - 1) {
					if (board[row - 1][col] != null && board[row][col + 1] != null && board[row + 1][col] != null
							&& board[row][col - 1] != null) {
						if (board[row - 1][col].getPlayerNumber() == board[row][col + 1].getPlayerNumber()
								&& board[row - 1][col].getPlayerNumber() == board[row + 1][col].getPlayerNumber()
								&& board[row - 1][col].getPlayerNumber() == board[row][col - 1].getPlayerNumber()) {
							return board[row - 1][col].getPlayerNumber();
						}
					}
				}
			}
		}


		return -1;	
	}
}






