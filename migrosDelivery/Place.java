/**
 * This is the Place object to store the x,y coordinates and types of the places.
 * @author Ahmet Faruk Turhan ID: *****
 * @since 21.04.2020
 *
 */
public class Place {

	int placeType; //type of the place
	double x; //x-coordinate of the place
	double y; //y-coordinate of the place

	/**
	 * This is the default constructor
	 */
	Place() {}

	/**
	 * This is the constructor to store the data
	 * @param placeType type of a place
	 * @param x x-coordinate of the place
	 * @param y y-coordinate of the place
	 */
	Place(int placeType, double x, double y) {

		this.placeType = placeType; //
		this.x = x;                 // Store the data
		this.y = y;                 //
	}
}
