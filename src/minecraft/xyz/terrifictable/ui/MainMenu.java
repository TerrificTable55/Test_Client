package xyz.terrifictable.ui;

import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import xyz.terrifictable.Client;
import xyz.terrifictable.alt.GuiAltManager;
import xyz.terrifictable.setting.settings.ModeSetting;

public class MainMenu extends GuiScreen {
    private static final String[] buttons = {"Singleplayer", "Multiplayer", "AltManager", "MenuOptions", "Settings", "Quit"};
    public static String image = "sea.jpg";

    public MainMenu() {
    }

    public void initGui() {

    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        assert Client.getModuleByName("MainMenu") != null;
        /* if (Client.getModuleByName("MainMenu").settings.get(0).name.equalsIgnoreCase("image")) {
            ModeSetting image = (ModeSetting) Client.getModuleByName("MainMenu").settings.get(0);

            if (image.getMode().equalsIgnoreCase("galaxy"))
                mc.getTextureManager().bindTexture(new ResourceLocation("test/galaxy.jpg"));
            if (image.getMode().equalsIgnoreCase("Mountain"))
                mc.getTextureManager().bindTexture(new ResourceLocation("test/background.jpg"));
            if (image.getMode().equalsIgnoreCase("Snowy Mountain"))
                mc.getTextureManager().bindTexture(new ResourceLocation("test/snowy_mountain.jpg"));
        } */

        mc.getTextureManager().bindTexture(new ResourceLocation("test/" + image));
        drawModalRectWithCustomSizedTexture(0, 0 , 0, 0, this.width, this.height, this.width, this.height);

        drawGradientRect(0, height - 100, width, height, 0x00000000, 0xff000000);

        int count = 0;
        for (String name : buttons) {
            float x = (width / buttons.length) * count + (width / buttons.length) / 2F + 8 - mc.fontRendererObj.getStringWidth(name) / 2F;
            float y = height - 20;

            boolean hovered = (mouseX >= x && mouseY >= y && mouseX < x + mc.fontRendererObj.getStringWidth(name) && mouseY < y + mc.fontRendererObj.FONT_HEIGHT);

            // 0xffff0000   0xff0f00ff   0xff0090ff
            Client.fr.drawCenteredString(name, (width / buttons.length) * count + (width / buttons.length) / 2F + 8, height - 20, hovered ? 0xff0090ff : -1);
            count++;
        }

        // CLIENT NAME
        GlStateManager.pushMatrix();
        GlStateManager.translate(width / 2F, height / 2F, 0);
        GlStateManager.scale(3, 3, 1);
        GlStateManager.translate(-(width / 2F), -(height / 2F), 0);
        // this.drawCenteredString(mc.fontRendererObj, Client.name + " v" + Client.version, (width / 2F) - 2, height / 2F - mc.fontRendererObj.FONT_HEIGHT / 2F, -1);
        this.drawCenteredString(mc.fontRendererObj, Client.name, (width / 2F) - 2, height / 2F - mc.fontRendererObj.FONT_HEIGHT / 2F, -1);
        GlStateManager.popMatrix();
        GlStateManager.pushMatrix();
        GlStateManager.translate(width / 2F, height / 2F, 0);
        GlStateManager.scale(1.2, 1.2, 1);
        GlStateManager.translate(-(width / 2F), -(height / 2F), 0);
        this.drawCenteredString(mc.fontRendererObj, " v" + Client.version, (width / 2F) - 2 + 33, height / 2F - 11 - mc.fontRendererObj.FONT_HEIGHT / 2F, 0xeeffc182);
        GlStateManager.popMatrix();

        // AUTHOR
        GlStateManager.pushMatrix();
        // Client.fr.drawCenteredString("by " + Client.author, (width / 2F) + 26, (height / 2F + mc.fontRendererObj.FONT_HEIGHT) + 6, -1);
        Client.fr.drawCenteredString("by " + Client.author, width - 50, mc.fontRendererObj.FONT_HEIGHT + 4, -1);
        GlStateManager.popMatrix();
    }

    public void mouseClicked(int mouseX, int mouseY, int button) {
        int count = 0;
        for (String name : buttons) {
            float x = (width / buttons.length) * count + (width / buttons.length) / 2F + 8 - mc.fontRendererObj.getStringWidth(name) / 2F;
            float y = height - 20;

            if (mouseX >= x && mouseY >= y && mouseX < x + mc.fontRendererObj.getStringWidth(name) && mouseY < y + mc.fontRendererObj.FONT_HEIGHT) {
                switch (name) {
                    case "Singleplayer":
                        mc.displayGuiScreen(new GuiSelectWorld(this));
                        break;
                    case "Multiplayer":
                        mc.displayGuiScreen(new GuiMultiplayer(this));
                        break;
                    case "MenuOptions":
                        mc.displayGuiScreen(new MainMenuOptions(this));
                        break;
                    case "Settings":
                        mc.displayGuiScreen(new GuiOptions(this, mc.gameSettings));
                        break;
                    case "AltManager":
                        mc.displayGuiScreen(new GuiAltManager());
                        break;
                    case "Quit":
                        Client.configManager.saveConfig();
                        mc.shutdown();
                        break;
                }

            }

            count++;
        }
    }

    public void onGuiClosed() {
    }
}
