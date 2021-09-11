package ar.edu.itba.pod.rmi.Services;


import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface QueryService extends Remote {
    List<String> getTakeoffsForAirport() throws RemoteException;

    List<String> getTakeoffsForAirline(String airline) throws RemoteException;

    List<String> getTakeoffsForLane(String laneName) throws RemoteException;
}
