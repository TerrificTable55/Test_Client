package xyz.terrifictable.module.modules.player;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemBlock;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import org.lwjgl.input.Keyboard;
import xyz.terrifictable.module.Module;

public class Scaffold extends Module {
    public Scaffold() {
        super("Scaffold", Keyboard.KEY_LMENU, Category.PLAYER);
    }

    @Override
    public void onUpdate() {
        if (!this.isToggled()) return;

        Entity player = mc.thePlayer;
        BlockPos bp = new BlockPos(player.posX, player.getEntityBoundingBox().minY, player.posZ);

        if (valid(bp.add(0, -2, 0)))
            place(bp.add(0, -1, -1), EnumFacing.UP);
        else if (valid(bp.add(-1, -1, 0)))
            place(bp.add(0, -1, 0), EnumFacing.EAST);
        else if (valid(bp.add(1, -1, 0)))
            place(bp.add(0, -1, -1), EnumFacing.WEST);
        else if (valid(bp.add(0, -1, -1)))
            place(bp.add(0, -1, 0), EnumFacing.SOUTH);
        else if (valid(bp.add(0, -1, 1)))
            place(bp.add(0, -1, 0), EnumFacing.NORTH);

        else if (valid(bp.add(1, -1, 1))) {
            if (valid(bp.add(0, -1, 1)))
                place(bp.add(0, -1, 1), EnumFacing.NORTH);
            place(bp.add(1, -1, 1), EnumFacing.EAST);
        } else if (valid(bp.add(-1, -1, 1))) {
            if (valid(bp.add(-1, -1, 0)))
                place(bp.add(0, -1, 1), EnumFacing.WEST);
            place(bp.add(-1, -1, 1), EnumFacing.SOUTH);
        } else if (valid(bp.add(-1, -1, -1))) {
            if (valid(bp.add(0, -1, -1)))
                place(bp.add(0, -1, 1), EnumFacing.SOUTH);
            place(bp.add(-1, -1, 1), EnumFacing.WEST);
        } else if (valid(bp.add(1, -1, -1))) {
            if (valid(bp.add(1, -1, 0)))
                place(bp.add(1, -1, 0), EnumFacing.EAST);
            place(bp.add(1, -1, -1), EnumFacing.NORTH);
        }
    }

    public void place(BlockPos pos, EnumFacing facing) {
        if (facing == EnumFacing.UP)
            pos = pos.add(0, -1, 0);
        else if (facing == EnumFacing.NORTH)
            pos = pos.add(0, 0, 1);
        else if (facing == EnumFacing.EAST)
            pos = pos.add(-1, 0, 0);
        else if (facing == EnumFacing.SOUTH)
            pos = pos.add(0, 0, -1);
        else if (facing == EnumFacing.WEST)
            pos = pos.add(1, 0, 0);

        EntityPlayerSP _player = mc.thePlayer;

        if (_player.getHeldItem() != null && _player.getHeldItem().getItem() instanceof ItemBlock) {
            _player.swingItem();
            mc.playerController.onPlayerRightClick(_player, mc.theWorld, _player.getHeldItem(), pos, facing, new Vec3(0.5, 0.5, 0.5));
            double x = pos.getX() + 0.25 - _player.posX;
            double y = pos.getY() + 0.25 - _player.posY;
            double z = pos.getZ() + 0.25 - _player.posZ;
            double distance = MathHelper.sqrt_double(x * x  + z * z);
            float yaw = (float) (Math.atan2(z, x) * 180 / Math.PI - 90);
            float pitch = (float) - (Math.atan2(y, distance) * 180 / Math.PI);
            sendPacket(new C03PacketPlayer.C06PacketPlayerPosLook(_player.posX, _player.posY, _player.posZ, yaw, pitch, _player.onGround));
        }
    }

    public boolean valid(BlockPos pos) {
        Block block = mc.theWorld.getBlock(pos);
        return !(block instanceof BlockLiquid) && block.getMaterial() != Material.air;
    }
}
