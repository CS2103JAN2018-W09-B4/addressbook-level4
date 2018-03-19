package seedu.address.model.cardtag;

import java.util.Collections;
import java.util.HashMap;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.model.card.Card;
import seedu.address.model.tag.Tag;

/**
 * This class captures the relations between cards and tags.
 *
 */
public class CardTag {
    private HashMap<Card, Set<Tag>> cardMap;
    private HashMap<Tag, Set<Card>> tagMap;

    public CardTag() {
        this.cardMap = new HashMap<>();
        this.tagMap = new HashMap<>();
    }

    public boolean isConnected(Card card, Tag tag) {
        Set<Tag> tags = cardMap.get(card);
        Set<Card> cards = tagMap.get(tag);

        return (tags != null && tags.contains(tag))
                || (cards != null && cards.contains(card)); // should always short-circuit here
    }

    public Set<Card> getCards(Tag tag) {
        Set<Card> cards = tagMap.get(tag);
        if (cards != null) {
            return cards;
        } else {
            return Collections.emptySet();
        }
    }

    public Set<Tag> getTags(Card card) {
        Set<Tag> tags = cardMap.get(card);
        if (tags != null) {
            return tags;
        } else {
            return Collections.emptySet();
        }
    }

    /**
     * Creates an edge between a card and a tag.
     *
     * Ensures that the card and tag are already in the graph.
     *
     * @param card a valid Card
     * @param tag a valid Tag
     */
    public void addEdge(Card card, Tag tag) throws DuplicateEdgeException {
        if (isConnected(card, tag)) {
            throw new DuplicateEdgeException();
        }

        Set<Tag> tags = cardMap.get(card);
        if (tags == null) {
            cardMap.put(card, Stream.of(tag).collect(Collectors.toSet()));
        } else {
            tags.add(tag);
        }

        Set<Card> cards = tagMap.get(tag);
        if (cards == null) {
            tagMap.put(tag, Stream.of(card).collect(Collectors.toSet()));
        } else {
            cards.add(card);
        }
    }

    public void removeEdge(Card card, Tag tag) throws EdgeNotFoundException {
        if (!isConnected(card, tag)) {
            throw new EdgeNotFoundException();
        }

        cardMap.get(card).remove(tag);
        tagMap.get(tag).remove(card);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof CardTag)) {
            return false;
        }

        CardTag otherCardTag = (CardTag) other;

        return Objects.equals(otherCardTag.cardMap, cardMap)
                && Objects.equals(otherCardTag.tagMap, tagMap);
    }
}
