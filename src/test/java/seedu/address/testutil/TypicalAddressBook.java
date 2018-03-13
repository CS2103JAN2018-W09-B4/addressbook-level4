package seedu.address.testutil;

import static seedu.address.testutil.TypicalCards.getTypicalCards;

import seedu.address.model.AddressBook;
import seedu.address.model.card.Card;
import seedu.address.model.card.exceptions.DuplicateCardException;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;

/**
 * Class that creates a typical address book.
 */
public class TypicalAddressBook {
    /**
     * Returns an {@code AddressBook} with all stub data.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook addressBook = new AddressBook();
        for (Person person : TypicalPersons.getTypicalPersons()) {
            try {
                addressBook.addPerson(person);
            } catch (DuplicatePersonException e) {
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
