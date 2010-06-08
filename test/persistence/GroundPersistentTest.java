package persistence;

import static org.junit.Assert.assertEquals;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.junit.Before;
import org.junit.Test;

import persistence.GroundPersistent;
import persistence.InvalidTagException;
import model.warehouse.Ground;

public class GroundPersistentTest extends XMLPersistentTest {

	private Ground ground;
	
	@Before
	public void setUp(){
		super.setUp();
		ground=new Ground(1000,10,30);
	}

	@Test
	public void validGround()
		throws DocumentException, InvalidTagException{
			
			Document doc= reader.read("test/persistence/ValidGround.xml");
			
			Element element=doc.getRootElement();
			
			Ground recovered = GroundPersistent.buildFromXML(element);
			
			assertEquals(recovered,ground);
		
	}
}
