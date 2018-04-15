package seedu.address.model.card;

import java.util.UUID;
import java.util.function.Predicate;

/**
 * Returns true iff the card has the same UUID.
 *
 * Should always only return a maximum of 1 card after filtering.
 */
public class UuidPredicate implements Predicate<Card> {
    private UUID uuid;

    public UuidPredicate(UUID uuid) {
        this.uuid = uuid;
    }

    @Override
    public boolean test(Card card) {
        return card.getId().equals(this.uuid);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UuidPredicate // instanceof handles nulls
                && this.uuid.equals(((UuidPredicate) other).uuid)); // state check
    }
}
