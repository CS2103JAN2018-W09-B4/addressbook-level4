package seedu.address.ui;

import java.util.logging.Logger;

import org.fxmisc.easybind.EasyBind;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.CardListPanelSelectionChangedEvent;
import seedu.address.commons.events.ui.JumpToCardRequestEvent;
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

    public void setConnections(ObservableList<Card> cardList) {
        ObservableList<CardCard> mappedList = EasyBind.map(
                cardList, (card) -> new CardCard(card, cardList.indexOf(card) + 1));
        cardListView.setItems(mappedList);
        cardListView.setCellFactory(listView -> new CardListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void setEventHandlerForSelectionChangeEvent() {
        cardListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in card list panel changed to : '" + newValue + "'");
                        raise(new CardListPanelSelectionChangedEvent(newValue));
                    }
                });
    }

    /**
     * Scrolls to the {@code TagCard} at the {@code index} and selects it.
     */
    private void scrollTo(int index) {
        Platform.runLater(() -> {
            cardListView.scrollTo(index);
            cardListView.getSelectionModel().clearAndSelect(index);
        });
    }

    @Subscribe
    private void handleJumpToCardRequestEvent(JumpToCardRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        scrollTo(event.targetIndex);
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code CardCard}.
     */
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
