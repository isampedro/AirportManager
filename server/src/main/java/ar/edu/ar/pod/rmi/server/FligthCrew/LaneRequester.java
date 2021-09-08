package ar.edu.ar.pod.rmi.server.FligthCrew;

import ar.edu.ar.pod.rmi.Categories;
import ar.edu.ar.pod.rmi.Flight;
import ar.edu.ar.pod.rmi.AirportExceptions.*;
import ar.edu.ar.pod.rmi.server.AirportOps.Admin;

public class LaneRequester{
    public static void flightLane(int flightId,
                                  int destinyAirport,
                                  String airline,
                                  Categories minimumCategory) throws NoAvailableLaneException{

        Flight flight = new Flight(flightId, minimumCategory, airline);
        Admin.addFlightToLane(flight);
    }
}
