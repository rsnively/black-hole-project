package blackhole;

import java.util.ArrayList;

/**
 * "Straight" lines with two endpoints. Rods are not really straight lines
 * because they have to stay within the sphere and you can't make a straight
 * line stay inside a sphere. Instead they are arcs of great circles.
 * 
 * @author Rory
 *
 */
public class Rod {
	
	// The two anchors on either end of the rod.
	private Anchor m_head;
	private Anchor m_tail;
	
	// Extensibility of the rod: true if the rod is able to extend without
	// breaking or twisting any other rods, false otherwise.
	// A rod is extensible if and only if its head is not tail-side.
	private boolean m_extensible;
	
	/**
	 * Constructors
	 */
	
	// Default constructor: the implementation of this sort of hangs on your
	// answers to the below questions.
	public Rod() {
		// For now, create a rod of zero length connecting two mass-less
		// anchors at the north pole.
		m_head = new Anchor();
		m_tail = new Anchor();
		m_head.add(this);
		m_tail.add(this);
		m_extensible = false;
	}
	
	// Copy constructor.
	public Rod(Rod other) {
		// Copy the endpoints over.
		m_head = other.m_head;
		m_tail = other.m_tail;
		
		// Add this rod to the endpoints' connectivity list.
		m_head.add(this);
		m_tail.add(this);
		
		// Rather than copy the other rod's extensibility, we'll check to make
		// sure the situation hasn't changed.
		m_extensible = isExtensible();
	}
	
	// Standard constructor: create a rod given two endpoints.
	public Rod(Anchor head, Anchor tail) {
		
		// @RIVER can a rod have zero length?
		if (head.loc().x() == tail.loc().x() &&
			head.loc().y() == tail.loc().y() &&
			head.loc().z() == tail.loc().z() ) {
			; // Do nothing.
		}
		
		// @RIVER if so, can a rod's head and tail be the same anchor?
		if (head == tail) {
			; // Do nothing.
		}
		
		// Use anchors as endpoints.
		m_head = head;
		m_tail = tail;
		
		// Add this rod to the endpoints' connectivity list.
		m_head.add(this);
		m_tail.add(this);
		
		// Check to see if the rod is extensible.
		m_extensible = isExtensible();
	}
	
	// Constructor given an endpoint, and a vector + a mass
	// For some reason this sounds like it would be useful.
	public Rod(Anchor head, double theta, double phi, double mass) {
		
		// @RIVER can a rod have zero length?
		if (theta == 0 && phi == 0) {
			; // Do nothing.
		}
		
		// Copy head anchor.
		m_head = head;
		// Create tail anchor.
		m_tail = new Anchor(head.loc().theta() + theta,
						    head.loc().phi() + phi,
						    mass);

		// Add this rod to the endpoints' connectivity.
		m_head.add(this);
		m_tail.add(this);
		
		// Check to see if the rod is extensible.
		m_extensible = isExtensible();
	}
	
	/**
	 * Accessors
	 */
	
	// Returns the head anchor.
	public Anchor head() {
		return m_head;
	}
	
	// Returns the tail anchor.
	public Anchor tail() {
		return m_tail;
	}
	
	// Returns whether or not the rod is extensible.
	// DOES NOT check the result, only returns the stored value.
	public boolean extensible() {
		return m_extensible;
	}
	
	// If the isExtensible function is called without any arguments, then this
	// is the rod we are testing, so call the recursive isExtensible() function
	// with an empty list argument, and the head and tail anchors as the 
	// anchors we are testing connectivity to.
	public boolean isExtensible() {
		return isExtensible(m_head, m_tail, new ArrayList<Anchor>());
	}
	
	// Determines whether or not the rod can be extended without twisting or
	// breaking any other rods in the overall structure. What this entails is
	// determining whether the head and tail of the rod are connected through
	// a path not containing this rod.
	//
	// Algorithm:
	// 1) Let T and H be the tail and head of the rod, respectively.
	// 2) Let L be a list of anchors (the argument to this function) which have
	//    already been checked. Initially this list is empty.
	// 3) If T = H, return FALSE.
	// 4) Otherwise, add T to L.
	// 5) For each rod R' attached to T other than R.
	//        - Let A be the anchor on the other end of R'
	//        - If A is already contained in L, skip it.
	//        - Run this algorithm with A as the new tail anchor.
	// 6) If we get back to T without any other anchor returning false, then
	//    we can safely say that the rod is extensible and return TRUE.
	public boolean isExtensible(Anchor head, Anchor tail, ArrayList<Anchor> anchors) {
		
		// If the head and tail anchors are one and the same, then the
		// rod is not extensible.
		if (tail == head) {
			return false;
		}
		
		// Add the tail node to the list of anchors we've checked.
		anchors.add(tail);
		
		// For each rod attached to the tail node:
		ArrayList<Rod> tailrods = tail.rods();
		int size = tailrods.size();
		for (int i = 0; i < size; i++) {
			Rod r = tailrods.get(i);
			// If we are looking at the rod between H and T, or we have found
			// a rod connected to an anchor already in L, we can skip it.
			if (this == r || anchors.contains(r)) {
				continue;
			}
			
			// Get the anchor at the other end of the rod (which could be the
			// rod's head or tail).
			Anchor a;
			if (tail == r.tail()) {
				a = r.head();
			}
			else {
				a = r.head();
			}
			
			// See if the rod's other end is connected to H through some anchor
			// we haven' checked yet.
			if (! isExtensible(head, a, anchors)) {
				return false;
			}
		}
		
		// Once we've ensured that the anchors are not connected, we can return
		// true.
		return true;
	}
	
	/**
	 * Mutators
	 */
	
}
