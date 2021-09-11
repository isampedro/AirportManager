package ar.edu.itba.pod.rmi.AirportExceptions;

public class NoAvailableLaneException extends Exception {
    public NoAvailableLaneException(int flightId ) {
        super("There are no available lanes for flight " + flightId + " to take off");
    }
}
