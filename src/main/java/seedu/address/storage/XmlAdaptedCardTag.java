package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.common.collect.Sets;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.cardtag.CardTag;

//@@author jethrokuan
/**
 * JAXB-friendly version of an edge in CardTag
 */
@XmlRootElement(name = "cardtag")
public class XmlAdaptedCardTag {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "%s field is missing!";

    @XmlElement(required = true)
    private List<XmlAdaptedCardMapEntry> cardEntry;

    @XmlElement(required = true)
    private List<XmlAdaptedTagMapEntry> tagEntry;

    public XmlAdaptedCardTag() {
        cardEntry = new ArrayList<>();
        tagEntry = new ArrayList<>();
    }

    /**
     * Constructs a new XmlAdaptedCardTag from given edge details.
     *
     * @param cardEntry List of Card -> [Tag] entries
     * @param tagEntry  List of Tag -> [Card] entries
     */
    public XmlAdaptedCardTag(List<XmlAdaptedCardMapEntry> cardEntry, List<XmlAdaptedTagMapEntry> tagEntry) {
        this.cardEntry = cardEntry;
        this.tagEntry = tagEntry;
    }

    public XmlAdaptedCardTag(CardTag cardTag) {
        this();
        cardEntry.addAll(cardTag.getCardMap().entrySet().stream()
                .map(XmlAdaptedCardMapEntry::new).collect(Collectors.toList()));
        tagEntry.addAll(cardTag.getTagMap().entrySet().stream()
                .map(XmlAdaptedTagMapEntry::new).collect(Collectors.toList()));
    }

    public List<XmlAdaptedCardMapEntry> getCardEntry() {
        return cardEntry;
    }

    public List<XmlAdaptedTagMapEntry> getTagEntry() {
        return tagEntry;
    }

    /**
     * Converts this addressbook into the model's {@code CardTag} object.
     * @return corresponding CardTag object
     * @throws IllegalValueException if there are invalid values within the cardTag entries.
     */
    public CardTag toModelType() throws IllegalValueException {
        CardTag cardTag = new CardTag();
        HashMap<String, Set<String>> cardMap = new HashMap<>();
        for (XmlAdaptedCardMapEntry entry : cardEntry) {
            String cardId = entry.getCardId();
            List<String> tags = entry.getTags();
            if (cardId == null) {
                throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "card ID"));
            }
            if (tags == null || tags.size() == 0) {
                throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "tags"));
            }

            cardMap.put(cardId, Sets.newHashSet(tags));
        }

        HashMap<String, Set<String>> tagMap = new HashMap<>();
        for (XmlAdaptedTagMapEntry entry : tagEntry) {
            String tagId = entry.getTagId();
            List<String> cards = entry.getCards();
            if (tagId == null) {
                throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "tag ID"));
            }
            if (cards == null || cards.size() == 0) {
                throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "cards"));
            }

            tagMap.put(tagId, Sets.newHashSet(cards));
        }

        cardTag.setCardMap(cardMap);
        cardTag.setTagMap(tagMap);

        return cardTag;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedCardTag)) {
            return false;
        }

        XmlAdaptedCardTag otherEdge = (XmlAdaptedCardTag) other;

        return Objects.equals(cardEntry, otherEdge.cardEntry)
                && Objects.equals(tagEntry, otherEdge.tagEntry);
    }
}
//@@author
