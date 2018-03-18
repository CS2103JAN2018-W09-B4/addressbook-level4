package seedu.address.model.cardtag;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalCards.CHEMISTRY_CARD;
import static seedu.address.testutil.TypicalCards.COMSCI_CARD;
import static seedu.address.testutil.TypicalCards.CS2103T_CARD;
import static seedu.address.testutil.TypicalCards.GEOGRAPHY_CARD;
import static seedu.address.testutil.TypicalCards.MATHEMATICS_CARD;
import static seedu.address.testutil.TypicalTags.BIOLOGY_TAG;
import static seedu.address.testutil.TypicalTags.MATHEMATICS_TAG;
import static seedu.address.testutil.TypicalTags.PHYSICS_TAG;

import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    private CardTag cardTag;

    @Before
    public void setUp() throws Exception {
        cardTag = new CardTag();

        for (Card card : addressBook.getCardList()) {
            cardTag.addCard(card);
        }

        for (Tag tag : addressBook.getTagList()) {
            cardTag.addTag(tag);
        }

        // Associate CARD_3 and CARD_5 to PHYSICS_TAG
        cardTag.associateCardTag(MATHEMATICS_CARD, PHYSICS_TAG);
        cardTag.associateCardTag(COMSCI_CARD, PHYSICS_TAG);

        // Associate CARD_4 and CARD_5 to BIOLOGY_TAG
        cardTag.associateCardTag(CHEMISTRY_CARD, BIOLOGY_TAG);
        cardTag.associateCardTag(COMSCI_CARD, BIOLOGY_TAG);
    }

    @Test
    public void addingCardsAndTagsWorks() {
        // Tags in the typical address book are present
        assertTrue(cardTag.getGraph().nodes().contains(PHYSICS_TAG));
        assertTrue(cardTag.getGraph().nodes().contains(BIOLOGY_TAG));

        // Cards in the typical address book are present
        assertTrue(cardTag.contains(MATHEMATICS_CARD));
        assertTrue(cardTag.contains(CHEMISTRY_CARD));

        // There are four edges now
        assertEquals(cardTag.countEdges(), 4);
    }

    @Test
    public void associateCardTag_existingCardsCanFormEdges() {
        // Case: there is a new connection between the card and the tag
        assertTrue(cardTag.hasConnection(MATHEMATICS_CARD, PHYSICS_TAG));
        assertTrue(cardTag.hasConnection(CHEMISTRY_CARD, BIOLOGY_TAG));

        // Case: there is no connection between the card and the tag
        assertFalse(cardTag.hasConnection(CHEMISTRY_CARD, PHYSICS_TAG));
    }

    @Test
    public void associateCardTag_nonExistingCardsCannotBeAdded() {
        // CARD_1 is not in the graph yet
        thrown.expect(AssertionError.class);
        cardTag.associateCardTag(CS2103T_CARD, PHYSICS_TAG);
    }

    @Test
    public void getCards_withEdges() {
        assertEquals(cardTag.getCards(PHYSICS_TAG), Stream.of(MATHEMATICS_CARD, COMSCI_CARD)
                .collect(Collectors.toSet()));
        assertEquals(cardTag.getCards(BIOLOGY_TAG), Stream.of(CHEMISTRY_CARD, COMSCI_CARD)
                .collect(Collectors.toSet()));
    }

    @Test
    public void getCards_withoutEdges() {
        assertEquals(cardTag.getCards(MATHEMATICS_TAG), Stream.of().collect(Collectors.toSet()));
    }

    @Test
    public void getTags_withEdges() {
        assertEquals(cardTag.getTags(MATHEMATICS_CARD), Stream.of(PHYSICS_TAG).collect(Collectors.toSet()));
        assertEquals(cardTag.getTags(COMSCI_CARD), Stream.of(PHYSICS_TAG, BIOLOGY_TAG)
                .collect(Collectors.toSet()));
    }

    @Test
    public void getTags_withoutEdges() {
        assertEquals(cardTag.getTags(GEOGRAPHY_CARD), Stream.of().collect(Collectors.toSet()));
    }
}
