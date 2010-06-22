package controller.game.edition.tools;

import static controller.game.edition.tools.Colors.*;
import static model.production.elements.ProductionLineElement.disconnectLineElement;

import java.awt.Color;
import java.awt.Graphics2D;

import model.exception.BusinessLogicException;
import model.game.Player;
import model.production.elements.ProductionLineElement;
import model.warehouse.Position;
import model.warehouse.TileElement;
import controller.game.GamePanelController;
import controller.game.edition.EditionTool;

public class MoveTool extends EditionTool {

	private StartMoveVisitor startMoveVisitor = new StartMoveVisitor();
	private ProductionLineElement movableElement;
	private Position elementPositionBeforeMove;
	private AddElementTool addTool = new AddElementTool();

	public MoveTool(GamePanelController gamePanelController, Player game) {
		super(gamePanelController, game);
	}

	@Override
	public void mouseClicked(Position mousePosition) {
		if (this.movableElement != null) {
			this.addTool.mouseClicked(mousePosition);
			if (!this.movableElement.isNowhere())
				this.movableElement = null;
		} else {
			TileElement element = getGround().getTileElementAt(mousePosition);
			this.startMoveVisitor.tryMove(element);
		}
	}

	@Override
	public void reset() {
		if (this.movableElement != null)
			this.addTool.mouseClicked(elementPositionBeforeMove);
		if (this.movableElement != null && this.movableElement.isNowhere())
			throw new BusinessLogicException("Should have put element");
		this.movableElement = null;
	}

	@Override
	public void paint(Graphics2D graphics) {
		Position mousePosition = getGroundPanel().getCurrentMousePosition();
		if (mousePosition != null) {
			if (this.movableElement != null) {
				this.addTool.paint(graphics);
			} else {
				this.startMoveVisitor.paint(mousePosition, graphics);
			}
		}
	}

	private void startMovingLineElement(ProductionLineElement element) {
		this.movableElement = element;
		this.elementPositionBeforeMove = element.getPosition();

		getGround().removeTileElement(element);
		disconnectLineElement(element);

		this.addTool.reset();
	}

	private class StartMoveVisitor extends EditableElementVisitor {

		private ProductionLineElement movableElement = null;
		private String notification = "";

		public void tryMove(TileElement element) {
			this.reset();
			element.accept(this);
			if (isMovableElement())
				startMovingLineElement(this.movableElement);
		}

		public void paint(Position position, Graphics2D graphics) {
			this.reset();
			TileElement element = getGround().getTileElementAt(position);
			element.accept(this);
			if (!this.notification.isEmpty())
				getGroundPanel().drawNotificationBesideMouse(this.notification,
						graphics);
			if (!isTileEmpty(position)) {
				Color color = isMovableElement() ? OK_COLOR : BAD_COLOR;
				getGroundPanel().drawRectangle(graphics, element.getPosition(),
						element.getWidth(), element.getHeight(), color);
			}
		}

		private boolean isMovableElement() {
			return this.movableElement != null;
		}

		private void reset() {
			this.movableElement = null;
			this.notification = "";
		}

		@Override
		protected void visitEditableElement(ProductionLineElement element) {
			this.movableElement = element;
			this.notification = "Click to move element";
		}

		@Override
		protected void visitNonEditableElement() {
			this.notification = "Click over line element to move it";
		}

	}

	private class AddElementTool extends AbstractAddLineElementTool {

		public AddElementTool() {
			super(MoveTool.this.getGamePanelController(), MoveTool.this
					.getGame());
		}

		@Override
		protected ProductionLineElement createLineElement() {
			return MoveTool.this.movableElement;
		}

		@Override
		protected boolean haveEnoughMoney() {
			return true;
		}

		@Override
		protected void putLineElementAt(Position position) {
			getGround().addTileElement(MoveTool.this.movableElement, position);
		}

	}
}
