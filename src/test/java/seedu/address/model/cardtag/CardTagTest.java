package seedu.address.model.cardtag;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalCards.CARD_1;
import static seedu.address.testutil.TypicalCards.CARD_3;
import static seedu.address.testutil.TypicalCards.CARD_4;
import static seedu.address.testutil.TypicalCards.CARD_5;
import static seedu.address.testutil.TypicalCards.CARD_6;
import static seedu.address.testutil.TypicalTags.BIOLOGY;
import static seedu.address.testutil.TypicalTags.PHYSICS;
import static seedu.address.testutil.TypicalTags.TAMIL;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.AddressBook;
import seedu.address.model.card.Card;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.TypicalAddressBook;

public class CardTagTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private AddressBook addressBook = TypicalAddressBook.getTypicalAddressBook();

    @Before
    public void setUp() throws Exception {
        for (Card card : addressBook.getCardList()) {
            CardTag.getInstance().addCard(card);
        }

        for (Tag tag : addressBook.getTagList()) {
            CardTag.getInstance().addTag(tag);
        }

        // Associate CARD_3 and CARD_5 to PHYSICS
        CardTag.getInstance().associateCardTag(CARD_3, PHYSICS);
        CardTag.getInstance().associateCardTag(CARD_5, PHYSICS);

        // Associate CARD_4 and CARD_5 to BIOLOGY
        CardTag.getInstance().associateCardTag(CARD_4, BIOLOGY);
        CardTag.getInstance().associateCardTag(CARD_5, BIOLOGY);
    }

    @Test
    public void addingCardsAndTagsWorks() {
        // Tags in the typical address book are present
        assertTrue(CardTag.getInstance().getGraph().nodes().contains(PHYSICS));
        assertTrue(CardTag.getInstance().getGraph().nodes().contains(BIOLOGY));

        // Cards in the typical address book are present
        assertTrue(CardTag.getInstance().getGraph().nodes().contains(CARD_3));
        assertTrue(CardTag.getInstance().getGraph().nodes().contains(CARD_4));

        // There are four edges now
        assertEquals(CardTag.getInstance().getGraph().edges().size(), 4);
    }

    @Test
    public void associateCardTag_existingCardsCanFormEdges() {
        // Case: there is a new connection between the card and the tag
        assertTrue(CardTag.getInstance().hasConnection(CARD_3, PHYSICS));
        assertTrue(CardTag.getInstance().hasConnection(CARD_4, BIOLOGY));

        // Case: there is no connection between the card and the tag
        assertFalse(CardTag.getInstance().hasConnection(CARD_4, PHYSICS));
    }

    @Test
    public void associateCardTag_nonExistingCardsCannotBeAdded() {
        // CARD_1 is not in the graph yet
        thrown.expect(AssertionError.class);
        CardTag.getInstance().associateCardTag(CARD_1, PHYSICS);
    }

    @Test
    public void getCards_withEdges() {
        assertEquals(CardTag.getInstance().getCards(PHYSICS), Stream.of(CARD_3, CARD_5).collect(Collectors.toSet()));
        assertEquals(CardTag.getInstance().getCards(BIOLOGY), Stream.of(CARD_4, CARD_5).collect(Collectors.toSet()));
    }

    @Test
    public void getCards_withoutEdges() {
        assertEquals(CardTag.getInstance().getCards(TAMIL), Stream.of().collect(Collectors.toSet()));
    }

    @Test
    public void getTags_withEdges() {
        assertEquals(CardTag.getInstance().getTags(CARD_3), Stream.of(PHYSICS).collect(Collectors.toSet()));
        assertEquals(CardTag.getInstance().getTags(CARD_5), Stream.of(PHYSICS, BIOLOGY).collect(Collectors.toSet()));
    }

    @Test
    public void getTags_withoutEdges() {
        assertEquals(CardTag.getInstance().getTags(CARD_6), Stream.of().collect(Collectors.toSet()));
    }

    @After
    public void tearDown() {
        CardTag.getInstance().reset();
    }
}
