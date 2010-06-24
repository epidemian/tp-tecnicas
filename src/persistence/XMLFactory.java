package persistence;

import static persistence.utils.DocumentLoader.loadDocuemnt;
import java.net.URL;
import java.util.List;
import java.util.Map;

import model.lab.TechnologyTree;
import model.production.RawMaterialType;
import model.production.ValidProductionSequences;
import model.production.elements.machine.MachineType;
import model.utils.Config;
import model.utils.InputFactory;
import model.warehouse.Ground;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import persistence.exceptions.InvalidTagException;
import persistence.exceptions.NoProductTypeDefinedInSequenceException;
import persistence.ground.GroundListPersistent;
import persistence.price.PriceMapPersistent;

public class XMLFactory extends InputFactory {


	// Document where is placed what is read from the file
	private Document document;

	private Config config;

	private String PATH_GROUNDS;
	private String PATH_TECHNOLOGIES;
	private String PATH_PRODUCTION_MACHINES;
	private String PATH_QUALITY_CONTROL_MACHINES;
	private String PATH_RAW_MATERIALS;

	private String PATH_PRICES_BASIC;
	private String PATH_PRICES_EXT;
	
	private int QUANTITY_PRICES_FILES;
		
	public XMLFactory(Config config) {
		this.config = config;
		
		PATH_GROUNDS=config.getValue("PATH_GROUNDS");
		PATH_TECHNOLOGIES=config.getValue("PATH_TECHNOLOGIES");
		PATH_PRODUCTION_MACHINES=config.getValue("PATH_PRODUCTION_MACHINES");
		PATH_QUALITY_CONTROL_MACHINES=config.getValue("PATH_QUALITY_CONTROL_MACHINES");
		PATH_RAW_MATERIALS=config.getValue("PATH_RAW_MATERIALS");
		
		PATH_PRICES_BASIC=config.getValue("PATH_PRICES_BASIC");
		PATH_PRICES_EXT=config.getValue("PATH_PRICES_EXT");
		QUANTITY_PRICES_FILES = config.getIntegerValue("QUANTITY_PRICES_FILES");
		//QUANTITY_PRICES_FILES = Integer.valueOf(config.getValue("QUANTITY_PRICES_FILES"));
		
	}

	@Override
	public TechnologyTree loadTechnologies(
			ValidProductionSequences validProductionSequences)

	throws DocumentException, SecurityException, InvalidTagException,
			ClassNotFoundException, NoSuchMethodException,
			NoProductTypeDefinedInSequenceException {

		document = loadDocuemnt(this.PATH_TECHNOLOGIES);
		Element element = document.getRootElement();

		return ProductionSequenceTechnologyListPersistent.buildFromXML(element,
				validProductionSequences);
	}

	

	@Override
	public List<Ground> loadGrounds() throws InvalidTagException,
			DocumentException {

		document = loadDocuemnt(this.PATH_GROUNDS);
		Element element = document.getRootElement();

		return GroundListPersistent.buildFromXML(element,config);

	}

	@Override
	public Map<String, Integer> loadPrices(int weekNumber)
			throws InvalidTagException, DocumentException {
		int number = weekNumber % QUANTITY_PRICES_FILES;
		String path = PATH_PRICES_BASIC + number + PATH_PRICES_EXT;
		document = loadDocuemnt(path);
		Element element = document.getRootElement();

		return PriceMapPersistent.buildFromXML(element);
	}

	@Override
	public List<MachineType> loadProductionMachines() throws DocumentException,
			InvalidTagException {
		return this.loadMachines(this.PATH_PRODUCTION_MACHINES);
	}

	@Override
	public List<RawMaterialType> loadRawMaterialTypes() throws Exception {

		document = loadDocuemnt(this.PATH_RAW_MATERIALS);
		Element element = document.getRootElement();

		List<RawMaterialType> list = RawMaterialTypeListPersistent
				.buildFromXML(element);

		return list;
	}

	@Override
	public List<MachineType> loadQualityControlMachines() throws Exception {
		return this.loadMachines(this.PATH_QUALITY_CONTROL_MACHINES);
	}

	private List<MachineType> loadMachines(String availableMachines)
			throws DocumentException, InvalidTagException {
		document = loadDocuemnt(availableMachines);
		Element element = document.getRootElement();

		return MachineTypeListPersistent.buildFromXML(element);
	}

}
