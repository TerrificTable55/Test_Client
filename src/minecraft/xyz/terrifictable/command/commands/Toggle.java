package xyz.terrifictable.command.commands;

import xyz.terrifictable.Client;
import xyz.terrifictable.command.Command;
import xyz.terrifictable.module.Module;

public class Toggle extends Command {
    public Toggle() {
        super("toggle", "Toggles a module", "", "toggle", "t");
        this.setSyntax(Client.prefix + this.getName() + " [MODULE]");
    }

    @Override
    public void onCommand(String[] args, String command) {
        if (args.length > 0) {
            String moduleName = args[0];

            boolean found = false;

            for (Module module : Client.modules) {
                if (module.name.equalsIgnoreCase(moduleName)) {
                    module.toggle();

                    Client.addChatMessage("\u00A7b" + module.name + "\u00A7f Toggled " + (module.isToggled() ? "\u00A72On" : "\u00A74Off"));

                    found = true;
                    break;
                }
            }

            if (!found) {
                Client.addChatMessage("Module \u00A7b'" + args[0] + "'\u00A7f not found, try \u00A76" + Client.prefix + "modules \u00A7ffor a list of modules");
            }
        }
    }
}
