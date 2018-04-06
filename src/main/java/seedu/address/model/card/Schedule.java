package seedu.address.model.card;

import static java.lang.Math.log;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Holds the ScheduleCommand information for a Card
 */
public class Schedule implements Comparable<Schedule> {

    public static final int VALID_CONFIDENCE_LEVEL_0 = 0;
    public static final int VALID_CONFIDENCE_LEVEL_1 = 1;
    public static final int VALID_CONFIDENCE_LEVEL_2 = 2;
    public static final int VALID_MIN_CONFIDENCE_LEVEL = 0;
    public static final int VALID_MAX_CONFIDENCE_LEVEL = 2;
    public static final String MESSAGE_ANSWER_CONSTRAINTS =
        "Confidence Levels should only be 0, 1 or 2";
    public static final String MESSAGE_DAY_CONSTRAINTS =
        "There are at most 31 and at least 1 day.";
    public static final String MESSAGE_MONTH_CONSTRAINTS =
        "There are at most 12 and at least 1 month.";
    public static final int INITIAL_LEARNING_PHASE = 3;
    public static final int INITIAL_LAST_INTERVAL = 1;
    public static final double INITIAL_EASING_FACTOR = 1.3;
    public static final double INITIAL_HISTORICAL_EASING_FACTOR = 1.3;

    private final double lowerBoundRememberRate = 0.85;

    private LocalDateTime nextReview;
    private int learningPhase = INITIAL_LEARNING_PHASE;
    private int lastInterval = INITIAL_LAST_INTERVAL;
    private double easingFactor = 1.3;
    private double historicalEasingFactor = 1.3;
    private int success = 0;
    private int failure = 0;

    public Schedule() {
        this.nextReview = LocalDate.now().atStartOfDay();
    }

    public Schedule(LocalDateTime date) {
        this.nextReview = date;
    }

    public LocalDateTime getNextReview() {
        return nextReview;
    }

    public void setNextReview(LocalDateTime date) {
        this.nextReview = date;
    }

    public void setRelativeNextReview(int days) {
        this.nextReview = LocalDate.now().atStartOfDay().plusDays(days);
    }

    public int getLastInterval() {
        return lastInterval;
    }

    public double getEasingFactor() {
        return easingFactor;
    }

    public double getSuccessRate() {
        double successRate = success / (double) (success + failure + 1);
        return successRate;
    }

    public double getHistoricalEasingFactor() {
        return historicalEasingFactor;
    }

    public int getLearningPhase() {
        return learningPhase;
    }

    public void setRelativeNextReviewToNow() {
        this.nextReview = LocalDateTime.now();
    }

    /**
     * Check if it is a valid confidence level between 0 1 2
     * @param confidenceLevel
     * @return true/false
     */
    public static boolean isValidConfidenceLevel(int confidenceLevel) {
        return confidenceLevel >= VALID_MIN_CONFIDENCE_LEVEL
            && confidenceLevel <= VALID_MAX_CONFIDENCE_LEVEL;
    }

    /**
     * Check if it is a valid confidence level between 0 1 2 string version
     * @param confidenceLevelString
     * @return true/false
     */
    public static boolean isValidConfidenceLevel(String confidenceLevelString)
        throws NumberFormatException {
        try {
            int confidenceLevel = Integer.parseInt(confidenceLevelString);
            return isValidConfidenceLevel(confidenceLevel);
        } catch (NumberFormatException nfe) {
            throw new NumberFormatException(nfe.getMessage());
        }
    }

    /**
     * Check if it is a valid Month between 1 to 12
     * @param month
     * @return true/false
     */
    public static boolean isValidMonth(int month) {
        return month >= 1 && month <= 12;
    }

    /**
     * Check if it is a valid day between 1 to 31
     * @param day
     * @return true/false
     */
    public static boolean isValidDay(int day) {
        return day >= 1 && day <= 31;
    }

    /**
     * Feedback router to switch between what to do given a certain
     * confidenceLevel input
     */
    public boolean feedbackHandlerRouter(int confidenceLevel) {
        checkArgument(isValidConfidenceLevel(confidenceLevel), MESSAGE_ANSWER_CONSTRAINTS);

        boolean tooEasy = false;
        switch (confidenceLevel) {
        case (0):
            // to push card to back of filtered queue
            setRelativeNextReviewToNow();
            break;
        case (1):
            feedback(false);
            setRelativeNextReviewToNow();
            break;
        case (2):
            feedback(true);
            tooEasy = true;
            break;
        default :
            break;
        }
        return tooEasy;
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

    @Override
    public int compareTo(Schedule otherSchedule) {
        return this.nextReview.compareTo(otherSchedule.getNextReview());
    }
}
