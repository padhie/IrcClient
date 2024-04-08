package IrcClient.Message.Event;

public class Sub {
    public final String user;
    public final SubPlan plan;
    public final int month;
    public final int cumulativeMonth;
    public final boolean gifted;
    public final int multimonthDuration;
    public final int multimonthTenure;
    public final int shouldShareStreak;
    public final String message;

    public Sub (
        String user,
        SubPlan plan,
        int month,
        int cumulativeMonth,
        boolean gifted,
        int multimonthDuration,
        int multimonthTenure,
        int shouldShareStreak,
        String message
    ) {
        this.user = user;
        this.plan = plan;
        this.month = month;
        this.cumulativeMonth = cumulativeMonth;
        this.gifted = gifted;
        this.multimonthDuration = multimonthDuration;
        this.multimonthTenure = multimonthTenure;
        this.shouldShareStreak = shouldShareStreak;
        this.message = message;
    }
}
