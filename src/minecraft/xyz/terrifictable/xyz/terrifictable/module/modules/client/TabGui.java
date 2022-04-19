package xyz.terrifictable.module.modules.client;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;
import xyz.terrifictable.Client;
import xyz.terrifictable.events.Event;
import xyz.terrifictable.events.listeners.EventKey;
import xyz.terrifictable.events.listeners.EventRenderGui;
import xyz.terrifictable.events.listeners.EventUpdate;
import xyz.terrifictable.module.Module;
import xyz.terrifictable.setting.Setting;
import xyz.terrifictable.setting.settings.BooleanSetting;
import xyz.terrifictable.setting.settings.KeybindSetting;
import xyz.terrifictable.setting.settings.ModeSetting;
import xyz.terrifictable.setting.settings.NumberSetting;
import xyz.terrifictable.util.ColorUtil;

import java.awt.*;
import java.util.List;

public class TabGui extends Module {
    public int currentTab, moduleIndex;
    public boolean expanded;

    // public NumberSetting hue = new NumberSetting("Hue", 0, 0, 360, 1);
    // public NumberSetting saturation = new NumberSetting("Saturation", 100, 0, 100, 1);
    // public NumberSetting brightness = new NumberSetting("Brightness", 100, 0, 100, 1);
    public NumberSetting rainbowDelay = new NumberSetting("RainbowSpeed", 4, 1, 15, 1);
    public NumberSetting rainbowSaturation = new NumberSetting("Rainbow Sat", 1, 0, 1, 0.1);
    public NumberSetting rainbowBrightness = new NumberSetting("Rainbow Bright", 1, 0, 1, 0.1);
    public BooleanSetting rainbow = new BooleanSetting("Rainbow", false);

    public TabGui() {
        super("TabGui", Keyboard.KEY_NONE, Category.CLIENT);
        this.addSettings(rainbow, rainbowSaturation, rainbowBrightness, rainbowDelay); // hue, saturation, brightness,
        toggled = true;
    }

    public void onEvent(Event event) {
        List<Module> modules = Client.getModulesByCategory(Module.Category.values()[currentTab]);

        if (event instanceof EventRenderGui) {
            FontRenderer fr = mc.fontRendererObj;

            float seconds = (float) rainbowDelay.getValue();
            int color = ColorUtil.getRainbow(seconds, (float) rainbowSaturation.getValue(), (float) rainbowBrightness.getValue());

            int primaryColor;
            int secondaryColor;
            if (rainbow.enabled) {
                primaryColor = color;
                secondaryColor = ColorUtil.getRainbow(seconds, (float) rainbowSaturation.getValue(), (float) rainbowBrightness.getValue() / 2F);
            } else {
                primaryColor = 0xffff0000;
                secondaryColor = 0xffd0021b;
            }

            // === CATEGORYS ===
            // 0xff0f00ff   0xff0090ff
            Gui.drawRect(2, 35, 57, 35 + Category.values().length * 12.5, 0x90000000);

            // Could make a config for entire text and small lines
            // Gui.drawRect(4, 37 + currentTab * 12, 55, 34 + currentTab * 12 + 12.5, primaryColor); // ENTIRE TEXT / space on sides
            Gui.drawRect(4 - 2, 37 + currentTab * 12, 55 + 2, 34 + currentTab * 12 + 12.5, primaryColor); // ENTIRE TEXT / no space on sides
            // Gui.drawRect(4, 37.5 + currentTab * 12, 6, 33 + currentTab * 12 + 12.5, primaryColor); // SMALL LINE

            int count = 0;
            for (Category category : Module.Category.values()) {
                fr.drawString(category.name, 8, 38 + (count * 12), -1);
                count++;
            }


            // === MODULES ===
            if (expanded) {
                if (modules.size() == 0) { expanded = false; return; }

                int index_m = 0, maxLen_m = 0;
                for (Module module : modules) {
                    if (fr.getStringWidth(module.name) > maxLen_m)
                        maxLen_m = fr.getStringWidth(module.name);
                    index_m++;
                }

                Gui.drawRect(60, 35, 57 + maxLen_m + 12, 35 + modules.size() * 12.3, 0x90000000);
                Gui.drawRect(63, 37 + moduleIndex * 12, 55 + maxLen_m + 12, 34 + moduleIndex * 12 + 13, primaryColor); // ENTIRE TEXT / space on sides
                // Gui.drawRect(62 -2, 37 + moduleIndex * 12, 55 + 57 + 2, 34 + moduleIndex * 12 + 13, primaryColor); // ENTIRE TEXT / no space on sides
                // Gui.drawRect(62, 37 + moduleIndex * 12, 64, 34 + moduleIndex * 12 + 13, primaryColor); // SMALL LINE

                count = 0;
                for (Module module : modules) {
                    if (module.isToggled()) {
                        fr.drawString(module.name, 65, 38 + (count * 12), 0xff00ff00);
                    } else
                        fr.drawString(module.name, 65, 38 + (count * 12), -1);

                    if (count == moduleIndex && module.expanded) {

                        // === SETTINGS ===
                        int index = 0, maxLen = 0;
                        for (Setting setting : module.settings) {
                            if (setting instanceof BooleanSetting) {
                                BooleanSetting bool = (BooleanSetting) setting;
                                if (maxLen < fr.getStringWidth(setting.name + ": " + (bool.enabled ? "on" : "off")))
                                    maxLen = fr.getStringWidth(setting.name + ": " + (bool.enabled ? "on" : "off"));
                            } else if (setting instanceof NumberSetting) {
                                NumberSetting number = (NumberSetting) setting;
                                if (maxLen < fr.getStringWidth(setting.name + ": " + number.getValue()))
                                    maxLen = fr.getStringWidth(setting.name + ": " + number.getValue());
                            } else if (setting instanceof ModeSetting) {
                                ModeSetting mode = (ModeSetting) setting;
                                if (maxLen < fr.getStringWidth(setting.name + ": " + mode.getMode()))
                                    maxLen = fr.getStringWidth(setting.name + ": " + mode.getMode());
                            } else if (setting instanceof KeybindSetting) {
                                KeybindSetting keyBind = (KeybindSetting) setting;
                                if (maxLen < fr.getStringWidth(setting.name + ": " + Keyboard.getKeyName(keyBind.code)))
                                    maxLen = fr.getStringWidth(setting.name + ": " + Keyboard.getKeyName(keyBind.code));
                            }
                            index++;
                        }

                        if (!module.settings.isEmpty()) {
                            Gui.drawRect(60 + maxLen_m + 9, 35, 57 + 24 + maxLen_m + maxLen - 2, 35 + module.settings.size() * 12.3, 0x90000000);
                            Gui.drawRect(62 + maxLen_m + 9, 37 + module.settingIndex * 12, 55 + 24 + maxLen_m + maxLen - 2, 34 + module.settingIndex * 12 + 13, module.settings.get(module.settingIndex).focused ? secondaryColor : primaryColor); // ENTIRE TEXT / space on sides
                            // Gui.drawRect(60 + maxLen_m + 9, 35, module.settingIndex * 12, 55 + 57 + 57 + 2, 34 + module.settingIndex * 12 + 13, primaryColor); // ENTIRE TEXT / no space on sides
                            // Gui.drawRect(62 + maxLen_m + 12, 37, module.settingIndex * 12, 64 + 34 + module.settingIndex * 12 + 13, primaryColor); // SMALL LINE

                            index = 0;
                            for (Setting setting : module.settings) {
                                if (setting instanceof BooleanSetting) {
                                    BooleanSetting bool = (BooleanSetting) setting;
                                    fr.drawString(setting.name + ": " + (bool.enabled ? "on" : "off"), 65 + maxLen_m + 9f, 38 + (index * 12), -1);
                                } else if (setting instanceof NumberSetting) {
                                    NumberSetting number = (NumberSetting) setting;
                                    fr.drawString(setting.name + ": " + number.getValue(), 65 + maxLen_m + 9f, 38 + (index * 12), -1);
                                } else if (setting instanceof ModeSetting) {
                                    ModeSetting mode = (ModeSetting) setting;
                                    fr.drawString(setting.name + ": " + mode.getMode(), 65 + maxLen_m + 9f, 38 + (index * 12), -1);
                                } else if (setting instanceof KeybindSetting) {
                                    KeybindSetting keyBind = (KeybindSetting) setting;
                                    fr.drawString(setting.name + ": " + Keyboard.getKeyName(keyBind.code), 65 + maxLen_m + 9f, 38 + (index * 12), -1);
                                }
                                index++;
                            }
                        }
                    }

                    count++;
                }
            }
        }

        if (event instanceof EventKey) {
            int keyCode = ((EventKey)event).key;

            if (expanded && !modules.isEmpty() && modules.get(moduleIndex).expanded) {
                Module module = modules.get(moduleIndex);

                if (!module.settings.isEmpty() && module.settings.get(module.settingIndex).focused && module.settings.get(module.settingIndex) instanceof KeybindSetting) {
                    if (keyCode != Keyboard.KEY_RETURN && keyCode != Keyboard.KEY_UP && keyCode != Keyboard.KEY_DOWN && keyCode != Keyboard.KEY_LEFT && keyCode != Keyboard.KEY_RIGHT && keyCode != Keyboard.KEY_ESCAPE) {
                        KeybindSetting keybind = (KeybindSetting) module.settings.get(module.settingIndex);

                        keybind.code = keyCode;
                        keybind.focused = false;

                        return;
                    }
                }
            }

            if (keyCode == Keyboard.KEY_UP)
                if (expanded) {
                    if (expanded && !modules.isEmpty() && modules.get(moduleIndex).expanded) {
                        Module module = modules.get(moduleIndex);

                        if (!module.settings.isEmpty()) {
                            if (module.settings.get(module.settingIndex).focused) {
                                Setting setting = module.settings.get(module.settingIndex);

                                if (setting instanceof NumberSetting) {
                                    ((NumberSetting)setting).increment(true);
                                }
                            } else {
                                if (module.settingIndex <= 0)
                                    module.settingIndex = module.settings.size() - 1;
                                else
                                    module.settingIndex--;
                            }
                        }
                    } else
                        if (moduleIndex <= 0)
                            moduleIndex = modules.size() - 1;
                        else
                            moduleIndex--;
                } else {
                    if (currentTab <= 0)
                        currentTab = Category.values().length - 1;
                    else
                        currentTab--;
                }

            if (keyCode == Keyboard.KEY_DOWN) {
                if (expanded) {
                    if (expanded && !modules.isEmpty() && modules.get(moduleIndex).expanded) {
                        Module module = modules.get(moduleIndex);

                        if (!module.settings.isEmpty()) {
                            if (module.settings.get(module.settingIndex).focused) {
                                Setting setting = module.settings.get(module.settingIndex);

                                if (setting instanceof NumberSetting) {
                                    ((NumberSetting)setting).increment(false);
                                }
                            } else {
                                if (module.settingIndex >= module.settings.size() -1 )
                                    module.settingIndex = 0;
                                else
                                    module.settingIndex++;
                            }
                        }
                    } else {
                        if (moduleIndex >= modules.size() -1 )
                            moduleIndex = 0;
                        else
                            moduleIndex++;
                    }
                } else {
                    if (currentTab >= Category.values().length - 1)
                        currentTab = 0;
                    else
                        currentTab++;
                }
            }

            if (keyCode == Keyboard.KEY_RETURN) {
                if (expanded && modules.size() != 0) {
                    Module module = modules.get(moduleIndex);

                    if (module.expanded) {
                        if (!module.settings.isEmpty()) {
                            Setting setting = module.settings.get(module.settingIndex);

                            if (setting instanceof BooleanSetting) {
                                ((BooleanSetting)setting).toggle();
                            } else if (setting instanceof ModeSetting) {
                                ((ModeSetting)setting).cycle();
                            } else {
                                module.settings.get(module.settingIndex).focused = !module.settings.get(module.settingIndex).focused;
                            }
                        }
                    } else {
                        if (!modules.get(moduleIndex).name.equalsIgnoreCase("tabgui") || !modules.get(moduleIndex).name.equalsIgnoreCase("arraylist"))
                            if (modules.get(moduleIndex).expanded) {

                            } else
                                modules.get(moduleIndex).toggle();
                    }

                }
            }

            if (keyCode == Keyboard.KEY_RIGHT) {
                if (expanded && !modules.isEmpty()) {
                    Module module = modules.get(moduleIndex);

                    if (!module.settings.isEmpty()) {
                        if (module.expanded) {

                        } else {
                            module.expanded = true;
                        }
                    }
                } else {
                    if (!expanded  && !modules.isEmpty()) {
                        moduleIndex = 0;
                    }
                    expanded = true;
                }
            }

            if (keyCode == Keyboard.KEY_LEFT) {
                Module module = modules.get(moduleIndex);

                if (!module.settings.isEmpty() && module.settings.get(module.settingIndex).focused) {

                } else {
                    if (expanded && !modules.isEmpty() && modules.get(moduleIndex).expanded)
                        modules.get(moduleIndex).expanded = false;
                    else
                        expanded = false;
                }
            }
        }
    }
}
