package persistence;

import java.util.ArrayList;
import java.util.List;

import model.lab.technologies.NewProductionSequenceTechnology;

import org.junit.Before;
import org.junit.Ignore;

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


	/*
	@Test
	public void validProductionSequenceTechnologyList() 
				throws DocumentException, InvalidTagException,
				SecurityException, ClassNotFoundException, 
				NoSuchMethodException, NoProductTypeDefinedInSequenceException{
		Document doc= 
			reader.read("test/persistence/input/" +
					"ValidProductionSequencesTechnologyList.xml");
		Element element=doc.getRootElement();
		
		List<NewProductionSequenceTechnology> recovered=
			ProductionSequenceTechnologyListPersistent.buildFromXML(element,
					new ValidProductionSequences());
		
		assertEquals(recovered,list);
	}
	*/
}
