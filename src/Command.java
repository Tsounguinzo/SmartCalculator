/*
 * Copyright (c) 2023 Beaudelaire Tsoungui Nzodoumkouo. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under My consent.
 *
 * This code is shared on GitHub in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY OF FITNESS FOR A PARTICULAR PURPOSE.
 *
 * Please contact Me at +1 438 509 3906
 * or LinkedIn: https://www.linkedin.com/in/beaudelaire-tsoungui-nzodoumkouo-809744231
 * if you need additional information or have any questions.
 */

package calculator;

import java.util.HashMap;
import java.util.Map;

public class Command {
    private final Map<String, String> commands = new HashMap<>();
    private static String commandMessage;
    private static String recentCommand;

    public Command(){
        commands.put("/exit", "Bye!");
        commands.put("/help", "The program calculates the sum of numbers");
    }

    public Command(String command, String message){
        if(command.length() >= 1 && command.charAt(0) != '/'){
            System.out.println('\'' + command + "' is an invalid command");
        } else {
            commands.put(command.toLowerCase(), message);
        }
    }

    public boolean isCommand(String command){
        if( command.length() >= 1 && command.charAt(0) == '/') {
            commandMessage = commands.getOrDefault(command.toLowerCase(), "Unknown command");
            recentCommand = command.toLowerCase();
            return true;
        } else {
            return false;
        }
    }

    public void getMessage(){
        System.out.println(commandMessage);
    }

    public boolean is(String command){
        return recentCommand.equalsIgnoreCase(command) && recentCommand != null;
    }

}
