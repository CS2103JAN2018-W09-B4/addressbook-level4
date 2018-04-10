package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.ReadOnlyCardBank;

/** Indicates the CardBank in the model has changed*/
public class CardBankChangedEvent extends BaseEvent {

    public final ReadOnlyCardBank data;

    public CardBankChangedEvent(ReadOnlyCardBank data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of tags " + data.getTagList().size() + " ,number of cards " + data.getCardList().size();
    }
}
