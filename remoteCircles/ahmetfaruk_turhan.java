/**
 * This program draws n circles in the screen boundaries and finds the maximum distance between two circles, then draws a red line between them.
 * @author Ahmet Faruk Turhan ID:*******
 * @since 2.03.20
 */
public class ahmetfaruk_turhan {
	/**
	 * this is the main method
	 * @param args arguments
	 */
	public static void main(String[] args) {
		int circleNum = 100; //number of circles

		Circles []circleArray = new Circles[circleNum]; //create an array of Circles object and set it's length to number of circles.

		// a loop for assign random values to x-coordinate, y-coordinate and radius.
		for (int i = 0; i < circleArray.length; i++) {
			double r = (Math.random() * 0.04) + 0.02; //assign random radius between 0.02 < radius < 0.06
			circleArray[i] = new Circles(r); //save radius to avoid NullPointerException

			double x = (Math.random() * (0.94 - circleArray[i].getR()) ) + circleArray[i].getR(); //assign random x values and generating coordinates in drawing screen boundaries.
			double y = (Math.random() * (0.94 - circleArray[i].getR()) ) + circleArray[i].getR(); //assign random y values and generating coordinates in drawing screen boundaries.

			circleArray[i] = new Circles(x, y, r); //save random x, y and radius values into the object
		}


		StdDraw.setCanvasSize(700, 700); //creating a 700x700 sized window

		//setting the scale of the x-y plane
		StdDraw.setXscale(0, 1);
		StdDraw.setYscale(0, 1);

		StdDraw.setPenColor(StdDraw.BLACK); //set pen color to black in order to set circle colors to black

		draw(circleArray); //draw n circles

		double currentMaxDistance = 0; //declaring & initializing the current max distance variable.

		double []maxCircles = new double[4]; //this array is  for storing the x-y coordinates of the longest distance circles. This is needed for drawing the line.
		//maxCircles[0] is the x coordinate of the first circle (x1)
		//maxCircles[1] is the y coordinate of the first circle (y1)
		//maxCircles[2] is the x coordinate of the second circle (x2)
		//maxCircles[3] is the y coordinate of the second circle (y2)

		double []maxCircleRadius = new double[2]; //this array is for storing the longest distance circles radiuses. This is needed for drawing the thick circles.
		//maxCircleRadius[0] is the radius of the first circle
		//maxCircleRadius[1] is the radius of the second circle

		//this loop compares all circles with each other to find the maximum distance between two circles.
		for (int i = 0; i < circleArray.length; i++) {
			for (int j = 0; j < circleArray.length; j++) {

				//if next distance is bigger than the current distance, then do following.
				if (distance(circleArray[i].getX(), circleArray[i].getY(), circleArray[j].getX(), circleArray[j].getY()) > currentMaxDistance) {
					maxCircles[0] = circleArray[i].getX(); //first circle's x-coordinate
					maxCircles[1] = circleArray[i].getY(); //first circle's y-coordinate
					maxCircles[2] = circleArray[j].getX(); //second circle's x-coordinate
					maxCircles[3] = circleArray[j].getY(); //second circle's y-coordinate

					maxCircleRadius[0] = circleArray[i].getR(); //first circle's radius
					maxCircleRadius[1] = circleArray[j].getR(); //second circle's radius

					currentMaxDistance = distance(circleArray[i].getX(), circleArray[i].getY(), circleArray[j].getX(), circleArray[j].getY()); //update max distance value
				}

			}
		}
		StdDraw.setPenColor(StdDraw.RED); //set pen color to red
		StdDraw.line(maxCircles[0], maxCircles[1],maxCircles[2], maxCircles[3]); //draw the line between the circles

		StdDraw.setPenColor(StdDraw.BLACK); //set pen color to black
		StdDraw.setPenRadius(0.006); //set pen radius to 0.006 for drawing thick circles	
		StdDraw.circle(maxCircles[0], maxCircles[1], maxCircleRadius[0]); //thicker circle
		StdDraw.circle(maxCircles[2], maxCircles[3], maxCircleRadius[1]); //thicker circle


	}
	/**
	 * Draws n circle 
	 * @param circleArray this object includes x-y coordinate and radius of the circles.
	 */
	public static void draw(Circles []circleArray) {
		for (int i = 0; i < circleArray.length; i++) {
			StdDraw.circle(circleArray[i].getX(), circleArray[i].getY(), circleArray[i].getR());
		}
	}
	/**
	 * This method uses Euclidean Distance formula to find the distance between two circles.
	 * @param x1 x-coordinate of the first circle
	 * @param y1 y-coordinate of the first circle
	 * @param x2 x-coordinate of the second circle
	 * @param y2 y-coordinate of the second circle
	 * @return the distance
	 */
	public static double distance(double x1, double y1, double x2, double y2) {
		double x2x1_m = Math.pow(x2 - x1, 2); //power of two of x2 - x1
		double y2y1_m = Math.pow(y2 - y1, 2); //power of two of y2 - y1

		double distance = Math.sqrt(x2x1_m + y2y1_m); //square root of the sum

		return distance; //returning distance


	}

}
