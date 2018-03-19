package seedu.address.model.cardtag;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javafx.collections.ObservableList;
import seedu.address.model.card.Card;
import seedu.address.model.tag.Tag;

/**
 * This class captures the relations between cards and tags.
 *
 */
public class CardTag {
    private HashMap<String, Set<String>> cardMap;
    private HashMap<String, Set<String>> tagMap;

    public CardTag() {
        this.cardMap = new HashMap<>();
        this.tagMap = new HashMap<>();
    }

    public HashMap<String, Set<String>> getCardMap() {
        return cardMap;
    }

    public HashMap<String, Set<String>> getTagMap() {
        return tagMap;
    }

    public void setCardMap(HashMap<String, Set<String>> cardMap) {
        this.cardMap = cardMap;
    }

    public void setTagMap(HashMap<String, Set<String>> tagMap) {
        this.tagMap = tagMap;
    }

    public boolean isConnected(String cardId, String tagId) {
        Set<String> tags = cardMap.get(cardId);
        Set<String> cards = tagMap.get(tagId);

        return (tags != null && tags.contains(tagId))
                || (cards != null && cards.contains(cardId)); // should always short-circuit here
    }

    public boolean isConnected(Card card, Tag tag) {
        return isConnected(card.getId().toString(), tag.getId().toString());
    }


    public List<Card> getCards(String tagId, ObservableList<Card> cardList) {
        Set<String> cards = tagMap.get(tagId);
        if (cards != null) {
            return cardList.filtered(card -> cards.contains(card.getId().toString()));
        } else {
            return Collections.emptyList();
        }
    }

    public List<Card> getCards(Tag tag, ObservableList<Card> cardList) {
        return getCards(tag.getId().toString(), cardList);
    }

    public List<Tag> getTags(String cardId, ObservableList<Tag> tagList) {
        Set<String> tags = cardMap.get(cardId);
        if (tags != null) {
            return tagList.filtered(tag -> tags.contains(tag.getId().toString()));
        } else {
            return Collections.emptyList();
        }
    }

    public List<Tag> getTags(Card card, ObservableList<Tag> tagList) {
        return getTags(card.getId().toString(), tagList);
    }

    /**
     * Creates an edge between a card and a tag.
     *
     * Ensures that the card and tag are already in the graph.
     *
     * @param cardId card id
     * @param tagId tag Id
     */
    public void addEdge(String cardId, String tagId) throws DuplicateEdgeException {
        if (isConnected(cardId, tagId)) {
            throw new DuplicateEdgeException();
        }

        Set<String> tags = cardMap.get(cardId);
        if (tags == null) {
            cardMap.put(cardId, Stream.of(tagId).collect(Collectors.toSet()));
        } else {
            tags.add(tagId);
        }

        Set<String> cards = tagMap.get(tagId);
        if (cards == null) {
            tagMap.put(tagId, Stream.of(cardId).collect(Collectors.toSet()));
        } else {
            cards.add(cardId);
        }
    }

    public void addEdge(Card card, Tag tag) throws DuplicateEdgeException {
        addEdge(card.getId().toString(), tag.getId().toString());
    }

    public void removeEdge(String cardId, String tagId) throws EdgeNotFoundException {
        if (!isConnected(cardId, tagId)) {
            throw new EdgeNotFoundException();
        }

        cardMap.get(cardId).remove(tagId);
        tagMap.get(tagId).remove(cardId);
    }

    public void removeEdge(Card card, Tag tag) throws EdgeNotFoundException {
        removeEdge(card.getId().toString(), tag.getId().toString());
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
