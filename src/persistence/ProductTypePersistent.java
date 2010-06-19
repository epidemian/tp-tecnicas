package persistence;


import model.production.ProductType;

import org.dom4j.Element;

import persistence.exceptions.InvalidTagException;

public class ProductTypePersistent {

	static String TAG_NAME="ProductType";
	static String TAG_ATR_ID="id";


	public static ProductType buildFromXML(Element element) 
		throws InvalidTagException{
		
		if (!element.getName().equals(ProductTypePersistent.TAG_NAME))
			throw new InvalidTagException();
		
		String name=element.attributeValue(ProductTypePersistent.TAG_ATR_ID);
		
		ProductType type=new ProductType(name);
		
		return type;
	}
	

}
