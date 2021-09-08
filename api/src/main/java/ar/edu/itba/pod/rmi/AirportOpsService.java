package ar.edu.itba.pod.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

import ar.edu.itba.pod.rmi.AirportExceptions.*;

public interface AirportOpsService extends Remote {
    void addLane( String laneName, Categories category ) throws LaneNameAlreadyExistsException, RemoteException;

    boolean isOpen( String laneName ) throws LaneNotExistentException, RemoteException;

    void openLane( String laneName ) throws SameLaneStateException, LaneNotExistentException, RemoteException;

    void closeLane( String laneName ) throws SameLaneStateException, LaneNotExistentException, RemoteException;

    void emitDeparture() throws RemoteException;

    void emitReorder() throws RemoteException;

    void addFlightToLane(Flight flight) throws NoAvailableLaneException, RemoteException;

    }
