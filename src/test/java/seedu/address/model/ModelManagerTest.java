package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_TAGS;
import static seedu.address.testutil.TypicalCardBank.getTypicalCardBank;
import static seedu.address.testutil.TypicalCards.MATHEMATICS_CARD;
import static seedu.address.testutil.TypicalCards.PHYSICS_CARD;
import static seedu.address.testutil.TypicalCards.PHYSICS_CARD_2;
import static seedu.address.testutil.TypicalTags.MATHEMATICS_TAG;
import static seedu.address.testutil.TypicalTags.PHYSICS_TAG;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.card.exceptions.NoCardSelectedException;
import seedu.address.model.tag.NameContainsKeywordsPredicate;
import seedu.address.testutil.CardBankBuilder;

public class ModelManagerTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    private ModelManager model;
    private LocalDateTime todaysDate;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalCardBank(), new UserPrefs());
        todaysDate = LocalDate.now().atStartOfDay();
    }

    @Test
    public void getFilteredTagList_modifyList_throwsUnsupportedOperationException() {
        ModelManager modelManager = new ModelManager();
        thrown.expect(UnsupportedOperationException.class);
        modelManager.getFilteredTagList().remove(0);
    }

    @Test
    public void setSelectedCard_null() {
        assertNull(model.getSelectedCard());
        model.setSelectedCard(null);
        assertNull(model.getSelectedCard());
    }

    @Test
    public void setSelectedCard_success() {
        assertNull(model.getSelectedCard());
        model.setSelectedCard(PHYSICS_CARD);
        assertEquals(model.getSelectedCard(), PHYSICS_CARD);
    }

    @Test
    public void getSelectedCard_null() {
        assertNull(model.getSelectedCard());
    }

    @Test
    public void setNextReview_throwsCommandException() throws Exception {
        thrown.expect(NoCardSelectedException.class);
        model.setNextReview(todaysDate);
    }

    @Test
    public void answerSelectedCard_throwsNoCardSelectedException() throws Exception {
        thrown.expect(NoCardSelectedException.class);
        model.answerSelectedCard(0);
    }

    @Test
    public void equals() {
        CardBank cardBank = new CardBankBuilder()
            .withTag(PHYSICS_TAG)
            .withTag(MATHEMATICS_TAG)
            .withCard(PHYSICS_CARD)
            .withCard(PHYSICS_CARD_2)
            .withCard(MATHEMATICS_CARD)
            .withEdge(PHYSICS_CARD, PHYSICS_TAG)
            .withEdge(PHYSICS_CARD_2, PHYSICS_TAG)
            .withEdge(MATHEMATICS_CARD, MATHEMATICS_TAG)
            .build();

        CardBank differentCardBank = new CardBank();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        ModelManager modelManager = new ModelManager(cardBank, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(cardBank, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different cardBank -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentCardBank, userPrefs)));

        // different filteredList -> returns false
        String[] keywords = PHYSICS_TAG.getName().fullName.split("\\s+");
        modelManager.updateFilteredTagList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(cardBank, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredTagList(PREDICATE_SHOW_ALL_TAGS);

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredTagList(PREDICATE_SHOW_ALL_TAGS);

        // different userPrefs -> returns true
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setCardBankName("differentName");
        assertTrue(modelManager.equals(new ModelManager(cardBank, differentUserPrefs)));
    }
}
