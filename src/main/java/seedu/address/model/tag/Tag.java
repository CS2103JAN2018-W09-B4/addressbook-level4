package seedu.address.model.tag;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;
import java.util.UUID;

/**
 * Represents a Tag in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Tag {

    private final UUID id;
    private final Name name;

    /**
     * Every field must be present and not null.
     */
    public Tag(Name name) {
        requireAllNonNull(name);
        this.name = name;
        this.id = UUID.randomUUID();
    }

    public Tag(UUID id, Name name) {
        requireAllNonNull(id, name);
        this.id = id;
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public Name getName() {
        return name;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Tag)) {
            return false;
        }

        Tag otherTag = (Tag) other;

        return otherTag.getName().equals(this.getName());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(" Name: ")
                .append(getName());
        return builder.toString();
    }
}
