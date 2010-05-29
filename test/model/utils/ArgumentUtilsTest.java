package model.utils;

import static model.utils.ArgumentUtils.*;
import static org.junit.Assert.*;
import model.exception.BusinessLogicException;

import org.junit.Test;

public class ArgumentUtilsTest {

	@Test
	public void checkNotNullWithNonNullReference() {
		checkNotNull(new Object());
		checkNotNull(new Object(), "something");
	}

	@Test(expected = BusinessLogicException.class)
	public void checkNotNullWithNullReference() {
		checkNotNull(null);
	}

	@Test(expected = BusinessLogicException.class)
	public void checkNotNullWithNullReferenceAndName() {
		checkNotNull(null, "nothing");
	}

	@Test
	public void checkNotNullReturnsSameObject() {
		Object object = "A String";
		assertSame(object, checkNotNull(object));
		assertSame(object, checkNotNull(object, "name"));
	}

	@Test
	public void checkGreaterThanWithOkArgs() {
		assertEquals(2, checkGreaterThan(2, 1));
	}

	@Test(expected = BusinessLogicException.class)
	public void checkGreaterThanWithBadArgs() {
		checkGreaterThan(1, 1);
	}

	@Test
	public void checkLessThanWithOkArgs() {
		assertEquals(-1, checkLessThan(-1, 0));
	}

	@Test(expected = BusinessLogicException.class)
	public void checkLessThanWithBadArgs() {
		checkLessThan(1, 1);
	}

	@Test
	public void checkGreaterEqualWithOkArgs() {
		assertEquals(1, checkGreaterEqual(1, 1));
		assertEquals(2, checkGreaterEqual(2, 1));
	}

	@Test(expected = BusinessLogicException.class)
	public void checkGreaterEqualWithBadArgs() {
		checkGreaterEqual(0, 1);
	}

	@Test
	public void checkLessEqualWithOkArgs() {
		assertEquals(1, checkLessEqual(1, 1));
		assertEquals(0, checkLessEqual(0, 1));
	}

	@Test(expected = BusinessLogicException.class)
	public void checkLessEqualWithBadArgs() {
		checkLessEqual(1, 0);
	}
	
	@Test
	public void checkInRangeWithOkArgs() {
		assertEquals(1, checkInRange(1, 1, 2));
		assertEquals(2, checkInRange(2, 1, 2));
	}

	@Test(expected = BusinessLogicException.class)
	public void checkInRangeWithSmallerArg() {
		checkInRange(1, 2, 3);
	}
	
	@Test(expected = BusinessLogicException.class)
	public void checkInRangeWithBiggerArg() {
		checkInRange(4, 2, 3);
	}

}
