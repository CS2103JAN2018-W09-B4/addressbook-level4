package seedu.address.storage;

import java.util.Objects;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Address;
import seedu.address.model.tag.Name;
import seedu.address.model.tag.Tag;

/**
 * JAXB-friendly version of the Tag.
 */
public class XmlAdaptedTag {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Tag's %s field is missing!";

    @XmlElement(required = true)
    private String name;

    @XmlElement(required = true)
    private String address;

    /**
     * Constructs an XmlAdaptedTag.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedTag() {}

    /**
     * Constructs an {@code XmlAdaptedTag} with the given tag details.
     */
    public XmlAdaptedTag(String name, String address) {
        this.name = name;
        this.address = address;
    }

    /**
     * Converts a given Tag into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedTag
     */
    public XmlAdaptedTag(Tag source) {
        name = source.getName().fullName;
        address = source.getAddress().value;
    }

    /**
     * Converts this jaxb-friendly adapted tag object into the model's Tag object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted tag
     */
    public Tag toModelType() throws IllegalValueException {

        if (this.name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(this.name)) {
            throw new IllegalValueException(Name.MESSAGE_NAME_CONSTRAINTS);
        }
        final Name name = new Name(this.name);

        if (this.address == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName()));
        }
        if (!Address.isValidAddress(this.address)) {
            throw new IllegalValueException(Address.MESSAGE_ADDRESS_CONSTRAINTS);
        }
        final Address address = new Address(this.address);

        return new Tag(name, address);
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
        return Objects.equals(name, otherTag.name)
                && Objects.equals(address, otherTag.address);
    }
}
