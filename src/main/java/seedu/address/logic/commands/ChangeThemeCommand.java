package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_THEME;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ChangeThemeRequestEvent;

/**
 * Changes the theme of the UI.
 */
public class ChangeThemeCommand extends Command {

    public static final String COMMAND_WORD = "theme";

    public static final String MESSAGE_SUCCESS = "Theme changed successfully";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a person to the address book. "
            + "Parameters: "
            + PREFIX_THEME + "THEME "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_THEME + "light ";

    private final String[] themeStrings = {"view/LightTheme.css", "view/DarkTheme.css"};
    private final String newTheme;

    public ChangeThemeCommand(Integer themeIndex) {
        newTheme = themeStrings[themeIndex];
    }

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new ChangeThemeRequestEvent(newTheme));
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
