package control;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import entity.Flight;
import entity.FlightStatus;
import entity.Seat;
import entity.SeatClass;

public class ImportControl {
	
	private static ImportControl instance;
	public static ImportControl getInstance() 
	{
		if (instance == null)
			instance = new ImportControl();
		return instance;
	}
	
	
	public ArrayList<Flight> importFlightsFromJson(){
		
		ArrayList<Flight> ourJsonFlightResult = new ArrayList<Flight>();
		
		
		
		try 
		{
			JSONObject obj = (JSONObject) new JSONParser().parse(new FileReader("json/flights.json"));
			JSONArray jo = (JSONArray) obj.get("flights") ; 
			Iterator<JSONObject> iterator = jo.iterator();
			while(iterator.hasNext())
			{
				JSONObject item = iterator.next();
				
				// Flight
				String flightNumber = (String) item.get("flightNumber");

				String depTime = (String) item.get("departureDate");
			    Timestamp departureDate = getFullTime(depTime);

				String departureAirportId = (String) item.get("departureAirportId");
				
				String landTime = (String) item.get("landingDate");
			    Timestamp landingDate = getFullTime(landTime);

				
				String landingAirportId = (String) item.get("landingAirportId");
				String flightStatus = (String) item.get("flightStatus");
				String tailNumber = (String) item.get("tailNumber");

				// Flight Status
				FlightStatus status;
				if (flightStatus.toLowerCase().equals(FlightStatus.Cancelled.toString().toLowerCase()) )
					status = FlightStatus.Cancelled;
				else if (flightStatus.toLowerCase().equals(FlightStatus.Delayed.toString().toLowerCase()))
					status = FlightStatus.Delayed;
				else
					status = FlightStatus.OnTime;

				Flight flight = new Flight(flightNumber, departureDate.toLocalDateTime(), departureAirportId, landingDate.toLocalDateTime(), landingAirportId, status, tailNumber);
				ourJsonFlightResult.add(flight);
//				String a = (String) item.get("THEATERID");	
//				int theaterID = Integer.parseInt(a);;
//				String theaterName = (String)item.get("THEATERNAME");
//				String c = (String)item.get("MAXCAPACITY");	
//				int maxCapacity = Integer.parseInt(c);; 			
//				String manager = (String)item.get("MANAGERNAME");
//				String city = (String)item.get("CITYNAME");
//				String d = (String)item.get("maxInCapsule");
//				int maxInCapsule = Integer.parseInt(d);
//				
//				
//				//Show
//				String e = (String)item.get("SHOWID");
//				int showId = Integer.parseInt(e);;
//				String showName = (String)item.get("SHOWNAME");
//				String f = (String)item.get("SHOWLENGTH");
//				int showLeangth = Integer.parseInt(f);
//				String g = (String)item.get("HASBREAK");
//				Boolean hasBreak = Boolean.parseBoolean(g);
//				
//				
//				
//				
//				//ShowInTheater
//				String m =  (String)item.get("DATEOFSHOW");
//				java.sql.Date dateOfShow = saveTheDate(m);
//				String ho = (String) item.get("STARTTIME"); 
//				/*SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
//				long ms = sdf.parse(ho).getTime();
//				Time startHour = new Time(ms);*/
//				java.sql.Time startHour = saveTheTime(ho);
//				//We in our System, we have only natural numbers as prices
//				String h = (String)item.get("PRICE");
//				double price = Double.parseDouble(h);	
//				int basicTicketPrice = (int) price;
//				String ud = (String) item.get("UPDATESTATUS");
//				java.sql.Date updateDate =saveTheDate(ud);
//				String status = (String)item.get("STATUS");
//				
//				
//				Show s = new Show(showId, showName, showLeangth, hasBreak);
//				Theater tr = new Theater(theaterID, theaterName, maxCapacity, manager, city, maxInCapsule);
//				
//				ShowInTheater shtr = new ShowInTheater(tr, s, dateOfShow, startHour, basicTicketPrice, updateDate, status);
//				ourJsonResult.add(shtr);		
			}
		
			
		} catch (IOException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//System.out.println(e.getMessage());
		}

		
		return ourJsonFlightResult;
	}
	
//	private Timestamp getOLDFullTime(String time) {
//		int year = Integer.parseInt(time.substring(6, 10));
//		int month = Integer.parseInt(time.substring(3, 5));
//		int day = Integer.parseInt(time.substring(0, 2));
//		int hour = Integer.parseInt(time.substring(11, 13));
//		int minute = Integer.parseInt(time.substring(17, 19));
//		
//		return new Timestamp(year, month, day, hour, minute, 0, 0);
//	}
	
	private Timestamp getFullTime(String time) {
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
	    java.util.Date parsedDate = null;
		try {
			parsedDate = dateFormat.parse(time);
		} catch (java.text.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    return new java.sql.Timestamp(parsedDate.getTime());
	}
	
	
	// Seats
	public ArrayList<Seat> importSeatsFromJson(){
		
		ArrayList<Seat> ourJsonSeatResult = new ArrayList<Seat>();
		try 
		{
			JSONObject obj = (JSONObject) new JSONParser().parse(new FileReader("json/flights.json"));
			JSONArray jo = (JSONArray) obj.get("seats") ; 
			Iterator<JSONObject> iterator = jo.iterator();
			while(iterator.hasNext())
			{
				JSONObject item = iterator.next();
				
				int seatRow =  Integer.parseInt((String) item.get("seatRow"));
				int seatNumber = Integer.parseInt((String) item.get("seatNumber"));
				
				String seatClassSTR = (String) item.get("seatClass");
				SeatClass seatClass;
				if (seatClassSTR.toLowerCase().equals(SeatClass.Business.toString().toLowerCase()) )
					seatClass = SeatClass.Business;
				else if (seatClassSTR.toLowerCase().equals(SeatClass.Economy.toString().toLowerCase()))
					seatClass = SeatClass.Economy;
				else
					seatClass = SeatClass.FirstClass;
				
				String tailNumber = (String) item.get("tailNumber");
				
				Seat seat = new Seat(seatRow, seatNumber, seatClass, tailNumber);
				ourJsonSeatResult.add(seat);
			}
		
			
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}

		
		return ourJsonSeatResult;
	}

}
