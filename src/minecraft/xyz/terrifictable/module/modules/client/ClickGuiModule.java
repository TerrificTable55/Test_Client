package xyz.terrifictable.module.modules.client;

import org.lwjgl.input.Keyboard;
import xyz.terrifictable.Client;
import xyz.terrifictable.module.Module;

public class ClickGuiModule extends Module {

    public ClickGuiModule() {
        super("ClickGui", Keyboard.KEY_RSHIFT, Category.CLIENT);
        addSettings();
    }

    @Override
    public void onEnable() {
        mc.displayGuiScreen(Client.getClickgui());

        this.toggled = false;

        super.onEnable();
    }
}
