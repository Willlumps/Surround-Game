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
		JMenuBar menus;
		JMenu fileMenu;
		JMenuItem quitItem, newGameItem;

		JFrame frame = new JFrame ("Surround");
		frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);

		fileMenu = new JMenu("File");
		newGameItem = new JMenuItem("New Game");
		quitItem = new JMenuItem("quit");

		fileMenu.add(newGameItem);
		fileMenu.add(quitItem);

		menus = new JMenuBar();
		menus.add(fileMenu);	
		
		frame.setJMenuBar(menus);

		Surround4Panel panel = new Surround4Panel(newGameItem, quitItem);
		frame.add(panel);
		frame.setSize(600, 600);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}
