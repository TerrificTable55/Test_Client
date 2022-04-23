package xyz.terrifictable.friend;

import net.minecraft.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class FriendManager {
    public void addFriend(String name, String alias) {
        getContents().add(new Friend(name, alias));
    }

    public void deleteFriend(String name) {
        for (Friend friend : getContents()) {
            if (friend.getName().equalsIgnoreCase(StringUtils.stripControlCodes(name)) || friend.getAlias().equalsIgnoreCase(StringUtils.stripControlCodes(name))) {
                getContents().remove(friend);
                break;
            }
        }
    }


    public Friend getFriend(String name) {
        for (Friend friend : getContents()) {
            if (friend.getName().equalsIgnoreCase(StringUtils.stripControlCodes(name)) || friend.getAlias().equalsIgnoreCase(StringUtils.stripControlCodes(name))) {
                return friend;
            }
        }
        return null;
    }

    public boolean isFriend(String name) {
        for (Friend friend : getContents()) {
            if (friend.getName().equalsIgnoreCase(StringUtils.stripControlCodes(name)) || friend.getAlias().equalsIgnoreCase(StringUtils.stripControlCodes(name))) {
                return true;
            }
        }
        return false;
    }

    private List<Friend> contents = new ArrayList<>();
    public List<Friend> getContents() {
        return contents;
    }
    public void setContents(ArrayList<Friend> contents) {
        this.contents = contents;
    }
}
