package xyz.terrifictable.setting.settings;

import xyz.terrifictable.setting.Setting;

public class KeybindSetting extends Setting {

    public int code;

    public KeybindSetting(int key) {
        this.name = "Keybind";
        this.code = key;
    }

    public int getKeyCode() {
        return code;
    }
    public void setKeyCode(int keyCode) {
        this.code = keyCode;
    }
}
