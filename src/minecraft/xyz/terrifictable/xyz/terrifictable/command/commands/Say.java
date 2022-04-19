package xyz.terrifictable.command.commands;

import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.C01PacketChatMessage;
import xyz.terrifictable.Client;
import xyz.terrifictable.command.Command;

public class Say extends Command {
    public Say() {
        super("say", "Says something in chat", "", "s");
        this.setSyntax(Client.prefix + this.getName());
    }

    @Override
    public void onCommand(String[] args, String command) {
        Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue(new C01PacketChatMessage(String.join(" ", args)));
    }
}
