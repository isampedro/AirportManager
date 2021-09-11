package ar.edu.itba.pod.rmi.client;

import ar.edu.itba.pod.rmi.Services.QueryService;
import ar.edu.itba.pod.rmi.client.ClientExceptions.WrongNumberOfArgumentsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.rmi.Naming;

public class QueryClient {
    private static String csvOutFile;
    private static String address;

    private static String runwayName = null;
    private static String airlineName = null;

    private static final Logger logger = LoggerFactory.getLogger(QueryClient.class);

    public static void main(String[] args) {
        logger.info("Query client starting...");

        try {
            if (args.length < 2 || args.length > 4)
                throw new WrongNumberOfArgumentsException();
            for (String arg : args) {
                String[] argument = arg.split("=");
                String argumentName = argument[0];
                String argumentValue = argument[1];

                if(argumentName.equals(ClientsArgsNames.SERVER_ADDRESS.getArgumentName())) {
                    address = argumentValue;
                } else if(argumentName.equals(ClientsArgsNames.CSV_OUTPATH.getArgumentName())) {
                    csvOutFile = argumentValue;
                } else if(argumentName.equals(ClientsArgsNames.LANE_NAME.getArgumentName())) {
                    runwayName = argumentValue;
                } else if(argumentName.equals(ClientsArgsNames.AIRLINE.getArgumentName()) ||
                        argumentName.equals(ClientsArgsNames.AIRLINE_NAME.getArgumentName())) {
                    airlineName = argumentValue;
                } else {
                    throw new IllegalArgumentException();
                }
            }

            if(runwayName != null && airlineName != null) {
                throw new IllegalArgumentException();
            }

        } catch (WrongNumberOfArgumentsException e) {
            logger.error(e.getMessage());
        }

        logger.info("argument are correct");

        try {
            QueryService queryService = (QueryService) Naming.lookup("//" + address + "/Airport-Service");
            if(airlineName != null) {
                queryService.getTakeoffsForAirline(airlineName);
            } else if(runwayName != null) {
                queryService.getTakeoffsForLane(airlineName);
            } else {
                queryService.getTakeoffsForAirport();
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }
}
