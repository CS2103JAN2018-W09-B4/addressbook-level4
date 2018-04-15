package seedu.address.logic.commands;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_TAGS;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.EmptyCardBackEvent;
import seedu.address.logic.parser.ListCommandParser;

//@@author jethrokuan
/**
 * Lists all cards in the card bank.
 */
public class ListCommand extends Command {
    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Cleared all filters.";

    public static final String MESSAGE_SUCCESS_NO_TAGS_ONLY = "Showing cards with no tags.";

    public static final String AUTOCOMPLETE_TEXT = COMMAND_WORD;
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": lists all cards and tags.\n"
            + COMMAND_WORD + " " + ListCommandParser.PREFIX_NO_TAGS_ONLY + ": lists only cards without tags.";

    private final boolean untaggedOnly;

    public ListCommand(boolean untaggedOnly) {
        this.untaggedOnly = untaggedOnly;
    }
    @Override
    public CommandResult execute() {
        String message;
        model.updateFilteredTagList(PREDICATE_SHOW_ALL_TAGS);
        if (untaggedOnly) {
            message = MESSAGE_SUCCESS_NO_TAGS_ONLY;
            model.showUntaggedCards();
        } else {
            message = MESSAGE_SUCCESS;
            model.showAllCards();
        }

        EventsCenter.getInstance().post(new EmptyCardBackEvent());
        return new CommandResult(message);
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof ListCommand)
                && untaggedOnly == ((ListCommand) other).untaggedOnly;
    }
}
//@@author
