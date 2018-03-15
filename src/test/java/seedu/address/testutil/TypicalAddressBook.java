package seedu.address.testutil;

import static seedu.address.testutil.TypicalCards.getTypicalCards;

import seedu.address.model.AddressBook;
import seedu.address.model.card.Card;
import seedu.address.model.card.exceptions.DuplicateCardException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.exceptions.DuplicateTagException;

/**
 * Class that creates a typical address book.
 */
public class TypicalAddressBook {
    /**
     * Returns an {@code AddressBook} with all stub data.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook addressBook = new AddressBook();
        for (Tag tag : TypicalTags.getTypicalTags()) {
            try {
                addressBook.addTag(tag);
            } catch (DuplicateTagException e) {
                throw new AssertionError("not possible");
            }
        }

        for (Card card : getTypicalCards()) {
            try {
                addressBook.addCard(card);
            } catch (DuplicateCardException e) {
                throw new AssertionError("not possible");
            }
        }
        return addressBook;
    }
}
