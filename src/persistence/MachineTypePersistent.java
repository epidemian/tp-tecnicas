package persistence;

import model.production.elements.machine.MachineType;
import model.production.elements.machine.MachineType.Builder;

import org.dom4j.Element;

public class MachineTypePersistent {

	static String TAG_NAME = "MachineType";
	static String TAG_ATR_ID = "id";
	static String TAG_ATR_WIDTH = "width";
	static String TAG_ATR_HEIGHT = "height";
	static String TAG_ATR_PRICE = "price";
	static String TAG_CHILD_INPUT = "Input";
	static String TAG_CHILD_OUTPUT = "Output";
	static String TAG_ATR_DAMAGE_CHANCE = "damageChance";
	static String TAG_ATR_BREAK_CHANCE = "breakChance";

	public static MachineType buildFromXML(Element element)
			throws InvalidTagException {

		if (!element.getName().equals(MachineTypePersistent.TAG_NAME))
			throw new InvalidTagException();

		String name = element.attributeValue(TAG_ATR_ID);
		int width = Integer.valueOf(element.attributeValue(TAG_ATR_WIDTH));
		int height = Integer.valueOf(element.attributeValue(TAG_ATR_HEIGHT));

		Builder builder = new MachineType.Builder(name, width, height);

		if (element.element(TAG_ATR_DAMAGE_CHANCE) != null)
			builder.damageChance(Float.valueOf(element
					.attributeValue(TAG_ATR_DAMAGE_CHANCE)));

		if (element.element(TAG_ATR_BREAK_CHANCE) != null)
			builder.breakChance(Float.valueOf(element
					.attributeValue(TAG_ATR_BREAK_CHANCE)));

		if (element.element(TAG_CHILD_INPUT) != null)
			builder.inputRelativePosition(PositionPersistent
					.buildFromXML(element.element(TAG_CHILD_INPUT)));

		if (element.element(TAG_CHILD_OUTPUT) != null)
			builder.outputRelativePosition(PositionPersistent
					.buildFromXML(element.element(TAG_CHILD_OUTPUT)));

		String priceValue = element.attributeValue(TAG_ATR_PRICE);
		if (priceValue != null)
			builder.price(Integer.valueOf(priceValue));

		return builder.build();
	}

}
