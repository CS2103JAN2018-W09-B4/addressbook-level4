package seedu.address.model.card;

import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;
import java.util.Objects;
import java.util.UUID;


/**
 * Represents a Flashcard.
 * Guarantees: Front and Back must not be null.
 *
 * TODO: Allow for different kinds of Front and Back
 */
public class Card {

    //@@author shawnclq
    public static final String MESSAGE_CARD_CONSTRAINTS =
            "Card front and back can take any values, and it should not be blank";

    /*
     * The first character of the card must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String CARD_VALIDATION_REGEX = "[^\\s].*";
    public static final String TYPE = "Normal";

    protected final UUID id;
    protected final String front;
    protected final String back;
    protected final Schedule schedule;
    protected String type;

    public Card(String front, String back) {
        this(UUID.randomUUID(), front, back);
    }

    public Card(UUID id, String front, String back) {
        requireAllNonNull(id, front, back);
        checkArgument(isValidCard(front), MESSAGE_CARD_CONSTRAINTS);
        checkArgument(isValidCard(back), MESSAGE_CARD_CONSTRAINTS);
        this.front = front;
        this.back = back;
        this.id = id;
        this.schedule = new Schedule();
        this.type = TYPE;
    }

    public Card(UUID id, List<String> front, List<String> back) {
        requireAllNonNull(id, front, back);
        StringBuilder frontBuilder = new StringBuilder();
        StringBuilder backBuilder = new StringBuilder();
        String frontPart;
        String backPart;
        for (int i = 0; i < front.size(); i++) {
            frontPart = front.get(i);
            checkArgument(isValidCard(front.get(i)), MESSAGE_CARD_CONSTRAINTS);
            if (i == 0) {
                frontBuilder.append(frontPart);
            } else {
                frontBuilder.append(FillBlanksCard.BLANK).append(frontPart);
            }
        }
        for (int i = 0; i < back.size(); i++) {
            backPart = back.get(i);
            checkArgument(isValidCard(back.get(i)), MESSAGE_CARD_CONSTRAINTS);
            if (i == 0) {
                backBuilder.append(backPart);
            } else {
                backBuilder.append(", ").append(backPart);
            }
        }
        checkArgument(FillBlanksCard.isValidFillBlanksCard(front, back),
                FillBlanksCard.MESSAGE_FILLBLANKS_CARD_ANSWER_CONSTRAINTS);
        this.id = id;
        this.front = frontBuilder.toString();
        this.back = backBuilder.toString();
        this.schedule = new Schedule();
        this.type = FillBlanksCard.TYPE;
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
    //@@author

    public Schedule getSchedule() {
        return schedule;
    }

    //@@author shawnclq
    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    /**
     * Returns true if a given front and back string is valid.
     */
    public static boolean isValidCard(String test) {
        requireAllNonNull(test);
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
    //@@author
}
