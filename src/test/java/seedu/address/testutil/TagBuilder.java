package seedu.address.testutil;

import seedu.address.model.tag.Description;
import seedu.address.model.tag.Name;
import seedu.address.model.tag.Tag;

/**
 * A utility class to help with building Tag objects.
 */
public class TagBuilder {

    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";

    private Name name;
    private Description description;

    public TagBuilder() {
        name = new Name(DEFAULT_NAME);
        description = new Description(DEFAULT_ADDRESS);
    }

    /**
     * Initializes the TagBuilder with the data of {@code tagToCopy}.
     */
    public TagBuilder(Tag tagToCopy) {
        name = tagToCopy.getName();
        description = tagToCopy.getDescription();
    }

    /**
     * Sets the {@code Name} of the {@code Tag} that we are building.
     */
    public TagBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code Description} of the {@code Tag} that we are building.
     */
    public TagBuilder withAddress(String address) {
        this.description = new Description(address);
        return this;
    }

    public Tag build() {
        return new Tag(name, description);
    }

}
