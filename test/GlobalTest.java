

import static model.production.ProductionLineElement.connectLineElements;

import java.util.List;

import model.game.Budget;
import model.lab.ResearchLab;
import model.lab.TechnologyTree;
import model.lab.technologies.NewProductionSequenceTechnology;
import model.production.Conveyor;
import model.production.MachineType;
import model.production.OutputProductionLineElement;
import model.production.ProductionLineElement;
import model.production.ProductionMachine;
import model.production.RawMaterials;
import model.production.ValidMachineTypes;
import model.production.ValidProductionSequences;
import model.production.ValidRawMaterialTypes;
import model.production.line.ProductionLine;
import model.warehouse.Ground;
import model.warehouse.PriceMap;
import model.warehouse.RentedWarehouse;
import model.warehouse.Warehouse;

import org.junit.Before;
import org.junit.Test;

import persistence.InputFactory;
import persistence.XMLFactory;

public class GlobalTest {

	private Warehouse warehouse;
	
	//not needed in game
	private Ground ground;
	private Budget budget;
	private InputFactory inputFactory;
	private PriceMap priceMap;
	private ValidProductionSequences validSequences;
	private ValidRawMaterialTypes validRawMaterials;
	private ValidMachineTypes validMachineTypes;
	
	//not needed in game
	private TechnologyTree tree;
	private ResearchLab lab;
	
	@Before
	public void setUp() throws Exception{

		budget=new Budget(1000);
		inputFactory = new XMLFactory();
		
		List<Ground> listGrounds = inputFactory.loadGrounds(
				"test/ValidGroundList.xml");
		
		priceMap = new PriceMap(inputFactory.loadPrices(
				"test/ValidPriceList.xml"));
		
		//We pick an arbitrary ground
		ground=listGrounds.get(0);
		
		this.validSequences=new ValidProductionSequences();
		
		this.tree=inputFactory.loadTechnologies("test/" +
				"ValidProductionSequencesTechnologyList.xml",validSequences);
		
		this.lab =new ResearchLab(tree,10,budget);
		
		warehouse=new RentedWarehouse(ground, budget,priceMap,validSequences);		
		
		//this.validRawMaterials
		
		this.validMachineTypes=new ValidMachineTypes(inputFactory.
				loadMachines("test/ValidMachineList.xml"));
		
		this.validRawMaterials=new ValidRawMaterialTypes(inputFactory.
				loadRawMaterialTypes("test/ValidRawMaterialTypeList.xml"));
	
		
	}
	
	
	
	public ProductionLine createLineProcessingCarton(){
		ProductionLineElement prodLineElement1 = new ProductionMachine(
				new MachineType("Licuado",1,1));
		ProductionLineElement prodLineElement2 = new Conveyor();
		ProductionLineElement prodLineElement3 = new ProductionMachine(
				new MachineType("Horno",1,1));
		ProductionLineElement prodLineElement4 = 
				new OutputProductionLineElement();

		connectLineElements(prodLineElement1, prodLineElement2);
		connectLineElements(prodLineElement2, prodLineElement3);
		connectLineElements(prodLineElement3, prodLineElement4);
		
		

		return ProductionLine.createValidProductionLine(prodLineElement1,
				this.warehouse.getStorageArea(),new RawMaterials());
	}
	
	@Test
	public void overallDay(){
		System.out.println(this.validSequences);
		System.out.println();
		System.out.println(tree);
		
		
	}
} 
