package blackhole;

/**
 * Physical massive points that we'll use to build machines, sort of like
 * white connectors in K'Nex. Unlike white connectors, they are infinitesimally
 * small.
 * 
 * @author Rory
 *
 */
public class Anchor {
	
	// The location of the anchor on the unit sphere.
	private Point m_loc;
	
	// The mass of the anchor. I'm assuming an anchor can be mass-less or have
	// negative mass, so we won't be constraining this value at all.
	private double m_mass;
	
	/**
	 * Constructors
	 */
	
	// Default constructor: creates a mass-less point at the north pole
	// of the unit sphere.
	public Anchor() {
		m_loc = new Point();
		m_mass = 0;
	}
	
	// Copy constructor: create one anchor from another.
	public Anchor(Anchor other) {
		m_loc = other.m_loc;
		m_mass = other.m_mass;
	}
	
	// Standard constructor: create an anchor with specified location and
	// mass.
	public Anchor(Point loc, double mass) {
		// Since this value is already stored as a point, we shouldn't have to
		// normalize anything.
		m_loc = loc;
		m_mass = mass;
	}
	
	// Standard constructor (given the point in spherical coordinates).
	public Anchor(double theta, double phi, double mass) {
		// We'll use the point constructor to normalize the coordinates.
		m_loc = new Point(theta, phi);
		m_mass = mass;
		
	}
	
	// Standard constructor (given the point in rectangular coordinates).
	public Anchor(double x, double y, double z, double mass) {
		// We'll use the point constructor to normalize the coordinates.
		m_loc = new Point(x, y, z);
		m_mass = mass;
	}
	
	
	/**
	 * Accessors
	 */
	
	// Get the location of the anchor on the unit sphere.
	public Point loc() {
		return m_loc;
	}
	
	// Get the mass of the anchor.
	public double mass() {
		return m_mass;
	}
	
	/**
	 * Mutators
	 */
	
	// Shifts the anchor around the unit sphere by the specified angles.
	public void translate(double theta, double phi) {
		// And that's why we defined it for the point class.
		m_loc.rotate(theta,  phi);
	}
	
	// Change the mass of the anchor.
	public void resize(double mass) {
		m_mass = mass;
	}
}
