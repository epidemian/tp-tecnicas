package persistence;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import model.production.RawMaterialType;
import org.dom4j.Element;

public class RawMaterialTypeListPersistent {

	static String TAG_NAME="RawMaterialTypeList";
	static String TAG_INNER="rawMaterial";
	static String TAG_ATR_ID="id";
	
	@SuppressWarnings("unchecked")
	public static List<RawMaterialType> buildFromXML(Element element) 
	throws InvalidTagException{
		
		if (!element.getName().equals(RawMaterialTypeListPersistent.TAG_NAME))
			throw new InvalidTagException();
		
		List<RawMaterialType> list= new ArrayList<RawMaterialType>();
		
		Iterator<Element> iter=element.elementIterator();
		
		while(iter.hasNext()){
			Element elem = iter.next();
			if (!elem.getName().equals(RawMaterialTypeListPersistent.TAG_INNER)){
				throw new InvalidTagException();
			}
			
			list.add(new RawMaterialType(elem.attributeValue(
					RawMaterialTypeListPersistent.TAG_ATR_ID)));
			
		}
		
		return list;
	}

}
