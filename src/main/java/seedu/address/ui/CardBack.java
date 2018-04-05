package seedu.address.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.CardListPanelSelectionChangedEvent;

public class CardBack extends UiPart<Region>{
    private static final String FXML = "CardBack.fxml";

    private Label cardBack;

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    public CardBack() {
        super(FXML);
    }

    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(CardListPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        //(event.getNewSelection());
    }
}
