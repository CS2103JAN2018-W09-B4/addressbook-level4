package seedu.flashy.logic.parser;

import static seedu.flashy.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.flashy.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.flashy.logic.commands.ListCommand;

//@@author jethrokuan
public class ListCommandParserTest {
    private ListCommandParser parser = new ListCommandParser();

    @Test
    public void parse_validValues_success() {
        assertParseSuccess(parser, "", new ListCommand(false));
        assertParseSuccess(parser, "  ", new ListCommand(false));
        assertParseSuccess(parser, ListCommandParser.PREFIX_NO_TAGS_ONLY,
                new ListCommand(true));
        assertParseSuccess(parser, "  " + ListCommandParser.PREFIX_NO_TAGS_ONLY + "  ",
                new ListCommand(true));
    }

    @Test
    public void parse_invalidValues_failure() {
        String expectedMessage = ListCommandParser.MESSAGE_PARSE_FAILURE;
        assertParseFailure(parser, "-c", expectedMessage);
        assertParseFailure(parser, "hello", expectedMessage);
    }
}
