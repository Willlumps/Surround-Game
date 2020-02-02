package surroundpack;

import javax.swing.*;

public class Surround4 {
	/**
	 * @param args
	 */
	public static void main (String[] args)
	{
		/*  The JMenuBar is associated with the frame. The first step
		 *  is to create the menu items, and file menu.
		 */

		/* JMenuBar for the game */
		JMenuBar menus;

		/* File Menu tab for the JMenuBar */
		JMenu fileMenu;

		/* Menu items for the file menu */
		JMenuItem quitItem, newGameItem, resetGameItem;

		//New JFrame with default close operation
		JFrame frame = new JFrame ("Surround");
		frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);

		//Creates new menu and menu items and labels them appropriately
		fileMenu = new JMenu("File");
		resetGameItem = new JMenuItem("Reset Game");
		newGameItem = new JMenuItem("New Game");
		quitItem = new JMenuItem("Quit");

		//Adds menu items to the menu
		fileMenu.add(resetGameItem);
		fileMenu.add(newGameItem);
		fileMenu.add(quitItem);

		//Adds menu to the MenuBar
		menus = new JMenuBar();
		menus.add(fileMenu);	

		//Adds MenuBar to the JFrame
		frame.setJMenuBar(menus);

		//Creates new game panel and adds the panel to the frame
		Surround4Panel panel = new Surround4Panel(resetGameItem, newGameItem, quitItem);
		frame.add(panel);
		frame.setSize(600, 600);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}
