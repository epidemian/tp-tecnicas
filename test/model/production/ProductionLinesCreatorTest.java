package model.production;

import static model.production.line.ProductionLine.*;
import static model.production.TestUtils.*;
import static model.production.elements.ProductionLineElement.connectLineElements;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import model.game.Budget;
import model.production.elements.Conveyor;
import model.production.elements.ProductionLineElement;
import model.production.elements.machine.MachineType;
import model.production.elements.machine.ProductionMachine;
import model.production.line.ProductionLine;
import model.warehouse.Ground;
import model.warehouse.MarketPrices;
import model.warehouse.Position;
import model.warehouse.ProductionLinesCreator;
import model.warehouse.Warehouse;

import org.junit.Before;
import org.junit.Test;

public class ProductionLinesCreatorTest {

	private ProductionLinesCreator linesCreator;
	private Ground ground;

	@Before
	public void setUp() {
		this.ground = new Ground(1000, 10, 10);
		Warehouse warehouse = Warehouse.purchaseWarehouse(this.ground,
				new Budget(), new MarketPrices(),
				new ValidProductionSequences());
		this.linesCreator = new ProductionLinesCreator(warehouse);

	}

	/*
	 * Generates context!
	 */
	private List<ProductionLine> putProductionLineElements(Ground ground) {

		/*
		 * Creates the first line.
		 */
		ProductionLineElement prodLineElement1 = createProductionMachine("Licuado");
		ProductionLineElement prodLineElement2 = new Conveyor();
		ProductionLineElement prodLineElement3 = createProductionMachine("Haz");
		ProductionLineElement prodLineElement4 = new Conveyor();
		ProductionLineElement prodLineElement5 = createProductionMachine("Horno");

		connectLineElements(prodLineElement1, prodLineElement2);
		connectLineElements(prodLineElement2, prodLineElement3);
		connectLineElements(prodLineElement3, prodLineElement4);
		connectLineElements(prodLineElement4, prodLineElement5);

		ProductionLine line = createDisfunctionalProductionLine(prodLineElement1);

		/*
		 * Creates the second line.
		 */
		ProductionLineElement prodLine2Element1 = createProductionMachine("Procesador");
		ProductionLineElement prodLine2Element2 = new Conveyor();
		ProductionLineElement prodLine2Element3 = createProductionMachine("Super-Machine");

		connectLineElements(prodLine2Element1, prodLine2Element2);
		connectLineElements(prodLine2Element2, prodLine2Element3);

		ProductionLine line2 = createDisfunctionalProductionLine(prodLine2Element1);

		/*
		 * Creates the third line.
		 */
		ProductionLineElement prodLine3Element1 = createProductionMachine("Isolated");

		ProductionLine line3 = createDisfunctionalProductionLine(prodLine3Element1);

		/*
		 * Puts the lines in the ground.
		 */
		ground.addTileElement(prodLineElement1, new Position(3, 2));
		ground.addTileElement(prodLineElement2, new Position(2, 2));
		ground.addTileElement(prodLineElement3, new Position(2, 3));
		ground.addTileElement(prodLineElement4, new Position(3, 4));
		ground.addTileElement(prodLineElement5, new Position(4, 4));

		ground.addTileElement(prodLine2Element1, new Position(5, 5));
		ground.addTileElement(prodLine2Element2, new Position(6, 5));
		ground.addTileElement(prodLine2Element3, new Position(6, 6));

		/*
		 * Isolated machine.
		 */
		ground.addTileElement(prodLine3Element1, new Position(8, 8));

		List<ProductionLine> linesCreated = new ArrayList<ProductionLine>();
		linesCreated.add(line);
		linesCreated.add(line2);
		linesCreated.add(line3);

		return linesCreated;
	}

	private ProductionLineElement createProductionMachine(String typeName) {
		return new ProductionMachine(createMachineType(typeName, 1, 1));
	}

	@Test
	public void createFromGroundTest() {

		/*
		 * Context.
		 */
		List<ProductionLine> linesExpected = this
				.putProductionLineElements(this.ground);

		List<ProductionLine> linesGround = this.linesCreator
				.createFromGround(ground);

		assertEquals(linesExpected.size(), linesGround.size());

		for (int i = 0; i < linesExpected.size(); i++)
			assertEquals(linesExpected.get(i).getLineElements(), linesGround
					.get(i).getLineElements());
	}
}
