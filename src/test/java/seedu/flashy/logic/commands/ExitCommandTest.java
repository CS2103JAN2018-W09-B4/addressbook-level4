package seedu.flashy.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.flashy.logic.commands.ExitCommand.MESSAGE_EXIT_ACKNOWLEDGEMENT;

import org.junit.Rule;
import org.junit.Test;

import seedu.flashy.commons.events.ui.ExitAppRequestEvent;
import seedu.flashy.ui.testutil.EventsCollectorRule;

public class ExitCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    @Test
    public void execute_exit_success() {
        CommandResult result = new ExitCommand().execute();
        assertEquals(MESSAGE_EXIT_ACKNOWLEDGEMENT, result.feedbackToUser);
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof ExitAppRequestEvent);
        assertTrue(eventsCollectorRule.eventsCollector.getSize() == 1);
    }
}
