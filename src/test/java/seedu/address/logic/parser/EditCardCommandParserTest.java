package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.BACK_DESC_CARD_1;
import static seedu.address.logic.commands.CommandTestUtil.FRONT_DESC_CARD_1;
import static seedu.address.logic.commands.CommandTestUtil.FRONT_DESC_CARD_2;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_FRONT_CARD;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BACK_CARD_1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FRONT_CARD_1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FRONT_CARD_2;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CARD;
import static seedu.address.testutil.TypicalTags.COMSCI_TAG;
import static seedu.address.testutil.TypicalTags.ENGLISH_TAG;

import java.util.Arrays;
import java.util.HashSet;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCardCommand;
import seedu.address.model.card.Card;
import seedu.address.model.tag.Name;
import seedu.address.testutil.EditCardDescriptorBuilder;

public class EditCardCommandParserTest {
    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCardCommand.MESSAGE_USAGE);

    private EditCardCommandParser parser = new EditCardCommandParser();

    //@@author jethrokuan
    @Test
    public void parser_allFieldsPresent_success() {
        EditCardCommand.EditCardDescriptor expected = new EditCardDescriptorBuilder()
                .withFront(VALID_FRONT_CARD_1)
                .withBack(VALID_BACK_CARD_1)
                .build();

        EditCardCommand.EditCardDescriptor expectedWithTags = new EditCardDescriptorBuilder(expected)
                .withTags(new HashSet<>(Arrays.asList(ENGLISH_TAG, COMSCI_TAG)))
                .build();

        // without tags
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + "1" + FRONT_DESC_CARD_1 + BACK_DESC_CARD_1,
                new EditCardCommand(INDEX_FIRST_CARD, expected));

        // with tags
        Object tags;
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + "1" + FRONT_DESC_CARD_1 + BACK_DESC_CARD_1
                + " " + PREFIX_TAG + ENGLISH_TAG.getName()
                        + " " +  PREFIX_TAG + COMSCI_TAG.getName(),
                new EditCardCommand(INDEX_FIRST_CARD, expectedWithTags));

    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid front
        assertParseFailure(parser, "1" + INVALID_FRONT_CARD, Card.MESSAGE_CARD_CONSTRAINTS);

        // invalid back
        assertParseFailure(parser, "1" + INVALID_FRONT_CARD, Card.MESSAGE_CARD_CONSTRAINTS);

        // invalid Tag name
        assertParseFailure(parser, "1" + INVALID_TAG_DESC, Name.MESSAGE_NAME_CONSTRAINTS);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + FRONT_DESC_CARD_1, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + FRONT_DESC_CARD_1, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_noFieldSpecified_failure() {
        assertParseFailure(parser, "1", EditCardCommand.MESSAGE_NOT_EDITED);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_CARD;
        String userInput = targetIndex.getOneBased() + FRONT_DESC_CARD_1;
        EditCardCommand.EditCardDescriptor descriptor = new EditCardDescriptorBuilder()
                .withFront(VALID_FRONT_CARD_1).build();
        EditCardCommand expectedCommand = new EditCardCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parser_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = INDEX_FIRST_CARD;
        String userInput = targetIndex.getOneBased() + FRONT_DESC_CARD_1 + FRONT_DESC_CARD_2;

        EditCardCommand.EditCardDescriptor descriptor = new EditCardDescriptorBuilder()
                .withFront(VALID_FRONT_CARD_2)
                .build();

        EditCardCommand expectedCommand = new EditCardCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
    //@@author
}
