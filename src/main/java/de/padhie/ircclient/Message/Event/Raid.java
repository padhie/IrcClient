package de.padhie.ircclient.Message.Event;

public class Raid {
    public final String raider;
    public final int viewerCount;
    public final String message;

    public Raid (
        String raider,
        int viewerCount,
        String message
    ) {
        this.raider = raider;
        this.viewerCount = viewerCount;
        this.message = message;
    }
}
