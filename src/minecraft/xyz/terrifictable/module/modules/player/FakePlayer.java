package xyz.terrifictable.module.modules.player;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import org.lwjgl.input.Keyboard;
import xyz.terrifictable.module.Module;
import xyz.terrifictable.setting.settings.ModeSetting;

import java.util.UUID;


public class FakePlayer extends Module {
    private EntityOtherPlayerMP _fakePlayer;

    public ModeSetting playerName = new ModeSetting("Name", "TerrificTable", "TerrificTable", "playerName");

    public FakePlayer() {
        super("FakePlayer", Keyboard.KEY_I, Category.PLAYER);
        addSettings(playerName);
    }

    public void onEnable() {
        GameProfile gameProfile;
        if (playerName.getMode().equalsIgnoreCase("playername"))
            gameProfile = mc.thePlayer.getGameProfile();
        else
            gameProfile = new GameProfile(UUID.randomUUID(), playerName.getMode());

        _fakePlayer = new EntityOtherPlayerMP(mc.theWorld, gameProfile);
        _fakePlayer.setEntityId(-1882);
        _fakePlayer.copyLocationAndAnglesFrom(mc.thePlayer);
        _fakePlayer.rotationYawHead = mc.thePlayer.rotationYawHead;
        mc.theWorld.addEntityToWorld(_fakePlayer.getEntityId(), _fakePlayer);
    }

    public void onDisable() {
        _fakePlayer.setDead();
    }
}
