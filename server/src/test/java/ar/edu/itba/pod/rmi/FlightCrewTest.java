package ar.edu.itba.pod.rmi;

import Airport.Categories;
import AirportExceptions.LaneNameAlreadyExistsException;
import AirportExceptions.NoAvailableLaneException;
import AirportOps.Admin;
import FligthCrew.LaneRequester;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

public class FlightCrewTest {

    @Test( expected = NoAvailableLaneException.class )
    @BeforeAll
    public void throwNoAvailableLaneException() throws NoAvailableLaneException {
        LaneRequester.flightLane( 1, 2, "Honduras' Airline", Categories.B );
    }

    @Test
    @AfterAll
    public void testRequestFlight() throws NoAvailableLaneException, LaneNameAlreadyExistsException {
        Admin.addLane("Airport1", Categories.C);
        Admin.addLane("Airport2", Categories.F);
        Admin.addLane("Airport3", Categories.A);
        Admin.addLane("Airport4", Categories.B);
        Admin.addLane("Airport5", Categories.D);
        Admin.addLane("Airport6", Categories.E);
        LaneRequester.flightLane( 1, 2, "Honduras' Airline", Categories.B );
    }
}
