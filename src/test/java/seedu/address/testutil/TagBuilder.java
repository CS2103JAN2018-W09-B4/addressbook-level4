package seedu.address.testutil;

import seedu.address.model.tag.Address;
import seedu.address.model.tag.Email;
import seedu.address.model.tag.Name;
import seedu.address.model.tag.Phone;
import seedu.address.model.tag.Tag;

/**
 * A utility class to help with building Tag objects.
 */
public class TagBuilder {

    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "alice@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";

    private Name name;
    private Phone phone;
    private Email email;
    private Address address;

    public TagBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
    }

    /**
     * Initializes the TagBuilder with the data of {@code tagToCopy}.
     */
    public TagBuilder(Tag tagToCopy) {
        name = tagToCopy.getName();
        phone = tagToCopy.getPhone();
        email = tagToCopy.getEmail();
        address = tagToCopy.getAddress();
    }

    /**
     * Sets the {@code Name} of the {@code Tag} that we are building.
     */
    public TagBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Tag} that we are building.
     */
    public TagBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Tag} that we are building.
     */
    public TagBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Tag} that we are building.
     */
    public TagBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    public Tag build() {
        return new Tag(name, phone, email, address);
    }

}
