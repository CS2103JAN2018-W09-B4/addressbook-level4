package seedu.address.logic.commands;

import static org.junit.Assert.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.card.Card;
import seedu.address.testutil.CardBuilder;

public class AddCardCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void equals() {
        Card cardOne = new CardBuilder().withFront("One")
                .withBack("One").withTags("One").build();
        Card cardTwo = new CardBuilder().withFront("Two")
                .withBack("Two").withTags("Two").build();

        AddCardCommand addCardOneCommand = new AddCardCommand(cardOne);
        AddCardCommand addCardTwoCommand = new AddCardCommand(cardTwo);

        // same object -> returns true
        assertTrue(addCardOneCommand.equals(addCardOneCommand));

        // same values -> returns true
        AddCardCommand addCardOneCommandCopy = new AddCardCommand(cardOne);
        assertTrue(addCardOneCommand.equals(addCardOneCommandCopy));

        // different types -> returns false
        assertFalse(addCardOneCommand.equals(1));

        // null -> returns false
        assertFalse(addCardOneCommand.equals(null));

        // different person -> returns false
        assertFalse(addCardOneCommand.equals(addCardTwoCommand));
    }
}
