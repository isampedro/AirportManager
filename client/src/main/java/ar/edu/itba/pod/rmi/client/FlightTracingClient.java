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
            if (args.length != LIM )
                throw new WrongNumberOfArgumentsException(LIM);
            for(String arg : args) {
                String[] argument = arg.split("=");
                String argumentName = argument[0];
                String argumentValue = argument[1];

                if(argumentName.equals(ClientsArgsNames.SERVER_ADDRESS.getArgumentName())) {
                    address = argumentValue;
                } else if(argumentName.equals(ClientsArgsNames.AIRLINE.getArgumentName())) {
                    airline = argumentValue;
                } else if(argumentName.equals(ClientsArgsNames.FLIGHT_ID.getArgumentName())) {
                    flightId = Integer.parseInt(argumentValue);
                }

            }
        } catch(WrongNumberOfArgumentsException e) {
            System.out.println(e.getMessage());
            return;
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
