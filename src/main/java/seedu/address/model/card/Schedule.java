package seedu.address.model.card;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Holds the Schedule information for a Card
 */
public class Schedule {

    private final LocalDateTime nextReview;
    private final int lastInterval = 1;
    private final double easingFactor = 1;

    public Schedule() {
        this.nextReview = LocalDate.now().atStartOfDay();
    }

    public LocalDateTime getNextReview() {
        return nextReview;
    }

    public int getLastInterval() {
        return lastInterval;
    }

    public double getEasingFactor() {
        return easingFactor;
    }
}
