package seedu.address.model.card;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;

//@@author pukipuki
public class ScheduleTest {
    private static final double delta = 0.000001;
    private Schedule schedule;
    private LocalDateTime todaysDate;

    @Before
    public void setUp() {
        todaysDate = LocalDate.now().atStartOfDay();
        schedule = new Schedule();
    }

    @Test
    public void getNextReview_success() {
        assertEquals(schedule.getNextReview(), todaysDate);
    }

    @Test
    public void setNextReview_success() {
        LocalDateTime expectedDate = todaysDate.plusYears(1L);
        schedule.setNextReview(expectedDate);
        assertEquals(schedule.getNextReview(), expectedDate);
    }

    @Test
    public void getLastInterval_success() {
        int actual = schedule.getLastInterval();
        assertEquals(actual, Schedule.INITIAL_LAST_INTERVAL);
    }

    @Test
    public void getEasingFactor_success() {
        double actual = schedule.getEasingFactor();
        assertEquals(actual, Schedule.INITIAL_EASING_FACTOR, delta);
    }

    @Test
    public void getHistoricalEasingFactor_success() {
        double actual = schedule.getHistoricalEasingFactor();
        assertEquals(actual, Schedule.INITIAL_HISTORICAL_EASING_FACTOR, delta);
    }

    @Test
    public void getLearningPhase_success() {
        int actual = schedule.getLearningPhase();
        assertEquals(actual, Schedule.INITIAL_LEARNING_PHASE);
    }

    @Test
    public void feedback_getSuccessRate() {
        Schedule s = new Schedule();
        s.feedback(true);
        s.feedback(false);
        s.feedback(true);
        assertEquals(2.0 / 4.0, s.getSuccessRate(), delta);
    }

    @Test
    public void feedback_learningPhaseTest() {
        Schedule s = new Schedule();
        int learningPhase = s.getLearningPhase();
        for (int i = 0; i < learningPhase - 1; i++) {
            s.feedback(true);
            assertEquals(1, s.getLastInterval());
        }
    }

    @Test
    public void feedback_getEasingFactor() {
        Schedule s = new Schedule();
        int learningPhase = s.getLearningPhase();
        for (int i = 0; i < learningPhase - 1; i++) {
            s.feedback(true);
            assertEquals(1, s.getLastInterval());
        }
        s.feedback(true);
        assertEquals(1.1, s.getEasingFactor(), delta);
        s.feedback(true);
        s.feedback(true);
        s.feedback(true);
        s.feedback(true);
        assertEquals(1.3569619443199672, s.getEasingFactor(), delta);
        s.feedback(false);
        s.feedback(false);
        s.feedback(false);
        s.feedback(false);
        assertEquals(0.28007138289996014, s.getEasingFactor(), delta);
    }

    @Test
    public void feedback_algoPositive() {
        Schedule s = new Schedule();
        int learningPhase = s.getLearningPhase();
        for (int i = 0; i < learningPhase - 1; i++) {
            s.feedback(true);
            assertEquals(1, s.getLastInterval());
        }
        s.feedback(true);
        s.feedback(true);
        s.feedback(true);
        s.feedback(true);
        assertEquals(1.1597147845723643, s.getEasingFactor(), delta);
    }

    @Test
    public void feedback_algoNegative() {
        Schedule s = new Schedule();
        int learningPhase = s.getLearningPhase();
        for (int i = 0; i < learningPhase - 1; i++) {
            s.feedback(true);
            assertEquals(1, s.getLastInterval());
        }
        s.feedback(false);
        assertEquals(0.3048048297281299, s.getEasingFactor(), delta);
    }
}
