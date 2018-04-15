package guitests.guihandles;

import java.util.Optional;

import javafx.scene.control.ListView;
import seedu.address.model.card.Card;
import seedu.address.ui.CardCard;

/**
 * Provides a handle for {@code CardListPanel} containing the list of {@code CardCard}
 */
public class CardListPanelHandle extends  NodeHandle<ListView<CardCard>> {
    public static final String CARD_LIST_VIEW_ID = "#cardListView";

    public CardListPanelHandle(ListView<CardCard> cardListPanelNode) {
        super(cardListPanelNode);
    }

    public CardCardHandle getCardCardHandle(int index) {
        return getCardCardHandle(getRootNode().getItems().get(index).card);
    }

    /**
     * Returns the {@code CardCardHandle} of the specified {@code Card} in the list.
     * @param card Card object to get CardCardHandle for
     * @return CardCardHandle for specified card
     */
    public CardCardHandle getCardCardHandle(Card card) {
        Optional<CardCardHandle> handle = getRootNode().getItems().stream()
                .filter(cardCard -> cardCard.card.equals(card))
                .map(cardCard -> new CardCardHandle(cardCard.getRoot()))
                .findFirst();
        return handle.orElseThrow(() -> new IllegalArgumentException("Card does not exist."));
    }

    /**
     * Returns the index of the selected card.
     */
    public int getSelectedCardIndex() {
        return getRootNode().getSelectionModel().getSelectedIndex();
    }

    /**
     * Returns the size of the list.
     * @return size of the list
     */
    public int getListSize() {
        return getRootNode().getItems().size();
    }
}
