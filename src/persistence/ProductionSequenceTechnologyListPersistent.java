package persistence;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import model.exception.BusinessLogicException;
import model.lab.TechnologyTree;
import model.lab.technologies.NewProductionSequenceTechnology;
import model.production.ValidProductionSequences;

import org.dom4j.Element;

import persistence.exceptions.InvalidTagException;
import persistence.exceptions.NoProductTypeDefinedInSequenceException;



public class ProductionSequenceTechnologyListPersistent {

	static String TAG_NAME="Sequences";
	
	@SuppressWarnings("unchecked")
	public static TechnologyTree 
						buildFromXML(Element element, ValidProductionSequences 
								validProductionSequences) 
			throws InvalidTagException, SecurityException, 
			ClassNotFoundException, NoSuchMethodException, 
			NoProductTypeDefinedInSequenceException{
		
	if (!element.getName().
			equals(ProductionSequenceTechnologyListPersistent.TAG_NAME))
		throw new InvalidTagException();
	
	TechnologyTree tree=new TechnologyTree();
	
	
	List<TechnologyPersistent> list=
		new ArrayList<TechnologyPersistent>();
	
	Iterator<Element> iter=element.elementIterator();
	
	while(iter.hasNext()){
		Element elem = iter.next();
		list.add(ProductionSequenceTechnologyPersistent.buildFromXML(elem, validProductionSequences));
	}
	
	Map<Integer,NewProductionSequenceTechnology> mapTechs =
		new HashMap<Integer, NewProductionSequenceTechnology>();
	
	for (TechnologyPersistent technology : list){
		if (mapTechs.containsKey(technology.getId()))
			throw new BusinessLogicException();
		mapTechs.put(technology.getId(),technology.getTechnology());
		tree.addTechnology(technology.getTechnology());
	}
	
	for (TechnologyPersistent technology : list){
		for(Integer id :technology.getDependencies()){			
			tree.addDependency(technology.getTechnology(), mapTechs.get(id));
		}
	}
	
	return tree;
	
	}
}
