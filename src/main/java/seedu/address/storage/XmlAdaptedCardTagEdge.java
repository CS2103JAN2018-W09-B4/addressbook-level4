package seedu.address.storage;

import java.util.Objects;

import javax.xml.bind.annotation.XmlElement;

/**
 * JAXB-friendly version of an edge in CardTag
 */
public class XmlAdaptedCardTagEdge {
    @XmlElement(required = true)
    String cardId;

    @XmlElement(required = true)
    String tagId;

    public XmlAdaptedCardTagEdge() {}

    /**
     * Constructs a new XmlAdaptedCardTagEdge from given edge details.
     * @param cardId card ID
     * @param tagId tag ID
     */
    public XmlAdaptedCardTagEdge(String cardId, String tagId) {
        this.cardId = cardId;
        this.tagId = tagId;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedCardTagEdge)) {
            return false;
        }

        XmlAdaptedCardTagEdge otherEdge = (XmlAdaptedCardTagEdge) other;

        return Objects.equals(cardId, otherEdge.cardId)
                && Objects.equals(tagId, otherEdge.tagId);
    }

}
