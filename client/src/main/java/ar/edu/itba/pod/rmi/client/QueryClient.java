package ar.edu.itba.pod.rmi.client;

import ar.edu.itba.pod.rmi.Services.QueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.rmi.Naming;
import java.util.List;

public class QueryClient {
    private static String csvOutFile;
    private static String address;

    private static String runwayName = null;
    private static String airlineName = null;

    private final static int LIM_L = 2, LIM_R = 4;

    private static final Logger logger = LoggerFactory.getLogger(QueryClient.class);

    public static void main(String[] args) {
        logger.info("Query client starting...");

        try {
            address = System.getProperty(ClientsArgsNames.SERVER_ADDRESS.getArgumentName());
            csvOutFile = System.getProperty(ClientsArgsNames.CSV_OUTPATH.getArgumentName());
            runwayName = System.getProperty(ClientsArgsNames.LANE_NAME.getArgumentName());
            airlineName = System.getProperty(ClientsArgsNames.AIRLINE.getArgumentName());

            if(csvOutFile == null || address == null) {
                throw new IllegalArgumentException("Address and out file must be specified.");
            }

            if(runwayName != null && airlineName != null) {
                throw new IllegalArgumentException("That's not a valid query.");
            }

        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return;
        }

        logger.info("Arguments are correct");

        try {
            QueryService queryService = (QueryService) Naming.lookup("//" + address + "/Airport-Service");
            List<String> takeOffOrders;
            if(airlineName != null) {
                takeOffOrders = queryService.getTakeoffsForAirline(airlineName);
            } else if(runwayName != null) {
                takeOffOrders = queryService.getTakeoffsForLane(runwayName);
            } else {
                takeOffOrders = queryService.getTakeoffsForAirport();
            }
            System.out.println(takeOffOrders);
            System.out.println("Query was successful.");
            if( !takeOffOrders.isEmpty() ) {
                writeToCSV(csvOutFile, takeOffOrders);
            } else {
                System.out.println("There are no departures for this query.");
            }

        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    private static void writeToCSV(String outPath, List<String> results){
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(outPath))) {
            for (String result : results) {
                bw.write(result);
                bw.newLine();
            }
            } catch (IOException e) {
            logger.error("IOException {} ",e.getMessage());
        }

    }
}
