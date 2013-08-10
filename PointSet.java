package blackhole;

import java.util.ArrayList;

/**
 * Custom class for a set of points. I'm assuming we're oging
 * to make some cool custom functions.
 * 
 * @author Rory
 */
public class PointSet {

	ArrayList<Point> m_points;
	
	/**
	 * Constructors
	 */
	
	// Default constructor, creates empty list.
	public PointSet() {
		m_points = new ArrayList<Point>();
	}
	
	// Copy constructor, creates one list from another.
	public PointSet(PointSet other) {
		m_points = other.m_points;
	}
	
	/**
	 * Accessors
	 */
	
	// Returns the number of points in the set.
	public int size() {
		return m_points.size();
	}
	
	// Returns the point at the given index.
	public Point get(int index) {
		return m_points.get(index);
	}
	
	// Find the average of a set of points.
	public Point average() {
		double x_avg = 0;
		double y_avg = 0;
		double z_avg = 0;
		
		for (int i = size()-1; i >= 0; i++) {
			x_avg += get(i).x();
			y_avg += get(i).y();
			z_avg += get(i).z();
		}
		
		x_avg /= size();
		y_avg /= size();
		z_avg /= size();
		
		return new Point(x_avg, y_avg, z_avg);
	}
	
	/**
	 * Mutators
	 */
	
	// Replaces the point at the given index with the given point.
	public Point set(int index, Point p) {
		return m_points.set(index, p);
	}
	
	// Append a point to the end of the list.
	public boolean add(Point p) {
		return m_points.add(p);
	}
	
	// Add a point at the specified index.
	public void add(int index, Point p) {
		m_points.add(index, p);
	}
	
	// Removes the point at the specified index.
	public Point remove(int index) {
		return m_points.remove(index);
	}
}
