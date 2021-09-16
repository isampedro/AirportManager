package ar.edu.itba.pod.rmi.test.server;

import ar.edu.itba.pod.rmi.Categories;
import ar.edu.itba.pod.rmi.server.Servant;
import org.junit.Test;

public class QueryTest {
    private final Servant servant = new Servant();

    @Test
    public void runTest() throws Exception{
        servant.addLane("Super duper runway", Categories.F);
        servant.addFlightToLane(5382, "SCL", "Air Canada;",
                Categories.F);
        servant.addFlightToLane(926, "COR", "Aerolíneas Argentinas",
                Categories.C);
        servant.addFlightToLane(927, "COR", "Aerolíneas Argentinas",
                Categories.D);
        servant.addFlightToLane(5383, "SCC", "Air Canada;",
                Categories.F);
        servant.addFlightToLane(928, "CAR", "Aerolíneas Argentinas",
                Categories.C);
        servant.addFlightToLane(929, "CLR", "Aerolíneas Argentinas",
                Categories.F);
        servant.addFlightToLane(5380, "RCL", "Air Canada;",
                Categories.A);
        servant.addFlightToLane(920, "BAR", "Aerolíneas Argentinas",
                Categories.B);
        servant.addFlightToLane(911, "USH", "Aerolíneas Argentinas",
                Categories.E);
        servant.emitDeparture();
        servant.emitDeparture();
        System.out.println(servant.getTakeoffsForAirport());
    }
}
