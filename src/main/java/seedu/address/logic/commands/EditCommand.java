package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_TAGS;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.tag.Address;
import seedu.address.model.tag.Email;
import seedu.address.model.tag.Name;
import seedu.address.model.tag.Phone;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.exceptions.DuplicateTagException;
import seedu.address.model.tag.exceptions.TagNotFoundException;

/**
 * Edits the details of an existing tag in the address book.
 */
public class EditCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the tag identified "
            + "by the index number used in the last tag listing. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_ADDRESS + "ADDRESS]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_EMAIL + "johndoe@example.com";

    public static final String MESSAGE_EDIT_TAG_SUCCESS = "Edited Tag: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_TAG = "This tag already exists in the address book.";

    private final Index index;
    private final EditTagDescriptor editTagDescriptor;

    private Tag tagToEdit;
    private Tag editedTag;

    /**
     * @param index of the tag in the filtered tag list to edit
     * @param editTagDescriptor details to edit the tag with
     */
    public EditCommand(Index index, EditTagDescriptor editTagDescriptor) {
        requireNonNull(index);
        requireNonNull(editTagDescriptor);

        this.index = index;
        this.editTagDescriptor = new EditTagDescriptor(editTagDescriptor);
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        try {
            model.updateTag(tagToEdit, editedTag);
        } catch (DuplicateTagException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_TAG);
        } catch (TagNotFoundException pnfe) {
            throw new AssertionError("The target tag cannot be missing");
        }
        model.updateFilteredTagList(PREDICATE_SHOW_ALL_TAGS);
        return new CommandResult(String.format(MESSAGE_EDIT_TAG_SUCCESS, editedTag));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<Tag> lastShownList = model.getFilteredTagList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TAG_DISPLAYED_INDEX);
        }

        tagToEdit = lastShownList.get(index.getZeroBased());
        editedTag = createEditedTag(tagToEdit, editTagDescriptor);
    }

    /**
     * Creates and returns a {@code Tag} with the details of {@code tagToEdit}
     * edited with {@code editTagDescriptor}.
     */
    private static Tag createEditedTag(Tag tagToEdit, EditTagDescriptor editTagDescriptor) {
        assert tagToEdit != null;

        Name updatedName = editTagDescriptor.getName().orElse(tagToEdit.getName());
        Phone updatedPhone = editTagDescriptor.getPhone().orElse(tagToEdit.getPhone());
        Email updatedEmail = editTagDescriptor.getEmail().orElse(tagToEdit.getEmail());
        Address updatedAddress = editTagDescriptor.getAddress().orElse(tagToEdit.getAddress());

        return new Tag(updatedName, updatedPhone, updatedEmail, updatedAddress);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand)) {
            return false;
        }

        // state check
        EditCommand e = (EditCommand) other;
        return index.equals(e.index)
                && editTagDescriptor.equals(e.editTagDescriptor)
                && Objects.equals(tagToEdit, e.tagToEdit);
    }

    /**
     * Stores the details to edit the tag with. Each non-empty field value will replace the
     * corresponding field value of the tag.
     */
    public static class EditTagDescriptor {
        private Name name;
        private Phone phone;
        private Email email;
        private Address address;

        public EditTagDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditTagDescriptor(EditTagDescriptor toCopy) {
            setName(toCopy.name);
            setPhone(toCopy.phone);
            setEmail(toCopy.email);
            setAddress(toCopy.address);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(this.name, this.phone, this.email, this.address);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setPhone(Phone phone) {
            this.phone = phone;
        }

        public Optional<Phone> getPhone() {
            return Optional.ofNullable(phone);
        }

        public void setEmail(Email email) {
            this.email = email;
        }

        public Optional<Email> getEmail() {
            return Optional.ofNullable(email);
        }

        public void setAddress(Address address) {
            this.address = address;
        }

        public Optional<Address> getAddress() {
            return Optional.ofNullable(address);
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditTagDescriptor)) {
                return false;
            }

            // state check
            EditTagDescriptor e = (EditTagDescriptor) other;

            return getName().equals(e.getName())
                    && getPhone().equals(e.getPhone())
                    && getEmail().equals(e.getEmail())
                    && getAddress().equals(e.getAddress());
        }
    }
}
