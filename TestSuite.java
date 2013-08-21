package blackhole;

/**
 * Class for testing the project.
 * @author Rory
 *
 */
public class TestSuite {
	
	// The total number of tests that are run.
	private int m_tests;
	// The number of tests that fail.
	private int m_failedtests;
	
	// Default constructor, initially we have run no tests (and none have
	// failed).
	public TestSuite() {
		m_tests = 0;
		m_failedtests = 0;
	}
	
	// Floating point comparison. (a, b)
	// Returns 1 if a > b
	// Returns 0 if a == b
	// Returns -1 if a < b
	public int fcomp(double a, double b) {
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
	public boolean feq(double a, double b) {
		return (fcomp(a, b) == 0);
	}
	
	// Floating point less than.
	public boolean flt(double a, double b) {
		return (fcomp(a,b) == -1);
	}
	
	// Floating point greater than.
	public boolean fgt(double a, double b) {
		return (fcomp(a,b) == 1);
	}
	
	// Floating point less than or equal.
	public boolean flte(double a, double b) {
		return (fcomp(a,b) != 1);
	}
	
	// Floating point greater than or equal.
	public boolean fgte(double a, double b) {
		return (fcomp(a,b) != -1);
	}
	
	// Test a single condition.
	// Side-effect: updates the number of tests and number of tests failed.
	//
	// @param b, boolean: The condition which should be true
	// @param message, string: descriptive error message
	public void test(boolean b, String message) {
		m_tests++;
		if (b == false) {
			m_failedtests++;
			System.err.println("Test " + m_tests + " failed:\n" + message + "\n");
		}
	}
	
	public void test(boolean b) {
		test(b, "");
	}
	
	public void testAll() {
		pointTests();
		
		if (m_failedtests > 0) {
			System.out.println(m_failedtests + " of " + m_tests + " tests failed.");
		}
		else {
			System.out.println("Lookin' good!");
		}
	}
	
	public void pointTests() {
		// Default constructor.
		Point p1 = new Point();
		test(feq(p1.theta(), 0), "Expected: p1.theta() = 0\nActual: p1.theta() = " + p1.theta());
		test(feq(p1.phi(), 0), "Expected: p1.phi() = 0\nActual: p1.phi() = " + p1.phi());
		test(feq(p1.x(), 0), "Expected: p1.x() = 0\nActual: p1.x() = " + p1.x());
		test(feq(p1.y(), 0), "Expected: p1.y() = 0\nActual: p1.y() = " + p1.y());
		test(feq(p1.z(), 1), "Expected: p1.z() = 1\nActual: p1.z() = " + p1.z());
		
		// Spherical coordinate constructor (angles in bounds).
		Point p2 = new Point(Math.PI / 2.0, Math.PI / 2.0);
		test(feq(p2.theta(), Math.PI / 2.0), "Expected: p2.theta() = pi/2\nActual: p2.theta() = " + p2.theta());
		test(feq(p2.phi(), Math.PI / 2.0), "Expected: p2.phi() = pi/2\nActual: p2.phi() = " + p2.phi());
		test(feq(p2.x(), 0), "Expected: p2.x() = 0\nActual: p2.x() = " + p2.x());
		test(feq(p2.y(), 1), "Expected: p2.y() = 1\nActual: p2.y() = " + p2.y());
		test(feq(p2.z(), 0), "Expected: p2.z() = 0\nActual: p2.z() = " + p2.z());
		
		// Spherical coordinate constructor (angles out of bounds).
		// Should test the validation function.
		Point p3 = new Point(Math.PI * 1.5, Math.PI * 3.5);
		test(feq(p3.theta(), Math.PI / 2.0), "Expected: p3.theta() = pi/2\nActual: p3.theta() = " + p3.theta());
		test(feq(p3.phi(), Math.PI / 2.0), "Expected: p3.phi() = pi/2\nActual: p3.phi() = " + p3.phi());
		test(feq(p3.x(), 0), "Expected: p3.x() = 0\nActual: p3.x() = " + p3.x());
		test(feq(p3.y(), 1), "Expected: p3.y() = 1\nActual: p3.y() = " + p3.y());
		test(feq(p3.z(), 0), "Expected: p3.z() = 0\nActual: p3.z() = " + p3.z());
		
		// Constructors on corner cases.
		Point p4 = new Point(0,0,0); // Should redirect to north pole
		test(feq(p4.theta(), 0), "Expected: p4.theta() = 0\nActual: p4.theta() = " + p4.theta());
		test(feq(p4.phi(), 0), "Expected: p4.phi() = 0\nActual: p4.phi() = " + p4.phi());
		test(feq(p4.x(), 0), "Expected: p4.x() = 0\nActual: p4.x() = " + p4.x());
		test(feq(p4.y(), 0), "Expected: p4.y() = 0\nActual: p4.y() = " + p4.y());
		test(feq(p4.z(), 1), "Expected: p4.z() = 1\nActual: p4.z() = " + p4.z());
		Point p5 = new Point(Math.PI, 0); // South pole spherical
		test(feq(p5.x(), 0), "Expected: p5.x() = 0\nActual: p5.x() = " + p4.x());
		test(feq(p5.y(), 0), "Expected: p5.y() = 0\nActual: p5.y() = " + p4.y());
		test(feq(p5.z(), -1), "Expected: p5.z() = -1\nActual: p5.z() = " + p5.z());
		Point p6 = new Point(0, 0, -1); // South pole rectangular
		test(feq(p6.theta(), Math.PI), "Expected: p6.theta() = pi\nActual: p6.theta() = " + p6.theta());
		test(feq(p6.phi(), 0), "Expected: p6.phi() = 0\nActual: p6.phi() = " + p6.phi());
		Point p7 = new Point(0,1,0); // Weird phi thing when x is 0
		test(feq(p7.theta(), Math.PI / 2.0), "Expected p7.theta() = pi/2\nActual: p7.theta() = " + p7.theta());
		test(feq(p7.phi(), Math.PI / 2.0), "Expected p7.phi() = pi/2\nActual: p7.phi() = " + p7.theta());
		
		// Midpoint testing
		Point p8 = p4.midpoint(p5); // Midpoint of north and south pole
		// Our current convention should place this at (theta = pi/2, phi = 0)
		test(feq(p8.theta(), Math.PI / 2.0), "Expected: p8.theta() = pi/2\nActual: p8.theta() = " + p8.theta());
		test(feq(p8.phi(), 0), "Expected: p8.phi() = 0\nActual: p8.phi() = " + p8.phi());
		Point p9 = new Point(0,-1,0);
		Point p10 = p7.midpoint(p9);
		test(feq(p10.theta(), Math.PI / 2.0), "Expected: p10.theta() = pi/2\nActual: p10.theta() = " + p10.theta());
		test(feq(p10.phi(), Math.PI), "Expected: p10.phi() = pi\nActual: p10.phi() = " + p10.phi());
		Point p11 = p10.midpoint(p9);
		test(feq(p11.phi(), 7.0 * Math.PI / 4.0), "Expected: p11.phi() =7/4 pi\nActual: p11.phi() = " + p11.phi());
	}
}
