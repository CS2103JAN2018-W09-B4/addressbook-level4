package seedu.flashy.logic.parser;

import static seedu.flashy.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.flashy.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.flashy.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.flashy.testutil.TypicalIndexes.INDEX_FIRST_CARD;

import org.junit.Test;

import seedu.flashy.logic.commands.SelectCardCommand;

//@@author yong-jie
/**
 * Tests the parsing functionality of {@code SelectCardCommandParser}
 */
public class SelectCardCommandParserTest {

    private SelectCardCommandParser parser = new SelectCardCommandParser();

    @Test
    public void parse_validArgs_returnsSelectCardCommand() {
        assertParseSuccess(parser, "1", new SelectCardCommand(INDEX_FIRST_CARD));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCardCommand.MESSAGE_USAGE));
    }
}
