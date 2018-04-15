package seedu.flashy.testutil;

import seedu.flashy.model.AddressBook;
import seedu.flashy.model.cardtag.CardTag;

/**
 * Typical card tag utility class for tests.
 */
public class TypicalCardTag {
    /**
     * Returns a typical CardTag.
     */
    public static CardTag getTypicalCardTag() {
        AddressBook addressBook = TypicalAddressBook.getTypicalAddressBook();

        return addressBook.getCardTag();
    }
}
