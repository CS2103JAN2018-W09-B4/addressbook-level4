package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.xml.bind.annotation.XmlElement;

public class XmlAdaptedTagMap {
    @XmlElement(required = true)
    private String tagId;

    @XmlElement(required = true)
    private List<String> cards;

    public XmlAdaptedTagMap() {
        cards = new ArrayList<>();
    }

    public String getTagId() {
        return tagId;
    }

    public List<String> getCards() {
        return cards;
    }

    public XmlAdaptedTagMap(Map.Entry<String, Set<String>> entry) {
        this();
        tagId = entry.getKey();
        for (String cardId : entry.getValue()) {
            cards.add(cardId);
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

        XmlAdaptedTagMap otherCardMap = (XmlAdaptedTagMap) other;

        return Objects.equals(otherCardMap.tagId, tagId)
                && Objects.equals(otherCardMap.cards, cards);
    }
}
