package ar.edu.itba.pod.rmi.client;

public enum ClientsActionNames {
    ADD("add"),
    OPEN("open"),
    CLOSE("close"),
    STATUS("status"),
    TAKE_OFF("takeOff"),
    REORDER("reorder");

    String argumentName;

    ClientsActionNames(String argumentName) {
        this.argumentName = argumentName;
    }

    public String getArgumentName() {
        return argumentName;
    }

    public static ClientsActionNames parseAction(String arg) {
        ClientsActionNames action;
        switch (arg) {
            case "add": action = ADD;
            break;
            case "open": action = OPEN;
            break;
            case "close": action = CLOSE;
            break;
            case "status": action = STATUS;
            break;
            case "takeOff": action = TAKE_OFF;
            break;
            case "reorder": action = REORDER;
            break;
            default:
                throw new IllegalStateException("Unexpected value: " + arg);
        }
        return action;
    }


}
