package persistence;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import model.utils.ConfigMock;
import model.warehouse.Ground;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.junit.Before;
import org.junit.Test;

import persistence.exceptions.InvalidTagException;
import persistence.ground.GroundListPersistent;

public class GroundListPersistentTest  extends XMLPersistentTest {
	List<Ground> list;
	
	@Before
	public void setUp(){
		super.setUp();
		list=new ArrayList<Ground>();
		list.add(new Ground(1000,10,30,new ConfigMock()));
		list.add(new Ground(3000,50,30,new ConfigMock()));
		list.add(new Ground(2000,30,30,new ConfigMock()));
	}
	
	@Test
	public void validGroundList()
		throws DocumentException, InvalidTagException{		
		
		Document doc= 
			reader.read("test/persistence/input/ValidGroundList.xml");
		Element element=doc.getRootElement();
		
		List<Ground> recovered=
			GroundListPersistent.buildFromXML(element,new ConfigMock());
		
		assertEquals(recovered,list);
		
	}
}
