package seedu.address.testutil;

import seedu.address.model.card.Card;

/**
 * A utility class that contains cards for use in tests.
 */
public class TypicalCards {
    private TypicalCards() {} // prevents instantiation

    public static final Card CARD1 = new Card("front1", "back1");

    public static final Card CARD2 = new Card("front2", "back2");

    public static final Card CARD3 = new Card("front3", "back3");
}
