package persistence;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.warehouse.Ground;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.junit.Before;
import org.junit.Test;

import persistence.exceptions.InvalidTagException;
import persistence.ground.GroundListPersistent;

public class ConfigTest extends XMLPersistentTest{

	Map<String, String> map;

	@Before
	public void setUp() {
		super.setUp();
		map = new HashMap<String, String>();
		map.put("BROKEN_PRICE_REPAIR_COEF", "0.03");
		map.put("DAMAGED_PRICE_REPAIR_COEF", "0.03");
	}
	
	
	@Test
	public void validConfigFileTest() throws DocumentException,
			InvalidTagException{

		Document doc= 
			reader.read("test/persistence/input/Config.xml");
		Element element=doc.getRootElement();
		
		Map<String, String> recovered = XMLConfig.buildFromXML(element);
		
		assertEquals(recovered,map);
	}
}
