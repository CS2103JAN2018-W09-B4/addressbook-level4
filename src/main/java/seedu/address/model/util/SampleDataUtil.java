package seedu.address.model.util;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.tag.Name;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.exceptions.DuplicateTagException;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Tag[] getSampleTags() {
        return new Tag[] {
            new Tag(new Name("Biology")),
            new Tag(new Name("Chemistry")),
            new Tag(new Name("Mathematics")),
            new Tag(new Name("Physics")),
            new Tag(new Name("Chinese")),
            new Tag(new Name("English"))
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        try {
            AddressBook sampleAb = new AddressBook();
            for (Tag sampleTag : getSampleTags()) {
                sampleAb.addTag(sampleTag);
            }
            return sampleAb;
        } catch (DuplicateTagException e) {
            throw new AssertionError("sample data cannot contain duplicate tags", e);
        }
    }

}
