package model.warehouse;

import static org.junit.Assert.*;
import model.production.MachineType;
import model.production.ProductionLineElement;
import model.production.ProductionMachine;

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
		assertTrue(ground.isAreaEmpty(Position.ZERO, ground.getRows(), ground
				.getCols()));

	}

	private ProductionLineElement createProdLineElement2x2() {
		MachineType type = new MachineType("testingMachine",2,2);
		return new ProductionMachine(type);
	}

	@Test
	public void IsAreaEmptyWhenAMachineIsPlaced() {

		// Tile starts with no machines
		// One line element is placed on the ground
		ProductionLineElement lineElement = createProdLineElement2x2();
		Position pos = new Position(5, 5);
		ground.addTileElement(lineElement, pos);

		// Check all the tiles occupied by the machine
		assertFalse(ground.isAreaEmpty(pos, 2, 2));

		// Checks when only some of the tiles are occupied
		Position pos2 = pos.add(new Position(1, 1));
		assertFalse(ground.isAreaEmpty(pos2, 2, 2));

	}

	@Test(expected = ArrayIndexOutOfBoundsException.class)
	public void isAreaEmptyWhenMachineIsPlacedOutOfBounds() {

		ProductionLineElement lineElement = createProdLineElement2x2();
		Position pos = new Position(9, 9);
		ground.addTileElement(lineElement, pos);
	}
}
