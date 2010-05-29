package model.warehouse;

import model.production.Conveyor;
import model.production.Machine;

public interface TileElementVisitor {

	void visitWall(Wall wall);

	void visitConveyor(Conveyor conveyor);

	/*
	 * Maybe more specific methods like visitProductionMachine and
	 * visitQualityControlMachine will need to be created, but for now this
	 * works...
	 */
	void visitMachine(Machine machine);

	
}
