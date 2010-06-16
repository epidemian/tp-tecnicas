package view.game;

import model.production.elements.Conveyor;
import model.production.elements.ProductionLineElement;
import model.production.elements.machine.MachineType;
import model.production.elements.machine.ProductionMachine;
import model.warehouse.Ground;
import model.warehouse.Position;
import model.warehouse.TileElement;
import model.warehouse.Wall;

public class ViewUtils {

	private static final String[] GROUND_CONFIG = {
			"WWWWWWWWWWWWWWWWWWWWWWWWW", 
			"W             WWWWWWWWWWW",
			"W             WWWWWWWWWWW", 
			"W             WWWWWWWWWWW",
			"W                       W", 
			"W                    OO W",
			"W                    OO W", 
			"W                    OO W",
			"W                    OO W", 
			"W                    OO W",
            "W                    OO W",
            "W                    OO W",
            "W                       W",
            "WWWWWWWWWWWWWWWWWWWWWWWWW",
            };

	public static Ground creatGroundSample1() {

		int cols = GROUND_CONFIG[0].length();
		int rows = GROUND_CONFIG.length;

		Ground ground = new Ground(0, rows, cols);

		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				Position pos = new Position(row, col);
				char c = GROUND_CONFIG[row].charAt(col);
				TileElement element = null;
				switch (c) {
				case 'C':
					element = new Conveyor();
					break;
				case 'M':
					element = new ProductionMachine(new MachineType(
							"Machine at " + pos, 1, 1));
					break;
				case 'W':
					element = new Wall(1, 1);
					break;
				}
				if (element != null)
					ground.addTileElement(element, pos);
			}
		}
		return ground;
	}

	public static Ground creatGroundSample2() {

		Ground ground = new Ground(0, 15, 20);

		// Wall upperWall=new Wall(10,1);
		Wall lowerWall = new Wall(16, 1);

		ground.addTileElement(lowerWall, new Position(1, 1));
		// ground.addTileElement(upperWall,new Position(5,1));

		return ground;
	}
}
