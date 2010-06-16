package view.game.edition.tools;

import static model.production.ProductionLineElement.*;
import static view.game.MoneyConstants.*;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import model.game.Budget;
import model.game.Game;
import model.production.Conveyor;
import model.production.ProductionLineElement;
import model.production.ProductionMachine;
import model.production.QualityControlMachine;
import model.warehouse.EmptyTileElement;
import model.warehouse.Position;
import model.warehouse.TileElement;
import model.warehouse.TileElementVisitor;
import model.warehouse.Wall;
import view.game.GamePanel;
import view.game.edition.EditionTool;

public class DeleteTool extends EditionTool {

	public DeleteTool(GamePanel gamePanel, Game game) {
		super(gamePanel, game);
	}

	@Override
	public void mouseClicked(Position mousePosition) {
		TileElement element = getGround().getTileElementAt(mousePosition);
		DeleteVisitor visitor = new DeleteVisitor();
		visitor.tryDelete(element);
	}

	@Override
	public void reset() {
	}

	@Override
	public void paint(Graphics2D graphics) {
		// TODO Auto-generated method stub

	}

	private void deleteLineElement(ProductionLineElement element) {
		Budget budget = this.getGame().getBudget();
		element.sell(budget);
		getGround().removeTileElement(element);
		disconnectLineElement(element);
	}

	private class DeleteVisitor extends TileElementVisitor {

		private ProductionLineElement deletableElement = null;
		private List<String> notifications = new ArrayList<String>();

		public void tryDelete(TileElement element) {
			this.reset();
			element.accept(this);
			deleteLineElement(deletableElement);
		}

		private void reset() {
			this.deletableElement = null;
			this.notifications.clear();
		}

		@Override
		public void visitConveyor(Conveyor conveyor) {
			this.visitDeletableElement(conveyor);
		}

		@Override
		public void visitEmptyElement(EmptyTileElement emptyTileElement) {
			this.visitNonDeletableElement();
		}

		@Override
		public void visitProductionMachine(ProductionMachine machine) {
			this.visitDeletableElement(machine);
		}

		@Override
		public void visitQualityControlMachine(QualityControlMachine machine) {
			this.visitDeletableElement(machine);
		}

		@Override
		public void visitWall(Wall wall) {
			this.visitNonDeletableElement();
		}

		private void visitDeletableElement(ProductionLineElement element) {
			this.deletableElement = element;
			this.notifications.add("+" + element.getSalePrice());
		}

		private void visitNonDeletableElement() {
			this.notifications.add("Click over line element to delete it");
		}

	}

}
