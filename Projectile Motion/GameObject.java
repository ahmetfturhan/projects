import java.util.ArrayList;
/**
 * This is the GameObject class, where the data of the game environment stores
 * @author Ahmet Faruk Turhan ID: ***
 * @since 03.05.2020
 */
public class GameObject {
	int type; //type of the rectangle object
	double x; //x-coordinate of the rectangle object
	double y; //y-coordinate of the rectangle object
	double w; //width of the rectangle object
	double h; //height of the rectangle object

	/**
	 * This is the default constructor
	 */
	GameObject() {}

	/**
	 * This is the constructor that we use for storing the data
	 * @param type type of the rectangle object
	 * @param x x-coordinate of the rectangle object
	 * @param y y-coordinate of the rectangle object
	 * @param w width of the rectangle object
	 * @param h height of the rectangle object
	 */
	GameObject(int type, double x, double y, double w, double h) {
		this.type = type;  //
		this.x = x;        //
		this.y = y;        //store the data
		this.w = w;        //
		this.h = h;        //
	}
	/**
	 * This method draws the game environment (obstacles, targets)
	 * @param environment arraylist of environment objects
	 */
	public static void draw(ArrayList<GameObject> environment ) {

		for (int i = 0; i < environment.size(); i++) { //get the x-y coordinate and height-width value of each rectangle object
			int type = environment.get(i).type;
			double x = environment.get(i).x;
			double y = environment.get(i).y;
			double w = environment.get(i).w;
			double h = environment.get(i).h;

			if (type == 1) { //If it's an obstacle, draw it with the color blue
				StdDraw.setPenColor(StdDraw.BOOK_BLUE); //set the pen color to blue
				StdDraw.filledRectangle(x, y, w/2.5, h*2); //draw the rectangle
			}
			else {
				StdDraw.setPenColor(StdDraw.RED); //if it's not an obstacle, draw it with the color red
				StdDraw.filledRectangle(x, y, w/2.5, h*2); //draw the rectangle
			}
		}
	}
	/**
	 * This method determines if the ball is in a rectangle object
	 * @param x x-coordinate of the ball
	 * @param y y-coordinate of the ball
	 * @param environment arraylist of environment objects
	 * @return return boolean of isInside (true or false)
	 */
	public static boolean isInside(double x, double y, ArrayList<GameObject> environment) {

		boolean is = false; //declare and initialize the boolean variable

		for (int i = 0; i < environment.size(); i++) { //get the x-y coordinate and height-width value of each rectangle object
			double x1 = environment.get(i).x;
			double y1 = environment.get(i).y;
			double w = environment.get(i).w;
			double h = environment.get(i).h;

			double topXBound = x1 - (w/2.5); //top left x-coordinate of the rectangle
			double topYBound = y1 + (h*2); //top left y-coordinate of the rectangle

			double bottomXBound = x1 + (w/2.5); //bottom right x-coordinate of the rectangle
			double bottomYBound = y1 - (h*2) ; //bottom right y-coordinate of the rectangle

			if ( (x > topXBound && y < topYBound) && (x < bottomXBound && y > bottomYBound) ) { //if the ball's coordinates within the rectangle's boundaries, return true
				is = true; //change the boolean variable to true

				break; //break the loop because ball is in a object
			}
		}
		return is; //return the result
	}
	/**
	 * This method determines if the hit object is an obstacle or a target
	 * @param x x-coordinate of the ball
	 * @param y y-coordinate of the ball
	 * @param environment environment arraylist of environment objects
	 * @return returns the type of the object
	 */

	public static int whichObject(double x, double y, ArrayList<GameObject> environment) {
		int result = 9; //initialize the variable

		for (int i = 0; i < environment.size(); i++) { //get the x-y coordinate and height-width value of each rectangle object
			int type = environment.get(i).type;
			double x1 = environment.get(i).x;
			double y1 = environment.get(i).y;
			double w = environment.get(i).w;
			double h = environment.get(i).h;

			double topXBound = x1 - (w/2.5); //top left x-coordinate of the rectangle
			double topYBound = y1 + (h*2); //top left y-coordinate of the rectangle

			double bottomXBound = x1 + (w/2.5); //bottom right x-coordinate of the rectangle
			double bottomYBound = y1 - (h*2) ; //bottom right y-coordinate of the rectangle

			if ( (x > topXBound && y < topYBound) && (x < bottomXBound && y > bottomYBound) ) { //if the ball's coordinates within the rectangle's boundaries, store the type of that object
				result = type; //store the type of that object 

				break; //break the loop
			}
		}
		return result; //return the type of the object
	}
}

