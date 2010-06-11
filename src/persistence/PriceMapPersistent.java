package persistence;


import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.dom4j.Element;

public class PriceMapPersistent {
	static String TAG_NAME="PriceMap";
	
	@SuppressWarnings("unchecked")
	public static Map<String,Integer> buildFromXML(Element element) 
	throws InvalidTagException{
		
	if (!element.getName().equals(PriceMapPersistent.TAG_NAME))
		throw new InvalidTagException();
	
	Map<String,Integer> map=new HashMap<String, Integer>();
	
	Iterator<Element> iter=element.elementIterator();
	
	while(iter.hasNext()){
		Element elem = iter.next();
		PriceItemPersistent item = PriceItemPersistent.buildFromXML(elem);
		map.put(item.getName(), item.getPrice());
	}
	
	return map;
	
	}

}
