package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.xml.bind.annotation.XmlElement;

public class XmlAdaptedCardMap {
    @XmlElement(required = true)
    private String cardId;

    @XmlElement(required = true)
    private List<String> tags;

    public XmlAdaptedCardMap() {
        tags = new ArrayList<>();
    }

    public String getCardId() {
        return cardId;
    }

    public List<String> getTags() {
        return tags;
    }

    public XmlAdaptedCardMap(Map.Entry<String, Set<String>> entry) {
        this();
        cardId = entry.getKey();
        for (String tagId: entry.getValue()) {
            tags.add(tagId);
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedCardTag)) {
            return false;
        }

        XmlAdaptedCardMap otherCardMap = (XmlAdaptedCardMap) other;

        return Objects.equals(otherCardMap.cardId, cardId)
                && Objects.equals(otherCardMap.tags, tags);
    }
}
