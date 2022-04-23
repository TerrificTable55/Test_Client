package xyz.terrifictable.ui;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;
import xyz.terrifictable.Client;

import java.awt.*;

import static xyz.terrifictable.Client.image;
import static xyz.terrifictable.Client.layout;

public class MainMenuOptions extends GuiScreen {

    // script that generated filenames and names (python):
    // import os; directory = '/home/terrific/java/test_client/src/minecraft/assets/minecraft/test'; Name_filenames = []; filenames = [];
    // for filename in os.listdir(directory):
    //      f = os.path.join(directory, filename)
    //         if os.path.isfile(f):
    //              filename = f.replace(directory + "\\", ""); filenames.append(filename); filename = filename.replace("_", " ").split(".")[0]; newFilename = ""
    //              for i in range(len(filename.split(" "))):
    //                  for x, char in enumerate(filename.split(" ")[i]):
    //                      if (x == 0):
    //                          newFilename += char.upper()
    //                      else:
    //                          newFilename += char
    //                  newFilename += " "
    //         Name_filenames.append(newFilename)
    // print(str(Name_filenames)).replace("[", "{").replace("]", "}").replace("'", "\"").replace(" \"", "\"").replace("\",\"", "\", \""); print(str(filenames).replace("[", "{").replace("]", "}").replace("'", "\"").replace(" \"", "\"").replace("\",\"", "\", \""))

    public static String[] images = { "galaxy.jpg", "mountains_rocket.jpg", "forest_river.jpg", "sea_forest_painting.jpg", "sea.jpg", "sea_ship.jpg", "mountains_sky.jpg", "tree_sun.jpg", "city.jpg", "mountains_sea.jpg", "night_sky.jpg", "buildings.png", "Forest_with_clouds.jpg", "lightblack.png", "mountain.jpeg", "night-city.jpg", "nord-shards.png", "sunrise.jpg", "galaxy_forrest.jpg", "nature.png", "fox_other_animal.png", "rainy_city.jpeg", "future_city.jpg", "forrset_fogy.jpg", "mountain_sky.jpg", "nature_sunrise.jpg", "nature_painting.jpg", "mountains_silluete.jpg", "fogy_forrest.jpg", "snowy_mountains.jpg", "keyboard.png", "vulcano_sea.jpg", "abstract_cubes.png", "black_white_mountain.png", "boat_sea.jpg", "china_city_painting.png", "china_something_painting.jpg", "devil_catana.png", "dragon_panting.jpg", "forrest_river.jpg", "future_city_girl.jpg", "future_city_guy.jpg", "more_random_colors.jpg", "mountains_china.jpg", "mountains_clouds.jpg", "mountains_galaxy.jpg", "mountains_moon.jpg", "mountains_painting.jpg", "mountains_shilluete.jpg", "mountains_sunrise.jpg", "random_thingy.png", "red_something.png", "samurain_something.jpg", "sea_mountains.jpg", "snowy_mountain.jpg", "some_colors.jpg", "some_room.png", "something_on_mountains.jpg", "stairs.png", "star_wars_something.jpg", "sunset_river.jpg"};
    private static String[] imagesNames = { "Galaxy", "Mountains Rocket", "Forest River", "Sea Forest Painting", "Sea", "Sea Ship", "Mountains Sky", "Tree Sun", "City", "Mountains Sea", "Night Sky", "Buildings", "Forest With Clouds", "Lightblack", "Mountain", "Night-city", "Nord-shards", "Sunrise", "Galaxy Forrest", "Nature", "Fox Other Animal", "Rainy City", "Future City", "Forrset Fogy", "Mountain Sky", "Nature Sunrise", "Nature Painting", "Mountains Silluete", "Fogy Forrest", "Snowy Mountains", "Keyboard", "Vulcano Sea", "Abstract Cubes", "Black White Mountain", "Boat Sea", "China City Painting", "China Something Painting", "Devil Catana", "Dragon Panting", "Forrest River", "Future City Girl", "Future City Guy", "More Random Colors", "Mountains China", "Mountains Clouds", "Mountains Galaxy", "Mountains Moon", "Mountains Painting", "Mountains Shilluete", "Mountains Sunrise", "Random Thingy", "Red Something", "Samurain Something", "Sea Mountains", "Snowy Mountain", "Some Colors", "Some Room", "Something On Mountains", "Stairs", "Star Wars Something", "Sunset River"};
    public static String[] layouts = { "Standard", "Side" };

    private GuiScreen previousScreen;
    private int i_fade = 0;
    private int j_fade = 0;

    public MainMenuOptions(GuiScreen previousScreen) {
        this.previousScreen = previousScreen;
    }


    @Override
    public void initGui() {
        // this.buttonList.add(new UiButton(0, width / 2 - 100, height / 4 + 24 + 72 + 12, "idk"));
        this.buttonList.add(new UiButton(1, width / 2 - 100, height / 4 + 24 + 72 + 12 + 24, "Back", 0, 0, 0));
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        mc.getTextureManager().bindTexture(new ResourceLocation("test/" + MainMenu.image));
        drawModalRectWithCustomSizedTexture(0, 0 , 0, 0, this.width, this.height, this.width, this.height);


        int x = width / 2 - 100 + 90 + 110;
        int y = height / 4 + 24 + 72 + 12 + 24 + 20;
        boolean i_hovered = isMouseOverButton(mouseX, mouseY, width / 2 - 100, height / 4 + 24 + 72 + 12 - 55, x, y - 80);


        if (!i_hovered) {
            if (i_fade != 100) {
                i_fade += 5;
            }
        } else {
            if (i_fade <= 40) {
                return;
            }
            if (i_fade != 70) {
                i_fade -= 5;
            }
        }

        final Color i_color = new Color(0, 0, 0, i_fade);

        Gui.drawRect(width / 2 - 100, height / 4 + 24 + 72 + 12 - 55, width / 2 - 100 + 90 + 110, height / 4 + 24 + 72 + 12 - 45 + mc.fontRendererObj.FONT_HEIGHT, i_color.getRGB()); // 0x80ffffff

        Client.fr.drawString("Image:     " + imagesNames[image], width / 2 - 100 + 30, height / 4 + 24 + 72 + 12 - 48, 0xff34e1eb); // -1);
        // Client.fr.drawCenteredString("Image:     " + imagesNames[image], width / 2 - 100 + 50, height / 4 + 24 + 72 + 12 - 48, 0xff34e1eb); // -1);
        // Client.fr.drawString(imagesNames[image], width / 2 - 100 + 80, height / 4 + 24 + 72 + 12 - 48, 0xff34e1eb); // -1);
        MainMenu.image = images[image];


        boolean j_hovered = isMouseOverButton(mouseX, mouseY, width / 2 - 100, height / 4 + 24 + 72 + 12 - 55 + 20, x + 20, y - 80 + 20);

        if (!j_hovered) {
            if (j_fade != 100) {
                j_fade += 5;
            }
        } else {
            if (j_fade <= 40) {
                return;
            }
            if (j_fade != 70) {
                j_fade -= 5;
            }
        }

        final Color j_color = new Color(0, 0, 0, j_fade);

        Gui.drawRect(width / 2 - 100, height / 4 + 24 + 72 + 12 - 12, width / 2 - 100 + 90 + 110, height / 4 + 24 + 72 + 12 - 40 + mc.fontRendererObj.FONT_HEIGHT, j_color.getRGB()); // 0x80ffffff

        Client.fr.drawString("Layout:     " + layouts[layout], width / 2 - 100 + 30, height / 4 + 24 + 72 + 12 - 12 - 13, 0xff34e1eb); // -1);
        MainMenu.layout = layouts[layout];

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

            if (isMouseOverButton(mouseX, mouseY, width / 2 - 100, height / 4 + 24 + 72 + 12 - 55 + 20, x + 20, y - 80 + 20)) {
                if (mouseButton == 0) {
                    if (layout + 1 >= layouts.length)
                        layout = 0;
                    else
                        layout++;
                } else if (mouseButton == 1) {
                    if (layout - 1 <= 0) {
                        layout = layouts.length - 1;
                    }
                    else
                        layout--;
                }
            }

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
