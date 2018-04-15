package seedu.flashy.logic.parser;

import static seedu.flashy.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.flashy.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.flashy.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.flashy.testutil.TypicalIndexes.INDEX_FIRST_TAG;

import org.junit.Test;

import seedu.flashy.logic.commands.SelectCommand;

/**
 * Test scope: similar to {@code DeleteCommandParserTest}.
 * @see DeleteCommandParserTest
 */
public class SelectCommandParserTest {

    private SelectCommandParser parser = new SelectCommandParser();

    @Test
    public void parse_validArgs_returnsSelectCommand() {
        assertParseSuccess(parser, "1", new SelectCommand(INDEX_FIRST_TAG));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));
    }
}
