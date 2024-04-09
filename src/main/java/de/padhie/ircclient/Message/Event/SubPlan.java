package de.padhie.ircclient.Message.Event;

import de.padhie.ircclient.Message.Type;
import org.jetbrains.annotations.Nullable;

public enum SubPlan {
    PRIME("Prime"),
    TIES1("1000"),
    TIER2("2000"),
    TIER3("3000");

    public final String plan;

    SubPlan (String plan) {
        this.plan = plan;
    }

    @Nullable
    public static SubPlan fromString (String plan) {
        try {
            return SubPlan.valueOf(plan);
        } catch (Throwable throwable) {
            return null;
        }
    }

    @Nullable
    public static SubPlan fromIrcString (String plan) {
        switch (plan.toLowerCase()) {
            case "prime":
                return SubPlan.PRIME;
            case "1000":
                return SubPlan.TIES1;
            case "2000":
                return SubPlan.TIER2;
            case "3000":
                return SubPlan.TIER3;
        }

        return null;
    }

    public boolean equal (Type plan) {
        return this.plan.equalsIgnoreCase(plan.toString());
    }
}
