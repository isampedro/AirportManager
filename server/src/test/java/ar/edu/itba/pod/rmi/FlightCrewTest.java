package ar.edu.itba.pod.rmi;

import ar.edu.ar.pod.rmi.Categories;
import ar.edu.ar.pod.rmi.AirportExceptions.*;

import ar.edu.ar.pod.rmi.server.Servant;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;


public class FlightCrewTest {
    private final Servant servant = new Servant();

    @Test( expected = NoAvailableLaneException.class )
    @BeforeAll
    public void throwNoAvailableLaneException() throws NoAvailableLaneException {
        servant.flightLane( 1, 2, "Honduras' Airline", Categories.B );
    }

    @Test
    @AfterAll
    public void testRequestFlight() throws NoAvailableLaneException, LaneNameAlreadyExistsException {
        servant.addLane("Airport1", Categories.C);
        servant.addLane("Airport2", Categories.F);
        servant.addLane("Airport3", Categories.A);
        servant.addLane("Airport4", Categories.B);
        servant.addLane("Airport5", Categories.D);
        servant.addLane("Airport6", Categories.E);
        servant.flightLane( 1, 2, "Honduras' Airline", Categories.B );
    }
}
