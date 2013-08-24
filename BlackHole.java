package blackhole;

import javax.swing.JFrame;

public class BlackHole extends JFrame {
	
	public BlackHole() {
		add(new Display());
		setTitle("Black Hole Thingy");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(600, 600);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	public static void main(String[] args) {
		
		new BlackHole();
		TestSuite t = new TestSuite();
		t.testAll();
	}
}
