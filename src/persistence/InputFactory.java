package persistence;

import java.util.List;
import java.util.Map;

import model.lab.technologies.NewProductionSequenceTechnology;
import model.warehouse.Ground;

import org.dom4j.DocumentException;

public abstract class InputFactory {
	public abstract List<NewProductionSequenceTechnology> 
				loadTechnologies(String path) 
			throws DocumentException, 
			SecurityException, InvalidTagException, ClassNotFoundException, 
			NoSuchMethodException, NoProductTypeDefinedInSequenceException;
	
	public abstract List<Ground>  loadGrounds(String path) 
			throws InvalidTagException, DocumentException;
	
	public abstract Map<String,Integer> loadPrices(String weekNr) 
			throws InvalidTagException, DocumentException;
	
}
