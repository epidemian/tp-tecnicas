package persistence;



import static org.junit.Assert.assertEquals;
import model.production.MachineType;


import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.junit.Before;
import org.junit.Test;

import persistence.InvalidTagException;
import persistence.MachineTypePersistent;

public class MachineTypePersistentTest extends XMLPersistentTest{

	private MachineType machineType;

	@Before
	public void setUp(){
		super.setUp();
		machineType=new MachineType("Oven");
	}	
	
	@Test
	public void validMachineTypeWithTypeOven() 
			throws DocumentException, InvalidTagException{
		
		Document doc= reader.read("test/persistence/ValidMachineType.xml");
		
		Element element=doc.getRootElement();
		
		MachineType recovered=
			 MachineTypePersistent.buildFromXML(element);
		
		assertEquals(recovered,machineType);
	}
}
