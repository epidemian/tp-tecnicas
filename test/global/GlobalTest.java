package global;

import java.util.List;

import static model.production.elements.ProductionLineElement.connectLineElements;
import model.game.Budget;
import model.game.time.UpdateScheduler;
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
	
	private UpdateScheduler scheduler=new UpdateScheduler(24,3,10);
	
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
	
	
	
	public ProductionLine createLineProcessingFlail(){
		ProductionLineElement prodLineElement1 = new ProductionMachine(
				new MachineType("Oven",3,4));
		ProductionLineElement prodLineElement2 = new ProductionMachine(
				new MachineType("Forge",3,4));
		ProductionLineElement prodLineElement3 = 
				new OutputProductionLineElement();

		connectLineElements(prodLineElement1, prodLineElement2);
		
		connectLineElements(prodLineElement2, prodLineElement3);
		
		RawMaterials rawMaterialConfiguration = new RawMaterials();
		
		return ProductionLine.createValidProductionLine(prodLineElement1,
				this.warehouse.getStorageArea(),rawMaterialConfiguration);
	}
	
	public ProductionLine createLineProcessingNothing(){
		ProductionLineElement prodLineElement1 = new ProductionMachine(
				new MachineType("Oven",3,4));
		ProductionLineElement prodLineElement2 = new ProductionMachine(
				new MachineType("Doesnt Exist",3,4));
		ProductionLineElement prodLineElement3 = 
				new OutputProductionLineElement();

		connectLineElements(prodLineElement1, prodLineElement2);
		
		connectLineElements(prodLineElement2, prodLineElement3);
		
		RawMaterials rawMaterialConfiguration = new RawMaterials();
		
		return ProductionLine.createValidProductionLine(prodLineElement1,
				this.warehouse.getStorageArea(),rawMaterialConfiguration);
	}
	
	@Test
	public void overallDay() throws Exception{
		System.out.println(this.validSequences);
	//	System.out.println();
	//	System.out.println(tree);
		
		/*
		for(int i=0;i<4;i++)
			this.changePrices(i);
		*/
		
		ProductionLine line=this.createLineProcessingFlail();
		//ProductionLine lineNothing=this.createLineProcessingNothing();
		
		scheduler.subscribeTickUpdatable(line);
		
		for(int i=0;i<scheduler.getTicksPerDay();i++){
			scheduler.updateTick();
		}
		
		
		System.out.println("Warehouse products:"+this.warehouse.getStorageArea().
				getProductsProduced().size());
		System.out.println("Line flail products:"+line.getDailyProduction());

		System.out.println("Balance before sell:"+this.budget.getBalance());
			warehouse.sell();
		System.out.println("Balance after sell:"+this.budget.getBalance());
		
		System.out.println(warehouse.getProductionLines().size());
	}
	
	private void changePrices(int weekNumber) throws Exception {
		int number= weekNumber % this.pricesPaths.length;
		priceMap.setMap(inputFactory.loadPrices(pricesPaths[number]));
	}
} 
