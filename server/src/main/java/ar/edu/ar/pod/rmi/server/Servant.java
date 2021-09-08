package ar.edu.ar.pod.rmi.server;

import ar.edu.ar.pod.rmi.Categories;
import ar.edu.ar.pod.rmi.Flight;
import ar.edu.ar.pod.rmi.Lane;
import ar.edu.ar.pod.rmi.LaneState;
import ar.edu.ar.pod.rmi.AirportExceptions.*;

import java.util.LinkedList;
import java.util.List;

public class Servant {
    private final List<Lane> laneList = new LinkedList<>();

    public void addLane( String laneName, Categories category ) throws LaneNameAlreadyExistsException {
        if( laneList.stream().anyMatch( (lane) -> lane.getName().equals(laneName))) {
            throw new LaneNameAlreadyExistsException();
        }

        laneList.add( new Lane(laneName, category));
    }

    public boolean isOpen( String laneName ) throws LaneNotExistentException {
        if( laneList.stream().anyMatch( (lane) -> lane.getName().equals(laneName))) {
            for( Lane lane: laneList ) {
                if( lane.getName().equals(laneName) ) {
                    return lane.getState().equals(LaneState.OPEN);
                }
            }
        }
        throw new LaneNotExistentException();
    }

    private void setLaneState( String laneName, LaneState state ) throws  SameLaneStateException {
        for( Lane lane: laneList ) {
            if( lane.getName().equals(laneName) ) {
                if( !lane.getState().equals(state) ) {
                    lane.setState(state);
                    return;
                } else {
                    throw new SameLaneStateException();
                }
            }
        }
    }

    public void openLane( String laneName ) throws SameLaneStateException, LaneNotExistentException {
        if( laneList.stream().anyMatch( (lane) -> lane.getName().equals(laneName))) {
            setLaneState( laneName, LaneState.OPEN );
            return;
        }

        throw new LaneNotExistentException();
    }

    public void closeLane( String laneName ) throws SameLaneStateException, LaneNotExistentException {
        if( laneList.stream().anyMatch( (lane) -> lane.getName().equals(laneName))) {
            setLaneState( laneName, LaneState.CLOSED );
            return;
        }

        throw new LaneNotExistentException();
    }

    public void emitDeparture() {
        for( Lane lane : laneList ) {
            if( lane.flightsAreAwaiting() && lane.getState().equals(LaneState.OPEN) ){
                lane.departFlight();
            }
        }
    }

    public void emitReorder() {
        List<Flight> flights = new LinkedList<>();

        for( Lane lane : laneList ) {
            if( lane.flightsAreAwaiting() ){
                flights.add(lane.departFlight());
            }
        }

        // TODO: terminar de reordenar los vuelos


    }

    public static void addFlightToLane(Flight flight) throws NoAvailableLaneException{
        Lane minLane = null;
        for (Lane lane : laneList) {
            if(lane.getCategory().isHigherOrEqual(flight.getCategory()) &&
                    lane.getState().equals(LaneState.OPEN)){
                if(minLane == null)
                    minLane = lane;
                else if(lane.getFlightsQuantity() < minLane.getFlightsQuantity())
                    minLane = lane;
            }
        }
        if (minLane == null)
            throw new NoAvailableLaneException();
        else
            minLane.addNewFlight(flight);
    }

    public void printAirports() {
        System.out.println("Airport lanes: ");
        laneList.forEach(System.out::println);
    }

    public int getLanesQuantity() {
        return laneList.size();
    }
}