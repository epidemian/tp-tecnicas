package persistence;

import java.util.Iterator;

import model.warehouse.Ground;
import model.warehouse.Position;
import model.warehouse.Wall;

import org.dom4j.Element;

public class GroundPersistent{

	static String TAG_NAME="Ground";
	static String TAG_INNER="Wall";
	static String TAG_ATR_PRICE="price";
	static String TAG_ATR_ROWS="rows";
	static String TAG_ATR_COLS="cols";	
	static String TAG_CHILD_ATR_ROWS="rows";
	static String TAG_CHILD_ATR_COLS="cols";
	static String TAG_CHILD_ATR_WIDTH="width";
	static String TAG_CHILD_ATR_HEIGHT="height";
	
	
	@SuppressWarnings("unchecked")
	public static Ground buildFromXML(Element element) 
	throws InvalidTagException{


	if (!element.getName().equals(GroundPersistent.TAG_NAME))
		throw new InvalidTagException();
	
	int price=Integer.valueOf(element.attributeValue(TAG_ATR_PRICE));
	int rows=Integer.valueOf(element.attributeValue(TAG_ATR_ROWS));
	int cols=Integer.valueOf(element.attributeValue(TAG_ATR_COLS));
	
	Ground ground=new Ground(price,rows,cols);
	
	Iterator<Element> iter=element.elementIterator();
	
	while(iter.hasNext()){
		Element elem = iter.next();
		if (!elem.getName().equals(GroundPersistent.TAG_INNER)){
			throw new InvalidTagException();
		}
		

		Wall wall=new Wall(Integer.valueOf(elem.attributeValue(
								GroundPersistent.TAG_CHILD_ATR_WIDTH)),
							Integer.valueOf(elem.attributeValue(
									GroundPersistent.TAG_CHILD_ATR_HEIGHT)));

		ground.putTileElement(wall, new Position(
								Integer.valueOf(elem.attributeValue(
										GroundPersistent.TAG_CHILD_ATR_ROWS)),
								Integer.valueOf(elem.attributeValue(
										GroundPersistent.TAG_CHILD_ATR_COLS))));
	}

	
	return ground;
	}
}
