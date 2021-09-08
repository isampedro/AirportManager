package ar.edu.ar.pod.rmi;
import ar.edu.ar.pod.rmi.AirportExceptions.*;

public interface LaneRequesterService {
    public void flightLane(int flightId, int destinyAirport, String airline, Categories minimumCategory) throws NoAvailableLaneException;
}
