package ar.edu.itba.pod.rmi;

import java.rmi.Remote;
import ar.edu.itba.pod.rmi.AirportExceptions.*;

public interface AirportOpsService extends Remote {
    void addLane( String laneName, Categories category ) throws LaneNameAlreadyExistsException;

    boolean isOpen( String laneName ) throws LaneNotExistentException;

    void openLane( String laneName ) throws SameLaneStateException, LaneNotExistentException;

    void closeLane( String laneName ) throws SameLaneStateException, LaneNotExistentException;

    void emitDeparture();

    void emitReorder();

    void addFlightToLane(Flight flight) throws NoAvailableLaneException;

    }
