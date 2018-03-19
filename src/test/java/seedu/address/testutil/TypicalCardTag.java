package seedu.address.testutil;

import seedu.address.model.AddressBook;
import seedu.address.model.cardtag.CardTag;

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
