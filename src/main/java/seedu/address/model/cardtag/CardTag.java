package seedu.address.model.cardtag;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.card.Card;
import seedu.address.model.tag.Tag;

//@@author jethrokuan
/**
 *
 * This class captures the relations between cards and tags.
 */
public class CardTag {
    public static final String MESSAGE_CARD_HAS_TAG = "Card already has tag '%s'";
    public static final String MESSAGE_CARD_NO_TAG = "Card has no tag '%s'";

    private HashMap<String, Set<String>> cardMap; // cardMap["cardId"] = Set<tagId>
    private HashMap<String, Set<String>> tagMap; // tagMap["tagId"] = Set<cardId>

    public CardTag() {
        this.cardMap = new HashMap<>();
        this.tagMap = new HashMap<>();
    }

    public CardTag(CardTag cardTag) {
        this.cardMap = new HashMap<>();
        this.tagMap = new HashMap<>();

        for (Map.Entry<String, Set<String>> entry: cardTag.cardMap.entrySet()) {
            String key = entry.getKey();
            Set<String> values = entry.getValue();

            this.cardMap.put(key, new HashSet<>());
            for (String id: values) {
                this.cardMap.get(key).add(id);
            }
        }

        for (Map.Entry<String, Set<String>> entry: cardTag.tagMap.entrySet()) {
            String key = entry.getKey();
            Set<String> values = entry.getValue();

            this.tagMap.put(key, new HashSet<>());
            for (String id: values) {
                this.tagMap.get(key).add(id);
            }
        }
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

    /**
     * Checks if the Card and Tag given are connected by an edge.
     * @param cardId UUID of card
     * @param tagId UUID of tag
     * @return true if cord and tag are connected, false otherwise
     */
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
            return FXCollections.observableArrayList();
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
     * Adds an edge between card and tag.
     * @param card Card
     * @param tag Tag
     * @throws DuplicateEdgeException when the edge between card and tag already exists
     */
    public void addEdge(Card card, Tag tag) throws DuplicateEdgeException {
        String cardId = card.getId().toString();
        String tagId = tag.getId().toString();

        if (isConnected(cardId, tagId)) {
            throw new DuplicateEdgeException(tag);
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

    /**
     * Removes the undirected edge between card and tag.
     * @param card Card
     * @param tag Tag
     * @throws EdgeNotFoundException if there is no edge to remove.
     */
    public void removeEdge(Card card, Tag tag) throws EdgeNotFoundException {
        String cardId = card.getId().toString();
        String tagId = tag.getId().toString();

        if (!isConnected(cardId, tagId)) {
            throw new EdgeNotFoundException(tag);
        }

        Set<String> tags = cardMap.get(cardId);
        Set<String> cards = tagMap.get(tagId);
        if (tags != null) {
            tags.remove(tagId);
            if (tags.isEmpty()) {
                cardMap.remove(cardId);
            }
        }

        if (cards != null) {
            cards.remove(cardId);
            if (cards.isEmpty()) {
                tagMap.remove(tagId);
            }
        }
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
//@@author
