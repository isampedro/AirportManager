package AirportOps;

import java.util.LinkedList;
import java.util.List;

import Airport.*;
import AirportExceptions.*;


public class Admin {

    private static final List<Lane> laneList = new LinkedList<>();

    public static void addLane( String laneName, Categories category ) throws LaneNameAlreadyExistsException {
        if( laneList.stream().anyMatch( (lane) -> lane.getName().equals(laneName))) {
            throw new LaneNameAlreadyExistsException();
        }

        laneList.add( new Lane(laneName, category));
    }

    public static boolean isOpen( String laneName ) throws LaneNotExistentException {
        if( laneList.stream().anyMatch( (lane) -> lane.getName().equals(laneName))) {
            for( Lane lane: laneList ) {
                if( lane.getName().equals(laneName) ) {
                    return lane.getState().equals(LaneState.OPEN);
                }
            }
        }
        throw new LaneNotExistentException();
    }

    private static void setLaneState( String laneName, LaneState state ) throws  SameLaneStateException {
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

    public static void openLane( String laneName ) throws SameLaneStateException, LaneNotExistentException {
        if( laneList.stream().anyMatch( (lane) -> lane.getName().equals(laneName))) {
            setLaneState( laneName, LaneState.OPEN );
            return;
        }

        throw new LaneNotExistentException();
    }

    public static void closeLane( String laneName ) throws SameLaneStateException, LaneNotExistentException {
        if( laneList.stream().anyMatch( (lane) -> lane.getName().equals(laneName))) {
            setLaneState( laneName, LaneState.CLOSED );
            return;
        }

        throw new LaneNotExistentException();
    }

    public static void emitDeparture() {
        for( Lane lane : laneList ) {
            if( lane.flightsAreAwaiting() && lane.getState().equals(LaneState.OPEN) ){
                lane.departFlight();
            }
        }
    }

    public static void emitReorder() {
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

    public static void printAirports() {
        System.out.println("Airport lanes: ");
        laneList.forEach(System.out::println);
    }

    public static int getLanesQuantity() {
        return laneList.size();
    }
}
