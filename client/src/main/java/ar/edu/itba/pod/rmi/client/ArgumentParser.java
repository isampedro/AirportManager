package ar.edu.itba.pod.rmi.client;

public class ArgumentParser {
    public static String parseArgument( String[] args, ClientsArgsNames argName ) {
        String[] argument;
        String argumentName, argumentValue, finalArgument;
        int i;
        for(i = 0; i < args.length; i++) {
            argument = args[i].split("=");

            if( argument[0].startsWith("-")) {
                argumentName = argument[0];
                argumentValue = argument[1];
                System.out.println(args[i]);
                if (argumentName.equals(argName.getArgumentName())) {
                    if (argumentValue.startsWith("\'")) {
                        System.out.println("hecho");
                        finalArgument = argumentValue.substring(1);
                        argumentValue = args[i + 1];
                        while (!argumentValue.endsWith("'")) {
                            finalArgument = finalArgument.concat(" " + argumentValue);
                            i++;
                            argumentValue = args[i + 1];
                        }
                        if (argumentValue.endsWith("\'")) {
                            finalArgument = finalArgument.concat( " " + argumentValue.substring(0, argumentValue.length() - 2));
                        }
                        return finalArgument;
                    }
                    return argumentValue;
                }
            }
        }

        return null;
    }
}
