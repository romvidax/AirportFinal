package Airport;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import javax.naming.directory.SearchControls;

public class Airport {
	private Departures myDepartures;
	private Arrivals myArrivals;
	private String airportName;
	String company="", city="", port="", country=""; //variables for HTML search.
	boolean isDepartures=false;
	boolean[] weekdays=new boolean[7];


	public Airport(String name ) {
		this.airportName = name;
		this.myDepartures = new Departures(10);
		this.myArrivals = new Arrivals(10);
	}
	public Airport(Scanner scan) {
		this.myDepartures = new Departures(10);
		this.myArrivals = new Arrivals(10);

		this.airportName=scan.nextLine();
		int numOfFlights=scan.nextInt();
		for(int i=0;i<numOfFlights;i++) {
			addFlight(new Flight(scan));
		}
	}

	public void save(String fileName) throws FileNotFoundException {
		File save=new File(fileName);
		PrintWriter pw=new PrintWriter(save);
		int numOfFlights=myDepartures.getNumOfFlights() + myArrivals.getNumOfFlights();
		pw.println(this.airportName);
		pw.println(numOfFlights);
		myDepartures.save(pw);
		myArrivals.save(pw);

		pw.close();
	}
	public Departures getDeparture() {
		return this.myDepartures;
	}
	public Arrivals getArrival() {
		return this.myArrivals;
	}

	public void addFlight(Flight flight) {
		if(flight.getDepartureName().equals("Israel"))
			getDeparture().addFlight(flight);
		else
			getArrival().addFlight(flight);
	}
	public ArrayList<Flight> searchArrivalsByDate(Flight[] arrivals,LocalDate lowDate, LocalDate highDate){
		ArrayList<Flight> results=new ArrayList<Flight>();
		for(int i=0;i<arrivals.length;i++)
			if(arrivals[i]!=null)
				if((arrivals[i].getDate().compareTo(lowDate)>=0) && (arrivals[i].getDate().compareTo(highDate)<=0))
					results.add(arrivals[i]);
		return results;
	}

	public ArrayList<Flight> searchDeparturesByDate(Flight[] flightArray,LocalDate lowDate, LocalDate highDate){
		ArrayList<Flight> results=new ArrayList<Flight>();
		for(int i=0;i<flightArray.length;i++)
			if((flightArray[i].getDate().compareTo(lowDate)>=0) && (flightArray[i].getDate().compareTo(highDate)<=0))
				results.add(flightArray[i]);
		return results;
	}

	private ArrayList<Flight> searchFlightsByCountry(ArrayList<Flight> flights, String country){
		ArrayList<Flight> results=new ArrayList<Flight>();
		for(Flight fly:flights) 
			if(fly.getDepartureName().equals(country))
				results.add(fly);

		return results;
	}

	private ArrayList<Flight> searchFlightsByCity(ArrayList<Flight> flights,String city){
		ArrayList<Flight> results=new ArrayList<Flight>();
		for(Flight fly: flights)
			if(fly.getDepartureName().equals(city))
				results.add(fly);
		return results;
	}

	private ArrayList<Flight> searchFlightsByPort(ArrayList<Flight> flights,String port){
		ArrayList<Flight> results=new ArrayList<Flight>();
		for(Flight fly: flights) 
			if(fly.getDepartureName().equals(port))
				results.add(fly);
		return results;
	}

	private ArrayList<Flight> searchFlightsByCompany(ArrayList<Flight> flights,String company){
		ArrayList<Flight> results=new ArrayList<Flight>();
		for(Flight fly:flights) 
			if(fly.getDepartureName().equals(company))
				results.add(fly);
		return results;
	}

	private ArrayList<Flight> searchFlightsByWeekdays(ArrayList<Flight> flights, boolean[] weekdays){
		ArrayList<Flight> results=new ArrayList<Flight>();
		int flightDay;
		for(Flight fly:flights) {
			flightDay=fly.convertWeekdayToInt();
				if(weekdays[flightDay])
					results.add(fly);
		}
		return results;
	}

	public void setIsDepartures(boolean isDepart) {
		this.isDepartures=isDepart;
	}

	public void setCompany(String company) {
		this.company=company;

	}
	public void setCountry(String country) {
		this.country=country;
	}
	public void setCity(String city) {
		this.city=city;
	}
	public void setPort(String port) {
		this.airportName=port;
	}
	public boolean[] setWeekday(boolean sunday, boolean monday, boolean tuesday, boolean wednesday, boolean thursday, boolean friday, boolean saturday) {
		boolean[] weekdays=new boolean[7];
		weekdays[0]=sunday;
		weekdays[1]=monday;
		weekdays[2]=tuesday;
		weekdays[3]=wednesday;
		weekdays[4]=thursday;
		weekdays[5]=friday;
		weekdays[6]=saturday;
		this.weekdays=weekdays;
	
		return weekdays;
	}
	


	public ArrayList<Flight> htmlSearchResults(LocalDate lowDate, LocalDate highDate){
		ArrayList<Flight> results;
		if(isDepartures)
			results=searchDeparturesByDate(getDeparture().getFlights(), lowDate, highDate);
		else
			results=searchArrivalsByDate(getArrival().getFlights(), lowDate, highDate);

		results=searchFlightsByCompany(results,company);
		results=searchFlightsByCity(results,city);
		results=searchFlightsByCountry(results,country);
		results=searchFlightsByPort(results,airportName);
		results=searchFlightsByWeekdays(results, weekdays);

		return results;
	}



	@Override
	public String toString() {
		return "Airport:\n" + airportName+ "\n----Departures----\n" + myDepartures.toString() + "\n----Arrivals----\n" + myArrivals.toString();
	}
}
