package seedu.flashy.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;

import seedu.flashy.commons.events.ui.ChangeThemeRequestEvent;
import seedu.flashy.ui.testutil.EventsCollectorRule;

//@@author yong-jie
public class ChangeThemeCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    @Test
    public void execute_changeTheme_success() {
        CommandResult result = new ChangeThemeCommand(0).execute();
        assertEquals(ChangeThemeCommand.MESSAGE_SUCCESS, result.feedbackToUser);
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof ChangeThemeRequestEvent);
        assertTrue(eventsCollectorRule.eventsCollector.getSize() == 1);
    }
}
