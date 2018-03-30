package seedu.address.model.tag;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.tag.exceptions.DuplicateTagException;
import seedu.address.model.tag.exceptions.TagNotFoundException;

/**
 * A list of tags that enforces uniqueness between its elements and does not allow nulls.
 *
 * Supports a minimal set of list operations.
 *
 * @see Tag#equals(Object)
 */
public class UniqueTagList implements Iterable<Tag> {

    private final ObservableList<Tag> internalList = FXCollections.observableArrayList();

    /**
     * Returns true if the list contains an equivalent tag as the given argument.
     */
    public boolean contains(Tag toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Adds a tag to the list.
     *
     * @return TagResult which contains the tag, and whether the tag is a new tag.
     */
    public AddTagResult add(Tag toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            Tag existingTag = internalList.get(internalList.indexOf(toAdd));
            return new AddTagResult(true, existingTag);
        } else {
            internalList.add(toAdd);
            return new AddTagResult(false, toAdd);
        }
    }

    /**
     * Replaces the tag {@code target} in the list with {@code editedTag}.
     *
     * @throws DuplicateTagException if the replacement is equivalent to another existing tag in the list.
     * @throws TagNotFoundException if {@code target} could not be found in the list.
     */
    public void setTag(Tag target, Tag editedTag)
            throws DuplicateTagException, TagNotFoundException {
        requireNonNull(editedTag);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new TagNotFoundException(target);
        }

        if (!target.equals(editedTag) && internalList.contains(editedTag)) {
            throw new DuplicateTagException();
        }

        internalList.set(index, editedTag);
    }

    /**
     * Removes the equivalent tag from the list.
     *
     * @throws TagNotFoundException if no such tag could be found in the list.
     */
    public boolean remove(Tag toRemove) throws TagNotFoundException {
        requireNonNull(toRemove);
        final boolean tagFoundAndDeleted = internalList.remove(toRemove);
        if (!tagFoundAndDeleted) {
            throw new TagNotFoundException(toRemove);
        }
        return tagFoundAndDeleted;
    }

    public void setTags(UniqueTagList replacement) {
        this.internalList.setAll(replacement.internalList);
    }

    public void setTags(List<Tag> tags) throws DuplicateTagException {
        requireAllNonNull(tags);
        final UniqueTagList replacement = new UniqueTagList();
        for (final Tag tag : tags) {
            replacement.add(tag);
        }
        setTags(replacement);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Tag> asObservableList() {
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public Iterator<Tag> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueTagList // instanceof handles nulls
                        && this.internalList.equals(((UniqueTagList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}
