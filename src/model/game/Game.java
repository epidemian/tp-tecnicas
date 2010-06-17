package model.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.production.RawMaterialType;
import model.production.StorageArea;
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
import model.warehouse.Warehouse;
import persistence.InputFactory;
import persistence.XMLFactory;

public class Game {

	private Ground ground;
        private List<Ground> grounds;
	private List<MachineType> qualityControlMachineType;
	private List<MachineType> productionMachinesType;
	private Map<RawMaterialType, Integer> rawMaterialPrices;
	private List<RawMaterialType> rawMaterialTypes;
	private Budget budget;
	private StorageArea storageArea;
	private String playerName;
        private Warehouse warehouse;

	public Game(Ground ground) {

            this.ground = ground;
            this.qualityControlMachineType = new ArrayList<MachineType>();
            this.productionMachinesType = new ArrayList<MachineType>();
            this.rawMaterialPrices = new HashMap<RawMaterialType, Integer>();
            this.rawMaterialTypes = new ArrayList<RawMaterialType>();
            this.budget = new Budget(1000);
            this.grounds = new ArrayList<Ground>();
            /*
             * TODO hardcoding just for test.
             */
            MachineType prodMachType = new MachineType.Builder("productionMachine", 3, 3).price(250).build();
            this.productionMachinesType.add(prodMachType);
            this.productionMachinesType.add(prodMachType);
            MachineType qualMachType = new MachineType.Builder("qualityControlMachine", 4, 3).build();
            this.qualityControlMachineType.add(qualMachType);
            this.qualityControlMachineType.add(qualMachType);
            RawMaterialType rawMatType = new RawMaterialType("rawMaterial1");
            RawMaterialType rawMatType2 = new RawMaterialType("rawMaterial2");
            this.rawMaterialPrices.put(rawMatType, 100);
            this.rawMaterialPrices.put(rawMatType2, 200);
            this.rawMaterialTypes.add(rawMatType);
            this.rawMaterialTypes.add(rawMatType2);
            RawMaterials rawMaterials = new RawMaterials();
            rawMaterials.store(rawMatType, 100);
            rawMaterials.store(rawMatType2, 300);
            this.storageArea = new StorageArea(rawMaterials, new ValidProductionSequences());

            try {
                InputFactory input = new XMLFactory();
                this.grounds = input.loadGrounds("test/global/ValidGroundList.xml");
            } catch (Exception ex) {
                Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

	public Ground getGround() {
		return this.ground;
	}

        public void setGround(Ground ground){
            this.ground = ground;
        }

	public Budget getBudget() {
		return this.budget;
	}

	public boolean canPurchase(int amount) {
		return getBudget().canPurchase(amount);
	}

	public Machine buyAndAddProductionMachine(MachineType machineType,
			Position position) {
		ProductionMachine machine = new ProductionMachine(machineType);
		buyAndAddMachine(machine, position);
		return machine;
	}

	public Machine buyAndAddQualityControlMachine(MachineType machineType,
			Position position) {
		QualityControlMachine machine = new QualityControlMachine(machineType);
		buyAndAddMachine(machine, position);
		return machine;
	}

	private void buyAndAddMachine(Machine machine, Position position) {
		int price = machine.getPurchasePrice();
		checkArgCondition(price, canPurchase(price));

		getBudget().decrement(price);
		getGround().addTileElement(machine, position);
	}

	public List<MachineType> getProductionMachinesTypes() {
		return this.productionMachinesType;
	}

	public List<MachineType> getQualityControlMachinesTypes() {
		return this.qualityControlMachineType;
	}

	public StorageArea getStorageArea() {
		return this.storageArea;
	}

	public List<RawMaterialType> getRawMaterialTypes() {
		return this.rawMaterialTypes;
	}

	public Map<RawMaterialType, Integer> getRawMaterialPrices() {
		return this.rawMaterialPrices;
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

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public void setInitialMoney(int money) {
		this.budget.setBalance(money);
	}
	
	public String getPlayerName(){
		return this.playerName;
	}

         public List<Ground> getGrounds() {
           return this.grounds;
        }

         public Warehouse getWarehouse(){
             return this.warehouse;
         }

         public void setWarehouse(Warehouse warehouse){
             this.warehouse = warehouse;
         }
}
