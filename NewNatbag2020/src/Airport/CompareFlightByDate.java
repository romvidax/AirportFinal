package Airport;

import java.util.Comparator;

public class CompareFlightByDate implements Comparator<Flight> {
	public int compare(Flight flight1, Flight flight2) {
		if(flight1==null||flight2==null)
			return 1;
		if(flight1.getDate().compareTo(flight2.getDate())==0) 
			return(flight1.getTime().compareTo(flight2.getTime()));
		return (flight1.getDate().compareTo(flight2.getDate()));
		
	}
}
