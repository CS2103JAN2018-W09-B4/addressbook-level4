package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.CardBank;
import seedu.address.model.ReadOnlyCardBank;
import seedu.address.model.card.Card;
import seedu.address.model.card.McqCard;

/**
 * An Immutable CardBank that is serializable to XML format
 */
@XmlRootElement(name = "cardBank")
public class XmlSerializableCardBank {

    @XmlElement
    private List<XmlAdaptedTag> tags;

    @XmlElement
    private List<XmlAdaptedCard> cards;

    @XmlElement
    private List<XmlAdaptedMcqCard> mcqCards;

    @XmlElement(name = "cardtag")
    private XmlAdaptedCardTag cardTag = null;

    /**
     * Creates an empty XmlSerializableCardBank.
     * This empty constructor is required for marshalling.
     */
    public XmlSerializableCardBank() {
        tags = new ArrayList<>();
        cards = new ArrayList<>();
        mcqCards = new ArrayList<>();
    }

    /**
     * Conversion
     */
    public XmlSerializableCardBank(ReadOnlyCardBank src) {
        this();
        tags.addAll(src.getTagList().stream().map(XmlAdaptedTag::new).collect(Collectors.toList()));
        for (Card card : src.getCardList()) {
            if (card.getType().equals(McqCard.TYPE)) {
                mcqCards.add(new XmlAdaptedMcqCard(card.getId().toString(), card.getFront(),
                    card.getBack(), ((McqCard) card).getOptions()));
            } else {
                cards.add(new XmlAdaptedCard(card.getId().toString(), card.getFront(), card.getBack()));
            }
        }
        cardTag = new XmlAdaptedCardTag(src.getCardTag());
    }

    /**
     * Converts this cardBank into the model's {@code CardBank} object.
     *
     * @throws IllegalValueException if there were any data constraints violated or duplicates in the
     *                               {@code XmlAdaptedTag} or {@code XmlAdaptedTag}.
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
