package persistenceLayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;
import java.util.List;

import model.production.AbstractType;
import model.production.MachineType;


import org.dom4j.Element;

public class PriceMapPersistent {
	static String TAG_NAME="PriceMap";
	
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
