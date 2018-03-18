package seedu.address.model.cardtag;

import java.util.List;
import java.util.Set;

import com.google.common.graph.GraphBuilder;
import com.google.common.graph.MutableGraph;

import seedu.address.model.card.Card;
import seedu.address.model.tag.Tag;

/**
 * This class captures the relations between cards and tags.
 *
 */
public class CardTag {
    private MutableGraph<Node> graph;

    public CardTag() {
        this.graph = GraphBuilder.undirected().build();
    }

    public void reset() {
        this.graph = GraphBuilder.undirected().build();
    }

    public MutableGraph<Node> getGraph() {
        return graph;
    }

    private void addNode(Node node) {
        this.graph.addNode(node);
    }

    // Aliases to add tags
    public void addCard(Card card) {
        addNode(card);
    }

    /**
     * Adds a list of cards to the graph.
     * @param cards list of cards
     */
    public void addCards(List<Card> cards) {
        for (Card card : cards) {
            addNode(card);
        }
    }

    /**
     * Adds a single tag to the graph.
     * @param tag Tag to add.
     */
    public void addTag(Tag tag) {
        addNode(tag);
    }

    /**
     * Adds a list of tags to the graph.
     * @param tags list of tags
     */
    public void addTags(List<Tag> tags) {
        for (Tag tag : tags) {
            addNode(tag);
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
    public void associateCardTag(Card card, Tag tag) {
        assert(graph.nodes().contains(card));
        assert(graph.nodes().contains(tag));
        graph.putEdge(card, tag);
    }

    public boolean contains(Node node) {
        return graph.nodes().contains(node);
    }

    public int countEdges() {
        return graph.edges().size();
    }

    public boolean hasConnection(Card card, Tag tag) {
        return graph.hasEdgeConnecting(card, tag);
    }

    public Set<Node> getCards(Tag tag) {
        return graph.successors(tag);
    }

    public Set<Node> getTags(Card card) {
        return graph.successors(card);
    }

    // Delete operations
    public void deleteCard(Card card) {
        this.graph.removeNode(card);
    }

    public void deleteTag(Tag tag) {
        this.graph.removeNode(tag);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof CardTag)) {
            return false;
        }

        return ((CardTag) other).getGraph().nodes().equals(this.getGraph().nodes())
                && ((CardTag) other).getGraph().edges().equals(this.getGraph().edges());
    }

}
