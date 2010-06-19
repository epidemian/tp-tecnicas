package persistence;

import java.util.List;
import java.util.Map;

import model.lab.TechnologyTree;

import model.production.RawMaterialType;

import model.production.ValidProductionSequences;
import model.production.elements.machine.MachineType;
import model.warehouse.Ground;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import persistence.exceptions.InvalidTagException;
import persistence.exceptions.NoProductTypeDefinedInSequenceException;
import persistence.ground.GroundListPersistent;
import persistence.price.PriceMapPersistent;

public class XMLFactory extends InputFactory{

	// Reader from the XML file
	protected SAXReader reader;
	
	//Document where is placed what is read from the file
	private Document document;
	
	public XMLFactory(){
		reader=new SAXReader();
	}
	
	@Override
	public TechnologyTree loadTechnologies
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
	public List<MachineType> loadProductionMachines(String availableMachines) 
						throws DocumentException, InvalidTagException{ 
		return this.loadMachines(availableMachines); 
	}

	@Override
	public List<RawMaterialType> loadRawMaterialTypes(String availableRaw)
			throws Exception {
			
			document=reader.read(availableRaw);
			Element element=document.getRootElement();
			
			List<RawMaterialType> list=RawMaterialTypeListPersistent.
										buildFromXML(element);
			
		return list;
	}

	@Override
	public List<MachineType> loadQualityControlMachines(String availableMachines)
			throws Exception {
		return this.loadMachines(availableMachines);
	}

	
	private List<MachineType> loadMachines(String availableMachines) 
							throws DocumentException, InvalidTagException{
		document= reader.read(availableMachines);		
		Element element=document.getRootElement();
	
		return MachineTypeListPersistent.buildFromXML(element);
	}
	
	
	
}
