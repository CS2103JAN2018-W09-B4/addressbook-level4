package seedu.address.testutil;

import java.util.UUID;

import seedu.address.logic.commands.EditCardCommand;
import seedu.address.logic.commands.EditCardCommand.EditCardDescriptor;
import seedu.address.model.card.Card;

/**
 * A utility class to help with building EditCardDescriptor objects.
 */
public class EditCardDescriptorBuilder {

    private EditCardDescriptor descriptor;

    public EditCardDescriptorBuilder() {
        descriptor = new EditCardCommand.EditCardDescriptor();
    }

    public EditCardDescriptorBuilder(EditCardDescriptor descriptor) {
        this.descriptor = new EditCardDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditCardDescriptor} with fields containing {@code card}'s details
     */
    public EditCardDescriptorBuilder(Card card) {
        descriptor = new EditCardDescriptor();
        descriptor.setFront(card.getFront());
        descriptor.setBack(card.getBack());
    }

    /**
     * Sets the {@code Front} of the {@code EditCardDescriptor} that we are building.
     */
    public EditCardDescriptorBuilder withFront(String front) {
        descriptor.setFront(front);
        return this;
    }

    /**
     * Sets the {@code Back} of the {@code EditCardDescriptor} that we are building.
     */
    public EditCardDescriptorBuilder withBack(String back) {
        descriptor.setBack(back);
        return this;
    }

    /**
     * Sets the {@code UUID} of the {@code EditCardDescriptor} that we are building.
     */
    public EditCardDescriptorBuilder withUuid(UUID uuid) {
        descriptor.setUuid(uuid);
        return this;
    }

    public EditCardDescriptor build() {
        return descriptor;
    }
}
