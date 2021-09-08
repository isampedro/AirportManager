package ar.edu.itba.pod.rmi.server;

import ar.edu.itba.pod.rmi.*;
import ar.edu.itba.pod.rmi.AirportExceptions.*;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Servant implements AirportOpsService, LaneRequesterService {
    private final Map<Integer, List<Lane>> laneMap;

    public Servant() {
        this.laneMap = new HashMap<>();
        for (int i = 1; i < Categories.maxAuthorization() ; i++) {
            laneMap.put(i, new LinkedList<>());
        }
    }

    @Override
    public void addFlightToLane(int flightId, int destinyAirport, String airline, Categories minimumCategory) throws NoAvailableLaneException {
        Flight flight = new Flight(flightId, minimumCategory, airline);
        Lane minLane = null;
        for( Integer key : laneMap.keySet() ) {
            for (Lane lane : laneMap.get(key)) {
                if(lane.getCategory().isHigherOrEqual(flight.getCategory()) &&
                        lane.getState().equals(LaneState.OPEN)){
                    if(minLane == null)
                        minLane = lane;
                    else if(lane.getFlightsQuantity() < minLane.getFlightsQuantity())
                        minLane = lane;
                }
            }
        }
        if (minLane == null)
            throw new NoAvailableLaneException();
        else {
            minLane.addNewFlight(flight);
            sortLanes();
        }
    }

    public void addLane( String laneName, Categories category ) throws LaneNameAlreadyExistsException {
        for( Integer key : laneMap.keySet() ) {
            if (laneMap.get(key).stream().anyMatch((lane) -> lane.getName().equals(laneName))) {
                throw new LaneNameAlreadyExistsException();
            }
        }
        laneMap.get(category.getAuthorization()).add( new Lane(laneName, category));
        sortLanes();
    }

    private void sortLanes() {
        for( Integer key : laneMap.keySet() ) {
            laneMap.get(key).sort( (lane1, lane2) -> {
                if( lane1.getCategory().equals(lane2.getCategory()) ) {
                    if( lane1.getFlightsQuantity() == lane2.getFlightsQuantity() ) {
                        return 0;
                    }
                    return lane1.getFlightsQuantity() < lane2.getFlightsQuantity() ? -1: 1;
                }
                return lane2.getCategory().isHigherOrEqual(lane1.getCategory()) ? 1:-1;
            });
        }
    }

    public boolean isOpen( String laneName ) throws LaneNotExistentException {
        for( Integer key : laneMap.keySet() ) {
            for( Lane lane: laneMap.get(key) ) {
                if( lane.getName().equals(laneName) ) {
                    return lane.getState().equals(LaneState.OPEN);
                }
            }
        }

        throw new LaneNotExistentException();
    }

    private void setLaneState( String laneName, LaneState state ) throws  SameLaneStateException, LaneNotExistentException {
        for( Integer key : laneMap.keySet() ) {
            for( Lane lane: laneMap.get(key) ) {
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

        throw new LaneNotExistentException();
    }

    public void openLane( String laneName ) throws SameLaneStateException, LaneNotExistentException {
        for( Integer key : laneMap.keySet() ) {
            if( laneMap.get(key).stream().anyMatch( (lane) -> lane.getName().equals(laneName))) {
                setLaneState( laneName, LaneState.OPEN );
                return;
            }
        }

        throw new LaneNotExistentException();
    }

    public void closeLane( String laneName ) throws SameLaneStateException, LaneNotExistentException {
        for( Integer key : laneMap.keySet() ) {
            if( laneMap.get(key).stream().anyMatch( (lane) -> lane.getName().equals(laneName))) {
                setLaneState( laneName, LaneState.CLOSED );
                return;
            }
        }

        throw new LaneNotExistentException();
    }

    public void emitDeparture() {
        for( Integer key : laneMap.keySet() ) {
            for( Lane lane : laneMap.get(key) ) {
                if( lane.flightsAreAwaiting() && lane.getState().equals(LaneState.OPEN) ){
                    lane.departFlight();
                }
            }
        }
    }

    public void emitReorder() {
        List<Flight> flights = new LinkedList<>();

        while( !emptyAirport() ) {
            for( Integer key : laneMap.keySet() ) {
                for (Lane lane : laneMap.get(key)) {
                    if (lane.flightsAreAwaiting()) {
                        flights.add(lane.departFlight());
                    }
                }
            }
        }



        // TODO: terminar de reordenar los vuelos


    }

    private boolean emptyAirport() {
        for( Integer key : laneMap.keySet() ) {
            for (Lane lane : laneMap.get(key)) {
                if (lane.flightsAreAwaiting()) {
                    return false;
                }
            }
        }
        return true;
    }

    public void printAirports() {
        System.out.println("Airport lanes: ");
        laneMap.values().forEach( lane -> lane.forEach(System.out::println));
    }

    public int getLanesQuantity() {
        int size = 0;
        for( Integer key : laneMap.keySet() ) {
            size += laneMap.get(key).size();
        }
        return size;
    }
}