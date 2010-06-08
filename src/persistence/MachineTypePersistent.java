package persistence;

import model.production.MachineType;



import org.dom4j.*;

public class MachineTypePersistent  {

	static String TAG_NAME="MachineType";
	static String TAG_ATR_ID="id";
	
	public static MachineType buildFromXML(Element element) 
		throws InvalidTagException{
	
	
	if (!element.getName().equals(MachineTypePersistent.TAG_NAME))
		throw new InvalidTagException();
	
	String name=element.attributeValue(TAG_ATR_ID);
	

	
	MachineType type=new MachineType(name);
	
	return type;
	}
	
}
