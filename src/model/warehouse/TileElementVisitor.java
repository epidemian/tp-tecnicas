package model.warehouse;

import model.production.Conveyor;
import model.production.ProductionMachine;
import model.production.QualityControlMachine;

public interface TileElementVisitor {

	void visitWall(Wall wall);
	void visitConveyor(Conveyor conveyor);
	void visitProductionMachine(ProductionMachine machine);
	void visitQualityControlMachine(QualityControlMachine machine);
}
