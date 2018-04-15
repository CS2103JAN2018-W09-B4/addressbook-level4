package seedu.flashy.testutil;

import seedu.flashy.model.CardBank;
import seedu.flashy.model.cardtag.CardTag;

/**
 * Typical card tag utility class for tests.
 */
public class TypicalCardTag {
    /**
     * Returns a typical CardTag.
     */
    public static CardTag getTypicalCardTag() {
        CardBank cardBank = TypicalCardBank.getTypicalCardBank();

        return cardBank.getCardTag();
    }
}
