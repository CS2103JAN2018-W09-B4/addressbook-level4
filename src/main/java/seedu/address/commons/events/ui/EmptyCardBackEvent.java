package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

//@@author yong-jie
/**
 * Represents a selection change in the Tag List Panel
 */
public class EmptyCardBackEvent extends BaseEvent {

    public EmptyCardBackEvent() {
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
