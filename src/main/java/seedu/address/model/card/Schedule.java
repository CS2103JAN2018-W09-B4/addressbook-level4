package seedu.address.model.card;

import static java.lang.Math.log;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Holds the Schedule information for a Card
 */
public class Schedule {
    private final int learningPhase = 4;
    private final double lowerBoundRememberRate = 0.85;

    private LocalDateTime nextReview;
    private int lastInterval = 1;
    private double easingFactor = 1;
    private int success = 0;
    private int failure = 0;
    private double successRate = 0;

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

    public double getSuccessRate() {
        return successRate;
    }

    /**
     * Feedback algorithm takes in whether the answer is correct.
     */
    public void isFeedbackSuccessful(boolean isSuccess) {
        if (isSuccess) {
            success++;
        } else {
            failure++;
        }

        successRate = success / (double) (success + failure);

        if (success + failure >= learningPhase) {
            easingFactor = easingFactor
                    * log(lowerBoundRememberRate)
                    / log(successRate);
        }

        lastInterval = (int) (easingFactor * lastInterval);
        nextReview = nextReview.plusDays((long) lastInterval);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Schedule // instanceof handles nulls
                && this.learningPhase == (((Schedule) other).learningPhase)
                && this.lowerBoundRememberRate ==
                (((Schedule) other).lowerBoundRememberRate)
                && this.lastInterval == (((Schedule) other).lastInterval)
                && this.easingFactor == (((Schedule) other).easingFactor)
                && this.success == (((Schedule) other).success)
                && this.failure == (((Schedule) other).failure)
                && this.successRate == (((Schedule) other).successRate));
    }
}
