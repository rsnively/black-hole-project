package blackhole;

public class BlackHole {
	
	public static void main(String[] args) {
		Point p = new Point(1, 0, 0);
		Point p2 = new Point(0, 1, 0);
		p.display();
		p2.display();
		
		Point p3 = p.midpoint(p2);
		p3.display();
	}
}
