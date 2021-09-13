package ar.edu.itba.pod.rmi.client;

import ar.edu.itba.pod.rmi.Events;
import ar.edu.itba.pod.rmi.Notifications;

import java.rmi.RemoteException;

public class Notification implements Notifications {

    private final int flight;
    private final String airline;

    public Notification(int flight, String airline) {
        this.flight = flight;
        this.airline = airline;
    }

    public int getFlight() {
        return flight;
    }

    public String getAirline() {
        return airline;
    }

    @Override
    public void notifyEvent(Events event, String destiny, String runway, int flightAhead) throws RemoteException {
        switch (event){
            case ADVANCE:
                System.out.printf("A flight departed from runway %s.Flight %d with destiny %s advanced one place and now has %d flights waiting ahead.\n", runway, this.flight, destiny, flightAhead);
                break;
            case ASSIGNED:
                System.out.printf("Flight %d with destiny %s was assigned to runway %s and there are %d flights waiting ahead.\n",this.flight, destiny,runway, flightAhead);
                break;
            case DEPARTURE:
                System.out.printf(" Flight %d with destiny %s departed on runway %s.\n",this.flight, destiny,runway);
                break;
        }
    }



/*TODO:
       Asignado a pista: Flight 5382 with destiny SCL was assigned to runway R1 and there are 3 flights waiting ahead.
       Avance: A flight departed from runway R1.Flight 5382 with destiny SCL has 2 flights waiting ahead.
       vuelo deppartio: Flight 5382 with destiny SCL departed on runway R3.
       asignadp: Flight 5382 with destiny SCL was assigned to runway R3 and there are 0 flights waiting ahead.
        */
}
