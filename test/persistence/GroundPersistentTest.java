package persistence;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import model.warehouse.Ground;
import model.warehouse.Position;
import model.warehouse.TileElement;
import model.warehouse.Wall;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.junit.Before;
import org.junit.Test;

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

		ground.putTileElement(lowerWall, new Position(1, 1));
		ground.putTileElement(upperWall, new Position(11, 1));

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

}
