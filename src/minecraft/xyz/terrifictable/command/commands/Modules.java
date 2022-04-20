package xyz.terrifictable.command.commands;

import xyz.terrifictable.Client;
import xyz.terrifictable.command.Command;
import xyz.terrifictable.module.Module;

public class Modules extends Command {
    public Modules() {
        super("modules", "Returns a list of all commands", "", "");
        this.setSyntax(Client.prefix + this.getName());
    }

    @Override
    public void onCommand(String[] args, String cmd) {
        StringBuilder modules = new StringBuilder();

        for (Module module : Client.modules) {
            if (module.name.equalsIgnoreCase("tabgui") || module.name.equalsIgnoreCase("arraylist")) {
                continue;
            }
            modules.append("\u00A7c").append(module.name).append("\u00A7f, ");
        }

        Client.addChatMessage("Modules: " + modules);
    }
}
