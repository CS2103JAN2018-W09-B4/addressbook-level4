package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.card.McqCard;

/**
 * JAXB-friendly version of the McqCard.
 */
public class XmlAdaptedMcqCard extends XmlAdaptedCard {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "McqCard's %s field is missing!";

    @XmlElement(required = true)
    private List<String> option = new ArrayList<>();

    /**
     * Constructs an XmlAdaptedMcqCard.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedMcqCard() {}


    /**
     * Constructs an {@code XmlAdaptedMcqCard} with the given mcq card details.
     */
    public XmlAdaptedMcqCard(String id, String front, String back, List<String> options) {
        super(id, front, back);
        if (options == null) {
            this.option = null;
        } else {
            this.option.addAll(options);
        }
    }

    /**
     * Converts a given McqCard into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedTag
     */
    public XmlAdaptedMcqCard(McqCard source) {
        super(source.getId().toString(), source.getFront(), source.getBack());
        this.option.addAll(source.getOptions());
    }

    @Override
    /**
     * Converts this jaxb-friendly adapted tag object into the model's McqCard object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted card
     */
    public McqCard toModelType() throws IllegalValueException {

        if (super.id == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, McqCard.class.getSimpleName()));
        }
        if (super.front == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, McqCard.class.getSimpleName()));
        }
        if (!McqCard.isValidCard(super.front)) {
            throw new IllegalValueException(McqCard.MESSAGE_CARD_CONSTRAINTS);
        }

        if (super.back == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, McqCard.class.getSimpleName()));
        }
        if (!McqCard.isValidCard(super.back)) {
            throw new IllegalValueException(McqCard.MESSAGE_CARD_CONSTRAINTS);
        }
        if (option == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, McqCard.class.getSimpleName()));
        }

        List<String> optionsSet = option;
        for (String option: optionsSet) {
            if (!McqCard.isValidCard(option)) {
                throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                        McqCard.class.getSimpleName()));
            }
        }
        if (!McqCard.isValidMcqCard(super.back, optionsSet)) {
            throw new IllegalValueException(McqCard.MESSAGE_MCQ_CARD_ANSWER_CONSTRAINTS);
        }

        return new McqCard(UUID.fromString(id), front, back, optionsSet);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedMcqCard)) {
            return false;
        }

        XmlAdaptedMcqCard otherCard = (XmlAdaptedMcqCard) other;
        return Objects.equals(id, otherCard.id)
                && Objects.equals(front, otherCard.front)
                && Objects.equals(back, otherCard.back)
                && option.containsAll(((XmlAdaptedMcqCard) other).option)
                && ((XmlAdaptedMcqCard) other).option.containsAll(option);
    }

}
