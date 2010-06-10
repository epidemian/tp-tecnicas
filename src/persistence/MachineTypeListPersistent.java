package persistence;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import model.production.MachineType;

import org.dom4j.Element;

public class MachineTypeListPersistent {
	
	static String TAG_NAME="MachineList";
	
	@SuppressWarnings("unchecked")
	public static List<MachineType> buildFromXML(Element element) 
		throws InvalidTagException{
		if (!element.getName().equals(MachineTypeListPersistent.TAG_NAME))
			throw new InvalidTagException();
		
		List<MachineType> list=new ArrayList<MachineType>();
		
		Iterator<Element> iter=element.elementIterator();
		
		while(iter.hasNext()){
			Element elem = iter.next();
			list.add(MachineTypePersistent.buildFromXML(elem));
		}
		
		return list;
	
	}
}
