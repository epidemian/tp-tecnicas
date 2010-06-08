package model.warehouse;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class PositionTest {

	private static final int ROW = 5;
	private static final int COL = -7;
	
	private Position position;

	@Before
	public void setUp() {
		position = new Position(ROW, COL);
	}

	@Test
	public void getRowAndCol() {
		assertEquals(ROW, position.getRow());
		assertEquals(COL, position.getCol());
	}
	
	@Test
	public void equalsWithEqualPositions() {
		Position position2 = new Position(ROW, COL);
		assertEquals(position, position2);
	}

	@Test
	public void equalsWithDifferentRow() {
		Position differentRow = new Position(ROW + 1, COL);
		assertThat(differentRow, not(equalTo(position)));
	}

	@Test
	public void equalsWithDifferentCol() {
		Position differentCol = new Position(ROW, COL + 1);
		assertThat(differentCol, not(equalTo(position)));
	}

	@Test
	public void defaultConstructorEqualsZero() {
		assertEquals(Position.ZERO, new Position());
		assertNotSame(Position.ZERO, new Position());
	}

	@Test
	public void clonedPositionIsEqual() {
		assertEquals(position, position.clone());
	}

	@Test
	public void clonedPositionNotSame() {
		assertNotSame(position, position.clone());
	}

	@Test
	public void addDoesNotAffectOriginalPosition() {
		Position clone = position.clone();
		position.add(new Position(1, -3));
		assertEquals(clone, position);
	}

	@Test
	public void addReturnsExpectedPosition() {
		Position expected = new Position(ROW + 1, COL - 3);
		assertEquals(expected, position.add(new Position(1, -3)));
	}
	
	@Test
	public void twoEqualPositionsHaveSameHashCode() {
		assertEquals(position.hashCode(), position.clone().hashCode());
	}
}
