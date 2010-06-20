package view.game.edition.tools;

import controller.game.GamePanelController;
import static view.game.edition.tools.Colors.*;
import static model.utils.StringUtils.join;
import static model.production.elements.ProductionLineElement.*;
import static view.game.MoneyConstants.*;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

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
import view.game.edition.EditionTool;

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
		Budget budget = this.getGame().getBudget();
		element.sell(budget);
		getGround().removeTileElement(element);
		disconnectLineElement(element);
	}

	private class DeleteVisitor extends TileElementVisitor {

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
				getPainter().drawRectangle(graphics, element.getPosition(),
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
		

		@Override
		public void visitInputProductionLineElement(
				InputProductionLineElement inputLineElement) {
			visitDeletableElement(inputLineElement);
		}

		@Override
		public void visitOutputProductionLineElement(
				OutputProductionLineElement outputLineElement) {
			visitNonDeletableElement();
		}

		private void visitDeletableElement(ProductionLineElement element) {
			this.deletableElement = element;
			this.notification = "+" + getMoneyString(element.getSalePrice())
					+ " - Click to sell element";
		}

		private void visitNonDeletableElement() {
			this.notification = "Click over line element to delete it";
		}

	}

}
