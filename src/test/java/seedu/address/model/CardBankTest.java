package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.TypicalCardBank.getTypicalCardBank;
import static seedu.address.testutil.TypicalCards.MATHEMATICS_CARD;
import static seedu.address.testutil.TypicalTags.PHYSICS_TAG;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
import seedu.address.testutil.CardArrayBuilder;
import seedu.address.testutil.TypicalCardBank;

public class CardBankTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final CardBank cardBank = new CardBank();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), cardBank.getTagList());
        assertEquals(Collections.emptyList(), cardBank.getCardList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        cardBank.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyCardBank_replacesData() {
        CardBank newData = getTypicalCardBank();
        cardBank.resetData(newData);
        assertEquals(newData, cardBank);
    }

    @Test
    public void resetData_withDuplicateTags_throwsAssertionError() {
        // Repeat PHYSICS_TAG twice
        List<Tag> newTags = Arrays.asList(PHYSICS_TAG, PHYSICS_TAG);
        List<Card> newCards = Arrays.asList(MATHEMATICS_CARD, MATHEMATICS_CARD);
        CardBankStub newData = new CardBankStub(newTags, newCards);

        thrown.expect(AssertionError.class);
        cardBank.resetData(newData);
    }

    @Test
    public void getTagList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        cardBank.getTagList().remove(0);
    }

    @Test
    public void getReviewList() throws Exception {
        LocalDateTime todaysDate = LocalDate.now().atStartOfDay();
        ObservableList<Card> list = cardBank.getReviewList(todaysDate);
        assert (list.isEmpty());
        int[] days = new int[]{-1, 0, 1, 3};
        Card[] cardArray = CardArrayBuilder.getMapDaysToCardArray(days);
        CardBank cardBankSchedule = TypicalCardBank
            .getCardBankFromCardArray(cardArray);
        list = cardBankSchedule.getReviewList(todaysDate.minusDays(1L));
        assertEquals(list.size(), 1);
        list = cardBankSchedule.getReviewList(todaysDate);
        assertEquals(list.size(), 2);
        list = cardBankSchedule.getReviewList(todaysDate.plusDays(1L));
        assertEquals(list.size(), 3);
        list = cardBankSchedule.getReviewList(todaysDate.plusDays(2L));
        assertEquals(list.size(), 3);
        list = cardBankSchedule.getReviewList(todaysDate.plusDays(3L));
        assertEquals(list.size(), 4);
    }

    @Test
    public void getReviewList_nullArgument_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        cardBank.getReviewList(null);
    }

    /**
     * A stub ReadOnlyCardBank whose tags and tags lists can violate interface constraints.
     */
    private static class CardBankStub implements ReadOnlyCardBank {
        private final ObservableList<Tag> tags = FXCollections.observableArrayList();
        private final ObservableList<Card> cards = FXCollections.observableArrayList();
        private final CardTag cardTag = new CardTag();

        CardBankStub(Collection<Tag> tags, Collection<Card> cards) {
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
