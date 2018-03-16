package seedu.address.model.cardtag;

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
    private static CardTag instance = new CardTag();

    private MutableGraph<Node> graph;

    private CardTag() {
        this.graph = GraphBuilder.undirected().build();
    }

    public static CardTag getInstance() {
        return instance;
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
    public void addTag(Tag tag) {
        addNode(tag);
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

    public boolean hasConnection(Card card, Tag tag) {
        return graph.hasEdgeConnecting(card, tag);
    }

    public Set<Node> getCards(Tag tag) {
        return graph.successors(tag);
    }

    public Set<Node> getTags(Card card) {
        return graph.successors(card);
    }
}
