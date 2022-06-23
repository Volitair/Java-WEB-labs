package com.example.javaweblabs.command;

import com.example.javaweblabs.command.abstraction.Command;

import java.util.HashMap;

public class CommandHandler {
    private final HashMap<String, Command> commands = new HashMap<>();

    public CommandHandler(HashMap<String, Command> commandMap) {
        commands.putAll(commandMap);
    }

    public Command getCommand(String commandName) {
        return commands.get(commandName);
    }
}
