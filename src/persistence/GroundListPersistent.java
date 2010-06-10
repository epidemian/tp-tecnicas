package persistence;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import model.warehouse.Ground;

import org.dom4j.Element;

public class GroundListPersistent {

	static String TAG_NAME="Map";
	
	@SuppressWarnings("unchecked")
	public static List<Ground> buildFromXML(Element element) 
	throws InvalidTagException{
	if (!element.getName().equals(GroundListPersistent.TAG_NAME))
		throw new InvalidTagException();
	
	List<Ground> list=new ArrayList<Ground>();
	
	Iterator<Element> iter=element.elementIterator();
	
	while(iter.hasNext()){
		Element elem = iter.next();
		list.add(GroundPersistent.buildFromXML(elem));
	}
	
	return list;

}
	
}
