package persistence.utils;

import java.net.URL;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

public class DocumentLoader {

	public static Document loadDocuemnt(String path) throws DocumentException {
		URL url = DocumentLoader.class.getClassLoader().getResource(path);
		if (url == null)
			throw new RuntimeException("Could not load " + path);
		return new SAXReader().read(url);
	}
}
