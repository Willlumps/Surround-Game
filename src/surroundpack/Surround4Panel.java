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

    /* Panel that keeps track of the number of wins each player has */
    private JPanel panel2;

    /* Array of JLabels that displays the number of wins each player has */
    private JLabel[] wins;

    /* Map that whose Key is the player number and Value is their number of wins */
    private Map<String, Integer> win;

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

    /* Boolean value that determines if the computer will make a play or not */
    private static boolean AI = false;


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

        panel2 = new JPanel(new GridLayout(0, 1));

        //Sets up the game board with input received from the user
        setUpGame();

        //centers the panel in the middle of the frame
        add(panel1, BorderLayout.CENTER);
        add(panel2, BorderLayout.PAGE_END);

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

        //If the number of players is two, prompt the user if they would like to play against the computer
        if (game.getNumPlayers() == 2) {
            int dialogResult = JOptionPane.showConfirmDialog (null, "Would you like to play against the computer?");
            //If yes, sets the AI value to true
            //If this value is true it will allow the AI method to execute;
            if(dialogResult == JOptionPane.YES_OPTION){
                AI = true;
            }
        }

        //If the computer is to make the first move, a random square will be selected on the board
        if (AI == true) {
            Random rand = new Random();

            //Random number between 0 and the board length - 1 are generated for the first play.
            int randRow = rand.nextInt(board.length - 1);
            int randCol = rand.nextInt(board.length - 1);

            //Sets the tile and switches to the next player
            if (game.select(randRow, randCol)) {
                board[randRow][randCol].setText("" + game.getCurrentPlayer());
                player = game.nextPlayer();
            }
        }

        //Sets up the panel that displays the number of wins each player has
        createWinners(game.getNumPlayers());
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
                            player = game.nextPlayer();
                            resetBack(board.length);

                            for (int r = 0; r < board.length; r++) {
                                for (int c = 0; c < board.length; c++) {
                                    game.checkForOpponents(r, c);
                                    paintBackground(r, c);
                                }
                            }
                            AI();
                            resetBack(board.length);

                            for (int r = 0; r < board.length; r++) {
                                for (int c = 0; c < board.length; c++) {
                                    game.checkForOpponents(r, c);
                                    paintBackground(r, c);
                                }
                            }

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
            //TODO CHECK IF EVERY SQUARE IS FILLED BUT THERE ISN'T A WINNER
            //TODO THEN GIVE A POPUP INDICATING SO AND RESET THE GAME

            if (winner != -1) {
                //If there is a winner, display the winning player in a popup
                JOptionPane.showMessageDialog(null, "Player " + winner + " Wins!");
                //resets game board to its initial state
                game.reset();
                resetBack(board.length);
                displayBoard(board.length);
                game.setPlayer(game.getFirstPlayer());

                //Loops through the players and updates the win total if they won the previous match.
                for (int i = 1; i < game.getNumPlayers() + 1; i++) {
                    if (winner == i) {
                        //replaces the value (number of wins) with the previous amount plus one
                        win.replace("" + i, win.get("" + i) + 1);
                    }
                    //updates the JLabels to display the correct number of wins.
                    wins[i].setText("Player " + i + " Wins: " + win.get("" + i));
                }
            }
        }
    }


    /**
     *  Changes the background color of the game tiles depending on whose turn it is and the risk level with respect
     *  to how close that tile is to being surrounded.
     *  Green indicates low risk.
     *  Yellow indicates medium risk.
     *  Red indicates high risk, this tile is about to be surrounded
     *
     * @param row The row that the tile is located in
     * @param col The col that the tile is located in
     *
     */

    private void paintBackground(int row, int col) {

        // Everything in this function follows simple guidelines in order to display the correct tile background
        // First it will check if the square is is null
        // If not it will pull the tile's propertyColor value
        // Depending on the position of the tile (corner/border/middle) and its property color it will set
        // The background of the tile in in relation to those two things as long as the current player
        // And the tile it is currently checking match up
        // For example, if the current tile belongs to player 2, it will only display displayed when it is player 2's
        // Turn to play, further only cells belonging to player 1 will be colored when it is player ones turn to play.

        // The corner tiles only have one color to check for as if both of its neighboring tiles are occupied
        // the game has ended or that tile can no longer be used to win
        // The corner pieces can be either red, indicating immediate risk of losing or
        // default (blue) indicating no risk

        //Top Left Corner
        if (row == 0 && col == 0 && game.getCell(0, 0) != null) {
            if (game.getCell(row, col).getPropertyColor() == 2) {
                if (game.getCurrentPlayer() == game.getCell(row, col).getPlayerNumber()) {
                    board[row][col].setBackground(Color.pink);
                }
            }
        }
        //Top Right Corner
        else if (row == 0 && col == board.length - 1 && game.getCell(row, col) != null) {
            if (game.getCell(row, col).getPropertyColor() == 2) {
                if (game.getCurrentPlayer() == game.getCell(row, col).getPlayerNumber()) {
                    board[row][col].setBackground(Color.pink);
                }
            }
        }
        //Bottom Left Corner
        else if (row == board.length - 1 && col == 0 && game.getCell(row, col) != null) {
            if (game.getCell(row, col).getPropertyColor() == 2) {
                if (game.getCurrentPlayer() == game.getCell(row, col).getPlayerNumber()) {
                    board[row][col].setBackground(Color.pink);
                }
            }
        }
        //Bottom Right Corner
        else if (row == board.length - 1 && col == board.length - 1 && game.getCell(row, col) != null) {
            if (game.getCell(row, col).getPropertyColor() == 2) {
                if (game.getCurrentPlayer() == game.getCell(row, col).getPlayerNumber()) {
                    board[row][col].setBackground(Color.pink);
                }
            }
        }
        // The bordering tiles (those that are around the perimeter of the game board) are checked for two colors
        // Yellow, indicating that one neighboring tile is occupied by an opponent
        // Red, indicating two neighboring tiles are occupied and this player is at risk of losing the game.
        // Otherwise the color is default (blue)

        //Top Border
        else if (row == 0 && game.getCell(row, col) != null) {
            if (game.getCell(row, col).getPropertyColor() == 2) {
                if (game.getCurrentPlayer() == game.getCell(row, col).getPlayerNumber()) {
                    board[row][col].setBackground(Color.decode("#e1e4af"));
                }
            } else if (game.getCell(row, col).getPropertyColor() == 3) {
                if (game.getCurrentPlayer() == game.getCell(row, col).getPlayerNumber()) {
                    board[row][col].setBackground(Color.pink);
                }
            }
        }
        //Left Border
        else if (col == 0 && game.getCell(row, col) != null) {
            if (game.getCell(row, col).getPropertyColor() == 2) {
                if (game.getCurrentPlayer() == game.getCell(row, col).getPlayerNumber()) {
                    board[row][col].setBackground(Color.decode("#e1e4af"));
                }
            } else if (game.getCell(row, col).getPropertyColor() == 3) {
                if (game.getCurrentPlayer() == game.getCell(row, col).getPlayerNumber()) {
                    board[row][col].setBackground(Color.pink);
                }
            }
        }
        //Bottom Border
        else if (row == board.length - 1 && game.getCell(row, col) != null) {
            if (game.getCell(row, col).getPropertyColor() == 2) {
                if (game.getCurrentPlayer() == game.getCell(row, col).getPlayerNumber()) {
                    board[row][col].setBackground(Color.decode("#e1e4af"));
                }
            } else if (game.getCell(row, col).getPropertyColor() == 3) {
                if (game.getCurrentPlayer() == game.getCell(row, col).getPlayerNumber()) {
                    board[row][col].setBackground(Color.pink);
                }
            }
        }
        //Right Border
        else if (col == board.length - 1 && game.getCell(row, col) != null) {
            if (game.getCell(row, col).getPropertyColor() == 2) {
                if (game.getCurrentPlayer() == game.getCell(row, col).getPlayerNumber()) {
                    board[row][col].setBackground(Color.decode("#e1e4af"));
                }
            } else if (game.getCell(row, col).getPropertyColor() == 3) {
                if (game.getCurrentPlayer() == game.getCell(row, col).getPlayerNumber()) {
                    board[row][col].setBackground(Color.pink);
                }
            }
        }
        // Any Tile in the middle of the board can be one of four colors.
        // Default (blue), indicates no neighboring opponents or the tile is at no risk of causing a loss
        // Green, the tile is surrounded by one opposing tile
        // Yellow, the tile is surrounded by two opposing tiles
        // Red, the tile is surrounded by three opposing tiles and is at risk of losing

        else if (row != 0 && col != 0 && row != board.length - 1 && col != board.length - 1 && game.getCell(row, col) != null){
            if (game.getCell(row, col).getPropertyColor() == 2) {
                if (game.getCurrentPlayer() == game.getCell(row, col).getPlayerNumber()) {
                    board[row][col].setBackground(Color.decode("#b4d88d"));
                }
            } else if (game.getCell(row, col).getPropertyColor() == 3) {
                if (game.getCurrentPlayer() == game.getCell(row, col).getPlayerNumber()) {
                    board[row][col].setBackground(Color.decode("#e1e4af"));
                }
            } else if (game.getCell(row, col).getPropertyColor() == 4) {
                if (game.getCurrentPlayer() == game.getCell(row, col).getPlayerNumber()) {
                    board[row][col].setBackground(Color.pink);
                }
            }
        }
    }


    /**
     *  If the AI is enabled, displays the latest move that the computer has completed.
     *
     */

    private void AI() {
        //If the AI variable is false, the computer will not make any plays, leaving two players to play against eachother.
        if (AI == false) {
            return;
        }

        //Receives the cell with the location of the next play sets it in a temporary cell.
        Cell nextMove = game.AI();

        //If the cell is not null, it will set the text to that tile and move onto the next player
        if (nextMove != null) {
            board[nextMove.getRow()][nextMove.getCol()].setText("" + game.getCurrentPlayer());
            player = game.nextPlayer();
        }
    }


    /**
     *  Resets the background of every tile on the board to its default color.
     *
     * @param size The size (width and height) of the game board.
     */

    private void resetBack(int size) {
        //Iterates through the entire game board and sets each tile to the default background color (blue)
        for (int i = 0; i < size; i++) {
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


    /**
     *  Creates a Map that holds the number of each player's number of wins and displays them on a Label.
     *  These labels are added to a panel that will go on the main GUI of the program
     *
     * @param size The number of players in the game.
     */

    private void createWinners(int size) {
        //Creates a new array of JLabels
        wins = new JLabel[size + 1];
        //Creates a new map with the Key being the player number and value the number of wins that player has.
        win = new HashMap<String, Integer>();

        //removes any existing labels from the panel.
        panel2.removeAll();

        for (int i = 1; i < size + 1; i++) {

            //Adds a new entry in the map with the player number and sets their wins to zero
            win.put("" + i, 0);
            //Adds the information from the map and displays it on a JLabel
            wins[i] = new JLabel("Player " + i + " Wins: " + win.get("" + i));

            //Adds the new JLabel to a the panel.
            panel2.add(wins[i]);
        }
    }
}


