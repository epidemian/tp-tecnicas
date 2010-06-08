package persistence;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;



import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.junit.Before;
import org.junit.Test;

import persistence.InvalidTagException;
import persistence.MachineTypeListPersistent;
import model.production.MachineType;

public class MachineTypeListPersistentTest extends XMLPersistentTest{

	List<MachineType> list;
	
	@Before
	public void setUp(){
		super.setUp();
		list=new ArrayList<MachineType>();
		list.add(new MachineType("LumberProcess"));
		list.add(new MachineType("Oven"));
		list.add(new MachineType("Forge"));
	}
	
	@Test
	public void validMachineTypeList()
		throws DocumentException, InvalidTagException{		
		
		Document doc= 
			reader.read("test/persistence/ValidMachineList.xml");
		Element element=doc.getRootElement();
		
		List<MachineType> recovered=
			MachineTypeListPersistent.buildFromXML(element);
		
		assertEquals(recovered,list);
		
	}
}
