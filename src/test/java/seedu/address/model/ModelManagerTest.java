package seedu.address.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_TAGS;
import static seedu.address.testutil.TypicalCards.MATHEMATICS_CARD;
import static seedu.address.testutil.TypicalCards.PHYSICS_CARD;
import static seedu.address.testutil.TypicalCards.PHYSICS_CARD_2;
import static seedu.address.testutil.TypicalTags.MATHEMATICS_TAG;
import static seedu.address.testutil.TypicalTags.PHYSICS_TAG;

import java.util.Arrays;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.card.exceptions.NoCardSelectedException;
import seedu.address.model.tag.NameContainsKeywordsPredicate;
import seedu.address.testutil.AddressBookBuilder;

public class ModelManagerTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void getFilteredTagList_modifyList_throwsUnsupportedOperationException() {
        ModelManager modelManager = new ModelManager();
        thrown.expect(UnsupportedOperationException.class);
        modelManager.getFilteredTagList().remove(0);
    }

    @Test
    public void getSelectedCard_null() {
        ModelManager modelManager = new ModelManager();
        assertNull(modelManager.getSelectedCard());
    }

    @Test
    public void answerSelectedCard_throwsNoCardSelectedException() throws NoCardSelectedException {
        ModelManager modelManager = new ModelManager();
        thrown.expect(NoCardSelectedException.class);
        modelManager.answerSelectedCard(0);
    }

    @Test
    public void equals() {
        AddressBook addressBook = new AddressBookBuilder()
                .withTag(PHYSICS_TAG)
                .withTag(MATHEMATICS_TAG)
                .withCard(PHYSICS_CARD)
                .withCard(PHYSICS_CARD_2)
                .withCard(MATHEMATICS_CARD)
                .withEdge(PHYSICS_CARD, PHYSICS_TAG)
                .withEdge(PHYSICS_CARD_2, PHYSICS_TAG)
                .withEdge(MATHEMATICS_CARD, MATHEMATICS_TAG)
                .build();

        AddressBook differentAddressBook = new AddressBook();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        ModelManager modelManager = new ModelManager(addressBook, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(addressBook, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different addressBook -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentAddressBook, userPrefs)));

        // different filteredList -> returns false
        String[] keywords = PHYSICS_TAG.getName().fullName.split("\\s+");
        modelManager.updateFilteredTagList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(addressBook, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredTagList(PREDICATE_SHOW_ALL_TAGS);

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredTagList(PREDICATE_SHOW_ALL_TAGS);

        // different userPrefs -> returns true
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setAddressBookName("differentName");
        assertTrue(modelManager.equals(new ModelManager(addressBook, differentUserPrefs)));
    }
}
