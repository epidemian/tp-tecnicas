package persistence;

import org.dom4j.io.SAXReader;
import org.junit.Before;

public class XMLPersistentTest {
	protected SAXReader reader;
	
	@Before
	public void setUp(){
		this.reader=new SAXReader();
	}
	
}
