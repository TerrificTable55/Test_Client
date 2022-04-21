package xyz.terrifictable.util;

import org.lwjgl.input.Keyboard;
import xyz.terrifictable.Client;
import xyz.terrifictable.module.Module;
import xyz.terrifictable.setting.Setting;
import xyz.terrifictable.setting.settings.BooleanSetting;
import xyz.terrifictable.setting.settings.ModeSetting;
import xyz.terrifictable.setting.settings.NumberSetting;
import xyz.terrifictable.ui.MainMenu;
import xyz.terrifictable.ui.MainMenuOptions;

import java.io.*;

public class ConfigUtil {

    private File _ConfigFile;
    private File ConfigFile;

    public ConfigUtil() {
        this._ConfigFile = new File("Test");
        if (!this._ConfigFile.exists()) {
            this._ConfigFile.mkdirs();
        }

        this.ConfigFile = new File("Test" + File.separator + "Settings");
        if (!this.ConfigFile.exists()) {
            this.ConfigFile.mkdirs();
        }
    }

    public void saveConfig() {
        try {
            File menu_file = new File(this._ConfigFile.getAbsolutePath(),  "Menu.txt");
            BufferedWriter menu_out = new BufferedWriter(new FileWriter(menu_file));

            // THIS IS SHIT
            int image_index = -1, count = 0;
            for (String image : MainMenuOptions.images) {
                if (image.equalsIgnoreCase(MainMenu.image)) {
                    image_index = count;
                }
                count++;
            }

            menu_out.write("IMAGE:" + MainMenu.image);
            menu_out.write("\r\n");
            menu_out.write("IMAGE_INDEX:" + image_index);
            menu_out.write("\r\n");


            menu_out.close();

            for (Module module : Client.modules) {
                File file = new File(this.ConfigFile.getAbsolutePath(), module.name + ".txt");
                BufferedWriter out = new BufferedWriter(new FileWriter(file));

                out.write("toggled:" + (module.toggled ? "On" : "Off"));
                out.write("\r\n");

                out.write("keybind:" + Keyboard.getKeyName(module.getKey()));
                out.write("\r\n");

                for (Setting setting : module.settings) {
                    if (setting instanceof ModeSetting) {
                        out.write("S:MODE:" + setting.name + ":" + ((ModeSetting) setting).getMode());
                        out.write("\r\n");
                    }
                    if (setting instanceof BooleanSetting) {
                        out.write("S:BOOL:" + setting.name + ":" + (((BooleanSetting) setting).isEnabled() ? "On" : "Off"));
                        out.write("\r\n");
                    }
                    if (setting instanceof NumberSetting) {
                        out.write("S:NUM:" + setting.name + ":" + ((NumberSetting) setting).getValue());
                        out.write("\r\n");
                    }
                }

                out.close();
            }

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void loadConfig() {
        try {
            File menu_file = new File(this._ConfigFile.getAbsolutePath(),  "Menu.txt");
            FileInputStream menu_fstream = new FileInputStream(menu_file.getAbsolutePath());
            DataInputStream menu_in = new DataInputStream(menu_fstream);
            BufferedReader menu_br = new BufferedReader(new InputStreamReader(menu_in));

            String menu_line;
            while ((menu_line = menu_br.readLine()) != null) {
                String currline = menu_line.trim();
                String setting = currline.split(":")[0];

                if (setting.equalsIgnoreCase("image")) {
                    MainMenu.image = currline.split(":")[1];
                }
                if (setting.equalsIgnoreCase("image_index")) {
                    Client.image = Integer.parseInt(currline.split(":")[1]);
                }
            }

            for (Module module : Client.modules) {
                File file = new File(this.ConfigFile.getAbsolutePath(), module.name + ".txt");
                FileInputStream fstream = new FileInputStream(file.getAbsolutePath());
                DataInputStream in = new DataInputStream(fstream);
                BufferedReader br = new BufferedReader(new InputStreamReader(in));

                String line;
                while ((line = br.readLine()) != null) {
                    String curLine = line.trim();
                    String val_1 = curLine.split(":")[0];

                    if (val_1.equalsIgnoreCase("S")) {
                        String setting_type = curLine.split(":")[1];
                        String setting_name = curLine.split(":")[2];
                        String setting_value = curLine.split(":")[3];

                        for (Setting setting : module.settings) {
                            if (setting_type.equalsIgnoreCase("mode")) {
                                // === MODE SETTINGS ===
                                if ((setting instanceof ModeSetting) && setting.name.equalsIgnoreCase(setting_name)) {
                                    ((ModeSetting) setting).setMode(setting_value);
                                }
                            }
                            if (setting_type.equalsIgnoreCase("bool")) {
                                // === BOOLEAN SETTINGS ===
                                if ((setting instanceof BooleanSetting) && setting.name.equalsIgnoreCase(setting_name)) {
                                    boolean enabled;
                                    if (setting_value.equalsIgnoreCase("on"))
                                        enabled = true;
                                    else
                                        enabled = false;
                                    ((BooleanSetting) setting).enabled = enabled;
                                }
                            }
                            if (setting_type.equalsIgnoreCase("num")) {
                                // === NUMBER SETTINGS ===
                                if ((setting instanceof NumberSetting) && setting.name.equalsIgnoreCase(setting_name)) {
                                    ((NumberSetting) setting).setValue(Double.parseDouble(setting_value));
                                }
                            }
                        }
                    }
                    else {
                        String value = curLine.split(":")[1];

                        if (val_1.equalsIgnoreCase("toggled")) {
                            if (value.equalsIgnoreCase("on")) {
                                module.toggled = true;
                            } else {
                                module.toggled = false;
                            }
                        }

                        if (val_1.equalsIgnoreCase("keybind")) {
                            module.setKey(Keyboard.getKeyIndex(value));
                        }
                    }

                }
                br.close();
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
