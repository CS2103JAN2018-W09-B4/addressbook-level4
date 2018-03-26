package seedu.address.model.card;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ScheduleTest {

    @Test
    public void feedback_getSuccessRate() {
        Schedule s = new Schedule();
        s.feedback(true);
        s.feedback(false);
        s.feedback(true);
        assertEquals(2.0 / 3.0, s.getSuccessRate(), 1);
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
        assertEquals(1.1, s.getEasingFactor(), 0.00000001);
        s.feedback(true);
        s.feedback(true);
        s.feedback(true);
        s.feedback(true);
        assertEquals(1.3569619443199672, s.getEasingFactor(), 0.00000001);
        s.feedback(false);
        s.feedback(false);
        s.feedback(false);
        s.feedback(false);
        assertEquals(0.28007138289996014, s.getEasingFactor(), 0.00000001);
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
        assertEquals(1.1597147845723643, s.getEasingFactor(), 0.00000001);
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
        assertEquals(0.3048048297281299, s.getEasingFactor(), 0.00000001);
    }

}
