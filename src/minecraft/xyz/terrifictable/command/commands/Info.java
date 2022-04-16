package xyz.terrifictable.command.commands;

import xyz.terrifictable.Client;
import xyz.terrifictable.command.Command;

public class Info extends Command {
    public Info() {
        super("info", "Info about the client", "", "i");
        this.setSyntax(Client.prefix + this.getName());
    }

    @Override
    public void onCommand(String[] args, String command) {
        Client.addChatmessage("\u00A7a" + Client.name + " \u00A7ev" + Client.version + "\u00A7f by " + Client.author);
    }
}
