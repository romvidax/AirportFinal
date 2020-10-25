package Airport;


import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Array;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

import javafx.util.converter.LocalDateStringConverter;

import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Main {

	public static boolean isValidFormat(String format, String value, Locale locale) {
		LocalDateTime ldt = null;
		DateTimeFormatter fomatter = DateTimeFormatter.ofPattern(format, locale);

		try {
			ldt = LocalDateTime.parse(value, fomatter);
			String result = ldt.format(fomatter);
			return result.equals(value);
		} catch (DateTimeParseException e) {
			try {
				LocalDate ld = LocalDate.parse(value, fomatter);
				String result = ld.format(fomatter);
				return result.equals(value);
			} catch (DateTimeParseException exp) {
				try {
					LocalTime lt = LocalTime.parse(value);
					return true;
				} catch (Exception error) {
					return false;
				}
			}
		}
	}

	public static boolean isValidFlightId(String id) {
		if (id.length() != 5)
			return false;
		//		String temp = id.substring(0, 1);  //we can use this form of writing
		//		temp.matches("A-Z");							//same can be done for the ints
		if (id.charAt(0) < 'A' || id.charAt(0) > 'Z')
			return false;
		if (id.charAt(1) < 'A' || id.charAt(1) > 'Z')
			return false;
		String idNums = id.substring(2);
		try {
			int nums = Integer.parseInt(idNums);
		} catch (Exception error) {
			return false;
		}
		return true;
	}

	public static boolean isValidDay(String day) {
		if((day.equalsIgnoreCase("sunday")) || (day.equalsIgnoreCase("monday")) ||(day.equalsIgnoreCase("tuesday"))|| (day.equalsIgnoreCase("wednesday"))|| (day.equalsIgnoreCase("thursday"))||(day.equalsIgnoreCase("friday"))||(day.equalsIgnoreCase("saturday")))
			return true;
		else
			return false;

	}
	public static void addFlight(Airport airport) {
		Scanner scan = new Scanner(System.in);
		String name = "", goingTo = "", comingFrom = "", flightId = "", port="", city="", weekday="";
		LocalTime timee = null, arrivalTime = null;
		LocalDate datee = null, arrivalDate = null;
		int terminal = 0;
		// Flight name
		System.out.println("Please enter the airline's name: ");
		name = scan.next();
		// Flight ID
		boolean validId = false;
		while (validId == false) {
			System.out.println("Enter the filght's ID: (ex: LY978)");
			flightId = scan.next();
			validId = isValidFlightId(flightId);
			if (validId == false)
				System.out.println("Wrong ID format. must have two uppercase letters followed by 3 digits");
		}

		// Flight's terminal
		boolean validTerminal = false;
		while (validTerminal == false) {
			System.out.println("What terminal is this flight on? ");
			terminal = scan.nextInt();
			if (terminal <= 0)
				System.out.println("has to be bigger than 0.");
			else
				validTerminal = true;
		}

		// Check that one of the destinations is Israel.
		boolean validDest = false;
		while (validDest == false) {
			// Departure name
			System.out.println("This flight departs from: ");
			goingTo= scan.next();
			scan.nextLine();

			// Arrival name compared to the Departure name
			boolean validArrive = false;
			while (validArrive == false) {
				System.out.println("This flight arrives to: ");
				comingFrom= scan.next();
				if (goingTo.equals(comingFrom))
					System.out.println("Cannot arrive to a country you depart from. Try again");
				else
					validArrive = true;
			}
			if (!goingTo.equals("Israel") && !comingFrom.equals("Israel"))
				System.out.println("one of the destinations has to be Israel.");
			else {
				goingTo=goingTo.replace(' ', '-');
				comingFrom=comingFrom.replace(' ', '-');

				validDest = true;
			}

		}
		scan.nextLine();

		//Port.
		System.out.println("Eneter the port that the flight departures:");
		port=scan.next();
		port=port.replace(' ', '-');
		scan.nextLine();

		//City.
		System.out.println("Eneter the city of the port:");
		city=scan.next();
		city=city.replace(' ', '-');
		scan.nextLine();

		//Weekday.
		boolean validDay=false;
		if(goingTo.equals("Israel"))
			while(validDay==false) {
				System.out.println("Enter the day the flight arrives:(must be a valid weekday)");
				weekday=scan.next();
				validDay=isValidDay(weekday);
			}

		else
			while(validDay==false) {
				System.out.println("Enter the day the flight departures:(must be a valid weekday)");
				weekday=scan.next();
				validDay=isValidDay(weekday);
			}




		// Departure date & Arrival date. compared and check.
		System.out.println("NOTE:The Arrival cannot be before the Departure");
		String date = "";
		boolean validFormat = false;
		while (validFormat == false) {
			System.out.println("Eneter the date of departure: (DD/MM/YYYY)");
			date = scan.next();
			validFormat = isValidFormat("dd/MM/yyyy", date, Locale.ENGLISH);
			if (validFormat == false)
				System.out.println("Wrong date. Please try again.");
		}
		String[] splitDate = date.split("/");
		datee = LocalDate.of(Integer.parseInt(splitDate[2]), Integer.parseInt(splitDate[1]),
				Integer.parseInt(splitDate[0]));
		scan.nextLine();

		String time = "";
		validFormat = false;
		while (validFormat == false) {
			System.out.println("Enter the time of arrival: (HH:MM)");
			time = scan.next();
			validFormat = isValidFormat("hh:mm", time, Locale.ENGLISH);
			if (validFormat == false)
				System.out.println("Wrong time. Please try again.");
		}
		String[] splitTime = time.split(":");
		timee = LocalTime.of(Integer.parseInt(splitTime[0]), Integer.parseInt(splitTime[1]));

		airport.addFlight(new Flight(name, comingFrom, goingTo ,port, city, weekday, timee, datee, terminal, flightId));

	}

	public static ArrayList<Flight> searchByDate(Airport airport, int ArrivOrDepart) {
		// if ArrivOrDepart=1 ----> Search on Arrivals
		// if ArrivOrDepart=0 ----> Search on Departures
		Scanner scan = new Scanner(System.in);
		LocalDate highDate = null;
		LocalDate lowDate = null;

		// High date & low date. Compared and check.
		System.out.println("NOTE:The HighDate cannot be before the LowDate");
		boolean compareHighToLow = false;
		while (compareHighToLow == false) {
			String date = "";
			boolean validFormat = false;
			while (validFormat == false) {
				System.out.println("Eneter the lower date of the search: (DD/MM/YYYY)");
				date = scan.next();
				validFormat = isValidFormat("dd/MM/yyyy", date, Locale.ENGLISH);
				if (validFormat == false)
					System.out.println("Wrong date. Please try again.");
			}
			String[] splitDate = date.split("/");
			lowDate = LocalDate.of(Integer.parseInt(splitDate[2]), Integer.parseInt(splitDate[1]),
					Integer.parseInt(splitDate[0]));
			scan.nextLine();

			validFormat = false;
			while (validFormat == false) {
				System.out.println("Eneter the higher date of the search: (DD/MM/YYYY)");
				date = scan.next();
				validFormat = isValidFormat("dd/MM/yyyy", date, Locale.ENGLISH);
				if (validFormat == false)
					System.out.println("Wrong date. Please try again.");
			}
			splitDate = date.split("/");
			highDate = LocalDate.of(Integer.parseInt(splitDate[2]), Integer.parseInt(splitDate[1]),
					Integer.parseInt(splitDate[0]));

			if (highDate.compareTo(lowDate) < 0)
				System.out.println("The higher date cannot be before the lower date! Try again.");
			else
				compareHighToLow = true;

		}
		if (ArrivOrDepart == 1)
			return airport.searchArrivalsByDate(airport.getArrival().getFlights(), lowDate, highDate);
		else
			return airport.searchArrivalsByDate(airport.getDeparture().getFlights(), lowDate, highDate);

	}


	public static ArrayList<Flight> searchFlights(Airport airport, int searchOp){
		// if searchOp = 0----> search flight FROM a CITY
		// if searchOp = 1----> search flight TO a CITY
		// if searchOp = 2----> search flight FROM a COUNTRY
		// if searchOp = 3----> search flight TO a COUNTY
		// if searchOp = 4----> search flight FROM a PORT
		// if searchOp = 5----> search flight TO a PORT

		String searchKey;
		ArrayList<Flight> results=new ArrayList<Flight>();
		Scanner scan=new Scanner(System.in);
		//CITY.
		if(searchOp==1) {
			System.out.println("Please enter the city you would like to search flights to: ");
			searchKey=scan.next();
			for(Flight fly: airport.getDeparture().getFlights()) {
				if(fly!=null)
					if(fly.getCity().equalsIgnoreCase(searchKey))
						results.add(fly);
			}
		}
		else if(searchOp==0) {
			System.out.println("Please enter the city you would like to search flights from: ");
			searchKey=scan.next();
			for(Flight fly: airport.getArrival().getFlights()) {
				if(fly!=null)
					if(fly.getCity().equalsIgnoreCase(searchKey))
						results.add(fly);
			}
		}

		//COUNTRY
		if(searchOp==3) {
			System.out.println("Please enter the country you would like to search flights to: ");
			searchKey=scan.next();
			for(Flight fly: airport.getDeparture().getFlights()) {
				if(fly!=null)
					if(fly.getDepartureName().equalsIgnoreCase(searchKey))
						results.add(fly);
			}
		}
		else if(searchOp==2) {
			System.out.println("Please enter the country you would like to search flights from: ");
			searchKey=scan.next();
			for(Flight fly: airport.getArrival().getFlights()) {
				if(fly!=null)
					if(fly.getArrivalName().equalsIgnoreCase(searchKey))
						results.add(fly);
			}
		}

		if(searchOp==5) {
			System.out.println("Please enter the port you would like to search flights to: ");
			searchKey=scan.next();
			for(Flight fly: airport.getDeparture().getFlights()) {
				if(fly!=null)
					if(fly.getPort().equalsIgnoreCase(searchKey))
						results.add(fly);
			}
		}
		else if(searchOp==4) {
			System.out.println("Please enter the port you would like to search flights from: ");
			searchKey=scan.next();
			for(Flight fly: airport.getArrival().getFlights()) {
				if(fly!=null)
					if(fly.getPort().equalsIgnoreCase(searchKey))
						results.add(fly);
			}
		}


		return results;

	}



	public static void main(String[] args) throws FileNotFoundException {

		File airportFile=new File("C:\\Users\\romvi\\git\\AirportFinal\\NewNatbag2020\\Natbag2020");
		Scanner load=new Scanner(airportFile);
		Airport airport=new Airport(load);
		Scanner scan = new Scanner(System.in);

		if(args.length>0) {
			try {
				boolean isHtml = args[0].equalsIgnoreCase("html");
				boolean isDepartures = args[1].equalsIgnoreCase("Departures");
				LocalDate startDate = LocalDate.of(Integer.parseInt(args[8]), Integer.parseInt(args[7]),
						Integer.parseInt(args[6]));
				LocalDate endDate = LocalDate.of(Integer.parseInt(args[11]), Integer.parseInt(args[10]),
						Integer.parseInt(args[9]));
				if (isHtml) {
					airport.setIsDepartures(isDepartures);
					if (isDepartures)
						System.out.println("Departures - ");
					else
						System.out.println("Arrivals - ");
					System.out.println("Fights with " + args[2]);
					System.out.println(" To " + args[3] + "-" +args[4]+ "-" +args[5]+ "<br>");
					airport.setCompany(args[2]);
					airport.setCountry(args[3]);
					airport.setCity(args[4]);
					airport.setPort(args[5]);

					System.out.println("Between: "  + startDate.getDayOfMonth() + "/" + startDate.getMonthValue() + "/"
							+ startDate.getYear() + " - " + endDate.getDayOfMonth() + "/" + endDate.getMonthValue() + "/"
							+ endDate.getYear() + "<br>");

					System.out.println("On: ");
					boolean[] weekdays= airport.setWeekday(Boolean.parseBoolean(args[12]), Boolean.parseBoolean(args[13]),Boolean.parseBoolean(args[14]),
							Boolean.parseBoolean(args[15]), Boolean.parseBoolean(args[16]), Boolean.parseBoolean(args[17]), Boolean.parseBoolean(args[18]));
					if(weekdays[0])
						System.out.print("Sunday, ");
					if(weekdays[1])
						System.out.print("monday, ");
					if(weekdays[2])
						System.out.print("Tuesday, ");
					if(weekdays[3])
						System.out.print("Wednesday, ");
					if(weekdays[4])
						System.out.print("Thursday, ");
					if(weekdays[5])
						System.out.print("Friday, ");
					if(weekdays[6])
						System.out.print("Saturday, ");

					System.out.println("<br><br>");
					int index = 1;
					ArrayList<Flight> results=airport.htmlSearchResults(startDate, endDate);
					if(results.size()==0)
						System.out.println("Could not find matching flights!");
					else {
						for (Flight f : results) {
							System.out.println(index + ") " + f + "<br>");
							index++;
						}
					}
				}
			}
			catch (Exception e) {
			}
		}


		else {

			//				airport.setIsDepartures(true);
			//				airport.setCompany("elal");
			//				airport.setCountry("france");
			//				airport.setCity("paris");
			//				airport.setPort("CDG");
			//				LocalDate startDate=LocalDate.of(2020, 6, 4);		
			//				LocalDate endDate=LocalDate.of(2020, 7, 31);
			//				airport.setWeekday(true, true, true, true, true, true, true);
			//				
			//				ArrayList<Flight> results2=airport.htmlSearchResults(startDate, endDate);
			//				int index=1;
			//				for (Flight f : results2) {
			//					System.out.println(index + ") " + f + "<br>");
			//					index++;
			//				}





			StringBuffer menu = new StringBuffer();
			menu.append("\n1--- Add a new Flight \n");
			menu.append("2--- Show departures\n");
			menu.append("3--- Show arrivals\n");
			menu.append("4--- Show all Flights\n");
			menu.append("5--- Search Arrivals within dates\n");
			menu.append("6--- Search Departures within dates\n");
			menu.append("7--- Search flights to a city\n");
			menu.append("8--- Search flights from a city\n");
			menu.append("9--- Search flights to a country\n");
			menu.append("10--- Search flights from a country\n");
			menu.append("11--- Search flights from a port\n");
			menu.append("12--- Search flights to a port\n");

			System.out.println("Welcome to Ben Gurion Airport!");
			int select = 0;
			ArrayList<Flight> results;
			while (select != -1) {
				System.out.println(menu.toString());
				select = scan.nextInt();
				switch (select) {
				case 1:
					addFlight(airport);
					airport.save("Natbag2020");
					break;
				case 2:
					System.out.println("\n---Departures---\n" + airport.getDeparture().toString());
					break;
				case 3:
					System.out.println("\n---Arrivals---\n" + airport.getArrival().toString());

					break;
				case 4:
					System.out.println(airport.toString());
					break;
				case 5:
					results = searchByDate(airport, 1);
					for (Flight flight : results)
						System.out.println(flight.toString());
					break;
				case 6:
					results = searchByDate(airport, 0);
					for (Flight flight : results)
						System.out.println(flight.toString());
					break;
				case 7:
					results = searchFlights(airport, 0);
					if(results.size()==0)
						System.out.println("Could not find results.");
					else
						for (Flight flight : results)
							System.out.println(flight.toString());
					break;
				case 8:
					results = searchFlights(airport, 1);
					if(results.size()==0)
						System.out.println("Could not find results.");
					else
						for (Flight flight : results)
							System.out.println(flight.toString());
					break;

				case 9:
					results = searchFlights(airport, 2);
					if(results.size()==0)
						System.out.println("Could not find results.");
					else
						for (Flight flight : results)
							System.out.println(flight.toString());
					break;

				case 10:
					results = searchFlights(airport, 3);
					if(results.size()==0)
						System.out.println("Could not find results.");
					else
						for (Flight flight : results)
							System.out.println(flight.toString());
					break;

				case 11:
					results = searchFlights(airport, 4);
					if(results.size()==0)
						System.out.println("Could not find results.");
					else
						for (Flight flight : results)
							System.out.println(flight.toString());
					break;
				case 12:
					results = searchFlights(airport, 5);
					if(results.size()==0)
						System.out.println("Could not find results.");
					else
						for (Flight flight : results)
							System.out.println(flight.toString());
					break;

				default:
					break;
				}
			}
		}
		System.out.println("Goodbye");
	}
}

