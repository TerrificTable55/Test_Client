package xyz.terrifictable.command.commands;

import xyz.terrifictable.Client;
import xyz.terrifictable.command.Command;
import xyz.terrifictable.friend.Friend;

public class FriendCommands extends Command {
    public FriendCommands() {
        super("friend", "commands for the friend system", "", "f");
        this.syntax = Client.prefix + this.name + " [add/rem/list(ls)/clear] <PLAYER_NAME>";
    }

    @Override
    public void onCommand(String[] args, String command) {

        if (args.length >= 1) {
            if (args[0].equalsIgnoreCase("add")) {
                if (args.length >= 2) {
                    Client.friendManager.addFriend(args[1], args[1]);
                    Client.addChatMessage("Added '\u00A7a" + args[1] + "\u00A7f' to friend list");
                } else {
                    Client.addChatMessage(Client.prefix + this.name + " add [PLAYER_NAME]");
                }
            } else if (args[0].equalsIgnoreCase("rem") || args[0].equalsIgnoreCase("del")) {
                Client.friendManager.deleteFriend(args[1]);
                Client.addChatMessage("Removed '\u00A7a" + args[1] + "\u00A7f' from friend list");
            } else if (args[0].equalsIgnoreCase("list") || args[0].equalsIgnoreCase("ls")) {
                StringBuilder friends = new StringBuilder();

                for (Friend friend : Client.friendManager.getContents()) {
                    friends.append("\u00A7c").append(friend.getName()).append("\u00A7f, ");
                }

                Client.addChatMessage("Friends: " + friends);
            } else if (args[0].equalsIgnoreCase("clear")) {
                for (Friend friend : Client.friendManager.getContents()) {
                    Client.friendManager.deleteFriend(friend.getName());
                }
                Client.addChatMessage("Cleared Friends list");
            } else {
                Client.addChatMessage(this.syntax);
            }
        } else {
            Client.addChatMessage(this.syntax);
        }
    }
}
