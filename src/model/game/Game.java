package model.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import model.game.time.UpdateScheduler;
import model.lab.ResearchLab;
import model.lab.TechnologyTree;
import model.production.RawMaterialType;
import model.production.StorageArea;
import model.production.ValidMachineTypes;
import model.production.ValidRawMaterialTypes;
import static model.utils.ArgumentUtils.*;
import java.util.List;
import java.util.Map;

import model.production.RawMaterials;
import model.production.ValidProductionSequences;
import model.production.elements.ProductionLineElement;
import model.production.elements.machine.Machine;
import model.production.elements.machine.MachineType;
import model.production.elements.machine.ProductionMachine;
import model.production.elements.machine.QualityControlMachine;
import model.warehouse.Ground;
import model.warehouse.Position;
import model.warehouse.PriceMap;
import model.warehouse.Warehouse;
import persistence.InputFactory;
import persistence.XMLFactory;

public class Game {

	private static final int DAYS_PER_MONTH = 10;
	private static final int DAYS_PER_WEEK = 3;
	private static final int TICKS_PER_DAY = 24;
	private static final int MAX_DAILY_LAB_FUNDING = 500;

	private Player player;

	private ValidProductionSequences validProductionSequences;
	private List<MachineType> validProductionMachineTypes;
	private List<MachineType> validQualityControlMachineTypes;
	private List<RawMaterialType> validRawMaterialTypes;

	private ResearchLab lab;
	private Warehouse warehouse;

	private UpdateScheduler scheduller;

	public Game(Player player,
			ValidProductionSequences validProductionSequences,
			List<MachineType> validProductionMachineTypes,
			List<MachineType> validQualityControlMachineTypes,
			List<RawMaterialType> validRawMaterialTypes, Warehouse warehouse,
			TechnologyTree techTree) {
		super();
		this.player = player;
		this.validProductionSequences = validProductionSequences;
		this.validProductionMachineTypes = validProductionMachineTypes;
		this.validQualityControlMachineTypes = validQualityControlMachineTypes;
		this.validRawMaterialTypes = validRawMaterialTypes;
		this.warehouse = warehouse;

		this.scheduller = new UpdateScheduler(TICKS_PER_DAY, DAYS_PER_WEEK,
				DAYS_PER_MONTH);

		this.lab = new ResearchLab(techTree, MAX_DAILY_LAB_FUNDING, getBudget());
		
		this.scheduller.subscribeDailyUpdatable(this.lab);
		this.scheduller.subscribeDailyUpdatable(this.warehouse);
		this.scheduller.subscribeWeeklyUpdatable(this.warehouse);
		this.scheduller.subscribeMonthlyUpdatable(this.warehouse);
	}


	// public Game(Ground ground) {
	//		
	// this.scheduller = new UpdateScheduler(TICKS_PER_DAY, DAYS_PER_WEEK,
	// DAYS_PER_MONTH);
	//
	// this.ground = ground;
	// this.qualityControlMachineType = new ArrayList<MachineType>();
	// this.productionMachinesType = new ArrayList<MachineType>();
	// this.rawMaterialPrices = new HashMap<RawMaterialType, Integer>();
	// this.rawMaterialTypes = new ArrayList<RawMaterialType>();
	// this.budget = new Budget(1000);
	// this.grounds = new ArrayList<Ground>();
	// /*
	// * TODO hardcoding just for test.
	// */
	// MachineType prodMachType = new MachineType.Builder("productionMachine",
	// 3, 3).price(250).build();
	// this.productionMachinesType.add(prodMachType);
	// this.productionMachinesType.add(prodMachType);
	// MachineType qualMachType = new MachineType.Builder(
	// "qualityControlMachine", 4, 3).build();
	// this.qualityControlMachineType.add(qualMachType);
	// this.qualityControlMachineType.add(qualMachType);
	// RawMaterialType rawMatType = new RawMaterialType("rawMaterial1");
	// RawMaterialType rawMatType2 = new RawMaterialType("rawMaterial2");
	// this.rawMaterialPrices.put(rawMatType, 100);
	// this.rawMaterialPrices.put(rawMatType2, 200);
	// this.rawMaterialTypes.add(rawMatType);
	// this.rawMaterialTypes.add(rawMatType2);
	// RawMaterials rawMaterials = new RawMaterials();
	// rawMaterials.store(rawMatType, 100);
	// rawMaterials.store(rawMatType2, 300);
	// this.storageArea = new StorageArea(rawMaterials,
	// new ValidProductionSequences());
	//
	// try {
	// InputFactory input = new XMLFactory();
	// this.grounds = input.loadGrounds("test/global/ValidGroundList.xml");
	// } catch (Exception ex) {
	// Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
	// }
	// }

	public Ground getGround() {
		return this.getWarehouse().getGround();
	}

	public Budget getBudget() {
		return this.player.getBudget();
	}

	public boolean canPurchase(int amount) {
		return getBudget().canPurchase(amount);
	}


	public StorageArea getStorageArea() {
		return this.warehouse.getStorageArea();
	}

	public PriceMap getRawMaterialPrices() {
		return this.getWarehouse().getPriceMap();
	}

	public boolean canAfford(int amount) {
		return getBudget().canPurchase(amount);
	}

	public void buyAndAddProductionLineElement(ProductionLineElement element,
			Position position) {
		int price = element.getPurchasePrice();
		checkArgCondition(price, canAfford(price));

		getBudget().decrement(price);
		getGround().addTileElement(element, position);
	}
	
	public void setInitialMoney(int money) {
		this.getBudget().setBalance(money);
	}

	public Warehouse getWarehouse() {
		return this.warehouse;
	}


	public ValidProductionSequences getValidProductionSequences() {
		return validProductionSequences;
	}


	public List<MachineType> getValidProductionMachineTypes() {
		return validProductionMachineTypes;
	}


	public List<MachineType> getValidQualityControlMachineTypes() {
		return validQualityControlMachineTypes;
	}


	public List<RawMaterialType> getValidRawMaterialTypes() {
		return validRawMaterialTypes;
	}


	public Player getPlayer() {
		return this.player;
	}
}
