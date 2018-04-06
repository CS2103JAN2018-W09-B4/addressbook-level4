package seedu.address.logic.parser;

import static seedu.address.logic.parser.CliSyntax.PREFIX_CONFIDENCE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.model.card.Schedule.VALID_CONFIDENCE_LEVEL_0;
import static seedu.address.model.card.Schedule.VALID_CONFIDENCE_LEVEL_1;
import static seedu.address.model.card.Schedule.VALID_CONFIDENCE_LEVEL_2;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.commands.AnswerCommand;

public class AnswerCommandParserTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private AnswerCommandParser parser = new AnswerCommandParser();

    @Test
    public void parse_nonsenseArguments_throwIllegalArgumentException() throws Exception {
        thrown.expect(IllegalArgumentException.class);
        assertParseSuccess(parser, " " + PREFIX_CONFIDENCE
            + PREFIX_CONFIDENCE, new AnswerCommand(0));
    }

    @Test
    public void parse_outOfRangeMessage_failure() {
        assertParseFailure(parser, " " + PREFIX_CONFIDENCE
            + "99", "Confidence Levels should only be 0, 1 or 2");
    }

    @Test
    public void parse_confidenceLevel_success() {
        assertParseSuccess(parser, " " + PREFIX_CONFIDENCE
            + VALID_CONFIDENCE_LEVEL_0, new AnswerCommand(0));
        assertParseSuccess(parser, " " + PREFIX_CONFIDENCE
            + VALID_CONFIDENCE_LEVEL_1, new AnswerCommand(1));
        assertParseSuccess(parser, " " + PREFIX_CONFIDENCE
            + VALID_CONFIDENCE_LEVEL_2, new AnswerCommand(2));
    }
}
