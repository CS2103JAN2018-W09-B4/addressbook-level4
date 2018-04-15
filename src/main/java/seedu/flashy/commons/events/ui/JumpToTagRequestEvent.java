package seedu.flashy.commons.events.ui;

import seedu.flashy.commons.core.index.Index;
import seedu.flashy.commons.events.BaseEvent;

/**
 * Indicates a request to jump to the list of tags
 */
public class JumpToTagRequestEvent extends BaseEvent {

    public final int targetIndex;

    public JumpToTagRequestEvent(Index targetIndex) {
        this.targetIndex = targetIndex.getZeroBased();
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
