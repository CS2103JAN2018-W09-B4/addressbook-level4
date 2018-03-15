package seedu.address.storage;

import java.util.Objects;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.card.Card;

/**
 * JAXB-friendly version of the Tag.
 */
public class XmlAdaptedCard {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Card's %s field is missing!";

    @XmlElement(required = true)
    private String front;
    @XmlElement(required = true)
    private String back;

    /**
     * Constructs an XmlAdaptedTag.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedCard() {}

    /**
     * Constructs an {@code XmlAdaptedTag} with the given tag details.
     */
    public XmlAdaptedCard(String front, String back) {
        this.front = front;
        this.back = back;
    }

    /**
     * Converts a given Tag into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedTag
     */
    public XmlAdaptedCard(Card source) {
        front = source.getFront();
        back = source.getBack();
    }

    /**
     * Converts this jaxb-friendly adapted tag object into the model's Card object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted card
     */
    public Card toModelType() throws IllegalValueException {
        if (this.front == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Card.class.getSimpleName()));
        }
        if (!Card.isValidCard(this.front)) {
            throw new IllegalValueException(Card.MESSAGE_CARD_CONSTRAINTS);
        }

        if (this.back == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Card.class.getSimpleName()));
        }
        if (!Card.isValidCard(this.back)) {
            throw new IllegalValueException(Card.MESSAGE_CARD_CONSTRAINTS);
        }
        return new Card(front, back);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedCard)) {
            return false;
        }

        XmlAdaptedCard otherCard = (XmlAdaptedCard) other;
        return Objects.equals(front, otherCard.front)
                && Objects.equals(back, otherCard.back);
    }
}
