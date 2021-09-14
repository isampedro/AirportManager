package ar.edu.itba.pod.rmi.client;

public enum ClientsArgsNames {
    SERVER_ADDRESS ("-DserverAddress"),
    ACTION_NAME ("-Daction"),
    CATEGORY_NAME ("-Dcategory"),
    LANE_NAME ("-Drunway"),
    AIRLINE ("-Dairline"),
    FLIGHT_ID ("-DflightCode"),
    CSV_INPATH ("-DinPath"),
    CSV_OUTPATH ("-DoutPath");

    String argumentName;

    ClientsArgsNames(String argumentName) {
        this.argumentName = argumentName;
    }

    public String getArgumentName() {
        return argumentName;
    }


}
