package xyz.terrifictable.events.listeners;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import xyz.terrifictable.events.Event;

public class Event2D extends Event<Event2D> {

    private float partitialTicks;
    private ScaledResolution scaledResolution;
    private FontRenderer fontRenderer;

    public Event2D(ScaledResolution scaledResolution, float partitialTicks, FontRenderer fontRenderer) {
        this.scaledResolution = scaledResolution;
        this.partitialTicks = partitialTicks;
        this.fontRenderer = fontRenderer;
    }

    public float getPartitialTicks() {
        return partitialTicks;
    }
    public ScaledResolution getScaledResolution() {
        return scaledResolution;
    }
    public FontRenderer getFontRenderer() { return fontRenderer; }
}
