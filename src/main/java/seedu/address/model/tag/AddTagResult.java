package seedu.address.model.tag;

/**
 * Result class for adding a tag. Returns both the tag, and an indication if this is a new tag.
 */
public class AddTagResult {
    private boolean tagExists;
    private Tag tag;

    public AddTagResult(boolean tagExists, Tag tag) {
        this.tagExists = tagExists;
        this.tag = tag;
    }

    public boolean isTagExists() {
        return tagExists;
    }

    public Tag getTag() {
        return tag;
    }
}
