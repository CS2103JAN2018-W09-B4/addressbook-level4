package seedu.address.model.card;

import java.util.Calendar;

/**
 * Holds the Schedule information for a Card
 */
public class Schedule {

    private final Calendar nextReview;
    private final int lastInterval = 1;
    private final double easingFactor = 1;

    public Schedule() {
        this.nextReview = Calendar.getInstance();
    }

    public Calendar getNextReview() {
        return nextReview;
    }

    public int getLastInterval() {
        return lastInterval;
    }

    public double getEasingFactor() {
        return easingFactor;
    }
}
