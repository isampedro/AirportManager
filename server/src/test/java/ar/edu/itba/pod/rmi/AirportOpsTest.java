package ar.edu.itba.pod.rmi;

import ar.edu.ar.pod.rmi.Categories;
import ar.edu.ar.pod.rmi.AirportExceptions.*;

import ar.edu.ar.pod.rmi.server.Servant;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

public class AirportOpsTest {
    private final Servant servant = new Servant();
    @Test
    @BeforeAll
    public void testAddLane() throws LaneNameAlreadyExistsException {
        servant.addLane("Airport1", Categories.C);
        servant.addLane("Airport2", Categories.F);
        servant.addLane("Airport3", Categories.A);
        servant.addLane("Airport4", Categories.B);
        servant.addLane("Airport5", Categories.D);
        servant.addLane("Airport6", Categories.E);
        assertEquals(6, servant.getLanesQuantity());
    }

    @Test
    public void testLanesOpenClose() throws LaneNotExistentException, SameLaneStateException {
        assertTrue( servant.isOpen("Airport1") );
        servant.closeLane( "Airport1" );
        assertFalse( servant.isOpen("Airport1") );
        servant.openLane( "Airport1" );
        assertTrue( servant.isOpen("Airport1") );
    }

    @Test( expected = LaneNameAlreadyExistsException.class )
    public void testThrowLaneNameAlreadyExistsException() throws LaneNameAlreadyExistsException {
        servant.addLane("Airport1", Categories.A);
    }

    @Test( expected = SameLaneStateException.class )
    public void testThrowSameLaneException() throws SameLaneStateException, LaneNotExistentException, LaneNameAlreadyExistsException {
        servant.openLane("Airport1");
    }

    @Test( expected = LaneNotExistentException.class )
    public void testThrowLaneNotExistentException() throws SameLaneStateException, LaneNotExistentException, LaneNameAlreadyExistsException {
        servant.closeLane("Airport10");
    }

    @Test( expected = LaneNotExistentException.class )
    public void testThrowLaneNotExistenException2() throws LaneNotExistentException{
        servant.isOpen("Airport10");
    }
}
