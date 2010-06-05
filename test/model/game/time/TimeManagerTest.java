package model.game.time;

import static org.junit.Assert.assertEquals;
import model.exception.BusinessLogicException;

import org.junit.Before;
import org.junit.Test;

public class TimeManagerTest {

	private static final int TICKS_PER_DAY = 5;
	private static final int DAYS_PER_WEEK = 3;
	private static final int DAYS_PER_MONTH = 10;
	private static final int TICKS_PER_WEEK = TICKS_PER_DAY * DAYS_PER_WEEK;
	private static final int TICKS_PER_MONTH = TICKS_PER_DAY * DAYS_PER_MONTH;
	private TimeManager timeManager;

	@Before
	public void setUp() throws Exception {
		timeManager = new TimeManager(TICKS_PER_DAY, DAYS_PER_WEEK,
				DAYS_PER_MONTH);
	}

	@Test(expected = BusinessLogicException.class)
	public void createWithZeroTicksPerDay() {
		new TimeManager(0, DAYS_PER_WEEK, DAYS_PER_MONTH);
	}

	@Test(expected = BusinessLogicException.class)
	public void createWithZeroDaysPerWeek() {
		new TimeManager(TICKS_PER_DAY, 0, DAYS_PER_MONTH);
	}

	@Test(expected = BusinessLogicException.class)
	public void createWithZeroDaysPerMonth() {
		new TimeManager(TICKS_PER_DAY, DAYS_PER_WEEK, 0);
	}

	@Test(expected = BusinessLogicException.class)
	public void subscribreSameTickUpdatableTwice() {
		TickUpdatable counter = new UpdateCounter();
		timeManager.subscribeTickUpdatable(counter);
		timeManager.subscribeTickUpdatable(counter);
	}

	@Test(expected = BusinessLogicException.class)
	public void subscribreSameDailyUpdatableTwice() {
		DailyUpdatable counter = new UpdateCounter();
		timeManager.subscribeDailyUpdatable(counter);
		timeManager.subscribeDailyUpdatable(counter);
	}
	
	@Test(expected = BusinessLogicException.class)
	public void subscribreSameWeeklyUpdatableTwice() {
		WeeklyUpdatable counter = new UpdateCounter();
		timeManager.subscribeWeeklyUpdatable(counter);
		timeManager.subscribeWeeklyUpdatable(counter);
	}

	@Test(expected = BusinessLogicException.class)
	public void subscribreSameMonthlyUpdatableTwice() {
		MonthlyUpdatable counter = new UpdateCounter();
		timeManager.subscribeMonthlyUpdatable(counter);
		timeManager.subscribeMonthlyUpdatable(counter);
	}

	@Test
	public void updateTickManyTimesAndCheckUpdateCounts() {
		UpdateCounter tickCounter = new UpdateCounter();
		UpdateCounter dayCounter = new UpdateCounter();
		UpdateCounter weekCounter = new UpdateCounter();
		UpdateCounter monthCounter = new UpdateCounter();

		this.timeManager.subscribeTickUpdatable(tickCounter);
		this.timeManager.subscribeDailyUpdatable(dayCounter);
		this.timeManager.subscribeWeeklyUpdatable(weekCounter);
		this.timeManager.subscribeMonthlyUpdatable(monthCounter);
		
		final int nTicks = TICKS_PER_MONTH + 123;
		for (int i = 0; i < nTicks; i++)
			this.timeManager.updateTick();

		assertEquals(nTicks, tickCounter.getCount());
		assertEquals(nTicks / TICKS_PER_DAY, dayCounter.getCount());
		assertEquals(nTicks / TICKS_PER_WEEK, weekCounter.getCount());
		assertEquals(nTicks / TICKS_PER_MONTH, monthCounter.getCount());
	}

}

class UpdateCounter implements TickUpdatable, DailyUpdatable, WeeklyUpdatable,
		MonthlyUpdatable {

	private int count = 0;

	public int getCount() {
		return this.count;
	}

	private void incrementCount() {
		this.count++;
	}

	@Override
	public void updateTick() {
		incrementCount();
	}

	@Override
	public void updateDay() {
		incrementCount();
	}

	@Override
	public void updateWeek() {
		incrementCount();
	}

	@Override
	public void updateMonth() {
		incrementCount();
	}
}
