package ar.edu.itba.pod.rmi.Services;
import ar.edu.itba.pod.rmi.AirportExceptions.*;
import ar.edu.itba.pod.rmi.Categories;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface LaneRequesterService extends Remote {
    void addFlightToLane(int flightId, String destinyAirport, String airline, Categories minimumCategory) throws NoAvailableLaneException, RemoteException;
}
