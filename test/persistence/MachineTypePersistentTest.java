package persistence;

import static model.production.TestUtils.*;

import static org.junit.Assert.*;
import model.production.elements.machine.MachineType;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.junit.Before;
import org.junit.Test;

import persistence.exceptions.InvalidTagException;

public class MachineTypePersistentTest extends XMLPersistentTest {

	private MachineType machineType;

	@Before
	public void setUp() {
		super.setUp();
		machineType = createMachineType("Oven", 4, 3);
	}

	@Test
	public void validMachineTypeWithTypeOven() throws DocumentException,
			InvalidTagException {

		Document doc = reader
				.read("test/persistence/input/ValidMachineType.xml");

		Element element = doc.getRootElement();

		MachineType recovered = MachineTypePersistent.buildFromXML(element);

		assertEquals(recovered, machineType);
	}

	@Test
	public void validMachineTypeWithDifferentDimensions()
			throws DocumentException, InvalidTagException {

		Document doc = reader.read("test/persistence/input/"
				+ "MachineTypeWithDifferentDimensions.xml");

		Element element = doc.getRootElement();

		MachineType recovered = MachineTypePersistent.buildFromXML(element);

		assertFalse(recovered.equals(machineType));
	}
}
