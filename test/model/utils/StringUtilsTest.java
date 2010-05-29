package model.utils;

import static model.utils.StringUtils.join;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class StringUtilsTest {
	
	private static final String DELIMITER = "^_^";

	@Test
	public void joinWithNullCollection() {
		String result = join(null, DELIMITER);
		assertEquals("", result);
	}
	
	@Test
	public void joinWithEmptyCollection() {
		String result = join(new ArrayList<String>(), DELIMITER);
		assertEquals("", result);
	}
	
	@Test
	public void joinCollectionWithOneElement() {
		final String ONE = "one";
		List<String> strings = Arrays.asList(ONE);
		String result = join(strings, DELIMITER);
		assertEquals(ONE, result);
	}
	
	@Test
	public void joinCollectionWithManyElements() {
		final String ONE = "one", TWO = "dos", THREE = "3";
		List<String> strings = Arrays.asList(ONE, TWO, THREE);
		String result = join(strings, DELIMITER);
		assertEquals(ONE + DELIMITER + TWO + DELIMITER + THREE, result);
	}
}
