package controller.game;

import java.util.Map;

import persistence.InputFactory;
import persistence.XMLFactory;

import model.exception.BusinessLogicException;
import model.game.time.WeeklyUpdatable;
import model.warehouse.PriceMap;

public class WeeklyPricesUpdater implements WeeklyUpdatable {

	private String[] pricesPaths = { "test/global/prices/prices0.xml",
			"test/global/prices/prices1.xml", "test/global/prices/prices2.xml",
			"test/global/prices/prices3.xml", };

	private int weekNumber;

	private InputFactory inputFactory;

	private PriceMap priceMap;

	public WeeklyPricesUpdater(PriceMap map) {
		super();
		this.weekNumber = 0;
		this.inputFactory = new XMLFactory();
		this.priceMap = map;

	}

	/*
	 * @Override public Map<String, Integer> getWeeklyPrices() {
	 * 
	 * int number = weekNumber % this.pricesPaths.length; try { return
	 * inputFactory.loadPrices(pricesPaths[number]); } catch (Exception e) {
	 * throw new BusinessLogicException("Price File doesn't exist"); } }
	 */

	@Override
	public void updateWeek() {
		this.changeWeeklyPrices();
		this.weekNumber++;
	}

	private void changeWeeklyPrices() {
		int number = weekNumber % this.pricesPaths.length; 
		try { 
			 this.priceMap.setMap(this.inputFactory.loadPrices(pricesPaths[number])); 
		} 
		catch (Exception e) {			
			throw new BusinessLogicException("Price File doesn't exist"); 
		} 
	}
	
	

	/*
	 * @Override public void nextWeek() { // TODO Auto-generated method stub
	 * 
	 * }
	 */

}
