package ar.edu.itba.pod.rmi.client;

import ar.edu.itba.pod.rmi.AirportExceptions.LaneNameAlreadyExistsException;
import ar.edu.itba.pod.rmi.AirportExceptions.LaneNotExistentException;
import ar.edu.itba.pod.rmi.AirportExceptions.SameLaneStateException;
import ar.edu.itba.pod.rmi.Categories;
import ar.edu.itba.pod.rmi.Services.AirportOpsService;
import ar.edu.itba.pod.rmi.client.ClientExceptions.WrongNumberOfArgumentsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;


public class AdministrationClient {
    private static final Logger logger = LoggerFactory.getLogger(AdministrationClient.class);
    private static String address;
    private static ClientsActionNames actionName;
    private final static int LIM_L = 2, LIM_R = 4;

    private static String runwayName = null;
    private static Categories minimumCategory = null;

    public static void main(String[] args) {
        logger.info("Admin client starting...");

        try {
            if (args.length < LIM_L || args.length > LIM_R) {
                throw new WrongNumberOfArgumentsException(LIM_L, LIM_R);
            }
            for(String arg : args) {
                String[] argument = arg.split("=");
                String argumentName = argument[0];
                String argumentValue = argument[1];

                if(argumentName.equals(ClientsArgsNames.SERVER_ADDRESS.getArgumentName())) {
                    address = argumentValue;
                } else if(argumentName.equals(ClientsArgsNames.ACTION_NAME.getArgumentName())) {
                    actionName = ClientsActionNames.parseArgument(argumentValue);
                } else if(argumentName.equals(ClientsArgsNames.LANE_NAME.getArgumentName())) {
                    runwayName = argumentValue;
                } else if(argumentName.equals(ClientsArgsNames.CATEGORY_NAME.getArgumentName())) {
                    minimumCategory = Categories.parseString(argumentValue);
                } else {
                    throw new IllegalArgumentException();
                }
            }

            if(actionName.equals(ClientsActionNames.ADD)) {
                if(runwayName == null || minimumCategory == null)
                    throw new WrongNumberOfArgumentsException(LIM_L, LIM_R);
            } else {
                if(minimumCategory != null)
                    throw new WrongNumberOfArgumentsException(LIM_L, LIM_R);
            }
        } catch (WrongNumberOfArgumentsException | IllegalArgumentException e) {
            logger.error(e.getMessage());
            return;
        }

        logger.info("Arguments are correct");

        try {
            logger.info("Getting AirportOpsService");
            final AirportOpsService opsService = (AirportOpsService) Naming.lookup("//" + address + "/Airport-Service");
            String message = null;
            switch (actionName) {
                case ADD:
                    opsService.addLane(runwayName, minimumCategory);
                    logger.info("Runway " + runwayName + " is " + (opsService.isOpen(runwayName) ? "open" : "closed"));
                    break;
                case OPEN:
                    try {
                        opsService.openLane(runwayName);
                    } catch ( SameLaneStateException e ) {
                        message = e.getMessage();
                    }
                    if( message == null ) {
                        logger.info("Runway " + runwayName + " is now " + (opsService.isOpen(runwayName) ? "open" : "closed"));
                    } else {
                        logger.info(message);
                    }
                    break;
                case CLOSE:
                    try {
                        opsService.closeLane(runwayName);
                    } catch ( SameLaneStateException e ) {
                        message = e.getMessage();
                    }
                    if( message == null ) {
                        logger.info("Runway " + runwayName + " is now " + (opsService.isOpen(runwayName) ? "open" : "closed"));
                    } else {
                        logger.info(message);
                    }
                    break;
                case STATUS:
                    opsService.isOpen(runwayName);
                    logger.info("Runway " + runwayName + " is " + (opsService.isOpen(runwayName) ? "open" : "closed"));
                    break;
                case TAKE_OFF:
                    logger.info(opsService.emitDeparture().size() + " flights departed");
                case REORDER:
                    Map<Boolean, List<Integer>> reordered = opsService.emitReorder();
                    reordered.get(false).forEach(flight -> logger.info("Cannot assign Flight " + flight));
                    logger.info(reordered.get(true).size() + " flights assigned.");
                    break;
            }
        }  catch (LaneNameAlreadyExistsException | LaneNotExistentException e) {
            logger.error(e.getMessage());
        } catch ( Exception e ) {
            logger.error(e.getMessage());
        }
    }
}
