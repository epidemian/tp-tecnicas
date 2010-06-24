package persistence.ground;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import model.utils.Config;
import model.warehouse.Ground;

import org.dom4j.Element;

import persistence.exceptions.InvalidTagException;

public class GroundListPersistent {

	static String TAG_NAME="Map";
	
	@SuppressWarnings("unchecked")
	public static List<Ground> buildFromXML(Element element,Config config) 
	throws InvalidTagException{
	if (!element.getName().equals(GroundListPersistent.TAG_NAME))
		throw new InvalidTagException();
	
	List<Ground> list=new ArrayList<Ground>();
	
	Iterator<Element> iter=element.elementIterator();
	
	while(iter.hasNext()){
		Element elem = iter.next();
		list.add(GroundPersistent.buildFromXML(elem,config));
	}
	
	return list;

}
	
}
