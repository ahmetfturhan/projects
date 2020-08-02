/**
 * This class is for creating a circle object. 
 * @author Ahmet Faruk Turhan ID:*********
 * @since 9.03.20
 *
 */
public class Circles {
	//data fields
	private double x; //x-coordinate of the circle
	private double y; //y-coordinates of the circle
	private double r; //radius of the circle

	/**
	 * default constructor
	 */
	Circles() {
	}
	/**
	 * This constructor is created to avoid NullPointerException
	 * @param rIn radius of the circle
	 */
	Circles(double rIn) {
		r = rIn;
	}
	/**
	 * Constructor of the Circles object
	 * @param xIn x-coordinate of the circle.
	 * @param yIn y-coordinate of the circle.
	 * @param rIn radius of the circle.
	 */
	Circles(double xIn, double yIn, double rIn) {
		x = xIn; //assigning
		y = yIn; //assigning
		r = rIn; //assigning
	}
	/**
	 * Setter method for x
	 * @param x x-coordinate of the circle
	 */
	public void setX(double x) {
		this.x = x;
	}
	/**
	 * Setter method for y
	 * @param y y-coordinate of the circle
	 */
	public void setY(double y) {
		this.y = y;
	}
	/**
	 * Setter method for radius
	 * @param r radius of the circle
	 */
	public void setR(double r) {
		this.r = r;
	}
	/**
	 * Getter method for x
	 * @return x-coordinate of a circle
	 */
	public double getX() {
		return x;
	}
	/**
	 * Getter method for y
	 * @return y-coordinate of a circle
	 */
	public double getY() {
		return y;
	}
	/**
	 * Getter method for radius
	 * @return radius of a circle
	 */
	public double getR() {
		return r;
	}

}
