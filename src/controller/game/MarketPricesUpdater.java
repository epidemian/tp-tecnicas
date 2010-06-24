package controller.game;

import model.exception.BusinessLogicException;
import model.game.time.WeeklyUpdatable;
import model.utils.InputFactory;
import model.warehouse.MarketPrices;

public class MarketPricesUpdater implements WeeklyUpdatable {

	private int weekNumber;

	private InputFactory inputFactory;

	private MarketPrices marketPrices;

	public MarketPricesUpdater(MarketPrices marketPrices, InputFactory inputFactory) {
		super();
		this.weekNumber = 0;
		this.inputFactory = inputFactory;
		this.marketPrices = marketPrices;
		changeWeeklyPrices();
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
		this.weekNumber++;
		this.changeWeeklyPrices();
		System.out.println("Update week! " + this.marketPrices);
	}

	private void changeWeeklyPrices() {
		try { 
			 this.marketPrices.setMap(this.inputFactory.loadPrices(weekNumber));
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
