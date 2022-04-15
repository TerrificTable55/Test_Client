package xyz.terrifictable.command.commands;

import xyz.terrifictable.Client;
import xyz.terrifictable.command.Command;
import xyz.terrifictable.command.CommandManager;

public class Commands extends Command {
    public Commands() {
        super("commands", "Returns a list of all commands", "", "");
        this.setSyntax(Client.prefix + this.getName());
    }

    @Override
    public void onCommand(String[] args, String cmd) {
        StringBuilder commands = new StringBuilder();

        for (Command command : CommandManager.commands) {
            commands.append("\u00A7c").append(command.name).append("\u00A7f, ");
        }

        Client.addChatmessage("Commands: " + commands);
    }
}
