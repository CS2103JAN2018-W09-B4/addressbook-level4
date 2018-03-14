package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysCard;

import org.junit.Test;

import guitests.guihandles.CardCardHandle;
import seedu.address.model.card.Card;
import seedu.address.testutil.CardBuilder;

public class CardCardTest extends GuiUnitTest {

    @Test
    public void display() {
        Card card = new CardBuilder().build();
        CardCard cardCard = new CardCard(card, 1);
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
