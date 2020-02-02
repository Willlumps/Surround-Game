package surroundpack;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class Surround4Panel extends JPanel {

    /**2D Array of buttons for the game board**/
    private JButton[][] board;

    private JPanel panel1;
    private int player;
    private ButtonListener listen;
    private JMenuItem quitItem;
    private JMenuItem newGameItem;
    private JMenuItem resetGameItem;
    private Surround4Game game;

    public Surround4Panel(JMenuItem resetGameItem, JMenuItem newGameItem, JMenuItem quitItem) {
        this.resetGameItem = resetGameItem;
        this.newGameItem = newGameItem;
        this.quitItem = quitItem;
        listen = new ButtonListener();

        setLayout(new BorderLayout());
        panel1 = new JPanel();

        setUpGame();

        add(panel1, BorderLayout.CENTER);
		quitItem.addActionListener(listen);
		newGameItem.addActionListener(listen);
		resetGameItem.addActionListener(listen);

	}

	private boolean validGameParam(String input) {
        if (input.matches("[0-9]+") && Integer.parseInt(input) > 3 && Integer.parseInt(input) < 21) {
            return true;
        }
        return false;
    }

    private boolean validNumPlayers(String input) {
        if (input.matches("[0-9]+") && Integer.parseInt(input) > 1) {
            return true;
        }
        return false;
    }

    public void setUpGame() {
        //Prompt user for size of the game board
        String strBoardSize = JOptionPane.showInputDialog(null, "Enter the size of the board:" +
                "Min: 4, Max: 20");
        int bSize = 10;
        //Checks for valid board size (numerical and greater than 3 and less than 20)
        if (validGameParam(strBoardSize)) {
            //convert input value and stores it as an integer
            bSize = Integer.parseInt(strBoardSize);
            //creates a board of specified size with the correct amount of cells
            createBoard(bSize);
            game = new Surround4Game(bSize);
        } else {
            //If input was invalid, warn user and set board to default size (10x10)
            JOptionPane.showMessageDialog(null, "Invalid board size, setting to default size (10x10)");
            createBoard(10);
            game = new Surround4Game(10);
        }

        //Prompt the user for the number of players
        String numPlayers = JOptionPane.showInputDialog(null, "Enter the number of players (At least 2)");
        //Checks for valid number of players
        if (validNumPlayers(numPlayers)) {
            //Sets the number of players to the specified amount
            game.setNumPlayers(Integer.parseInt(numPlayers));
        } else {
            //If input was invalid, warns user and sets the number of players to the default amount (2)
            JOptionPane.showMessageDialog(null, "Invalid number of players, setting to default (2)");
            game.setNumPlayers(2);
        }

        //Prompt the user for which player will go first
        String firstPlayer = JOptionPane.showInputDialog(null, "What player will go first? " +
                "(1 - " + game.getNumPlayers() + ")");
        if (validNumPlayers(firstPlayer) && Integer.parseInt(firstPlayer) <= game.getNumPlayers()) {
            game.setPlayer(Integer.parseInt(firstPlayer));
        } else {
            JOptionPane.showMessageDialog(null, "Invalid player, Player 1 will start");
            game.setPlayer(1);
        }

    }

    private class ButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
			if (e.getSource() == quitItem)
				System.exit(1);

			if (e.getSource() == newGameItem) {
			    panel1.removeAll();
			    setUpGame();
			    panel1.revalidate();
            }

			if (e.getSource() == resetGameItem) {
			    game.reset();

            }

			for (int row = 0; row < board.length; row++)
                for (int col = 0; col < board.length; col++)
                    if (board[row][col] == e.getSource())
                        if (game.select(row, col)) {
                            //		board[row][col].setText(""+game.getCurrentPlayer());
                            player = game.nextPlayer();
                        } else
                            JOptionPane.showMessageDialog(null, "Not a valid square! Pick again.");

            displayBoard(board.length);
            int winner = game.getWinner();
            if (winner != -1) {
                JOptionPane.showMessageDialog(null, "Player " + winner + " Wins!");
                game.reset();
                displayBoard(board.length);
            }
        }
    }

    private void createBoard(int size) {

        board = new JButton[size][size];
        panel1.setLayout(new GridLayout(size, size));

        for (int i = 0; i < size; i++) // rows
            for (int j = 0; j < size; j++) {
                board[i][j] = new JButton(" ");
                board[i][j].addActionListener(listen);
                panel1.add(board[i][j]);
            }
    }

    private void displayBoard(int size) {

        for (int row = 0; row < size; row++)
            for (int col = 0; col < size; col++) {
                Cell c = game.getCell(row, col);
                if (c != null)
                    board[row][col].setText("" + c.getPlayerNumber());
                else
                    board[row][col].setText(" ");
            }
    }


}


