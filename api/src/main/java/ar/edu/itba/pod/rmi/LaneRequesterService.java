package ar.edu.itba.pod.rmi;
import ar.edu.itba.pod.rmi.AirportExceptions.*;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface LaneRequesterService extends Remote {
    void addFlightToLane(int flightId, int destinyAirport, String airline, Categories minimumCategory) throws NoAvailableLaneException, RemoteException;
}
