package controller.game.edition;

import model.production.elements.Conveyor;
import model.production.elements.InputProductionLineElement;
import model.production.elements.OutputProductionLineElement;
import model.production.elements.ProductionLineElement;
import model.production.elements.machine.Machine;

public class ConnectionRules implements
		model.production.elements.ConnectionRules {

	private static final ConnectionRules INSTANCE = new ConnectionRules();

	public static ConnectionRules getInstance() {
		return INSTANCE;
	}

	private ConnectionRules() {
	}

	private static final ConnectionRule[] RULES = {
			new ConnectionRule(Conveyor.class, Conveyor.class),
			new ConnectionRule(Conveyor.class, Machine.class),
			new ConnectionRule(Conveyor.class, InputProductionLineElement.class),
			new ConnectionRule(Conveyor.class,
					OutputProductionLineElement.class),
			new ConnectionRule(InputProductionLineElement.class, Machine.class),
			new ConnectionRule(OutputProductionLineElement.class, Machine.class) };

	@Override
	public boolean canConnect(ProductionLineElement element1,
			ProductionLineElement element2) {
		for (ConnectionRule rule : RULES)
			if (rule.elementsComply(element1, element2))
				return true;
		return false;
	}

	public static boolean canConnectLineElements(
			ProductionLineElement element1, ProductionLineElement element2) {
		return INSTANCE.canConnect(element1, element2);
	}

}

class ConnectionRule {
	private Class<? extends ProductionLineElement> class1;
	private Class<? extends ProductionLineElement> class2;

	public ConnectionRule(Class<? extends ProductionLineElement> class1,
			Class<? extends ProductionLineElement> class2) {
		this.class1 = class1;
		this.class2 = class2;
	}

	public boolean elementsComply(ProductionLineElement element1,
			ProductionLineElement element2) {
		return (class1.isInstance(element1) && class2.isInstance(element2))
				|| (class2.isInstance(element1) && class1.isInstance(element2));
	}

}