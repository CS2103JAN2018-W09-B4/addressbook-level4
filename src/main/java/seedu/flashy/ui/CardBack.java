package seedu.flashy.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.flashy.commons.core.LogsCenter;
import seedu.flashy.commons.events.ui.CardListPanelSelectionChangedEvent;
import seedu.flashy.commons.events.ui.EmptyCardBackEvent;
import seedu.flashy.model.card.Card;
import seedu.flashy.model.card.McqCard;

//@@author yong-jie
/**
 * A UI component that displays information about a {@code Card}.
 */
public class CardBack extends UiPart<Region> {
    private static final String FXML = "CardBack.fxml";

    @FXML
    private Label cardBack;

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    public CardBack() {
        super(FXML);
        registerAsAnEventHandler(this);
    }

    @Subscribe
    private void handleCardListPanelSelectionChangedEvent(CardListPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        Card card = event.getNewSelection().card;
        if (card.getType().equals(McqCard.TYPE)) {
            cardBack.setText(card.getOptions()
                    .get(Integer.valueOf(card.getBack()) - 1));
        } else {
            cardBack.setText(card.getBack());
        }
    }

    @Subscribe
    private void handleEmptyCardBackEvent(EmptyCardBackEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        cardBack.setText("");
    }
}
