package seedu.flashy.commons.events.ui;

import seedu.flashy.commons.core.index.Index;
import seedu.flashy.commons.events.BaseEvent;

//@@author yong-jie
/**
 * Indicates a request to jump to the list of tags
 */
public class JumpToCardRequestEvent extends BaseEvent {

    public final int targetIndex;

    public JumpToCardRequestEvent(Index targetIndex) {
        this.targetIndex = targetIndex.getZeroBased();
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
