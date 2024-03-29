package ar.edu.itba.pod.rmi.client;

import ar.edu.itba.pod.rmi.Notifications;
import ar.edu.itba.pod.rmi.Services.FlightTracingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.rmi.Naming;
import java.rmi.server.UnicastRemoteObject;

public class FlightTracingClient {
    private static final Logger logger = LoggerFactory.getLogger(FlightTracingClient.class);
    private static String address;
    private static String airline;
    private static int flightId;
    private static final int LIM = 3;


    public static void main(String[] args) {
        logger.info("Flight tracing client starting...");

        try {
            address = System.getProperty(ClientsArgsNames.SERVER_ADDRESS.getArgumentName());
            airline = System.getProperty(ClientsArgsNames.AIRLINE.getArgumentName());
            String flightIdString = System.getProperty(ClientsArgsNames.FLIGHT_ID.getArgumentName());
            if(address == null || airline == null || flightIdString == null) {
                throw new IllegalArgumentException("Address,out file and flight ID must be specified.");
            }

            flightId = Integer.parseInt(flightIdString);

        } catch ( NumberFormatException e ) {
            System.out.println("flightID must be a number.");
        } catch(IllegalArgumentException e) {
            System.out.println("Illegal argument: " + e.getMessage());
            return;
        }

        logger.info("Arguments are correct");
        Notifications clientNotifier;
        try {
            logger.info("Getting FlightTracerService");
            final FlightTracingService service = (FlightTracingService) Naming.lookup("//" + address + "/Airport-Service");

            logger.info("Exporting notification");
            clientNotifier = new Notification(flightId,airline);
            UnicastRemoteObject.exportObject(clientNotifier, 0);

            logger.info("Registering Airline");
            service.registerAirline(airline,flightId,clientNotifier);
        }catch ( Exception e){
            logger.error(e.getMessage());
        }
    }
}
