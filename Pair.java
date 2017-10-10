/**
 * A Pair object represents a pair of states denoted by their numbers.  We create a pair by passing in
 * two integers, one for the x coordinate and one for the y coordinate respectively.  We provide a few
 * methods for getting the coordinates as well as a print method.
 *
 * @author Derek Leung
 */

public class Pair {
	private final int x;
	private final int y;
	
	public Pair(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public String toString() {
		return "(" + x + "," + y + ")";
	}
}
