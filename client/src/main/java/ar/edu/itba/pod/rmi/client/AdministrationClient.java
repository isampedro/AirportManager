package ar.edu.itba.pod.rmi.client;

import ar.edu.itba.pod.rmi.Categories;
import ar.edu.itba.pod.rmi.Services.AirportOpsService;
import ar.edu.itba.pod.rmi.client.ClientExceptions.WrongNumberOfArgumentsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.rmi.Naming;


public class AdministrationClient {
    private static final Logger logger = LoggerFactory.getLogger(AdministrationClient.class);
    private static String address;
    private static ClientsActionNames actionName;

    private static String runwayName = null;
    private static Categories minimumCategory = null;

    public static void main(String[] args) {
        logger.info("Admin client starting...");

        try {
            if (args.length < 2 || args.length > 4)
                throw new WrongNumberOfArgumentsException();
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

            if(!(actionName.equals(ClientsActionNames.TAKE_OFF) || actionName.equals(ClientsActionNames.REORDER))) {
                if(runwayName == null || minimumCategory == null)
                    throw new WrongNumberOfArgumentsException();
            } else {
                if(runwayName != null || minimumCategory != null)
                    throw new WrongNumberOfArgumentsException();
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        logger.info("argument are correct");

        try {
            logger.info("Getting AirportOpsService");
            final AirportOpsService opsService = (AirportOpsService) Naming.lookup("//" + address + "/Airport-Service");
            switch (actionName) {
                case ADD:
                    opsService.addLane(runwayName, minimumCategory);
                    break;
                case OPEN:
                    opsService.openLane(runwayName);
                    break;
                case CLOSE:
                    opsService.closeLane(runwayName);
                    break;
                case STATUS:
                    opsService.isOpen(runwayName);
                    break;
                case TAKE_OFF:
                    opsService.emitDeparture();
                case REORDER:
                    opsService.emitReorder();
                    break;
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }
}
