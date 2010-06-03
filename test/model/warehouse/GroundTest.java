package model.warehouse;

import static org.junit.Assert.*;

import model.production.*;

import org.junit.Before;
import org.junit.Test;

public class GroundTest {

	Ground ground;

	@Before
	public void setUp() {
		ground = new Ground(1000, 10, 10);
	}

	@Test
	public void IsAreaEmptyWhenEmpty() {
		// Tile starts with no machines
		for (int cols = 0; cols < ground.getCols(); cols++) {
			for (int rows = 0; rows < ground.getRows(); rows++) {
				assertTrue(ground.isAreaEmpty(cols, rows, 1, 1));
			}
		}

	}

	private ProductionLineElement createProdLineElement2x2() {
		MachineType type = new MachineType("testingMachine");
		return new ProductionMachine(type, null,
				null, 2, 2);
	}

	@Test
	public void IsAreaEmptyWhenAMachineIsPlaced() {

		// Tile starts with no machines
		// One line element is placed on the ground
		ProductionLineElement lineElement = createProdLineElement2x2();

		int row = 5;
		int col = 5;

		for (int i = 0; i < lineElement.getWidth(); i++) {
			for (int j = 0; j < lineElement.getHeight(); j++) {
				ground.getTile(row + j, col + i).setTileElement(lineElement);
			}
		}

		// Check all the tiles occupied by the machine
		assertFalse(ground.isAreaEmpty(row, col, 2, 2));

		// Checks when only some of the tiles are occupied
		assertFalse(ground.isAreaEmpty(6, 6, 2, 2));

	}

	@Test(expected = ArrayIndexOutOfBoundsException.class)
	public void isAreaEmptyWhenMachineIsPlacedOutOfBounds() {

		ProductionLineElement lineElement = createProdLineElement2x2();

		int row = 9;
		int col = 9;

		for (int i = 0; i < lineElement.getWidth(); i++) {
			for (int j = 0; j < lineElement.getHeight(); j++) {
				ground.getTile(row + j, col + i).setTileElement(lineElement);
			}
		}

	}
}
