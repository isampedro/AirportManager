package ar.edu.itba.pod.rmi.AirportExceptions;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface FlightTracingService extends Remote {

    void notifyEvent(String notification) throws RemoteException;
}
