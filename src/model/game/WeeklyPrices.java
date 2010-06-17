package model.game;

import java.util.Map;

public interface WeeklyPrices {

	public Map<String, Integer> getWeeklyPrices();

	public void nextWeek();
	
}
