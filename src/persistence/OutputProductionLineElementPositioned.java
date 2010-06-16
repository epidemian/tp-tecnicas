package persistence;

import model.production.elements.OutputProductionLineElement;
import model.warehouse.Position;

public class OutputProductionLineElementPositioned {

	public OutputProductionLineElementPositioned(Position position,
			OutputProductionLineElement output) {
		super();
		this.position = position;
		this.output = output;
	}
	private Position position;
	private OutputProductionLineElement output;
	
	public Position getPosition() {
		return position;
	}
	public void setPosition(Position position) {
		this.position = position;
	}
	public OutputProductionLineElement getOutput() {
		return output;
	}
	public void setOutput(OutputProductionLineElement output) {
		this.output = output;
	}
	
}
