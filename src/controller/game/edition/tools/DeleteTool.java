package controller.game.edition.tools;

import static controller.game.edition.tools.Colors.*;
import static model.production.elements.ProductionLineElement.disconnectLineElement;
import static view.game.MoneyConstants.getMoneyString;

import java.awt.Color;
import java.awt.Graphics2D;

import model.game.Budget;
import model.game.Player;
import model.production.elements.Conveyor;
import model.production.elements.InputProductionLineElement;
import model.production.elements.OutputProductionLineElement;
import model.production.elements.ProductionLineElement;
import model.production.elements.machine.ProductionMachine;
import model.production.elements.machine.QualityControlMachine;
import model.warehouse.EmptyTileElement;
import model.warehouse.Position;
import model.warehouse.TileElement;
import model.warehouse.TileElementVisitor;
import model.warehouse.Wall;
import controller.game.GamePanelController;
import controller.game.edition.EditionTool;

public class DeleteTool extends EditionTool {

	private DeleteVisitor visitor = new DeleteVisitor();

	public DeleteTool(GamePanelController gamePanelController, Player game) {
		super(gamePanelController, game);
	}

	@Override
	public void mouseClicked(Position mousePosition) {
		TileElement element = getGround().getTileElementAt(mousePosition);
		this.visitor.tryDelete(element);
	}

	@Override
	public void reset() {
	}

	@Override
	public void paint(Graphics2D graphics) {
		Position mousePosition = getGroundPanel().getCurrentMousePosition();
		if (mousePosition != null)
			this.visitor.paint(mousePosition, graphics);

	}

	private void deleteLineElement(ProductionLineElement element) {
		Budget budget = this.getPlayer().getBudget();
		element.sell(budget);
		getGround().removeTileElement(element);
		disconnectLineElement(element);
	}

	private class DeleteVisitor extends EditableElementVisitor {

		private ProductionLineElement deletableElement = null;
		private String notification = "";

		public void tryDelete(TileElement element) {
			this.reset();
			element.accept(this);
			if (isDeletableElement())
				deleteLineElement(this.deletableElement);
		}

		public void paint(Position position, Graphics2D graphics) {
			this.reset();
			TileElement element = getGround().getTileElementAt(position);
			element.accept(this);
			if (!this.notification.isEmpty())
				getGroundPanel().drawNotificationBesideMouse(this.notification,
						graphics);
			if (!isTileEmpty(position)) {
				Color color = isDeletableElement() ? OK_COLOR : BAD_COLOR;
				getGroundPanel().drawRectangle(graphics, element.getPosition(),
						element.getWidth(), element.getHeight(), color);
			}
		}

		private boolean isDeletableElement() {
			return this.deletableElement != null;
		}

		private void reset() {
			this.deletableElement = null;
			this.notification = "";
		}

		@Override
		protected void visitEditableElement(ProductionLineElement element) {
			this.deletableElement = element;
			this.notification = "+" + getMoneyString(element.getSalePrice())
					+ " - Click to sell element";
		}

		@Override
		protected void visitNonEditableElement() {
			this.notification = "Click over line element to sell it";
		}

	}

}
