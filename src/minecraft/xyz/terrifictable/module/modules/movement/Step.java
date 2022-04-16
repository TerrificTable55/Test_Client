package xyz.terrifictable.module.modules.movement;

import net.minecraft.network.play.client.C03PacketPlayer;
import org.lwjgl.input.Keyboard;
import xyz.terrifictable.module.Module;

public class Step extends Module {
    public Step() {
        super("Step", Keyboard.KEY_Y, Category.MOVEMENT);
    }

    @Override
    public void onUpdate() {
        if (!this.isToggled()) return;

        if (mc.thePlayer.isCollidedHorizontally & mc.thePlayer.onGround) {
            mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 0.42f, mc.thePlayer.posZ, mc.thePlayer.onGround));
            mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 0.72f, mc.thePlayer.posZ, mc.thePlayer.onGround));
            mc.thePlayer.stepHeight = 1.0f;
        } else {
            mc.thePlayer.stepHeight = 0.5f;
        }
    }
}
