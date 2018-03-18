package seedu.address.testutil;

import java.util.UUID;

import seedu.address.model.card.Card;

/**
 * A utility class to help with building Card objects.
 */
public class CardBuilder {

    public static final String DEFAULT_FRONT = "When is national day in Singapore?";
    public static final String DEFAULT_BACK = "9th August";

    private UUID id;
    private String front;
    private String back;

    public CardBuilder() {
        id = UUID.randomUUID();
        front = DEFAULT_FRONT;
        back = DEFAULT_BACK;
    }

    /**
     * Initializes the CardBuilder with the data of {@code tagToCopy}.
     */
    public CardBuilder(Card cardToCopy) {
        id = cardToCopy.getId();
        front = cardToCopy.getFront();
        back = cardToCopy.getBack();
    }


    /**
     * Sets the {@code id} of the {@code Card} that we are building.
     */
    public CardBuilder withId(String id) {
        this.id = UUID.fromString(id);
        return this;
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

    public Card build() {
        return new Card(id, front, back);
    }

}
