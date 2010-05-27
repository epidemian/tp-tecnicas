package model.lab;

import java.util.ArrayList;
import java.util.List;

import model.exception.BusinessLogicException;
import model.lab.Technology;
import model.lab.technologies.NewProductionSequenceTechnology;
import model.production.MachineType;
import model.production.ProductType;
import model.production.ProductionSequence;
import model.production.RawMaterialType;
import model.production.RawMaterials;
import model.production.ValidProductionSequences;

import org.junit.*;

import static org.junit.Assert.*;

public class NewProductionSequenceTechnologyTest {

	private ProductionSequence productionSequence;
	private ValidProductionSequences validProductionSequences;
	private ProductType productType;
	private Technology technology;

	@Before
	public void setUp() {

		List<MachineType> lineMachines = getMachineTypeList("Requirements",
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
				"Waterfall software development",
				"The ultimate software development process", 100, false);
	}

	private List<MachineType> getMachineTypeList(String... names) {
		List<MachineType> machineTypes = new ArrayList<MachineType>(
				names.length);
		for (String name : names)
			machineTypes.add(new MachineType(name));
		return machineTypes;
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
	public void researchAndCheckProductionSequenceIdentifyProductType() {
		ProductType productTypeBeforeResearch = this.productionSequence
				.identifyProductType(this.validProductionSequences);
		assertEquals(ProductType.getWaste(), productTypeBeforeResearch);

		technology.research();

		ProductType productTypeAfterResearch = this.productionSequence
				.identifyProductType(this.validProductionSequences);
		assertEquals(this.productType, productTypeAfterResearch);
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
				validSequences, "Name", "Desc", 0, false);
	}
}
