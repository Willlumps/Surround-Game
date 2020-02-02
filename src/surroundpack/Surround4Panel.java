package surroundpack;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

import javax.swing.*;

public class Surround4Panel extends JPanel {

    /* 2D Array of buttons for the game board */
    private JButton[][] board;

    /* Panel that will contain all of the games JButtons */
    private JPanel panel1;

    /* The player of the game */
    private int player;

    /* Listener for all of the buttons on the board */
    private ButtonListener listen;

    /* Quit menu item - used for exiting the game */
    private JMenuItem quitItem;

    /* New Game menu item - used for starting the game fresh (new board size, players, etc. */
    private JMenuItem newGameItem;

    /* Reset game menu item - used to reset the game board a blank board */
    private JMenuItem resetGameItem;

    /* Surround game that holds the game logic */
    private Surround4Game game;


    /**
     *
     *   This constructor creates a Surround game with given menu items
     *
     * @param resetGameItem Used to reset the game board
     * @param newGameItem Used to start a new game with a different booard size and/or
     *                    number of players.
     * @param quitItem Used to exit the game
     */

    public Surround4Panel(JMenuItem resetGameItem, JMenuItem newGameItem, JMenuItem quitItem) {
        //sets the menu items to the passed parameters
        this.resetGameItem = resetGameItem;
        this.newGameItem = newGameItem;
        this.quitItem = quitItem;

        //Creates new ButtonListener
        listen = new ButtonListener();

        //Sets the layout of the panel
        setLayout(new BorderLayout());

        //Creates new panel for the game
        panel1 = new JPanel();

        //Sets up the game board with input received from the user
        setUpGame();

        //centers the panel in the middle of the frame
        add(panel1, BorderLayout.CENTER);

        //action listeners for the menu items
		quitItem.addActionListener(listen);
		newGameItem.addActionListener(listen);
		resetGameItem.addActionListener(listen);
	}


    /**
     *  Validates whether or not the user entered a valid game board size
     *
     * @param input The input received from the user
     * @return True if the input was a valid board size, false otherwise
     */

	private boolean validGameParam(String input) {
        //Checks for an integer value between 4 and 20
	    if (input.matches("[0-9]+") && Integer.parseInt(input) > 3 && Integer.parseInt(input) < 21) {
            return true;
        }
        return false;
    }


    /**
     *  Validates whether or not the user entered a valid number of players
     *
     * @param input The input received from the user
     * @return True if the input was a valid number of players, false otherwise
     */

    private boolean validNumPlayers(String input) {
        //Checks for an integer value greater than one
        if (input.matches("[0-9]+") && Integer.parseInt(input) > 1) {
            return true;
        }
        return false;
    }


    /**
     *  Validates whether or not the user entered a valid starting player (between
     *  and the number of players.)
     *
     * @param input The input received from the user
     * @return True if the input was a valid player of the game, false otherwise
     */

    private boolean validFirstPlayer(String input) {
        //Checks for an integer value greater than zero and less than or equal to the number of players
        if (input.matches("[0-9]+") && Integer.parseInt(input) > 0 && Integer.parseInt(input) <= game.getNumPlayers()) {
            return true;
        }
        return false;
    }


    /**
     *  Sets up the game board by prompting the user for the board size, number of
     *  players, and which player will go first.
     *
     */

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
        //Checks for valid starting player
        if (validFirstPlayer(firstPlayer)) {
            //sets the starting player to the specified player
            game.setPlayer(Integer.parseInt(firstPlayer));
        } else {
            //If input was not a valid player, warns user and sets the starting player to player one
            JOptionPane.showMessageDialog(null, "Invalid player, Player 1 will start");
            game.setPlayer(1);
        }
    }


    /**
     *  Inner class to respond to user action
     *
     */

    private class ButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            //Exits the game if the quit menu item was selected
            if (e.getSource() == quitItem)
                System.exit(1);

            //Removes all components from the game panel and goes through game setup once again prompting
            //the user for the necessary info
            if (e.getSource() == newGameItem) {
                panel1.removeAll();
                setUpGame();
                panel1.repaint();
                panel1.revalidate();
            }

            //resets the game board back to its initial state with the player who went first last time, to
            //make the first move again.
            if (e.getSource() == resetGameItem) {
                game.reset();
                resetBack(board.length);
                displayBoard(board.length);
                game.setPlayer(game.getFirstPlayer());

            }

            //loops through the game squares to check if one was pressed
            for (int row = 0; row < board.length; row++) {
                for (int col = 0; col < board.length; col++) {
                    //If a square was pressed, execute the following
                    if (board[row][col] == e.getSource()) {
                        //checks if the selected square is null (empty)
                        if (game.select(row, col)) {
                            //if empty, sets the square to display the number of the player that pressed it

                            board[row][col].setText("" + game.getCurrentPlayer());
                            board[row][col].setBackground(Color.pink);
                            player = game.nextPlayer();
                            AI();


                            //moves onto the next player
                            //player = game.nextPlayer();
                        } else {
                            //If the button pressed was already in play, warn user prompt them to pick another square
                            JOptionPane.showMessageDialog(null, "Not a valid square! Pick again.");
                        }
                    }
                }
            }

            //Displays the correct number on each square on the board
            displayBoard(board.length);

            //Calls getWinner method to check for a winner
            int winner = game.getWinner();


            if (winner != -1) {
                //If there is a winner, display the winning player in a popup
                JOptionPane.showMessageDialog(null, "Player " + winner + " Wins!");
                //resets game board to its initial state
                game.reset();
                resetBack(board.length);
                displayBoard(board.length);
                game.setPlayer(game.getFirstPlayer());
            }
        }
    }

    public int checkAdjacent(int row, int col) {
        int count = 0;

        if (row != 0) {
            count++;
        }
        if (row != board.length) {
            count++;
        }
        if (col != 0) {
            count++;
        }
        if (col != board.length) {
            count++;
        }
        return count;
    }

    public void AI() {

        Map<TestObject, Integer> test = new HashMap<TestObject, Integer>();



        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board.length; col++) {
                //System.out.println(game.getCell(row, col));

                if (game.getCell(row, col) != null && game.getCurrentPlayer() == 1) {
                    int count = checkAdjacent(row, col);
                    TestObject obj = new TestObject(row, col, game.getClass());

                    test.put(obj, count);

                }

                for(Map.Entry<TestObject, Integer> entry : test.entrySet()) {
                    TestObject key = entry.getKey();
                    Integer value = entry.getValue();

                    System.out.println("" + key + " " + value);
                }
//                if (game.getCell(row, col) != null) {
//                    if (game.getCell(row, col).getPlayerNumber() == 2) {
//                        if (game.select(row + 1, col)) {
//                            board[row + 1][col].setText("" + game.getCurrentPlayer());
//                            player = game.nextPlayer();
//                        }
//
//                    }
//                }

            }

        }

    }

    private void resetBack(int size) {
        for (int i = 0; i < size; i++) {           // rows
            for (int j = 0; j < size; j++) {
                board[i][j].setBackground(null);
            }
        }
    }


    /**
     *  Creates a game board of specified size and creates a button for each "square" on the board
     * @param size The size of the game board with an equal number of rows and columns per the
     *             passed value
     */

    private void createBoard(int size) {

        //Creates a new 2D array of the passed size
        board = new JButton[size][size];
        //Sets the layout of the panel to a GridLayout of the passed size
        panel1.setLayout(new GridLayout(size, size));

        //loops through each row and column of the panel
        for (int i = 0; i < size; i++)           // rows
            for (int j = 0; j < size; j++) {     // columns
                //Creates a button for the specific row and column spot
                board[i][j] = new JButton(" ");
                //Adds an ActionListener to the button
                board[i][j].addActionListener(listen);
                //Adds the button to the panel
                panel1.add(board[i][j]);
            }
    }


    /**
     *  Displays the game board with the correct value on each of the squares
     * @param size The size of the game board so the correct number of squares
     *             can be displayed.
     */
    private void displayBoard(int size) {

        //iterates over every button on the panel
        for (int row = 0; row < size; row++)        // rows
            for (int col = 0; col < size; col++) {  // columns
                //Gets the current cell being iterated over
                Cell c = game.getCell(row, col);
                //if the cell is not null (empty), set the text on the button to the player that clicked it.
                if (c != null)
                    board[row][col].setText("" + c.getPlayerNumber());
                else
                    //Otherwise leaves the button blank
                    board[row][col].setText("");
            }
    }
}


