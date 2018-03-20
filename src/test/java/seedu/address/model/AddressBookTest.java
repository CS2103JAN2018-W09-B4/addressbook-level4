package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;
import static seedu.address.testutil.TypicalCards.MATHEMATICS_CARD;
import static seedu.address.testutil.TypicalTags.PHYSICS_TAG;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.card.Card;
import seedu.address.model.cardtag.CardTag;
import seedu.address.model.tag.Tag;

public class AddressBookTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final AddressBook addressBook = new AddressBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), addressBook.getTagList());
        assertEquals(Collections.emptyList(), addressBook.getCardList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        addressBook.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        AddressBook newData = getTypicalAddressBook();
        addressBook.resetData(newData);
        assertEquals(newData, addressBook);
    }

    @Test
    public void resetData_withDuplicateTags_throwsAssertionError() {
        // Repeat PHYSICS_TAG twice
        List<Tag> newTags = Arrays.asList(PHYSICS_TAG, PHYSICS_TAG);
        List<Card> newCards = Arrays.asList(MATHEMATICS_CARD, MATHEMATICS_CARD);
        AddressBookStub newData = new AddressBookStub(newTags, newCards);

        thrown.expect(AssertionError.class);
        addressBook.resetData(newData);
    }

    @Test
    public void getTagList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        addressBook.getTagList().remove(0);
    }

    @Test
    public void getTodayReviewList() {
        AddressBook newData = getTypicalAddressBook();
        addressBook.resetData(newData);
        List<Card> expectedList = addressBook.getCardList();
        List<Card> actualList = addressBook.getTodayReviewList();
        assertEquals(expectedList, actualList);
    }

    /**
     * A stub ReadOnlyAddressBook whose tags and tags lists can violate interface constraints.
     */
    private static class AddressBookStub implements ReadOnlyAddressBook {
        private final ObservableList<Tag> tags = FXCollections.observableArrayList();
        private final ObservableList<Card> cards = FXCollections.observableArrayList();
        private final CardTag cardTag = new CardTag();

        AddressBookStub(Collection<Tag> tags, Collection<Card> cards) {
            this.tags.setAll(tags);
            this.cards.setAll(cards);
        }

        @Override
        public ObservableList<Tag> getTagList() {
            return tags;
        }

        @Override
        public ObservableList<Card> getCardList() {
            return cards;
        }

        @Override
        public CardTag getCardTag() {
            return cardTag;
        }
    }

}
