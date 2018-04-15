package seedu.flashy.commons.events.ui;

import seedu.flashy.commons.events.BaseEvent;

//@@author yong-jie
/**
 * Indicates a request to change theme
 */
public class ChangeThemeRequestEvent extends BaseEvent {
    public final String theme;

    public ChangeThemeRequestEvent(String theme) {
        this.theme = theme;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
