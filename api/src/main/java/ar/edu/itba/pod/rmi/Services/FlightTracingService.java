package ar.edu.itba.pod.rmi.Services;

import ar.edu.itba.pod.rmi.Notifications;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface FlightTracingService extends Remote {

    void registerAirline(String airline, int flightId, Notifications handler) throws RemoteException;

}
