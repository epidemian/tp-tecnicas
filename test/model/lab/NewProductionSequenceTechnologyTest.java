package model.lab;

import static org.junit.Assert.*;

import java.util.List;

import model.exception.BusinessLogicException;
import model.lab.technologies.NewProductionSequenceTechnology;
import model.production.ProductType;
import model.production.ProductionSequence;
import model.production.RawMaterialType;
import model.production.RawMaterials;
import model.production.TestUtils;
import model.production.ValidProductionSequences;
import model.production.elements.machine.MachineType;

import org.junit.Before;
import org.junit.Test;

public class NewProductionSequenceTechnologyTest {

	private ProductionSequence productionSequence;
	private ValidProductionSequences validProductionSequences;
	private ProductType productType;
	private Technology technology;

	@Before
	public void setUp() {

		List<MachineType> lineMachines = TestUtils.createMachineTypeList("Requirements",
				"Design", "Implementation", "Testing", "Maintenance");
		RawMaterials rawMaterials = new RawMaterials();
		rawMaterials.store(new RawMaterialType("Time"), 1000);
		rawMaterials.store(new RawMaterialType("Money"), 1000);

		this.productType = new ProductType("Software");
		this.productionSequence = new ProductionSequence(lineMachines,
				rawMaterials);
		this.validProductionSequences = new ValidProductionSequences();
		this.technology = new NewProductionSequenceTechnology(
				this.productionSequence, this.productType,
				this.validProductionSequences,
				"Develop software using waterfall development process", 100);
	}

	@Test(expected = BusinessLogicException.class)
	public void createWithNullProductionSeguence() {
		createWithNullValues(true, false, false);
	}

	@Test(expected = BusinessLogicException.class)
	public void createWithNullProductType() {
		createWithNullValues(false, true, false);
	}

	@Test(expected = BusinessLogicException.class)
	public void createWithNullValidProductionSequence() {
		createWithNullValues(true, false, false);
	}

	@Test
	public void checkIdentifyProductTypeReturnsWasteIfTechnologyIsUnresearched() {
		ProductType productType = this.productionSequence
				.identifyProductType(this.validProductionSequences);
		assertFalse(this.technology.isResearched());
		assertEquals(ProductType.getWaste(), productType);
	}

	@Test
	public void researchAndCheckProductionSequenceIdentifyProductType() {
		technology.research();

		ProductType productType = this.productionSequence
				.identifyProductType(this.validProductionSequences);
		assertEquals(this.productType, productType);
	}

	private NewProductionSequenceTechnology createWithNullValues(
			boolean nullSequence, boolean nullProductType,
			boolean nullValidSequences) {
		ProductionSequence sequence = nullSequence ? null
				: this.productionSequence;
		ProductType productType = nullProductType ? null : this.productType;
		ValidProductionSequences validSequences = nullValidSequences ? null
				: this.validProductionSequences;
		return new NewProductionSequenceTechnology(sequence, productType,
				validSequences, "", 0);
	}
}
