package blackhole;

public class BlackHole {
	
	private static int m_tests;
	private static int m_failedtests;
	
	public static void main(String[] args) {
		
		testSuite();
	}
	
	// Test suite for everything.
	public static void testSuite() {
		
		m_tests = 0;
		m_failedtests = 0;
		
		/**
		 * Point testing
		 */
		
		// Default constructor.
		Point p1 = new Point();
		test(1, feq(p1.theta(), 0), "Expected: p1.theta() = 0\nActual: p1.theta() = " + p1.theta());
		test(2, feq(p1.phi(), 0), "Expected: p1.phi() = 0\nActual: p1.phi() = " + p1.phi());
		test(3, feq(p1.x(), 0), "Expected: p1.x() = 0\nActual: p1.x() = " + p1.x());
		test(4, feq(p1.y(), 0), "Expected: p1.y() = 0\nActual: p1.y() = " + p1.y());
		test(5, feq(p1.z(), 1), "Expected: p1.z() = 1\nActual: p1.z() = " + p1.z());
		
		// Spherical coordinate constructor (angles in bounds).
		Point p2 = new Point(Math.PI / 2.0, Math.PI / 2.0);
		test(6, feq(p2.theta(), Math.PI / 2.0), "Expected: p2.theta() = pi/2\nActual: p2.theta() = " + p2.theta());
		test(7, feq(p2.phi(), Math.PI / 2.0), "Expected: p2.phi() = pi/2\nActual: p2.phi() = " + p2.phi());
		test(8, feq(p2.x(), 0), "Expected: p2.x() = 0\nActual: p2.x() = " + p2.x());
		test(9, feq(p2.y(), 1), "Expected: p2.y() = 1\nActual: p2.y() = " + p2.y());
		test(10, feq(p2.z(), 0), "Expected: p2.z() = 0\nActual: p2.z() = " + p2.z());
		
		// Spherical coordinate constructor (angles out of bounds).
		// Should test the validation function.
		Point p3 = new Point(Math.PI * 1.5, Math.PI * 3.5);
		test(11, feq(p3.theta(), Math.PI / 2.0), "Expected: p3.theta() = pi/2\nActual: p3.theta() = " + p3.theta());
		test(12, feq(p3.phi(), Math.PI / 2.0), "Expected: p3.phi() = pi/2\nActual: p3.phi() = " + p3.phi());
		test(13, feq(p3.x(), 0), "Expected: p3.x() = 0\nActual: p3.x() = " + p3.x());
		test(14, feq(p3.y(), 1), "Expected: p3.y() = 1\nActual: p3.y() = " + p3.y());
		test(15, feq(p3.z(), 0), "Expected: p3.z() = 0\nActual: p3.z() = " + p3.z());
		
		// Constructors on corner cases.
		Point p4 = new Point(0,0,0); // Should redirect to north pole
		test(16, feq(p4.theta(), 0), "Expected: p4.theta() = 0\nActual: p4.theta() = " + p4.theta());
		test(17, feq(p4.phi(), 0), "Expected: p4.phi() = 0\nActual: p4.phi() = " + p4.phi());
		test(18, feq(p4.x(), 0), "Expected: p4.x() = 0\nActual: p4.x() = " + p4.x());
		test(19, feq(p4.y(), 0), "Expected: p4.y() = 0\nActual: p4.y() = " + p4.y());
		test(20, feq(p4.z(), 1), "Expected: p4.z() = 1\nActual: p4.z() = " + p4.z());
		Point p5 = new Point(Math.PI, 0); // South pole spherical
		test(21, feq(p5.x(), 0), "Expected: p5.x() = 0\nActual: p5.x() = " + p4.x());
		test(22, feq(p5.y(), 0), "Expected: p5.y() = 0\nActual: p5.y() = " + p4.y());
		test(23, feq(p5.z(), -1), "Expected: p5.z() = -1\nActual: p5.z() = " + p5.z());
		Point p6 = new Point(0, 0, -1); // South pole rectangular
		test(24, feq(p6.theta(), Math.PI), "Expected: p6.theta() = pi\nActual: p6.theta() = " + p6.theta());
		test(25, feq(p6.phi(), 0), "Expected: p6.phi() = 0\nActual: p6.phi() = " + p6.phi());
		Point p7 = new Point(0,1,0); // Weird phi thing when x is 0
		test(26, feq(p7.theta(), Math.PI / 2.0), "Expected p7.theta() = pi/2\nActual: p7.theta() = " + p7.theta());
		test(27, feq(p7.phi(), Math.PI / 2.0), "Expected p7.phi() = pi/2\nActual: p7.phi() = " + p7.theta());
		
		if (m_failedtests > 0) {
			System.out.println(m_failedtests + " of " + m_tests + " tests failed.");
		}
		else {
			System.out.println("Lookin' good!");
		}
		
	}
	
	// Test a single condition.
	// Side-effect: updates the number of tests and number of tests failed.
	//
	// @param n, integer: The test number, to help find in code
	// @param b, boolean: The condition which should be true
	// @param message, string: descriptive error message
	public static void test(int n, boolean b, String message) {
		m_tests++;
		if (b == false) {
			m_failedtests++;
			System.err.println("Test " + n + " failed:\n" + message + "\n");
		}
	}
	
	// Floating point comparison. (a, b)
	// Returns 1 if a > b
	// Returns 0 if a == b
	// Returns -1 if a < b
	public static int fcomp(double a, double b) {
		// Error for floating point arithmetic.
		double delta = 0.0000000005;
		
		// Test for less than
		if (a < b - delta) {
			return -1;
		}
		
		// Test for greater than.
		if (a > b + delta) {
			return 1;
		}
		
		// Close enough to be equal.
		return 0;
	}
	
	// Floating point equality.
	public static boolean feq(double a, double b) {
		return (fcomp(a, b) == 0);
	}
	
	// Floating point less than.
	public static boolean flt(double a, double b) {
		return (fcomp(a,b) == -1);
	}
	
	// Floating point greater than.
	public static boolean fgt(double a, double b) {
		return (fcomp(a,b) == 1);
	}
	
	// Floating point less than or equal.
	public static boolean flte(double a, double b) {
		return (fcomp(a,b) != 1);
	}
	
	// Floating point greater than or equal.
	public static boolean fgte(double a, double b) {
		return (fcomp(a,b) != -1);
	}
}
