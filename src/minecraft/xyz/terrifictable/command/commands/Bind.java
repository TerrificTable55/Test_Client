package xyz.terrifictable.command.commands;

import org.lwjgl.input.Keyboard;
import xyz.terrifictable.Client;
import xyz.terrifictable.command.Command;
import xyz.terrifictable.module.Module;

public class Bind extends Command {
    public Bind() {
        super("bind", "Binds a key to a module", "", "b");
        this.setSyntax(Client.prefix + this.getName().toLowerCase() + " set [MODULE] [KEY]  /  del  /  clear");
    }

    @Override
    public void onCommand(String[] args, String command) {
        if (args.length > 0) {
            if (args[0].equalsIgnoreCase("set")) {
                if (args.length == 3) {
                    String moduleName = args[1];
                    String keyName = args[2];

                    boolean found = false;

                    for (Module module : Client.modules) {
                        if (module.name.equalsIgnoreCase(moduleName)) {
                            module.keyCode.setKeyCode(Keyboard.getKeyIndex(keyName.toUpperCase()));
                            found = true;

                            Client.addChatMessage(String.format("\u00A7b%s\u00A7f Bound to \u00A7a%s", module.name, keyName.toUpperCase()));
                            break;
                        }
                    }

                    if (!found)
                        Client.addChatMessage(String.format("Module \u00A7b'%s'\u00A7f not found, try \u00A76%smodules \u00A7ffor a list of modules", args[0], Client.prefix));
                }
            } else if (args[0].equalsIgnoreCase("del")) {
                String moduleName = args[1];

                boolean found = false;

                for (Module module : Client.modules) {
                    if (module.name.equalsIgnoreCase(moduleName)) {
                        module.keyCode.setKeyCode(0);
                        found = true;

                        Client.addChatMessage("Successfully Unbound \u00A7b" + module.name);
                        break;
                    }
                }

                if (!found)
                    Client.addChatMessage(String.format("Module \u00A7b'%s'\u00A7f not found, try \u00A76%smodules \u00A7ffor a list of modules", args[0], Client.prefix));

            } else if (args[0].equalsIgnoreCase("clear")) {
                for (Module module : Client.modules) {
                    module.keyCode.setKeyCode(0);
                }

                Client.addChatMessage("Removed all Keybinds");
            } else
                Client.addChatMessage(this.getSyntax());
        } else
            Client.addChatMessage(this.getSyntax());
    }
}
