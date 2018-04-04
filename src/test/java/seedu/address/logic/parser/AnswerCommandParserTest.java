package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.AnswerCommand;

public class AnswerCommandParserTest {
    private AnswerCommandParser parser = new AnswerCommandParser();

    @Test
    public void parse_confidenceLevel_success() {
        assertParseSuccess(parser, " c/0",
            new AnswerCommand(0));
    }
}
