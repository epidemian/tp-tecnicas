package persistence;

import static org.junit.Assert.assertEquals;
import model.exception.BusinessLogicException;
import model.production.ProductType;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.junit.Before;
import org.junit.Test;

public class ProductTypePersistentTest extends XMLPersistentTest{

	private ProductType prodType;

	@Before
	public void setUp(){
		super.setUp();
		prodType=new ProductType("Axe");
	}	
	
	@Test
	public void validProductTypeXMLFileWithProductTypeAxe() 
			throws DocumentException, InvalidTagException{
		
		Document doc= reader.read("test/persistence/input/ValidProductType.xml");
		
		Element element=doc.getRootElement();
		
		ProductType recovered=
			(ProductType) ProductTypePersistent.buildFromXML(element);
		
		assertEquals(recovered,prodType);
	}
	
	@Test ( expected = InvalidTagException.class)
	public void ProductTypeXMLFileWithInvalidTag() 
			throws DocumentException, InvalidTagException{
			
		Document doc= reader.read("test/persistence/input/" +
				"InvalidTagProductType.xml");
		
		Element element=doc.getRootElement();
		
		ProductType recovered=
			(ProductType) ProductTypePersistent.buildFromXML(element);
		
		assertEquals(recovered,prodType);
	}
	
	@Test ( expected = BusinessLogicException.class )
	public void ProductTypeXMLFileWithNoName() 
			throws DocumentException, InvalidTagException{
			
		Document doc= reader.read("test/persistence/input/" +
				"MissingIDProductType.xml");
		
		Element element=doc.getRootElement();
		
		ProductType recovered=
			(ProductType) ProductTypePersistent.buildFromXML(element);
		
		assertEquals(recovered,prodType);
	}
	
}
