package xyz.terrifictable.module.modules.client;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import xyz.terrifictable.Client;
import xyz.terrifictable.module.Module;
import xyz.terrifictable.util.font.MinecraftFontRenderer;

import java.io.IOException;
import java.util.List;

public class ClickGuiRender extends GuiScreen {

    private ScaledResolution sr;
    private MinecraftFontRenderer fr;

    public ClickGuiRender() { }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);


        sr = new ScaledResolution(mc);
        fr = Client.fr;

        int categoryIndex = 0;

        for (Module.Category category : Module.getCategorys()) {
            int moduleIndex = 0;


            // === MODULES ===
            List<Module> modules = Client.getModulesByCategory(category);

            // === biggest module length ===
            int maxLen_m = 0;
            for (Module module : modules) {
                if (!module.name.equalsIgnoreCase("save")) {
                    if (fr.getStringWidth(module.name) > maxLen_m) {
                        maxLen_m = mc.fontRendererObj.getStringWidth(module.name);
                    }
                }
            }
            // === biggest module length ===

            // === CATEGORYS ===
            Gui.drawRect(2 + categoryIndex * 80, 2, (categoryIndex * 80) + fr.getStringWidth(category.name) + maxLen_m - 20, mc.fontRendererObj.FONT_HEIGHT + 7, 0x90000000);
            fr.drawString(category.name, 5 + categoryIndex * 80, 5, -1);
            // === CATEGORYS ===

            // Draw modules box
            Gui.drawRect(2 + categoryIndex * 80, 2 + mc.fontRendererObj.FONT_HEIGHT  + 7, (categoryIndex * 80) + fr.getStringWidth(category.name) + maxLen_m - 20, (modules.size() * 12) + mc.fontRendererObj.FONT_HEIGHT + 9, 0x90000000);

            // Cycle through modules
            for (Module module : Client.getModulesByCategory(category)) {
                if (!module.name.equalsIgnoreCase("save")) {
                    fr.drawString(module.name, 5 + categoryIndex * 80, (moduleIndex * 12) + 20, -1); // Draw module name
                    if (module.isToggled() || module.name.equalsIgnoreCase("clickgui")) {
                        fr.drawString(module.name, 5 + categoryIndex * 80, (moduleIndex * 12) + 20, 0xff00ff00); // Color name green if toggled
                    }

                    if (isInside(mouseX, mouseY, // check if mouse is above current module
                            2 + categoryIndex * 80,
                            moduleIndex * 12 + 20,
                            (categoryIndex * 80) + fr.getStringWidth(category.name) + maxLen_m - 20,
                            moduleIndex * 12 + 20 + mc.fontRendererObj.FONT_HEIGHT
                    )) { fr.drawString(module.name, 5 + categoryIndex * 80, (moduleIndex * 12) + 20, 0xff00eeff); } // color light blue if hovered
                    moduleIndex++;
                }
            }

            categoryIndex++;
        }
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);

        int categoryIndex = 0;
        for (Module.Category category : Module.getCategorys()) { // Cycle trough Categorys
            // === Longest module name ===
            int maxLen_m = 0;
            for (Module module : Client.getModulesByCategory(category)) {
                if (fr.getStringWidth(module.name) > maxLen_m) {
                    maxLen_m = mc.fontRendererObj.getStringWidth(module.name);
                }
            }
            // === Longest module name ===

            // === Get which module is clicked on ===
            int moduleIndex = 0;
            for (Module module : Client.getModulesByCategory(category)) { // Cycle trough modules
                // Check if current module is clicked on
                if (mouseButton == 0 && isInside(mouseX,
                        mouseY,
                        2 + categoryIndex * 80,
                        moduleIndex * 12 + 20,
                        (categoryIndex * 80) + fr.getStringWidth(category.name) + maxLen_m - 20,
                        (moduleIndex * 12 + 20) + mc.fontRendererObj.FONT_HEIGHT
                )) { module.toggle(); /* Toggle module */  }
                moduleIndex++;
            }
            // === Get which module is clicked on ===
            categoryIndex++;
        }
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        super.mouseReleased(mouseX, mouseY, state);
    }

    @Override
    public void initGui() {
        super.initGui();
    }

    public boolean isInside(int mouseX, int mouseY, double x, double y, double width, double height) {
        return ((mouseX > x && mouseX < width) && (mouseY > y && mouseY < height));
    }
}
