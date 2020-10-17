package Airport;


import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;

public class Flight {
	private String airline, port, city, weekday;
	private int terminal;
	private String flightId;
	private String comingFrom;
	private String goingTo;
	private LocalTime time;
	private LocalDate date;


	public Flight(String airline, String  goingTo, String comingFrom, String port, String city,String weekday, LocalTime time, LocalDate date, int terminal,
			String flightId) {
		this.port=port;
		this.city=city;
		this.weekday=weekday;
		this.airline = airline;
		this.terminal = terminal;
		this.flightId = flightId;
		this.comingFrom = comingFrom;
		this.goingTo = goingTo;
		this.time = time;
		this.date = date;

	}
	public Flight(Scanner scan) {
		String line="";
		String[] info;
		for( int i=0;i<10;i++) {
			line+=scan.next();
		}
		
		info=line.split(",");
		 this.airline=info[0];
		 this.comingFrom=info[1];
		 this.goingTo=info[2];
		 this.time=LocalTime.parse(info[3]);
		 this.date=LocalDate.parse(info[4]);
		 this.terminal=Integer.parseInt(info[5]);
		 this.flightId=info[6];
		 this.port=info[7];
		 this.city=info[8];
		 this.weekday=info[9];
		
	}
	public Flight(Flight flight) {
		this.port=flight.getPort();
		this.city=flight.getCity();
		this.weekday=flight.getWeekday();
		this.terminal = flight.getDepartureTerminal();
		this.flightId = flight.getFlightId();
		this.comingFrom = flight.getArrivalName();
		this.goingTo = flight.getDepartureName();
		this.time = flight.getTime();
		this.date = flight.getDate();
		this.airline = flight.getAirline();
	}
	public String getPort() {
		return this.port;
	}
	 public String getCity() {
		 return this.city;
	 }
	 public String getWeekday() {
		 return this.weekday;
	 }

	public String getFlightId() {
		return flightId;
	}

	public String getArrivalName() {
		return goingTo;
	}

	public String getDepartureName() {
		return comingFrom;
	}

	public LocalTime getTime() {
		return time;
	}

	public LocalDate getDate() {
		return date;
	}

	public int getDepartureTerminal() {
		return this.terminal;
	}

	public void setDepartureTerminal(int departureTerminal) {
		this.terminal = departureTerminal;
	}

	public String getAirline() {
		return this.airline;
	}

	public boolean equals(Object other) {
		if (!(other instanceof Flight))
			return false;
		Flight temp = (Flight) other;
		return flightId == temp.getFlightId() && airline.equals(temp.getAirline());
	}
	
	public void save(PrintWriter pw) {
		pw.print(this.airline+", ");
		pw.print(this.comingFrom +", ");
		pw.print(this.goingTo+", ");
		pw.print(this.time.toString()+", ");
		pw.print(this.date.toString()+", ");
		pw.print(this.terminal+", ");
		pw.print(this.flightId+", ");
		pw.print(this.port+", ");
		pw.print(this.city+", ");
		pw.print(this.weekday+",");
		pw.println();

	}

	@Override
	public String toString() {
		return "Flight: " + flightId + ", Airline:" + this.airline + ",  Departure: " + goingTo + ", Arrival: "
				+ comingFrom+", port: "+port+", city:"+city + " , Departures on: " + date.getDayOfMonth() + "/" + date.getMonthValue() + "/"
				+ date.getYear() + ", Arrival Time: "  + time +", weekday: "+weekday+ "\n";
	}
}

