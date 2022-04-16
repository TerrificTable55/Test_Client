package xyz.terrifictable.ui;

import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import xyz.terrifictable.Client;

public class MainMenu extends GuiScreen {
    private static final String[] buttons = {"Singleplayer", "Multiplayer", "Settings", "Language", "Quit"};

    public MainMenu() {
    }

    public void initGui() {

    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        mc.getTextureManager().bindTexture(new ResourceLocation("test/galaxy.jpg"));
        drawModalRectWithCustomSizedTexture(0, 0 , 0, 0, this.width, this.height, this.width, this.height);

        drawGradientRect(0, height - 100, width, height, 0x00000000, 0xff000000);

        int count = 0;
        for (String name : buttons) {
            float x = (width / buttons.length) * count + (width / buttons.length) / 2F + 8 - mc.fontRendererObj.getStringWidth(name) / 2F;
            float y = height - 20;

            boolean hovered = (mouseX >= x && mouseY >= y && mouseX < x + mc.fontRendererObj.getStringWidth(name) && mouseY < y + mc.fontRendererObj.FONT_HEIGHT);

            // 0xffff0000   0xff0f00ff   0xff0090ff
            this.drawCenteredString(mc.fontRendererObj, name, (width / buttons.length) * count + (width / buttons.length) / 2F + 8, height - 20, hovered ? 0xff0090ff : -1);
            count++;
        }

        GlStateManager.pushMatrix();
        GlStateManager.translate(width / 2F, height / 2F, 0);
        GlStateManager.scale(3, 3, 1);
        GlStateManager.translate(-(width / 2F), -(height / 2F), 0);
        this.drawCenteredString(mc.fontRendererObj, Client.name + " v" + Client.version, width / 2F, height / 2F - mc.fontRendererObj.FONT_HEIGHT / 2F, -1);
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
                    case "Settings":
                        mc.displayGuiScreen(new GuiOptions(this, mc.gameSettings));
                        break;
                    case "Language":
                        mc.displayGuiScreen(new GuiLanguage(this, mc.gameSettings, mc.getLanguageManager()));
                        break;
                    case "Quit":
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
