package xyz.terrifictable.module;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import xyz.terrifictable.events.Event;
import xyz.terrifictable.setting.Setting;
import xyz.terrifictable.setting.settings.KeybindSetting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Module {

    public String name;
    public KeybindSetting keyCode = new KeybindSetting(0);
    public boolean toggled;
    public Category category;
    public Minecraft mc = Minecraft.getMinecraft();

    public boolean expanded;
    public int settingIndex;
    public List<Setting> settings = new ArrayList<Setting>();

    public Module(String name, int key, Category category) {
        this.name = name;
        keyCode.code = key;
        this.category = category;
        this.addSettings(keyCode);
    }

    public void onEnable() {}
    public void onDisable() {}
    public void onUpdate() {}
    public void onRender() {}
    public void onEvent(Event event) {}

    public void addSettings(Setting... settings) {
        this.settings.addAll(Arrays.asList(settings));
        this.settings.sort(Comparator.comparingInt(s -> s == keyCode ? 1 : 0));
    }

    public boolean isToggled() {
        return toggled;
    }
    public void setToggled(boolean state) {
        toggled = state;
    }
    public void toggle() {
        toggled = !toggled;

        if (toggled) onEnable();
        else onDisable();
    }
    public int getKey() {
        return keyCode.code;
    }
    public void setKey(int key) {
        keyCode.code = key;
    }
    public enum Category {
        COMBAT("Combat"),
        MOVEMENT("Movement"),
        PLAYER("Player"),
        RENDER("Render"),
        CLIENT("Client");

        public String name;

        Category(String name) {
            this.name = name;
        }
    }
}
