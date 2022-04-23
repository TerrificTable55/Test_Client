package xyz.terrifictable.ui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import xyz.terrifictable.Client;

import java.awt.*;

public class UiButton extends GuiButton {

    private int fade;
    private int r = 255, g = 255, b = 255;

    public UiButton(int buttonId, int x, int y, String buttonText) {
        super(buttonId, x, y, 200, 20, buttonText);
    }

    public UiButton(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText) {
        super(buttonId, x, y, widthIn, heightIn, buttonText);
    }

    public UiButton(int buttonId, int x, int y, String buttonText, int r, int g, int b) {
        super(buttonId, x, y, 200, 20, buttonText);
        if (r != 255 || r != -1)
            this.r = r;
        if (g != 255 || g != -1)
            this.g = g;
        if (b != 255 || b != -1)
            this.b = b;
    }

    public UiButton(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText, int r, int g, int b) {
        super(buttonId, x, y, widthIn, heightIn, buttonText);
        if (r != 255 || r != -1)
            this.r = r;
        if (g != 255 || g != -1)
            this.g = g;
        if (b != 255 || b != -1)
            this.b = b;
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        if (this.visible) {
            this.hovered = ((mouseX >= this.xPosition && mouseX < this.xPosition + this.width) && (mouseY >= this.yPosition && mouseY < this.yPosition + this.height));

            if (!hovered) {
                if (this.fade != 100)
                    this.fade += 5;
            } else {
                if (this.fade <= 40)
                    return;
                if (this.fade != 70)
                    this.fade -= 5;
            }

            final Color color = new Color(this.r, this.g, this.b, this.fade);
            Gui.drawRect(this.xPosition, this.yPosition, this.xPosition + this.width, this.yPosition + this.height, color.getRGB());
            Client.fr.drawCenteredString(this.displayString, this.xPosition + (this.width / 2f), this.yPosition + ((this.height - 8) / 2f), 0xff34e1eb);
        }
    }
}
