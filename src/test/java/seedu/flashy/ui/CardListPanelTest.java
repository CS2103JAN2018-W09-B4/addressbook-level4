package seedu.flashy.ui;

import static org.junit.Assert.assertEquals;
import static seedu.flashy.testutil.TypicalCardTag.getTypicalCardTag;
import static seedu.flashy.testutil.TypicalCards.getTypicalCards;
import static seedu.flashy.testutil.TypicalTags.getTypicalTags;
import static seedu.flashy.ui.testutil.GuiTestAssert.assertCardDisplaysCard;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.CardCardHandle;
import guitests.guihandles.CardListPanelHandle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.flashy.model.card.Card;
import seedu.flashy.model.cardtag.CardTag;
import seedu.flashy.model.tag.Tag;

public class CardListPanelTest extends GuiUnitTest {
    private static final ObservableList<Card> TYPICAL_CARDS =
            FXCollections.observableList(getTypicalCards());

    private static final CardTag TYPICAL_CARD_TAG = getTypicalCardTag();

    private static final ObservableList<Tag> TYPICAL_TAGS = FXCollections.observableList(getTypicalTags());

    private CardListPanelHandle cardListPanelHandle;

    @Before
    public void setUp() {
        CardListPanel cardListPanel = new CardListPanel(TYPICAL_CARDS, TYPICAL_CARD_TAG, TYPICAL_TAGS);
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
            assertEquals(Integer.toString(i + 1), actualCard.getId());
        }
    }
}
