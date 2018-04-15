package seedu.flashy.logic.parser;

import static seedu.flashy.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import static seedu.flashy.logic.commands.ChangeThemeCommand.COMMAND_WORD;
import static seedu.flashy.logic.commands.ChangeThemeCommand.MESSAGE_USAGE;
import static seedu.flashy.logic.commands.CommandTestUtil.CORRESPONDING_THEME_INDEX_1;
import static seedu.flashy.logic.commands.CommandTestUtil.CORRESPONDING_THEME_INDEX_2;
import static seedu.flashy.logic.commands.CommandTestUtil.INVALID_THEME;
import static seedu.flashy.logic.commands.CommandTestUtil.VALID_THEME_1;
import static seedu.flashy.logic.commands.CommandTestUtil.VALID_THEME_2;
import static seedu.flashy.logic.parser.CliSyntax.PREFIX_THEME;
import static seedu.flashy.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.flashy.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.flashy.logic.parser.ParserUtil.MESSAGE_INVALID_THEME;

import org.junit.Test;

import seedu.flashy.logic.commands.ChangeThemeCommand;

//@@author yong-jie
public class ChangeThemeCommandParserTest {
    private static final String SPACE = " ";
    private ChangeThemeCommandParser parser = new ChangeThemeCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        assertParseSuccess(parser,
                COMMAND_WORD + SPACE + PREFIX_THEME + VALID_THEME_1,
                new ChangeThemeCommand(CORRESPONDING_THEME_INDEX_1));

        assertParseSuccess(parser,
                COMMAND_WORD + SPACE + PREFIX_THEME + VALID_THEME_2,
                new ChangeThemeCommand(CORRESPONDING_THEME_INDEX_2));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        final String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE);

        assertParseFailure(parser, "", expectedMessage);
    }

    @Test
    public void parse_invalidField_failure() {
        final String expectedMessage = MESSAGE_INVALID_THEME;

        assertParseFailure(parser,
                COMMAND_WORD + SPACE + PREFIX_THEME + INVALID_THEME,
                expectedMessage);
    }

}
