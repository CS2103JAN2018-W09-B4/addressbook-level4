package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.BACK_DESC_CS2103T_CARD;
import static seedu.address.logic.commands.CommandTestUtil.BACK_DESC_MCQ_CARD;
import static seedu.address.logic.commands.CommandTestUtil.FRONT_DESC_CS2101_CARD;
import static seedu.address.logic.commands.CommandTestUtil.FRONT_DESC_CS2103T_CARD;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_BACK_CARD;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_FRONT_CARD;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BIOLOGY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_ENGLISH;
import static seedu.address.testutil.TypicalCards.CS2103T_CARD;
import static seedu.address.testutil.TypicalCards.ECONOMICS_CARD;
import static seedu.address.testutil.TypicalCards.ENGLISH_CARD;
import static seedu.address.testutil.TypicalCards.HISTORY_CARD;
import static seedu.address.testutil.TypicalCards.PHYSICS_CARD;
import static seedu.address.testutil.TypicalCards.PHYSICS_CARD_2;
import static seedu.address.testutil.TypicalTags.BIOLOGY_TAG;
import static seedu.address.testutil.TypicalTags.ENGLISH_TAG;
import static seedu.address.testutil.TypicalTags.KEYWORD_MATCHING_MIDTERMS;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddCardCommand;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.card.Card;
import seedu.address.model.card.exceptions.DuplicateCardException;
import seedu.address.testutil.CardUtil;

//@@author jethrokuan
public class AddCardCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void add() throws Exception {
        Model model = getModel();

        /* ------------------------ Perform add operations on the shown unfiltered list ----------------------------- */

        /* Case: add a tag to a non-empty address book, command with leading spaces and trailing spaces
         * -> added
         */
        Card toAdd = CS2103T_CARD;
        String command = "   " + AddCardCommand.COMMAND_WORD + "  " + FRONT_DESC_CS2103T_CARD + BACK_DESC_CS2103T_CARD;
        assertCommandSuccess(command, toAdd);

        /* Case: undo adding CS2103T card to the list -> CS2103T card deleted */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo adding CS2103T card to the list -> CS2103T card added again */
        command = RedoCommand.COMMAND_WORD;
        model.addCard(toAdd);
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: add a Card with all fields same as another card in the card bank -> rejected */
        command = AddCardCommand.COMMAND_WORD + FRONT_DESC_CS2103T_CARD + BACK_DESC_CS2103T_CARD;
        assertCommandFailure(command, AddCardCommand.MESSAGE_DUPLICATE_CARD);

        /* Case: add to empty address book -> added */
        model.resetData(new AddressBook());
        clearCardBank();

        model = getModel();
        command = AddCommand.COMMAND_WORD + NAME_DESC_BIOLOGY;
        model.addTag(BIOLOGY_TAG);
        expectedResultMessage = String.format(AddCommand.MESSAGE_SUCCESS, BIOLOGY_TAG);
        assertCommandSuccess(command, model, expectedResultMessage);

        command = AddCommand.COMMAND_WORD + NAME_DESC_ENGLISH;
        model.addTag(ENGLISH_TAG);
        expectedResultMessage = String.format(AddCommand.MESSAGE_SUCCESS, ENGLISH_TAG);
        assertCommandSuccess(command, model, expectedResultMessage);

        assertCommandSuccess(PHYSICS_CARD);
        assertCommandSuccess(PHYSICS_CARD_2);
        assertCommandSuccess(HISTORY_CARD);

        /* -------------------------- Perform add operation on the shown filtered list ------------------------------ */

        /* Case: filters the card list before adding -> added */
        showTagsWithName(KEYWORD_MATCHING_MIDTERMS);
        assertCommandSuccess(ENGLISH_CARD);

        /* ------------------------ Perform add operation while a tag card is selected --------------------------- */

        /* Case: selects first card in the card list, add a card-> added, card selection remains unchanged */
        selectTag(Index.fromOneBased(1));
        assertCommandSuccess(ECONOMICS_CARD);

        /* ----------------------------------- Perform invalid add operations --------------------------------------- */

        /* Case: add a duplicate card -> rejected */
        command = CardUtil.getAddCardCommand(ENGLISH_CARD);
        assertCommandFailure(command, AddCardCommand.MESSAGE_DUPLICATE_CARD);

        /* Case: missing front -> rejected */
        command = AddCardCommand.COMMAND_WORD + BACK_DESC_MCQ_CARD;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCardCommand.MESSAGE_USAGE));

        /* Case: missing back -> rejected */
        command = AddCardCommand.COMMAND_WORD + FRONT_DESC_CS2101_CARD;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCardCommand.MESSAGE_USAGE));

        /* Case: invalid keyword -> rejected */
        command = "adds " + CardUtil.getCardDetails(toAdd);
        assertCommandFailure(command, Messages.MESSAGE_UNKNOWN_COMMAND);

        /* Case: invalid front -> rejected */
        command = AddCardCommand.COMMAND_WORD + INVALID_FRONT_CARD + BACK_DESC_CS2103T_CARD;
        assertCommandFailure(command, Card.MESSAGE_CARD_CONSTRAINTS);

        /* Case: invalid back -> rejected */
        command = AddCardCommand.COMMAND_WORD + FRONT_DESC_CS2103T_CARD + INVALID_BACK_CARD;
        assertCommandFailure(command, Card.MESSAGE_CARD_CONSTRAINTS);
    }

    /**
     * Executes the {@code AddCardCommand} that adds {@code toAdd} to the model and asserts that the,<br>
     * 1. Command box displays an empty string.<br>
     * 2. Command box has the default style class.<br>
     * 3. Result display box displays the success message of executing {@code AddCardCommand} with the details of
     * {@code toAdd}.<br>
     * 4. {@code Model}, {@code Storage} and {@code TagListPanel} equal to the corresponding components in
     * the current model added with {@code toAdd}.<br>
     * 5. Browser url and selected card remain unchanged.<br>
     * 6. Status bar's sync status changes.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(Card toAdd) throws DuplicateCardException {
        assertCommandSuccess(CardUtil.getAddCardCommand(toAdd), toAdd);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(Card)}. Executes {@code command}
     * instead.
     * @see AddCardCommandSystemTest#assertCommandSuccess(Card)
     */
    private void assertCommandSuccess(String command, Card toAdd) throws DuplicateCardException {
        Model expectedModel = getModel();
        expectedModel.addCard(toAdd);

        String expectedResultMessage = String.format(AddCardCommand.MESSAGE_SUCCESS, toAdd);

        assertCommandSuccess(command, expectedModel, expectedResultMessage);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Tag)} except asserts that
     * the,<br>
     * 1. Result display box displays {@code expectedResultMessage}.<br>
     * 2. {@code Model}, {@code Storage} and {@code cardListPanel} equal to the corresponding components in
     * {@code expectedModel}.<br>
     * @see AddCardCommandSystemTest#assertCommandSuccess(String, Card)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and asserts that the,<br>
     * 1. Command box displays {@code command}.<br>
     * 2. Command box has the error style class.<br>
     * 3. Result display box displays {@code expectedResultMessage}.<br>
     * 4. {@code Model}, {@code Storage} and {@code TagListPanel} remain unchanged.<br>
     * 5. Browser url, selected card and status bar remain unchanged.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
//@@author
