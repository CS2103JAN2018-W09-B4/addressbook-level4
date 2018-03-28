package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.ObservableList;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.card.Card;
import seedu.address.model.card.exceptions.CardNotFoundException;
import seedu.address.model.card.exceptions.DuplicateCardException;
import seedu.address.model.cardtag.DuplicateEdgeException;
import seedu.address.model.cardtag.EdgeNotFoundException;
import seedu.address.model.tag.AddTagResult;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.exceptions.DuplicateTagException;
import seedu.address.model.tag.exceptions.TagNotFoundException;
import seedu.address.testutil.TagBuilder;

public class AddCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor_nullTag_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddCommand(null);
    }

    @Test
    public void execute_tagAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingTagAdded modelStub = new ModelStubAcceptingTagAdded();
        Tag validTag = new TagBuilder().build();

        CommandResult commandResult = getAddCommandForTag(validTag, modelStub).execute();

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, validTag), commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validTag), modelStub.tagsAdded);
    }

    @Test
    public void equals() {
        Tag alice = new TagBuilder().withName("Alice").build();
        Tag bob = new TagBuilder().withName("Bob").build();
        AddCommand addAliceCommand = new AddCommand(alice);
        AddCommand addBobCommand = new AddCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddCommand addAliceCommandCopy = new AddCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different tag -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }

    /**
     * Generates a new AddCommand with the details of the given tag.
     */
    private AddCommand getAddCommandForTag(Tag tag, Model model) {
        AddCommand command = new AddCommand(tag);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public AddTagResult addTag(Tag tag) {
            fail("This method should not be called.");
            return new AddTagResult(false, tag);
        }

        @Override
        public void resetData(ReadOnlyAddressBook newData) {
            fail("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void deleteTag(Tag target) throws TagNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void updateTag(Tag target, Tag editedTag)
                throws DuplicateTagException {
            fail("This method should not be called.");
        }

        @Override
        public ObservableList<Tag> getFilteredTagList() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public ObservableList<Card> getFilteredCardList() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void addEdge(Card card, Tag tag) throws DuplicateEdgeException {
            fail("This method should not be called");
        }

        @Override
        public void removeEdge(Card card, Tag tag) throws EdgeNotFoundException {
            fail("This method should not be called");
        }

        @Override
        public void updateFilteredTagList(Predicate<Tag> predicate) {
            fail("This method should not be called.");
        }

        @Override
        public void filterCardsByTag(Tag tag) {
            fail("This method should not be called.");
        }

        @Override
        public void showAllCards() {
            fail("This method should not be called.");
        }

        @Override
        public void showDueCards() {
            fail("This method should not be called.");
        }

        @Override
        public void addCard(Card card) throws DuplicateCardException {
            fail("This method should not be called.");
        }

        @Override
        public void deleteCard(Card target) throws CardNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void updateCard(Card target, Card editedCard) throws CardNotFoundException {
            fail("This method should not be called.");
        }
    }

    /**
     * A Model stub that always accept the tag being added.
     */
    private class ModelStubAcceptingTagAdded extends ModelStub {
        final ArrayList<Tag> tagsAdded = new ArrayList<>();

        @Override
        public AddTagResult addTag(Tag tag) {
            requireNonNull(tag);
            tagsAdded.add(tag);
            return new AddTagResult(false, tag);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

}
