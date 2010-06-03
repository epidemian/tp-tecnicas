package model.production;

import static org.junit.Assert.*;
import static model.production.ProductionLineElement.connectLineElements;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import model.warehouse.Ground;
import model.warehouse.ProductionLinesCreator;

public class ProductionLinesCreatorTest {

	private ProductionLinesCreator linesCreator;
	private Ground ground;
	private StorageArea storageArea;

	@Before
	public void setUp() {
		this.linesCreator = new ProductionLinesCreator(new StorageArea(
				new RawMaterials(), new ValidProductionSequences()));

		this.ground = new Ground(1000, 10, 10);
		this.storageArea = new StorageArea(new RawMaterials(),
				new ValidProductionSequences());
		this.linesCreator = new ProductionLinesCreator(this.storageArea);
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

		ProductionLine line = ProductionLine.createValidProductionLine(
				prodLineElement1, this.storageArea, new RawMaterials());

		/*
		 * Creates the second line.
		 */
		ProductionLineElement prodLine2Element1 = createProductionMachine("Procesador");
		ProductionLineElement prodLine2Element2 = new Conveyor();
		ProductionLineElement prodLine2Element3 = createProductionMachine("Super-Machine");

		connectLineElements(prodLine2Element1, prodLine2Element2);
		connectLineElements(prodLine2Element2, prodLine2Element3);

		ProductionLine line2 = ProductionLine.createValidProductionLine(
				prodLine2Element1, this.storageArea, new RawMaterials());

		/*
		 * Creates the third line.
		 */
		ProductionLineElement prodLine3Element1 = createProductionMachine("Isolated");

		ProductionLine line3 = ProductionLine.createValidProductionLine(
				prodLine3Element1, this.storageArea, new RawMaterials());

		/*
		 * Puts the lines in the ground.
		 */
		ground.getTile(3, 2).setTileElement(prodLineElement1);
		ground.getTile(2, 2).setTileElement(prodLineElement2);
		ground.getTile(2, 3).setTileElement(prodLineElement3);
		ground.getTile(3, 4).setTileElement(prodLineElement4);
		ground.getTile(4, 4).setTileElement(prodLineElement5);

		ground.getTile(5, 5).setTileElement(prodLine2Element1);
		ground.getTile(6, 5).setTileElement(prodLine2Element2);
		ground.getTile(6, 6).setTileElement(prodLine2Element3);

		/*
		 * Isolated machine.
		 */
		ground.getTile(8, 8).setTileElement(prodLine3Element1);

		List<ProductionLine> linesCreated = new ArrayList<ProductionLine>();
		linesCreated.add(line);
		linesCreated.add(line2);
		linesCreated.add(line3);

		return linesCreated;
	}

	private ProductionLineElement createProductionMachine(String typeName) {
		return new ProductionMachine(new MachineType(typeName), 1, 1);
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
