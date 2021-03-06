package xyz.terrifictable.events.listeners;

import xyz.terrifictable.events.Event;

public class EventMotion extends Event<EventMotion> {

    public double x;
    public static double y;
    public double z;
    public float yaw;
    public static float pitch;
    public boolean onGround;

    public EventMotion(double x, double y, double z, float yaw, float pitch, boolean onGround) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
        this.onGround = onGround;
    }

    public double getX() {
        return x;
    }
    public void setX(double x) {
        this.x = x;
    }
    public static double getY() {
        return y;
    }
    public void setY(double y) {
        this.y = y;
    }
    public double getZ() {
        return z;
    }
    public void setZ(double z) {
        this.z = z;
    }
    public float getYaw() {
        return yaw;
    }
    public void setYaw(float yaw) {
        this.yaw = yaw;
    }
    public float getPitch() {
        return pitch;
    }
    public static void setPitch(float pitchIn) {
        pitch = pitchIn;
    }
    public boolean isOnGround() {
        return onGround;
    }
    public void setOnGround(boolean onGround) {
        this.onGround = onGround;
    }
}
