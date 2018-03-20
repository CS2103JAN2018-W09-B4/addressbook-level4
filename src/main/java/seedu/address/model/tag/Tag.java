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
    private final Description description;

    /**
     * Every field must be present and not null.
     */
    public Tag(Name name, Description description) {
        requireAllNonNull(name, description);
        this.name = name;
        this.description = description;
        this.id = UUID.randomUUID();
    }

    public Tag(UUID id, Name name, Description description) {
        requireAllNonNull(id, name, description);
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public UUID getId() {
        return id;
    }

    public Name getName() {
        return name;
    }

    public Description getDescription() {
        return description;
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

        // TODO: account for ID equality. Some test cases check for object equality.
        //        return otherTag.getId().equals(this.getId())
        //                && otherTag.getName().equals(this.getName())
        //                && otherTag.getDescription().equals(this.getDescription());

        return otherTag.getName().equals(this.getName())
                && otherTag.getDescription().equals(this.getDescription());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, description);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(" Name: ")
                .append(getName())
                .append(" Description: ")
                .append(getDescription());
        return builder.toString();
    }
}
