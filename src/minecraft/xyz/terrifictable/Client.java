package xyz.terrifictable;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import org.lwjgl.opengl.Display;
import xyz.terrifictable.alt.AltManager;
import xyz.terrifictable.command.CommandManager;
import xyz.terrifictable.events.Event;
import xyz.terrifictable.events.listeners.EventChat;
import xyz.terrifictable.events.listeners.EventKey;
import xyz.terrifictable.module.Module;
import xyz.terrifictable.module.ModuleManager;
import xyz.terrifictable.module.modules.combat.AimAssist;
import xyz.terrifictable.module.modules.combat.FastBow;
import xyz.terrifictable.module.modules.combat.Killaura;
import xyz.terrifictable.module.modules.movement.*;
import xyz.terrifictable.module.modules.player.ChestStealer;
import xyz.terrifictable.module.modules.player.FakePlayer;
import xyz.terrifictable.module.modules.player.NoFall;
import xyz.terrifictable.module.modules.player.autoSell;
import xyz.terrifictable.module.modules.render.*;
import xyz.terrifictable.ui.Hud;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Client {

    public static String author = "TerrificTable";
    public static String name = "Test";
    public static String version = "1.2";
    public static String prefix = ">";

    public static CopyOnWriteArrayList<Module> modules = new CopyOnWriteArrayList<Module>();
    public static CommandManager commandManager = new CommandManager();
    public static ModuleManager moduleManager = new ModuleManager();
    public static AltManager altManager = new AltManager();
    public static Hud hud = new Hud();

    public static void startup() {
        Display.setTitle(name + " v" +version);

        // Combat
        addModule(new AimAssist());
        addModule(new Killaura());
        addModule(new FastBow());

        // Movement
        addModule(new AutoWalk());
        addModule(new Jetpack());
        addModule(new Dolphin());
        addModule(new Parkour());
        addModule(new Sprint());
        addModule(new Spider());
        addModule(new Glide());
        addModule(new Sneak());
        addModule(new Speed());
        addModule(new Step());
        addModule(new BHop());
        addModule(new Fly());

        // Player
        addModule(new ChestStealer());
        addModule(new FakePlayer());
        addModule(new NoFall());

        // Render
        addModule(new xyz.terrifictable.module.modules.render.ArrayList());
        addModule(new xyz.terrifictable.module.modules.render.Hud());
        addModule(new Fulbright());
        addModule(new TabGui());
        addModule(new ESP());
    }

    public static void addModule(Module module) {
        modules.add(module);
    }
    public static void onEvent(Event event) {
        if (event instanceof EventChat) {
            commandManager.handleChat((EventChat) event);
        }
        for (Module module : modules) {
            if (module.isToggled())
                module.onEvent(event);
        }
    }
    public static void keyPress(int key) {
        Client.onEvent(new EventKey(key));

        for (Module module : modules) {
            if (module.getKey() == key)
                module.toggle();
        }
    }
    public static List<Module> getModulesByCategory(Module.Category category) {
        List<Module> modules = new ArrayList<Module>();

        for (Module module : Client.modules) {
            if (module.category == category) {
                modules.add(module);
            }
        }

        return modules;
    }
    public static Module getModuleByName(String module_name) {
        for (Module module : modules) {
            if (module.name.equalsIgnoreCase(module_name)) {
                return module;
            }
        }
        return null;
    }
    public static void addChatmessage(String message) {
        message = "\u00A77[\u00A7c" + Client.name + "\u00A77]  \u00A7f" + message;

        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(message));
    }
}
