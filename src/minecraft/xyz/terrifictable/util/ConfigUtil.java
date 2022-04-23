package xyz.terrifictable.util;

import org.lwjgl.input.Keyboard;
import xyz.terrifictable.Client;
import xyz.terrifictable.alt.Alt;
import xyz.terrifictable.alt.AltManager;
import xyz.terrifictable.friend.Friend;
import xyz.terrifictable.module.Module;
import xyz.terrifictable.setting.Setting;
import xyz.terrifictable.setting.settings.BooleanSetting;
import xyz.terrifictable.setting.settings.ModeSetting;
import xyz.terrifictable.setting.settings.NumberSetting;
import xyz.terrifictable.ui.MainMenu;

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
            File alt_file = new File(this._ConfigFile.getAbsolutePath(),  "Alts.txt");
            BufferedWriter alt_out = new BufferedWriter(new FileWriter(alt_file));

            if (AltManager.registry.size() > 0) {
                for (Alt alt : AltManager.registry) {
                    // Rot13
                    // String username = EncryptionUtil.Rot_Encrypt(alt.getUsername(), 18);
                    // String password = EncryptionUtil.Rot_Encrypt(alt.getPassword(), 16);

                    String username = EncryptionUtil.Base85_Encrypt(alt.getUsername().getBytes());

                    if (alt.getPassword().equalsIgnoreCase("")) {
                        alt_out.write(username + ":" + "alt.offline");
                    }
                    else {
                        String password = EncryptionUtil.Base85_Encrypt(alt.getPassword().getBytes());
                        alt_out.write(username + ":" + password);
                    }
                    alt_out.write("\r\n");
                }
            }
            alt_out.close();


            File menu_file = new File(this._ConfigFile.getAbsolutePath(),  "Menu.txt");
            BufferedWriter menu_out = new BufferedWriter(new FileWriter(menu_file));

            // THIS IS SHIT
            // int image_index = -1, count = 0;
            // for (String image : MainMenuOptions.images) {
            //     if (image.equalsIgnoreCase(MainMenu.image)) {
            //         image_index = count;
            //     }
            //     count++;
            // }

            menu_out.write("layout:" + MainMenu.layout);
            menu_out.write("\r\n");
            menu_out.write("layout_index:" + Client.layout);
            menu_out.write("\r\n");
            menu_out.write("image:" + MainMenu.image);
            menu_out.write("\r\n");
            menu_out.write("image_index:" + Client.image);
            menu_out.write("\r\n");


            menu_out.close();


            File friend_file = new File(this._ConfigFile.getAbsolutePath(),  "Friends.txt");
            BufferedWriter friend_out = new BufferedWriter(new FileWriter(friend_file));

            for (Friend friend : Client.friendManager.getContents()) {
                friend_out.write(friend.getName() + ":" + friend.getAlias());
                friend_out.write("\r\n");
            }
            friend_out.close();


            for (Module module : Client.modules) {
                File file = new File(this.ConfigFile.getAbsolutePath(), module.name + ".txt");
                BufferedWriter out = new BufferedWriter(new FileWriter(file));

                if (!module.name.equalsIgnoreCase("fakeplayer")) {
                    out.write("toggled:" + (module.toggled ? "On" : "Off"));
                    out.write("\r\n");
                }
                if (module.name.equalsIgnoreCase("clickgui")) {
                    out.write("toggled:Off");
                    out.write("\r\n");
                }

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
            File alt_file = new File(this._ConfigFile.getAbsolutePath(),  "Alts.txt");
            FileInputStream alt_fstream = new FileInputStream(alt_file.getAbsolutePath());
            DataInputStream alt_in = new DataInputStream(alt_fstream);
            BufferedReader alt_br = new BufferedReader(new InputStreamReader(alt_in));

            String alt_line;
            while ((alt_line = alt_br.readLine()) != null) {

                String currline = alt_line.trim();
                String username = alt_line.split(":")[0];
                String password = alt_line.split(":")[1];

                // ROT
                // username = EncryptionUtil.Rot_Decrypt(username, 18);
                // password = EncryptionUtil.Rot_Decrypt(password, 16);

                username = new String(EncryptionUtil.Base85_Decrypt(username));

                if (password.equalsIgnoreCase("alt.offline")) {
                    AltManager.registry.add(new Alt(username, ""));
                }
                else {
                    password = EncryptionUtil.Base85_Encrypt(password.getBytes());
                    AltManager.registry.add(new Alt(username, password));
                }
            }
            alt_br.close();


            File menu_file = new File(this._ConfigFile.getAbsolutePath(),  "Menu.txt");
            FileInputStream menu_fstream = new FileInputStream(menu_file.getAbsolutePath());
            DataInputStream menu_in = new DataInputStream(menu_fstream);
            BufferedReader menu_br = new BufferedReader(new InputStreamReader(menu_in));

            String menu_line;
            while ((menu_line = menu_br.readLine()) != null) {
                String currline = menu_line.trim();
                String setting = currline.split(":")[0];

                if (setting.equalsIgnoreCase("layout")) {
                    MainMenu.layout = currline.split(":")[1];
                }
                if (setting.equalsIgnoreCase("layout_index")) {
                    Client.layout = Integer.parseInt(currline.split(":")[1]);
                }
                if (setting.equalsIgnoreCase("image")) {
                    MainMenu.image = currline.split(":")[1];
                }
                if (setting.equalsIgnoreCase("image_index")) {
                    Client.image = Integer.parseInt(currline.split(":")[1]);
                }
            }
            menu_br.close();


            File friend_file = new File(this._ConfigFile.getAbsolutePath(),  "Friends.txt");
            FileInputStream friend_fstream = new FileInputStream(friend_file.getAbsolutePath());
            DataInputStream friend_in = new DataInputStream(friend_fstream);
            BufferedReader friend_br = new BufferedReader(new InputStreamReader(friend_in));

            String friend_line;
            while ((friend_line = friend_br.readLine()) != null) {
                String currline = menu_line.trim();
                String[] friend = currline.split(":");

                Client.friendManager.addFriend(friend[0], friend[1]);
            }
            friend_br.close();


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
