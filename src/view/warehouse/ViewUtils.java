package view.warehouse;

import model.production.Conveyor;
import model.production.MachineType;
import model.production.ProductionMachine;
import model.warehouse.Ground;
import model.warehouse.Position;
import model.warehouse.TileElement;

public class ViewUtils {

	public static Ground creatGroundSample1() {

		int cols = 25;
		int rows = 13;

		Ground ground = new Ground(0, rows, cols);

		
		
		/*
		 * Machines.
		 */
		TileElement machine1 = new ProductionMachine(
				new MachineType("machine1",2,2));
		TileElement machine2 = new ProductionMachine(
				new MachineType("machine2",2,2));
		TileElement machine3 = new ProductionMachine(
				new MachineType("machine3",2,2));
		TileElement machine4 = new ProductionMachine(
				new MachineType("machine4",2,2));

		ground.addTileElement(machine1, new Position(1,1));
		ground.addTileElement(machine2, new Position(1,6));
		ground.addTileElement(machine3, new Position(4,6));
		ground.addTileElement(machine4, new Position(9,6));
		
		/*
		 * Conveyors.
		 */
		ground.addTileElement(new Conveyor(), new Position(1,3));
		ground.addTileElement(new Conveyor(), new Position(1,4));
		ground.addTileElement(new Conveyor(), new Position(1,5));
			
		ground.addTileElement(new Conveyor(), new Position(1,8));
		ground.addTileElement(new Conveyor(), new Position(1,9));
		ground.addTileElement(new Conveyor(), new Position(1,10));
		ground.addTileElement(new Conveyor(), new Position(2,10));
		ground.addTileElement(new Conveyor(), new Position(3,10));
		ground.addTileElement(new Conveyor(), new Position(4,10));
		ground.addTileElement(new Conveyor(), new Position(4,9));
		ground.addTileElement(new Conveyor(), new Position(4,8));
	
		ground.addTileElement(new Conveyor(), new Position(6,6));
		ground.addTileElement(new Conveyor(), new Position(7,6));
		ground.addTileElement(new Conveyor(), new Position(8,6));
		
		return ground;
	}
}
