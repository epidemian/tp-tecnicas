package persistence;

import static org.junit.Assert.assertEquals;
import model.production.RawMaterialType;
import model.production.RawMaterials;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.junit.Before;
import org.junit.Test;

public class RawMaterialsPersistentTest extends XMLPersistentTest{

	RawMaterials rawMaterials;
	
	@Before
	public void setUp(){
		super.setUp();
		rawMaterials=new RawMaterials();
		
		rawMaterials.store(new RawMaterialType("wood"), 75);
		rawMaterials.store(new RawMaterialType("gold"), 100);
		rawMaterials.store(new RawMaterialType("stone"), 200);
		
	}
	
	/**
	 * Testing the recovery of a correct XML file
	 */
	@Test
	public void validRawMaterialsRecovery() 
		throws DocumentException, InvalidTagException{		
		
		Document doc= 
			reader.read("test/persistence/input/ValidRawMaterials.xml");
		Element element=doc.getRootElement();
		
		RawMaterials recovered=
			RawMaterialsPersistent.buildFromXML(element);
		
		assertEquals(recovered,rawMaterials);
	}
	
	/**
	 * A material with the incorrect tag is entered inside the list of materials
	 */
	@Test (expected = InvalidTagException.class)
	public void RawMaterialsWithNonMaterialChild() 
		throws DocumentException, InvalidTagException{		
		
		Document doc= 
			reader.read("test/persistence/input/" +
					"RawMaterialsWithNonMaterialChild.xml");
		Element element=doc.getRootElement();
		
		RawMaterials recovered=
			RawMaterialsPersistent.buildFromXML(element);
		
		assertEquals(recovered,rawMaterials);
	}
}
