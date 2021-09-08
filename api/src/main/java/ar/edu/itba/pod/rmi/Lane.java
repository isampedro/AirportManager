package ar.edu.itba.pod.rmi;

import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

public class Lane {
    private final String name;
    private LaneState state;
    private final Categories category;
    private final Queue<Flight> flights;

    public Lane(String name, Categories category) {
        this.flights = new LinkedList<>();
        this.name = name;
        this.state = LaneState.OPEN;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public LaneState getState() {
        return state;
    }

    public void setState(LaneState state) {
        this.state = state;
    }

    public void addNewFlight( Flight flight ) {
        flights.offer(flight);
    }

    public boolean flightsAreAwaiting() {
        return !flights.isEmpty();
    }

    public Flight departFlight() {
        if( flightsAreAwaiting() ) {
            return flights.poll();
        }

        return null;
    }

    public int getFlightsQuantity(){
        return flights.size();
    }

    public Categories getCategory() {
        return category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lane lane = (Lane) o;
        return Objects.equals(name, lane.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "Lane name: '" + name +
                ", lane state: " + state +
                ", lane category: " + category +
                ", flights: " + flights;
    }
}