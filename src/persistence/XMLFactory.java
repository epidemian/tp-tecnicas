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

	private static final int WEEKS = 3;
	private static final String PRICES_PATH = "xml/prices/prices";
	private static final String XML_EXTENSION = ".xml";

	// Reader from the XML file
	protected SAXReader reader;
	
	//Document where is placed what is read from the file
	private Document document;

	private String groundsPath = "xml/ValidGroundList.xml";
	private String technologiesPath = "xml/ValidProductionSequencesTechnologyList.xml";
	private String productionMachinesPath = "xml/ValidProductionMachineList.xml";
	private String qualityControlMachinesPath = "xml/ValidQualityControlMachineList.xml";
	private String rawMaterialsPath = "xml/ValidRawMaterialTypeList.xml";

	
	public XMLFactory(){
		reader=new SAXReader();
	}
	
	@Override
	public TechnologyTree loadTechnologies
					(ValidProductionSequences 
							validProductionSequences) 
							
					throws DocumentException,SecurityException, 
						InvalidTagException, ClassNotFoundException, 
						NoSuchMethodException, 
						NoProductTypeDefinedInSequenceException {
		
		document= reader.read(this.technologiesPath);		
		Element element=document.getRootElement();
		
		return ProductionSequenceTechnologyListPersistent.
												buildFromXML(element, 
														validProductionSequences);
	}

	@Override
	public List<Ground> loadGrounds() 
								throws InvalidTagException, DocumentException {
		
		document= reader.read(this.groundsPath );		
		Element element=document.getRootElement();
		
		return GroundListPersistent.buildFromXML(element);
	
	}

	@Override
	public Map<String, Integer> loadPrices(int weekNumber) 
				throws InvalidTagException, DocumentException {
		int number = weekNumber % WEEKS;
		String path = PRICES_PATH + number + XML_EXTENSION;
		document= reader.read(path);		
		Element element=document.getRootElement();
		
		return PriceMapPersistent.buildFromXML(element);		
	}

	@Override
	public List<MachineType> loadProductionMachines() 
						throws DocumentException, InvalidTagException{ 
		return this.loadMachines(this.productionMachinesPath ); 
	}

	@Override
	public List<RawMaterialType> loadRawMaterialTypes()
			throws Exception {
			
			document=reader.read(this.rawMaterialsPath);
			Element element=document.getRootElement();
			
			List<RawMaterialType> list=RawMaterialTypeListPersistent.
										buildFromXML(element);
			
		return list;
	}

	@Override
	public List<MachineType> loadQualityControlMachines()
			throws Exception {
		return this.loadMachines(this.qualityControlMachinesPath );
	}

	
	private List<MachineType> loadMachines(String availableMachines) 
							throws DocumentException, InvalidTagException{
		document= reader.read(availableMachines);		
		Element element=document.getRootElement();
	
		return MachineTypeListPersistent.buildFromXML(element);
	}
	
	
	
}
