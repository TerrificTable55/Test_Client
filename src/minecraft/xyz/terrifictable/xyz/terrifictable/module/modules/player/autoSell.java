package xyz.terrifictable.module.modules.player;

import org.lwjgl.input.Keyboard;
import xyz.terrifictable.events.Event;
import xyz.terrifictable.events.listeners.EventChatRecieve;
import xyz.terrifictable.module.Module;

public class autoSell extends Module {
    public autoSell() {
        super("AutoSell", Keyboard.KEY_P, Category.PLAYER);
    }

    public void onEvent(Event event) {
        if (!(event instanceof EventChatRecieve)) return;

        // TODO
    }
}
