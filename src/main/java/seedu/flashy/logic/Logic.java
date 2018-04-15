package seedu.flashy.logic;

import javafx.collections.ObservableList;
import seedu.flashy.logic.commands.CommandResult;
import seedu.flashy.logic.commands.exceptions.CommandException;
import seedu.flashy.logic.parser.exceptions.ParseException;
import seedu.flashy.model.card.Card;
import seedu.flashy.model.cardtag.CardTag;
import seedu.flashy.model.tag.Tag;

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
