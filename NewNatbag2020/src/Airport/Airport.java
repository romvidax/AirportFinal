package Airport;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Airport {
	private Departures myDepartures;
	private Arrivals myArrivals;
	private String airportName;


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


	@Override
	public String toString() {
		return "Airport:\n" + airportName+ "\n----Departures----\n" + myDepartures.toString() + "\n----Arrivals----\n" + myArrivals.toString();
	}
}
