package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADD_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BACK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DAY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FRONT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MONTH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMOVE_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_YEAR;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.card.Card;
import seedu.address.model.tag.NameContainsKeywordsPredicate;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.exceptions.TagNotFoundException;
import seedu.address.testutil.EditCardDescriptorBuilder;
import seedu.address.testutil.EditTagDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_ENGLISH = "English";
    public static final String VALID_NAME_COMSCI = "Computer Science";
    public static final String VALID_NAME_SOCIOLOGY = "Sociology";
    public static final String VALID_NAME_BIOLOGY = "Biology Midterms";

    public static final String NAME_DESC_ENGLISH = " " + PREFIX_NAME + VALID_NAME_ENGLISH;
    public static final String NAME_DESC_COMSCI = " " + PREFIX_NAME + VALID_NAME_COMSCI;
    public static final String NAME_DESC_SOCIOLOGY = " " + PREFIX_NAME + VALID_NAME_SOCIOLOGY;
    public static final String NAME_DESC_BIOLOGY = " " + PREFIX_NAME + VALID_NAME_BIOLOGY;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "English&"; // '&' not allowed in names
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "English&";

    public static final String INVALID_ADD_TAG_DESC = " " + PREFIX_ADD_TAG + "English&";
    public static final String INVALID_REMOVE_TAG_DESC = " " + PREFIX_REMOVE_TAG + "English&";

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditTagDescriptor DESC_ENGLISH;
    public static final EditCommand.EditTagDescriptor DESC_COMSCI;

    public static final EditCardCommand.EditCardDescriptor CS2103T_CARD;
    public static final EditCardCommand.EditCardDescriptor CS2101_CARD;

    public static final String VALID_FRONT_CS2103T_CARD = "What is OOP?";
    public static final String VALID_BACK_CS2103T_CARD = "A programming paradigm";
    public static final String VALID_FRONT_CS2101_CARD = "What is the main point of the class?";
    public static final String VALID_BACK_CS2101_CARD = "To learn to be audience-centred";
    public static final String VALID_MCQ_FRONT = "When is National Day in Singapore?";
    public static final String VALID_MCQ_BACK = "3";
    public static final String VALID_MCQ_OPTION_1 = "10th August";
    public static final String VALID_MCQ_OPTION_2 = "11th August";
    public static final String VALID_MCQ_OPTION_3 = "9th August";
    public static final List<String> VALID_MCQ_OPTION_SET = Arrays.asList(
                    new String[]{VALID_MCQ_OPTION_1, VALID_MCQ_OPTION_2, VALID_MCQ_OPTION_3});
    public static final String VALID_FILLBLANKS_BACK = "square";
    public static final String VALID_FILLBLANKS_FRONT = "A __ is a four sided polygon with equal sides meeting"
            + "at right angles.";

    public static final String FRONT_DESC_MCQ_CARD = " " + PREFIX_FRONT + VALID_MCQ_FRONT;
    public static final String BACK_DESC_MCQ_CARD = " " + PREFIX_BACK + VALID_MCQ_BACK;
    public static final String OPTION_1_DESC_MCQ_CARD = " " + PREFIX_OPTION + VALID_MCQ_OPTION_1;
    public static final String OPTION_2_DESC_MCQ_CARD = " " + PREFIX_OPTION + VALID_MCQ_OPTION_2;
    public static final String OPTION_3_DESC_MCQ_CARD = " " + PREFIX_OPTION + VALID_MCQ_OPTION_3;
    public static final String BACK_DESC_FILLBLANKS_CARD = " " + PREFIX_BACK + VALID_FILLBLANKS_BACK;
    public static final String FRONT_DESC_FILLBLANKS_CARD = " " + PREFIX_FRONT + VALID_FILLBLANKS_FRONT;

    public static final String INVALID_FRONT_CARD = " " + PREFIX_FRONT; // empty string not allowed
    public static final String INVALID_BACK_CARD = " " + PREFIX_BACK; // empty string not allowed
    public static final String INVALID_MCQ_CARD_BACK = " " + PREFIX_BACK + "Hello World"; // empty string not allowed
    public static final String INVALID_MCQ_CARD_OPTION = " " + PREFIX_OPTION; // empty string not allowed
    public static final String INVALID_FILLBLANKS_CARD_BACK = " " + PREFIX_BACK + VALID_FILLBLANKS_BACK
            + ", Extra answer"; // empty string not allowed

    public static final String FRONT_DESC_CS2103T_CARD = " " + PREFIX_FRONT + VALID_FRONT_CS2103T_CARD;
    public static final String FRONT_DESC_CS2101_CARD = " " + PREFIX_FRONT + VALID_FRONT_CS2101_CARD;
    public static final String BACK_DESC_CS2103T_CARD = " " + PREFIX_BACK + VALID_BACK_CS2103T_CARD;
    public static final String BACK_DESC_CS2101_CARD = " " + PREFIX_BACK + VALID_BACK_CS2101_CARD;

    public static final String VALID_THEME_1 = "light";
    public static final String VALID_THEME_2 = "dark";
    public static final Integer CORRESPONDING_THEME_INDEX_1 = 0;
    public static final Integer CORRESPONDING_THEME_INDEX_2 = 1;

    public static final String[] LIST_DAY_MONTH_YEAR = new String[]{
        PREFIX_DAY.toString(), PREFIX_MONTH.toString(), PREFIX_YEAR.toString()};
    public static final long[] LIST_VALID_DAY_MONTH_YEAR = new long[]{1L, 1L, 1L};
    public static final String[] LIST_PREFIX_RUBBISH = new String[]{
        PREFIX_DAY.toString() + "RUBBISH",
        PREFIX_MONTH.toString() + "RUBBISH",
        PREFIX_YEAR.toString() + "RUBBISH"
    };
    public static final String INVALID_29FEBRUARY = ""
        + " " + PREFIX_DAY + "29"
        + " " + PREFIX_MONTH + "2"
        + " " + PREFIX_YEAR + "2018";
    public static final String INVALID_30FEBRUARY = ""
        + " " + PREFIX_DAY + "30"
        + " " + PREFIX_MONTH + "2";
    public static final String INVALID_32MARCH = ""
        + " " + PREFIX_DAY + "32"
        + " " + PREFIX_MONTH + "3";
    public static final String INVALID_31APRIL = ""
        + " " + PREFIX_DAY + "31"
        + " " + PREFIX_MONTH + "4";
    public static final String INVALID_32DAY_OF_MONTH = ""
        + " " + PREFIX_DAY + "32";
    public static final String INVALID_0DAY_OF_MONTH = ""
        + " " + PREFIX_DAY + "0";

    public static final String INVALID_THEME = "solarized";

    static {
        DESC_ENGLISH = new EditTagDescriptorBuilder().withName(VALID_NAME_ENGLISH)
            .build();
        DESC_COMSCI = new EditTagDescriptorBuilder().withName(VALID_NAME_COMSCI)
            .build();
    }

    static {
        CS2103T_CARD = new EditCardDescriptorBuilder().withFront(VALID_FRONT_CS2103T_CARD)
                .withBack(VALID_BACK_CS2103T_CARD)
                .build();
        CS2101_CARD = new EditCardDescriptorBuilder().withFront(VALID_FRONT_CS2101_CARD)
                .withBack(VALID_BACK_CS2101_CARD)
                .build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the result message matches {@code expectedMessage} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
                                            Model expectedModel) {
        try {
            CommandResult result = command.execute();
            assertEquals(expectedMessage, result.feedbackToUser);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the address book and the filtered tag list in the {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        AddressBook expectedAddressBook = new AddressBook(actualModel.getAddressBook());
        List<Tag> expectedFilteredList = new ArrayList<>(actualModel.getFilteredTagList());

        try {
            command.execute();
            fail("The expected CommandException was not thrown.");
        } catch (CommandException e) {
            assertEquals(expectedMessage, e.getMessage());
            assertEquals(expectedAddressBook, actualModel.getAddressBook());
            assertEquals(expectedFilteredList, actualModel.getFilteredTagList());
        }
    }

    public static void assertEqualCardId(Card targetCard, Card editedCard) {
        assertEquals(targetCard.getId(), editedCard.getId());
    }

    /**
     * Updates {@code model}'s filtered list to show only the tag at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showTagAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredTagList().size());

        Tag tag = model.getFilteredTagList().get(targetIndex.getZeroBased());
        final String[] splitName = tag.getName().fullName.split("\\s+");
        model.updateFilteredTagList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredTagList().size());
    }

    /**
     * Deletes the first tag in {@code model}'s filtered list from {@code model}'s address book.
     */
    public static void deleteFirstTag(Model model) {
        Tag firstTag = model.getFilteredTagList().get(0);
        try {
            model.deleteTag(firstTag);
        } catch (TagNotFoundException pnfe) {
            throw new AssertionError("Tag in filtered list must exist in model.", pnfe);
        }
    }

    /**
     * Returns an {@code UndoCommand} with the given {@code model} and {@code undoRedoStack} set.
     */
    public static UndoCommand prepareUndoCommand(Model model, UndoRedoStack undoRedoStack) {
        UndoCommand undoCommand = new UndoCommand();
        undoCommand.setData(model, new CommandHistory(), undoRedoStack);
        return undoCommand;
    }

    /**
     * Returns a {@code RedoCommand} with the given {@code model} and {@code undoRedoStack} set.
     */
    public static RedoCommand prepareRedoCommand(Model model, UndoRedoStack undoRedoStack) {
        RedoCommand redoCommand = new RedoCommand();
        redoCommand.setData(model, new CommandHistory(), undoRedoStack);
        return redoCommand;
    }


}
