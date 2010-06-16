package model.warehouse;

import static model.utils.ArgumentUtils.checkNotNull;

import java.util.ArrayList;
import java.util.Collection;

import model.game.Budget;
import model.game.time.DailyUpdatable;
import model.game.time.MonthlyUpdatable;
import model.production.Product;
import model.production.RawMaterials;
import model.production.StorageArea;
import model.production.ValidProductionSequences;
import model.production.elements.ProductionLineElement;
import model.production.elements.machine.Machine;
import model.production.line.ProductionLine;

public abstract class Warehouse implements MonthlyUpdatable, DailyUpdatable{
	protected Budget budget; 
	protected Ground ground;
			
	
	/**
	 * Contains the raw material and the products already finished
	 */
	private StorageArea storageArea;
	
	

	private PriceMap priceMap;
	
	private int totalProductsMade;
	private int totalDefectiveProductsMade;
	
	private Collection<ProductionLine> productionLines;
	
	public Collection<ProductionLine> getProductionLines() {
		return productionLines;
	}

	
	//TODO remove (?) 
	@Deprecated
	public Warehouse(Ground ground, Budget budget){
		checkNotNull(ground, "ground");
		checkNotNull(budget, "budget");
		
		this.ground = ground;
		this.budget = budget;
		this.totalDefectiveProductsMade=0;
		this.totalProductsMade=0;
	}
	
	public Warehouse(Ground ground, Budget budget,PriceMap map,
			ValidProductionSequences sequences){
		checkNotNull(ground, "ground");
		checkNotNull(budget, "budget");
		checkNotNull(map, "map");
		this.ground = ground;
		this.budget = budget;
		this.priceMap=map;
		this.totalDefectiveProductsMade=0;
		this.totalProductsMade=0;
		this.storageArea=new StorageArea(new RawMaterials(),sequences);
		this.productionLines=new ArrayList<ProductionLine>();
	}
	
	public void createProductionLines(){
		
		ProductionLinesCreator creator = new ProductionLinesCreator(
				this.storageArea);
		productionLines = creator.createFromGround(this.ground);
	}

	private void sellMachines(){			
		if (this.productionLines != null) {
			for (ProductionLine productionLine : this.productionLines) {
				for (ProductionLineElement element : productionLine) {
					element.sell(budget);
				}
			}
		}
	}	
	
	public int getPriceGround(){
		checkNotNull(ground, "ground");
		return ground.getPrice();
	}
	
	
	protected abstract void sellGround();
	
	public void sell() {
		sellGround(); 
        sellMachines(); 	
	}
	
	public void updateDay(){
		
		sellProducts();
		
		setTotalDefectiveProductsMade(this.totalDefectiveProductsMade+
				this.storageArea.countDefectiveProducts());
		
		setTotalProductsMade(this.totalProductsMade+
				this.storageArea.getProductsProduced().size());
		
		this.storageArea.getProductsProduced().clear();
		
	}

	private void sellProducts()  {
		double partial=0;
		int productPrice=0;
		
		//TODO ver como se resuelve esta excepcion...
		try {
		
			for(Product prod : this.storageArea.getProductsProduced()){	
				productPrice = this.priceMap.getPrice(prod);
				partial += Math.pow(1-this.getDefectivePercentage(),2)
																*productPrice;
			}
			
		} catch (PriceProductDoesNotExistException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		this.budget.increment((int)Math.round(partial));
	}


	private void setTotalProductsMade(int totalProductsMade) {
		this.totalProductsMade = totalProductsMade;
	}


	public int getTotalProductsMade() {
		return totalProductsMade;
	}


	private void setTotalDefectiveProductsMade(int totalDefectiveProductsMade) {
		this.totalDefectiveProductsMade = totalDefectiveProductsMade;
	}


	public int getTotalDefectiveProductsMade() {
		return totalDefectiveProductsMade;
	}
	
	private double getDefectivePercentage(){
		return (this.totalProductsMade!=0? 
				this.totalDefectiveProductsMade/this.totalProductsMade:
				0);	
	}

	public StorageArea getStorageArea() {
		return storageArea;
	}
	
}