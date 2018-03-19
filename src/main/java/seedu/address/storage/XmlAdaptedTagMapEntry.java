package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.xml.bind.annotation.XmlElement;

/**
 * Xml Adapted class for a Map Entry in CardTag's tagMap.
 */
public class XmlAdaptedTagMapEntry {
    @XmlElement(required = true)
    private String tagId;

    @XmlElement(required = true)
    private List<String> cards;

    public XmlAdaptedTagMapEntry() {
        cards = new ArrayList<>();
    }

    /**
     * Constructs an instance of XmlAdaptedTagMapEntry from a Map Entry in CardTag's tagMap
     * @param entry Map Entry of tagMap.
     */
    public XmlAdaptedTagMapEntry(Map.Entry<String, Set<String>> entry) {
        this();
        tagId = entry.getKey();
        for (String cardId : entry.getValue()) {
            cards.add(cardId);
        }
    }

    public String getTagId() {
        return tagId;
    }

    public List<String> getCards() {
        return cards;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedCardTag)) {
            return false;
        }

        XmlAdaptedTagMapEntry otherCardMap = (XmlAdaptedTagMapEntry) other;

        return Objects.equals(otherCardMap.tagId, tagId)
                && Objects.equals(otherCardMap.cards, cards);
    }
}
