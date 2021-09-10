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




    public static void main(String[] args) {
        logger.info("Flight tracing client starting...");

        try {
            if (args.length != 3 )
                throw new WrongNumberOfArgumentsException();
            for(String arg : args) {
                String[] argument = arg.split("=");
                String argumentName = argument[0];
                String argumentValue = argument[1];

                if(argumentName.equals(ClientsArgsNames.SERVER_ADDRESS.getArgumentName())) {
                    address = argumentValue;
                } else if(argumentName.equals(ClientsArgsNames.AIRLINE.getArgumentName()) ||
                        argumentName.equals(ClientsArgsNames.AIRLINE_NAME.getArgumentName())) {
                    airline = argumentValue;
                } else if(argumentName.equals(ClientsArgsNames.FLIGHT_ID.getArgumentName())) {
                    flightId = Integer.parseInt(argumentValue);
                }

            }
        } catch(Exception e) {
            logger.error("Illegal argument: {}",e.getMessage());
        }

        logger.info("argument are correct");
        Notifications clientNotifier = null;
        try {
            logger.info("Getting FlightTracerService");
            final FlightTracingService service = (FlightTracingService) Naming.lookup("//" + address + "/Airport-Service");

            logger.info("Exporting notification");
            clientNotifier = new Notification(flightId,airline);
            UnicastRemoteObject.exportObject(clientNotifier, 0);

            logger.info("Registering Airline");
            service.registerAirline(airline,flightId,clientNotifier);
            //TODO: check if notification prints in client as it should and not in server?
        }catch ( Exception e){
            logger.error(e.getMessage());
            //UnicastRemoteObject.unexportObject(clientNotifier,true); we need to do it?
        }
    }
}
