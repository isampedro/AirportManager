package ar.edu.itba.pod.rmi.Services;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

import ar.edu.itba.pod.rmi.AirportExceptions.*;
import ar.edu.itba.pod.rmi.Categories;

public interface AirportOpsService extends Remote {
    void addLane( String laneName, Categories category ) throws LaneNameAlreadyExistsException, RemoteException;

    boolean isOpen( String laneName ) throws LaneNotExistentException, RemoteException;

    void openLane( String laneName ) throws SameLaneStateException, LaneNotExistentException, RemoteException;

    void closeLane( String laneName ) throws SameLaneStateException, LaneNotExistentException, RemoteException;

    List<Integer> emitDeparture() throws RemoteException;

    Map<Boolean, List<Integer>> emitReorder() throws RemoteException;

    }
