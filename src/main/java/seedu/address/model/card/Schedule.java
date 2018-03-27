package seedu.address.model.card;

import static java.lang.Math.log;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Holds the Schedule information for a Card
 */
public class Schedule {
    private final double lowerBoundRememberRate = 0.85;

    private LocalDateTime nextReview;
    private int learningPhase = 3;
    private int lastInterval = 1;
    private double easingFactor = 1.3;
    private double historicalEasingFactor = 1.3;
    private int success = 0;
    private int failure = 0;

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
        double successRate = success / (double) (success + failure);
        return successRate;
    }

    public double getHistoricalEasingFactor() {
        return historicalEasingFactor;
    }

    public int getLearningPhase() {
        return learningPhase;
    }

    /**
     * Feedback algorithm takes in whether the answer is correct.
     */
    public void feedback(boolean isSuccess) {
        if (isSuccess) {
            success++;
        } else {
            failure++;
        }

        int total = success + failure;
        double successRate = (double) success / (double) (total + 1);

        if (total >= learningPhase) {
            double newEasingFactor = historicalEasingFactor
                    * log(lowerBoundRememberRate)
                    / log(successRate);

            if (isSuccess) {
                easingFactor = Math.max(newEasingFactor, 1.1);
            } else {
                easingFactor = Math.min(newEasingFactor, 1.1);
            }

            double count = total - learningPhase + 1;
            double pastFactor = (count - 1.0) / count;
            double nextFactor = 1.0 / count;

            historicalEasingFactor =
                    historicalEasingFactor * pastFactor
                            + easingFactor * nextFactor;

            lastInterval = (int) Math.ceil(easingFactor * lastInterval);
        }
        nextReview = nextReview.plusDays((long) lastInterval);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Schedule // instanceof handles nulls
                && this.learningPhase == (((Schedule) other).learningPhase)
                && this.lowerBoundRememberRate
                == (((Schedule) other).lowerBoundRememberRate)
                && this.lastInterval == (((Schedule) other).lastInterval)
                && this.easingFactor == (((Schedule) other).easingFactor)
                && this.success == (((Schedule) other).success)
                && this.failure == (((Schedule) other).failure));
    }
}
