package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONFIDENCE;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.card.exceptions.NoCardSelectedException;

/**
 * Answers a selected flashcard
 */
public class AnswerCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "answer";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Answer a selected flashcard. "
            + "Parameters: "
            + PREFIX_CONFIDENCE + "CONFIDENCE LEVEL";

    public static final String MESSAGE_SUCCESS = "Your card has been scheduled.";
    public static final String MESSAGE_CARD_NOT_SELECTED = "Cannot answer to no card, please select a card first.";

    private final int confidenceLevel;

    /**
     * Creates an AnswerCommand to answer the selected {@code Card}
     */
    public AnswerCommand(int confidenceLevel) {
        this.confidenceLevel = confidenceLevel;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        try {
            model.answerSelectedCard(confidenceLevel);
            return new CommandResult(MESSAGE_SUCCESS);
        } catch (NoCardSelectedException e) {
            throw new CommandException(MESSAGE_CARD_NOT_SELECTED);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AnswerCommand // instanceof handles nulls
                && confidenceLevel == (((AnswerCommand) other).confidenceLevel));
    }
}
