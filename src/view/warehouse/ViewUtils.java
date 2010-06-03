package view.warehouse;

import model.production.Conveyor;
import model.production.MachineType;
import model.production.ProductionMachine;
import model.warehouse.Ground;
import model.warehouse.TileElement;

public class ViewUtils {

	public static Ground creatGroundSample1() {

		int cols = 25;
		int rows = 12;

		Ground ground = new Ground(0, rows, cols);

		/*
		 * Machines.
		 */
		TileElement machine1 = new ProductionMachine(
				new MachineType("machine1"), null, null);
		TileElement machine2 = new ProductionMachine(
				new MachineType("machine2"), null, null);
		TileElement machine3 = new ProductionMachine(
				new MachineType("machine3"), null, null);
		TileElement machine4 = new ProductionMachine(
				new MachineType("machine4"), null, null);

		machine1.setHeight(2);
		machine1.setWidth(2);
		machine2.setHeight(2);
		machine2.setWidth(2);
		machine3.setHeight(2);
		machine3.setWidth(2);
		machine4.setHeight(2);
		machine4.setWidth(2);
		
		ground.getTile(1, 1).setTileElement(machine1);
		ground.getTile(1, 2).setTileElement(machine1);
		ground.getTile(2, 1).setTileElement(machine1);
		ground.getTile(2, 2).setTileElement(machine1);
				
		ground.getTile(1, 6).setTileElement(machine2);
		ground.getTile(1, 7).setTileElement(machine2);
		ground.getTile(2, 6).setTileElement(machine2);
		ground.getTile(2, 7).setTileElement(machine2);
				
		ground.getTile(4, 6).setTileElement(machine3);
		ground.getTile(4, 7).setTileElement(machine3);
		ground.getTile(5, 6).setTileElement(machine3);
		ground.getTile(5, 7).setTileElement(machine3);
				
		ground.getTile(9, 6).setTileElement(machine4);
		ground.getTile(9, 7).setTileElement(machine4);
		ground.getTile(10, 6).setTileElement(machine4);
		ground.getTile(10, 7).setTileElement(machine4);
		
		/*
		 * Conveyors.
		 */
		ground.getTile(1, 3).setTileElement(new Conveyor(null, null));
		ground.getTile(1, 4).setTileElement(new Conveyor(null, null));
		ground.getTile(1, 5).setTileElement(new Conveyor(null, null));
		
		ground.getTile(1, 8).setTileElement(new Conveyor(null, null));
		ground.getTile(1, 9).setTileElement(new Conveyor(null, null));
		ground.getTile(1, 10).setTileElement(new Conveyor(null, null));
		ground.getTile(2, 10).setTileElement(new Conveyor(null, null));
		ground.getTile(3, 10).setTileElement(new Conveyor(null, null));
		ground.getTile(4, 10).setTileElement(new Conveyor(null, null));
		ground.getTile(4, 9).setTileElement(new Conveyor(null, null));
		ground.getTile(4, 8).setTileElement(new Conveyor(null, null));
		
		ground.getTile(6, 6).setTileElement(new Conveyor(null, null));
		ground.getTile(7, 6).setTileElement(new Conveyor(null, null));
		ground.getTile(8, 6).setTileElement(new Conveyor(null, null));
			
		return ground;
	}
}
