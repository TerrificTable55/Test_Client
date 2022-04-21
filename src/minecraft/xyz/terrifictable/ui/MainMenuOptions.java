package xyz.terrifictable.ui;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import xyz.terrifictable.Client;

import java.awt.*;

import static xyz.terrifictable.Client.image;

public class MainMenuOptions extends GuiScreen {

    public static String[] images = { "city.jpg", "forest_river.jpg", "galaxy.jpg", "mountains_evening.jpg", "mountains_rocket.jpg", "mountains_sky.jpg", "night_sky.jpg", "sea.jpg", "sea_forest_painting.jpg", "mountains_sea.jpg", "sea_ship.jpg", "snowy_mountain.jpg", "tree_sun.jpg" };
    private static String[] imagesNames = { "City", "Forest River", "Galaxy", "Mountains Evening", "Rocket", "Mountains Sky", "Night Sky", "Sea", "Sea Forrest Painting", "Mountains Sea", "Ship", "Snowy Mountains", "Tree" };
    private GuiScreen previousScreen;
    private int i_fade = 0;

    public MainMenuOptions(GuiScreen previousScreen) {
        this.previousScreen = previousScreen;

        // TODO: === TODO ===
        // TODO: - Image
        // TODO: - ...
    }


    @Override
    public void initGui() {
        // this.buttonList.add(new UiButton(0, width / 2 - 100, height / 4 + 24 + 72 + 12, "idk"));
        this.buttonList.add(new UiButton(1, width / 2 - 100, height / 4 + 24 + 72 + 12 + 24, "Back"));
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();

        Client.fr.drawCenteredString("Image: ", width / 2 - 100 + 50, height / 4 + 24 + 72 + 12 - 48, -1);
        Client.fr.drawString(imagesNames[image], width / 2 - 100 + 80, height / 4 + 24 + 72 + 12 - 48, -1);

        int x = width / 2 - 100 + 90 + 110;
        int y = height / 4 + 24 + 72 + 12 + 24 + 20;
        boolean i_hovered = isMouseOverButton(mouseX, mouseY, width / 2 - 100, height / 4 + 24 + 72 + 12 - 55, x, y - 80);


        if (!i_hovered) {
            if (i_fade != 100)
                i_fade += 5;
        } else {
            if (i_fade <= 40)
                return;
            if (i_fade != 70)
                i_fade -= 5;
        }

        final Color i_color = new Color(255, 255, 255, i_fade);

        Gui.drawRect(width / 2 - 100, height / 4 + 24 + 72 + 12 - 55, width / 2 - 100 + 90 + 110, height / 4 + 24 + 72 + 12 - 45 + mc.fontRendererObj.FONT_HEIGHT, i_color.getRGB()); // 0x80ffffff
        MainMenu.image = images[image];

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    private boolean isMouseOverButton(int mouseX, int mouseY, int minX, int minY, int maxX, int maxY) {
        return (mouseX >= minX && mouseX <= maxX) &&
                (mouseY >= minY && mouseY <= maxY);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        int count = 1;
        for (GuiButton button : this.buttonList) {

            int x = width / 2 - 100 + width;
            int y = height / 4 + 24 + 72 + 12 + (count * 24) + 20;

            if (isMouseOverButton(mouseX, mouseY, width / 2 - 100, height / 4 + 24 + 72 + 12 - 55, x, y - 80)) {
                if (mouseButton == 0) {
                    if (image + 1 >= images.length)
                        image = 0;
                    else
                        image++;
                } else if (mouseButton == 1) {
                    if (image - 1 <= 0) {
                        image = images.length - 1;
                    }
                    else
                        image--;
                }
            }

            if (isMouseOverButton(mouseX, mouseY, width / 2 - 100, height / 4 + 24 + 72 + 12 + (count * 24), x, y)) {
                switch (button.id) {
                    case 1: {
                        this.mc.displayGuiScreen(this.previousScreen);
                        break;
                    }
                }
            }

            count++;
        }
    }

    public void onGuiClosed() {
    }
}
