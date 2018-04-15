package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_TAGS;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.tag.Name;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.exceptions.DuplicateTagException;
import seedu.address.model.tag.exceptions.TagNotFoundException;

/**
 * Edits the details of an existing tag in the address book.
 */
public class EditCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "edit";

    public static final String PARAMS = "INDEX "
            + "[" + PREFIX_NAME + "NAME]";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the tag identified "
            + "by the index number used in the last tag listing. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: " + PARAMS
            + " Example: " + COMMAND_WORD + " 1 "
            + PREFIX_NAME + "Midterms Sad";

    public static final String AUTOCOMPLETE_TEXT = COMMAND_WORD + " " + PARAMS;

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

        UUID id = tagToEdit.getId();
        Name updatedName = editTagDescriptor.getName().orElse(tagToEdit.getName());

        return new Tag(id, updatedName);
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

        public EditTagDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditTagDescriptor(EditTagDescriptor toCopy) {
            setName(toCopy.name);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(this.name);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
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

            return getName().equals(e.getName());
        }
    }
}
