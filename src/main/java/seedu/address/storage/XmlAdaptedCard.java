package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.card.Card;
import seedu.address.model.card.FillBlanksCard;
import seedu.address.model.card.McqCard;

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
    @XmlElement(required = true)
    private List<String> option = new ArrayList<>();
    @XmlElement(required = true)
    private String type;

    /**
     * Constructs an XmlAdaptedCard.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedCard() {}


    /**
     * Constructs an {@code XmlAdaptedCard} with the given card details.
     */
    public XmlAdaptedCard(String id, String front, String back, List<String> options, String type) {
        this.id = id;
        this.front = front;
        this.back = back;
        this.type = type;
        if (options == null) {
            option = null;
        } else {
            option.addAll(options);
        }
    }

    /**
     * Converts a given Card into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedCard
     */
    public XmlAdaptedCard(Card source) {
        this(source.getId().toString(), source.getFront(), source.getBack(), null, source.getType());
    }

    /**
     * Converts a given McqCard into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedCard
     */
    public XmlAdaptedCard(McqCard source) {
        this(source.getId().toString(), source.getFront(), source.getBack(), source.getOptions(), source.getType());
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
        if (this.type.equals(FillBlanksCard.TYPE)) {
            if (!FillBlanksCard.isValidFillBlanksCard(this.front, this.back)) {
                throw new IllegalValueException(FillBlanksCard.MESSAGE_FILLBLANKS_CARD_ANSWER_CONSTRAINTS);
            }
            return new FillBlanksCard(UUID.fromString(id), this.front, this.back);
        }
        if (this.type.equals(McqCard.TYPE)) {
            if (!McqCard.isValidMcqCard(this.back, this.option)) {
                throw new IllegalValueException(McqCard.MESSAGE_MCQ_CARD_ANSWER_CONSTRAINTS);
            }
            return new McqCard(UUID.fromString(this.id), this.front, this.back, this.option);
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

    public String getType() {
        return type;
    }
}
