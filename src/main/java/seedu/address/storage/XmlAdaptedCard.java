package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.card.Card;

/**
 * JAXB-friendly version of the Person.
 */
public class XmlAdaptedCard {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Card's %s field is missing!";

    @XmlElement(required = true)
    private String front;
    @XmlElement(required = true)
    private String back;

    /**
     * Constructs an XmlAdaptedPerson.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedCard() {}

    /**
     * Constructs an {@code XmlAdaptedPerson} with the given person details.
     */
    public XmlAdaptedCard(String front, String back) {
        this.front = front;
        this.back = back;
    }

    /**
     * Converts a given Person into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedPerson
     */
    public XmlAdaptedCard(Card source) {
        front = source.getFront();
        back = source.getBack();
    }

//    /**
//     * Converts this jaxb-friendly adapted person object into the model's Person object.
//     *
//     * @throws IllegalValueException if there were any data constraints violated in the adapted person
//     */
//    public Card toModelType() throws IllegalValueException {
//        if (this.name == null) {
//            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
//        }
//        if (!Name.isValidName(this.name)) {
//            throw new IllegalValueException(Name.MESSAGE_NAME_CONSTRAINTS);
//        }
//        final Name name = new Name(this.name);
//
//        if (this.phone == null) {
//            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
//        }
//        if (!Phone.isValidPhone(this.phone)) {
//            throw new IllegalValueException(Phone.MESSAGE_PHONE_CONSTRAINTS);
//        }
//        final Phone phone = new Phone(this.phone);
//
//        if (this.email == null) {
//            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
//        }
//        if (!Email.isValidEmail(this.email)) {
//            throw new IllegalValueException(Email.MESSAGE_EMAIL_CONSTRAINTS);
//        }
//        final Email email = new Email(this.email);
//
//        if (this.address == null) {
//            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName()));
//        }
//        if (!Address.isValidAddress(this.address)) {
//            throw new IllegalValueException(Address.MESSAGE_ADDRESS_CONSTRAINTS);
//        }
//        final Address address = new Address(this.address);
//
//        final Set<Tag> tags = new HashSet<>(personTags);
//        return new Person(name, phone, email, address, tags);
//    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedPerson)) {
            return false;
        }

        XmlAdaptedCard otherCard = (XmlAdaptedCard) other;
        return Objects.equals(front, otherCard.front)
                && Objects.equals(back, otherCard.back);
    }
}
