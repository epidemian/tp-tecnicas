package controller.game;

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

	@Override
	public void updateWeek() {
		this.weekNumber++;
		this.changeWeeklyPrices();
	}

	private void changeWeeklyPrices() {
			 try {
				this.marketPrices.setMap(this.inputFactory.loadPrices(weekNumber));
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
	}

}
