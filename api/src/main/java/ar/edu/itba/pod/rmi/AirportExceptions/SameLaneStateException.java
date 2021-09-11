package ar.edu.itba.pod.rmi.AirportExceptions;

public class SameLaneStateException extends Exception {
    public SameLaneStateException(boolean state) {
        super("Lane wanted to be " + (state ? "opened": "closed") + " when already " + (state ? "open": "closed"));
    }
}
