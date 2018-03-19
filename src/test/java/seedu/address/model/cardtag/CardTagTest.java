package seedu.address.model.cardtag;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalCards.CHEMISTRY_CARD;
import static seedu.address.testutil.TypicalCards.COMSCI_CARD;
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
import seedu.address.testutil.TypicalAddressBook;

public class CardTagTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private AddressBook addressBook = TypicalAddressBook.getTypicalAddressBook();

    private CardTag cardTag;

    @Before
    public void setUp() throws Exception {
        cardTag = new CardTag();

        // Associate CARD_3 and CARD_5 to PHYSICS_TAG
        cardTag.addEdge(MATHEMATICS_CARD, PHYSICS_TAG);
        cardTag.addEdge(COMSCI_CARD, PHYSICS_TAG);

        // Associate CARD_4 and CARD_5 to BIOLOGY_TAG
        cardTag.addEdge(CHEMISTRY_CARD, BIOLOGY_TAG);
        cardTag.addEdge(COMSCI_CARD, BIOLOGY_TAG);
    }

    @Test
    public void addEdge_worksForNewEdges() {
        assertTrue(cardTag.isConnected(MATHEMATICS_CARD, PHYSICS_TAG));
        assertTrue(cardTag.isConnected(CHEMISTRY_CARD, BIOLOGY_TAG));
    }

    @Test
    public void addEdge_whereEdgeExistsThrowsDuplicateEdgeException() throws DuplicateEdgeException {
        thrown.expect(DuplicateEdgeException.class);
        cardTag.addEdge(COMSCI_CARD, PHYSICS_TAG);
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
