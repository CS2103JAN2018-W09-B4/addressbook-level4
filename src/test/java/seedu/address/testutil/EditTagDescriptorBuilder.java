package seedu.address.testutil;

import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditTagDescriptor;
import seedu.address.model.tag.Address;
import seedu.address.model.tag.Name;
import seedu.address.model.tag.Tag;

/**
 * A utility class to help with building EditTagDescriptor objects.
 */
public class EditTagDescriptorBuilder {

    private EditTagDescriptor descriptor;

    public EditTagDescriptorBuilder() {
        descriptor = new EditCommand.EditTagDescriptor();
    }

    public EditTagDescriptorBuilder(EditTagDescriptor descriptor) {
        this.descriptor = new EditTagDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditTagDescriptor} with fields containing {@code tag}'s details
     */
    public EditTagDescriptorBuilder(Tag tag) {
        descriptor = new EditTagDescriptor();
        descriptor.setName(tag.getName());
        descriptor.setAddress(tag.getAddress());
    }

    /**
     * Sets the {@code Name} of the {@code EditTagDescriptor} that we are building.
     */
    public EditTagDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }


    /**
     * Sets the {@code Address} of the {@code EditTagDescriptor} that we are building.
     */
    public EditTagDescriptorBuilder withAddress(String address) {
        descriptor.setAddress(new Address(address));
        return this;
    }

    public EditTagDescriptor build() {
        return descriptor;
    }
}
