package seedu.address.model.card;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;
import java.util.UUID;

//@@author shawnclq
/**
 * Represents a fill-in-the-blanks Flashcard.
 * Guarantees: Front, Back must not be null.
 *
 */
public class FillBlanksCard extends Card {

    public static final String MESSAGE_FILLBLANKS_CARD_CONSTRAINTS =
            "Fill Blanks Card should have multiple front and back parameters, and it should not be blank";
    public static final String MESSAGE_FILLBLANKS_CARD_ANSWER_CONSTRAINTS =
            "Fill Blanks Card back should have the same number of answers as there are blanks";
    public static final String TYPE = "FillBlanks";
    public static final String BLANK = " ______ ";

    public FillBlanksCard(List<String> front, List<String> back) {
        this(UUID.randomUUID(), front, back);
    }

    public FillBlanksCard(UUID id, List<String> front, List<String> back) {
        super(id, front, back);
    }

    /**
     * Returns true if a given front and back string is valid.
     */
    public static boolean isValidFillBlanksCard(List<String> front, List<String> back) {
        requireAllNonNull(front, back);
        return (front.size() - 1) == back.size();
    }

    /**
     * Returns true if a given string contains blanks.
     */
    public static boolean containsBlanks(String test) {
        return (test.indexOf(BLANK)) != -1;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof FillBlanksCard)) {
            return false;
        }

        FillBlanksCard otherCard = (FillBlanksCard) other;

        return otherCard.getFront().equals(this.getFront())
                && otherCard.getBack().equals(this.getBack());
    }
}
//@@author
