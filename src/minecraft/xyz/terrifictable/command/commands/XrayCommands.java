package xyz.terrifictable.command.commands;

import net.minecraft.block.Block;
import xyz.terrifictable.Client;
import xyz.terrifictable.command.Command;
import xyz.terrifictable.module.modules.render.Xray;

public class XrayCommands extends Command {
    public XrayCommands() {
        super("xray", "add block / remove block", "", "");
        this.setSyntax(Client.prefix + this.getName() + " [ADD/REM/LIST] [BLOCK_NAME]");
    }

    @Override
    public void onCommand(String[] args, String command) {
        if (args[0].equalsIgnoreCase("add")) {
            Xray.xrayBlocks.add(Block.getBlockFromName(args[1]));
            Client.addChatMessage("Added " + args[1] + " to xray blocks");
        } else if (args[0].equalsIgnoreCase("rem")) {
            Xray.xrayBlocks.remove(Block.getBlockFromName(args[1]));
            Client.addChatMessage("Removed " + args[1] + " from xray blocks");
        } else if (args[0].equalsIgnoreCase("list")) {
            StringBuilder xrayBlocks = new StringBuilder();
            for (Block x : Xray.xrayBlocks) {
                xrayBlocks.append(x.getUnlocalizedName().replace("tile.", "")).append(", ");
            }
            Client.addChatMessage("Xray Blocks: " + xrayBlocks);
        } else {
            Client.addChatMessage(this.getSyntax());
        }
    }
}
