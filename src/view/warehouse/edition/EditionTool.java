package view.warehouse.edition;

import static java.lang.System.out;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionAdapter;

import view.warehouse.GroundPanel;
import view.warehouse.GroundPanelPainter;

public class EditionTool implements GroundPanelPainter {


	public EditionTool(GroundPanel groundPanel) {
		groundPanel.addMouseListener(new MouseAdapter() {
		});
		groundPanel.addMouseMotionListener(new MouseMotionAdapter() {
		});
		groundPanel.setPainter(this);
	}

	@Override
	public void paint(GroundPanel groundPanel, Graphics graphics) {
		// TODO Auto-generated method stub
		//out.println("Paint!");
	}

}
