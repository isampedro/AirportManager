package ar.edu.itba.pod.rmi;
import ar.edu.itba.pod.rmi.AirportExceptions.*;

public interface LaneRequesterService {
    void flightLane(int flightId, int destinyAirport, String airline, Categories minimumCategory) throws NoAvailableLaneException;
}
