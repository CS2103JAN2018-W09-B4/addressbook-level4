package seedu.address.testutil;

import java.util.UUID;

import seedu.address.model.tag.Name;
import seedu.address.model.tag.Tag;

/**
 * A utility class to help with building Tag objects.
 */
public class TagBuilder {

    public static final String DEFAULT_NAME = "Physics";

    private Name name;
    private UUID id;

    public TagBuilder() {
        id = UUID.randomUUID();
        name = new Name(DEFAULT_NAME);
    }

    /**
     * Initializes the TagBuilder with the data of {@code tagToCopy}.
     */
    public TagBuilder(Tag tagToCopy) {
        id = tagToCopy.getId();
        name = tagToCopy.getName();
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


    public Tag build() {
        return new Tag(id, name);
    }
}
