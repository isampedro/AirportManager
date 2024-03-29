package ar.edu.itba.pod.rmi.server;

public enum LaneState {
    OPEN,
    CLOSED,
    ;

    @Override
    public String toString() {
        String laneState;

        switch (this) {
            case OPEN:
                        laneState = "Open";
                        break;
            case CLOSED:
                        laneState = "Closed";
                        break;
            default:
                throw new IllegalStateException("Unexpected value: " + this);
        }

        return laneState;
    }
}
