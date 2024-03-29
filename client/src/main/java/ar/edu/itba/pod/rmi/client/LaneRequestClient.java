package ar.edu.itba.pod.rmi.client;

import ar.edu.itba.pod.rmi.AirportExceptions.NoAvailableLaneException;
import ar.edu.itba.pod.rmi.Categories;
import ar.edu.itba.pod.rmi.Services.LaneRequesterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class LaneRequestClient {

    private static final Logger logger = LoggerFactory.getLogger(LaneRequestClient.class);
    private static String address;
    private static String csvInFileName;
    private static final int LIM = 2;

    public static void main(String[] args) {
        logger.info("Lane request client starting...");

        try {
            csvInFileName = System.getProperty(ClientsArgsNames.CSV_INPATH.getArgumentName());
            address = System.getProperty(ClientsArgsNames.SERVER_ADDRESS.getArgumentName());

            if(address == null || csvInFileName == null) {
                throw new IllegalArgumentException("Address and in file must be specified.");
            }


            ArrayList<Line> requests = parseCsv(csvInFileName);
            LaneRequesterService requesterService = (LaneRequesterService) Naming.lookup("//" + address + "/Airport-Service");
            int i = 0;
            for(Line request: requests) {
                try {
                    requesterService.addFlightToLane(request.flightId, request.destinyAirport,
                            request.airlineName, request.minimumCategory);
                    i++;
                } catch (NoAvailableLaneException l) {
                    System.out.println("Cannot assign Flight " + request.flightId + ".");
                }
            }
            System.out.println(i + " flights assigned.");
        } catch (RemoteException e) {
            logger.error("Remote message error: {}",e.getMessage());
        } catch (NotBoundException | MalformedURLException ex) {
            System.out.println("There is a problem with the csv path provided.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private static ArrayList<Line> parseCsv(String fileName) {
        ArrayList<Line> parsedLines = new ArrayList<>();
        String[] lineArgs;
        String line;
        boolean title = true;
        try {
            FileReader fileReader =
                    new FileReader(fileName);

            BufferedReader bufferedReader =
                    new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null) {
                if(title) {
                    title = false;
                } else {
                    lineArgs = line.split(";");
                    parsedLines.add(new Line(Integer.parseInt(lineArgs[0]), lineArgs[1], lineArgs[2], Categories.parseString(lineArgs[3])));
                }
            }

            bufferedReader.close();
        } catch(FileNotFoundException ex) {
            System.out.println("Unable to open file '" + fileName + ".");
        } catch(IOException ex) {
            System.out.println("Error reading file '" + fileName + ".");
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
