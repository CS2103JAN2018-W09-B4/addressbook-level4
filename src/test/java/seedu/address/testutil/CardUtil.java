package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_BACK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FRONT;

import seedu.address.logic.commands.AddCardCommand;
import seedu.address.model.card.Card;

/**
 * A utility class for {@code Card}
 */
public class CardUtil {
    /**
     * Returns command for adding a {@code Card}
     *
     * @param card
     * @return string representing AddCardCommand
     */
    public static String getAddCardCommand(Card card) {
        return AddCardCommand.COMMAND_WORD + " " + getCardDetails(card);
    }

    /**
     * Returns the part of the command string representing a card's details
     *
     * @param card
     * @return command string for card details
     */
    public static String getCardDetails(Card card) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(PREFIX_FRONT + card.getFront() + " ");
        stringBuilder.append(PREFIX_BACK + card.getBack());
        return stringBuilder.toString();
    }
}
