package ar.edu.itba.pod.rmi.client;

import ar.edu.itba.pod.rmi.Services.QueryService;
import ar.edu.itba.pod.rmi.client.ClientExceptions.WrongNumberOfArgumentsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.rmi.Naming;
import java.util.ArrayList;
import java.util.List;

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
            List<String> takeOffOrders;
            if(airlineName != null) {
                takeOffOrders = queryService.getTakeoffsForAirline(airlineName);
            } else if(runwayName != null) {
                takeOffOrders = queryService.getTakeoffsForLane(airlineName);
            } else {
                takeOffOrders = queryService.getTakeoffsForAirport();
            }
            writeToCSV(csvOutFile, takeOffOrders);

        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    private static void writeToCSV(String outPath, List<String> results){
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(outPath))) {
                for(int i=0; i < results.size(); i++){
                    bw.write(results.get(i));
                    bw.newLine();
                }
            } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
