package persistence;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import model.production.MachineType;
import model.warehouse.Position;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.junit.Before;
import org.junit.Test;

public class MachineTypeListPersistentTest extends XMLPersistentTest{

	List<MachineType> list;
	
	@Before
	public void setUp(){
		super.setUp();
		list=new ArrayList<MachineType>();
		list.add(new MachineType("LumberProcess",2,3,new Position(1,-1),
									new Position(1,2),0.15f,0.05f,10));
		list.add(new MachineType("Oven",2,3,new Position(1,-1),
									new Position(1,2),0.15f,0.05f,20));
		list.add(new MachineType("Forge",2,3,new Position(1,-1),
									new Position(1,2),0.15f,0.05f,30));
	}
	
	@Test
	public void validMachineTypeList()
		throws DocumentException, InvalidTagException{		
		
		Document doc= 
			reader.read("test/persistence/input/ValidMachineList.xml");
		Element element=doc.getRootElement();
		
		List<MachineType> recovered=
			MachineTypeListPersistent.buildFromXML(element);
		
		
		assertEquals(recovered,list);
		
	}
	
	@Test
	public void MachineTypeListWithDifferentPrices()
		throws DocumentException, InvalidTagException{		
		
		Document doc= 
			reader.read("test/persistence/input/" +
					"MachineTypeListWithDifferentPrices.xml");
		Element element=doc.getRootElement();
		
		List<MachineType> recovered=
			MachineTypeListPersistent.buildFromXML(element);
		
		assertFalse(recovered.equals(list));
		
	}
}
