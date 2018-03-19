package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import com.google.common.collect.Sets;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.cardtag.CardTag;

/**
 * JAXB-friendly version of an edge in CardTag
 */
@XmlRootElement(name = "cardtag")
public class XmlAdaptedCardTag {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "%s field is missing!";

    @XmlElement(required = true, name = "cardEntry")
    List<XmlAdaptedCardMap> cardEntries;

    @XmlElement(required = true, name = "tagEntry")
    List<XmlAdaptedTagMap> tagEntries;

    public XmlAdaptedCardTag() {
        cardEntries = new ArrayList<>();
        tagEntries = new ArrayList<>();
    }

    /**
     * Constructs a new XmlAdaptedCardTag from given edge details.
     * @param cardEntries List of Card -> [Tag] entries
     * @param tagEntries List of Tag -> [Card] entries
     */
    public XmlAdaptedCardTag(List<XmlAdaptedCardMap> cardEntries, List<XmlAdaptedTagMap> tagEntries) {
        this.cardEntries = cardEntries;
        this.tagEntries = tagEntries;
    }

    public XmlAdaptedCardTag(CardTag cardTag) {
        this();
        cardEntries.addAll(cardTag.getCardMap().entrySet().stream()
                .map(XmlAdaptedCardMap::new).collect(Collectors.toList()));
        tagEntries.addAll(cardTag.getTagMap().entrySet().stream()
                .map(XmlAdaptedTagMap::new).collect(Collectors.toList()));
    }

    public CardTag toModelType() throws IllegalValueException {
        CardTag cardTag = new CardTag();
        HashMap<String, Set<String>> cardMap = new HashMap<>();
        for (XmlAdaptedCardMap entry : cardEntries) {
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
        for (XmlAdaptedTagMap entry : tagEntries) {
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

        return Objects.equals(cardEntries, otherEdge.cardEntries)
                && Objects.equals(tagEntries, otherEdge.tagEntries);
    }

}