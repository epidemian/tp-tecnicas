package persistence.ground;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import model.warehouse.Position;
import model.warehouse.Wall;

import org.dom4j.Element;

import persistence.exceptions.InvalidTagException;

public class WallsPersistent {

	static String TAG_NAME="Walls";
	static String TAG_INNER="Wall";
	static String TAG_CHILD_ATR_ROWS="rows";
	static String TAG_CHILD_ATR_COLS="cols";
	static String TAG_CHILD_ATR_WIDTH="width";
	static String TAG_CHILD_ATR_HEIGHT="height";
	
	@SuppressWarnings("unchecked")
	static public List<WallItem> buildFromXML(Element element)
	throws InvalidTagException{
		
		if (!element.getName().equals(WallsPersistent.TAG_NAME))
			throw new InvalidTagException();
		
		List<WallItem> list=new ArrayList<WallItem>();
		
		Iterator<Element> iter=element.elementIterator();
		
		while(iter.hasNext()){
			Element elem = iter.next();
			if (!elem.getName().equals(WallsPersistent.TAG_INNER)){
				throw new InvalidTagException();
			}
			list.add(new WallItem(new Wall(Integer.valueOf(elem.attributeValue(
					WallsPersistent.TAG_CHILD_ATR_WIDTH)),
					Integer.valueOf(elem.attributeValue(
							WallsPersistent.TAG_CHILD_ATR_HEIGHT))),
					new Position(
							Integer.valueOf(elem.attributeValue(
									WallsPersistent.TAG_CHILD_ATR_ROWS)),
							Integer.valueOf(elem.attributeValue(
									WallsPersistent.TAG_CHILD_ATR_COLS)))));
			
		}
		
		return list;	
	}
}
