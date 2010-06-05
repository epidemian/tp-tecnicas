package model.warehouse;

import model.production.Conveyor;
import model.production.ProductionMachine;
import model.production.QualityControlMachine;

public interface TileElementVisitor {
	
	public void visitConveyor(Conveyor conveyor);

	public void visitProductionMachine(ProductionMachine machine);

	
	public void visitQualityControlMachine(QualityControlMachine machine);

	
	public void visitWall(Wall wall);
}
