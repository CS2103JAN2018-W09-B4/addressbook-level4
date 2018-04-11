package systemtests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.BACK_DESC_CHEMISTRY_CARD;
import static seedu.address.logic.commands.CommandTestUtil.FRONT_DESC_CHEMISTRY_CARD;
import static seedu.address.logic.commands.CommandTestUtil.FRONT_DESC_CS2101_CARD;
import static seedu.address.logic.commands.CommandTestUtil.FRONT_DESC_MATHEMATICS_CARD;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_FRONT_CARD;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FRONT_CS2101_CARD;
import static seedu.address.testutil.TypicalCards.CHEMISTRY_CARD;
import static seedu.address.testutil.TypicalCards.MATHEMATICS_CARD;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CARD;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCardCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.card.Card;
import seedu.address.model.card.exceptions.CardNotFoundException;
import seedu.address.model.card.exceptions.DuplicateCardException;
import seedu.address.testutil.CardBuilder;

//@@author shawnclq
public class EditCardCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void edit() throws Exception {
        Model model = getModel();

        /* ----------------- Performing edit operation while an unfiltered list is being shown ---------------------- */

        /* Case: edit all fields, command with leading spaces, trailing spaces and multiple spaces between each field
         * -> edited
         */
        Index index = INDEX_FIRST_CARD;
        String command = " " + EditCardCommand.COMMAND_WORD + "  " + index.getOneBased() + "  "
                + FRONT_DESC_CS2101_CARD;
        Card editedCard = new CardBuilder().withFront(VALID_FRONT_CS2101_CARD).withBack(MATHEMATICS_CARD.getBack())
                .build();
        assertCommandSuccess(command, index, editedCard);

        /* Case: undo editing the last card in the list -> last card restored */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo editing the last card in the list -> last card edited again */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        model.updateCard(
                getModel().getFilteredCardList().get(INDEX_FIRST_CARD.getZeroBased()), editedCard);
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: edit a card with new values same as existing values -> edited */
        command = EditCardCommand.COMMAND_WORD + " " + index.getOneBased() + FRONT_DESC_MATHEMATICS_CARD;
        assertCommandSuccess(command, index, MATHEMATICS_CARD);

        Card cardToEdit = getModel().getFilteredCardList().get(index.getZeroBased());
        editedCard = new CardBuilder(cardToEdit).build();

        /* --------------------------------- Performing invalid edit operation -------------------------------------- */

        /* Case: invalid index (0) -> rejected */
        assertCommandFailure(EditCardCommand.COMMAND_WORD + " 0" + FRONT_DESC_CS2101_CARD,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditCardCommand.MESSAGE_USAGE));

        /* Case: invalid index (-1) -> rejected */
        assertCommandFailure(EditCardCommand.COMMAND_WORD + " -1" + FRONT_DESC_CS2101_CARD,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditCardCommand.MESSAGE_USAGE));

        /* Case: invalid index (size + 1) -> rejected */
        int invalidIndex = getModel().getFilteredCardList().size() + 1;
        assertCommandFailure(EditCardCommand.COMMAND_WORD + " " + invalidIndex + FRONT_DESC_CS2101_CARD,
                Messages.MESSAGE_INVALID_CARD_DISPLAYED_INDEX);

        /* Case: missing index -> rejected */
        assertCommandFailure(EditCardCommand.COMMAND_WORD + FRONT_DESC_CS2101_CARD,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditCardCommand.MESSAGE_USAGE));

        /* Case: missing all fields -> rejected */
        assertCommandFailure(EditCardCommand.COMMAND_WORD + " " + INDEX_FIRST_CARD.getOneBased(),
                EditCardCommand.MESSAGE_NOT_EDITED);

        /* Case: invalid name -> rejected */
        assertCommandFailure(EditCardCommand.COMMAND_WORD + " "
                        + INDEX_FIRST_CARD.getOneBased() + INVALID_FRONT_CARD, Card.MESSAGE_CARD_CONSTRAINTS);

        /* Case: edit a card with new values same as another card's values -> rejected */
        assertTrue(getModel().getAddressBook().getCardList().contains(CHEMISTRY_CARD));
        index = INDEX_FIRST_CARD;
        assertFalse(getModel().getFilteredCardList().get(index.getZeroBased()).equals(CHEMISTRY_CARD));
        command = EditCardCommand.COMMAND_WORD + " " + index.getOneBased()
                + FRONT_DESC_CHEMISTRY_CARD + BACK_DESC_CHEMISTRY_CARD;
        assertCommandFailure(command, EditCardCommand.MESSAGE_DUPLICATE_CARD);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Index, Card, Index)} except that
     * the browser url and selected card remain unchanged.
     * @param toEdit the index of the current model's filtered list
     * @see EditCardCommandSystemTest#assertCommandSuccess(String, Index, Card, Index)
     */
    private void assertCommandSuccess(String command, Index toEdit, Card editedCard) {
        assertCommandSuccess(command, toEdit, editedCard, null);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String, Index)} and in addition,<br>
     * 1. Asserts that result display box displays the success message of executing {@code EditCardCommand}.<br>
     * 2. Asserts that the model related components are updated to reflect the card at index {@code toEdit} being
     * updated to values specified {@code editedCard}.<br>
     * @param toEdit the index of the current model's filtered list.
     * @see EditCardCommandSystemTest#assertCommandSuccess(String, Model, String, Index)
     */

    private void assertCommandSuccess(String command, Index toEdit, Card editedCard,
                                      Index expectedSelectedCardIndex) {
        Model expectedModel = getModel();
        try {
            expectedModel.updateCard(
                    expectedModel.getFilteredCardList().get(toEdit.getZeroBased()), editedCard);
            //expectedModel.updateFilteredCardList(PREDICATE_SHOW_ALL_CARDS);
        } catch (DuplicateCardException | CardNotFoundException e) {
            throw new IllegalArgumentException(
                    "editedCard is a duplicate in expectedModel, or it isn't found in the model.");
        }

        assertCommandSuccess(command, expectedModel,
                String.format(EditCardCommand.MESSAGE_EDIT_CARD_SUCCESS, editedCard), expectedSelectedCardIndex);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String, Index)} except that the
     * browser url and selected card remain unchanged.
     * @see EditCardCommandSystemTest#assertCommandSuccess(String, Model, String, Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        assertCommandSuccess(command, expectedModel, expectedResultMessage, null);
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays an empty string.<br>
     * 2. Asserts that the result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the model related components equal to {@code expectedModel}.<br>
     * 4. Asserts that the browser url and selected card update accordingly depending on the card at
     * {@code expectedSelectedCardIndex}.<br>
     * 5. Asserts that the status bar's sync status changes.<br>
     * 6. Asserts that the command box has the default style class.<br>
     * Verifications 1 to 3 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */

    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage,
                                      Index expectedSelectedCardIndex) {
        executeCommand(command);
        //expectedModel.updateFilteredCardList(PREDICATE_SHOW_ALL_CARDS);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays {@code command}.<br>
     * 2. Asserts that result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the model related components equal to the current model.<br>
     * 4. Asserts that the browser url, selected card and status bar remain unchanged.<br>
     * 5. Asserts that the command box has the error style.<br>
     * Verifications 1 to 3 are performed by
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
