package seedu.address.model.card;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

/**
 * Represents a Flashcard.
 * Guarantees: Front and Back must not be null.
 *
 * TODO: Allow for different kinds of Front and Back
 */
public class Card {
    private final String front;
    private final String back;

    public Card(String front, String back) {
        requireAllNonNull(front, back);
        this.front = front;
        this.back = back;
    }

    public String getFront() {
        return front;
    }

    public String getBack() {
        return back;
    }
}
