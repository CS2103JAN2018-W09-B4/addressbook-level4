package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.card.Card;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;


/**
 * A utility class to help with building Card objects.
 */
public class CardBuilder {

    public static final String DEFAULT_FRONT = "When is national day in Singapore?";
    public static final String DEFAULT_BACK = "9th August";
    public static final String DEFAULT_TAGS = "friends";


    private String front;
    private String back;
    private Set<Tag> tags;

    public CardBuilder() {
        front = DEFAULT_FRONT;
        back = DEFAULT_BACK;
        tags = SampleDataUtil.getTagSet(DEFAULT_TAGS);
    }

    /**
     * Initializes the CardBuilder with the data of {@code tagToCopy}.
     */
    public CardBuilder(Card cardToCopy) {
        front = cardToCopy.getFront();
        back = cardToCopy.getBack();
        tags = new HashSet<>(cardToCopy.getTags());
    }

    /**
     * Sets the {@code front} of the {@code Card} that we are building.
     */
    public CardBuilder withFront(String front) {
        this.front = front;
        return this;
    }

    /**
     * Sets the {@code back} of the {@code Card} that we are building.
     */
    public CardBuilder withBack(String back) {
        this.back = back;
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Card} that we are building.
     */
    public CardBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Builds and returns the card
     * @return the copied card
     */
    public Card build() {
        return new Card(front, back, tags);
    }

}
