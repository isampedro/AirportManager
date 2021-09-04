package Airport;

public class Flight {
    int id;
    Categories category;
    String Airline;

    public Flight(int id, Categories category, String airline) {
        this.id = id;
        this.category = category;
        Airline = airline;
    }

    public int getId() {
        return id;
    }

    public Categories getCategory() {
        return category;
    }

    public String getAirline() {
        return Airline;
    }
}
