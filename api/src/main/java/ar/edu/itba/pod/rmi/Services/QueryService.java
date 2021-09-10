package ar.edu.itba.pod.rmi.Services;


import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface QueryService extends Remote {
    List<Integer> getTakeoffsForAirport() throws RemoteException;

    List<Integer> getTakeoffsForAirline(String airline) throws RemoteException;

    List<Integer> getTakeoffsForLane(String laneName) throws RemoteException;
}
