package surroundpack;

import static org.junit.Assert.*;

import org.junit.Test;

public class testingSurrGame {

	//Testing for upper left corner win
	@Test
	public void testCornersUL() {
		Surround4Game g = new Surround4Game();
		g.select(0, 1);
		g.nextPlayer();
		g.select(0,0);
		g.nextPlayer();
		g.select(1, 0);
		assertTrue(g.getWinner() == 1);
	}

	//Testing for upper right corner win
	@Test
	public void testCornersUR() {
		Surround4Game g = new Surround4Game();
		g.select(0, 8);
		g.nextPlayer();
		g.select(0,9);
		g.nextPlayer();
		g.select(1, 9);
		assertTrue(g.getWinner() == 1);
	}

	//Testing for lower left corner win
	@Test
	public void testCornersLL() {
		Surround4Game g = new Surround4Game();
		g.select(8, 0);
		g.nextPlayer();
		g.select(9,0);
		g.nextPlayer();
		g.select(9, 1);
		assertTrue(g.getWinner() == 1);
	}

	//Testing for lower right corner win
	@Test
	public void testCornersLR() {
		Surround4Game g = new Surround4Game();
		g.select(9, 8);
		g.nextPlayer();
		g.select(9,9);
		g.nextPlayer();
		g.select(8, 9);
		assertTrue(g.getWinner() == 1);
	}

	//Testing for upper border win
	@Test
	public void testUpperBorder() {
		Surround4Game g = new Surround4Game();
		g.select(0, 0);
		g.nextPlayer();
		g.select(0,1);
		g.nextPlayer();
		g.select(0, 2);
		g.nextPlayer();
		g.select(9, 2);
		g.nextPlayer();
		g.select(1, 1);
		assertTrue(g.getWinner() == 1);
	}

	//Testing for right border win
	@Test
	public void testRightBorder() {
		Surround4Game g = new Surround4Game();
		g.select(0, 9);
		g.nextPlayer();
		g.select(1,9);
		g.nextPlayer();
		g.select(2, 9);
		g.nextPlayer();
		g.select(0, 0);
		g.nextPlayer();
		g.select(1, 8);
		assertTrue(g.getWinner() == 1);
	}

	//Testing for bottom border win
	@Test
	public void testBottomBorder() {
		Surround4Game g = new Surround4Game();
		g.select(9, 0);
		g.nextPlayer();
		g.select(9,1);
		g.nextPlayer();
		g.select(9, 2);
		g.nextPlayer();
		g.select(0, 0);
		g.nextPlayer();
		g.select(8, 1);
		assertTrue(g.getWinner() == 1);
	}

	//Testing for left border win
	@Test
	public void testLeftBorder() {
		Surround4Game g = new Surround4Game();
		g.select(0, 0);
		g.nextPlayer();
		g.select(1,0);
		g.nextPlayer();
		g.select(2, 0);
		g.nextPlayer();
		g.select(0, 9);
		g.nextPlayer();
		g.select(1, 1);
		assertTrue(g.getWinner() == 1);
	}

	//Testing for a middle of the board win
	@Test
	public void middleBoard() {
		Surround4Game g = new Surround4Game();
		g.select(2, 2);
		g.nextPlayer();
		g.select(3,2);
		g.nextPlayer();
		g.select(4, 2);
		g.nextPlayer();
		g.select(0, 0);
		g.nextPlayer();
		g.select(3, 1);
		g.nextPlayer();
		g.select(9,9);
		g.nextPlayer();
		g.select(3, 3);
		assertTrue(g.getWinner() == 1);
	}
}
