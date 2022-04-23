package xyz.terrifictable.events.listeners;

import net.minecraft.client.gui.ScaledResolution;
import xyz.terrifictable.events.Event;

public class EventRender3D extends Event<EventRender3D> {
    public final ScaledResolution scaledResolution;

    public EventRender3D(ScaledResolution scaledResolution, float ticks) {
        this.partialTicks = ticks;
        this.scaledResolution = scaledResolution;
    }

    float partialTicks;

    public float getTicks() {
        return this.partialTicks;
    }

    public float getPartialTicks() {
        return this.partialTicks;
    }

}
