package seedu.address.model.card;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import seedu.address.commons.util.CollectionUtil;

//@@author shawnclq
/**
 * Represents a MCQ Flashcard.
 * Guarantees: Front, Back, Options must not be null.
 *
 */
public class McqCard extends Card {

    public static final String MESSAGE_MCQ_CARD_CONSTRAINTS =
            "MCQ Card front, back and options can take any values, and it should not be blank";
    public static final String MESSAGE_MCQ_CARD_ANSWER_CONSTRAINTS =
            "MCQ Card back should be an integer between 1 and the number of options input";
    public static final String TYPE = "MCQ";

    public final ObservableList<String> optionsList = FXCollections.observableArrayList();

    public McqCard(String front, String back, List<String> options) {
        this(UUID.randomUUID(), front, back, options);
    }

    public McqCard(UUID id, String front, String back, List<String> options) {
        super(id, front, back);
        super.setType(TYPE);
        requireAllNonNull(options);
        for (String option: options) {
            checkArgument(super.isValidCard(option), super.MESSAGE_CARD_CONSTRAINTS);
        }
        optionsList.addAll(options);

        assert CollectionUtil.elementsAreUnique(optionsList);
    }

    /**
     * Returns all options in this list as a Set.
     * This set is mutable and change-insulated against options list.
     */
    @Override
    public List<String> getOptions() {
        assert CollectionUtil.elementsAreUnique(optionsList);
        return optionsList;
    }

    /**
     * Replaces the options in the list with those in the argument options list.
     */
    public void setOptions(List<String> options) {
        requireAllNonNull(options);
        optionsList.addAll(options);

        assert CollectionUtil.elementsAreUnique(optionsList);
    }

    /**
     * Returns true if the list contains an equivalent String as the given argument.
     */
    public boolean containsOption(String option) {
        requireNonNull(option);
        return optionsList.contains(option);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<String> asObservableList() {
        assert CollectionUtil.elementsAreUnique(optionsList);
        return FXCollections.unmodifiableObservableList(optionsList);
    }

    /**
     * Returns true if a given front and back string is valid.
     */
    public static boolean isValidMcqCard(String back, List<String> options) {
        requireAllNonNull(back, options);
        int backInt;
        try {
            backInt = Integer.valueOf(back);
        } catch (NumberFormatException e) {
            return false;
        }
        return (backInt >= 1) && (backInt <= options.size());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof McqCard)) {
            return false;
        }

        McqCard otherCard = (McqCard) other;

        return otherCard.getFront().equals(this.getFront())
                && otherCard.getBack().equals(this.getBack())
                && otherCard.getOptions().containsAll(optionsList)
                && optionsList.containsAll(otherCard.getOptions());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(super.id, super.front, back, optionsList);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Front: ").append(getFront());
        for (String option: optionsList) {
            builder.append(" Option: ").append(option);
        }
        builder.append(" Back: ").append(getBack());
        return builder.toString();
    }

}
//@@author
