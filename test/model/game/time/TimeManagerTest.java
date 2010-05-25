package model.game.time;

import model.exception.BusinessLogicException;

import org.junit.*;

import static org.junit.Assert.*;

public class TimeManagerTest {

	private static final int TICKS_PER_DAY = 10;
	private static final int DAYS_PER_MONTH = 5;
	private static final int TICKS_PER_MONTH = TICKS_PER_DAY * DAYS_PER_MONTH;
	private TimeManager timeManager;

	@Before
	public void setUp() throws Exception {
		timeManager = new TimeManager(TICKS_PER_DAY, DAYS_PER_MONTH);
	}

	@Test(expected = BusinessLogicException.class)
	public void createWithZeroTicksPerDay() {
		new TimeManager(0, DAYS_PER_MONTH);
	}

	@Test(expected = BusinessLogicException.class)
	public void createWithZeroDaysPerMonth() {
		new TimeManager(TICKS_PER_DAY, 0);
	}

	@Test(expected = BusinessLogicException.class)
	public void subscribreSameTickUpdatableTwice() {
		TickUpdatable counter = new TickCounter();
		timeManager.subscribeTickUpdatable(counter);
		timeManager.subscribeTickUpdatable(counter);
	}

	@Test(expected = BusinessLogicException.class)
	public void subscribreSameDailyUpdatableTwice() {
		DailyUpdatable counter = new DayCounter();
		timeManager.subscribeDailyUpdatable(counter);
		timeManager.subscribeDailyUpdatable(counter);
	}

	@Test(expected = BusinessLogicException.class)
	public void subscribreSameMonthlyUpdatableTwice() {
		MonthlyUpdatable counter = new MonthCounter();
		timeManager.subscribeMonthlyUpdatable(counter);
		timeManager.subscribeMonthlyUpdatable(counter);
	}

	@Test
	public void updateTickManyTimesAndCheckUpdateCounts() {
		TickCounter tickCounter = subscribeNewTickCounter();
		DayCounter dayCounter = subscribeNewDayCounter();
		MonthCounter monthCounter = subscribeNewUpdateCounter();
		
		final int nTicks = 123;
		for (int i = 0; i < nTicks; i++)
			timeManager.updateTick();

		assertEquals(nTicks, tickCounter.getCount());
		assertEquals(nTicks / TICKS_PER_DAY, dayCounter.getCount());
		assertEquals(nTicks / TICKS_PER_MONTH, monthCounter.getCount());
	}

	private TickCounter subscribeNewTickCounter() {
		TickCounter counter = new TickCounter();
		timeManager.subscribeTickUpdatable(counter);
		return counter;
	}

	private DayCounter subscribeNewDayCounter() {
		DayCounter counter = new DayCounter();
		timeManager.subscribeDailyUpdatable(counter);
		return counter;
	}

	private MonthCounter subscribeNewUpdateCounter() {
		MonthCounter counter = new MonthCounter();
		timeManager.subscribeMonthlyUpdatable(counter);
		return counter;
	}

}

class Counter {

	private int count = 0;

	public int getCount() {
		return this.count;
	}

	protected void incrementCount() {
		this.count++;
	}
}

class TickCounter extends Counter implements TickUpdatable {
	@Override
	public void updateTick() {
		incrementCount();
	}
}

class DayCounter extends Counter implements DailyUpdatable {
	@Override
	public void updateDay() {
		incrementCount();
	}
}

class MonthCounter extends Counter implements MonthlyUpdatable {
	@Override
	public void updateMonth() {
		incrementCount();
	}
}
