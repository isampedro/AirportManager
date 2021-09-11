package ar.edu.itba.pod.rmi.AirportExceptions;

public class LaneNameAlreadyExistsException extends Exception{
    public LaneNameAlreadyExistsException(String laneName) {
        super("Lane " + laneName + " already exists. Please, choose a nonexistent name");
    }
}
