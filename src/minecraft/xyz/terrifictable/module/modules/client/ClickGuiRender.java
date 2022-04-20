package xyz.terrifictable.module.modules.client;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import xyz.terrifictable.Client;
import xyz.terrifictable.module.Module;

import java.io.IOException;
import java.util.List;

public class ClickGuiRender extends GuiScreen {

    private ScaledResolution sr;
    private FontRenderer fr;
    public boolean click;

    public ClickGuiRender() { }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);


        sr = new ScaledResolution(mc);
        fr = mc.fontRendererObj;

        int categoryIndex = 0;

        for (Module.Category category : Module.getCategorys()) {
            int moduleIndex = 0;


            // === MODULES ===
            List<Module> modules = Client.getModulesByCategory(category);

            int maxLen_m = 0;
            for (Module module : modules) {
                if (fr.getStringWidth(module.name) > maxLen_m) {
                    maxLen_m = fr.getStringWidth(module.name);
                }
            }

            // === CATEGORYS ===
            Gui.drawRect(2 + categoryIndex * 80, 2, (categoryIndex * 80) + fr.getStringWidth(category.name) + maxLen_m - 20, fr.FONT_HEIGHT + 7, 0x90000000);
            fr.drawString(category.name, 5 + categoryIndex * 80, 5, -1);
            // === CATEGORYS ===

            Gui.drawRect(2 + categoryIndex * 80, 2 + fr.FONT_HEIGHT  + 7, (categoryIndex * 80) + fr.getStringWidth(category.name) + maxLen_m - 20, (modules.size() * 12) + fr.FONT_HEIGHT + 9, 0x90000000);


            for (Module module : Client.getModulesByCategory(category)) {
                fr.drawString(module.name, 5 + categoryIndex * 80, (moduleIndex * 12) + 20, -1);
                if (module.isToggled()) {
                    fr.drawString(module.name, 5 + categoryIndex * 80, (moduleIndex * 12) + 20, 0xff00ff00);
                }
                if (isInside(mouseX, mouseY, 2 + categoryIndex * 80, moduleIndex * 12 + 20, (categoryIndex * 80) + fr.getStringWidth(category.name) + maxLen_m - 20, moduleIndex * 12 + 20 + fr.FONT_HEIGHT)) {
                    fr.drawString(module.name, 5 + categoryIndex * 80, (moduleIndex * 12) + 20, 0xff00eeff);
                }
                moduleIndex++;
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
        click = true;

        int categoryIndex = 0;
        for (Module.Category category : Module.getCategorys()) {
            int maxLen_m = 0;
            for (Module module : Client.getModulesByCategory(category)) {
                if (fr.getStringWidth(module.name) > maxLen_m) {
                    maxLen_m = fr.getStringWidth(module.name);
                }
            }

            int moduleIndex = 0;
            for (Module module : Client.getModulesByCategory(category)) {
                int y = moduleIndex * 12 + 20;
                if (isInside(mouseX, mouseY, 2 + categoryIndex * 80, y, (categoryIndex * 80) + fr.getStringWidth(category.name) + maxLen_m - 20, y + fr.FONT_HEIGHT)) {
                    module.toggle();
                }
                moduleIndex++;
            }
            categoryIndex++;
        }
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        super.mouseReleased(mouseX, mouseY, state);
        click = false;
    }

    @Override
    public void initGui() {
        super.initGui();
        click = false;
    }

    public boolean isInside(int mouseX, int mouseY, double x, double y, double width, double height) {
        return ((mouseX > x && mouseX < width) && (mouseY > y && mouseY < height));
    }
}
