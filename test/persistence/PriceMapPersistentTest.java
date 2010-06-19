package persistence;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.junit.Before;
import org.junit.Test;

import persistence.exceptions.InvalidTagException;
import persistence.price.PriceMapPersistent;

public class PriceMapPersistentTest extends XMLPersistentTest{
	
	Map<String, Integer> map;
	
	@Before
	public void setUp(){
		super.setUp();
		map=new HashMap<String, Integer>();
		map.put("Gold", 100);
		map.put("Sword", 1000);
	}
	
	@Test
	public void validPriceMap()
		throws DocumentException, InvalidTagException{		
			
			Document doc= 
				reader.read("test/persistence/input/ValidPriceList.xml");
			Element element=doc.getRootElement();
			
			Map<String, Integer> recovered=
				PriceMapPersistent.buildFromXML(element);
			
			assertEquals(recovered,map);
			
	}

}
