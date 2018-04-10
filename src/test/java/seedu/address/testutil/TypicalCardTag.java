package seedu.address.testutil;

import seedu.address.model.CardBank;
import seedu.address.model.cardtag.CardTag;

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
