package view;

import java.awt.Dimension;
import javax.swing.JFrame;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;
        private static final Dimension defaultSize = new Dimension(850,650);

	public MainFrame() {

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Display the window.
		this.setVisible(true);
		this.setSize(defaultSize);
	}

        @Override
        public void setSize(Dimension dimension){
            this.setExtendedState(this.getExtendedState() | JFrame.NORMAL);
	    super.setSize(dimension);
        }

	public void maximize() {
		this.setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH);
	}

}
