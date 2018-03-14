package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.TypicalCards.getTypicalCards;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysCard;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.CardCardHandle;
import guitests.guihandles.CardListPanelHandle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.card.Card;

public class CardListPanelTest extends GuiUnitTest {
    private static final ObservableList<Card> TYPICAL_CARDS =
            FXCollections.observableList(getTypicalCards());

    private CardListPanelHandle cardListPanelHandle;

    @Before
    public void setUp() {
        CardListPanel cardListPanel = new CardListPanel(TYPICAL_CARDS);
        uiPartRule.setUiPart(cardListPanel);

        cardListPanelHandle = new CardListPanelHandle(getChildNode(cardListPanel.getRoot(),
                cardListPanelHandle.CARD_LIST_VIEW_ID));
    }

    @Test
    public void display() {
        for (int i = 0; i < TYPICAL_CARDS.size(); i++) {
            Card expectedCard = TYPICAL_CARDS.get(i);
            CardCardHandle actualCard = cardListPanelHandle.getCardCardHandle(i);

            assertCardDisplaysCard(expectedCard, actualCard);
            assertEquals(Integer.toString(i + 1) + ". ", actualCard.getId());
        }
    }
}
