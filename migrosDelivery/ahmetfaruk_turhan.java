import java.awt.Font;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
/**
 * This program simulates the route of a Migros Delivery and finds the best route to follow.
 * @author Ahmet Faruk Turhan ID: ******
 * @since 21.04.2020 
 */

public class ahmetfaruk_turhan {
	/**
	 * This is the main method.
	 * @param args Arguments
	 */
	public static void main(String[] args) {

		try {

			String fileName = "sa"; //file name 
			System.out.print("Press 1 for data1.txt \nPress 2 for data2.txt \nPress 3 for data3.txt \n>: "); //notify the user
			Scanner userInput = new Scanner(System.in); //a scanner object to get the user input
			int dataNumber = userInput.nextInt(); //store the user input
			switch (dataNumber) { 
			case 1:
				fileName = "data1.txt"; //set filename to data1.txt
				break;
			case 2:
				fileName = "data2.txt"; //set filename to data2.txt
				break;
			case 3:
				fileName = "data3.txt"; //set filename to data3.txt
				break;
			default:
				System.out.println("Please enter a valid number"); //if there is no match
			}


			File home = new File(fileName); //File object to read data file

			ArrayList<Place> place = readDataFile(home); //ArrayList to store the home data

			ArrayList<Integer> currentPath = new ArrayList<Integer>();

			ArrayList<Integer> bestPath = new ArrayList<Integer>();

			int numberOfTry = 50000000; // this program executes 50 million times

			StdDraw.setCanvasSize(600, 600); //600x600 window

			StdDraw.text(0.500, 0.500, "Calculating... Please Wait");							//
			StdDraw.text(0.500, 0.400, "random paths generated: "+numberOfTry);                 //
			StdDraw.text(0.500, 0.360, "If it cannot find the quickest, please run it again."); //    Nothing important here
			Font font = new Font("Arial Black", Font.PLAIN, 18); //set font                     //
			StdDraw.setFont(font);                                                              //
			StdDraw.text(0.500, 0.600, "Thank you for your patience :)");                       //

			int migrosLine = findMigros(home) - 1; //Find the line that contains "Migros" (arrays start from 0, so i subtract 1)

			double currentDistance = 40.0; //initial value for current distance
			double minDistance = 30.0; //initial value for minimum distance

			int []best = new int[13]; //An array to store the best path

			for (int j = 0; j < numberOfTry; j++) {

				currentPath.clear(); //clear the path to avoid infinite sized ArrayList

				currentPath.add(migrosLine); //every path starts with Migros

				for (int i = 0; i < place.size() - 1; i++) { //11 times

					int num = (int)(Math.random() * 12); //generate random numbers between 0 and 12 [0, 12)

					if (currentPath.size() == 12) //extra security to avoid infinite loop
						break;

					while (currentPath.contains(num)) //if a home already exists in the ArrayList, generate another one.
						num = (int)(Math.random() * 12); //generate random numbers between 0 and 12 [0, 12)

					currentPath.add(num); //add home to the path
				}
				currentPath.add(migrosLine); //every path ends with Migros

				currentDistance = calculatePath(place, currentPath); //calculate the random generated path's distance

				if (currentDistance < minDistance) { //compare if it's the smallest distance
					best = copyArray(currentPath); //store the best path into an array
					minDistance = currentDistance; //store the minimum distance
					currentPath.clear(); //clear the path
				}
			}

			bestPath = arrayCopy(best); //copy array into the ArrayList
			printPath(bestPath); //print the best path
			System.out.println("\nDistance: " + minDistance); //print the minimum distance

			StdDraw.clear(); //clear the drawing window
			
			StdDraw.setFont();
			StdDraw.text(0.8013,0.0100, "total: "+Double.toString(minDistance)+" km"); //print the minimum distance
			Font font1 = new Font("Arial Black", Font.PLAIN, 17); //set font
			StdDraw.setFont(font1);                                                              //


			StdDraw.setPenColor(StdDraw.RED); //set pen color to red

			for (int i = 0; i < bestPath.size() - 1; i++) { //draw lines between homes
				int currentIndex = i; //current index (to improve readability)
				int nextIndex = i + 1; //next index (to improve readability)
				StdDraw.line(place.get(bestPath.get(currentIndex)).x, place.get(bestPath.get(currentIndex)).y, //coordinates of the current home 
						place.get(bestPath.get(nextIndex)).x, place.get(bestPath.get(nextIndex)).y); // coordinates of the next home
			}

			for (int i = 0; i < place.size(); i++) { //draw circles and home numbers
				if (i == migrosLine) { 
					StdDraw.setPenColor(StdDraw.BOOK_BLUE); //set pen color to blue
					StdDraw.filledCircle(place.get(i).x, place.get(i).y, 0.03); //draw a circle for migros
					StdDraw.setPenColor(StdDraw.WHITE); //set pen color to white
					StdDraw.text(place.get(i).x, place.get(i).y, Integer.toString(migrosLine + 1)); //write the number of migros
				}
				else {
					StdDraw.setPenColor(); //set pen color to black
					StdDraw.filledCircle(place.get(i).x, place.get(i).y, 0.02); //draw a circle for home
					StdDraw.setPenColor(StdDraw.WHITE); //set pen color to white
					StdDraw.text(place.get(i).x, place.get(i).y, Integer.toString(i+1)); //write the number of the home
				}
			}
			userInput.close(); //close the Scanner object

		} catch (FileNotFoundException ex) { //catch the FileNotFoundException
			System.out.println("data file can not found. (or you entered an invalid number)"); //notify user
		}

	}
	/**
	 * This method finds the line that contains "Migros", this method helps while reading data files and drawing circles.
	 * @param home File object to read the data file
	 * @return The line that contains "Migros"
	 * @throws FileNotFoundException if there is no data file, throws an exception
	 */
	public static int findMigros(File home) throws FileNotFoundException {
		Scanner in = new Scanner(home); //Scanner object to read the data file

		int lineNumber = 0; //counting lines
		int migrosLine = -1; //a variable to store the line of migros
		boolean isIn = false; //a boolean variable to check if there is a "Migros" in the line

		in.useDelimiter(","); //set the delimiter to ","
		while (in.hasNextLine()) { //while there is more line, execute
			String currentLine = in.nextLine(); //read the current line
			lineNumber++; //increase the line number 

			if ( !(currentLine.indexOf("Migros") == -1)) { //if indexOf returns a -1 it means there is no occurrence
				isIn = true; //set boolean to true
				migrosLine = lineNumber; //store the line of the migros
			}
			if (isIn == true) { //if Migros found, terminate the loop
				break; //break the loop
			}

		}
		in.close(); //close the Scanner object
		return migrosLine; //return the line of the Migros
	}
	/**
	 * This method reads the data files, stores it into an ArrayList
	 * @param home A File object to read the txt files
	 * @return returns an ArrayList that contains coordinates of the places
	 * @throws FileNotFoundException may throw an exception if there is no data file
	 */
	public static ArrayList<Place> readDataFile(File home) throws FileNotFoundException {

		ArrayList<Place> places = new ArrayList<Place>(); //an arraylist to store the data
		Scanner input = new Scanner(home); //A Scanner object to read the file
		int migros = findMigros(home); //The line that contains "Migros"
		int lineCounter = 0; //counter for line

		input.useDelimiter(","); //set the delimiter to ","

		while (input.hasNextLine()) { //while there is more line, continue the loop

			lineCounter++; //increment the counter

			if (lineCounter == migros) { //if the current line contains "Migros", do the following
				int placeType = 1; //set place type to 1
				input.useDelimiter(","); //set delimiter to "," every time the loop executes

				String tempX = input.next(); //read the following as a String
				Double x = Double.valueOf(tempX); //convert temporary string to double


				String tempY = input.next(); //read the following as a String
				Double y = Double.valueOf(tempY); //convert temporary string to double

				input.reset(); //reset the scanner
				input.useDelimiter(""); //use nothing as a delimiter to skip the ","
				input.next(); //skip the ","
				input.reset(); //reset the Scanner

				input.next(); //Skip the "Migros" part

				Place newHome = new Place(placeType, x, y); //store the data using the constructor

				places.add(newHome); //add new place to the arraylist
			}
			input.useDelimiter(","); //use "," as delimiter
			int placeNum = 2; //set the placeType to 2 (for homes)
			String tempX = input.next(); //read the following as a string
			Double x = Double.valueOf(tempX); //convert the temporary string to the double
			input.reset(); // reset the scanner
			input.useDelimiter(""); //use nothing as a delimiter to skip the ","
			input.next(); //skip the ","
			input.reset(); //reset the scanner

			String tempY = input.next(); //read the following as a string
			Double y = Double.valueOf(tempY); //convert the temporary string to the double

			Place newHome = new Place(placeNum, x, y);//store the data using the constructor

			places.add(newHome);//add new place to the arraylist
		}
		input.close(); //close the scanner object
		return places; //return the ArrayList of places
	}
	/**
	 * This method finds the distance between two places using Euclidean Distance formula
	 * @param firstX x-coordinate of the first place
	 * @param firstY y-coordinate of the first place
	 * @param secondX x-coordinate of the second place
	 * @param secondY y-coordinate of the second place
	 * @return returns the distance between two places
	 */
	public static double findDistance(double firstX, double firstY, double secondX, double secondY) {

		double x2x1_sa = (secondX - firstX); //x2 - x1
		double y2y1_as = (secondY - firstY); //y2 - y1
		double x2x1 = Math.pow(x2x1_sa, 2); //power of two of x2 - x1
		double y2y1 = Math.pow(y2y1_as, 2); //power of two of y2 - y1

		double distance = Math.sqrt(x2x1 + y2y1); //square root of the sum

		return distance; //return the distance

	}
	/**
	 * This method calculates the total distance of a path.
	 * @param place place ArrayList to get the coordinates of the places
	 * @param path provide the path
	 * @return returns the total distance of a path
	 */
	public static double calculatePath(ArrayList<Place> place, ArrayList<Integer> path) {
		double total = 0.0; //declare the total
		double distance = 0.0; //declare the distance

		for (int i = 0; i < place.size()  ; i++) { //finds the distance of the path
			distance = findDistance(place.get(path.get(i)).x, place.get(path.get(i)).y, 
					place.get(path.get(i+1)).x, place.get(path.get(i+1)).y); // finds the distance between two places

			total += distance; //add the distance value to total
		}
		return total; //return the distance of a path
	}
	/**
	 * This method prints the path in the correct format.
	 * @param path the Integer ArrayList to provide the path
	 */
	public static void printPath(ArrayList<Integer> path) {

		System.out.print("Best Path: [");
		for (int i = 0; i < path.size(); i++) { //print the elements of the path ArrayList
			if (i == path.size() - 1)
				System.out.print(path.get(i)+1);
			else
				System.out.print(path.get(i)+1 + ", ");

		}
		System.out.print("]");
	}
	/**
	 * This method copies an ArrayList to an Array
	 * @param list the ArrayList to copy
	 * @return returns array version of the ArrayList
	 */
	public static int[] copyArray(ArrayList<Integer> list) {
		int[] newArray = new int[13]; //create a new array to store the data
		for (int i = 0; i < list.size(); i++) { //copy the elements
			newArray[i] = list.get(i); //copy the elements
		}
		return newArray; //return the array
	}
	/**
	 * This method copies an Array to an ArrayList
	 * @param best the array to copy
	 * @return returns the copied ArrayList
	 */
	public static ArrayList<Integer> arrayCopy(int []best) {
		ArrayList<Integer> newArray = new ArrayList<Integer>(); //create a new arraylist to store the data
		for (int i = 0; i < best.length; i++) {
			newArray.add(best[i]); //copy the elements
		}
		return newArray; //return the ArrayList
	}
}
