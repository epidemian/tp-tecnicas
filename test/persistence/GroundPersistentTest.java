package persistence;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import model.production.Direction;
import model.production.elements.OutputProductionLineElement;
import model.warehouse.Ground;
import model.warehouse.Position;
import model.warehouse.TileElement;
import model.warehouse.Wall;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.junit.Before;
import org.junit.Test;

import persistence.exceptions.InvalidTagException;
import persistence.ground.GroundPersistent;

public class GroundPersistentTest extends XMLPersistentTest {

	private Ground ground;

	@Before
	public void setUp() {
		super.setUp();
		ground = new Ground(1000, 15, 20);
	}

	@Test
	public void validEmptyGround() throws DocumentException,
			InvalidTagException {

		Document doc = reader.read("test/persistence/input/"
				+ "ValidEmptyGround.xml");

		Element element = doc.getRootElement();

		Ground recovered = GroundPersistent.buildFromXML(element);

		assertEquals(recovered, ground);

	}

	@Test
	public void validGroundWithWalls() throws DocumentException,
			InvalidTagException {

		Wall upperWall = new Wall(10, 1);
		Wall lowerWall = new Wall(10, 1);

		ground.addTileElement(lowerWall, new Position(1, 1));
		ground.addTileElement(upperWall, new Position(11, 1));

		Document doc = reader.read("test/persistence/input/"
				+ "ValidGroundWithWalls.xml");

		Element element = doc.getRootElement();

		Ground recovered = GroundPersistent.buildFromXML(element);

		assertEquals(recovered, ground);
		for (int col = 0; col < recovered.getCols(); col++) {
			for (int row = 0; row < recovered.getRows(); row++) {
				Position pos = new Position(row, col);
				TileElement recoveredElement = recovered.getTileElementAt(pos);
				Class<?> expectedClass = ground.getTileElementAt(pos)
						.getClass();

				assertThat(recoveredElement, is(instanceOf(expectedClass)));
			}
		}
	}
	
	@Test 
	public void validGroundWithOutputAndWalls() 
						throws DocumentException, InvalidTagException{

		Wall upperWall = new Wall(10, 1);
		Wall lowerWall = new Wall(10, 1);

		ground.addTileElement(lowerWall, new Position(1, 1));
		ground.addTileElement(upperWall, new Position(11, 1));

		OutputProductionLineElement prodLineElement1=
			new OutputProductionLineElement(Direction.NORTH);
		OutputProductionLineElement prodLineElement2=
			new OutputProductionLineElement(Direction.SOUTH);
		
		ground.addTileElement(prodLineElement1, new Position(5,6));
		ground.addTileElement(prodLineElement2, new Position(8,7));
		
		Document doc = reader.read("test/persistence/input/"
				+ "ValidGroundWithWallsAndOutputs.xml");

		Element element = doc.getRootElement();

		Ground recovered = GroundPersistent.buildFromXML(element);

		assertEquals(recovered, ground);
		for (int col = 0; col < recovered.getCols(); col++) {
			for (int row = 0; row < recovered.getRows(); row++) {
				Position pos = new Position(row, col);
				TileElement recoveredElement = recovered.getTileElementAt(pos);
				Class<?> expectedClass = ground.getTileElementAt(pos)
						.getClass();

				assertThat(recoveredElement, is(instanceOf(expectedClass)));
			}
		}
	}

}
