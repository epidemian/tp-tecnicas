package persistenceLayer;

import java.util.Iterator;

import org.dom4j.Element;

import model.production.RawMaterialType;
import model.production.RawMaterials;

public class RawMaterialsPersistent {
	
	static String TAG_NAME="RawMaterials";
	static String TAG_INNER="rawMaterial";
	static String TAG_ATR_ID="id";
	static String TAG_ATR_QUANTITY="quantity";
	
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
