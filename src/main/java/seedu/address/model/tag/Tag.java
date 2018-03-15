package seedu.address.model.tag;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

/**
 * Represents a Tag in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Tag {

    private final Name name;
    private final Address address;

    /**
     * Every field must be present and not null.
     */
    public Tag(Name name, Address address) {
        requireAllNonNull(name, address);
        this.name = name;
        this.address = address;
    }

    public Name getName() {
        return name;
    }

    public Address getAddress() {
        return address;
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
        return otherTag.getName().equals(this.getName())
                && otherTag.getAddress().equals(this.getAddress());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, address);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Address: ")
                .append(getAddress());
        return builder.toString();
    }

}
