import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;

/**
 * This program reads the city database and calculates the total distance of the given trip
 * @author Ahmet Faruk Turhan ID: *********
 * @since 22.03.2020
 */
public class ahmetfaruk_turhan {
	/**
	 * This is the main method.
	 * @param args Arguments
	 * @throws FileNotFoundException it may throw an FileNotFoundException
	 */
	public static void main(String[] args) throws FileNotFoundException {

		String fileName = "city.txt"; //Name of the file

		try {

			File city = new File(fileName); //File object to read the city.txt
			ArrayList<City> cities = readCityFile(city); // an ArrayList to store the cities and their data

			Scanner in = new Scanner(System.in); //Scanner object to get an input from the user.

			System.out.print("Which trip file do you want to use?\n Press a for trip1.txt\n Press b for trip2.txt\n "
					+ "Press c for trip3.txt\n Press d for trip4.txt\n Press e for trip5.txt\n Press q to exit program\n>: "); //ask user to which file to read
			String userInput = in.next(); //Store user input


			if (userInput.equals("a")) { // if user enters "a" then do the following
				File trip = new File("trip1.txt"); //File object to read the trip1.txt
				ArrayList<City> tripList = readTripFile(trip); //an ArrayList to store the city names in the trip file 
				tripChecker(tripList, cities); //Check if all cities in the trip file are valid
				printTrip(tripList, cities); //Print the result
			}
			else if (userInput.equals("b")) { // if user enters "b" then do the following
				File trip = new File("trip2.txt"); //File object to read the trip2.txt
				ArrayList<City> tripList = readTripFile(trip); //an ArrayList to store the city names in the trip file
				tripChecker(tripList, cities); //Check if all cities in the trip file are valid
				printTrip(tripList, cities); //Print the result
			}
			else if (userInput.equals("c")) {// if user enters "c" then do the following
				File trip = new File("trip3.txt"); //File object to read the trip3.txt
				ArrayList<City> tripList = readTripFile(trip); //an ArrayList to store the city names in the trip file 
				tripChecker(tripList, cities);//Check if all cities in the trip file are valid
				printTrip(tripList, cities); //Print the result
			}
			else if (userInput.equals("d")) {// if user enters "d" then do the following
				File trip = new File("trip4.txt"); //File object to read the trip4.txt
				ArrayList<City> tripList = readTripFile(trip); //an ArrayList to store the city names in the trip file 
				tripChecker(tripList, cities);//Check if all cities in the trip file are valid
				printTrip(tripList, cities); //Print the result
			}
			else if (userInput.equals("e")) {// if user enters "e" then do the following
				File trip = new File("trip5.txt"); //File object to read the trip5.txt
				ArrayList<City> tripList = readTripFile(trip); //an ArrayList to store the city names in the trip file 
				tripChecker(tripList, cities);//Check if all cities in the trip file are valid
				printTrip(tripList, cities); //Print the result
			}
			else if (userInput.equals("q")) {// if user enters "q" then do the following
				System.out.println("Exiting program..."); //Warn user
				System.exit(0); //exit the program

			}
			else { //if user didn't enter a valid letter, exit program
				System.out.println("Incorrect choice, exiting program..."); //Warn user
				System.exit(0); //exit the program
			}
			in.close(); //close the Scanner object

		} catch (FileNotFoundException ex) { //if there is no file then print this
			System.out.println("Input file " +fileName+ " can not be found."); //print file not found
		}
	}
	/**
	 * This method reads the city.txt and stores it into the ArrayList
	 * @param file File object to read txt file
	 * @return ArrayList 
	 * @throws FileNotFoundException it may throw a FileNotFoundException
	 */
	public static ArrayList<City> readCityFile(File file) throws FileNotFoundException {
		ArrayList<City> cities = new ArrayList<City>(); //an array list to store the cities
		Scanner input = new Scanner(file); //Scanner object to read the file
		
		input.nextLine(); //skip the "City;Latitude;Longitude;Country;Population" part of the .txt file
		input.useDelimiter(";"); //values are separated by a semicolon (";")
		
		while (input.hasNextLine()) {  //if a next line exists, then continue

			input.useDelimiter(";"); //values are separated by a semicolon (";")
			String city = input.next(); //read city
			city = city.replaceAll(" \n", ""); //It reads with a newline character,so this line deletes that newline character from String (otherwise the program can't compare Strings like cities)
			city = city.trim(); //Delete all spaces

			int latitude = input.nextInt(); //read latitude
			int longitude = input.nextInt(); //read longitude

			String country = input.next(); //read country
			input.reset(); //Reset the scanner object                                    //
			input.useDelimiter(""); //Use nothing as a delimiter to avoid that semicolon // Program throws an InputMismatchException without this 4 lines because there is
			input.next(); //skip the last semicolon                                      // no semicolon at the end of the each line in the city.txt, this causes to read
			input.reset(); //reset the scanner object to default                         // population + next line's city as an integer and throws an input mismatch exception

			int population = input.nextInt(); //read population

			input.reset(); //reset the Scanner object

			City citiesss = new City(city, latitude, longitude, country, population); //add values into the City object with the constructor
			cities.add(citiesss); //add the City object into the array list

		}
		input.close(); //close Scanner object
		System.out.println("City database is loaded with " +cities.size()+ " cities.\n"); //notify the user
		return cities; //return the cities ArrayList
	}
	/**
	 * This method reads the given trip file and stores into an Arraylist
	 * @param file File object to read .txt file 
	 * @return ArrayList returns city names as an arraylist
	 * @throws FileNotFoundException //may throw a FileNotFoundException
	 */
	public static ArrayList<City> readTripFile(File file) throws FileNotFoundException {
		ArrayList<City> trip = new ArrayList<City>(); //An ArrayList to store the city names
		Scanner input = new Scanner(file); //Scanner object to read the .txt file
		
		while (input.hasNextLine()) { //if a next line exists, then continue
			String city = input.nextLine(); //read city name
			City trips = new City(city); //add the city name into the temporary object
			trip.add(trips); //add the object into the arrayList
		}
		input.close(); //close scanner object
		return trip; //return city names as an ArrayList
	}
	/**
	 * This method generates an final output, displays each trip between cities and their distances.
	 * @param trip trip list to get the trip destinations
	 * @param city city list to get the cities data (longitude, latitude)
	 */
	public static void printTrip(ArrayList<City> trip, ArrayList<City> city) {
		ArrayList <City> currentTwo = new ArrayList<City>(); //This arraylist stores the current two cities and their data
		int finalDistance = 0; //for calculating the total distance
		
		for (int i = 0; i < trip.size() - 1; i++) {
			currentTwo = null; //reset the ArrayList to store new two cities.
			currentTwo = findTwo(trip, city, i); //finds the next two cities to travel
			DecimalFormat d = new DecimalFormat("#0.0000"); //format the latitude and longitude for a good order
			
			String city1 = currentTwo.get(0).getName(); //first city's name
			double latitude1 = currentTwo.get(0).getLatitude() / 10000.0000; //first city's actual latitude (divide it by 10000 to get the actual latitude)
			double longitude1 = currentTwo.get(0).getLongitude() / 10000.0000; //first city's actual longitude (divide it by 10000 to get the actual longitude)

			String city2 = currentTwo.get(1).getName(); //second city's name
			double latitude2 = currentTwo.get(1).getLatitude() / 10000.0000; //second city's actual latitude
			double longitude2 = currentTwo.get(1).getLongitude() / 10000.0000; //second city's actual latitude

			int distance = (int)Math.round(findDistance(longitude1, latitude1, longitude2, latitude2)); //Find the distance between two cities

			finalDistance += distance; //add the current distance to final distance to calculate the total distance

			System.out.printf("%-20s %-1s %9s %-1s %9s %-20s %-1s %9s %-1s %9s %-9s %9s %n", city1, 
					"[x:", d.format(longitude1)+",", "y:",d.format(latitude1)+"] -->  ",city2, 
					"[x:", d.format(longitude2)+",", "y:",d.format(latitude2)+"]", "Distance:", distance + " km" ); //print the cities and their data with an order
		}
		System.out.println("\nTotal Trip Distance: " + finalDistance + " km"); //print the total distance
	}
	/**
	 * This method checks if all cities in the trip file are valid
	 * @param trip city names that is in the trip file to compare
	 * @param city city database to check if cities are valid
	 */
	public static void tripChecker(ArrayList<City> trip, ArrayList<City> city) {
		boolean isFound = false; //a boolean to remember if a city is found

		for (int i = 0; i < trip.size(); i++) {
			if (isFound == false && i > 0) { //if a city is not valid, exit the program
				System.out.println(trip.get(i-1).getName() + " is not found in the city database. Incorrect city name in the trip file."); //warn the user 
				System.out.println("Exiting program..."); //notify the user
				System.exit(0); //exit the program
			}
			isFound = false; //set it to the false every time the loop executes
			for (int j = 0; j < city.size(); j++) {
				
				//System.out.println(j+1 +"th Comparing "+city.get(j).getName()+" with "+trip.get(i).getName()); //show the steps 
				
				if (city.get(j).getName().equals(trip.get(i).getName())) { //if the given city name found, set isFound to true and break the loop
					isFound = true; //set isFound to true
					break; //break the loop
				}
			}
		}
		System.out.println("\nAll cities in the trip file are valid.\n"); //Notify the user
	}
	/**
	 * This method finds the next two cities to print 
	 * @param trip trip ArrayList to get the city names
	 * @param city city ArrayList to get the city data (city name, latitude, longitude)
	 * @param tripIndex specify the index to avoid repetition
	 * @return returns the two cities and their data as an ArrayList
	 */
	public static ArrayList<City> findTwo(ArrayList<City> trip, ArrayList<City> city, int tripIndex ) {

		ArrayList<City> currentTwo = new ArrayList<City>(); //an ArrayList to store the city data

		for (int j = 0; j < city.size(); j++) {
			if (trip.get(tripIndex).getName().equals(city.get(j).getName())) {

				String firstCity = city.get(j).getName(); //name of the first city
				int firstLatitude = city.get(j).getLatitude(); //latitude of the first city
				int firstLongitude = city.get(j).getLongitude(); //longitude of the first city

				City tempObj = new City(firstCity, firstLatitude, firstLongitude); //add data into the temporary object 
				currentTwo.add(tempObj); //add the temporary object into the ArrayList
			}
		}
		if (!(tripIndex == trip.size() - 1)) { //if the counter reaches a minus of the size of the array, don't execute the following
			for (int i = 0; i < city.size(); i++) {
				if (trip.get(tripIndex + 1).getName().equals(city.get(i).getName())) { //find the city name by comparing all cities in the city database

					String secondCity = city.get(i).getName(); //Name of the second city
					int secondLatitude = city.get(i).getLatitude(); //latitude of the second city
					int secondLongitude = city.get(i).getLongitude(); //longitude of the second city

					City temporaryObj = new City(secondCity, secondLatitude, secondLongitude); //add the data into the object with the constructor
					currentTwo.add(temporaryObj); //add the object into the ArrayList
				}
			}
		}
		return currentTwo; //return the two cities as an ArrayList
	}
	/**
	 * This method finds the distance between two cities using the Euclidean Distance formula
	 * @param longx1 longitude of the first city
	 * @param laty1 latitude of the first city
	 * @param longx2 longitude of the second city
	 * @param laty2 latitude of the second city
	 * @return returns the distance between two cities
	 */
	public static double findDistance(double x1, double y1, double x2, double y2) {
		//x1 = longitude of the first city, y1 = latitude of the first city, x2 = longitude of the second city, y2 = latitude of the second city

		double x2x1D = (x2 - x1) * 85; //subtract x1 from x2 and multiply it by 85 because the distance between two consecutive longitudes is 85km
		double y2y1D = (y2 - y1) * 111; //subtract y1 from y2 and multiply it by 111 because the distance between two consecutive latitudes is 111km
		double x2x1 = Math.pow(x2x1D, 2); //power of two of x2 - x1
		double y2y1 = Math.pow(y2y1D, 2); //power of two of y2 - y1
		
		double distance = Math.sqrt(x2x1 + y2y1); //square root of the x2x1 + y2y1
		return distance; //return the distance in kilometers
	}

}

