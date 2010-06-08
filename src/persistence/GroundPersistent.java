package persistence;

import org.dom4j.Element;

import model.production.MachineType;
import model.warehouse.Ground;

public class GroundPersistent{

	static String TAG_NAME="Ground";
	static String TAG_ATR_PRICE="price";
	static String TAG_ATR_ROWS="rows";
	static String TAG_ATR_COLS="cols";	
	
	
	public static Ground buildFromXML(Element element) 
	throws InvalidTagException{


	if (!element.getName().equals(GroundPersistent.TAG_NAME))
		throw new InvalidTagException();
	
	int price=Integer.valueOf(element.attributeValue(TAG_ATR_PRICE));
	int rows=Integer.valueOf(element.attributeValue(TAG_ATR_ROWS));
	int cols=Integer.valueOf(element.attributeValue(TAG_ATR_COLS));

	
	return new Ground(price,rows,cols);
	}
}
