package FligthCrue;

import Airport.*;
import AirportExceptions.*;
import AirportOps.Admin;

public class LaneRequester{
    public static void flightLane(int flightId,
                                  int destinyAirport,
                                  String airline,
                                  Categories minimumCategory) throws NoAvailableLaneException{

        Flight flight = new Flight(flightId, minimumCategory, airline);
        Admin.addFlightToLane(flight);
    }
}
