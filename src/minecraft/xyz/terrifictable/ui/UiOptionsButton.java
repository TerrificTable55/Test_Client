package xyz.terrifictable.ui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import xyz.terrifictable.Client;

import java.awt.*;

public class UiOptionsButton extends GuiButton {

    private int fade;

    public UiOptionsButton(int buttonId, int x, int y, String buttonText) {
        super(buttonId, x, y, 200, 20, buttonText);
    }

    public UiOptionsButton(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText) {
        super(buttonId, x, y, widthIn, heightIn, buttonText);
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

            final Color color = new Color(255, 255, 255, this.fade);
            Gui.drawRect(this.xPosition, this.yPosition, this.xPosition + this.width - 50, this.yPosition + this.height, color.getRGB());
            Client.fr.drawCenteredString(this.displayString, this.xPosition + (this.width / 2f) - 21, this.yPosition + ((this.height - 8) / 2f), 0xff34e1eb);
        }
    }
}
