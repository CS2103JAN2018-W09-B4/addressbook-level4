package seedu.address.model.util;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.tag.Description;
import seedu.address.model.tag.Name;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.exceptions.DuplicateTagException;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Tag[] getSampleTags() {
        return new Tag[] {
            new Tag(new Name("Alex Yeoh"),
                new Description("Blk 30 Geylang Street 29, #06-40")),
            new Tag(new Name("Bernice Yu"),
                new Description("Blk 30 Lorong 3 Serangoon Gardens, #07-18")),
            new Tag(new Name("Charlotte Oliveiro"),
                new Description("Blk 11 Ang Mo Kio Street 74, #11-04")),
            new Tag(new Name("David Li"),
                new Description("Blk 436 Serangoon Gardens Street 26, #16-43")),
            new Tag(new Name("Irfan Ibrahim"),
                new Description("Blk 47 Tampines Street 20, #17-35")),
            new Tag(new Name("Roy Balakrishnan"),
                new Description("Blk 45 Aljunied Street 85, #11-31"))
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
