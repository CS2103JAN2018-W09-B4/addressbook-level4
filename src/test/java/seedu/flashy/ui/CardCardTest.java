package seedu.flashy.ui;

import static org.junit.Assert.assertEquals;
import static seedu.flashy.testutil.TypicalTags.BIOLOGY_TAG;
import static seedu.flashy.testutil.TypicalTags.PHYSICS_TAG;
import static seedu.flashy.ui.testutil.GuiTestAssert.assertCardDisplaysCard;

import org.junit.Test;

import guitests.guihandles.CardCardHandle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.flashy.model.card.Card;
import seedu.flashy.model.card.McqCard;
import seedu.flashy.model.tag.Tag;
import seedu.flashy.testutil.CardBuilder;
import seedu.flashy.testutil.McqCardBuilder;

public class CardCardTest extends GuiUnitTest {

    @Test
    public void display() {
        Card card = new CardBuilder().build();
        ObservableList<Tag> tags = FXCollections.observableArrayList(BIOLOGY_TAG, PHYSICS_TAG);
        CardCard cardCard = new CardCard(card, 1, tags);
        uiPartRule.setUiPart(cardCard);
        assertCardDisplay(cardCard, card, 1);
    }

    @Test
    public void displayMcqCard() {
        McqCard card = new McqCardBuilder().build();
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
        assertEquals(Integer.toString(expectedId), cardCardHandle.getId());

        // verify card details are displayed properly
        assertCardDisplaysCard(card, cardCardHandle);
    }
}
