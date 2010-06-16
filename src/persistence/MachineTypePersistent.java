package persistence;



import model.production.MachineType;
import model.production.machineState.BrokenMachineState;
import model.production.machineState.DamagedMachineState;
import model.production.machineState.HealthyMachineState;
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
	static String TAG_ATR_DAMAGE_CHANCE="damageChance";
	static String TAG_ATR_BREAK_CHANCE="breakChance";
	
	public static MachineType buildFromXML(Element element) 
		throws InvalidTagException{
	
	
		if (!element.getName().equals(MachineTypePersistent.TAG_NAME))
			throw new InvalidTagException();
		
		String name=element.attributeValue(TAG_ATR_ID);
		int width=Integer.valueOf(element.attributeValue(TAG_ATR_WIDTH));
		int height=Integer.valueOf(element.attributeValue(TAG_ATR_HEIGHT));
		
		Position input=null;
		Position output=null;
		
		float damageCoef=(element.element(TAG_ATR_DAMAGE_CHANCE)!=null ?
				Float.valueOf(element.attributeValue(TAG_ATR_DAMAGE_CHANCE)):
					DamagedMachineState.DEFECT_DAMAGE_CHANCE);
		
		float breakCoef=(element.element(TAG_ATR_BREAK_CHANCE)!=null ?
				Float.valueOf(element.attributeValue(TAG_ATR_BREAK_CHANCE)):
					BrokenMachineState.DEFECT_BREAK_CHANCE);
		
		
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

		return new MachineType(name,width,height,input,output, 
				breakCoef,damageCoef, price);
	}
	
}
