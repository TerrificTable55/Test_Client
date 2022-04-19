package xyz.terrifictable.command;

import xyz.terrifictable.Client;
import xyz.terrifictable.command.commands.*;
import xyz.terrifictable.events.listeners.EventChat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandManager {

    public static List<Command> commands = new ArrayList<Command>();

    public CommandManager() {
        setup();
    }

    public void setup() {
        addCommand(new addXrayBlock());
        addCommand(new Commands());
        addCommand(new Modules());
        addCommand(new Prefix());
        addCommand(new Toggle());
        addCommand(new Bind());
        addCommand(new Info());
        addCommand(new Say());
    }

    public void addCommand(Command command) {
        commands.add(command);
    }
    public void handleChat(EventChat event) {
        String message = event.getMessage();

        if (!message.startsWith(Client.prefix))
            return;

        event.setCancelled(true);
        message = message.substring(Client.prefix.length());

        if (message.split(" ").length > 0) {
            String commandName = message.split(" ")[0];

            boolean found = false;

            for (Command command : commands) {
                if (command.aliases.contains(commandName) || command.name.equalsIgnoreCase(commandName)) {
                    command.onCommand(Arrays.copyOfRange(message.split(" "), 1, message.split(" ").length), message);
                    found = true;
                }
            }
            if (!found)
                Client.addChatMessage("Command '\u00A7b" + commandName + "\u00A7f' not found try \u00A76" + Client.prefix + "commands \u00A7ffor a list of all commands");
        }
    }
}
