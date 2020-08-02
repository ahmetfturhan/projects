/**
 * This class is for the City object that stores the city data (city name, latitude, longitude, country, population)
 * @author Ahmet Faruk Turhan ID:*********
 * @since 22.03.2020
 */
public class City {

	//data fields
	private String name; //name of the city
	private int latitude; //latitude of the city
	private int longitude; //longitude of the city
	private String country; //country 
	private int population;  //population of the city
	
	/**
	 * this is the default constructor
	 */
	City() {
	}
	
	/**
	 * A constructor for readCityFile method, stores: city name, latitude, longitude, country name, population of the city
	 * @param name city name
	 * @param latitude latitude of the city
	 * @param longitude longitude of the city
	 * @param country country name
	 * @param population population of the city
	 */
	City(String name, int latitude, int longitude, String country, int population ) {
		this.name = name; //name of the city
		this.latitude = latitude; //latitude of the city
		this.longitude = longitude; //longitude of the city
		this.country = country; //country
		this.population = population; //population of the city
	}
	
	/**
	 * A constructor for readTripFile method, only stores city name
	 * @param name city name
	 */
	City(String name) {
		this.name = name; //city name
	}
	
	/**
	 * Constructor for findTwo method, only stores city name, longitude and latitude
	 * @param name city name
	 * @param latitude city's latitude
	 * @param longitude city's longitude
	 */
	City(String name, int latitude, int longitude) {
		this.name = name;
		this.latitude = latitude;
		this.longitude = longitude;
	}
	//setter-getter methods
	/**
	 * Set city name
	 * @param name city name
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * get city name
	 * @return city name
	 */
	public String getName() {
		return name;
	}
	/**
	 * set latitude of the city
	 * @param latitude latitude of the city
	 */
	public void setLatitude(int latitude) {
		this.latitude = latitude;
	}
	/**
	 * set longitude of the city
	 * @param longitude longitude of the city
	 */
	public void setLongitude(int longitude) {
		this.longitude = longitude;
	}
	/**
	 * Set country of the city
	 * @param country country name
	 */
	public void setCountry(String country) {
		this.country = country;
	}
	/**
	 * Set population of the city
	 * @param population population of the city
	 */
	public void setPopulation(int population) {
		this.population = population;
	}
	/**
	 * get latitude of the city
	 * @return latitude of the city
	 */
	public int getLatitude() {
		return latitude;
	}
	/**
	 * get longitude of the city
	 * @return longitude of the city
	 */
	public int getLongitude() {
		return longitude;
	}
	/**
	 * get country name of the city
	 * @return country name
	 */
	public String getCountry() {
		return country;
	}
	/**
	 * get population of the city
	 * @return population of the city
	 */
	public int getPopulation() {
		return population;
	}

}
