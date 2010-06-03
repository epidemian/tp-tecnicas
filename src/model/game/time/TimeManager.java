package model.game.time;

import static model.utils.ArgumentUtils.*;

import java.util.ArrayList;
import java.util.Collection;

/**
 * 
 * TODO: look for a better name! xD
 */
public class TimeManager implements TickUpdatable {

	private Collection<TickUpdatable> tickUpdatables;
	private Collection<DailyUpdatable> dailyUpdatables;
	private Collection<WeeklyUpdatable> weeklyUpdatables;
	private Collection<MonthlyUpdatable> monthlyUpdatables;
	private int tickCount;
	private int ticksPerDay;
	private int daysPerWeek;
	private int daysPerMonth;

	public TimeManager(int ticksPerDay, int daysPerWeek, int daysPerMonth) {
		setTicksPerDay(ticksPerDay);
		setDaysPerWeek(daysPerWeek);
		setDaysPerMonth(daysPerMonth);
		this.tickCount = 0;
		this.tickUpdatables = new ArrayList<TickUpdatable>();
		this.dailyUpdatables = new ArrayList<DailyUpdatable>();
		this.weeklyUpdatables = new ArrayList<WeeklyUpdatable>();
		this.monthlyUpdatables = new ArrayList<MonthlyUpdatable>();
	}

	/**
	 * Increments the tick count and updates all subscribed
	 * {@link TickUpdatable}'s. If a new day starts (i.e: the tick count becomes
	 * a multiple of 'ticks per day') all subscribed {@link DailyUpdatable}'s
	 * are updated. Analogously, if a new month starts, all subscribed
	 * {@link MonthlyUpdatable}'s are updated.
	 */
	@Override
	public void updateTick() {
		incrementTick();
		updateTickUpdatables();
		if (isNewDay())
			updateDailyUpdatables();
		if (isNewWeek())
			updateWeeklyUpdatables();
		if (isNewMonth())
			updateMonthlyUpdatables();
	}

	public void subscribeTickUpdatable(TickUpdatable updatable) {
		subscribeUpdatableTo(updatable, this.tickUpdatables);
	}

	public void subscribeDailyUpdatable(DailyUpdatable updatable) {
		subscribeUpdatableTo(updatable, this.dailyUpdatables);
	}
	
	public void subscribeWeeklyUpdatable(WeeklyUpdatable updatable) {
		subscribeUpdatableTo(updatable, this.weeklyUpdatables);
	}

	public void subscribeMonthlyUpdatable(MonthlyUpdatable updatable) {
		subscribeUpdatableTo(updatable, this.monthlyUpdatables);
	}

	private <T> void subscribeUpdatableTo(T updatable, Collection<T> where) {
		checkNotNull(updatable);
		checkArgCondition(updatable, !where.contains(updatable),
				"already suscribed");
		where.add(updatable);
	}

	private void incrementTick() {
		this.tickCount++;
	}

	private boolean isNewDay() {
		return this.tickCount % getTicksPerDay() == 0;
	}
	
	private boolean isNewWeek() {
		return this.tickCount % getTicksPerWeek() == 0;
	}

	private boolean isNewMonth() {
		return this.tickCount % getTicksPerMonth() == 0;
	}

	private void updateTickUpdatables() {
		for (TickUpdatable updatable : this.tickUpdatables)
			updatable.updateTick();
	}

	private void updateDailyUpdatables() {
		for (DailyUpdatable updatable : this.dailyUpdatables)
			updatable.updateDay();
	}
	
	private void updateWeeklyUpdatables() {
		for (WeeklyUpdatable updatable : this.weeklyUpdatables)
			updatable.updateWeek();
	}

	private void updateMonthlyUpdatables() {
		for (MonthlyUpdatable updatable : this.monthlyUpdatables)
			updatable.updateMonth();
	}

	public int getTicksPerDay() {
		return ticksPerDay;
	}

	private void setTicksPerDay(int ticksPerDay) {
		checkGreaterThan(ticksPerDay, 0, "ticks per day");
		this.ticksPerDay = ticksPerDay;
	}

	public int getDaysPerWeek() {
		return daysPerWeek;
	}

	private void setDaysPerWeek(int daysPerWeek) {
		checkGreaterThan(daysPerWeek, 0, "days per week");
		this.daysPerWeek = daysPerWeek;
	}

	public int getDaysPerMonth() {
		return daysPerMonth;
	}

	private void setDaysPerMonth(int daysPerMonth) {
		checkGreaterThan(daysPerMonth, 0, "days per month");
		this.daysPerMonth = daysPerMonth;
	}

	private int getTicksPerMonth() {
		return getTicksPerDay() * getDaysPerMonth();
	}
	
	private int getTicksPerWeek() {
		return getTicksPerDay() * getDaysPerWeek();
	}
}
