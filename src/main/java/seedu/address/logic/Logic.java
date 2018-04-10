package seedu.address.logic;

import javafx.collections.ObservableList;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.card.Card;
import seedu.address.model.cardtag.CardTag;
import seedu.address.model.tag.Tag;

/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException If an error occurs during parsing.
     */
    CommandResult execute(String commandText) throws CommandException, ParseException;

    /** Returns an unmodifiable view of the filtered list of tags */
    ObservableList<Tag> getFilteredTagList();

    /** Returns the list of input entered by the user, encapsulated in a {@code ListElementPointer} object */
    ListElementPointer getHistorySnapshot();

    /** Returns an unmodifiable view of the filtered list of cards */
    ObservableList<Card> getFilteredCardList();

    /** Returns a view of the CardTag */
    CardTag getCardTag();

    /** Returns a view of all Cards */
    ObservableList<Tag> getTagList();
}
