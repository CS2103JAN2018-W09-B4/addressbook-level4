package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.card.Card;
import seedu.address.model.card.McqCard;

/**
 * An Immutable AddressBook that is serializable to XML format
 */
@XmlRootElement(name = "addressbook")
public class XmlSerializableAddressBook {

    @XmlElement
    private List<XmlAdaptedTag> tags;

    @XmlElement
    private List<XmlAdaptedCard> cards;

    @XmlElement
    private List<XmlAdaptedMcqCard> mcqCards;

    @XmlElement(name = "cardtag")
    private XmlAdaptedCardTag cardTag = null;

    /**
     * Creates an empty XmlSerializableAddressBook.
     * This empty constructor is required for marshalling.
     */
    public XmlSerializableAddressBook() {
        tags = new ArrayList<>();
        cards = new ArrayList<>();
        mcqCards = new ArrayList<>();
    }

    /**
     * Conversion
     */
    public XmlSerializableAddressBook(ReadOnlyAddressBook src) {
        this();
        tags.addAll(src.getTagList().stream().map(XmlAdaptedTag::new).collect(Collectors.toList()));
        for (Card card: src.getCardList()) {
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
     * Converts this addressbook into the model's {@code AddressBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated or duplicates in the
     * {@code XmlAdaptedTag} or {@code XmlAdaptedTag}.
     */
    public AddressBook toModelType() throws IllegalValueException {
        AddressBook addressBook = new AddressBook();
        for (XmlAdaptedTag tag : tags) {
            addressBook.addTag(tag.toModelType());
        }
        for (XmlAdaptedCard card : cards) {
            addressBook.addCard(card.toModelType());
        }

        if (cardTag != null) {
            addressBook.setCardTag(cardTag.toModelType());
        }

        return addressBook;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlSerializableAddressBook)) {
            return false;
        }

        XmlSerializableAddressBook otherAb = (XmlSerializableAddressBook) other;
        return tags.equals(otherAb.tags);
    }
}
