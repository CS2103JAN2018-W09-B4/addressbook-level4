package seedu.flashy.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.flashy.logic.commands.HelpCommand.SHOWING_HELP_MESSAGE;

import org.junit.Rule;
import org.junit.Test;

import seedu.flashy.commons.events.ui.ShowHelpRequestEvent;
import seedu.flashy.ui.testutil.EventsCollectorRule;

public class HelpCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    @Test
    public void execute_help_success() {
        CommandResult result = new HelpCommand().execute();
        assertEquals(SHOWING_HELP_MESSAGE, result.feedbackToUser);
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof ShowHelpRequestEvent);
        assertTrue(eventsCollectorRule.eventsCollector.getSize() == 1);
    }
}
