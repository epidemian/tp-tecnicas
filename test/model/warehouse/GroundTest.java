package model.warehouse;

import static org.junit.Assert.*;
import model.exception.BusinessLogicException;
import model.production.MachineType;
import model.production.ProductionLineElement;
import model.production.ProductionMachine;

import org.junit.Before;
import org.junit.Test;

public class GroundTest {

	private static final int WIDTH = 15;
	private static final int HEIGHT = 10;
	Ground ground;

	@Before
	public void setUp() {
		ground = new Ground(1000, HEIGHT, WIDTH);
	}

	@Test
	public void canAddTileElementByDimensionWithGroundDimensions() {
		assertTrue(ground.canAddTileElementByDimension(WIDTH, HEIGHT,
				Position.ZERO));

	}

	@Test
	public void canAddTileElementByDimensionWithOutOfBoundDimensions() {
		Position[] invalidPositions = { new Position(-1, 0),
				new Position(0, -1), new Position(0, WIDTH),
				new Position(HEIGHT, 0) };

		for (Position invalidPos : invalidPositions)
			assertFalse(ground.canAddTileElementByDimension(1, 1, invalidPos));
	}

	private ProductionLineElement createProdLineElement2x2() {
		MachineType type = new MachineType("testingMachine", 2, 2);
		return new ProductionMachine(type);
	}

	@Test
	public void canAddTileElementWhenAreaIsEmpty() {
		assertTrue(ground.canAddTileElement(createProdLineElement2x2(),
				new Position(5, 5)));
	}

	@Test
	public void canAddTileElementWhenAreaIsNotEmpty() {

		// Tile starts with no machines
		// One line element is placed on the ground
		ProductionLineElement lineElement = createProdLineElement2x2();
		Position pos = new Position(5, 5);
		ground.addTileElement(lineElement, pos);

		// Check all the tiles occupied by the machine
		assertFalse(ground.canAddTileElement(lineElement, pos));

		// Checks when only some of the tiles are occupied
		Position pos2 = pos.add(new Position(1, 1));
		assertFalse(ground.canAddTileElement(lineElement, pos2));

	}

	@Test(expected = BusinessLogicException.class)
	public void businessLogicExceptionIsThrownWhenMachineIsPutOutOfBounds() {

		ProductionLineElement lineElement = createProdLineElement2x2();
		Position pos = new Position(HEIGHT - 1, WIDTH -1);
		ground.addTileElement(lineElement, pos);
	}
}
