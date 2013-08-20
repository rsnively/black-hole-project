package blackhole;

/**
 * Gotta start somewhere...
 * @author Rory
 *
 */
public class Point {

	/**
	 * Convention for polar coordinates:
	 *     - Theta [0, pi] will be used as latitude, with a value of 0
	 *       at the north pole, and pi at the south pole.
	 *     - Phi [0, 2*pi] will be used as longitude, a value of 0 will
	 *       lie along the x-axis in equivalent rectangular coordinates.
	 */

	// I'm going to store everything as polar coordinates unless a huge amount
	// of conversions to rectangular form are required, at which point it may
	// make more sense to keep tabs on both.
	private double m_theta;
	private double m_phi;

	/**
	 * Constructors:
	 *     - Default constructor creates a point at the top of the unit sphere.
	 *     - A copy constructor (to make one point out of another).
	 *     - A polar coordinate constructor (takes two arguments).
	 *     - A rectangular coordinate constructor (takes three arguments).
	 */

	// Default constructor, sets point to top of unit sphere.
	public Point() {
		m_theta = 0;
		m_phi = 0;
	}
	
	// Copy constructor, copies given point into this one.
	public Point(Point other) {
		m_theta = other.theta();
		m_phi = other.phi();
		
		validate();
	}

	// Polar coordinate constructor.
	public Point(double theta, double phi) {
		m_theta = theta;
		m_phi = phi;
		
		validate();
	}

	// Rectangular coordinate constructor.
	public Point(double x, double y, double z) {
		
		// The only point which can truly screw us its the origin, so we'll
		// return a point at the north pole.
		if (Double.compare(x, 0) == 0 && 
			Double.compare(y, 0) == 0 &&
			Double.compare(z, 0) == 0) {
			z = 1;
		}
		
		// Make sure coordinates conform to unit sphere. If they do not,
		// scale them accordingly.
		double radius = Math.sqrt(x*x + y*y + z*z);		
		x = x / radius;
		y = y / radius;
		z = z / radius;

		// http://en.wikipedia.org/wiki/Spherical_coordinate_system#Coordinate_system_conversions
		m_theta = Math.acos(z);
		
		// We could run into a couple problems with phi when x = 0.
		if (Double.compare(x, 0) == 0) {
			switch (Double.compare(y, 0)) {
				// If x is 0 and y is negative, we are at phi = -pi/2
				case -1: m_phi = 3.0 * Math.PI / 2.0; break;
				// If both x and y are 0, we are at a pole, and it makes sense
				// to set phi to 0.
				case 0: m_phi = 0; break;
				// If x is 0 and y is positive, we are at phi = pi/2
				case 1: m_phi = Math.PI / 2.0; break;
				default: break;
			}
		}
		else {
			m_phi = Math.atan(y/x);
		}
	}

	/**
	 * Accessors (for polar and rectangular coordinates).
	 */

	// Accessors for polar coordinates.
	public double phi() {
		return m_phi;
	}

	public double theta() {
		return m_theta;
	}

	// Accessors for rectangular coordinates (see above wikipedia page).
	public double x() {
		return Math.cos(m_phi) * Math.sin(m_theta);
	}

	public double y() {
		return Math.sin(m_phi) * Math.sin(m_theta);
	}

	public double z() {
		return Math.cos(m_theta);
	}
	
	public void display() {
		String r_crds = "(" + Double.toString(x()) +
						", " + Double.toString(y()) +
						", " + Double.toString(z()) +
						")\n";
		String p_crds = "(" + Double.toString(m_theta / Math.PI) + " pi" +
				        ", " + Double.toString(m_phi / Math.PI) + " pi" +
				        ")\n";
		System.out.println("Rectangular: " + r_crds + "Spherical: " + p_crds);
	}
	
	// Returns the midpoint of this point and a second point.
	public Point midpoint(Point p) {
		
		double x_avg = (x() + p.x()) / 2.0;
		double y_avg = (y() + p.y()) / 2.0;
		double z_avg = (z() + p.z()) / 2.0;
		
		// If points are directly across from one another...
		if (Double.compare(x_avg, 0) == 0 &&
		    Double.compare(y_avg, 0) == 0 &&
		    Double.compare(z_avg, 0) == 0) {
			
			// I guess, average the polar coordinate values and return that.
			double theta_avg = (m_theta + p.theta()) / 2.0;
			double phi_avg = (m_phi + p.phi()) / 2.0;
			return new Point(theta_avg, phi_avg);
		}
		
		// The point copy constructor normalizes these values for us.
		return new Point(x_avg, y_avg, z_avg);
	}

	/**
	 * Public mutator methods.
	 */

	// Rotates the point around the unit sphere by the specified angles.
	public void rotate(double theta, double phi) {
		m_theta += theta;
		m_phi += phi;
		
		validate();
	}

	/**
	 * Private methods.
	 */

	// Make sure arguments are restricted to given range.
	// Currently this code corrects the values to their equivalents
	// in the appropriate range, without throwing any errors.
	private void validate() {
		
		// If we are given a negative theta value, invert it and add
		// pi to phi.
		if (Double.compare(m_theta, 0) == -1) {
			m_theta *= -1.0;
			m_phi += Math.PI;
		}
		// If a theta is given that is greater than pi, scale it back.
		while (Double.compare(m_theta,  Math.PI) == 1) {
			m_theta -= (2.0 * Math.PI);
		}
		// This may have made a previously positive value negative, so
		// check again.
		if (Double.compare(m_theta, 0) == -1) {
			m_theta *= -1.0;
			m_phi += Math.PI;
		}

		// If we are given a negative phi value, rotate by positive 2 pi
		// until it is in the proper range.
		while (Double.compare(m_phi, 0) == -1) {
			m_phi += (2.0 * Math.PI);
		}
		// If we are given a phi value over 2 pi, rotate by negative 2 pi
		// until it is in the proper range.
		while (Double.compare(m_phi, 2.0 * Math.PI) == 1) {
			m_phi -= (2.0 * Math.PI);
		}
	}
}
