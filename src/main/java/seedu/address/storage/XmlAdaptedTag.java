package seedu.address.storage;

import java.util.Objects;
import java.util.UUID;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Description;
import seedu.address.model.tag.Name;
import seedu.address.model.tag.Tag;

/**
 * JAXB-friendly version of the Tag.
 */
public class XmlAdaptedTag {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Tag's %s field is missing!";

    @XmlElement(required = true)
    private String id;

    @XmlElement(required = true)
    private String name;

    @XmlElement(required = true)
    private String description;

    /**
     * Constructs an XmlAdaptedTag.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedTag() {}

    /**
     * Constructs an {@code XmlAdaptedTag} with the given tag details.
     */
    public XmlAdaptedTag(String id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    /**
     * Converts a given Tag into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedTag
     */
    public XmlAdaptedTag(Tag source) {
        id = source.getId().toString();
        name = source.getName().fullName;
        description = source.getDescription().value;
    }

    /**
     * Converts this jaxb-friendly adapted tag object into the model's Tag object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted tag
     */
    public Tag toModelType() throws IllegalValueException {
        if (this.id == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Id"));
        }

        if (this.name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(this.name)) {
            throw new IllegalValueException(Name.MESSAGE_NAME_CONSTRAINTS);
        }
        final Name name = new Name(this.name);

        if (this.description == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Description.class.getSimpleName()));
        }
        if (!Description.isValidDescription(this.description)) {
            throw new IllegalValueException(Description.MESSAGE_DESCRIPTION_CONSTRAINTS);
        }
        final Description description = new Description(this.description);

        return new Tag(UUID.fromString(id), name, description);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedTag)) {
            return false;
        }

        XmlAdaptedTag otherTag = (XmlAdaptedTag) other;
        return Objects.equals(id, otherTag.id)
                && Objects.equals(name, otherTag.name)
                && Objects.equals(description, otherTag.description);
    }
}
