package seedu.flashy.commons.events.ui;

import seedu.flashy.commons.events.BaseEvent;
import seedu.flashy.ui.CardCard;

//@@author yong-jie
/**
 * Represents a selection change in the Card List Panel
 */
public class CardListPanelSelectionChangedEvent extends BaseEvent {

    private final CardCard newSelection;

    public CardListPanelSelectionChangedEvent(CardCard newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public CardCard getNewSelection() {
        return newSelection;
    }

}
