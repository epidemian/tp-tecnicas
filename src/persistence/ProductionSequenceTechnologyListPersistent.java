package persistence;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import model.lab.technologies.NewProductionSequenceTechnology;
import model.production.ValidProductionSequences;

import org.dom4j.Element;



public class ProductionSequenceTechnologyListPersistent {

	static String TAG_NAME="Sequences";
	
	@SuppressWarnings("unchecked")
	public static List<NewProductionSequenceTechnology> 
						buildFromXML(Element element, ValidProductionSequences 
								validProductionSequences) 
			throws InvalidTagException, SecurityException, 
			ClassNotFoundException, NoSuchMethodException, 
			NoProductTypeDefinedInSequenceException{
		
	if (!element.getName().
			equals(ProductionSequenceTechnologyListPersistent.TAG_NAME))
		throw new InvalidTagException();
	
	List<NewProductionSequenceTechnology> list=
		new ArrayList<NewProductionSequenceTechnology>();
	
	Iterator<Element> iter=element.elementIterator();
	
	while(iter.hasNext()){
		Element elem = iter.next();
		list.add(ProductionSequenceTechnologyPersistent.buildFromXML(elem, validProductionSequences));
	}
	
	return list;

}
}
