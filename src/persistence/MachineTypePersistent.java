package persistence;

import model.production.MachineType;



import org.dom4j.*;

public class MachineTypePersistent  {

	static String TAG_NAME="MachineType";
	static String TAG_ATR_ID="id";
	static String TAG_ATR_WIDTH="width";
	static String TAG_ATR_HEIGHT="height";
	static String TAG_ATR_PRICE="price";
	
	public static MachineType buildFromXML(Element element) 
		throws InvalidTagException{
	
	
	if (!element.getName().equals(MachineTypePersistent.TAG_NAME))
		throw new InvalidTagException();
	
	String name=element.attributeValue(TAG_ATR_ID);
	int width=Integer.valueOf(element.attributeValue(TAG_ATR_WIDTH));
	int height=Integer.valueOf(element.attributeValue(TAG_ATR_HEIGHT));
	
	MachineType type=new MachineType(name,width,height);
	
	String priceValue=element.attributeValue(TAG_ATR_PRICE);
	if (priceValue!=null)
		type=new MachineType(name,width,height,Integer.valueOf(priceValue));
	else
		type=new MachineType(name,width,height);
	
	return type;
	}
	
}
