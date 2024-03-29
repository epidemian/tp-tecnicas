package view.game.ground;

import static model.utils.ArgumentUtils.*;

import java.awt.Graphics;
import java.awt.Graphics2D;

import view.Painter;

import model.game.Player;

public class GroundPanel extends StaticGroundPanel {

	private static final long serialVersionUID = 1L;

	private double elapsedTickTime = 0;
	private boolean isPaused = true;
	private Painter painter;
	private UnpausedProductPainter unpausedProductPainter;
	private PausedProductPainter pausedProductPainter;

	public GroundPanel(Player player) {
		this(player, DEFAULT_TILE_SIZE);
	}

	public GroundPanel(Player player, int tileSize) {
		super(player.getGround(), tileSize);
		
		pausedProductPainter = new PausedProductPainter(getGround());
		unpausedProductPainter = new UnpausedProductPainter(player.getWarehouse());

		this.setPainter(new Painter() {
			@Override
			public void paint(Graphics2D graphics) {
			}
		});
	}

	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		super.paintComponent(g2d);

		
		if (this.isPaused)
			pausedProductPainter.paintProducts(g2d, elapsedTickTime);
		else
			unpausedProductPainter.paintProducts(g2d, elapsedTickTime);

		this.painter.paint(g2d);
	}

	public void setElapsedTickTime(double elapsedTickTime) {
		checkArgCondition(elapsedTickTime, 0 <= elapsedTickTime
				&& elapsedTickTime <= 1.0);
		this.elapsedTickTime = elapsedTickTime;
	}

	public void setPaused(boolean isPaused) {
		this.isPaused = isPaused;
	}

	public void setPainter(Painter painter) {
		checkNotNull(painter, "painter");
		this.painter = painter;
	}
}
