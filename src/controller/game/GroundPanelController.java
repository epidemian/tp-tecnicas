package controller.game;

import static model.utils.ArgumentUtils.checkNotNull;

import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import model.game.Player;
import model.warehouse.Position;
import view.game.ground.GroundPanel;
import controller.game.edition.EditionTool;

public class GroundPanelController {

	private Player player;
	private GroundPanel groundPanel;
	private EditionTool editionTool;
	
	public GroundPanelController(Player player, GroundPanel groundPanel) {
		this.player = player;
		this.groundPanel = groundPanel;
		
		this.setEditionTool(new NullEditionTool());
		this.setMouseListeners();
	}
	
	public void setEditionTool(EditionTool tool) {
		checkNotNull(tool, "edition tool");
		tool.reset();
		if (this.editionTool != null)
			this.editionTool.reset();
		this.editionTool = tool;
		this.groundPanel.setPainter(editionTool);
	}
	
	public EditionTool getEditionTool() {
		return this.editionTool;
	}
	
	private void setMouseListeners() {

		groundPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Position position = groundPanel.getCurrentMousePosition();
				editionTool.mouseClicked(position);
			}
		});
	}

}

class NullEditionTool extends EditionTool {

	public NullEditionTool() {
		super(null, null);
	}

	@Override
	public void paint(Graphics2D graphics) {
	}

	@Override
	public void mouseClicked(Position position) {
	}

	@Override
	public void reset() {
	}

}