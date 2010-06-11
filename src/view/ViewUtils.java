package view;

import model.production.Conveyor;
import model.production.MachineType;
import model.production.ProductionLineElement;
import model.production.ProductionMachine;
import model.warehouse.Ground;
import model.warehouse.Position;

public class ViewUtils {

	private static final String[] GROUND_CONFIG = {
			"                         ", 
			"    CCCMCCCCC            ",
			"            CCCMCCC      ", 
			"   CCCMCC         C      ",
			"               CCCC      ", 
			"       CMCC    C         ",
			"       C       CCM       ", 
			"   CCMCC                 ",
			"                         ", 
			"                         ", };

	public static Ground creatGroundSample1() {

		int cols = GROUND_CONFIG[0].length();
		int rows = GROUND_CONFIG.length;

		Ground ground = new Ground(0, rows, cols);

		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				Position pos = new Position(row, col);
				char c = GROUND_CONFIG[row].charAt(col);
				ProductionLineElement element = null;
				switch (c) {
				case 'C':
					element = new Conveyor();
					break;
				case 'M':
					element = new ProductionMachine(new MachineType(
							"Machine at " + pos, 1, 1));
					break;
				}
				if (element != null)
					ground.addTileElement(element, pos);
			}
		}
		return ground;
	}
}
