package Airport;

import java.util.LinkedList;
import java.util.List;

public class Airport {
    List<Lane> laneList = new LinkedList<Lane>();



    public void addLane( String laneName, Categories category ) {
        laneList.add( new Lane(laneName, category));
    }

    public boolean isOpen( String laneName ) {
        if( laneList.stream().anyMatch( (lane) ->  ))
    }
}
