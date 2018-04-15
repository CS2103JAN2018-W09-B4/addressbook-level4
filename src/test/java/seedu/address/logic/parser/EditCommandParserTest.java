package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_COMSCI;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_ENGLISH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_COMSCI;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_ENGLISH;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_TAG;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_TAG;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_TAG;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditTagDescriptor;
import seedu.address.model.tag.Name;
import seedu.address.testutil.EditTagDescriptorBuilder;

public class EditCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE);

    private EditCommandParser parser = new EditCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_NAME_ENGLISH, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }


    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + NAME_DESC_ENGLISH, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + NAME_DESC_ENGLISH, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_NAME_DESC, Name.MESSAGE_NAME_CONSTRAINTS); // invalid name
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_TAG;
        String userInput = targetIndex.getOneBased()
                + NAME_DESC_ENGLISH;

        EditCommand.EditTagDescriptor descriptor = new EditTagDescriptorBuilder().withName(VALID_NAME_ENGLISH)
                .build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_TAG;
        String userInput = targetIndex.getOneBased() + NAME_DESC_COMSCI;

        EditCommand.EditTagDescriptor descriptor = new EditTagDescriptorBuilder()
                .withName(VALID_NAME_COMSCI)
                .build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        Index targetIndex = INDEX_THIRD_TAG;
        String userInput = targetIndex.getOneBased() + NAME_DESC_ENGLISH;
        EditTagDescriptor descriptor = new EditTagDescriptorBuilder().withName(VALID_NAME_ENGLISH).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = INDEX_FIRST_TAG;
        String userInput = targetIndex.getOneBased() + NAME_DESC_COMSCI + NAME_DESC_ENGLISH;

        EditCommand.EditTagDescriptor descriptor = new EditTagDescriptorBuilder()
                .withName(VALID_NAME_ENGLISH)
                .build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
