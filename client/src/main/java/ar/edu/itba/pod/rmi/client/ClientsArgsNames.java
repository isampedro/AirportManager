package ar.edu.itba.pod.rmi.client;

public enum ClientsArgsNames {
    SERVER_ADDRESS ("-DserverAddress"),
    ACTION_NAME ("-Daction"),
    CATEGORY_NAME ("-Dcategory"),
    LANE_NAME ("-Drunway"),
    AIRLINE ("-Dairline"),
    AIRLINE_NAME ("-DairlineName"),
    FLIGHT_ID ("-DflightCode"),
    CSV_INPATH ("-inPath"),
    CSV_OUTPATH ("-outPath");

    String argumentName;

    ClientsArgsNames(String argumentName) {
        this.argumentName = argumentName;
    }

    public String getArgumentName() {
        return argumentName;
    }


}
