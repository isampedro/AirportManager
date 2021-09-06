import Airport.Categories;
import AirportExceptions.LaneNameAlreadyExistsException;
import AirportExceptions.LaneNotExistentException;
import AirportExceptions.SameLaneStateException;
import AirportOps.Admin;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;

import static org.junit.Assert.*;


public class AirportOpsTest {

    @Test
    @BeforeAll
    public void testAddLane() throws LaneNameAlreadyExistsException {
        Admin.addLane("Airport1", Categories.C);
        Admin.addLane("Airport2", Categories.F);
        Admin.addLane("Airport3", Categories.A);
        Admin.addLane("Airport4", Categories.B);
        Admin.addLane("Airport5", Categories.D);
        Admin.addLane("Airport6", Categories.E);
        assertEquals(6, Admin.getLanesQuantity());
    }

    @Test
    public void testLanesOpenClose() throws LaneNotExistentException, SameLaneStateException {
        assertTrue( Admin.isOpen("Airport1") );
        Admin.closeLane( "Airport1" );
        assertFalse( Admin.isOpen("Airport1") );
        Admin.openLane( "Airport1" );
        assertTrue( Admin.isOpen("Airport1") );
    }

    @Test( expected = LaneNameAlreadyExistsException.class )
    public void testThrowLaneNameAlreadyExistsException() throws LaneNameAlreadyExistsException {
        Admin.addLane("Airport1", Categories.A);
    }

    @Test( expected = SameLaneStateException.class )
    public void testThrowSameLaneException() throws SameLaneStateException, LaneNotExistentException, LaneNameAlreadyExistsException {
        Admin.openLane("Airport1");
    }

    @Test( expected = LaneNotExistentException.class )
    public void testThrowLaneNotExistenException() throws SameLaneStateException, LaneNotExistentException, LaneNameAlreadyExistsException {
        Admin.closeLane("Airport10");
    }

    @Test( expected = LaneNotExistentException.class )
    public void testThrowLaneNotExistenException2() throws LaneNotExistentException{
        Admin.isOpen("Airport10");
    }
}
