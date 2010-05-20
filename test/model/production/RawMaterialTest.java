package model.production;

import model.core.BusinessLogicException;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class RawMaterialTest {

	private RawMaterial rawMaterial;

	@Before
	public void setUp() {
		this.rawMaterial = new RawMaterial("clay",10);
	}
	
	@Test(expected = BusinessLogicException.class)
	public void createRawMaterialWithNullName() {
		new RawMaterial(null, 0);
	}
	
	@Test(expected = BusinessLogicException.class)
	public void createRawMaterialWithNegativeQuantity() {
		new RawMaterial("clay", -1);
	}

	@Test(expected = NotEnoughRawMaterialException.class)
	public void extractMoreThanAvailable() throws NotEnoughRawMaterialException {
		this.rawMaterial.extract(20);
	}

	@Test
	public void canExtractMoreThanAvailable() {
		boolean canExtract = this.rawMaterial.canExtract(20);
		assertFalse(canExtract);
	}

	@Test(expected = BusinessLogicException.class)
	public void extractNegativeQuantity() throws NotEnoughRawMaterialException {
		this.rawMaterial.extract(-20);
	}
	
	@Test
	public void equalsRawMaterial(){
		RawMaterial rawMaterial = new RawMaterial("clay",0);
		boolean isEquals = this.rawMaterial.equals(rawMaterial);
		assertTrue(isEquals);
	}
	
	
}
