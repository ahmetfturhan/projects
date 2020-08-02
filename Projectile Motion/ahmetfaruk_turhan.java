import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.awt.Font;
import java.awt.event.KeyEvent;

/**
 * This program simulates the projectile motion, a ball is thrown to hit the targets by adjusting angle and velocity.
 * @author Ahmet Faruk Turhan ID: ****
 * @since 03.05.2020
 */
public class ahmetfaruk_turhan {

	/**
	 * This is the main method
	 * @param args Arguments
	 * @throws FileNotFoundException May throw a FileNotFoundException
	 */
	public static void main(String[] args) throws FileNotFoundException {
		try {
			StdDraw.setCanvasSize(1200, 568); //Create the drawing window	
			StdDraw.setScale(0, 1500); //Limit the screen boundaries between 0, 1500
			StdDraw.enableDoubleBuffering(); //Useful for animations

			playGame(); //call the game method to play the game

			StdDraw.show(); //This line of code needs to be entered if doubleBuffering is used.

			while (true) { //Control the game 
				if (StdDraw.isKeyPressed(KeyEvent.VK_P)) { //If key P is pressed, restart the game.
					StdDraw.clear(); //clear drawing window
					StdDraw.show();
					playGame(); //call the game method to play game again
					StdDraw.show();
				}
				if (StdDraw.isKeyPressed(KeyEvent.VK_Q))  //If key Q is pressed, quit the game
					break; //break the while loop

			}
			System.exit(0); //Finally exit the program if the while loop above breaks


		} catch (FileNotFoundException ex) { //handle the fileNotFound exception
			System.out.println("Game environment file cannot found"); //notify the user
		}

	}
	/**
	 * This method reads the text files that contains game environment's coordinates, rectangular's height, width, etc. and stores them into an arrayList.
	 * @param games File object to read the data
	 * @return ArrayList of the data in the game environment
	 * @throws FileNotFoundException may throw a FileNotFoundException
	 */
	public static ArrayList<GameObject> readGameEnvironment(File games) throws FileNotFoundException {
		ArrayList<GameObject> game = new ArrayList<GameObject>(); //An arraylist to store the data
		Scanner input = new Scanner(games); //Scanner object to read the data
		int counter = 0; //A counter to avoid jumping line 1

		while(input.hasNextLine()) { //read the text file

			if (counter == 0) {             //
			}                               //
			else {                          //
				input.nextLine();           // for some reason, scanner won't jump into the next line, so I did it manually.
				input.reset();              //
			}

			input.useDelimiter(";"); //use ";" as a delimiter to read the data correctly

			int type = input.nextInt(); //read the type of the current object

			double x = input.nextDouble(); //read the x-coordinate of the current object

			double y = input.nextDouble(); //read the y-coordinate of the current object

			double w = input.nextDouble(); //read the width of the current object

			input.reset(); //reset the Scanner to avoid inputMismatch exception
			input.useDelimiter(""); //Use "" (nothing) as a delimiter to skip the last semicolon
			input.next(); //skip the semicolon
			input.reset(); //reset the scanner to it's defaults

			double h = input.nextDouble(); // read the height of the current object

			GameObject temp = new GameObject(type, x, y, w, h); //store the data into a GameObject
			game.add(temp); //add the data into the arraylist
			counter++; //increase the line counter
		}

		input.close(); //close the Scanner object
		return game; //return the arraylist
	}
	/**
	 * This is the method to play the game
	 * @throws FileNotFoundException may throw a FileNotFoundException 
	 */
	public static void playGame() throws FileNotFoundException {
		Font ti = new Font("Sans Serif", Font.BOLD, 20); //create a font
		File games = new File("game_environment.txt"); //File object to read the data
		ArrayList<GameObject> gameObjects = readGameEnvironment(games); //read the text file into an arraylist

		StdDraw.setFont(ti); //set the font
		StdDraw.setPenColor(StdDraw.LIGHT_GRAY); //set pen color to gray
		StdDraw.text(1300, 1400, "Press P to play again"); //notify the user
		StdDraw.text(1000, 1400, "Press Q to quit"); //notify the user
		StdDraw.show(); //show
		StdDraw.setFont(); //set font to default

		final double g = 9.81; //gravity of the earth
		double v = 140; //initial velocity
		double angle = 70; //initial angle


		double x0 = 200.0; //initial x-coordinate
		double y0 = 200.0; //initial y-coordinate


		double x = x0; //set the x-coordinate
		double y = y0; //set the y-coordinate

		double t = 0.0; //initial time in seconds

		double r = 2; //radius of the ball

		StdDraw.setPenColor(StdDraw.GREEN); //set pen color to green
		StdDraw.filledRectangle(0, 0, 200, 200); //draw a table for the ball
		GameObject.draw(gameObjects); //draw the game environment (obstacles, targets)
		StdDraw.setPenColor(); //set pen color to default
		StdDraw.filledCircle(x0, y0, r); //draw the ball
		StdDraw.show(); //show

		int textX = 150; //texts coordinate
		int textYV = 1450; //velocity text's y-coordinate
		int textYA = 1380; //angle text's y-coordinate

		boolean isInside = false; //Determine if the ball is inside an object
		int objectType = 9; //determine the object type, whether it is a target or an obstacle

		StdDraw.setPenColor(); //set pen color to default
		StdDraw.setFont(ti); //set font
		StdDraw.text(textX, textYV, "Velocity: " +Double.toString(v)); //write the velocity value on the top left of the drawing screen 
		double anglee = Math.round(angle * 100.0) / 100.0; //format the angle to two digit (xx.xx)
		StdDraw.text(textX, textYA, "Angle: "+Double.toString(anglee)); //write the angle value on the top left of the drawing screen
		StdDraw.show(); //show

		double vrr = v * 0.009; //length of the trajectory

		double angleTT = Math.toRadians(angle); //convert angle degrees to radians to use in a formula

		double vxx = v * Math.cos(angleTT); //velocity's x value
		double vyy = v * Math.sin(angleTT); //velocity's y value

		double xtt = x0 + (vxx * vrr);                            //
		double ytt = y0 + (vyy * vrr) - (0.5 * g * vrr * vrr );   // draw the trajectory according to projectile motion formula

		StdDraw.setPenColor(StdDraw.MAGENTA); //set pen color to magenta
		StdDraw.line(x0, y0, xtt, ytt); //draw the trajectory line
		StdDraw.show(); //show

		while (true) { 
			if (StdDraw.isKeyPressed(KeyEvent.VK_LEFT)) { //if left key pressed, decrease the velocity
				StdDraw.setPenColor(StdDraw.WHITE); //set pen color to white
				StdDraw.filledRectangle(textX, textYV, 90, 30); //draw a white rectangle over the text every time velocity changes
				StdDraw.show(); //show

				v = v - 1; //decrease the velocity by 1

				double vr = v * 0.009; //length of the trajectory

				double angleT = Math.toRadians(angle); //convert angle degrees to radians to use in a formula

				double vx = v * Math.cos(angleT); //velocity's x value
				double vy = v * Math.sin(angleT); //velocity's y value

				double xt = x0 + (vx * vr);                            // set the trajector's x-coordinate according to projectile motion formula
				double yt = y0 + (vy * vr) - (0.5 * g * vr * vr );     // set the trajector's y-coordinate according to projectile motion formula

				StdDraw.setPenColor(); //set pen color to black
				StdDraw.text(textX, textYV, "Velocity: " +Double.toString(v)); //write the velocity value on the top left of the drawing screen

				StdDraw.setPenColor(StdDraw.WHITE); //set pen color to white
				StdDraw.filledRectangle(x0+120, y0+271, 130, 270); //everytime velocity changes, draw a white rectangle over the trajectory line
				StdDraw.show(); //show

				StdDraw.setPenColor(); //set pen color to default
				StdDraw.filledCircle(x0, y0, r); //draw the ball again because it's covered by the white rectangle

				StdDraw.setPenColor(StdDraw.MAGENTA); //set pen color to magenta
				StdDraw.line(x0, y0, xt, yt); //draw the trajectory line

				StdDraw.show(); //show
				StdDraw.pause(200); //wait 200 milliseconds
			} 
			if (StdDraw.isKeyPressed(KeyEvent.VK_RIGHT)) { //if right key pressed, increase the velocity
				StdDraw.setPenColor(StdDraw.WHITE); //set pen color to white
				StdDraw.filledRectangle(textX, textYV, 90, 30); //draw a white rectangle over the text, everytime it changes
				StdDraw.show(); //show

				v = v + 1; //increase the velocity by 1

				double vr = v * 0.009; //length of the trajectory

				double angleT = Math.toRadians(angle); //convert angle degrees to radians to use in a formula

				double vx = v * Math.cos(angleT); //velocity's x value
				double vy = v * Math.sin(angleT); //velocity's y value

				double xt = x0 + (vx * vr);                        //set the trajector's x-coordinate according to projectile motion formula
				double yt = y0 + (vy * vr) - (0.5 * g * vr * vr ); //set the trajector's y-coordinate according to projectile motion formula

				StdDraw.setPenColor(); //set pen color to default
				StdDraw.text(textX, textYV, "Velocity: " +Double.toString(v)); //write the velocity value on the top left of the drawing screen

				StdDraw.setPenColor(StdDraw.WHITE); //set pen color to white
				StdDraw.filledRectangle(x0+120, y0+271, 130, 270); //everytime velocity changes, draw a white rectangle over the trajectory line
				StdDraw.show(); //show

				StdDraw.setPenColor(); //set pen color to default
				StdDraw.filledCircle(x0, y0, r); //draw the ball again

				StdDraw.setPenColor(StdDraw.MAGENTA); //set pen color to magenta
				StdDraw.line(x0, y0, xt, yt); //draw the trajectory line
				StdDraw.show(); //show

				StdDraw.pause(200); //pause for 200 milliseconds
			}

			//angle
			if (StdDraw.isKeyPressed(KeyEvent.VK_UP)) { //if up key pressed, increase the angle
				StdDraw.setPenColor(StdDraw.WHITE); //set pen color to white
				StdDraw.filledRectangle(textX, textYA, 150, 25); //draw a white rectangle over the text, everytime it changes
				StdDraw.show();

				angle = angle + 0.50; //increase the angle by 0.50

				double vr = v * 0.009; //length of the trajectory line
				double angleT = Math.toRadians(angle); //convert angle degrees to radians to use in a formula
				double vx = v * Math.cos(angleT); //velocity's x value
				double vy = v * Math.sin(angleT); //velocity's y value

				double xt = x0 + (vx * vr);                        //set the trajector's x-coordinate according to projectile motion formula
				double yt = y0 + (vy * vr) - (0.5 * g * vr * vr ); //set the trajector's y-coordinate according to projectile motion formula

				anglee = Math.round(angle * 100.0) / 100.0; //format the angle to last 2 digits (xx.xx)

				StdDraw.setPenColor(); //set pen color to black
				StdDraw.text(textX, textYA, "Angle: "+Double.toString(anglee)); //write the angle value on the top left of the drawing screen

				StdDraw.setPenColor(StdDraw.WHITE); //set pen color to white
				StdDraw.filledRectangle(x0+120, y0+271, 130, 270); //everytime velocity changes, draw a white rectangle over the trajectory line
				StdDraw.show(); //show

				StdDraw.setPenColor(); //set pen color to black
				StdDraw.filledCircle(x0, y0, r); //draw  the ball again

				StdDraw.setPenColor(StdDraw.MAGENTA); //set pen color to magenta
				StdDraw.line(x0, y0, xt, yt); //draw the trajectory line
				StdDraw.show(); //show

				StdDraw.pause(200); //wait for 200 ms
			}
			if (StdDraw.isKeyPressed(KeyEvent.VK_DOWN)) { //if down key is pressed, decrease the angle
				StdDraw.setPenColor(StdDraw.WHITE); //set pen color to white
				StdDraw.filledRectangle(textX, textYA, 150, 25); //draw a white rectangle over the text, everytime it changes
				StdDraw.show(); //show

				angle = angle - 0.50; //decrease the angle by 0.50

				double vr = v * 0.009; //length of the trajectory line

				double angleT = Math.toRadians(angle); //convert angle degrees to radians to use in a formula

				double vx = v * Math.cos(angleT); //velocity's x value
				double vy = v * Math.sin(angleT); //velocity's y value

				double xt = x0 + (vx * vr);                        //set the trajector's x-coordinate according to projectile motion formula
				double yt = y0 + (vy * vr) - (0.5 * g * vr * vr ); //set the trajector's y-coordinate according to projectile motion formula

				anglee = Math.round(angle * 100.0) / 100.0; //format the angle to last 2 digits (xx.xx)

				StdDraw.setPenColor(); //set pen color to black
				StdDraw.text(textX, textYA,"Angle: "+ Double.toString(anglee)); //write the angle value on the top left of the drawing screen

				StdDraw.setPenColor(StdDraw.WHITE); //set pen color to white
				StdDraw.filledRectangle(x0+120, y0+271, 130, 270); //everytime velocity changes, draw a white rectangle over the trajectory line
				StdDraw.show(); //show

				StdDraw.setPenColor(); //set pen color to black
				StdDraw.filledCircle(x0, y0, r); //draw the ball again

				StdDraw.setPenColor(StdDraw.MAGENTA); //set pen color to magenta
				StdDraw.line(x0, y0, xt, yt);//draw the trajectory line

				StdDraw.show();
				StdDraw.pause(200); //wait for 200ms
			}
			if (StdDraw.isKeyPressed(KeyEvent.VK_SPACE)) //if space key is pressed, throw the ball by breaking the loop
				break;

			if (StdDraw.isKeyPressed(KeyEvent.VK_Q)) //if key Q is pressed, exit the game
				System.exit(0);

		}
		angle = Math.toRadians(angle); //convert angle degrees to radians

		double vx = v * Math.cos(angle); //set the velocity's x value 
		double vy = v * Math.sin(angle); //set the velocity's y value

		Font sm = new Font("Sans Serif", Font.PLAIN, 10); //create a new font


		while (true) {

			if (isInside == true && objectType == 1) { //if ball hits an object and if it's a obstacle

				StdDraw.setPenColor(StdDraw.BOOK_BLUE); //set pen color to blue
				StdDraw.text(150, 1310, "Hit the obstacle"); //notify the user
				StdDraw.setPenColor(); //set pen color to default

				double xBall = x; //x-coordinate of the ball

				xBall = Math.round(xBall * 10.0) / 10.0; //format the decimal (#.x)

				StdDraw.setFont(sm); //set font
				StdDraw.text(x+20, y+20, Double.toString(xBall)); //write the hit point to next to the ball

				StdDraw.show(); //show
				StdDraw.setFont(); //set font to default

				break; //break the loop
			}

			if (isInside == true && objectType == 2) { //if ball hits an object and if it's a target

				StdDraw.setPenColor(StdDraw.RED); //set pen color to red
				StdDraw.text(150, 1310, "Hit the target"); //notify the user
				StdDraw.setPenColor(); //set pen color to default

				double xBall = x; //x-coordinate of the ball

				xBall = Math.round(xBall * 10.0) / 10.0; //format the decimal (#.x)

				StdDraw.setFont(sm); //set font
				StdDraw.text(x+20, y+20, Double.toString(xBall)); //write the hit point to next to the ball

				StdDraw.show(); //show
				StdDraw.setFont(); //set font to default

				break; //break the loop
			}

			if (y < 0 || x > 1500 || x < 0) { //if the ball goes out of the screen boundaries

				StdDraw.setPenColor(StdDraw.BLUE); //set pen color to blue
				StdDraw.textLeft(60, 1310, "You couldn't even hit an obstacle! Try again."); //notify the user
				StdDraw.setPenColor(); //set pen color to default

				double xBall = x; //x-coordinate of the ball

				xBall = Math.round(xBall * 10.0) / 10.0; //format the decimal (#.x)

				StdDraw.setFont(sm); //set font
				StdDraw.text(x+20, y+20, Double.toString(xBall)); //write the hit point to next to the ball

				StdDraw.show(); //show
				StdDraw.setFont(); //set font to default

				break; //break the loop
			}
			x = x0 + (vx * t); //set the x coordinate of the ball according to projectile motion formula
			y = y0 + (vy * t) - (0.5 * g * t * t ); //set the y coordinate of the ball according to projectile motion formula


			StdDraw.setPenColor(); //set pen color to black
			StdDraw.filledCircle(x, y, r); //draw the ball with new coordinates

			StdDraw.show(); //show

			objectType = GameObject.whichObject(x, y, gameObjects); //determine the object type
			isInside = GameObject.isInside(x, y, gameObjects); //determine if the ball is inside an object

			StdDraw.pause(10); //wait for 10ms

			t += 0.1; //increase the time, otherwise the ball won't move
		}
	}
}
