package ar.edu.itba.pod.rmi.AirportExceptions;

public class LaneNotExistentException extends Exception{
    public LaneNotExistentException(String laneName) {
        super("Lane " + laneName + " doesn't exist. Please, choose an existent name");
    }
}
