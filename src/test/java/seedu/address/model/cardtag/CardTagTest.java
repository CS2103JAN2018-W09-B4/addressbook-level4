package seedu.address.model.cardtag;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalCards.CHEMISTRY_CARD;
import static seedu.address.testutil.TypicalCards.COMSCI_CARD;
import static seedu.address.testutil.TypicalCards.CS2103T_CARD;
import static seedu.address.testutil.TypicalCards.GEOGRAPHY_CARD;
import static seedu.address.testutil.TypicalCards.MATHEMATICS_CARD;
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
        CardTag.getInstance().associateCardTag(MATHEMATICS_CARD, PHYSICS);
        CardTag.getInstance().associateCardTag(COMSCI_CARD, PHYSICS);

        // Associate CARD_4 and CARD_5 to BIOLOGY
        CardTag.getInstance().associateCardTag(CHEMISTRY_CARD, BIOLOGY);
        CardTag.getInstance().associateCardTag(COMSCI_CARD, BIOLOGY);
    }

    @Test
    public void addingCardsAndTagsWorks() {
        // Tags in the typical address book are present
        assertTrue(CardTag.getInstance().getGraph().nodes().contains(PHYSICS));
        assertTrue(CardTag.getInstance().getGraph().nodes().contains(BIOLOGY));

        // Cards in the typical address book are present
        assertTrue(CardTag.getInstance().getGraph().nodes().contains(MATHEMATICS_CARD));
        assertTrue(CardTag.getInstance().getGraph().nodes().contains(CHEMISTRY_CARD));

        // There are four edges now
        assertEquals(CardTag.getInstance().getGraph().edges().size(), 4);
    }

    @Test
    public void associateCardTag_existingCardsCanFormEdges() {
        // Case: there is a new connection between the card and the tag
        assertTrue(CardTag.getInstance().hasConnection(MATHEMATICS_CARD, PHYSICS));
        assertTrue(CardTag.getInstance().hasConnection(CHEMISTRY_CARD, BIOLOGY));

        // Case: there is no connection between the card and the tag
        assertFalse(CardTag.getInstance().hasConnection(CHEMISTRY_CARD, PHYSICS));
    }

    @Test
    public void associateCardTag_nonExistingCardsCannotBeAdded() {
        // CARD_1 is not in the graph yet
        thrown.expect(AssertionError.class);
        CardTag.getInstance().associateCardTag(CS2103T_CARD, PHYSICS);
    }

    @Test
    public void getCards_withEdges() {
        assertEquals(CardTag.getInstance().getCards(PHYSICS), Stream.of(MATHEMATICS_CARD, COMSCI_CARD)
                .collect(Collectors.toSet()));
        assertEquals(CardTag.getInstance().getCards(BIOLOGY), Stream.of(CHEMISTRY_CARD, COMSCI_CARD)
                .collect(Collectors.toSet()));
    }

    @Test
    public void getCards_withoutEdges() {
        assertEquals(CardTag.getInstance().getCards(TAMIL), Stream.of().collect(Collectors.toSet()));
    }

    @Test
    public void getTags_withEdges() {
        assertEquals(CardTag.getInstance().getTags(MATHEMATICS_CARD), Stream.of(PHYSICS).collect(Collectors.toSet()));
        assertEquals(CardTag.getInstance().getTags(COMSCI_CARD), Stream.of(PHYSICS, BIOLOGY)
                .collect(Collectors.toSet()));
    }

    @Test
    public void getTags_withoutEdges() {
        assertEquals(CardTag.getInstance().getTags(GEOGRAPHY_CARD), Stream.of().collect(Collectors.toSet()));
    }

    @After
    public void tearDown() {
        CardTag.getInstance().reset();
    }
}
