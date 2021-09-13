package ar.edu.itba.pod.rmi.client.ClientExceptions;

public class WrongNumberOfArgumentsException extends Exception {
    public WrongNumberOfArgumentsException( int limL, int limR) {
        super("Wrong number of arguments provided. Please, provide between " + limL + " and " + limR + " arguments");
    }

    public WrongNumberOfArgumentsException( int lim ) {
        super("Wrong number of arguments provided. Please, provide " + lim + " arguments");
    }
}
