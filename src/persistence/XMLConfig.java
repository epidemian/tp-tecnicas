package persistence;

import static persistence.utils.DocumentLoader.loadDocuemnt;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import model.exception.BusinessLogicException;
import model.utils.Config;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import persistence.exceptions.InvalidTagException;

public class XMLConfig extends Config {

	static String TAG_NAME = "Config";
	static String TAG_INNER = "Item";
	static String TAG_CHILD_ID = "id";
	static String TAG_CHILD_VALUE = "value";
	static final String CONFIG_PATH = "xml/Config.xml";


	// Document where is placed what is read from the file
	private Document document;

	private Map<String, String> map;

	public XMLConfig() {
		try {
			document = loadDocuemnt(CONFIG_PATH);
		} catch (DocumentException e) {
			throw new BusinessLogicException("Config file not present");
		}
	}

	@Override
	public String getValue(String key) {
		if (map.get(key) == null)
			throw new BusinessLogicException("Key " + key + " not found");
		return map.get(key);
	}

	@Override
	public void loadConfig() throws InvalidTagException {
		Element element = document.getRootElement();
		this.map = XMLConfig.buildFromXML(element);
	}

	@Override
	public String toString() {
		return this.map.toString();
	}

	@SuppressWarnings("unchecked")
	public static Map<String, String> buildFromXML(Element element)
			throws InvalidTagException {
		if (!element.getName().equals(XMLConfig.TAG_NAME))
			throw new InvalidTagException();

		Map<String, String> map = new HashMap<String, String>();

		Iterator<Element> iter = element.elementIterator();

		while (iter.hasNext()) {
			Element elem = iter.next();
			if (!elem.getName().equals(XMLConfig.TAG_INNER)) {
				throw new InvalidTagException();
			}

			map.put(elem.attributeValue(TAG_CHILD_ID), elem
					.attributeValue(TAG_CHILD_VALUE));
		}

		return map;
	}

}
