package seedu.address.storage;

import java.util.Objects;
import java.util.UUID;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.card.Card;
import seedu.address.model.card.FillBlanksCard;

/**
 * JAXB-friendly version of the Card.
 */
public class XmlAdaptedCard {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Card's %s field is missing!";

    @XmlElement(required = true)
    protected String front;
    @XmlElement(required = true)
    protected String back;
    @XmlElement(required = true)
    protected String id;

    /**
     * Constructs an XmlAdaptedCard.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedCard() {}


    /**
     * Constructs an {@code XmlAdaptedCard} with the given tag details.
     */
    public XmlAdaptedCard(String id, String front, String back) {
        this.id = id;
        this.front = front;
        this.back = back;
    }

    /**
     * Converts a given Card into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedTag
     */
    public XmlAdaptedCard(Card source) {
        id = source.getId().toString();
        front = source.getFront();
        back = source.getBack();
    }

    /**
     * Converts this jaxb-friendly adapted tag object into the model's Card object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted card
     */
    public Card toModelType() throws IllegalValueException {
        if (this.id == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Card.class.getSimpleName()));
        }
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
        if (FillBlanksCard.containsBlanks(this.front)) {
            if (!FillBlanksCard.isValidFillBlanksCard(this.front, this.back)) {
                throw new IllegalValueException(FillBlanksCard.MESSAGE_FILLBLANKS_CARD_ANSWER_CONSTRAINTS);
            }
            return new FillBlanksCard(UUID.fromString(id), this.front, this.back);
        }
        return new Card(UUID.fromString(id), front, back);
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
        return Objects.equals(id, otherCard.id)
                && Objects.equals(front, otherCard.front)
                && Objects.equals(back, otherCard.back);
    }
}
