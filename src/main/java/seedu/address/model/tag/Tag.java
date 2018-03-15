package seedu.address.model.tag;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

/**
 * Represents a Tag in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Tag {

    private final Name name;
    private final Phone phone;
    private final Email email;
    private final Address address;

    /**
     * Every field must be present and not null.
     */
    public Tag(Name name, Phone phone, Email email, Address address) {
        requireAllNonNull(name, phone, email, address);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
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
                && otherTag.getPhone().equals(this.getPhone())
                && otherTag.getEmail().equals(this.getEmail())
                && otherTag.getAddress().equals(this.getAddress());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Phone: ")
                .append(getPhone())
                .append(" Email: ")
                .append(getEmail())
                .append(" Address: ")
                .append(getAddress());
        return builder.toString();
    }

}
