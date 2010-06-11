package persistence;

import java.util.Iterator;

import model.production.RawMaterialType;
import model.production.RawMaterials;

import org.dom4j.Element;

public class RawMaterialsPersistent {
	
	static String TAG_NAME="RawMaterials";
	static String TAG_INNER="rawMaterial";
	static String TAG_ATR_ID="id";
	static String TAG_ATR_QUANTITY="quantity";
	
	@SuppressWarnings("unchecked")
	public static RawMaterials buildFromXML(Element element) 
		throws InvalidTagException{

		if (!element.getName().equals(RawMaterialsPersistent.TAG_NAME))
			throw new InvalidTagException();
		
		RawMaterials materials=new RawMaterials();
		
		Iterator<Element> iter=element.elementIterator();
		
		while(iter.hasNext()){
			Element elem = iter.next();
			if (!elem.getName().equals(RawMaterialsPersistent.TAG_INNER)){
				throw new InvalidTagException();
			}
			materials.store(new RawMaterialType(elem.attributeValue(TAG_ATR_ID)), 
						Integer.valueOf(elem.attributeValue(TAG_ATR_QUANTITY)));
		}
		
		return materials;
		
	}
}
