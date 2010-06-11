package view.warehouse.edition;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionAdapter;

import view.warehouse.GroundPanel;

public class EditionToolMediator {


	public EditionToolMediator(GroundPanel groundPanel) {
		groundPanel.addMouseListener(new MouseAdapter() {
		});
		groundPanel.addMouseMotionListener(new MouseMotionAdapter() {
		});
		groundPanel.setPainter(this);
	}

}
