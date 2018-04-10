package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.TypicalTags.BIOLOGY_TAG;
import static seedu.address.testutil.TypicalTags.PHYSICS_TAG;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysCard;

import org.junit.Test;

import guitests.guihandles.CardCardHandle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.card.Card;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.CardBuilder;

public class CardCardTest extends GuiUnitTest {

    @Test
    public void display() {
        Card card = new CardBuilder().build();
        ObservableList<Tag> tags = FXCollections.observableArrayList(BIOLOGY_TAG, PHYSICS_TAG);
        CardCard cardCard = new CardCard(card, 1, tags);
        uiPartRule.setUiPart(cardCard);
        assertCardDisplay(cardCard, card, 1);
    }

    /**
     * Asserts that {@code cardCard} displays the details of {@code card} correctly and matches
     * {@code expectedId}
     * @param cardCard
     * @param card
     * @param expectedId
     */
    private void assertCardDisplay(CardCard cardCard, Card card, int expectedId) {
        guiRobot.pauseForHuman();

        CardCardHandle cardCardHandle = new CardCardHandle(cardCard.getRoot());

        // verify id is displayed correctly
        assertEquals(Integer.toString(expectedId) + ". ", cardCardHandle.getId());

        // verify card details are displayed properly
        assertCardDisplaysCard(card, cardCardHandle);
    }
}
