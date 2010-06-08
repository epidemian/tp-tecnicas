package persistence;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import persistenceLayer.InvalidTagException;

import persistenceLayer.NoProductTypeDefinedInSequenceException;
import persistenceLayer.ProductionSequenceTechnologyListPersistent;

import model.lab.technologies.NewProductionSequenceTechnology;

//TODO RESOLVE IGNORES!!
@Ignore
public class ProductionSequenceTechnologyListPersistentTest 
	extends XMLPersistentTest{
	
	List<NewProductionSequenceTechnology> list;
	
	@Before
	public void setUp(){
		super.setUp();
		
		list=new ArrayList<NewProductionSequenceTechnology>();
		
		list.add(ProductionSequenceTechnologyPersistentTest.
				createProductionSequenceTechnologyAxe());
		list.add(ProductionSequenceTechnologyPersistentTest.
				createProductionSequenceTechnologySword());
	}


	@Ignore
	public void validProductionSequenceTechnologyList() 
				throws DocumentException, InvalidTagException,
				SecurityException, ClassNotFoundException, 
				NoSuchMethodException, NoProductTypeDefinedInSequenceException{
		Document doc= 
			reader.read("test/persistence/" +
					"ValidProductionSequencesTechnologyList.xml");
		Element element=doc.getRootElement();
		
		List<NewProductionSequenceTechnology> recovered=
			ProductionSequenceTechnologyListPersistent.buildFromXML(element);
		
		assertEquals(recovered,list);
	}
}
