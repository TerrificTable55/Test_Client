package xyz.terrifictable.events.listeners;

import xyz.terrifictable.events.Event;

public class EventChatRecieve extends Event<EventChatRecieve> {

    public String message;

    public EventChatRecieve(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
}
