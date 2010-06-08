package persistenceLayer;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;



import model.lab.technologies.NewProductionSequenceTechnology;
import model.production.MachineType;
import model.production.ProductType;
import model.production.ProductionSequence;
import model.production.RawMaterials;
import model.production.ValidProductionSequences;

import org.dom4j.*;

public class ProductionSequenceTechnologyPersistent {

	static String TAG_NAME="ProductionSequence";
	static String TAG_ATR_DESC="desc";
	static String TAG_ATR_COST="cost";

	public static NewProductionSequenceTechnology buildFromXML(Element element) 
			throws InvalidTagException, ClassNotFoundException, 
					SecurityException, NoSuchMethodException,
					NoProductTypeDefinedInSequenceException{
		
		if (!element.getName()
				.equals(ProductionSequenceTechnologyPersistent.TAG_NAME))
			throw new InvalidTagException();
		
		String desc=element.attributeValue(TAG_ATR_DESC);
		int cost = Integer.valueOf((element.attributeValue(TAG_ATR_COST)));
		
		RawMaterials rawMaterials;
		List<MachineType> machines;
		ProductType productType;
		
		List<Element> childs = element.elements();
		Iterator<Element> iter=childs.iterator();
		rawMaterials=recoverRawMaterials(iter);

		iter=childs.iterator();
		machines=recoverMachines(iter);		
		
		iter=childs.iterator();
		productType=recoverProductType(iter);
		
		ProductionSequence prodSequence =
			 new ProductionSequence(machines,rawMaterials);
		
		return new NewProductionSequenceTechnology(prodSequence,
				productType, new ValidProductionSequences(), desc, cost);					
		}
	
	private static RawMaterials recoverRawMaterials(Iterator<Element> iter) 
			throws InvalidTagException {
		RawMaterials rawMaterials=new RawMaterials();
		
		Element element;
		
		while(iter.hasNext()){
			element=iter.next();
			if (element.getName()==RawMaterialsPersistent.TAG_NAME){	
				rawMaterials=RawMaterialsPersistent.buildFromXML(element);
			}
			
		}
		return rawMaterials;
	}
	
	private static List<MachineType> recoverMachines(Iterator<Element> iter) 
			throws InvalidTagException {
		
		List<MachineType> machines = new ArrayList<MachineType>();
		
		Element element;
		while(iter.hasNext()){
			element=iter.next();
			if (element.getName()==MachineTypeListPersistent.TAG_NAME){	
				machines=MachineTypeListPersistent.buildFromXML(element);
			}
			
		}
	
		return machines;
	}
	
	private static ProductType recoverProductType(Iterator<Element> iter) 
			throws InvalidTagException, NoProductTypeDefinedInSequenceException{
		
		ProductType type = null;
		
		Element element;
		while(iter.hasNext()){
			element=iter.next();
			if (element.getName()==ProductTypePersistent.TAG_NAME){	
				type=ProductTypePersistent.buildFromXML(element);
			}
			
		}
		if (type == null){
			throw new NoProductTypeDefinedInSequenceException();
		}
		
		return type;
	}
	
}
