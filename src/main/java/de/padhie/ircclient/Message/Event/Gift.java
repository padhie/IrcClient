package de.padhie.ircclient.Message.Event;

public class Gift {
    public final String user;
    public final String recipient;
    public final SubPlan plan;
    public final int month;
    public final int giftedMonth;
    public final String message;

    public Gift (
        String user,
        String recipient,
        SubPlan plan,
        int month,
        int giftedMonth,
        String message
    ) {
        this.user = user;
        this.recipient = recipient;
        this.plan = plan;
        this.month = month;
        this.giftedMonth = giftedMonth;
        this.message = message;
    }
}
