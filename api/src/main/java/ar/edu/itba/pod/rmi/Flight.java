package ar.edu.itba.pod.rmi;

public class Flight {
    int id;
    Categories category;
    String airline;

    public Flight(int id, Categories category, String airline) {
        this.id = id;
        this.category = category;
        this.airline = airline;
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
