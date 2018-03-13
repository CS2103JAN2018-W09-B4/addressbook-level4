package seedu.address.ui;

import java.util.logging.Logger;

import org.fxmisc.easybind.EasyBind;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.card.Card;

/**
 * Panel containing a list of cards
 */
public class CardListPanel extends UiPart<Region> {
    private static final String FXML = "CardListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(CardListPanel.class);

    @FXML
    private ListView<CardCard> cardListView;

    public CardListPanel(ObservableList<Card> filteredCardList) {
        super(FXML);
        setConnections(filteredCardList);
        registerAsAnEventHandler(this);
    }

    private void registerAsAnEventHandler(CardListPanel cardListPanel) {
    }

    public void setConnections(ObservableList<Card> cardList) {
        ObservableList<CardCard> mappedList = EasyBind.map(
                cardList, (card) -> new CardCard(card, cardList.indexOf(card) + 1));
        cardListView.setItems(mappedList);
        cardListView.setCellFactory(listView -> new CardListViewCell());
    }

    private void scrollTo(int index) {
        Platform.runLater(() -> {
            cardListView.scrollTo(index);
            cardListView.getSelectionModel().clearAndSelect(index);
        });
    }

    class CardListViewCell extends ListCell<CardCard> {
        @Override
        protected void updateItem(CardCard card, boolean empty) {
            super.updateItem(card, empty);

            if (empty || card == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(card.getRoot());
            }
        }
    }
}
