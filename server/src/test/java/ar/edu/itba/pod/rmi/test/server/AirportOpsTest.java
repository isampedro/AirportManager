package ar.edu.itba.pod.rmi.test.server;

import ar.edu.itba.pod.rmi.Categories;
import ar.edu.itba.pod.rmi.AirportExceptions.*;
import ar.edu.itba.pod.rmi.server.Servant;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

public class AirportOpsTest {
    private Servant servantTest = new Servant();
    @Test
    @BeforeAll
    public void testAddLane() throws LaneNameAlreadyExistsException {
        servantTest.addLane("Airport1", Categories.C);
        servantTest.addLane("Airport2", Categories.F);
        servantTest.addLane("Airport3", Categories.A);
        servantTest.addLane("Airport4", Categories.B);
        servantTest.addLane("Airport5", Categories.D);
        servantTest.addLane("Airport6", Categories.E);
        assertEquals(6, servantTest.getLanesQuantity());
    }

    @Test
    public void testLanesOpenClose() throws LaneNotExistentException, SameLaneStateException, LaneNameAlreadyExistsException {
        servantTest.addLane("Airport1", Categories.C);
        servantTest.addLane("Airport2", Categories.F);
        servantTest.addLane("Airport3", Categories.A);
        servantTest.addLane("Airport4", Categories.B);
        servantTest.addLane("Airport5", Categories.D);
        servantTest.addLane("Airport6", Categories.E);
        assertTrue( servantTest.isOpen("Airport1") );
        servantTest.closeLane( "Airport1" );
        assertFalse( servantTest.isOpen("Airport1") );
        servantTest.openLane( "Airport1" );
        assertTrue( servantTest.isOpen("Airport1") );
    }

    @Test( expected = LaneNameAlreadyExistsException.class )
    public void testThrowLaneNameAlreadyExistsException() throws LaneNameAlreadyExistsException {
        servantTest.addLane("Airport1", Categories.C);
        servantTest.addLane("Airport2", Categories.F);
        servantTest.addLane("Airport3", Categories.A);
        servantTest.addLane("Airport4", Categories.B);
        servantTest.addLane("Airport5", Categories.D);
        servantTest.addLane("Airport6", Categories.E);
        servantTest.addLane("Airport1", Categories.A);
    }

    @Test( expected = SameLaneStateException.class )
    public void testThrowSameLaneException() throws SameLaneStateException, LaneNotExistentException, LaneNameAlreadyExistsException {
        servantTest.addLane("Airport1", Categories.C);
        servantTest.addLane("Airport2", Categories.F);
        servantTest.addLane("Airport3", Categories.A);
        servantTest.addLane("Airport4", Categories.B);
        servantTest.addLane("Airport5", Categories.D);
        servantTest.addLane("Airport6", Categories.E);
        servantTest.openLane("Airport1");
    }

    @Test( expected = LaneNotExistentException.class )
    public void testThrowLaneNotExistentException() throws SameLaneStateException, LaneNotExistentException, LaneNameAlreadyExistsException {
        servantTest.closeLane("Airport10");
    }

    @Test( expected = LaneNotExistentException.class )
    public void testThrowLaneNotExistenException2() throws LaneNotExistentException{
        servantTest.isOpen("Airport10");
    }
}
