package global;

import java.util.List;

import static model.production.elements.ProductionLineElement.connectLineElements;
import model.game.Budget;
import model.lab.ResearchLab;
import model.lab.TechnologyTree;
import model.production.RawMaterials;
import model.production.ValidMachineTypes;
import model.production.ValidProductionSequences;
import model.production.ValidRawMaterialTypes;
import model.production.elements.Conveyor;
import model.production.elements.OutputProductionLineElement;
import model.production.elements.ProductionLineElement;
import model.production.elements.machine.MachineType;
import model.production.elements.machine.ProductionMachine;
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
	
	private String[] pricesPaths = {
		"test/global/prices/prices0.xml",
		"test/global/prices/prices1.xml",
		"test/global/prices/prices2.xml",
		"test/global/prices/prices3.xml",
	};
	
	//not needed in game
	private Ground ground;
	private Budget budget;
	private InputFactory inputFactory;
	private PriceMap priceMap;
	private ValidProductionSequences validSequences;
	private ValidRawMaterialTypes validRawMaterials;
	private ValidMachineTypes validProductionMachineTypes;
	private ValidMachineTypes validQualityControlMachineTypes;
	
	//not needed in game
	private TechnologyTree tree;
	private ResearchLab lab;
	
	@Before
	public void setUp() throws Exception{

		budget=new Budget(1000);
		inputFactory = new XMLFactory();
		
		List<Ground> listGrounds = inputFactory.loadGrounds(
				"test/global/ValidGroundList.xml");
		
		priceMap = new PriceMap(inputFactory.loadPrices(
				"test/global/ValidPriceList.xml"));
		
		//We pick an arbitrary ground
		ground=listGrounds.get(0);
		
		this.validSequences=new ValidProductionSequences();
		
		this.tree=inputFactory.loadTechnologies("test/global/" +
				"ValidProductionSequencesTechnologyList.xml",validSequences);
		
		this.lab =new ResearchLab(tree,10,budget);
		
		warehouse=new RentedWarehouse(ground, budget,priceMap,validSequences);		
		
		//this.validRawMaterials
		
		this.validProductionMachineTypes=new ValidMachineTypes(inputFactory.
				loadProductionMachines("test/global/" +
						"ValidProductionMachineList.xml"));
		
		this.validQualityControlMachineTypes=new ValidMachineTypes(inputFactory.
				loadProductionMachines("test/global/" +
						"ValidQualityControlMachineList.xml"));
		
		
		this.validRawMaterials=new ValidRawMaterialTypes(inputFactory.
				loadRawMaterialTypes("test/global/ValidRawMaterialTypeList.xml"));
	
		
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
		
		

		RawMaterials rawMaterialConfiguration = new RawMaterials();
		
		return ProductionLine.createValidProductionLine(prodLineElement1,
				this.warehouse.getStorageArea(),rawMaterialConfiguration);
	}
	
	@Test
	public void overallDay() throws Exception{
		System.out.println(this.validSequences);
		System.out.println();
		System.out.println(tree);
		
		for(int i=0;i<4;i++)
			this.changePrices(i);
		
	}
	
	private void changePrices(int weekNumber) throws Exception {
		int number= weekNumber % this.pricesPaths.length;
		this.priceMap= new PriceMap(inputFactory.loadPrices(pricesPaths[number]));
	}
} 
