package ar.edu.itba.pod.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Notifications extends Remote {

    void notifyEvent( Events event, String destiny, String runaway, int flightsAhead) throws RemoteException;
}
