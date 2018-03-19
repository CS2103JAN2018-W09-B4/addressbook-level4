package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.xml.bind.annotation.XmlElement;

/**
 * XML Adapted Entry in the CardTag cardMap HashMap.
 */
public class XmlAdaptedCardMapEntry {
    @XmlElement(required = true)
    private String cardId;

    @XmlElement(required = true)
    private List<String> tags;

    public XmlAdaptedCardMapEntry() {
        tags = new ArrayList<>();
    }

    /**
     * Constructs an instance of XMmlAdaptedCardMapEntry from a Map Entry.
     * @param entry Map Entry for cardMap
     */
    public XmlAdaptedCardMapEntry(Map.Entry<String, Set<String>> entry) {
        this();
        cardId = entry.getKey();
        for (String tagId: entry.getValue()) {
            tags.add(tagId);
        }
    }

    public String getCardId() {
        return cardId;
    }

    public List<String> getTags() {
        return tags;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedCardTag)) {
            return false;
        }

        XmlAdaptedCardMapEntry otherCardMap = (XmlAdaptedCardMapEntry) other;

        return Objects.equals(otherCardMap.cardId, cardId)
                && Objects.equals(otherCardMap.tags, tags);
    }
}
