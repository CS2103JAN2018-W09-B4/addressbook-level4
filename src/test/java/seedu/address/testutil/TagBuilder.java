package seedu.address.testutil;

import java.util.UUID;

import seedu.address.model.tag.Description;
import seedu.address.model.tag.Name;
import seedu.address.model.tag.Tag;

/**
 * A utility class to help with building Tag objects.
 */
public class TagBuilder {

    public static final String DEFAULT_NAME = "Physics";
    public static final String DEFAULT_DESCRIPTION = "physics physics";

    private Name name;
    private Description description;
    private UUID id;

    public TagBuilder() {
        id = UUID.randomUUID();
        name = new Name(DEFAULT_NAME);
        description = new Description(DEFAULT_DESCRIPTION);
    }

    /**
     * Initializes the TagBuilder with the data of {@code tagToCopy}.
     */
    public TagBuilder(Tag tagToCopy) {
        id = tagToCopy.getId();
        name = tagToCopy.getName();
        description = tagToCopy.getDescription();
    }

    /**
     * Sets the {@code id} of the {@code Tag} that we are building
     */
    public TagBuilder withId(String id) {
        this.id = UUID.fromString(id);
        return this;
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
    public TagBuilder withDescription(String address) {
        this.description = new Description(address);
        return this;
    }

    public Tag build() {
        return new Tag(id, name, description);
    }
}
