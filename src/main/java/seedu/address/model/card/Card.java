package seedu.address.model.card;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;
import java.util.UUID;

import seedu.address.model.cardtag.Node;

/**
 * Represents a Flashcard.
 * Guarantees: Front and Back must not be null.
 *
 * TODO: Allow for different kinds of Front and Back
 */
public class Card implements Node {

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
    private final Schedule schedule;

    public Card(String front, String back) {
        requireAllNonNull(front, back);
        this.front = front;
        this.back = back;
        this.id = UUID.randomUUID();
        this.schedule = new Schedule();
    }

    public Card(UUID id, String front, String back) {
        requireAllNonNull(id, front, back);
        this.front = front;
        this.back = back;
        this.id = id;
        this.schedule = new Schedule();
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

    public Schedule getSchedule() {
        return schedule;
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

        Card otherCard = (Card) other;

        // TODO: account for ID equality. Some test cases check for object equality.
        //        return otherCard.getId().toString().equals(this.getId().toString())
        //                && otherCard.getFront().equals(this.getFront())
        //                && otherCard.getBack().equals(this.getBack());

        return otherCard.getFront().equals(this.getFront())
                && otherCard.getBack().equals(this.getBack());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(id, front, back);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Front: ")
                .append(getFront())
                .append(" Back: ")
                .append(getBack());
        return builder.toString();
    }
}
