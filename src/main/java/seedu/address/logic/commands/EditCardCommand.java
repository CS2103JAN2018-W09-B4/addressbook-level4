package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BACK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FRONT;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.card.Card;
import seedu.address.model.card.exceptions.CardNotFoundException;
import seedu.address.model.card.exceptions.DuplicateCardException;
import seedu.address.model.cardtag.DuplicateEdgeException;
import seedu.address.model.cardtag.EdgeNotFoundException;
import seedu.address.model.tag.Tag;

/**
 * Edits the details of an existing card in the address book.
 */
public class EditCardCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "editc";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the card identified "
            + "by the index number used in the last card listing. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_FRONT + "FRONT] "
            + "[" + PREFIX_BACK + "BACK]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_FRONT + " What is 1 + 1? " + PREFIX_BACK + " 2";

    public static final String MESSAGE_EDIT_CARD_SUCCESS = "Edited Card: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_CARD = "This card already exists in the address book.";

    private final Index index;
    private final EditCardDescriptor editCardDescriptor;

    private Card cardToEdit;
    private Card editedCard;

    private List<Tag> tagsToEdit;
    private Optional<Set<Tag>> editedTags;

    /**
     * @param index of the card in the filtered card list to edit
     * @param editCardDescriptor details to edit the card with
     */
    public EditCardCommand(Index index, EditCardDescriptor editCardDescriptor) {
        requireNonNull(index);
        requireNonNull(editCardDescriptor);

        this.index = index;
        this.editCardDescriptor = new EditCardDescriptor(editCardDescriptor);
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        try {
            model.updateCard(cardToEdit, editedCard);
        } catch (DuplicateCardException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_CARD);
        } catch (CardNotFoundException pnfe) {
            throw new AssertionError("The target card cannot be missing");
        }

        if (editedTags.isPresent()) {
            try {
                model.updateTagsForCard(editedCard, editedTags.get());
            } catch (DuplicateEdgeException dpe) {
                throw new IllegalStateException("Should not be able to reach here.");
            } catch (EdgeNotFoundException enfe) {
                throw new IllegalStateException("Should not be able to reach here.");
            }
        }

        model.showAllCards();
        return new CommandResult(String.format(MESSAGE_EDIT_CARD_SUCCESS, editedCard));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<Card> lastShownList = model.getFilteredCardList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_CARD_DISPLAYED_INDEX);
        }

        cardToEdit = lastShownList.get(index.getZeroBased());
        tagsToEdit = model.getTags(cardToEdit);
        editedCard = createEditedCard(cardToEdit, editCardDescriptor);
        editedTags = editCardDescriptor.getTags();
    }

    /**
     * Creates and returns a {@code Card} with the details of {@code cardToEdit}
     * edited with {@code editCardDescriptor}.
     */
    private static Card createEditedCard(Card cardToEdit, EditCardDescriptor editCardDescriptor) {
        assert cardToEdit != null;

        String updatedFront = editCardDescriptor.getFront().orElse(cardToEdit.getFront());
        String updatedBack = editCardDescriptor.getBack().orElse(cardToEdit.getBack());

        return new Card(cardToEdit.getId(), updatedFront, updatedBack);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCardCommand)) {
            return false;
        }

        // state check
        EditCardCommand e = (EditCardCommand) other;
        return index.equals(e.index)
                && editCardDescriptor.equals(e.editCardDescriptor)
                && Objects.equals(cardToEdit, e.cardToEdit);
    }

    /**
     * Stores the details to edit the card with. Each non-empty field value will replace the
     * corresponding field value of the card.
     */
    public static class EditCardDescriptor {
        private String front;
        private String back;
        private UUID id;
        private Set<Tag> tags;

        public EditCardDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code cards} is used internally.
         */
        public EditCardDescriptor(EditCardDescriptor toCopy) {
            setFront(toCopy.front);
            setBack(toCopy.back);
            setTags(toCopy.tags);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(this.front, this.back, this.tags);
        }

        public void setFront(String front) {
            this.front = front;
        }

        public Optional<String> getFront() {
            return Optional.ofNullable(front);
        }

        public void setBack(String back) {
            this.back = back;
        }

        public Optional<String> getBack() {
            return Optional.ofNullable(back);
        }

        public Optional<Set<Tag>> getTags() {
            return Optional.ofNullable(tags);
        }

        public void setTags(Set<Tag> tags) {
            this.tags = tags;
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditCardDescriptor)) {
                return false;
            }

            // state check
            EditCardDescriptor e = (EditCardDescriptor) other;

            return getFront().equals(e.getFront())
                    && getBack().equals(e.getBack())
                    && getTags().equals(e.getTags());
        }


        public void setId(UUID uuid) {
            this.id = uuid;
        }
    }
}
