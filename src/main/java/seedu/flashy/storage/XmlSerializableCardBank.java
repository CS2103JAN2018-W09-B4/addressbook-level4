package seedu.flashy.storage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.flashy.commons.exceptions.IllegalValueException;
import seedu.flashy.model.CardBank;
import seedu.flashy.model.ReadOnlyCardBank;
import seedu.flashy.model.card.Card;
import seedu.flashy.model.card.McqCard;

/**
 * An Immutable CardBank that is serializable to XML format
 */
@XmlRootElement(name = "cardbank")
public class XmlSerializableCardBank {

    @XmlElement
    private List<XmlAdaptedTag> tags;

    @XmlElement
    private List<XmlAdaptedCard> cards;

    @XmlElement(name = "cardtag")
    private XmlAdaptedCardTag cardTag = null;

    /**
     * Creates an empty XmlSerializableCardBank.
     * This empty constructor is required for marshalling.
     */
    public XmlSerializableCardBank() {
        tags = new ArrayList<>();
        cards = new ArrayList<>();
    }

    /**
     * Conversion
     */
    public XmlSerializableCardBank(ReadOnlyCardBank src) {
        this();
        tags.addAll(src.getTagList().stream().map(XmlAdaptedTag::new).collect(Collectors.toList()));
        for (Card card: src.getCardList()) {
            if (card.getType().equals(McqCard.TYPE)) {
                cards.add(new XmlAdaptedCard(card.getId().toString(), card.getFront(),
                        card.getBack(), ((McqCard) card).getOptions(), card.getType()));
            } else {
                cards.add(new XmlAdaptedCard(card.getId().toString(), card.getFront(),
                        card.getBack(), Arrays.asList(), card.getType()));
            }
        }
        cardTag = new XmlAdaptedCardTag(src.getCardTag());
    }

    /**
     * Converts this cardbank into the model's {@code CardBank} object.
     *
     * @throws IllegalValueException if there were any data constraints violated or duplicates in the
     * {@code XmlAdaptedTag} or {@code XmlAdaptedTag}.
     */
    public CardBank toModelType() throws IllegalValueException {
        CardBank cardBank = new CardBank();
        for (XmlAdaptedTag tag : tags) {
            cardBank.addTag(tag.toModelType());
        }
        for (XmlAdaptedCard card : cards) {
            cardBank.addCard(card.toModelType());
        }

        if (cardTag != null) {
            cardBank.setCardTag(cardTag.toModelType());
        }

        return cardBank;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlSerializableCardBank)) {
            return false;
        }

        XmlSerializableCardBank otherAb = (XmlSerializableCardBank) other;
        return tags.equals(otherAb.tags);
    }
}
