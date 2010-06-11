package persistence;

import model.warehouse.Position;

import org.dom4j.Element;

public class PositionPersistent {
	static String TAG_ATR_ROWS="rows";
	static String TAG_ATR_COLS="cols";
	
	public static Position buildFromXML(Element element) 
	throws InvalidTagException{
		
		int rows=Integer.valueOf(element.attributeValue(TAG_ATR_ROWS));
		int cols=Integer.valueOf(element.attributeValue(TAG_ATR_COLS));
		
		return new Position(rows,cols);
		
	}
}
