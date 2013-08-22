package blackhole;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;

import javax.swing.JPanel;

public class Display extends JPanel {

	public Display() {
		setFocusable(true);
		setBackground(Color.BLACK);
		setDoubleBuffered(true);
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		g.setColor(Color.DARK_GRAY);
		g.fillOval(150,150,300,300);
		g.dispose();
	}
}
