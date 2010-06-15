package persistence;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import model.production.OutputProductionLineElement;
import model.warehouse.Position;
import model.warehouse.Wall;

import org.dom4j.Element;

public class OutputProductionLineElementsPersistent {

	static String TAG_NAME="Outputs";
	static String TAG_INNER="OutputElement";
	static String TAG_CHILD_ATR_ROWS="rows";
	static String TAG_CHILD_ATR_COLS="cols";
	
	static public List<OutputProductionLineElementPositioned> buildFromXML(Element element)
	throws InvalidTagException{
		
		if (!element.getName().equals(OutputProductionLineElementsPersistent.TAG_NAME))
			throw new InvalidTagException();
		
		List<OutputProductionLineElementPositioned> list=
			new ArrayList<OutputProductionLineElementPositioned>();
		
		Iterator<Element> iter=element.elementIterator();
		
		while(iter.hasNext()){
			Element elem = iter.next();
			if (!elem.getName().equals(
					OutputProductionLineElementsPersistent.TAG_INNER)){
				throw new InvalidTagException();
			}
			
			list.add(new OutputProductionLineElementPositioned(
						new Position(Integer.valueOf(elem.attributeValue(
									WallsPersistent.TAG_CHILD_ATR_ROWS)),
									Integer.valueOf(elem.attributeValue(
											WallsPersistent.TAG_CHILD_ATR_COLS)))
						,new OutputProductionLineElement()));
			
		}
		
		return list;
	}	
}
