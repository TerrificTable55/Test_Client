package xyz.terrifictable.command.commands;

import xyz.terrifictable.Client;
import xyz.terrifictable.command.Command;

public class Prefix extends Command {
    public Prefix() {
        super("prefix", "Changes command prefix", "", "");
        this.setSyntax(Client.prefix + this.getName() + " [NEW_PREFIX]");
    }

    @Override
    public void onCommand(String[] args, String command) {
        Client.prefix = args[0];
        Client.addChatmessage("New Prefix: \u00A7a" + args[0]);
    }
}
