package ar.edu.itba.pod.rmi.server;

import ar.edu.itba.pod.rmi.Categories;

public class Flight {
    private final int id;
    private final Categories category;
    private final String airline;
    private final int destinyAirport;
    public Flight(int id, Categories category, String airline, int destinyAirport) {
        this.id = id;
        this.category = category;
        this.airline = airline;
        this.destinyAirport = destinyAirport;
    }

    public int getDestinyAirport() {
        return destinyAirport;
    }

    public int getId() {
        return id;
    }

    public Categories getCategory() {
        return category;
    }

    public String getAirline() {
        return airline;
    }

    @Override
    public String toString() {
        return "Flight " + id +
                ", of category " + category +
                ", from the Airline " + airline;
    }
}
