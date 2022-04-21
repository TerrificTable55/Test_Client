package xyz.terrifictable.setting.settings;

import xyz.terrifictable.setting.Setting;

import java.util.Arrays;
import java.util.List;

public class ModeSetting extends Setting {

    public int index;
    public List<String> modes;

    public ModeSetting(String name, String defaultMode, String... modes) {
        this.name = name;
        this.modes = Arrays.asList(modes);
        this.index = this.modes.indexOf(defaultMode);
    }

    public String getMode() {
        return modes.get(index);
    }
    public void setMode(String mode) {
        index = modes.indexOf(mode);
    }
    public boolean is(String mode) {
        return index == modes.indexOf(mode);
    }
    public void cycle() {
        if (index < modes.size() - 1)
            index++;
        else index = 0;
    }
}
