package persistence;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import model.lab.technologies.NewProductionSequenceTechnology;
import model.production.MachineType;
import model.production.ProductType;
import model.production.ProductionSequence;
import model.production.RawMaterials;
import model.production.ValidProductionSequences;

import org.dom4j.Element;

public class ProductionSequenceTechnologyPersistent {

	static String TAG_NAME="ProductionSequence";
	static String TAG_ATR_DESC="desc";
	static String TAG_ATR_COST="cost";
	static String TAG_ATR_RESEARCHED="researched";
	static String TAG_ATR_ID="id";
	static String TAG_ATR_DEPENDENCIES="dependencies";

	@SuppressWarnings("unchecked")
	public static TechnologyPersistent buildFromXML(Element element, 
							ValidProductionSequences validProductionSequences)
	
			throws InvalidTagException, ClassNotFoundException, 
					SecurityException, NoSuchMethodException,
					NoProductTypeDefinedInSequenceException{
		
		if (!element.getName()
				.equals(ProductionSequenceTechnologyPersistent.TAG_NAME))
			throw new InvalidTagException();
		
		String desc=element.attributeValue(TAG_ATR_DESC);
		int cost = Integer.valueOf((element.attributeValue(TAG_ATR_COST)));
		
		int id= Integer.valueOf((element.attributeValue(TAG_ATR_ID)));
		
		boolean researched =
			Boolean.valueOf(element.attributeValue(TAG_ATR_RESEARCHED));

		
		List<Integer> deps=(element.attributeValue(TAG_ATR_DEPENDENCIES)!=null?
								recoverDependencies(element.attributeValue(TAG_ATR_DEPENDENCIES)):
								new ArrayList<Integer>());
		
		
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
		
		NewProductionSequenceTechnology tech= 
			new NewProductionSequenceTechnology(prodSequence,productType, 
					validProductionSequences, desc, cost);
		
		if (researched){
			tech.research();
		}
		
		return new TechnologyPersistent(tech,id,deps);
							
		}
	
	private static List<Integer> recoverDependencies(String attributeValue) {
		List<Integer> list=new ArrayList<Integer>();
		String[] dependencies=attributeValue.split(",");
		for(int i=0;i<dependencies.length;i++){
			list.add(new Integer(dependencies[i]));	
		}
		return list;
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
