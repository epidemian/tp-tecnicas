package persistence;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import model.warehouse.Ground;
import model.warehouse.Position;
import model.warehouse.Wall;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class GroundPersistentTest extends XMLPersistentTest {

	private Ground ground;
	
	@Before
	public void setUp(){
		super.setUp();
		ground=new Ground(1000,15,20);
	}
	
	@Test
	public void validEmptyGround()
		throws DocumentException, InvalidTagException{
			
			Document doc= reader.read("test/persistence/input/" +
					"ValidEmptyGround.xml");
			
			Element element=doc.getRootElement();
			
			Ground recovered = GroundPersistent.buildFromXML(element);
			
			assertEquals(recovered,ground);
		
	}
	
	// TODO Refactorize Position Caculation (?) 
	// TODO reimplement equals of ground...
	@Test
	public void validGroundWithWalls() 
			throws DocumentException, InvalidTagException{
		
		
		Wall upperWall=new Wall(10,1);
		Wall lowerWall=new Wall(10,1);
			
		ground.addTileElement(lowerWall,new Position(1,1));
		ground.addTileElement(upperWall,new Position(11,1));
		
		Document doc= reader.read("test/persistence/input/" +
						"ValidGroundWithWalls.xml");
		
		Element element=doc.getRootElement();		
		
		Ground recovered = GroundPersistent.buildFromXML(element);
		
		assertEquals(recovered,ground);
		for (int col=0;col<recovered.getCols();col++){
			for (int row=0;row<recovered.getRows();row++){
				assertEquals(recovered.getTileElementAt(new Position(row,col)),
						ground.getTileElementAt(new Position(row,col)));
			}
		}
	}
	
}
