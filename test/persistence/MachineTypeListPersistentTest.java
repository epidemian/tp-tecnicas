package persistence;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import model.production.elements.machine.MachineType;
import model.production.elements.machine.MachineType.Builder;
import model.warehouse.Position;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.junit.Before;
import org.junit.Test;

import persistence.exceptions.InvalidTagException;

public class MachineTypeListPersistentTest extends XMLPersistentTest {

	List<MachineType> list;

	@Before
	public void setUp() {
		super.setUp();
		list = new ArrayList<MachineType>();
		Position inputPos = new Position(1, -1);
		Position outputPos = new Position(1, 2);
		Builder typeBuilder = new MachineType.Builder("LumberProcess", 2, 3)
				.inputRelativePosition(inputPos).outputRelativePosition(
						outputPos);

		MachineType type1 = typeBuilder.price(10).build();
		MachineType type2 = typeBuilder.name("Oven").price(20).build();
		MachineType type3 = typeBuilder.name("Forge").price(30).build();
		list.add(type1);
		list.add(type2);
		list.add(type3);
	}

	@Test
	public void validMachineTypeList() throws DocumentException,
			InvalidTagException {

		Document doc = reader
				.read("test/persistence/input/ValidMachineList.xml");
		Element element = doc.getRootElement();

		List<MachineType> recovered = MachineTypeListPersistent
				.buildFromXML(element);

		assertEquals(recovered, list);

	}

	@Test
	public void MachineTypeListWithDifferentPrices() throws DocumentException,
			InvalidTagException {

		Document doc = reader.read("test/persistence/input/"
				+ "MachineTypeListWithDifferentPrices.xml");
		Element element = doc.getRootElement();

		List<MachineType> recovered = MachineTypeListPersistent
				.buildFromXML(element);

		assertEquals(list, recovered);

	}
}
