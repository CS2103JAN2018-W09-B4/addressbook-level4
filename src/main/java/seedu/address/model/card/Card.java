package seedu.address.model.card;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;


import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

/**
 * Represents a Flashcard.
 * Guarantees: Front and Back must not be null.
 *
 * TODO: Allow for different kinds of Front and Back
 */
public class Card {

    public static final String MESSAGE_CARD_CONSTRAINTS =
            "Card front and back can take any values, and it should not be blank";

    /*
     * The first character of the card must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String CARD_VALIDATION_REGEX = "[^\\s].*";

    private final UUID id;
    private final String front;
    private final String back;

    private final UniqueTagList tags;

    public Card(String front, String back, Set<Tag> tags) {
        requireAllNonNull(front, back, tags);
        this.front = front;
        this.back = back;
        // protect internal tags from changes in the arg list
        this.tags = new UniqueTagList(tags);
        this.id = UUID.randomUUID();
    }

    public UUID getId() {
        return id;
    }

    public String getFront() {
        return front;
    }

    public String getBack() {
        return back;
    }

    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags.toSet());
    }

    /**
     * Returns true if a given front and back string is valid.
     */
    public static boolean isValidCard(String test) {
        return test.matches(CARD_VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Card)) {
            return false;
        }

        Card otherPerson = (Card) other;
        return otherPerson.getFront().equals(this.getFront())
                && otherPerson.getBack().equals(this.getBack());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(front, back, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Front: ")
                .append(getFront())
                .append(" Back: ")
                .append(getBack())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }
}
