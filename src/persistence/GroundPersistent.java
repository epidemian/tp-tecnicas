package persistence;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import model.warehouse.Ground;
import model.warehouse.Position;
import model.warehouse.Wall;

import org.dom4j.Element;

public class GroundPersistent{

	static String TAG_NAME="Ground";
	static String TAG_ATR_PRICE="price";
	static String TAG_ATR_ROWS="rows";
	static String TAG_ATR_COLS="cols";	
	static String TAG_CHILD_WALLS="Walls";
	static String TAG_CHILD_OUTPUTS="Outputs";
		
	@SuppressWarnings("unchecked")
	public static Ground buildFromXML(Element element) 
		throws InvalidTagException{
		
		if (!element.getName().equals(GroundPersistent.TAG_NAME))
			throw new InvalidTagException();
		
		int price=Integer.valueOf(element.attributeValue(TAG_ATR_PRICE));
		int rows=Integer.valueOf(element.attributeValue(TAG_ATR_ROWS));
		int cols=Integer.valueOf(element.attributeValue(TAG_ATR_COLS));
		
		Ground ground=new Ground(price,rows,cols);
		
		List<WallItem> walls = (element.element(TAG_CHILD_WALLS)!=null ?
				WallsPersistent.buildFromXML
				(element.element(TAG_CHILD_WALLS)):
					new ArrayList<WallItem>());	
		
		for(WallItem wallItem : walls ){
			ground.putTileElement(wallItem.getWall(),wallItem.getPos());
		}
		
		List<OutputProductionLineElementPositioned> outputs =
				(element.element(TAG_CHILD_OUTPUTS)!=null ?
					OutputProductionLineElementsPersistent.buildFromXML
					(element.element(TAG_CHILD_OUTPUTS)):
						new ArrayList<OutputProductionLineElementPositioned>());
		
		for(OutputProductionLineElementPositioned output : outputs){
			ground.putTileElement(output.getOutput(),output.getPosition());
		}

		return ground;
	}	
}
