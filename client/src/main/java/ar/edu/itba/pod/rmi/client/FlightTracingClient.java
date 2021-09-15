package ar.edu.itba.pod.rmi.client;

import ar.edu.itba.pod.rmi.Notifications;
import ar.edu.itba.pod.rmi.Services.FlightTracingService;
import ar.edu.itba.pod.rmi.client.ClientExceptions.WrongNumberOfArgumentsException;
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
            flightId = Integer.parseInt(System.getProperty(ClientsArgsNames.FLIGHT_ID.getArgumentName()));
        } catch(Exception e) {
            System.out.println("Illegal argument: " + e.getMessage());
            return;
        }

        logger.info("argument are correct");
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
