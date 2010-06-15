package persistence;

import java.util.List;
import java.util.Map;

import model.lab.technologies.NewProductionSequenceTechnology;
import model.production.MachineType;
import model.production.ValidProductionSequences;
import model.warehouse.Ground;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class XMLFactory extends InputFactory{

	// Reader from the XML file
	/**
	 * TODO: See if Reader suits better as it could be placed in InputFactory
	 */
	protected SAXReader reader;
	
	//Document where is placed what is read from the file
	private Document document;
	
	public XMLFactory(){
		reader=new SAXReader();
	}
	
	@Override
	public List<NewProductionSequenceTechnology> loadTechnologies
					(String technologiesPath,ValidProductionSequences 
							validProductionSequences) 
							
					throws DocumentException,SecurityException, 
						InvalidTagException, ClassNotFoundException, 
						NoSuchMethodException, 
						NoProductTypeDefinedInSequenceException {
		
		document= reader.read(technologiesPath);		
		Element element=document.getRootElement();
		
		return ProductionSequenceTechnologyListPersistent.
												buildFromXML(element, 
														validProductionSequences);
	}

	@Override
	public List<Ground> loadGrounds(String groundsPath) 
								throws InvalidTagException, DocumentException {
		document= reader.read(groundsPath);		
		Element element=document.getRootElement();
		
		return GroundListPersistent.buildFromXML(element);
	
	}

	@Override
	public Map<String, Integer> loadPrices(String pricesPath) 
				throws InvalidTagException, DocumentException {
		document= reader.read(pricesPath);		
		Element element=document.getRootElement();
		
		return PriceMapPersistent.buildFromXML(element);		
	}

	@Override
	public List<MachineType> loadMachines(String availableMachines) 
				throws InvalidTagException, DocumentException {
			document= reader.read(availableMachines);		
			Element element=document.getRootElement();
		
		return MachineTypeListPersistent.buildFromXML(element); 
	}
	
	
	
	
}
