package seedu.address.testutil;

import seedu.address.model.AddressBook;
import seedu.address.model.card.Card;
import seedu.address.model.card.exceptions.DuplicateCardException;
import seedu.address.model.cardtag.DuplicateEdgeException;
import seedu.address.model.tag.Tag;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code AddressBook ab = new AddressBookBuilder().withTag("John", "Doe").withTag("Friend").build();}
 */
public class AddressBookBuilder {

    private AddressBook addressBook;

    public AddressBookBuilder() {
        addressBook = new AddressBook();
    }

    public AddressBookBuilder(AddressBook addressBook) {
        this.addressBook = addressBook;
    }

    /**
     * Adds a new {@code Tag} to the {@code AddressBook} that we are building.
     */
    public AddressBookBuilder withTag(Tag tag) {
        addressBook.addTag(tag);
        return this;
    }

    /**
     * Adds a new {@code Card} to the {@code AddressBook} that we are building.
     */
    public AddressBookBuilder withCard(Card card) {
        try {
            addressBook.addCard(card);
        } catch (DuplicateCardException e) {
            throw new IllegalArgumentException("card is expected to be unique.");
        }
        return this;
    }

    /**
     * Adds a new {@code CardTag} edge to the {@code AddressBook} that we are building.
     */
    public AddressBookBuilder withEdge(Card card, Tag tag) {
        try {
            addressBook.addEdge(card, tag);
        } catch (DuplicateEdgeException e) {
            throw new IllegalArgumentException("edge already exists");
        }
        return this;
    }

    public AddressBook build() {
        return addressBook;
    }
}
