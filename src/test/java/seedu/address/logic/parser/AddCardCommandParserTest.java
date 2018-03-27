package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.BACK_DESC_CARD_1;
import static seedu.address.logic.commands.CommandTestUtil.FRONT_DESC_CARD_1;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_BACK_CARD;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_FRONT_CARD;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BACK_CARD_1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FRONT_CARD_1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_COMSCI;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_ENGLISH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import seedu.address.logic.commands.AddCardCommand;
import seedu.address.model.card.Card;
import seedu.address.model.tag.Name;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.CardBuilder;


public class AddCardCommandParserTest {
    private AddCardCommandParser parser = new AddCardCommandParser();

    //@@author jethrokuan
    @Test
    public void parse_allFieldsPresent_success() {
        Card expectedCard = new CardBuilder()
                .withFront(VALID_FRONT_CARD_1)
                .withBack(VALID_BACK_CARD_1)
                .build();

        Set<Tag> expectedTags = new HashSet<>(Arrays.asList(
                new Tag(new Name(VALID_NAME_ENGLISH)),
                new Tag(new Name(VALID_NAME_COMSCI)
        )));

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + FRONT_DESC_CARD_1 + BACK_DESC_CARD_1,
                new AddCardCommand(expectedCard, new HashSet<>()));

        // with tags
        String tagString = " " + PREFIX_TAG + VALID_NAME_ENGLISH + " " + PREFIX_TAG + VALID_NAME_COMSCI;
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + FRONT_DESC_CARD_1 + BACK_DESC_CARD_1 + tagString,
                new AddCardCommand(expectedCard, expectedTags));
    }
    //@@author

    //@@author jethrokuan
    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCardCommand.MESSAGE_USAGE);

        // missing front prefix
        assertParseFailure(parser, FRONT_DESC_CARD_1,
                expectedMessage);

        // missing back prefix
        assertParseFailure(parser, BACK_DESC_CARD_1,
                expectedMessage);
    }
    //@@author

    //@@author jethrokuan
    @Test
    public void parse_invalidValue_failure() {
        // invalid front
        assertParseFailure(parser, INVALID_FRONT_CARD + BACK_DESC_CARD_1, Card.MESSAGE_CARD_CONSTRAINTS);

        // invalid back
        assertParseFailure(parser, FRONT_DESC_CARD_1 + INVALID_BACK_CARD, Card.MESSAGE_CARD_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + FRONT_DESC_CARD_1 + BACK_DESC_CARD_1,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCardCommand.MESSAGE_USAGE));
    }
    //@@author
}
