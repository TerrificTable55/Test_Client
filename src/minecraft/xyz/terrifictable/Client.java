package xyz.terrifictable;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import org.lwjgl.opengl.Display;
import xyz.terrifictable.alt.AltManager;
import xyz.terrifictable.command.CommandManager;
import xyz.terrifictable.events.Event;
import xyz.terrifictable.events.listeners.EventChat;
import xyz.terrifictable.events.listeners.EventKey;
import xyz.terrifictable.friend.FriendManager;
import xyz.terrifictable.module.Module;
import xyz.terrifictable.module.ModuleManager;
import xyz.terrifictable.module.modules.client.ClickGuiModule;
import xyz.terrifictable.module.modules.client.ClickGuiRender;
import xyz.terrifictable.module.modules.client.ModuleList;
import xyz.terrifictable.module.modules.client.TabGui;
import xyz.terrifictable.module.modules.combat.*;
import xyz.terrifictable.module.modules.movement.*;
import xyz.terrifictable.module.modules.player.*;
import xyz.terrifictable.module.modules.render.*;
import xyz.terrifictable.ui.Hud;
import xyz.terrifictable.util.ConfigUtil;
import xyz.terrifictable.util.font.FontUtil;
import xyz.terrifictable.util.font.MinecraftFontRenderer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Client {

    public static String author = "TerrificTable";
    public static String name = "Test";
    public static String version = "1.7";
    public static String prefix = ">";

    public static CopyOnWriteArrayList<Module> modules = new CopyOnWriteArrayList<Module>();
    public static CommandManager commandManager = new CommandManager();
    public static FriendManager friendManager = new FriendManager();
    public static ModuleManager moduleManager = new ModuleManager();
    public static ClickGuiRender clickgui = new ClickGuiRender();
    public static ConfigUtil configManager = new ConfigUtil();
    public static AltManager altManager = new AltManager();
    public static MinecraftFontRenderer fr;
    public static Hud hud = new Hud();

    public static int image = 0;
    public static int layout = 0;

    public static void startup() {
        Display.setTitle(name + " v" +version);

        FontUtil.bootstrap();
        fr = FontUtil.normal;

        // Client
        addModule(new ClickGuiModule());
        addModule(new TabGui());

        // Combat
        // addModule(new TargetStrafe());
        addModule(new AimAssist());
        addModule(new Criticals());
        addModule(new AutoArmor());
        addModule(new Killaura());
        addModule(new AntiBot());
        addModule(new FastBow());

        // Movement
        addModule(new AutoWalk());
        addModule(new Velocity());
        addModule(new Jetpack());
        addModule(new Dolphin());
        addModule(new Parkour());
        addModule(new Sprint());
        addModule(new Spider());
        addModule(new Glide());
        addModule(new Sneak());
        addModule(new Phase());
        addModule(new Speed());
        addModule(new Step());
        addModule(new BHop());
        addModule(new Fly());

        // Player
        addModule(new InventoryManager());
        addModule(new ChestStealer());
        addModule(new AutoRespawn());
        addModule(new NewScaffold());
        addModule(new AntiCobweb());
        addModule(new FakePlayer());
        addModule(new FastLadder());
        addModule(new FastPlace());
        addModule(new AutoTool());
        addModule(new AutoMine());
        addModule(new AntiVoid());
        addModule(new AntiAfk());
        addModule(new NoFall());

        // Render
        addModule(new xyz.terrifictable.module.modules.render.Hud());
        addModule(new ModuleList());
        addModule(new TargetHud());
        addModule(new Fulbright());
        addModule(new NewESP());
        addModule(new Xray());
        // addModule(new ESP());

        configManager.loadConfig();
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
    public static void addChatMessage(String message) {
        message = "\u00A77[\u00A7c" + Client.name + "\u00A77]  \u00A7f" + message;
        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(message));
    }
    public static ClickGuiRender getClickgui() {
        return clickgui;
    }
}
