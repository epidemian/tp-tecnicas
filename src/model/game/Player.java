package model.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import model.exception.BusinessLogicException;
import model.game.time.TickUpdatable;
import model.game.time.UpdateScheduler;
import model.game.time.WeeklyUpdatable;
import model.lab.ResearchLab;
import model.lab.TechnologyTree;
import model.production.RawMaterialType;
import model.production.StorageArea;
import model.production.ValidMachineTypes;
import model.production.ValidRawMaterialTypes;
import static model.utils.ArgumentUtils.*;
import java.util.List;
import java.util.Map;

import controller.game.MarketPricesUpdater;

import model.production.RawMaterials;
import model.production.ValidProductionSequences;
import model.production.elements.ProductionLineElement;
import model.production.elements.machine.Machine;
import model.production.elements.machine.MachineType;
import model.production.elements.machine.ProductionMachine;
import model.production.elements.machine.QualityControlMachine;
import model.warehouse.Ground;
import model.warehouse.Position;
import model.warehouse.MarketPrices;
import model.warehouse.Warehouse;
import persistence.InputFactory;
import persistence.XMLFactory;

public class Player implements TickUpdatable {

	private static final int DAYS_PER_MONTH = 2;
	private static final int DAYS_PER_WEEK = 3;
	private static final int TICKS_PER_DAY = 12;
	private static final int MAX_DAILY_LAB_FUNDING = 500;

	private Budget budget;
	private String playerName;

	private ValidProductionSequences validProductionSequences;
	private List<MachineType> validProductionMachineTypes;
	private List<MachineType> validQualityControlMachineTypes;
	private List<RawMaterialType> validRawMaterialTypes;

	private ResearchLab lab;

	private Warehouse warehouse;

	private UpdateScheduler scheduler;
	private MarketPrices marketPrices;

	public Player(String playerName, int initialBudget,
			InputFactory inputFactory) {
		super();
		this.playerName = playerName;
		this.budget = new Budget(initialBudget);

		TechnologyTree technologyTree;
		this.validProductionSequences = new ValidProductionSequences();

		try {
			technologyTree = inputFactory
					.loadTechnologies(validProductionSequences);
			this.validProductionMachineTypes = inputFactory
					.loadProductionMachines();
			this.validQualityControlMachineTypes = inputFactory
					.loadQualityControlMachines();
			this.validRawMaterialTypes = inputFactory.loadRawMaterialTypes();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		this.scheduler = new UpdateScheduler(TICKS_PER_DAY, DAYS_PER_WEEK,
				DAYS_PER_MONTH);

		this.marketPrices = new MarketPrices();
		WeeklyUpdatable pricesUpdater = new MarketPricesUpdater(marketPrices,
				inputFactory);
		this.scheduler.subscribeWeeklyUpdatable(pricesUpdater);

		this.lab = new ResearchLab(technologyTree, MAX_DAILY_LAB_FUNDING,
				getBudget());
		this.scheduler.subscribeDailyUpdatable(this.lab);

		this.warehouse = null;
		// this.scheduller.subscribeDailyUpdatable(this.warehouse);
		// this.scheduller.subscribeWeeklyUpdatable(this.warehouse);
		// this.scheduller.subscribeMonthlyUpdatable(this.warehouse);
	}

	public Ground getGround() {
		return this.getWarehouse().getGround();
	}

	public Budget getBudget() {
		return this.budget;
	}

	public boolean canPurchase(int amount) {
		return getBudget().canPurchase(amount);
	}

	public StorageArea getStorageArea() {
		return this.warehouse.getStorageArea();
	}

	public MarketPrices getMarketPrices() {
		return this.marketPrices;
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

	public ResearchLab getLab() {
		return lab;
	}

	public void purchaseWarehouse(Ground ground) {
		if (this.warehouse != null)
			throw new BusinessLogicException("Already have a warehouse");
		
		this.warehouse = Warehouse.purchaseWarehouse(ground, getBudget(),
				getMarketPrices(), getValidProductionSequences());
		subscribeWarehouse();
	}

	public void rentWarehouse(Ground ground) {
		if (this.warehouse != null)
			throw new BusinessLogicException("Already have a warehouse");
		
		this.warehouse = Warehouse.rentWarehouse(ground, getBudget(),
				getMarketPrices(), getValidProductionSequences());
		subscribeWarehouse();
	}

	public void sellWarehouse() {
		unsubscribeWarehouse();
		this.warehouse.sell();
		this.warehouse = null;
	}

	public String getDate() {
		return this.scheduler.getDate();
	}

	@Override
	public void updateTick() {
		this.scheduler.updateTick();
	}
	
	private void subscribeWarehouse() {
		this.scheduler.subscribeMonthlyUpdatable(this.warehouse);
		this.scheduler.subscribeDailyUpdatable(this.warehouse);
	}
	
	private void unsubscribeWarehouse() {
		this.scheduler.unsubscribeMonthlyUpdatable(this.warehouse);
		this.scheduler.unsubscribeDailyUpdatable(this.warehouse);
	}
}
