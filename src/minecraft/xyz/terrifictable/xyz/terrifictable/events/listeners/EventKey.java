package xyz.terrifictable.events.listeners;

import xyz.terrifictable.events.Event;

public class EventKey extends Event<EventKey> {
    public int key;

    public EventKey(int code) {
        this.key = code;
    }

    public void setKey(int key) {
        this.key = key;
    }
    public int getKey() {
        return key;
    }
}
