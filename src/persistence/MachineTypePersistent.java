package persistence;



import model.production.MachineType;
import model.warehouse.Position;

import org.dom4j.Element;

public class MachineTypePersistent  {

	static String TAG_NAME="MachineType";
	static String TAG_ATR_ID="id";
	static String TAG_ATR_WIDTH="width";
	static String TAG_ATR_HEIGHT="height";
	static String TAG_ATR_PRICE="price";
	static String TAG_CHILD_INPUT="Input";
	static String TAG_CHILD_OUTPUT="Output";
	
	public static MachineType buildFromXML(Element element) 
		throws InvalidTagException{
	
	
		if (!element.getName().equals(MachineTypePersistent.TAG_NAME))
			throw new InvalidTagException();
		
		String name=element.attributeValue(TAG_ATR_ID);
		int width=Integer.valueOf(element.attributeValue(TAG_ATR_WIDTH));
		int height=Integer.valueOf(element.attributeValue(TAG_ATR_HEIGHT));
		
		Position input=null;
		Position output=null;
		
		
		input=(element.element(TAG_CHILD_INPUT)!=null ?
				PositionPersistent. buildFromXML
									(element.element(TAG_CHILD_INPUT)):
					new Position(0,-1)	
			);
		
		output=(element.element(TAG_CHILD_OUTPUT)!=null ?  
				PositionPersistent.buildFromXML
									(element.element(TAG_CHILD_OUTPUT)):
				new Position(0,width)
			);
		
		
		
		//Checks if it has an assigned price.
		String priceValue=element.attributeValue(TAG_ATR_PRICE);
		int price= ((priceValue!=null)? 
							Integer.valueOf(priceValue) : 
							MachineType.DEFECT_MACHINE_PRICE
			);

		return new MachineType(name,width,height,input,output,price);
	}
	
}
