package ar.edu.itba.pod.rmi.client;

import ar.edu.itba.pod.rmi.Categories;
import ar.edu.itba.pod.rmi.Services.LaneRequesterService;
import ar.edu.itba.pod.rmi.client.ClientExceptions.WrongNumberOfArgumentsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.rmi.Naming;
import java.util.ArrayList;

public class LaneRequestClient {

    private static final Logger logger = LoggerFactory.getLogger(LaneRequestClient.class);
    private static String address;
    private static String csvInFileName;

    public static void main(String[] args) {
        logger.info("Lane request client starting...");

        try {
            if (args.length != 2)
                throw new WrongNumberOfArgumentsException();
            for (String arg : args) {
                String[] argument = arg.split("=");
                String argumentName = argument[0];
                String argumentValue = argument[1];

                if(argumentName.equals(ClientsArgsNames.CSV_INPATH.getArgumentName())) {
                    csvInFileName = argumentValue;
                } else if(argumentName.equals(ClientsArgsNames.SERVER_ADDRESS.getArgumentName())) {
                    address = argumentValue;
                } else {
                    throw new IllegalArgumentException();
                }
            }

            ArrayList<Line> requests = parseCsv(csvInFileName);
            LaneRequesterService requesterService = (LaneRequesterService) Naming.lookup("//" + address + "/Airport-Service");
            int i = 0;
            for(Line request: requests) {
                try {
                    requesterService.addFlightToLane(request.flightId, request.destinyAirport,
                            request.airlineName, request.minimumCategory);
                    i++;
                } catch (Exception e) {
                    logger.info("Cannot assign Flight " + request.flightId);
                }
            }
            logger.info(i + " flights assigned.");
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

        public static ArrayList<Line> parseCsv(String fileName) {
        ArrayList<Line> parsedLines = new ArrayList<>();
        String[] lineArgs;
        String line;

        try {
            FileReader fileReader =
                    new FileReader(fileName);

            BufferedReader bufferedReader =
                    new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null) {
                lineArgs = line.split(";");
                parsedLines.add(new Line(Integer.parseInt(lineArgs[0]), lineArgs[1],
                        lineArgs[2], Categories.parseString(lineArgs[3])));
            }

            bufferedReader.close();
        } catch(FileNotFoundException ex) {
            logger.error("Unable to open file '" + fileName + "'");
        } catch(IOException ex) {
            logger.error("Error reading file '" + fileName + "'");
        }
        return parsedLines;
    }

    private static class Line {
        private final int flightId;
        private final String destinyAirport;
        private final String airlineName;
        private final Categories minimumCategory;

        public Line(int flightId, String destinyAirport, String airlineName, Categories minimumCategory) {
            this.flightId = flightId;
            this.destinyAirport = destinyAirport;
            this.airlineName = airlineName;
            this.minimumCategory = minimumCategory;
        }
    }
}
