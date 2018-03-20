package seedu.address.model.card;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ScheduleTest {

    @Test
    public void isFeedbackSuccessful_getSuccessRate() {
        Schedule s = new Schedule();
        s.isFeedbackSuccessful(true);
        s.isFeedbackSuccessful(false);
        s.isFeedbackSuccessful(true);
        assertEquals(s.getSuccessRate(), 2.0 / 3.0, 1);
    }

    @Test
    public void isFeedbackSuccessful_getEasingFactor() {
        Schedule s = new Schedule();
        assertEquals(s.getEasingFactor(), 1.0, 3);
        s.isFeedbackSuccessful(true);
        assertEquals(s.getEasingFactor(), 1.0, 3);
        s.isFeedbackSuccessful(false);
        assertEquals(s.getEasingFactor(), 1.0, 3);
        s.isFeedbackSuccessful(true);
        assertEquals(s.getEasingFactor(), 1.0, 3);
        s.isFeedbackSuccessful(true);
        assertEquals(s.getEasingFactor(), 0.5649254682876186, 3);
    }
}
